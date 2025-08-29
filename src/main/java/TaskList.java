import java.util.ArrayList;
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task t) { 
        tasks.add(t); 
    }

    public Task get(int i) { 
        return tasks.get(i); 
    }

    public int size() { 
        return tasks.size(); 
    }

    public Task remove(int i) { 
        return tasks.remove(i); 
    }

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
