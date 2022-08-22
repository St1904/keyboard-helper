package ru.keyboard.form.resolvers;

import ru.keyboard.form.processes.mapping.MappingController;
import ru.keyboard.form.panels.Direction;
import ru.keyboard.form.util.ScancodeUtil;

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

    private final AtomicBoolean isStopped = new AtomicBoolean(true);

    private final Queue<KeyEvent> keysQueue;
    private final MappingController mappingController;

    public KeysResolver(Queue<KeyEvent> keysQueue, MappingController mappingController) {
        this.keysQueue = keysQueue;
        this.mappingController = mappingController;
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
        Direction dir = Direction.get(event);
        if (event.isControlDown() && dir != null) {
            mappingController.moveTo(dir);
        } else if (event.isControlDown() && event.getKeyCode() == KeyEvent.VK_ENTER) {
            mappingController.stop();
        } else {
            mappingController.saveAndMove(event);
        }
    }

    public void interrupt() {
        isStopped.set(true);
    }
}
