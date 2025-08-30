package eggy.task;
public class ToDo extends Task {
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

    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
