package me.trackrunny.jdalearning.command.commands.meme;

import me.trackrunny.jdalearning.Bot;
import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.explodingbush.ksoftapi.entities.Reddit;
import net.explodingbush.ksoftapi.enums.ImageType;

import java.awt.*;

public class ProgrammerMemeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        final EmbedBuilder embedBuilder = new EmbedBuilder();

        final Reddit reddit = Bot.kSoftAPI.getRedditImage(ImageType.RANDOM_REDDIT).setSubreddit("ProgrammerHumor").execute();

        embedBuilder.setTitle("→ " + reddit.getTitle());
        embedBuilder.setImage(reddit.getImageUrl());
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        channel.sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "programmermeme";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("→ Programmer Meme Info");
        embedBuilder.setDescription("• Sends a programmer meme from Reddit. ");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(Variables.embedColor);

        return embedBuilder.build();
    }
}
