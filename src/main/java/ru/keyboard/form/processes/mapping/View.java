package ru.keyboard.form.processes.mapping;

import ru.keyboard.form.MainFrame;
import ru.keyboard.form.Model;
import ru.keyboard.form.panels.InfoPanel;
import ru.keyboard.form.panels.KeyboardPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Sokolova
 * @since 23.01.2021
 */
public class View implements ActionListener {

    private Model model;
    private MappingController mappingController;
    private JFrame mainFrame;

    private InfoPanel infoPanel;
    private KeyboardPanel keyboardPanel;

    public View(Model model, MappingController mappingController) {
        this.model = model;
        this.mappingController = mappingController;
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    // TODO нужно как-то умнее переключаться между окнами
    // TODO например, в модели меняется State, view слушает это событие и рисует нужную панельку
    public void drawInfoPanel() {
        infoPanel = new InfoPanel(this);
        mainFrame.setContentPane(infoPanel);
        mainFrame.revalidate();
    }

    public void drawKeyboardView() {
        keyboardPanel = new KeyboardPanel(model.getRows(), model.getColumns());
        // TODO почему в методе на отрисовку добавляется keyListener?
        mainFrame.addKeyListener(mappingController.getKeyListener());
        mainFrame.setContentPane(keyboardPanel);
        mainFrame.revalidate();
        // запрашиваем фокус после переключения, чтобы листенер срабатывал
//        mainFrame.requestFocus();
    }

    public void moveTo(int prevX, int prevY, int nextX, int nextY) {
        keyboardPanel.moveTo(prevX, prevY, nextX, nextY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "accept":
                infoPanel.updateModel(model);
                mappingController.accept();
        }
    }
}
