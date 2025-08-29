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
    public static void main(String[] args) {
        Storage storage = new Storage("data/eggy.txt");
        TaskList list = storage.loadTasksFromFile();
        Ui ui = new Ui(list, storage);
        ui.showWelcome();
        try {
            ui.stringHandler();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ui.showGoodbye();
    }

}
