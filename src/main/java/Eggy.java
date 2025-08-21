import java.util.Scanner;

public class Eggy {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = "____________________________________________________________";
        String formatted_string = String.format(
                "%s\nHello! I'm EGGY\nWhat can I do for you?\n%s\n", line, line);
        System.out.println(formatted_string);
        String current = "";
        while (!current.equals("bye")) {
            current = sc.nextLine();
            System.out.println(String.format("\n%s\n%s\n%s", line, current, line));
        }
        System.out.println("Bye. Hope to see you again soon!");
        sc.close();
    }
}
