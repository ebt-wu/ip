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

    public static void toString(Scanner sc) {
        String line = "____________________________________________________________";
        while (!current.equals("bye")) {
            current = sc.nextLine();
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
            } else {
                Task re = append(current);
                System.out.println(String.format("\n%s\nadded: %s\n%s\n", line, re.toString(), line));
            }
        }
    }

    public static Task append(String newElement) {
        if (count < list.length) {
            Task appended = new Task(newElement);
            list[count] = appended;
            count++;
            return appended;
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
        toString(sc);
        System.out.println("Bye. Hope to see you again soon!");
        sc.close();
    }

}
