public class DeadlineTask extends Task {
    String deadline;

    public DeadlineTask(String input) {
        super("");
        int byIndex = input.indexOf("/by");
        String command = "deadline";
        this.description = input.substring(command.length(), byIndex).trim();
        this.deadline = input.substring(byIndex + "/by".length()).trim();
    }

    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.deadline);
    }

}
