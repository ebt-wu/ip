package eggy.task;

/**
 * Represents a generic Task with a description and completion status.
 */
public class Task {
    /**
     * The description of the task.
     */
    public String description;
    /**
     * The completion status of the task.
     */
    public boolean isDone;

    /**
     * Constructs a Task with the given description and sets it as not done.
     * 
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is done or not.
     * 
     * @return "X" if done, otherwise a space " ".
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /** Toggles the completion status of the task. */
    public void changeMark() {
        isDone = !isDone;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), description);
    }

}