package metrics;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import Instances.Project.Progress;

//TODO: really not optimized, java already has a date class and 
// the whole class is centered around it, re do the whole class
public class Date {
    int year, month, day, hour, minute, second;

    public Date(int year, int month, int day) {
        validateDate(year, month, day, 0, 0, 0);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date(int year, int month, int day, int hour, int minute, int second) {
        validateDate(year, month, day, hour, minute, second);
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    private static void validateDate(int year, int month, int day, int hour, int minute, int second) {
        if (month < 1 || month > 12 || day < 1 || day > 31 || hour < 0 || hour > 23 || minute < 0 || minute > 59 || second < 0 || second > 59) {
            throw new IllegalArgumentException("Invalid date/time values");
        }
    }

    public static Date format(String s) {
        try {
            Instant instant = Instant.parse(s);
    
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
    
            return new Date(
                zonedDateTime.getYear(),
                zonedDateTime.getMonthValue(),
                zonedDateTime.getDayOfMonth(),
                zonedDateTime.getHour(),
                zonedDateTime.getMinute(),
                zonedDateTime.getSecond()
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + s, e);
        }
    }
    

    public static Progress projectProgress(Date s, Date e) {
        Instant nowInstant = Instant.now();
        ZonedDateTime zonedDateTime = nowInstant.atZone(ZoneId.systemDefault());
        Date now = new Date(
            zonedDateTime.getYear(),
            zonedDateTime.getMonthValue(),
            zonedDateTime.getDayOfMonth(),
            zonedDateTime.getHour(),
            zonedDateTime.getMinute(),
            zonedDateTime.getSecond()
        );

        if (before(now, s)) {
            return Progress.NOT_STARTED;
        }
        if (after(now, e)) {
            return Progress.FINISHED;
        }
        return Progress.IN_PROGRESS;
    }

    public static boolean before(Date d, Date now) {
        return toLocalDateTime(d).isBefore(toLocalDateTime(now));
    }

    public static boolean after(Date d, Date now) {
        return toLocalDateTime(d).isAfter(toLocalDateTime(now));
    }

    private static LocalDateTime toLocalDateTime(Date d) {
        return LocalDateTime.of(
            d.year, 
            d.month, 
            d.day, 
            d.hour, 
            d.minute, 
            d.second
        );
    }
}
