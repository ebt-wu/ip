import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardOpenOption;

public class Eggy {
    public static int count = 0;
    public static Task[] list = new Task[100];
    public static String current = "";
    private static final Path DATA_DIR = Paths.get(".", "data");
    private static final Path DATA_FILE = DATA_DIR.resolve("eggy.txt");

    public static Task handleMarkUnmark(String command) {
        String[] parts = command.split(" ");
        int index;
        index = Integer.parseInt(parts[1]) - 1;
        Task task = list[index];
        task.changeMark();
        System.out.println("    " + task);
        return task;
    }

    public static String formattedString(String str) {
        String line = "____________________________________________________________";
        return String.format("%s\n%s\n%s\n", line, str, line);
    }

    public static Task deleteTask(int index) {
        if (index < 0 || index >= count) {
            System.out.println("Invalid index for deletion.");
            return null;
        }

        Task removedTask = list[index];

        for (int i = index; i < count - 1; i++) {
            list[i] = list[i + 1];
        }

        list[count - 1] = null;

        count--;

        return removedTask;
    }

    public static void toString(Scanner sc) throws Exception {
        String line = "____________________________________________________________";
        String standard = "Got it. I've added this task:\n";
        String remove = "Noted. I've removed this task:\n";
        while (!current.equals("bye")) {
            current = sc.nextLine();
            try {
                if (current.equals("list")) {
                    System.out.println(getStringInList(list) + String.format("%s", line));
                } else if (current.startsWith("mark ")) {
                    System.out.println("\n" + line);
                    System.out.println("Nice! I've marked this task as done:\n");
                    handleMarkUnmark(current);
                    System.out.println("\n" + line);
                } else if (current.startsWith("unmark ")) {
                    System.out.println("\n" + line);
                    System.out.println("OK, I've marked this task as not done yet:\n");
                    handleMarkUnmark(current);
                    System.out.println("\n" + line);
                } else if (current.startsWith("todo ")) {
                    String command = "todo";
                    Task re = append(current, command);
                    System.out.println(String.format("\n%s\n%s\n    %s\nNow you have %s tasks in the list\n%s\n", line,
                            standard, re.toString(), count, line));

                } else if (current.startsWith("deadline ")) {
                    String command = "deadline";
                    Task re = append(current, command);
                    System.out.println(String.format("\n%s\n%s\n    %s\nNow you have %s tasks in the list\n%s\n", line,
                            standard, re.toString(), count, line));
                } else if (current.startsWith("event ")) {
                    String command = "event";
                    Task re = append(current, command);
                    System.out.println(String.format("\n%s\n%s\n    %s\nNow you have %s tasks in the list\n%s\n", line,
                            standard, re.toString(), count, line));
                } else if (current.startsWith("delete ")) {
                    String[] parts = current.split(" ");
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task removed = deleteTask(index);
                    System.out.println(String.format("\n%s\n%s\n    %s\nNow you have %s tasks in the list\n%s\n", line,
                            remove, removed.toString(), count, line));
                } else {
                    throw new Exception(line + "\nOOPS!!! I'm sorry, but I don't know what that means :-(\n" + line);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Task append(String newElement, String command) throws Exception {
        String line = "____________________________________________________________";
        Task task;
        if (count < list.length) {
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

            list[count] = task;
            count++;
            saveTasksToFile();
            return task;
        } else {
            System.out.println("Array is full, cannot append new element.");
            return new Task("");
        }
    }

    public static String getStringInList(Task[] list) {
        String result = "Here are the tasks in your list:\n";
        for (int i = 0; i < count; i++) {
            Task task = list[i];
            result = result + String.format("%d. ", i + 1) + task + "\n";
        }
        return result;
    }

    public static void loadTasksFromFile() {
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
                        list[count++] = t;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load tasks: " + e.getMessage());
        }
    }

    public static void saveTasksToFile() {
        try {
            Path dataDir = Paths.get(".", "data");
            Path dataFile = dataDir.resolve("eggy.txt");

            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
            List<String> lines = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                Task t = list[i];
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

    public static void main(String[] args) {
        loadTasksFromFile();
        Scanner sc = new Scanner(System.in);
        String line = "____________________________________________________________";
        String formatted_string = String.format(
                "%s\nHello! I'm EGGY\nWhat can I do for you?\n%s\n", line, line);
        System.out.println(formatted_string);
        try {
            toString(sc);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Bye. Hope to see you again soon!");
        sc.close();
    }

}
