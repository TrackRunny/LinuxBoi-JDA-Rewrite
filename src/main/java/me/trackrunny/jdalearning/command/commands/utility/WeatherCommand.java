package me.trackrunny.jdalearning.command.commands.utility;

import me.trackrunny.jdalearning.Bot;
import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.explodingbush.ksoftapi.entities.Weather;
import net.explodingbush.ksoftapi.enums.Units;

import java.awt.*;
import java.util.List;

public class WeatherCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();
        final Weather weather =  Bot.kSoftAPI.getKumo().getWeatherAction().setUnits(Units.US).setLocationQuery(args.get(0)).execute();

        final double celsius = (Double.parseDouble(String.valueOf(weather.getTemperature())) - 32) * 5 / 9;
        final String temperature = String.format("%s℉ — (%s℃)", weather.getTemperature(), Math.round(celsius));
        final String humidity = String.valueOf(weather.getHumidity() * 100);
        final String cloudCoverage = String.valueOf(weather.getCloudCover() * 100);

        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Weather Command");
        embedBuilder.setThumbnail(weather.getIconUrl());
        embedBuilder.addField("• Weather", weather.getSummary(), true);
        embedBuilder.addField("• Temperature", temperature, true);
        embedBuilder.addField("• Humidity", String.format("%s%%", humidity), true);
        embedBuilder.addField("• Wind", String.format("%s MPH", weather.getWindSpeed()), true);
        embedBuilder.addField("• Cloud Coverage", String.format("%s%%", cloudCoverage), true);
        embedBuilder.addField("• Location", String.valueOf(weather.getLocation().getAddress()), true);
        embedBuilder.setColor(Variables.embedColor);
        embedBuilder.setFooter(Variables.embedFooter);

        channel.sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "weather";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Shows the weather for a specific location" +
                                    "\n• Usage: `j!weather <location>`");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }
}
