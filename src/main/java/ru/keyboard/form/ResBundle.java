package ru.keyboard.form;

import java.util.ResourceBundle;

/**
 * @author Sokolova
 * @since 17.01.2021
 */
public class ResBundle {

    private static final ResourceBundle RES_BUNDLE = ResourceBundle.getBundle("labels");

    public static String getString(String key) {
        return RES_BUNDLE.getString(key);
    }
}
