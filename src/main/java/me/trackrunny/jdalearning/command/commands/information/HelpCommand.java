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

package me.trackrunny.jdalearning.command.commands.information;

import me.trackrunny.jdalearning.CommandManager;
import me.trackrunny.jdalearning.Config;
import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();
        final EmbedBuilder embedBuilder = new EmbedBuilder();

        if (args.isEmpty()) {
            final StringBuilder builder = new StringBuilder();

            embedBuilder.setTitle("→ List Of Commands!\n");
            embedBuilder.setFooter(Variables.embedFooter);
            embedBuilder.setColor(new Color(Variables.embedColor));

            manager.getCommands().stream().map(ICommand::getName).forEach(
                    (it) -> embedBuilder.setDescription(builder.append('`').append(Config.get("prefix")).append(it).append("`\n"))
            );

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if (command == null) {
            embedBuilder.setTitle("→ Search Error");
            embedBuilder.setDescription(String.format("• No command found for %s", search));
            embedBuilder.setFooter(Variables.embedFooter);
            embedBuilder.setColor(new Color(Variables.embedColor));

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        channel.sendMessage(command.getHelp()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Shows help for each command " +
                                    "Example: `j!help <command>`");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("help", "cmds", "commandlist");
    }
}