package eggy.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Deadline task which extends the generic Task class.
 * A DeadlineTask includes a description and a deadline date/time.
 */
public class DeadlineTask extends Task {
    /**
     * Formatter for parsing input date/time in ISO_LOCAL_DATE_TIME format.
     */
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    /**
     * Formatter for outputting date/time in a readable format like "MMM dd yyyy,
     * h:mm a".
     */
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * The deadline date and time of this task.
     */
    protected LocalDateTime deadline;

    /**
     * Constructs a DeadlineTask by parsing the input string to extract the
     * description and deadline.
     *
     * @param input The input string containing the task type, description, and
     *              deadline in the format:
     */
    public DeadlineTask(String input) {
        super("");
        parseDeadline(input);
    }

    /**
     * Parses the input string to set the description and deadline fields.
     * If the date/time cannot be parsed, the deadline is set to null and an error
     * message is printed.
     *
     * @param input The input string to parse
     */
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

    /**
     * Returns a string representation of this DeadlineTask, including its mark
     * status and formatted deadline.
     * If the deadline is invalid or not set, "Invalid date" is shown.
     *
     * @return A string representation of the task
     */
    @Override
    public String toString() {
        String deadlineStr = (deadline == null) ? "Invalid date" : deadline.format(OUTPUT_FORMAT);
        return String.format("[D]%s (by: %s)", super.toString(), deadlineStr);
    }

    /**
     * Retrieves the deadline of this task.
     *
     * @return The LocalDateTime representing the deadline, or null if invalid or
     *         not set.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }
}
