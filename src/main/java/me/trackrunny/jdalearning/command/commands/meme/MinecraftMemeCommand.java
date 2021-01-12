package me.trackrunny.jdalearning.command.commands.meme;

import me.trackrunny.jdalearning.Config;
import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.explodingbush.ksoftapi.KSoftAPI;
import net.explodingbush.ksoftapi.entities.Reddit;
import net.explodingbush.ksoftapi.enums.ImageType;

import java.awt.*;

public class MinecraftMemeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        final EmbedBuilder embedBuilder = new EmbedBuilder();

        final KSoftAPI kSoftAPI = new KSoftAPI(Config.get("KSOFT_TOKEN"));
        final Reddit reddit = kSoftAPI.getRedditImage(ImageType.RANDOM_REDDIT).setSubreddit("MinecraftMemes").execute();

        embedBuilder.setTitle("→ " + reddit.getTitle());
        embedBuilder.setImage(reddit.getImageUrl());
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        channel.sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "minecraftmeme";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Sends out a Minecraft meme!");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }
}
