package eggy.task;

/**
 * Represents a ToDo task which extends the generic Task class.
 * A ToDo includes only a description without any date/time.
 */
public class ToDo extends Task {
    /**
     * Constructs a ToDo by parsing the input string to extract the description.
     * 
     * @param input The input string containing the task type and description in
     *              the format: "todo <description>"
     */
    public ToDo(String input) {
        super("");
        String command = "todo";
        try {
            this.description = input.substring(command.length()).trim();
        } catch (Exception e) {
            String line = "____________________________________________________________";
            System.out.println(line + "\nOOPS!!! I'm sorry, but I don't know what that means :-(\n" + line);
        }
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
