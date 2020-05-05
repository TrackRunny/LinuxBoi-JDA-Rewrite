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

public class ChuckNorrisCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        final EmbedBuilder embedBuilder = new EmbedBuilder();

        WebUtils.ins.getJSONObject("https://api.icndb.com/jokes/random?limitTo=[nerdy]").async((json) -> {
            if (!json.get("type").asText().equals("success")) {
                channel.sendMessage("• Something did not go right! Please try again later.").queue();
                System.out.println(json);
                return;
            }

            final JsonNode value = json.get("value");
            final String joke = value.get("joke").asText();

            embedBuilder.setTitle("→ Chuck Norris Joke");
            embedBuilder.setDescription(String.format("• Joke: %s", joke));
            embedBuilder.setColor(Variables.embedColor);
            embedBuilder.setFooter(Variables.embedFooter);

            channel.sendMessage(embedBuilder.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "chuck-noris";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Displays a random Chuck Noris fact");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }
}
