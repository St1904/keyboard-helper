package ru.keyboard.form.writers;

import ru.keyboard.form.Model;
import ru.keyboard.form.jaxb.config.XmlConfig;
import ru.keyboard.form.jaxb.config.XmlProperty;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс отвечает за формирование и сохранение config файла (заглушки для дальнейшего наполнения вручную)
 */
public class ConfigWriter {

    // TODO в общие константы куда-нибудь вынести - и переименовать
    private static final Path MAP_FILE_DIRECTORY = Paths.get("D:\\temp");
    private static final String CONFIG_FILE_PREFIX = "keyboard-";
    private static final String CONFIG_FILE_ENDING = "-config.xml";

    public static void createConfigFile(Model model) {
        try {
            String configContent = createConfigContent(model);
            System.out.println(configContent);
            writeConfigFile(model, configContent);
        } catch (JAXBException e) {
            System.out.println("Problem with JAXB");
            e.printStackTrace();
        }
    }

    private static String createConfigContent(Model model) throws JAXBException {
        Marshaller marshaller = Util.create(XmlConfig.class);
        StringWriter sw = new StringWriter();
        Util.addHeader(sw);
        marshaller.marshal(createConfigObject(model), sw);
        return sw.toString();
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
    
    private static void writeConfigFile(Model model, String configContent) {
        String mapFileName = CONFIG_FILE_PREFIX + model.getProviderName() + CONFIG_FILE_ENDING;
        Path mapFile = MAP_FILE_DIRECTORY.resolve(mapFileName);
        try {
            if (!Files.exists(mapFile)) {
                Files.createFile(mapFile);
            }
            Files.write(mapFile, configContent.getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
