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

package me.trackrunny.jdalearning.command.commands.meme;

import me.trackrunny.jdalearning.Config;
import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.explodingbush.ksoftapi.KSoftAPI;
import net.explodingbush.ksoftapi.entities.Reddit;
import net.explodingbush.ksoftapi.enums.ImageType;

import java.awt.*;

public class MemeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        final EmbedBuilder embedBuilder = new EmbedBuilder();

        final KSoftAPI kSoftAPI = new KSoftAPI(Config.get("KSOFT_TOKEN"));
        final Reddit reddit = kSoftAPI.getRedditImage(ImageType.RANDOM_MEME).execute();

        embedBuilder.setTitle("→ " + reddit.getTitle());
        embedBuilder.setImage(reddit.getImageUrl());
        embedBuilder.setFooter("— LinuxBoi | Rewrote in JDA");
        embedBuilder.setColor(new Color(241, 90, 36));

        channel.sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "meme";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("→ Meme Info");
        embedBuilder.setDescription("• Sends a random Reddit meme.");
        embedBuilder.setFooter("— LinuxBoi | Rewrote in JDA");
        embedBuilder.setColor(new Color(241, 90, 36));

        return embedBuilder.build();
    }
}