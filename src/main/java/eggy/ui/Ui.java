package eggy.ui;

import java.util.Scanner;
import java.util.List;

import eggy.save.Storage;
import eggy.task.Task;
import eggy.TaskList;

/**
 * Handles user interaction, reads input commands, and displays output messages.
 * Manages the input loop and command processing for the Eggy chatbot.
 */
public class Ui {
    // Constants for formatting output
    private static final String LINE = "____________________________________________________________";
    private static final String WELCOME_MSG = "Hey there! Welcome to Eggy — your friendly task assistant.\nWhat can I do for you today?";
    private static final String GOODBYE_MSG = "Bye. Hope to see you again soon!";
    private static final String ADD_TASK_MSG = "Got it. I've added this task:\n";
    private static final String REMOVE_TASK_MSG = "Noted. I've removed this task:\n";
    private static final String ERROR_PREFIX = "Error: ";

    private final Scanner scanner = new Scanner(System.in);
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Constructs the Ui.
     * 
     * @param taskList the TaskList to operate on
     * @param storage  the Storage instance for persistent saving/loading
     */
    public Ui(TaskList taskList, Storage storage) {
        this.taskList = taskList;
        this.storage = storage;
    }

    /** Reads a single line of user input. */
    public String readCommand() {
        return scanner.nextLine();
    }

    /** Displays a message to the user. */
    public void show(String message) {
        System.out.println(message);
    }

    /** Displays an error message to the user. */
    public void showError(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    /** Displays a horizontal separator line. */
    public void showLine() {
        System.out.println(LINE);
    }

    /** Displays the welcome message. */
    public void showWelcome() {
        showLine();
        show(WELCOME_MSG);
        showLine();
    }

    /** Displays the goodbye message. */
    public void showGoodbye() {
        show(GOODBYE_MSG);
    }

    /** Returns a formatted string of all tasks in the given list. */
    public String formatTaskList(List<Task> tasks) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            result.append(String.format("%d. %s%n", i + 1, tasks.get(i)));
        }
        return result.toString();
    }

    /** Returns a formatted string of all current tasks. */
    public String getFormattedTaskList() {
        return "Here are the tasks in your list:\n" + formatTaskList(taskList.getAll());
    }

    /** Main command handler loop until "bye" is entered. */
    public void startCommandLoop() {
        showWelcome();
        String input;
        while (!(input = readCommand()).equals("bye")) {
            showLine();
            try {
                String output = handleCommand(input);
                show(output);
            } catch (Exception e) {
                showError(e.getMessage());
            } finally {
                showLine();
            }
            storage.saveTasksToFile(taskList);
        }
        showGoodbye();
    }

    private String getCommandList() {
        return "Try these commands:\n"
                + "     todo <description>\n"
                + "     deadline <description> /by <YYYY-MM-DDTHH:MM>\n"
                + "     event <description> /from <time> /to <time>\n"
                + "     list\n"
                + "     mark <task-number>\n"
                + "     unmark <task-number>\n"
                + "     delete <task-number>\n"
                + "     find <keyword>\n";
    }

    /**
     * Processes a single user command and returns the corresponding message.
     * 
     * @param input the user command
     * @return the display message
     * @throws Exception for invalid commands
     */
    public String handleCommand(String input) throws Exception {
        if (input.equals("list")) {
            return getFormattedTaskList();
        } else if (input.startsWith("mark ") || input.startsWith("unmark ")) {
            boolean isMark = input.startsWith("mark ");
            taskList.handleMarkUnmark(input);
            return isMark
                    ? "Nice! I've marked this task as done:\n" + getFormattedTaskList()
                    : "OK, I've marked this task as not done yet:\n" + getFormattedTaskList();
        } else if (input.startsWith("todo ")) {
            Task added = taskList.append(input, "todo");
            return ADD_TASK_MSG + "    " + added + "\nNow you have " + taskList.size() + " tasks in the list";
        } else if (input.startsWith("deadline ")) {
            Task added = taskList.append(input, "deadline");
            return ADD_TASK_MSG + "    " + added + "\nNow you have " + taskList.size() + " tasks in the list";
        } else if (input.startsWith("event ")) {
            Task added = taskList.append(input, "event");
            return ADD_TASK_MSG + "    " + added + "\nNow you have " + taskList.size() + " tasks in the list";
        } else if (input.startsWith("delete ")) {
            String[] parts = input.split(" ");
            int index = Integer.parseInt(parts[1]) - 1;
            Task removed = taskList.remove(index);
            return REMOVE_TASK_MSG + "    " + removed + "\nNow you have " + taskList.size() + " tasks in the list";
        } else if (input.startsWith("find ")) {
            String keyword = input.substring(5);
            List<Task> found = taskList.findTasks(keyword);
            return "Here are the matching tasks in your list:\n" + formatTaskList(found);
        } else {
            throw new Exception(
                    "I'm sorry, but I don't know what that means :-("
                            + "\n" + getCommandList());
        }

    }

    /**
     * Processes a single user command and returns the corresponding message.
     * This method is designed for integration with GUI applications.
     * 
     * @param input the user command
     * @return the display message
     * @throws Exception for invalid commands
     */
    public String getResponse(String input) throws Exception {
        return handleCommand(input);
    }

    /** Returns a string wrapped between horizontal lines. */
    public String formattedString(String str) {
        return String.format("%s\n%s\n%s\n", LINE, str, LINE);
    }
}
