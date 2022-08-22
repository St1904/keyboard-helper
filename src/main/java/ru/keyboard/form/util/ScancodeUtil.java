package ru.keyboard.form.util;

import java.awt.event.KeyEvent;

public class ScancodeUtil {

    public static String getScanCodeStr(KeyEvent event) {
        return String.format("0x%08X", getScanCode(getModifiers(event), event.getKeyCode(), event.getKeyChar()));
    }

    private static long getScanCode(int modifiers, int keyCode, char keyChar) {
        long scanCode = 0;
        scanCode += keyChar;
        scanCode += (long) keyCode << 0x10;
        scanCode += (long) modifiers << 0x20;
        return scanCode;
    }

    /**
     * shift и ctrl не зажаты -> 0
     * shift -> 1
     * ctrl -> 2
     * shift + ctrl -> 3
     */
    private static int getModifiers(KeyEvent keyEvent) {
        int modifiers = keyEvent.getModifiers();
        return modifiers & KeyEvent.SHIFT_MASK | modifiers & KeyEvent.CTRL_MASK;
    }
}
