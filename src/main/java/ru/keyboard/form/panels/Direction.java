package ru.keyboard.form.panels;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sokolova
 * @since 01.02.2021
 */
public enum Direction {

    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private static final Map<Integer, Direction> DIRS = new HashMap<>();
    static {
        DIRS.put(KeyEvent.VK_UP, UP);
        DIRS.put(KeyEvent.VK_DOWN, DOWN);
        DIRS.put(KeyEvent.VK_LEFT, LEFT);
        DIRS.put(KeyEvent.VK_RIGHT, RIGHT);
    }

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction get(KeyEvent event) {
        return DIRS.get(event.getKeyCode());
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
