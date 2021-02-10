package ru.keyboard.form;

import ru.keyboard.form.panels.Direction;
import ru.keyboard.listeners.KeyboardListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Sokolova
 * @since 23.01.2021
 */
public class Controller {

    private static final Path MAP_FILE_DIRECTORY = Paths.get("D:\\temp");

    private final Model model;
    private final View view;

    private final Queue<KeyEvent> keysQueue;
    private final KeyListener keyListener;

    private int curX;
    private int curY;

    public Controller(Model model) {
        this.model = model;
        this.keysQueue = new LinkedBlockingQueue<>();
        // TODO нужно решать какой листенер нам нужен (например через проперти или в рантайме)
        keyListener = new KeyboardListener(keysQueue);
        view = new View(model, this);
        view.drawInfoPanel();
//        view.addKeyListener(keyListener);
    }

    public void accept() {
        view.drawKeyboardView();
        model.setScanCodes(new String[model.getColumns()][model.getRows()]);
        startKeysResolver();
    }

    public void startKeysResolver() {
        Thread thread = new KeysResolver(keysQueue, this);
        thread.start();
    }

    public void saveAndMove(KeyEvent event) {
        // записали сканкод текущей клавиши
        model.getScanCodes()[curX][curY] = getScanCodeStr(event);

        // определили, куда переходим дальше
        int nextY = curY + 1;
        if (0 < nextY && nextY < model.getRows()) {
            // перемещение по колонке
            curY = nextY;
        } else {
            // колонка кончилась, попробуем начать следующую
            int nextX = curX + 1;
            if (0 <= nextY && nextX < model.getColumns()) {
                curX = nextX;
                curY = 0;
            } else {
                // TODO некуда переходить - надо что-то делать с этим
            }
        }

        // отрисовали перемещение
        view.moveTo(curX, curY);
    }

    public void moveTo(Direction dir) {
        // только отрисовали перемещение - сканкод не сохраняется
        view.moveTo(dir);
    }

    // получен сигнал завершения
    public void stop() {
        // TODO сформировать мапу, завершить потоки, закрыть окно
        // создали шапку
        Template template = new Template()
                .startDescription()
                // TODO это не совсе оно же? + забыла layout
                .appendName(model.getKeyboardReadableName())
                .appendModel(model.getProviderName())
                .endDescription()
                .startButtonsBlock(model.getRows(), model.getColumns(), model.getKeyWidthPx(), model.getKeyHeightPx());
        String[][] scanCodes = model.getScanCodes();
        int x = model.getXStartPx();
        int y = model.getYStartPx();
        // заполняем кнопочки
        for (String[] scanCodeColumn : scanCodes) {
            for (String s : scanCodeColumn) {
                template.addButton(x, y, s);
                y += model.getKeyHeightPx() + model.getKeyDistancePx();
            }
            template.addEmptyLine();
            x += model.getKeyWidthPx() + model.getKeyDistancePx();
            y = model.getYStartPx();
        }
        template.addEnd();

        String result = template.getResult();
        System.out.println(result);

        String mapFileName = model.getProviderName() + "-map.xml";
        Path mapFile = MAP_FILE_DIRECTORY.resolve(mapFileName);
        try {
            if (Files.exists(mapFile)) {
                System.out.println("MAP FILE " + mapFileName + " ALREADY EXISTS!");
                return;
            }
            Files.createFile(mapFile);
            Files.write(mapFile, result.getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getScanCodeStr(KeyEvent event) {
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

    public KeyListener getKeyListener() {
        return keyListener;
    }
}
