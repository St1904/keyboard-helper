package ru.keyboard.form.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "keyboard")
@XmlAccessorType(XmlAccessType.FIELD)
public class KeyboardMap {

    @XmlElement(name = "buttons")
    private XmlButtonWrapper buttons;

    public KeyboardMap() {
    }

    public XmlButtonWrapper getButtons() {
        return buttons;
    }

    public void setButtons(XmlButtonWrapper buttons) {
        this.buttons = buttons;
    }
}
