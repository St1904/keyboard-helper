package ru.keyboard.form.jaxb.map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * Обертка для основного списка кнопок в map файле (завернуто в buttons)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlButtonWrapper {

    @XmlElement(name = "main")
    private XmlMainBlock main;

    @XmlElementWrapper(name = "template")
    @XmlElement(name = "button")
    private List<XmlTemplateButton> templateButtons;

    @XmlAttribute(name = "CountX")
    private int countX;

    @XmlAttribute(name = "CountY")
    private int countY;

    public XmlButtonWrapper() {
    }

    public XmlMainBlock getMain() {
        return main;
    }

    public void setMain(XmlMainBlock main) {
        this.main = main;
    }

    public List<XmlTemplateButton> getTemplateButtons() {
        return templateButtons;
    }

    public void setTemplateButtons(List<XmlTemplateButton> templateButtons) {
        this.templateButtons = templateButtons;
    }

    public int getCountX() {
        return countX;
    }

    public void setCountX(int countX) {
        this.countX = countX;
    }

    public int getCountY() {
        return countY;
    }

    public void setCountY(int countY) {
        this.countY = countY;
    }
}
