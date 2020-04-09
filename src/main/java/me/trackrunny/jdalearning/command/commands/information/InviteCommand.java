package me.trackrunny.jdalearning.command.commands.information;

import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.List;

public class InviteCommand implements ICommand {
    private static final String invite = "(http://bit.ly/2Zm5XyP)";

    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final Message message = ctx.getMessage();
        final Emote emoji = ctx.getJDA().getEmoteById("648198008076238862");
        final Member member = ctx.getMember();

        message.addReaction(emoji).complete();

        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Invite Me To Your Server!");
        embedBuilder.setDescription(String.format("• [**Click Here**]%s", invite));
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        member.getUser().openPrivateChannel().queue((channel) -> {
            channel.sendMessage(embedBuilder.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public MessageEmbed getHelp() {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("→ Command Usage");
        embedBuilder.setDescription("• Generates a invite link to invite me.");
        embedBuilder.setFooter(Variables.embedFooter);
        embedBuilder.setColor(new Color(Variables.embedColor));

        return embedBuilder.build();
    }
}
