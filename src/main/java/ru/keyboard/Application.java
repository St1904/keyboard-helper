package ru.keyboard;

import ru.keyboard.form.processes.logging.LoggingController;
import ru.keyboard.form.processes.mapping.MappingController;
import ru.keyboard.form.Model;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Sokolova
 * @since 17.01.2021
 */
public class Application {

    private static final String MAPS_DIR_ARG = "-map-dir=";
    private static final String TYPED_ONLY_ARG = "-typed-only";

    public static void main(String[] args) {
        String mapsDirectory = null;
        boolean typedOnly = false;
        for (String arg : args) {
            if (arg.startsWith(MAPS_DIR_ARG)) {
                mapsDirectory = arg.substring(MAPS_DIR_ARG.length());
            }
            if (arg.equals(TYPED_ONLY_ARG)) {
                typedOnly = true;
            }
        }
        new Application().run(mapsDirectory, typedOnly);
    }

    private void run(String mapsDirectory, boolean typedOnly) {
        if (typedOnly) {
            new LoggingController();
        } else {
            Model model = new Model();
            new MappingController(model, mapsDirectory);
        }
    }
}
