package ru.keyboard.form;

import ru.keyboard.form.panels.Direction;

import java.awt.event.KeyEvent;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * @author Sokolova
 * @since 31.01.2021
 */
public class KeysResolver extends Thread {

    private static final Logger LOG = Logger.getLogger(KeysResolver.class.getName());

    private static final int TIMEOUT = 20;

    private volatile AtomicBoolean isStopped = new AtomicBoolean(true);

    private final Queue<KeyEvent> keysQueue;
    private final Controller controller;

    public KeysResolver(Queue<KeyEvent> keysQueue, Controller controller) {
        this.keysQueue = keysQueue;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            while (isStopped.get()) {
                if (!keysQueue.isEmpty()) {
                    KeyEvent event = keysQueue.poll();
                    String scanCode = controller.getScanCodeStr(event);
                    LOG.info(scanCode);

                    resolve(event);
                }
                TimeUnit.MILLISECONDS.sleep(TIMEOUT);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void resolve(KeyEvent event) {
        Direction dir = Direction.get(event);
        if (event.isControlDown() && dir != null) {
            controller.moveTo(dir);
        } else if (event.isControlDown() && event.getKeyCode() == KeyEvent.VK_ENTER) {
            controller.stop();
        } else {
            controller.saveAndMove(event);
        }
    }

    public void interrupt() {
        isStopped.set(true);
    }
}
