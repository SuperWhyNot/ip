package jack;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        boolean isGUI = true;
        for (String arg : args) {
            if (arg.equals("--command")) {
                isGUI = false;
                break;
            } else if (arg.equals("-c")) {
                isGUI = false;
            }
        }
        if (isGUI) {
            Application.launch(Main.class, args);
        } else {
            new Jack("./data/duke.txt").run();
        }
    }
}