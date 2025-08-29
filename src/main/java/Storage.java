import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path dataFile;

    public Storage(String filePath) {
        dataFile = Paths.get(filePath);
    }

    public void saveTasksToFile(TaskList list) {
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
                            .append(dt.description).append(" | ").append(dt.deadline);
                } else if (t instanceof Event) {
                    Event ev = (Event) t;
                    sb.append("E | ").append(ev.isDone ? "1" : "0").append(" | ")
                            .append(ev.description).append(" | ").append(ev.fromTime).append(" | ").append(ev.toTime);
                }
                lines.add(sb.toString());
            }
            Files.write(dataFile, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed to save tasks: " + e.getMessage());
        }
    }

    public TaskList loadTasksFromFile() {
        TaskList list = new TaskList(new ArrayList<Task>());
        try {
            Path dataDir = Paths.get(".", "data");
            Path dataFile = dataDir.resolve("eggy.txt");

            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
            if (Files.exists(dataFile)) {
                List<String> lines = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
                for (String line : lines) {
                    if (line.trim().isEmpty())
                        continue;

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

    public void save(ArrayList<Task> tasks) {
        // similar to your saveTasksToFile logic but takes ArrayList<Task>
    }
}
