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
