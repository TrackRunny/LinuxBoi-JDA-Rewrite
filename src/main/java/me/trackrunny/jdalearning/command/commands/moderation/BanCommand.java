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

package me.trackrunny.jdalearning.command.commands.moderation;

import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class BanCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final Member member = ctx.getMember();

        final EmbedBuilder embedBuilder = new EmbedBuilder();

        if (args.size() < 1 || message.getMentionedMembers().isEmpty()) {
            embedBuilder.setTitle("→ Missing Arguments");
            embedBuilder.setDescription("• Usage: `j!ban <@user> [reason]`");
            embedBuilder.setFooter(Variables.embedFooter);
            embedBuilder.setColor(new Color(Variables.embedColor));
            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        final Member target = message.getMentionedMembers().get(0);

        if (!member.canInteract(target) || !member.hasPermission(Permission.BAN_MEMBERS)) {
            embedBuilder.setTitle("→ Missing Permissions");
            embedBuilder.setDescription("• You do not have permissions to ban members.");
            embedBuilder.setFooter(Variables.embedFooter);
            embedBuilder.setColor(new Color(Variables.embedColor));
            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        String reason = String.join(" ", args.subList(1, args.size()));

        if (reason.isEmpty()) {
            reason = "No reason provided.";
        } else {
            reason = String.join(" ", args.subList(1, args.size()));
        }

        embedBuilder.setTitle("→ Successfully Banned User");
        embedBuilder.setDescription(String.format("• Banned: %s\nReason: `%s`", target.getAsMention(), reason));
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        ctx.getGuild()
                .ban(target, 7)
                .reason(reason)
                .queue(__ -> channel.sendMessage(embedBuilder.build()).queue());
    }

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Bans a user from a guild.\nUsage: `j!ban <@user> [reason]`");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));
        return embedBuilder.build();
    }
}
