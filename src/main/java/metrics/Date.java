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
        // Vérification pour février
        if (month == 2) {
            if ((year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))) { //  bissextile
                if (day > 29) {
                    throw new IllegalArgumentException("Invalid date/time values");
                }
            } else { //  non bissextile
                if (day > 28) {
                    throw new IllegalArgumentException("Invalid date/time values");
                }
            }
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

    public static String week_schedule(){
        return """
                   8h30 | 9h30 | 10h30 | 11h30 | 12h30 | 13h30 | 14h30 | 15h30 | 16h30 | 17h30 | 18h30 | 19h30 | 20h30 | 21h30 | 22h30 |
        Mon       ----  | ---- | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- |
        Tue       ----  | ---- | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- |
        Wed       ----  | ---- | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- |
        Thu       ----  | ---- | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- |
        Fri       ----  | ---- | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- |
        Sat       ----  | ---- | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- |
        Sun       ----  | ---- | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- | ----- | ----  | ----  | ----- |
        """;
    }

    // Static method to parse dates in the JJMMAAAA HHMM format
    public static Date parse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Invalid date format. Expected format: JJMMAAAA HHMM");
        }

        try {
            int day = Integer.parseInt(input.substring(0, 2));
            int month = Integer.parseInt(input.substring(2, 4));
            int year = Integer.parseInt(input.substring(4, 8));
            int hour = Integer.parseInt(input.substring(9, 11));
            int minute = Integer.parseInt(input.substring(11, 13));

            return new Date(year, month, day, hour, minute, 0);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric values in date string: " + input, e);
        }
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d-%02dT%02d:%02d:%02d", year, month, day, hour, minute, second);
    }
    
}
