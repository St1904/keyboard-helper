package ru.keyboard.form.writers;

import ru.keyboard.form.Model;
import ru.keyboard.form.jaxb.config.XmlConfig;
import ru.keyboard.form.jaxb.config.XmlProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.keyboard.form.Constants.CONFIG_FILE_ENDING;
import static ru.keyboard.form.Constants.KEYBOARD_PREFIX;

/**
 * Класс отвечает за формирование и сохранение config файла (заглушки для дальнейшего наполнения вручную)
 */
public class ConfigWriter {

    public static void createConfigFile(Model model) {
        Util.createFile(model, true, XmlConfig.class, ConfigWriter::createConfigObject, getFileName(model));
    }

    private static XmlConfig createConfigObject(Model model) {
        XmlConfig xmlConfig = new XmlConfig();
        xmlConfig.setDescription(model.getKeyboardReadableName());
        List<XmlProperty> properties = new ArrayList<>();
        properties.add(new XmlProperty("provider", model.getProviderName()));
        properties.add(new XmlProperty("serviceClass", "ru.crystals.pos.keyboard.plugin.v2.KeyboardPluginV2Impl"));
        properties.add(new XmlProperty("keyboardTimeOut", "50"));
        properties.add(propertyWithSingleInner("cardPrefix", "37"));
        properties.add(propertyWithSingleInner("cardSufix", "63"));
        properties.add(propertyWithSingleInner("cardPrefix2", "59"));
        properties.add(propertyWithSingleInner("cardSufix2", "63"));
        properties.add(new XmlProperty("useKeyLockMap", "true"));
        properties.add(propertyWithSingleInner("keyLockPrefix", "8"));
        properties.add(propertyWithSingleInner("keyLockSufix", "8"));
        properties.add(keyLockMap());
        xmlConfig.setProperties(properties);
        return xmlConfig;
    }

    private static XmlProperty propertyWithSingleInner(String key, String value) {
        XmlProperty property = new XmlProperty();
        property.setKey(key);
        XmlProperty inner = new XmlProperty();
        inner.setValue(value);
        property.setProperties(Collections.singletonList(inner));
        return property;
    }

    private static XmlProperty keyLockMap() {
        XmlProperty property = new XmlProperty();
        property.setKey("keyLockMap");
        List<XmlProperty> inner = new ArrayList<>();
        inner.add(new XmlProperty("A", "0"));
        inner.add(new XmlProperty("B", "1"));
        inner.add(new XmlProperty("C", "2"));
        inner.add(new XmlProperty("D", "2"));
        inner.add(new XmlProperty("E", "2"));
        property.setProperties(inner);
        return property;
    }

    private static String getFileName(Model model) {
        return KEYBOARD_PREFIX + model.getProviderName() + CONFIG_FILE_ENDING;
    }
}
