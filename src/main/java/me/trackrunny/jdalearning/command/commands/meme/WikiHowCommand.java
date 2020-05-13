package me.trackrunny.jdalearning.command.commands.meme;

import me.trackrunny.jdalearning.Config;
import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.explodingbush.ksoftapi.KSoftAPI;

import java.awt.*;
import java.util.List;

public class WikiHowCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel textChannel = ctx.getChannel();
        final KSoftAPI kSoftAPI = new KSoftAPI(Config.get("KSOFT_TOKEN"));

        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(String.format("→ %s", kSoftAPI.getRandomWikihow().execute().getTitle()));
        embedBuilder.setImage(kSoftAPI.getRandomWikihow().execute().getImage());
        embedBuilder.setColor(Variables.embedColor);
        embedBuilder.setFooter(Variables.embedFooter);

        textChannel.sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "wikihow";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Displays a random Wikihow image");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }
}
