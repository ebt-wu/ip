public class ToDo extends Task {
    public ToDo(String input) {
        super("");
        String command = "todo";
        this.description = input.substring(command.length()).trim();
    }

    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
