package ru.keyboard.form.resolvers;

import ru.keyboard.form.processes.logging.LoggingController;
import ru.keyboard.form.util.ScancodeUtil;

import java.awt.event.KeyEvent;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class LoggingKeysResolver extends Thread {

    private static final Logger LOG = Logger.getLogger(KeysResolver.class.getName());

    private static final int TIMEOUT = 20;

    private final AtomicBoolean isStopped = new AtomicBoolean(true);

    private final Queue<KeyEvent> keysQueue;
    private final LoggingController controller;

    public LoggingKeysResolver(Queue<KeyEvent> keysQueue, LoggingController controller) {
        this.keysQueue = keysQueue;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            while (isStopped.get()) {
                if (!keysQueue.isEmpty()) {
                    KeyEvent event = keysQueue.poll();
                    String scanCode = ScancodeUtil.getScanCodeStr(event);
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
        if (event.isControlDown() && event.getKeyCode() == KeyEvent.VK_ENTER) {
            controller.stop();
        } else {
            controller.addKey(event);
        }
    }
}
