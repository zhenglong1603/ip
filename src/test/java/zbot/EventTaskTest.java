package zbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import zbot.tasks.EventTask;

class EventTaskTest {

    @Test
    void testValidEventTask() {
        EventTask task = new EventTask("Team Meeting", "2025-02-20", "2025-02-21");

        assertEquals("2025-02-20", task.getFromDate());
        assertEquals("2025-02-21", task.getToDate());
        assertEquals("Feb 20 2025", task.fromDateString());
        assertEquals("Feb 21 2025", task.toDateString());
    }

    @Test
    void testInvalidEventTaskDates() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new EventTask("Conference", "2025-03-10", "2025-03-09");
        });

        assertEquals(
                "Sorry!! The 'to' date cannot be earlier than the 'from' date. Maybe check again?",
                exception.getMessage());
    }

    @Test
    void testToStringFormat() {
        EventTask event = new EventTask("Project Presentation", "2025-04-15", "2025-04-16");
        String expectedOutput = "[E][ ] Project Presentation (from: Apr 15 2025 to: Apr 16 2025)";

        assertEquals(expectedOutput, event.toString());
    }
}
