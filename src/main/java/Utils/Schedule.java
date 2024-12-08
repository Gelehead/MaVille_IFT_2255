package Utils;

import UI_UX.Speaker;
import java.util.Random;

public class Schedule {

    private String[][] schedule = new String[7][15];

    public Schedule() {
        initializeSchedule();
    }

    private void initializeSchedule() {
        for (int i = 0; i < schedule.length; i++) {
            for (int j = 0; j < schedule[i].length; j++) {
                schedule[i][j] = "---"; 
            }
        }
    }

    public void generateMockSchedule() {
        Random random = new Random();
        for (int i = 0; i < schedule.length; i++) { 
            for (int j = 0; j < schedule[i].length; j++) { 
                if (random.nextBoolean()) { 
                    schedule[i][j] = "###"; 
                } else {
                    schedule[i][j] = "---"; 
                }
            }
        }
    }

    // Run the command-line interface
    public void runCLI() {
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        String[] times = {"8h30", "9h30", "10h30", "11h30", "12h30", "13h30", "14h30",
                          "15h30", "16h30", "17h30", "18h30", "19h30", "20h30", "21h30", "22h30"};

        while (true) {
            System.out.println("\nCurrent Schedule:");
            printSchedule();

            System.out.println("\nEnter the day (Mon, Tue, Wed, Thu, Fri, Sat, Sun) or type 'exit' to finish:");
            String day = Speaker.ask().trim();

            if (day.equalsIgnoreCase("exit")) {
                break;
            }

            int dayIndex = -1;
            for (int i = 0; i < days.length; i++) {
                if (days[i].equalsIgnoreCase(day)) {
                    dayIndex = i;
                    break;
                }
            }

            if (dayIndex == -1) {
                System.out.println("Invalid day. Try again.");
                continue;
            }

            System.out.println("Enter the time slot (e.g., 8h30, 9h30, ..., 22h30):");
            String time = Speaker.ask().trim();

            int timeIndex = -1;
            for (int i = 0; i < times.length; i++) {
                if (times[i].equalsIgnoreCase(time)) {
                    timeIndex = i;
                    break;
                }
            }

            if (timeIndex == -1) {
                System.out.println("Invalid time slot. Try again.");
                continue;
            }

            System.out.println("Mark the time slot as (### for busy, --- for free):");
            String status = Speaker.ask().trim();

            if (!status.equals("###") && !status.equals("---")) {
                System.out.println("Invalid status. Use ### or --- only.");
                continue;
            }

            schedule[dayIndex][timeIndex] = status;
            System.out.println("Schedule updated!");
        }
    }

    public void printSchedule() {
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        String[] times = {"8h30", "9h30", "10h30", "11h30", "12h30", "13h30", "14h30",
                          "15h30", "16h30", "17h30", "18h30", "19h30", "20h30", "21h30", "22h30"};

        System.out.print("        ");
        for (String time : times) {
            System.out.print(time + " | ");
        }
        System.out.println();

        for (int i = 0; i < schedule.length; i++) {
            System.out.print(days[i] + "   ");
            for (int j = 0; j < schedule[i].length; j++) {
                System.out.print(schedule[i][j] + "  | ");
            }
            System.out.println();
        }
    }


    //  pour le serveur frontend 
    // Méthode pour mettre à jour les créneaux horaires avec HH:mm
    public boolean updateSlot(int dayIndex, String startTime, String endTime, String status) {
        String[] times = {"08:30", "09:30", "10:30", "11:30", "12:30", "13:30", 
                          "14:30", "15:30", "16:30", "17:30", "18:30", "19:30", 
                          "20:30", "21:30", "22:30"};
        int startIndex = -1, endIndex = -1;
        for (int i = 0; i < times.length; i++) {
            if (startIndex == -1 && times[i].compareTo(startTime) >= 0) {
                startIndex = i;
            }
            if (times[i].compareTo(endTime) <= 0) {
                endIndex = i;
            }
        }
    
        if (startIndex == -1 || endIndex == -1 || startIndex > endIndex) {
            throw new IllegalArgumentException("L'heure de début doit être avant l'heure de fin.");
        }
    
        for (int i = startIndex; i <= endIndex; i++) {
            schedule[dayIndex][i] = status;
        }
    
        return true;
    }
    
    

    private String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    // getters setters

    public String[][] getSchedule() {
        return schedule;
    }
    
    public void setSchedule(String[][] schedule) {
        this.schedule = schedule;
    }
    
    public String[] getDays() {
        return days;
    }
    
    public void setDays(String[] days) {
        this.days = days;
    }

}
