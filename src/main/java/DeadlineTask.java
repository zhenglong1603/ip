public class DeadlineTask extends Task{
    String deadline;
    public DeadlineTask(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    public String getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }
}