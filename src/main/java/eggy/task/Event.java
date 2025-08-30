package eggy.task;

public class Event extends Task {
    String fromTime;
    String toTime;

    public Event(String input) {
        super("");
        parseEvent(input);
    }

    public String getFromTime() {
        return this.fromTime;
    }

    public String getToTime() {
        return this.toTime;
    }

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

    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.fromTime, this.toTime);
    }
}
