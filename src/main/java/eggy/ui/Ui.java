package eggy.ui;

import java.util.Scanner;
import java.util.List;

import eggy.save.Storage;
import eggy.task.Task;
import eggy.TaskList;


/**
 * Handles user interaction including reading input commands and displaying output messages.
 * Manages the main input loop and commands processing for the Eggy chatbot application.
 */
public class Ui {
    /**
     * The TaskList maintained by the UI.
    */
    private TaskList list;

    /**
     * Scanner for reading user input from the console.
    */
    private final Scanner sc = new Scanner(System.in);

    /**
     * Storage instance for saving and loading tasks.
    */
    private Storage storage;

    /**
     * Holds the current input line from the user.
    */
    private String current = "";

    /**
     * Constructs the Ui with a TaskList and Storage instance.
     * 
     * @param list The TaskList to operate on.
     * @param storage The Storage instance to use for persistent saving/loading.
     */
    public Ui(TaskList list, Storage storage) {
        this.list = list;
        this.storage = storage;
    }

    /**
     * Reads a single line of command input from the user.
     * 
     * @return The user input string.
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Displays a message to the user.
     * 
     * @param message The message to display.
     */
    public void show(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message to the user.
     * 
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Displays a horizontal separator line.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a welcome message when the program starts.
     */
    public void showWelcome() {
        showLine();
        show("Hello! I'm EGGY\nWhat can I do for you?");
        showLine();
    }

    /**
     * Displays a goodbye message when the program ends.
     */
    public void showGoodbye() {
        show("Bye. Hope to see you again soon!");
    }
    
    /**
     * Returns a formatted string listing all tasks in the provided list.
     * Each task is numbered and displayed with its string representation.
     * 
     * @param tasks The list of tasks to format.
     * @return A string of all tasks formatted for display.
     */
    public String printTaskinList(List<Task> tasks) {
        String result = "";
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            result = result + String.format("%d. ", i + 1) + task + "\n";
        }
        return result;
    }
    /**
     * Main loop to handle user input commands continuously until "bye" is entered.
     * Supports commands such as "list", "mark", "unmark", "todo", "deadline", "event", and "delete".
     * Saves the task list to storage after each successful command.
     * 
     * @throws Exception if a command is unrecognized or input is invalid.
     */
    public void stringHandler() throws Exception {
        String line = "____________________________________________________________";
        String standard = "Got it. I've added this task:\n";
        String remove = "Noted. I've removed this task:\n";
        int count = list.size();
        while (!current.equals("bye")) {
            current = sc.nextLine();
            showLine();
            try {
                if (current.equals("list")) {
                    System.out.println(getStringInList() + String.format("%s", line));
                } else if (current.startsWith("mark ")) {
                    System.out.println("\n" + line);
                    System.out.println("Nice! I've marked this task as done:\n");
                    list.handleMarkUnmark(current);
                    System.out.println("\n" + line);
                } else if (current.startsWith("unmark ")) {
                    System.out.println("\n" + line);
                    System.out.println("OK, I've marked this task as not done yet:\n");
                    list.handleMarkUnmark(current);
                    System.out.println("\n" + line);
                } else if (current.startsWith("todo ")) {
                    count++;
                    String command = "todo";
                    Task re = list.append(current, command);
                    System.out.println(String.format("\n%s\n%s\n    %s\nNow you have %s tasks in the list\n%s\n", line,
                            standard, re.toString(), count, line));

                } else if (current.startsWith("deadline ")) {
                    count++;
                    String command = "deadline";
                    Task re = list.append(current, command);
                    System.out.println(String.format("\n%s\n%s\n    %s\nNow you have %s tasks in the list\n%s\n", line,
                            standard, re.toString(), count, line));
                } else if (current.startsWith("event ")) {
                    count++;
                    String command = "event";
                    Task re = list.append(current, command);
                    System.out.println(String.format("\n%s\n%s\n    %s\nNow you have %s tasks in the list\n%s\n", line,
                            standard, re.toString(), count, line));
                } else if (current.startsWith("delete ")) {
                    count--;
                    String[] parts = current.split(" ");
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task removed = list.remove(index);
                    System.out.println(String.format("\n%s\n%s\n    %s\nNow you have %s tasks in the list\n%s\n", line,
                            remove, removed.toString(), count, line));
                } else if (current.startsWith("find ")) {
                    int index = 5;
                    String keyword = current.substring(index);
                    List<Task> l = list.findTasks(keyword);
                    System.out.println(
                            String.format("\n%s\n    Here are the matching tasks for your list:\n%s\n%s\n", line,
                                    printTaskinList(l), line));
                } else {
                    throw new Exception(line + "\nOOPS!!! I'm sorry, but I don't know what that means :-(\n" + line);
                }
                storage.saveTasksToFile(list);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Returns a formatted string listing all tasks currently in the list.
     * Each task is numbered and displayed with its string representation.
     * 
     * @return A string of all tasks formatted for display.
     */
    public String getStringInList() {
        String result = "Here are the tasks in your list:\n";
        for (int i = 0; i < list.size(); i++) {
            Task task = list.get(i);
            result = result + String.format("%d. ", i + 1) + task + "\n";
        }
        return result;
    }

    /**
     * Returns a string wrapped with horizontal lines above and below for formatting.
     * 
     * @param str The string to format.
     * @return The formatted string.
     */
    public String formattedString(String str) {
        String line = "____________________________________________________________";
        return String.format("%s\n%s\n%s\n", line, str, line);
    }
}
