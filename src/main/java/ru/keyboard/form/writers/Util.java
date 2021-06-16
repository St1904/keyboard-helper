package ru.keyboard.form.writers;

import ru.keyboard.form.Model;
import ru.keyboard.form.jaxb.XmlRoot;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

import static ru.keyboard.form.Constants.OUTPUT_DIRECTORY;
import static ru.keyboard.form.Constants.XML_HEADER;

/**
 * Вспомогательные (общие) методы для формирования xml файлов
 */
public class Util {

    static void createFile(Model model,
                           boolean addHeader,
                           Class<?> xmlClass,
                           Function<Model, XmlRoot> createContent,
                           String outputFileName) {
        try {
            JAXBContext context = JAXBContext.newInstance(xmlClass);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            StringWriter sw = new StringWriter();
            if (addHeader) {
                sw.append(XML_HEADER);
            }
            marshaller.marshal(createContent.apply(model), sw);
            String content = sw.toString();
            System.out.println(content);
            writeFile(outputFileName, content);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    static void writeFile(String fileName, String fileContent) {
        Path mapFile = OUTPUT_DIRECTORY.resolve(fileName);
        try {
            if (!Files.exists(mapFile)) {
                Files.createFile(mapFile);
            }
            Files.write(mapFile, fileContent.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
