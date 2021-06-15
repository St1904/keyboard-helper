package ru.keyboard.form.jaxb.map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс-сущность для xxx-map.xml
 */
@XmlRootElement(name = "keyboard")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMap {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "layout")
    private String layout;

    @XmlAttribute(name = "model")
    private String model;

    @XmlElement(name = "buttons")
    private XmlButtonWrapper buttons;

    public XmlMap() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public XmlButtonWrapper getButtons() {
        return buttons;
    }

    public void setButtons(XmlButtonWrapper buttons) {
        this.buttons = buttons;
    }
}
