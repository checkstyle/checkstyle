package com.puppycrawl.tools.checkstyle.api.metadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleDetails {
    private String name;
    private String fullQualifiedName;
    private String parent;
    private String description;
    private final List<ModulePropertyDetails> properties = new ArrayList<>();
    private final Map<String, ModulePropertyDetails> modulePropertyKeyMap = new HashMap<>();
    private final List<String> violationMessageKeys = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullQualifiedName() {
        return fullQualifiedName;
    }

    public void setFullQualifiedName(String fullQualifiedName) {
        this.fullQualifiedName = fullQualifiedName;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ModulePropertyDetails> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public void addToProperties(ModulePropertyDetails property) {
        properties.add(property);
        modulePropertyKeyMap.put(property.getName(), property);
    }

    public List<String> getViolationMessageKeys() {
        return Collections.unmodifiableList(violationMessageKeys);
    }

    public void addToViolationMessages(String msg) {
        violationMessageKeys.add(msg);
    }

    public ModulePropertyDetails getModulePropertyByKey(String key) {
        return modulePropertyKeyMap.get(key);
    }

    public void setModulePropertyByKey(List<ModulePropertyDetails> modulePropertyDetailsList) {
        modulePropertyDetailsList.forEach(modulePropertyDetails -> {
            modulePropertyKeyMap.put(modulePropertyDetails.getName(), modulePropertyDetails);
        });
    }
}
