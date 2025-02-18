package zbot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import zbot.tasks.DeadlineTask;

class DeadlineTaskTest {

    @Test
    void testValidDeadlineTask() {
        DeadlineTask task = new DeadlineTask("Submit report", "2024-10-15");
        assertEquals("2024-10-15", task.getDeadline());
        assertEquals("Oct 15 2024", task.deadlineString());
    }

    @Test
    void testToString() {
        DeadlineTask task = new DeadlineTask("Finish project", "2024-12-01");
        assertEquals("[D][ ] Finish project (by: Dec 01 2024)", task.toString());
    }
}
