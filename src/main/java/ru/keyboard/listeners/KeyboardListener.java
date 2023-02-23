package ru.keyboard.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * @author Sokolova
 * @since 30.01.2021
 */
public class KeyboardListener implements KeyListener {

    private static final Logger LOG = Logger.getLogger(KeyboardListener.class.getName());

    private final Queue<KeyEvent> keysQueue;

    private final List<KeyEvent> localAltBuffer = new LinkedList<>();
    /**
     * Флаг, определяющий, что был нажат и отпущен shift, без других клавиш между этими событиями
     */
    private boolean isShiftSingle;
    /**
     * Флаг, определяющий, что был нажат и отпущен ctrl, без других клавиш между этими событиями
     */
    private boolean isCtrlSingle;

    public KeyboardListener(Queue<KeyEvent> keysQueue) {
        this.keysQueue = keysQueue;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        log(e, "(keyPressed)");

        // встретились клавиши с модификаторами shift или ctrl (и это не shift или ctrl) - сбросим флаги
        if (e.getKeyCode() != KeyEvent.VK_SHIFT && e.isShiftDown()) {
            isShiftSingle = false;
        }
        if (e.getKeyCode() != KeyEvent.VK_CONTROL && e.isControlDown()) {
            isCtrlSingle = false;
        }
        if (e.isAltDown()) {
            // при нажатом alt собираем alt-код в локальный буфер (кроме нажатия самого alt)
            if (e.getKeyCode() != KeyEvent.VK_ALT) {
                localAltBuffer.add(e);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            isShiftSingle = true;
        } else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            isCtrlSingle = true;
        } else {
            // не функциональные клавиши и не клавиши с модификатором Alt - соберем как обычные нажатия
            keysQueue.add(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // игнорируем печатный символ, просто залогируем
        log(e, "(keyTyped)");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        log(e, "(keyReleased)");
        if (e.getKeyCode() == KeyEvent.VK_SHIFT && isShiftSingle) {
            // shift был нажат и отпущен без лишних кнопок
            keysQueue.add(new KeyEvent(e.getComponent(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), KeyEvent.SHIFT_MASK,
                    KeyEvent.VK_SHIFT, KeyEvent.CHAR_UNDEFINED));
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL && isCtrlSingle) {
            // ctrl был нажат и отпущен без лишних кнопок
            keysQueue.add(new KeyEvent(e.getComponent(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), KeyEvent.CTRL_MASK,
                    KeyEvent.VK_CONTROL, KeyEvent.CHAR_UNDEFINED));
        }
        if (e.getKeyCode() == KeyEvent.VK_ALT) {
            if (localAltBuffer.isEmpty()) {
                // пустая очередь событий после нажатия alt - значит просто нажали и отпустили alt
                keysQueue.add(new KeyEvent(e.getComponent(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), KeyEvent.ALT_MASK,
                        KeyEvent.VK_ALT, KeyEvent.CHAR_UNDEFINED));
            } else {
                // отпустили alt - нужно собрать код клавиши из буфера и добавить его в очередь
                flushAltSequence(e);
                localAltBuffer.clear();
            }
        }
    }

    /**
     * Метод превращает последовательность цифр (собранных при зажатом alt) в код символа
     * и отправляет в очередь нажатий как одну кнопку.
     * Например:
     * 1. Пришли события alt + '5' + '9'
     * 2. В localAltBuffer попали нажатия кнопок '5' и '9'
     * 3. "Склеим" цифры из keyChar этих событий в одно число - 59
     * 4. Полученное число приведем к char - ';'
     *
     * @param e событие keyReleased Alt - чтобы получить контейнер
     */
    private void flushAltSequence(KeyEvent e) {
        if (localAltBuffer.size() > 3) {
            LOG.info("Too many keyboard events inside alt code sequence");
            return;
        }
        int code = 0;
        int multiplier = 1;
        for (int i = localAltBuffer.size() - 1; i >= 0; i--) {
            KeyEvent currentEvent = localAltBuffer.get(i);
            if (currentEvent.getKeyChar() == KeyEvent.CHAR_UNDEFINED || currentEvent.getKeyChar() < '0' || currentEvent.getKeyChar() > '9') {
                LOG.info("Not typed symbol or not a number inside alt code sequence");
                return;
            }
            // превращаем символ в число ('2' -> 2)
            int number = localAltBuffer.get(i).getKeyChar() - '0';
            code += number * multiplier;
            multiplier *= 10;
        }
        keysQueue.add(new KeyEvent(e.getComponent(), KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, 0, (char) code));
    }

    private static void log(KeyEvent keyEvent, String method) {
        LOG.info(String.format("(%-13s) keyCode: 0x%04X, keyChar: %c (0x%04X), shift: %5b, alt: %5b, ctrl: %5b, %d, %c, %d",
                method, keyEvent.getKeyCode(), keyEvent.getKeyChar(), (int) keyEvent.getKeyChar(),
                keyEvent.isShiftDown(), keyEvent.isAltDown(), keyEvent.isControlDown(),
                keyEvent.getKeyCode(), keyEvent.getKeyChar(), (byte) keyEvent.getKeyChar()));
    }
}
