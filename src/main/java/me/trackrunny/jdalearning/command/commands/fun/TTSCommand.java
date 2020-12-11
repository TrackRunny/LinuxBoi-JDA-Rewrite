package me.trackrunny.jdalearning.command.commands.fun;

import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class TTSCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        final EmbedBuilder embedBuilder =  new EmbedBuilder();

        if (args.isEmpty()) {
            embedBuilder.setTitle("→ Missing Arguments");
            embedBuilder.setDescription("• Usage: `j!tts <message>`");
            embedBuilder.setFooter(Variables.embedFooter);
            embedBuilder.setColor(new Color(Variables.embedColor));

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        final String message = args.get(0);

        channel.sendMessage(message).tts(true).queue();
    }

    @Override
    public String getName() {
        return "tts";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Sends a message from the bot returned via TTS on Discord." +
                "\nUsage: `j!tts <message>`");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(241, 90, 36));

        return embedBuilder.build();
    }
}