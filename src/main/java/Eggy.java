import java.util.Scanner;

public class Eggy {
    public static int count = 0;
    public static Task[] list = new Task[100];
    public static String current = "";

    public static Task handleMarkUnmark(String command) {
        String[] parts = command.split(" ");
        int index;
        index = Integer.parseInt(parts[1]) - 1;
        Task task = list[index];
        task.changeMark();
        System.out.println("    " + task);
        return task;
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

    public static void main(String[] args) {
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
