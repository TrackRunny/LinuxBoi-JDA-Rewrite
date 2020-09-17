package me.trackrunny.jdalearning.command.commands.image;

import me.duncte123.botcommons.web.WebUtils;
import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class TrumpTweet implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        final EmbedBuilder embedBuilder = new EmbedBuilder();

        if (args.isEmpty()) {
            embedBuilder.setTitle("→ Missing Arguments");
            embedBuilder.setDescription("• Usage: `j!trumptweet <text>`");
            embedBuilder.setFooter(Variables.embedFooter);
            embedBuilder.setColor(Variables.embedColor);

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        final String text = args.get(0);

        WebUtils.ins.getJSONObject(String.format("https://nekobot.xyz/api/imagegen?type=trumptweet&text=%s", text)).async((json) -> {
            if (!json.get("success").asBoolean()) {
                channel.sendMessage("Something went wrong while trying to make a tweet").queue();
                return;
            }

            final String image = json.get("message").asText();

            embedBuilder.setTitle("→ New TrumpTweet");
            embedBuilder.setImage(image);
            embedBuilder.setColor(Variables.embedColor);
            embedBuilder.setFooter(Variables.embedFooter);

            channel.sendMessage(embedBuilder.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "trumptweet";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Sends a trump tweet image.\nUsage: `j!trumptweet <text>`");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }
}
