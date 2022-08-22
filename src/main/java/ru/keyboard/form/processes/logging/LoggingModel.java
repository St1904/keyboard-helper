package ru.keyboard.form.processes.logging;

import ru.keyboard.form.util.ScancodeUtil;

import java.awt.event.KeyEvent;
import java.util.LinkedHashSet;
import java.util.Set;

public class LoggingModel {

    private final Set<String> result = new LinkedHashSet<>();

    public void addKey(KeyEvent e) {
        result.add(String.format("%s, \"%c\"", ScancodeUtil.getScanCodeStr(e), e.getKeyChar()));
    }

    public Set<String> getResult() {
        return result;
    }
}
