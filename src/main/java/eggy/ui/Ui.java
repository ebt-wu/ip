package eggy.ui;

import java.util.Scanner;
import java.util.List;

import eggy.save.Storage;
import eggy.task.Task;
import eggy.TaskList;

public class Ui {
    private TaskList list;
    private final Scanner sc = new Scanner(System.in);
    private Storage storage;
    private String current = "";

    public Ui(TaskList list, Storage storage) {
        this.list = list;
        this.storage = storage;
    }

    public String readCommand() {
        return sc.nextLine();
    }

    public void show(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showWelcome() {
        showLine();
        show("Hello! I'm EGGY\nWhat can I do for you?");
        showLine();
    }

    public void showGoodbye() {
        show("Bye. Hope to see you again soon!");
    }

    public String printTaskinList(List<Task> tasks) {
        String result = "";
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            result = result + String.format("%d. ", i + 1) + task + "\n";
        }
        return result;
    }

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

    public String getStringInList() {
        String result = "Here are the tasks in your list:\n";
        for (int i = 0; i < list.size(); i++) {
            Task task = list.get(i);
            result = result + String.format("%d. ", i + 1) + task + "\n";
        }
        return result;
    }

    public String formattedString(String str) {
        String line = "____________________________________________________________";
        return String.format("%s\n%s\n%s\n", line, str, line);
    }
}
