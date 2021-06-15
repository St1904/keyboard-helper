package ru.keyboard.form.jaxb.map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Блок "main", объединяющий список кнопок
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMainBlock {

    @XmlAttribute(name = "width")
    private int width;

    @XmlAttribute(name = "height")
    private int height;

    @XmlAttribute(name = "kbdAlphaNumeric")
    private boolean kbdAlphaNumeric;

    @XmlElement(name = "button")
    private List<XmlButton> buttons;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isKbdAlphaNumeric() {
        return kbdAlphaNumeric;
    }

    public void setKbdAlphaNumeric(boolean kbdAlphaNumeric) {
        this.kbdAlphaNumeric = kbdAlphaNumeric;
    }

    public List<XmlButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<XmlButton> buttons) {
        this.buttons = buttons;
    }
}
