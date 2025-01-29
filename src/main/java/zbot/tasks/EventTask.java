package zbot.tasks;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventTask extends Task {

    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");

    LocalDate fromDate;
    LocalDate toDate;
    public EventTask(String description, String fromDate, String toDate) {
        super(description);
        this.fromDate = LocalDate.parse(fromDate, inputFormatter);
        this.toDate = LocalDate.parse(toDate, inputFormatter);
    }

    public String getFromDate() {
        return this.fromDate.format(inputFormatter);
    }

    public String getToDate() {
        return this.toDate.format(inputFormatter);
    }

    public String fromDateString() {
        return this.fromDate.format(outputFormatter);
    }

    public String toDateString() {
        return this.toDate.format(outputFormatter);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromDateString() + " to: " +  toDateString() + ")";
    }
}