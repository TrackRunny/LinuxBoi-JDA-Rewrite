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

import io.ipinfo.api.IPInfo;
import io.ipinfo.api.model.IPResponse;
import me.trackrunny.jdalearning.Config;
import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class IpInfoCommand implements ICommand {
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();

        final EmbedBuilder embedBuilder = new EmbedBuilder();
        final IPInfo ipInfo = IPInfo.builder().setToken(Config.get("IP_INFO_TOKEN")).build();

        if (args.isEmpty()) {
            embedBuilder.setTitle("Missing Arguments");
            embedBuilder.setDescription("Usage: `j!ip <address>`");
            embedBuilder.setFooter("LinuxBoi | Rewrote in JDA");
            embedBuilder.setColor(new Color(241, 90, 36));
            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        try {
            final String latitudeAndLongitude, city, region, country, postalCode, org;

            final IPResponse response = ipInfo.lookupIP(message.getContentRaw().substring(5));
            final String ipAddress = String.format("`%s`", message.getContentRaw().substring(5));

            embedBuilder.setTitle("IP Address Lookup");
            embedBuilder.setColor(new Color(241, 90, 36));
            embedBuilder.addField("IP Address", ipAddress, true);
            embedBuilder.setFooter("LinuxBoi | Rewrote in JDA");

            if (response.getLatitude() == null || response.getLongitude() == null) {
                latitudeAndLongitude = "`Latitude and longitude not found.`";
            } else {
                latitudeAndLongitude = String.format("`%s | %s`", response.getLatitude(), response.getLongitude());
            }

            embedBuilder.addField("Latitude & Longitude", latitudeAndLongitude, true);

            if (response.getCity() == null) {
                city = "`City not found.`";
            } else {
                city = String.format("`%s`", response.getCity());
            }

            embedBuilder.addField("City", city, true);

            if (response.getRegion() == null) {
                region = "`Region not found.`";
            } else {
                region = String.format("`%s`", response.getRegion());
            }

            embedBuilder.addField("Region / State", region, true);

            if (response.getCountryName() == null) {
                country = "`Country not found.`";
            } else {
                country = String.format("`%s`", response.getCountryName());
            }

            embedBuilder.addField("County", country, true);

            if (response.getPostal() == null) {
                postalCode = "`Postal code not found.`";
            } else {
                postalCode = String.format("`%s`", response.getPostal());
            }

            embedBuilder.addField("Postal Code", postalCode, true);

            if (response.getOrg() == null) {
                org = "`ISP not found.`";
            } else {
                org = String.format("`%s`", response.getOrg());
            }

            embedBuilder.addField("ISP-Name", org, true);

            channel.sendMessage(embedBuilder.build()).queue();
        } catch (Exception e) {
            embedBuilder.setTitle("Invalid IP Address");
            embedBuilder.setDescription("Please put a valid IP Address." +
                                        "\nUsage: `j!ip <address>`");
            channel.sendMessage(embedBuilder.build()).queue();
        }
    }

    public String getName() {
        return "ip";
    }

    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Command Usage");
        embedBuilder.setDescription("Shows information about an IP address." +
                                    "\nUsage: `j!ip <address>");
        embedBuilder.setFooter("LinuxBoi | Rewrote in JDA");
        embedBuilder.setColor(new Color(241, 90, 36));
        return embedBuilder.build();
    }
}