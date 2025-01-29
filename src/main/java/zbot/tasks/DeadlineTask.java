package zbot.tasks;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DeadlineTask extends Task {
    LocalDate deadline;
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");


    public DeadlineTask(String description, String deadline) {
        super(description);
        this.deadline = LocalDate.parse(deadline, inputFormatter);

    }

    public String deadlineString() {
        return this.deadline.format(outputFormatter);
    }

    public String getDeadline() {
        return this.deadline.format(inputFormatter);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + deadlineString() + ")";
    }
}