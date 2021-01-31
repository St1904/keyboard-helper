package ru.keyboard;

import ru.keyboard.form.Controller;
import ru.keyboard.form.Model;

/**
 * @author Sokolova
 * @since 17.01.2021
 */
public class Application {

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        Model model = new Model();
        Controller controller = new Controller(model);
    }
}
