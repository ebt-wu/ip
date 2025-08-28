import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineTask extends Task {
    protected LocalDateTime deadline;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public DeadlineTask(String input) {
        super("");
        parseDeadline(input);
    }

    private void parseDeadline(String input) {
        try {
            int byIndex = input.indexOf("/by");
            String command = "deadline";
            this.description = input.substring(command.length(), byIndex).trim();

            String dateString = input.substring(byIndex + 3).trim();
            this.deadline = LocalDateTime.parse(dateString, INPUT_FORMAT);
        } catch (DateTimeParseException | StringIndexOutOfBoundsException e) {
            System.err.println("Error parsing deadline date/time: " + e.getMessage());
            this.deadline = null;
        }
    }

    @Override
    public String toString() {
        String deadlineStr = (deadline == null) ? "Invalid date" : deadline.format(OUTPUT_FORMAT);
        return String.format("[D]%s (by: %s)", super.toString(), deadlineStr);
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }
}
