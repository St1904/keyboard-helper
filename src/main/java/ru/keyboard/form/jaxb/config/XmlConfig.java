package ru.keyboard.form.jaxb.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Класс-сущность для keyboard-xxx-config.xml
 */
@XmlRootElement(name = "moduleConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlConfig {

    @XmlAttribute(name = "xsi:schemaLocation")
    private final String schemaLocation = "http://crystals.ru/cash/settings ../../module-config.xsd";

    @XmlAttribute(name = "settingsGroup")
    private final String settingsGroup = "equipment";

    @XmlAttribute(name = "visible")
    private final boolean visible = true;

    @XmlAttribute(name = "description")
    private String description;

    @XmlAttribute(name = "xmlns")
    private final String xmlns = "http://crystals.ru/cash/settings";

    @XmlAttribute(name = "xmlns:xsi")
    private final String xmlnsXsi = "http://www.w3.org/2001/XMLSchema-instance";

    @XmlElement(name = "property")
    private List<XmlProperty> properties;

    public XmlConfig() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<XmlProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<XmlProperty> properties) {
        this.properties = properties;
    }
}
