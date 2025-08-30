package eggy.task;
public class Task {
    public String description;
    public boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public String getDescription() {
        return description;
    }
    
    public void changeMark() {
        isDone = !isDone;
    }

    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), description);
    }

}