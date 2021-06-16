package ru.keyboard.form.writers;

import ru.keyboard.form.Model;
import ru.keyboard.form.jaxb.map.XmlButton;
import ru.keyboard.form.jaxb.map.XmlButtonWrapper;
import ru.keyboard.form.jaxb.map.XmlMainBlock;
import ru.keyboard.form.jaxb.map.XmlMap;
import ru.keyboard.form.jaxb.map.XmlTemplateButton;

import java.util.ArrayList;
import java.util.List;

import static ru.keyboard.form.Constants.MAP_FILE_ENDING;

/**
 * Класс отвечает за формирование и сохранение map файла
 */
public class MapWriter {

    public static void createMapFile(Model model) {
        Util.createFile(model, true, XmlMap.class, MapWriter::createKeyboardMapObject, getFileName(model));
    }

    private static XmlMap createKeyboardMapObject(Model model) {
        XmlMap map = new XmlMap();
        map.setName(model.getKeyboardReadableName());
        map.setLayout(model.getLayout());
        map.setModel(model.getProviderName());
        map.setButtons(createButtonsWrapper(model));
        return map;
    }

    private static XmlButtonWrapper createButtonsWrapper(Model model) {
        XmlButtonWrapper buttonWrapper = new XmlButtonWrapper();
        buttonWrapper.setCountX(model.getColumns());
        buttonWrapper.setCountY(model.getRows());
        buttonWrapper.setMain(createMainBlock(model));
        buttonWrapper.setTemplateButtons(createTemplateButtons(model));
        return buttonWrapper;
    }

    private static XmlMainBlock createMainBlock(Model model) {
        XmlMainBlock mainBlock = new XmlMainBlock();
        mainBlock.setWidth(model.getKeyWidthPx());
        mainBlock.setHeight(model.getKeyHeightPx());
        mainBlock.setKbdAlphaNumeric(false);
        mainBlock.setButtons(createButtons(model));
        return mainBlock;
    }

    private static List<XmlButton> createButtons(Model model) {
        List<XmlButton> buttons = new ArrayList<>();
        int x = model.getXStartPx();
        int y = model.getYStartPx();
        for (String[] scanCodeColumn : model.getScanCodes()) {
            for (String s : scanCodeColumn) {
                XmlButton button = new XmlButton();
                button.setX(x);
                button.setY(y);
                button.setScanCode(s);
                buttons.add(button);
                y += model.getKeyHeightPx() + model.getKeyDistancePx();
            }
            x += model.getKeyWidthPx() + model.getKeyDistancePx();
            y = model.getYStartPx();
        }
        return buttons;
    }

    private static List<XmlTemplateButton> createTemplateButtons(Model model) {
        List<XmlTemplateButton> buttons = new ArrayList<>();

        XmlTemplateButton single = new XmlTemplateButton();
        single.setWidth(model.getKeyWidthPx());
        single.setHeight(model.getKeyHeightPx());
        single.setMaxCount(5);
        single.setId(1);
        single.setPazzle(1);
        buttons.add(single);

        XmlTemplateButton horizontal = new XmlTemplateButton();
        horizontal.setWidth(model.getKeyWidthPx() * 2 + model.getKeyDistancePx());
        horizontal.setHeight(model.getKeyHeightPx());
        horizontal.setMaxCount(5);
        horizontal.setId(2);
        horizontal.setPazzle(2);
        buttons.add(horizontal);

        XmlTemplateButton vertical = new XmlTemplateButton();
        vertical.setWidth(model.getKeyWidthPx());
        vertical.setHeight(model.getKeyHeightPx() * 2 + model.getKeyDistancePx());
        vertical.setMaxCount(5);
        vertical.setId(3);
        vertical.setPazzle(2);
        buttons.add(vertical);

        XmlTemplateButton quad = new XmlTemplateButton();
        quad.setWidth(model.getKeyWidthPx() * 2 + model.getKeyDistancePx());
        quad.setHeight(model.getKeyHeightPx() * 2 + model.getKeyDistancePx());
        quad.setMaxCount(5);
        quad.setId(4);
        quad.setPazzle(4);
        buttons.add(quad);

        return buttons;
    }

    private static String getFileName(Model model) {
        return model.getProviderName() + MAP_FILE_ENDING;
    }
}
