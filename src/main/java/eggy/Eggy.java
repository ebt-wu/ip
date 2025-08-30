package eggy;

import eggy.save.Storage;
import eggy.ui.Ui;

// Main class to run the Eggy application
public class Eggy {
    // Main method to start the application
    public static void main(String[] args) {
        Storage storage = new Storage(); 
        TaskList list = storage.loadTasksFromFile();
        Ui ui = new Ui(list, storage);
        ui.showWelcome();
        try {
            ui.stringHandler();
        } catch (Exception e) { // I dont know what exceptions might be thrown here so  I just put the most general one
            System.out.println(e.getMessage());
        }
        ui.showGoodbye();
    }

}
