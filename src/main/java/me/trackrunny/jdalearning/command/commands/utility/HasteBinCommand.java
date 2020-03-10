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

import com.fasterxml.jackson.databind.ObjectMapper;
import me.duncte123.botcommons.web.ContentType;
import me.duncte123.botcommons.web.WebParserUtils;
import me.duncte123.botcommons.web.WebUtils;
import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class HasteBinCommand implements ICommand {
    private static final String hasteBin = "https://hasteb.in/";

    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();
        final EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("→ Hastebin Code");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        if (args.isEmpty()) {
            embedBuilder.setTitle("→ Missing Arguments");
            embedBuilder.setDescription("• Usage: `j!hastebin <text>`");
            embedBuilder.setFooter(Variables.embedFooter);
            embedBuilder.setColor(new Color(241, 90, 36));

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        final String invoke = this.getName();
        final String contentRaw = ctx.getMessage().getContentRaw();
        final int index = contentRaw.indexOf(invoke) + invoke.length();
        final String body = contentRaw.substring(index).trim();

        this.createHaste(body, (text) ->
                channel.sendMessage(embedBuilder.setDescription(String.format("• Hastebin code: **%s**", text)).build()).queue());
    }

    private void createHaste(String text, Consumer<String> callback) {
        Request request = WebUtils.defaultRequest()
                .post(RequestBody.create(text.getBytes()))
                .addHeader("Content-Type", ContentType.TEXT_HTML.getType())
                .url(hasteBin + "documents")
                .build();

        WebUtils.ins.prepareRaw(request, (r) -> WebParserUtils.toJSONObject(r, new ObjectMapper())).async(
                (json) -> {
                    String key = json.get("key").asText();

                    callback.accept(hasteBin + key);
                },
                (e) -> callback.accept("Error: " + e.getMessage())
        );
    }

    @Override
    public String getName() {
        return "hastebin";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Uploads a section of code to hastebin" +
                                    "\n• Usage: `j!hastebin <text>`");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(241, 90, 36));

        return embedBuilder.build();
    }
}
