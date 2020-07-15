package com.puppycrawl.tools.checkstyle.api.metadata;

import java.util.Collections;
import java.util.List;

public class ModulePropertyDetails {
    private String name;
    private String type;
    private String defaultValue;
    /**
     * This property can have two options:
     * scalar - it means that value is single (like int, String, .... ).
     * set (or collection) - is set/array/collection of values separated by , in our case set of tokens or set of String.
     */
    private String valueType;
    private String description;
    private List<String> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getValues() {
        return Collections.unmodifiableList(values);
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
