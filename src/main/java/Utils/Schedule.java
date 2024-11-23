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
}
