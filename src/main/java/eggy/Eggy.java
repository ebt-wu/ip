package eggy;

import eggy.save.Storage;
import eggy.ui.Ui;

/**
 * Main class to run the Eggy application.
 */

public class Eggy {
    private Storage storage;
    private Ui ui;  
    private TaskList list;

    public Eggy() {
        storage = new Storage();
        list = storage.loadTasksFromFile();
        ui = new Ui(list, storage);
    }
    /*
     * Main method to start the Eggy application.
     * It initializes the Storage, TaskList, and Ui classes,
     */
    // public static void main(String[] args) {
    //     Storage storage = new Storage();
    //     TaskList list = storage.loadTasksFromFile();
    //     Ui ui = new Ui(list, storage);
    //     ui.showWelcome();
    //     try {
    //         ui.stringHandler();
    //     } catch (Exception e) {
    //         System.out.println(e.getMessage());
    //     }
    //     ui.showGoodbye();
    // }

    /**
     * Generates a response from Eggy based on user input.
     *
     * @param input The user input string.
     * @return The response string from Eggy.
     */
    public String getResponse(String input) {
        try {
            return ui.getResponse(input);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
