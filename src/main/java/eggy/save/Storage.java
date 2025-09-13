package eggy.save;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import eggy.TaskList;
import eggy.task.DeadlineTask;
import eggy.task.Event;
import eggy.task.Task;
import eggy.task.ToDo;

/**
 * Handles saving and loading of tasks to and from a persistent storage file.
 * Provides methods to save a TaskList to a file and to load tasks from the file
 * into a TaskList.
 */
public class Storage {

    /**
     * Constructs a Storage instance.
     * Currently no setup is required on instantiation.
     */
    public Storage() {

    }

    /**
     * Saves all tasks from the provided TaskList to a file named "eggy.txt" inside
     * a "data" directory.
     * The tasks are serialized in a specific format indicating their type and
     * completion status.
     * If the "data" directory or the data file does not exist, they are created.
     *
     * @param list The TaskList containing tasks to be saved.
     */
    public void saveTasksToFile(TaskList list) {
        assert list != null : "TaskList should not be null";
        try {
            Path dataDir = Paths.get(".", "data");
            Path dataFile = dataDir.resolve("eggy.txt");

            if (!Files.exists(dataFile)) {
                Files.createDirectories(dataDir);
            }
            List<String> lines = new ArrayList<>();
            int count = list.size();
            for (int i = 0; i < count; i++) {
                Task t = list.get(i);
                StringBuilder sb = new StringBuilder();

                if (t instanceof ToDo) {
                    sb.append("T | ").append(t.isDone ? "1" : "0").append(" | ").append(t.description);
                } else if (t instanceof DeadlineTask) {
                    DeadlineTask dt = (DeadlineTask) t;
                    sb.append("D | ").append(dt.isDone ? "1" : "0").append(" | ")
                            .append(dt.description).append(" | ").append(dt.getDeadline());
                } else if (t instanceof Event) {
                    Event ev = (Event) t;
                    sb.append("E | ").append(ev.isDone ? "1" : "0").append(" | ")
                            .append(ev.description).append(" | ").append(ev.getFromTime()).append(" | ")
                            .append(ev.getToTime());
                }
                lines.add(sb.toString());
            }
            Files.write(dataFile, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed to save tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the persistent file "eggy.txt" located in the "data"
     * directory.
     * Reads and parses each line, reconstructing Task objects of appropriate types
     * with their done status.
     * If the directory or file does not exist, they are created or an empty
     * TaskList is returned.
     *
     * @return A TaskList populated with tasks loaded from the file. Returns an
     *         empty list if the file is missing or empty.
     */
    public TaskList loadTasksFromFile() {
        TaskList list = new TaskList(new ArrayList<Task>());
        try {
            Path dataDir = Paths.get(".", "data");
            Path dataFile = dataDir.resolve("eggy.txt");

            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
                assert Files.exists(dataDir);
            }
            if (Files.exists(dataFile)) {
                List<String> lines = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
                for (String line : lines) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    String[] parts = line.split("\\|");
                    String taskType = parts[0].trim();
                    String doneFlag = parts[1].trim();
                    String description = parts[2].trim();

                    Task t = null;
                    if (taskType.equals("T")) {
                        t = new ToDo("todo " + description);
                    } else if (taskType.equals("D")) {
                        String deadline = (parts.length > 3) ? parts[3].trim() : "";
                        t = new DeadlineTask("deadline " + description + " /by " + deadline);
                    } else if (taskType.equals("E")) {
                        String from = (parts.length > 3) ? parts[3].trim() : "";
                        String to = (parts.length > 4) ? parts[4].trim() : "";
                        t = new Event("event " + description + " /from " + from + " /to " + to);
                    } else {
                        // Document assumption: should never reach here with valid data
                        assert false : "Unknown task type: " + taskType;
                    }

                    if (t != null) {
                        if (doneFlag.equals("1")) {
                            t.changeMark();
                        }
                        list.add(t);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load tasks: " + e.getMessage());
        }
        return list;
    }
}
