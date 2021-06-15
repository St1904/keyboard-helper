package ru.keyboard.form;

import javafx.util.Pair;
import ru.keyboard.form.jaxb.map.XmlMap;
import ru.keyboard.form.jaxb.map.XmlButton;
import ru.keyboard.form.panels.Direction;
import ru.keyboard.form.writers.ConfigWriter;
import ru.keyboard.form.writers.MapWriter;
import ru.keyboard.listeners.KeyboardListener;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Sokolova
 * @since 23.01.2021
 */
public class Controller {

    private static final String MAP_FILE_ENDING = "-map.xml";
    private static final String VIRT_MAP_SEPARATOR = ",";

    private final Model model;
    private final View view;

    private final Queue<KeyEvent> keysQueue;
    private final KeyListener keyListener;

    private int curX;
    private int curY;

    private final String mapsDirectory;
    private Path oldMapFile;

    public Controller(Model model, String mapsDirectory) {
        this.model = model;
        this.keysQueue = new LinkedBlockingQueue<>();
        // TODO нужно решать какой листенер нам нужен (например через проперти или в рантайме)
        keyListener = new KeyboardListener(keysQueue);
        view = new View(model, this);
        view.drawInfoPanel();
        this.mapsDirectory = mapsDirectory;
    }

    public void accept() {
        if (!validateModel()) {
            return;
        }
        view.drawKeyboardView();
        model.setScanCodes(new String[model.getColumns()][model.getRows()]);
        startKeysResolver();
    }

    private boolean validateModel() {
        oldMapFile = getProviderMapFile();
        if (model.isVirtualMap() && oldMapFile == null) {
            System.out.println("Cannot find map file in directory: " + mapsDirectory);
            System.out.println("Check application argument '-map-dir'");
            return false;
        }
        // TODO а другие поля?
        return true;
    }

    private Path getProviderMapFile() {
        if (mapsDirectory == null) {
            return null;
        }
        Path mapsDirPath = Paths.get(mapsDirectory);
        if (Files.notExists(mapsDirPath) || !Files.isDirectory(mapsDirPath)) {
            return null;
        }
        Path mapFile = mapsDirPath.resolve(model.getProviderName() + MAP_FILE_ENDING);
        if (Files.notExists(mapFile) || !Files.isRegularFile(mapFile)) {
            return null;
        }
        return mapFile;
    }

    public void startKeysResolver() {
        Thread thread = new KeysResolver(keysQueue, this);
        thread.start();
    }

    public void saveAndMove(KeyEvent event) {
        // записали сканкод текущей клавиши
        model.getScanCodes()[curX][curY] = getScanCodeStr(event);

        // записали последнее положение
        int prevX = curX;
        int prevY = curY;
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
            }
        }

        // отрисовали перемещение
        view.moveTo(prevX, prevY, curX, curY);
    }

    public void moveTo(Direction dir) {
        int nextX = curX + dir.getDx();
        int nextY = curY + dir.getDy();
        if (0 <= nextX && nextX < model.getColumns()
                && 0 <= nextY && nextY < model.getRows()) {
            view.moveTo(curX, curY, nextX, nextY);
            curX = nextX;
            curY = nextY;
        }
    }

    // TODO завершить потоки, закрыть окно
    // получен сигнал завершения
    public void stop() {
        checkDuplicates();
        if (model.isVirtualMap()) {
            // создание virtual-map файла на основе существующего с таким же именем
            String virtMap = createVirtualMap();
            System.out.println(virtMap);
            // TODO implement
//            writeVirtMapFile(virtMap);
        } else {
            MapWriter.createMapFile(model);
            ConfigWriter.createConfigFile(model);
        }
    }

    private void checkDuplicates() {
        String[][] scanCodes = model.getScanCodes();
        Set<String> uniqueScanCodes = new HashSet<>();
        for (int i = 0; i < scanCodes.length; i++) {
            String[] scanCodeColumn = scanCodes[i];
            for (int j = 0; j < scanCodeColumn.length; j++) {
                String s = scanCodeColumn[j];
                if (s != null && uniqueScanCodes.contains(s)) {
                    System.out.println("DUPLICATE SCAN CODE " + s + " строка = " + (j + 1) + ", колонка = " + (i + 1));
                }
                uniqueScanCodes.add(s);
            }
        }
    }

    private String createVirtualMap() {
        StringBuilder sb = new StringBuilder();
        Map<Pair<Integer, Integer>, String> oldScanCodes = getOldScancodes();
        String[][] scanCodes = model.getScanCodes();
        int x = model.getXStartPx();
        int y = model.getYStartPx();
        for (String[] scanCodeColumn : scanCodes) {
            for (String s : scanCodeColumn) {
                String oldScanCode = oldScanCodes.get(new Pair<>(x, y));
                if (oldScanCode == null) {
                    System.out.println("ERROR! Old scan code not found (x: " + x + ", y: " + y + ")");
                } else {
                    sb.append(s).append(VIRT_MAP_SEPARATOR).append(oldScanCode).append("\n");
                }
                y += model.getKeyHeightPx() + model.getKeyDistancePx();
            }
            x += model.getKeyWidthPx() + model.getKeyDistancePx();
            y = model.getYStartPx();
        }

        return sb.toString();
    }

    private Map<Pair<Integer, Integer>, String> getOldScancodes() {
        Map<Pair<Integer, Integer>, String> result = new HashMap<>();
        try (FileReader mapFileReader = new FileReader(oldMapFile.toFile())) {
            JAXBContext context = JAXBContext.newInstance(XmlMap.class);
            XmlMap xmlMap = (XmlMap) context.createUnmarshaller().unmarshal(mapFileReader);
            for (XmlButton button : xmlMap.getButtons().getMain().getButtons()) {
                result.put(new Pair<>(button.getX(), button.getY()), button.getScanCode());
            }
        } catch (JAXBException e) {
            System.out.println("Problem with JAXB");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Problem with FileReader");
            e.printStackTrace();
        }
        return result;
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
