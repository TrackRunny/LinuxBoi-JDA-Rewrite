package me.trackrunny.jdalearning.command.commands.information;

import me.duncte123.botcommons.web.WebUtils;
import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class KoalaFactCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        final EmbedBuilder embedBuilder = new EmbedBuilder();

        WebUtils.ins.getJSONObject("https://some-random-api.ml/facts/koala").async((json) -> {
            if (!json.get("fact").isEmpty()) {
                channel.sendMessage("• Something did not go right! Please try again later.\"").queue();
                System.out.println(json);
                return;
            }

            final String fact = json.get("fact").asText();

            embedBuilder.setTitle("→ Koala Fact");
            embedBuilder.setDescription(String.format("• %s", fact));
            embedBuilder.setColor(Variables.embedColor);
            embedBuilder.setFooter(Variables.embedFooter);

            channel.sendMessage(embedBuilder.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "koalafact";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Gets a random koala fact.\nUsage: `j!koalafact`");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }
}
