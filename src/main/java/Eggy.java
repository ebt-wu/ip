public class Eggy {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String formatted_string = String.format(
                "%s\nHello! I'm EGGY\nWhat can I do for you?\n%s\nBye. Hope to see you again soon!\n%s", line, line,
                line);
        System.out.println(formatted_string);
    }
}
