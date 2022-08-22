package ru.keyboard.form.processes.logging;

import ru.keyboard.form.MainFrame;

import javax.swing.JFrame;

public class LoggingView {

    private final JFrame mainFrame;

    public LoggingView(LoggingController controller) {
        this.mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        this.mainFrame.addKeyListener(controller.getKeyListener());
    }
}
