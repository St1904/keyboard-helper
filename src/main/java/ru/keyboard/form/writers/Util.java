package ru.keyboard.form.writers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static ru.keyboard.form.Constants.OUTPUT_DIRECTORY;
import static ru.keyboard.form.Constants.XML_HEADER;

/**
 * Вспомогательные (общие) методы для формирования xml файлов
 */
public class Util {

    static Marshaller createMarshaller(Class<?> clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        return marshaller;
    }

    static void addHeader(StringWriter sw) {
        sw.append(XML_HEADER);
    }

    static void writeFile(String fileName, String fileContent) {
        Path mapFile = OUTPUT_DIRECTORY.resolve(fileName);
        try {
            if (!Files.exists(mapFile)) {
                Files.createFile(mapFile);
            }
            Files.write(mapFile, fileContent.getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
