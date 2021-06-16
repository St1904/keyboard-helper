package ru.keyboard.form.jaxb.kbd;

import ru.keyboard.form.jaxb.XmlRoot;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Класс-сущность для xxx-kbd.xml и keyboard-xxx-0-kbd.xml
 */
@XmlRootElement(name = "keyboard-data")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlKbd implements XmlRoot {

    @XmlAttribute(name = "CountX")
    private int countX;

    @XmlAttribute(name = "CountY")
    private int countY;

    @XmlAttribute(name = "Model")
    private String model;

    @XmlAttribute(name = "LocalizedName")
    private String localizedName;

    // для генерации закрывающего тега на следующей стройке (для удобства доработки xml вручную)
    @XmlValue
    private final String content = "\n";

    public XmlKbd() {
    }

    public void setCountX(int countX) {
        this.countX = countX;
    }

    public void setCountY(int countY) {
        this.countY = countY;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }
}
