package eggy;

import eggy.save.Storage;
import eggy.ui.Ui;

public class Eggy {
    public static void main(String[] args) {
        Storage storage = new Storage();
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
