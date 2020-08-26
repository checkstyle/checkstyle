////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ModuleDetails {
    private final List<ModulePropertyDetails> properties = new ArrayList<>();
    private final Map<String, ModulePropertyDetails> modulePropertyKeyMap = new HashMap<>();
    private final List<String> violationMessageKeys = new ArrayList<>();
    private String name;
    private String fullQualifiedName;
    private String parent;
    private String description;
    private ModuleType moduleType;

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

    public void addToProperties(List<ModulePropertyDetails> modulePropertyDetailsList) {
        properties.addAll(modulePropertyDetailsList);
        modulePropertyDetailsList.forEach(modulePropertyDetails -> {
            modulePropertyKeyMap.put(modulePropertyDetails.getName(), modulePropertyDetails);
        });
    }

    public List<String> getViolationMessageKeys() {
        return Collections.unmodifiableList(violationMessageKeys);
    }

    public void addToViolationMessages(String msg) {
        violationMessageKeys.add(msg);
    }

    public void addToViolationMessages(List<String> msgList) {
        violationMessageKeys.addAll(msgList);
    }

    public ModulePropertyDetails getModulePropertyByKey(String key) {
        return modulePropertyKeyMap.get(key);
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }
}
