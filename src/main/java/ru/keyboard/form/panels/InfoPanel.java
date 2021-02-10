package ru.keyboard.form.panels;

import ru.keyboard.form.Model;
import ru.keyboard.form.ResBundle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.EnumMap;
import java.util.Map;

import static ru.keyboard.form.panels.Field.*;

/**
 * @author Sokolova
 * @since 23.01.2021
 */
public class InfoPanel extends JPanel {

    private final ActionListener actionListener;
    private final Map<Field, JTextField> textFields = new EnumMap<>(Field.class);

    public InfoPanel(ActionListener actionListener) {
        this.actionListener = actionListener;
        setLayout(null);

        addLabelAndTextField("MODEL_NAME", "MODEL_NAME_EXAMPLE", 0, 200, PROVIDER);
        addLabelAndTextField("MODEL_READABLE", "MODEL_READABLE_EXAMPLE", 1, 200, READABLE_NAME);

        addLabelAndTextField("KEYS_ROWS", "KEYS_ROWS_EXAMPLE", 3, 25, ROWS_NUM);
        addLabelAndTextField("KEYS_COLUMNS", "KEYS_COLUMNS_EXAMPLE", 4, 25, COLS_NUM);

        addLabelAndTextField("KEYS_WIDTH", null, 6, 25, KEY_WIDTH_PX);
        addLabelAndTextField("KEYS_HEIGHT", null, 7, 25, KEY_HEIGHT_PX);
        addLabelAndTextField("KEYS_DISTANCE", null, 8, 25, KEY_DIST_PX);
        addLabelAndTextField("KEYS_X_START", null, 9, 25, X_START_PX);
        addLabelAndTextField("KEYS_Y_START", null, 10, 25, Y_START_PX);

        addAcceptButton();
    }

    private void addLabelAndTextField(String labelKey, String exampleKey, int y, int textFieldWidth, Field field) {
        int height = 20 + 25 * y;

        JLabel label = new JLabel(ResBundle.getString(labelKey));
        label.setBounds(20, height, 220, 20);
//        label.setFocusable(false);
        add(label);

        JTextField textField = new JTextField();
        textField.setBounds(240, height, textFieldWidth, 20);
//        textField.setFocusable(false);
        add(textField);
        textFields.put(field, textField);

        String exampleText = null;
        if (exampleKey != null) {
            exampleText = ResBundle.getString(exampleKey);
        }
        JLabel example = new JLabel(exampleText);
        example.setForeground(Color.GRAY);
        example.setBounds(245 + textFieldWidth, height, 100 + textFieldWidth, 20);
        add(example);
    }

    private void addAcceptButton() {
        JButton button = new JButton();
        button.setBounds(600, 500, 150, 30);
        button.setText(ResBundle.getString("ACCEPT_BUTTON"));
        button.setActionCommand("accept");
        button.addActionListener(actionListener);
        add(button);
    }

    public void updateModel(Model model) {
        model.setProviderName(getFieldText(PROVIDER));
        model.setKeyboardReadableName(getFieldText(READABLE_NAME));
        model.setRows(getFieldInt(ROWS_NUM));
        model.setColumns(getFieldInt(COLS_NUM));
        model.setKeyWidthPx(getFieldInt(KEY_WIDTH_PX));
        model.setKeyHeightPx(getFieldInt(KEY_HEIGHT_PX));
        model.setKeyDistancePx(getFieldInt(KEY_DIST_PX));
        model.setXStartPx(getFieldInt(X_START_PX));
        model.setYStartPx(getFieldInt(Y_START_PX));
    }

    private String getFieldText(Field field) {
        return textFields.get(field).getText();
    }

    private int getFieldInt(Field field) {
        String s = textFields.get(field).getText();
        if (s == null || s.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(s);
    }
}
