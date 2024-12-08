package metrics;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import Instances.Project.Progress;

//TODO: really not optimized, java already has a date class and 
// the whole class is centered around it, re do the whole class

/**
 * La classe {@code Date} représente une date et une heure.
 * Elle fournit des méthodes pour valider, formater et comparer des dates.
 */
public class Date {
    int year, month, day, hour, minute, second;

    /**
     * Constructeur pour créer une date avec année, mois et jour.
     * 
     * @param year  l'année de la date
     * @param month le mois de la date (1-12)
     * @param day   le jour de la date (1-31)
     * @throws IllegalArgumentException si les valeurs de date sont invalides
     */
    public Date(int year, int month, int day) {
        validateDate(year, month, day, 0, 0, 0);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Constructeur pour créer une date avec année, mois, jour, heure, minute et seconde.
     * 
     * @param year   l'année de la date
     * @param month  le mois de la date (1-12)
     * @param day    le jour de la date (1-31)
     * @param hour   l'heure de la date (0-23)
     * @param minute la minute de la date (0-59)
     * @param second la seconde de la date (0-59)
     * @throws IllegalArgumentException si les valeurs de date/heure sont invalides
     */
    public Date(int year, int month, int day, int hour, int minute, int second) {
        validateDate(year, month, day, hour, minute, second);
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    /**
     * Valide les valeurs de date et d'heure.
     * 
     * @param year   l'année de la date
     * @param month  le mois de la date (1-12)
     * @param day    le jour de la date (1-31)
     * @param hour   l'heure de la date (0-23)
     * @param minute la minute de la date (0-59)
     * @param second la seconde de la date (0-59)
     * @throws IllegalArgumentException si les valeurs de date/heure sont invalides
     */
    private static void validateDate(int year, int month, int day, int hour, int minute, int second) {
        if (month < 1 || month > 12 || day < 1 || day > 31 || hour < 0 || hour > 23 || minute < 0 || minute > 59 || second < 0 || second > 59) {
            throw new IllegalArgumentException("Invalid date/time values");
        }
        // Vérification pour février
        if (month == 2) {
            if ((year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))) { // Bissextile
                if (day > 29) {
                    throw new IllegalArgumentException("Invalid date/time values");
                }
            } else { // Non bissextile
                if (day > 28) {
                    throw new IllegalArgumentException("Invalid date/time values");
                }
            }
        }
    }

    /**
     * Formate une chaîne de caractères en objet {@code Date}.
     * 
     * @param s la chaîne de caractères à formater
     * @return l'objet {@code Date} correspondant
     * @throws IllegalArgumentException si le format de la chaîne est invalide
     */
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

    /**
     * Calcule l'état d'avancement d'un projet en fonction des dates de début et de fin.
     * 
     * @param s la date de début du projet
     * @param e la date de fin du projet
     * @return l'état d'avancement du projet
     */
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

    /**
     * Vérifie si une date {@code d} est antérieure à une autre date {@code now}.
     * 
     * @param d   la première date
     * @param now la deuxième date
     * @return {@code true} si {@code d} est antérieure à {@code now}, sinon {@code false}
     */
    public static boolean before(Date d, Date now) {
        return toLocalDateTime(d).isBefore(toLocalDateTime(now));
    }

    /**
     * Vérifie si une date {@code d} est postérieure à une autre date {@code now}.
     * 
     * @param d   la première date
     * @param now la deuxième date
     * @return {@code true} si {@code d} est postérieure à {@code now}, sinon {@code false}
     */
    public static boolean after(Date d, Date now) {
        return toLocalDateTime(d).isAfter(toLocalDateTime(now));
    }

    /**
     * Convertit un objet {@code Date} en {@code LocalDateTime}.
     * 
     * @param d l'objet {@code Date} à convertir
     * @return l'objet {@code LocalDateTime} correspondant
     */
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

    /**
     * Retourne un emploi du temps hebdomadaire sous forme de chaîne de caractères.
     * 
     * @return l'emploi du temps hebdomadaire
     */
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

    /**
     * Analyse une chaîne de caractères au format JJMMAAAA HHMM pour créer un objet {@code Date}.
     * 
     * @param input la chaîne de caractères à analyser
     * @return l'objet {@code Date} correspondant
     * @throws IllegalArgumentException si le format de la chaîne est invalide
     */
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

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet {@code Date}.
     * 
     * @return la représentation sous forme de chaîne de caractères de l'objet {@code Date}
     */
    @Override
    public String toString() {
        return String.format("%04d-%02d-%02dT%02d:%02d:%02d", year, month, day, hour, minute, second);
    }
    

    // getters

    /**
     * Retourne l'année de la date.
     * 
     * @return l'année de la date
     */
    public int getYear() { return year; }

    /**
     * Retourne le mois de la date.
     * 
     * @return le mois de la date
     */
    public int getMonth() { return month; }

    /**
     * Retourne le jour de la date.
     * 
     * @return le jour de la date
     */
    public int getDay() { return day; }

    /**
     * Retourne l'heure de la date.
     * 
     * @return l'heure de la date
     */
    public int getHour() { return hour; }

    /**
     * Retourne la minute de la date.
     * 
     * @return la minute de la date
     */
    public int getMinute() { return minute; }

    /**
     * Retourne la seconde de la date.
     * 
     * @return la seconde de la date
     */
    public int getSecond() { return second; }
}
