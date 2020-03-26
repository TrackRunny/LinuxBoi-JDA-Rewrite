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

import me.duncte123.botcommons.web.WebUtils;
import me.trackrunny.jdalearning.database.SQLiteDataSource;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

public class Bot {

    public static long timeStart = 0;

    // - Creating constructor
    private Bot() throws LoginException, SQLException {
        WebUtils.setUserAgent("Mozilla/5.0 JDA-LearningBot#6713 / TrackRunny#0001");

        SQLiteDataSource.getConnection();

        new JDABuilder(AccountType.BOT)
                .setToken(Config.get("token"))
                .addEventListeners(new Listener())
                .setActivity(Activity.watching("Gradle 6.1 — \uD83D\uDC18"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .build();
    }

    public static void main(String[] args) throws LoginException, SQLException {
        new Bot();

        timeStart = System.currentTimeMillis();
    }
}
