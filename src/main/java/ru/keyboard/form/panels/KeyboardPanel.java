package ru.keyboard.form.panels;

import javax.swing.*;
import java.awt.*;

/**
 * @author Sokolova
 * @since 23.01.2021
 */
public class KeyboardPanel extends JPanel {

    private final JPanel[][] panels;

    public KeyboardPanel(int rows, int columns) {
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

    public void moveTo(int prevX, int prevY, int nextX, int nextY) {
        panels[prevX][prevY].setBackground(Color.DARK_GRAY);
        panels[nextX][nextY].setBackground(Color.GREEN);
    }
}
