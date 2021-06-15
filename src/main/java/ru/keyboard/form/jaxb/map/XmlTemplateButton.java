package ru.keyboard.form.jaxb.map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Содержимое блока template в map файле
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlTemplateButton {

    @XmlAttribute(name = "width")
    private int width;

    @XmlAttribute(name = "height")
    private int height;

    @XmlAttribute(name = "maxCount")
    private int maxCount;

    @XmlAttribute(name = "id")
    private int id;

    @XmlAttribute(name = "pazzle")
    private int pazzle;

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

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPazzle() {
        return pazzle;
    }

    public void setPazzle(int pazzle) {
        this.pazzle = pazzle;
    }
}
