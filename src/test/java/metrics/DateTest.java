package metrics;
import Instances.Project.Progress;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class DateTest {

    @Test

    public void testValidateDate() {
        Date validDate = new Date(2024, 11, 22);
        assertEquals(2024, validDate.year);
        assertEquals(11, validDate.month);
        assertEquals(22, validDate.day);
    }

    @Test
    public void testInvalidMonth() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Date(2024, 13, 22);
        });
    }

    @Test
    public void testInvalidDay() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Date(2024, 11, 32);
        });
    }


    @Test
    public void testBissextileYear() {
        Date validDate = new Date(2024, 2, 29);
        assertEquals(29, validDate.day);
    }


    @Test
    public void testNonBissextileYear() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Date(2023, 2, 29);
        });
    }

    @Test
    public void testFormatInvalidDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            Date.format("Invalid-Date-String");
        });
    }

    @Test
    public void testBefore() {
        Date date1 = new Date(2024, 11, 21);
        Date date2 = new Date(2024, 11, 22);
        assertTrue(Date.before(date1, date2));
        assertFalse(Date.before(date2, date1));
    }

    @Test
    public void testAfter() {
        Date date1 = new Date(2024, 11, 21);
        Date date2 = new Date(2024, 11, 22);
        assertTrue(Date.after(date2, date1));
        assertFalse(Date.after(date1, date2));
    }

    @Test
    public void testProjectProgress() {
        // Projet non commencé
        Date startDateFuture = new Date(2024, 11, 30);
        Date endDateFuture = new Date(2024, 12, 31);
        assertEquals(Progress.NOT_STARTED, Date.projectProgress(startDateFuture, endDateFuture));

        // Projet terminé
        Date startDatePast = new Date(2024, 10, 1);
        Date endDatePast = new Date(2024, 10, 30);
        assertEquals(Progress.FINISHED, Date.projectProgress(startDatePast, endDatePast));

        // Projet en cours
        Date startDateInProgress = new Date(2024, 11, 1);
        Date endDateInProgress = new Date(2024, 12, 1);
        assertEquals(Progress.IN_PROGRESS, Date.projectProgress(startDateInProgress, endDateInProgress));
    }

}