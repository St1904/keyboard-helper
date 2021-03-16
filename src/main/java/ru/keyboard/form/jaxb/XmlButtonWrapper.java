package ru.keyboard.form.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlButtonWrapper {

    @XmlElementWrapper(name = "main")
    @XmlElement(name = "button")
    private List<XmlButton> buttons;

    @XmlAttribute(name = "CountX")
    private String countX;

    @XmlAttribute(name = "CountY")
    private String countY;

    public XmlButtonWrapper() {
    }

    public List<XmlButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<XmlButton> buttons) {
        this.buttons = buttons;
    }

    public String getCountX() {
        return countX;
    }

    public void setCountX(String countX) {
        this.countX = countX;
    }

    public String getCountY() {
        return countY;
    }

    public void setCountY(String countY) {
        this.countY = countY;
    }
}
