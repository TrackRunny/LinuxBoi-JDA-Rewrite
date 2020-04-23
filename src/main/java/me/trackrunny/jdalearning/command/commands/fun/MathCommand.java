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

public class MathCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        final EmbedBuilder embedBuilder = new EmbedBuilder();

        WebUtils.ins.getJSONObject("http://numbersapi.com/random/math?json").async((json) -> {
            if (!json.get("found").asBoolean()) {
                channel.sendMessage("• Something did not go right! Please try again later.").queue();
                System.out.println(json);
                return;
            }

            final String fact = json.get("text").asText();
            final String number = json.get("number").asText();

            embedBuilder.setTitle("→ Random Math Fact");
            embedBuilder.setDescription(String.format("• Fact: %s" +
                    "\n• Number: %s", fact, number));
            embedBuilder.setColor(Variables.embedColor);
            embedBuilder.setFooter(Variables.embedFooter);

            channel.sendMessage(embedBuilder.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "math";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Displays a random math fact");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }
}
