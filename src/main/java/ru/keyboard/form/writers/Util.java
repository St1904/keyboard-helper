package ru.keyboard.form.writers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * Вспомогательные (общие) методы для формирования xml файлов
 */
public class Util {

    static Marshaller create(Class<?> clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        return marshaller;
    }

    static void addHeader(StringWriter sw) {
        sw.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    }
}
