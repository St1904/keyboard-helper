package ru.keyboard.form;

import ru.keyboard.form.panels.Direction;
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
    private Controller controller;
    private JFrame mainFrame;

    private InfoPanel infoPanel;
    private KeyboardPanel keyboardPanel;

    public View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    // TODO нужно как-то умнее переключаться между окнами
    // TODO например, в модели меняется State, view слушает это событие и рисует нужную панельку
    public void drawInfoPanel() {
        infoPanel = new InfoPanel(this, controller.getKeyListener());
        mainFrame.setContentPane(infoPanel);
        mainFrame.revalidate();
    }

    public void drawKeyboardView() {
        keyboardPanel = new KeyboardPanel(model.getRows(), model.getColumns());
        // TODO почему в методе на отрисовку добавляется keyListener?
        mainFrame.addKeyListener(controller.getKeyListener());
        mainFrame.setContentPane(keyboardPanel);
        mainFrame.revalidate();
        // запрашиваем фокус после переключения, чтобы листенер срабатывал
//        mainFrame.requestFocus();
    }

    // TODO очень длинная цепочка: KeysResolver -> Controller -> View -> Panel
    public void moveTo(Direction dir) {
        keyboardPanel.moveTo(dir);
    }

    public void moveTo(int nextX, int nextY) {
        keyboardPanel.moveTo(nextX, nextY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "accept":
                infoPanel.updateModel(model);
                controller.accept();
        }
    }
}
