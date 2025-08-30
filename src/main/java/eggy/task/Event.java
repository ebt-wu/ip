package eggy.task;

/**
 * Represents an Event task which extends the generic Task class.
 * An Event includes a description, start time, and end time.
 */
public class Event extends Task {
    /**
     * The start time of the event.
     */
    String fromTime;
    /**
     * The end time of the event.
     */
    String toTime;

    /**
     * Constructs an Event by parsing the input string to extract the
     * description, start time, and end time.
     * 
     * @param input The input string containing the task type, description, start
     *              time, and end time in the format:
     *              "event <description> /from <start time> /to <end time>"
     */
    public Event(String input) {
        super("");
        parseEvent(input);
    }

    /** Time getters for Event 
     * @return fromTime and toTime
    */
    public String getFromTime() {
        return this.fromTime;
    }

    public String getToTime() {
        return this.toTime;
    }

    /** Parses the input string to set the description, fromTime, and toTime fields.
     * 
     * @param input The input string to parse.
    */
    public void parseEvent(String input) {
        String command = "event";

        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
            System.out.println("Invalid input format.");
            return;
        }
        this.description = input.substring(command.length(), fromIndex).trim();
        fromTime = input.substring(fromIndex + "/from".length(), toIndex).trim();
        toTime = input.substring(toIndex + "/to".length()).trim();
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.fromTime, this.toTime);
    }
}
