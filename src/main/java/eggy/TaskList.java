package eggy;
import java.util.ArrayList;

import eggy.task.DeadlineTask;
import eggy.task.Event;
import eggy.task.Task;
import eggy.task.ToDo;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    /* 
    The actual list of tasks
     */
    private final ArrayList<Task> tasks;

    /**
     * Constructor for an empty TaskList.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds task into the list of tasks.
     * @param t Task to be added
     */
    public void add(Task t) { 
        tasks.add(t); 
    }

    /**
     * Gets the task at index i.
     * @param i Index of the task to be retrieved
     * @return Task at index i
     */
    public Task get(int i) { 
        return tasks.get(i); 
    }

    /**
     * Returns the number of tasks in the list.
     * @return Number of tasks in the list
     */
    public int size() { 
        return tasks.size(); 
    }

    /**
     * Removes the task at index i from the list.
     * @param i Index of the task to be removed
     * @return The removed task
     */
    public Task remove(int i) { 
        return tasks.remove(i); 
    }

    /**
     * Appends a new task to the list based on the command and its details.
     * Supports "deadline", "event", and "todo" commands.
     * Throws an exception if the command is unrecognized or if required details are missing.
     *
     * @param newElement The full command string containing task details.
     * @param command The type of task to be added ("deadline", "event", or "todo").
     * @return The newly added Task.
     * @throws Exception If the command is unrecognized or if required details are missing.
     */
    public Task append(String newElement, String command) throws Exception {
        String line = "____________________________________________________________";
        Task task;

        if (command.equals("deadline")) {
            if (!newElement.contains("/by")) {
                throw new Exception(
                        line + "\nOOPS!!! I'm sorry, please provide a deadline or write in proper format\n" + line);
            }
            task = new DeadlineTask(newElement);
        } else if (command.equals("event")) {
            if (!newElement.contains("/from") || !newElement.contains("/to")) {
                throw new Exception(
                        line + "\nOOPS!!! I'm sorry, please provide both the start and the end timings or write in proper format\n"
                                + line);
            }
            task = new Event(newElement);
        } else if (command.equals("todo")) {
            String description = newElement.substring("todo ".length()).trim();
            if (description.isEmpty()) {
                throw new Exception(line + "\nOOPS!!! The description of a todo cannot be empty.\n" + line);
            }
            task = new ToDo(newElement);
        } else {
            throw new Exception(line + "\nOOPS!!! I'm sorry, but I don't know what that means :-(\n" + line);
        }

        tasks.add(task);
        return task;
    
    }

    /**
     * Marks or unmarks a task as done based on the command.
     * The command should be in the format "mark X" or "unmark X", where X is the
     * 1-based index of the task to be marked/unmarked.
     *
     * @param command The command string indicating the action and task index.
     * @return The Task that was marked or unmarked.
     * @throws NumberFormatException If the index is not a valid integer.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public Task handleMarkUnmark(String command) {
        String[] parts = command.split(" ");
        int index;
        index = Integer.parseInt(parts[1]) - 1;
        Task task = this.get(index);
        task.changeMark();
        System.out.println("    " + task);
        return task;
    }
}
