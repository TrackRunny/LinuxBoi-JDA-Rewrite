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

import me.trackrunny.jdalearning.command.CommandContext;
import me.trackrunny.jdalearning.command.ICommand;
import me.trackrunny.jdalearning.command.commands.fun.*;
import me.trackrunny.jdalearning.command.commands.image.CaptchaCommand;
import me.trackrunny.jdalearning.command.commands.image.ChangeMyMindCommand;
import me.trackrunny.jdalearning.command.commands.image.TrumpTweet;
import me.trackrunny.jdalearning.command.commands.image.TweetCommand;
import me.trackrunny.jdalearning.command.commands.information.*;
import me.trackrunny.jdalearning.command.commands.meme.*;
import me.trackrunny.jdalearning.command.commands.moderation.BanCommand;
import me.trackrunny.jdalearning.command.commands.moderation.ChangePrefix;
import me.trackrunny.jdalearning.command.commands.moderation.KickCommand;
import me.trackrunny.jdalearning.command.commands.utility.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new HelpCommand(this));
        addCommand(new AvatarCommand());
        addCommand(new UptimeCommand());
        addCommand(new PingCommand());
        addCommand(new LinuxMemeCommand());
        addCommand(new MemeCommand());
        addCommand(new KickCommand());
        addCommand(new BanCommand());
        addCommand(new HasteBinCommand());
        addCommand(new IpInfoCommand());
        addCommand(new MenuPasteCommand());
        addCommand(new InviteCommand());
        addCommand(new ChangePrefix());
        addCommand(new MathCommand());
        addCommand(new ChuckNorrisCommand());
        addCommand(new WikiHowCommand());
        addCommand(new CowsayCommand());
        addCommand(new BitcoinCommand());
        addCommand(new AdviceCommand());
        addCommand(new JokeCommand());
        addCommand(new WindowsMemeCommand());
        addCommand(new FahrenheitToCelsiusCommand());
        addCommand(new CelsiusToFahrenheitCommand());
        addCommand(new PasswordCommand());
        addCommand(new DankMemeCommand());
        addCommand(new DogFactCommand());
        addCommand(new WeatherCommand());
        addCommand(new TweetCommand());
        addCommand(new TrumpTweet());
        addCommand(new EdgyMemeCommand());
        addCommand(new ChangeMyMindCommand());
        addCommand(new ProgrammerMemeCommand());
        addCommand(new KoalaFactCommand());
        addCommand(new CaptchaCommand());
        addCommand(new McpeCommand());
        addCommand(new TTSCommand());
        addCommand(new RockPaperScissorsCommand());
        addCommand(new AppleMemeCommand());
        addCommand(new MinecraftMemeCommand());
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present");
        }

        commands.add(cmd);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    void handle(GuildMessageReceivedEvent event, String prefix) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }

}