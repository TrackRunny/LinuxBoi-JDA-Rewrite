package me.trackrunny.jdalearning.command.commands.fun;

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

public class AdviceCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        final EmbedBuilder embedBuilder = new EmbedBuilder();


        WebUtils.ins.getJSONObject("https://api.adviceslip.com/advice").async((json) -> {
            final JsonNode value = json.get("slip");
            final String advice = value.get("advice").asText();

            embedBuilder.setTitle("→ Good Advice");
            embedBuilder.setDescription(String.format("• Advice: %s", advice));
            embedBuilder.setColor(Variables.embedColor);
            embedBuilder.setFooter(Variables.embedFooter);

            channel.sendMessage(embedBuilder.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "advice";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Displays advice for you.");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }
}
