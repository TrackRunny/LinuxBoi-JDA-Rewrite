/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * JDALearningBot - Discord bot to learn JDA 4 library                       *
 * Copyright (C) 2019-2020 TrackRunny                                        *
 *                                                                           *
 * This program is free software: you can redistribute it and/or modify      *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * This program is distributed in the hope that it will be useful,           *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with this program. If not, see <https://www.gnu.org/licenses/>.     *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package me.trackrunny.jdalearning.command.commands.utility;

import me.duncte123.botcommons.web.WebUtils;
import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class PasswordCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();
        final Member member = ctx.getMember();

        final EmbedBuilder embedBuilder = new EmbedBuilder();
        final String length = args.get(0);

        if (args.isEmpty()) {
            embedBuilder.setTitle("→ Missing Arguments");
            embedBuilder.setDescription("• Usage: `j!password <length>`");
            embedBuilder.setFooter(Variables.embedFooter);
            embedBuilder.setColor(new Color(Variables.embedColor));

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        WebUtils.ins.getJSONObject(String.format("https://apis.duncte123.me/random-string/%s", length)).async((json) -> {
            if (!json.get("success").asBoolean()) {
                channel.sendMessage("• Something did not go right! Please try again later.\"").queue();
                System.out.println(json);
                return;
            }

            final String data = json.get("data").asText();

            embedBuilder.setTitle("→ Generated Password");
            embedBuilder.setDescription(String.format("• `%s`", data));
            embedBuilder.setColor(Variables.embedColor);
            embedBuilder.setFooter(Variables.embedFooter);

            member.getUser().openPrivateChannel().queue((textChannel) -> {
                textChannel.sendMessage(embedBuilder.build()).queue();
            });
        });
    }

    @Override
    public String getName() {
        return "password";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Generates a random password based on a certain length");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }
}
