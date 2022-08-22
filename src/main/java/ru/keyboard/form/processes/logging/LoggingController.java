package ru.keyboard.form.processes.logging;

import ru.keyboard.form.resolvers.LoggingKeysResolver;
import ru.keyboard.listeners.KeyboardListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Контроллер, который выводит пустое окно и только логирует нажатые символы с их сканкодами
 */
public class LoggingController {

    private final KeyListener keyListener;
    private final LoggingModel model;

    public LoggingController() {
        Queue<KeyEvent> keysQueue = new LinkedBlockingQueue<>();
        this.keyListener = new KeyboardListener(keysQueue);
        this.model = new LoggingModel();
        new LoggingView(this);
        new LoggingKeysResolver(keysQueue, this).start();
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

    public void addKey(KeyEvent e) {
        model.addKey(e);
    }

    public void stop() {
        for (String s : model.getResult()) {
            System.out.println(s);
        }
    }
}
