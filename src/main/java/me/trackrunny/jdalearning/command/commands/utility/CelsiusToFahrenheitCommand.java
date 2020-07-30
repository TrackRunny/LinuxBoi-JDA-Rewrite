package me.trackrunny.jdalearning.command.commands.utility;

import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class CelsiusToFahrenheitCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        final EmbedBuilder embedBuilder = new EmbedBuilder();

        if (args.isEmpty()) {
            embedBuilder.setTitle("→ Missing Arguments");
            embedBuilder.setDescription("• Usage: `j!celsius <temperature>`");
            embedBuilder.setFooter(Variables.embedFooter);
            embedBuilder.setColor(new Color(Variables.embedColor));

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        final String celsius = args.get(0);
        final int fahrenheit = (Integer.parseInt(celsius) * 9 / 5) + 32;

        embedBuilder.setTitle("→ Celsius To Fahrenheit");
        embedBuilder.setDescription(String.format("• Fahrenheit temperature: `%s`", fahrenheit));
        embedBuilder.setColor(Variables.embedColor);
        embedBuilder.setFooter(Variables.embedFooter);

        channel.sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "celsius";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Converts celsius to fahrenheit.\nUsage: `j!celsius <temperature>`");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }
}
