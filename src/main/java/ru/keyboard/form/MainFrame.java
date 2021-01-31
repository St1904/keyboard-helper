package ru.keyboard.form;

import javax.swing.*;

/**
 * @author Sokolova
 * @since 17.01.2021
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        super(ResBundle.getString("MAIN_FRAME_TITLE"));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setFocusable(true);
        setResizable(false);
    }
}
