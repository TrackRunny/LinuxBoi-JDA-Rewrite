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

import com.fasterxml.jackson.databind.JsonNode;
import me.duncte123.botcommons.web.WebUtils;
import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class McpeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel textChannel = ctx.getChannel();

        final EmbedBuilder embedBuilder = new EmbedBuilder();

        if (args.isEmpty() || args.size() < 2) {
            embedBuilder.setTitle("→ Missing Arguments");
            embedBuilder.setDescription("• Usage: `j!mcpe <server> <port>`");
            embedBuilder.setFooter(Variables.embedFooter);
            embedBuilder.setColor(new Color(Variables.embedColor));

            textChannel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        final String server = ctx.getArgs().get(0);
        final String port = ctx.getArgs().get(1);

        WebUtils.ins.getJSONObject(String.format("https://mcapi.us/server/query?ip=%s&port=%s", server, port)).async((json) -> {
            if (!json.get("status").asText().equals("success")) {
                textChannel.sendMessage("I was not able to query the server successfully").queue();
                return;
            }

            final JsonNode playerInfo = json.get("players");

            final String players = String.format("%s/%s", playerInfo.get("now").asText(), playerInfo.get("max").asText());
            final String map = json.get("map").asText();
            final String version = json.get("version").asText();
            final String software = json.get("server_mod").asText();
            final String motd = json.get("hostname").asText();

            embedBuilder.setTitle("→ Server Status");
            embedBuilder.addField("• Address", server, true);
            embedBuilder.addField("• Port", port, true);
            embedBuilder.addField("• Players", players, true);
            embedBuilder.addField("• Map", map, true);
            embedBuilder.addField("• Version", version, true);
            embedBuilder.addField("• Software", software, true);
            embedBuilder.addField("• MOTD", motd, false);
            embedBuilder.setColor(Variables.embedColor);

            textChannel.sendMessage(embedBuilder.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "mcpe";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Querys a MCPE server! \nUsage: `j!mcpe <server> <port>`");
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }
}