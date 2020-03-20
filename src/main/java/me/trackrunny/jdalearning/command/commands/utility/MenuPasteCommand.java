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

import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.menudocs.paste.PasteClient;
import org.menudocs.paste.PasteClientBuilder;
import org.menudocs.paste.PasteHost;

import java.awt.*;
import java.util.List;

public class MenuPasteCommand implements ICommand {

    private final PasteClient client = new PasteClientBuilder()
            .setUserAgent("JDA-LearningBot")
            .setDefaultExpiry("1h")
            .setPasteHost(PasteHost.MENUDOCS)
            .build();

    private static final String menuDocs = "https://paste.menudocs.org/paste/";

    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Uploaded Code");
        embedBuilder.setFooter("— LinuxBoi | Rewrote in JDA");
        embedBuilder.setColor(new Color(241, 90, 36));

        if (args.size() < 2) {
            embedBuilder.setTitle("→ Missing Arguments");
            embedBuilder.setDescription("• Usage: `j!menupaste <language> <text>`");
            embedBuilder.setFooter("— LinuxBoi | Rewrote in JDA");
            embedBuilder.setColor(new Color(241, 90, 36));

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        final String language = args.get(0);
        final String contentRaw = ctx.getMessage().getContentRaw();
        final int index = contentRaw.indexOf(language) + language.length();
        final String text = contentRaw.substring(index).trim();

        client.createPaste(language, text).async(
                (id) -> channel.sendMessage(embedBuilder.setDescription(String.format("• MenuDocs code: **%s%s**", menuDocs, id)).build()).queue()
        );
    }

    @Override
    public String getName() {
        return "menupaste";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Uploads a section of code to paste.menudocs.org" +
                                    "\n• Usage: `j!menupaste <language> <text>`");
        embedBuilder.setFooter("— LinuxBoi | Rewrote in JDA");
        embedBuilder.setColor(new Color(241, 90, 36));

        return embedBuilder.build();
    }
}
