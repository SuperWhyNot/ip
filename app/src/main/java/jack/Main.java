package jack;

import java.io.IOException;

import jack.storage.Storage;
import jack.task.TaskList;
import jack.ui.GUI;
import jack.ui.Ui;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            TaskList tasks = new TaskList();
            Storage storage = new Storage("./data/duke.txt");
            tasks = storage.read();
            
            GUI gui = new GUI(stage, tasks, new Ui(), storage);
            gui.show();
        } catch (Excep e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
