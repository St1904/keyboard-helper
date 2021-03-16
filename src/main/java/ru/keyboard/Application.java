package ru.keyboard;

import ru.keyboard.form.Controller;
import ru.keyboard.form.Model;

/**
 * @author Sokolova
 * @since 17.01.2021
 */
public class Application {

    private static final String mapsDirArg = "-map-dir=";

    public static void main(String[] args) {
        String mapsDirectory = null;
        for (String arg : args) {
            if (arg.startsWith(mapsDirArg)) {
                mapsDirectory = arg.substring(mapsDirArg.length());
            }
        }
        new Application().run(mapsDirectory);
    }

    private void run(String mapsDirectory) {
        Model model = new Model();
        Controller controller = new Controller(model, mapsDirectory);
    }
}
