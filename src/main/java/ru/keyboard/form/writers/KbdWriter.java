package ru.keyboard.form.writers;

import ru.keyboard.form.Model;
import ru.keyboard.form.jaxb.kbd.XmlKbd;

import static ru.keyboard.form.Constants.CONCRETE_KBD_SUFFIX;
import static ru.keyboard.form.Constants.KBD_ENDING;
import static ru.keyboard.form.Constants.KEYBOARD_PREFIX;

public class KbdWriter {

    public static void createKbdFiles(Model model) {
        Util.createFile(model, true, XmlKbd.class, KbdWriter::createTemplateKbdObject, getTemplateKbdFileName(model));
        Util.createFile(model, true, XmlKbd.class, KbdWriter::createConcreteKbdObject, getConcreteKbdFileName(model));
    }

    private static XmlKbd createTemplateKbdObject(Model model) {
        XmlKbd template = new XmlKbd();
        template.setCountX(model.getColumns());
        template.setCountY(model.getRows());
        template.setModel(model.getProviderName());
        return template;
    }

    private static XmlKbd createConcreteKbdObject(Model model) {
        XmlKbd template = createTemplateKbdObject(model);
        template.setLocalizedName("Стандартная");
        return template;
    }

    private static String getTemplateKbdFileName(Model model) {
        return model.getProviderName() + KBD_ENDING;
    }

    private static String getConcreteKbdFileName(Model model) {
        return KEYBOARD_PREFIX + model.getProviderName() + CONCRETE_KBD_SUFFIX + KBD_ENDING;
    }
}
