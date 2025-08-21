import java.util.Scanner;

public class Eggy {
    public static int count = 0;
    public static String[] list = new String[100];
    public static String current = "";

    public static void toString(Scanner sc) {
        String line = "____________________________________________________________";
        while (!current.equals("bye")) {
            current = sc.nextLine();
            if (!current.equals("list")) {
                append(current);
                System.out.println(String.format("\n%s\n%s\n%s", line, current, line));
            } else {
                System.out.println(getStringInList(list) + String.format("%s", line));
            }
        }
    }

    public static void append(String newElement) {
        if (count < list.length) {
            list[count] = newElement;
            count++;
        } else {
            System.out.println("Array is full, cannot append new element.");
        }
    }

    public static String getStringInList(String[] list) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result = result + String.format("%d. ", i + 1) + list[i] + "\n";
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
