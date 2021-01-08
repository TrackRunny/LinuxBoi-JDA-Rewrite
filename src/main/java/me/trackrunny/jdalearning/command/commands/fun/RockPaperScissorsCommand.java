 
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

package me.trackrunny.jdalearning.command.commands.fun;

import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class RockPaperScissorsCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        final EmbedBuilder embedBuilder = new EmbedBuilder();

        if (args.isEmpty())  {
            embedBuilder.setTitle("→ Missing Arguments");
            embedBuilder.setDescription("• Usage: `j!rps <rock/paper/scissors>`");
            embedBuilder.setFooter(Variables.embedFooter);
            embedBuilder.setColor(new Color(Variables.embedColor));

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        final String text = args.get(0);

        switch (text) {
            case "rock": {
                embedBuilder.setTitle("→ Rock, Paper, Scissors!");
                embedBuilder.setDescription("• I choose paper! ( :newspaper:  )");
                embedBuilder.setFooter(Variables.embedFooter);
                embedBuilder.setColor(new Color(Variables.embedColor));

                channel.sendMessage(embedBuilder.build()).queue();
                break;
            }
            case "paper": {
                embedBuilder.setTitle("→ Rock, Paper, Scissors!");
                embedBuilder.setDescription("• I choose scissors! ( :scissors: )");
                embedBuilder.setFooter(Variables.embedFooter);
                embedBuilder.setColor(new Color(Variables.embedColor));

                channel.sendMessage(embedBuilder.build()).queue();
                break;
            }
            case "scissors": {
                embedBuilder.setTitle("→ Rock, Paper, Scissors!");
                embedBuilder.setDescription("• I choose rock! ( :rock: )");
                embedBuilder.setFooter(Variables.embedFooter);
                embedBuilder.setColor(new Color(Variables.embedColor));

                channel.sendMessage(embedBuilder.build()).queue();
                break;
            }
            default: {
                embedBuilder.setTitle("→ Incorrect Arguments");
                embedBuilder.setDescription("• Please select rock, paper, or scissors!: `j!rps <rock/paper/scissors>`");
                embedBuilder.setFooter(Variables.embedFooter);
                embedBuilder.setColor(new Color(Variables.embedColor));

                channel.sendMessage(embedBuilder.build()).queue();
                break;
            }
        }
    }

    @Override
    public String getName() {
        return "rps";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• The bot will play rock, paper, scissors with you!" +
                                    "\nUsage: `j!rps <rock/paper/scissors>`");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }
}
