package ru.keyboard.form.panels;

import javax.swing.*;
import java.awt.*;

/**
 * @author Sokolova
 * @since 23.01.2021
 */
public class KeyboardPanel extends JPanel {

    private final JPanel[][] panels;
    private int curX = 0;
    private int curY = 0;
    private int rows;
    private int columns;

    public KeyboardPanel(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        setLayout(new GridLayout(rows, columns, 1, 1));

        panels = new JPanel[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                JPanel panel = new JPanel();
                panel.setFocusable(false);
                panel.setBackground(Color.DARK_GRAY);
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                JLabel label = new JLabel("i: " + i + ", j: " + j);
                panel.add(label);

                panels[i][j] = panel;
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // добавляем в другом порядке, чтобы клавиши шли колонками
                add(panels[j][i]);
            }
        }
        // начинаем всегда с верхнего левого угла
        panels[0][0].setBackground(Color.GREEN);
    }

    public void moveTo(Direction dir) {
        int nextX = curX + dir.getDx();
        int nextY = curY + dir.getDy();
        moveTo(nextX, nextY);
    }

    public void moveTo(int nextX, int nextY) {
        if (0 <= nextX && nextX < columns
                && 0 <= nextY && nextY < rows) {
            panels[curX][curY].setBackground(Color.DARK_GRAY);
            curX = nextX;
            curY = nextY;
            panels[curX][curY].setBackground(Color.GREEN);
        }
    }
}
