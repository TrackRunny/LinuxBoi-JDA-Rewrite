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

package me.trackrunny.jdalearning.utils;

import java.util.concurrent.TimeUnit;

public class Time {
    public static String formatTime(Long time) {
        TimeUnit u = TimeUnit.MILLISECONDS;

        long days = u.toDays(time) % 7;
        long hours = u.toHours(time) % 24;
        long minutes = u.toMinutes(time) % 60;
        long seconds = u.toSeconds(time) % 60;

        String day = "";
        String hour = "";
        String minute = "";
        String second = "";

        if (days >= 0) {
            day = String.format("Days: `%2d` | ", days);
        }

        if (hours >= 0) {
            hour = String.format("Hours: `%2d` | ", hours);
        }

        if (minutes >= 0) {
            minute = String.format("Minutes: `%2d` | ", minutes);
        }

        if (seconds >= 0) {
            second = String.format("Seconds: `%2d` ", seconds);
        }

        return day + hour + minute + second + " ";
    }
}
