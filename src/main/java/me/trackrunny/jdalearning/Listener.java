/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * JDALearningBot - Discord bot to learn JDA 4 library                       *
 * Copyright (C) 2019-2020 TrackRunny                                        *
 *                                                                           *
 * This program is free software: you can redistribute it and/or modify      *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * This program is distributed in the hope that it will be useful,           *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with this program. If not, see <https://www.gnu.org/licenses/>.     *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package me.trackrunny.jdalearning;

import me.duncte123.botcommons.BotCommons;
import me.trackrunny.jdalearning.database.SQLiteDataSource;
import me.trackrunny.jdalearning.variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.Presence;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Listener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private final CommandManager manager = new CommandManager();

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        final String bot = event.getJDA().getSelfUser().getAsTag();

        LOGGER.info("---------------JDA-LearningBot-----------------------");
        LOGGER.info("• Bot is online and connected to " + bot);
        LOGGER.info("• Created by " + Variables.owner + " on Discord");
        LOGGER.info("-----------------------------------------------------");
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        User user = event.getAuthor();

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        final long guildId = event.getGuild().getIdLong();
        final TextChannel channel = event.getChannel();
        String prefix = Prefixes.PREFIXES.computeIfAbsent(guildId, this::getPrefix);
        String raw = event.getMessage().getContentRaw();

        if (raw.equalsIgnoreCase(prefix + "shutdown") && user.getId().equals(Config.get("owner_id"))) {
            LOGGER.info("Shutting down...");
            EmbedBuilder embedBuilder =  new EmbedBuilder();
            embedBuilder.setTitle("→ Success");
            embedBuilder.setDescription("• Shutting down now...");
            embedBuilder.setColor(new Color(241, 90, 36));

            event.getChannel().sendMessage(embedBuilder.build()).queue();
            event.getJDA().shutdown();
            BotCommons.shutdown(event.getJDA());

            return;
        }

        if (raw.equalsIgnoreCase(prefix + "status online") && user.getId().equals(Config.get("owner_id"))) {
            LOGGER.info("Changed online status");

            final Presence presence = event.getJDA().getPresence();

            presence.setStatus(OnlineStatus.ONLINE);

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("→ Changed status");
            embedBuilder.setDescription("• Status is now set to `online`");
            embedBuilder.setColor(Variables.embedColor);
            embedBuilder.setFooter(Variables.embedFooter);

            channel.sendMessage(embedBuilder.build()).queue();

            return;
        } else if (raw.equalsIgnoreCase(prefix + "status idle") && user.getId().equals(Config.get("owner_id"))) {
            LOGGER.info("Changed online status");

            final Presence presence = event.getJDA().getPresence();

            presence.setStatus(OnlineStatus.IDLE);

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("→ Changed status");
            embedBuilder.setDescription("• Status is now set to `idle`");
            embedBuilder.setColor(Variables.embedColor);
            embedBuilder.setFooter(Variables.embedFooter);

            channel.sendMessage(embedBuilder.build()).queue();

            return;
        } else if (raw.equalsIgnoreCase(prefix + "status dnd") && user.getId().equals(Config.get("owner_id"))) {
            LOGGER.info("Changed online status");

            final Presence presence = event.getJDA().getPresence();

            presence.setStatus(OnlineStatus.DO_NOT_DISTURB);

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("→ Changed status");
            embedBuilder.setDescription("• Status is now set to `dnd`");
            embedBuilder.setColor(Variables.embedColor);
            embedBuilder.setFooter(Variables.embedFooter);

            channel.sendMessage(embedBuilder.build()).queue();

            return;
        } else if (raw.equalsIgnoreCase(prefix + "status invisible") && user.getId().equals(Config.get("owner_id"))) {
            LOGGER.info("Changed online status");

            final Presence presence = event.getJDA().getPresence();

            presence.setStatus(OnlineStatus.INVISIBLE);

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("→ Changed status");
            embedBuilder.setDescription("• Status is now set to `invisible`");
            embedBuilder.setColor(Variables.embedColor);
            embedBuilder.setFooter(Variables.embedFooter);

            channel.sendMessage(embedBuilder.build()).queue();

            return;
        }

        if (raw.startsWith(prefix)) {
            manager.handle(event, prefix);
        }
    }

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        final EmbedBuilder embedBuilder =  new EmbedBuilder();
        embedBuilder.setTitle("→ Thanks for inviting me!");
        embedBuilder.setDescription("• Please use `l!commands` to see all commands.");
        embedBuilder.setColor(Variables.embedColor);

        event.getGuild().getDefaultChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        LOGGER.info(String.format("Left guild: %s", event.getGuild().getName()));
    }

    private String getPrefix(long guildId) {
        try (final PreparedStatement preparedStatement = SQLiteDataSource
                .getConnection()
                .prepareStatement("SELECT prefix FROM guild_settings WHERE guild_id = ?")) {

            preparedStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("prefix");
                }
            }

            try (final PreparedStatement insertStatement = SQLiteDataSource
                    .getConnection()
                    .prepareStatement("INSERT INTO guild_settings(guild_id) VALUES(?)")) {

                insertStatement.setString(1, String.valueOf(guildId));

                insertStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Config.get("PREFIX");
    }
}
