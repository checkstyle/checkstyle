////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Simple POJO class for module details. */
public final class ModuleDetails {

    /** List of properties of the module. */
    private final List<ModulePropertyDetails> properties = new ArrayList<>();

    /** List of violation message keys of the module. */
    private final List<String> violationMessageKeys = new ArrayList<>();

    /** Name of the module. */
    private String name;

    /** Fully qualified name of the module. */
    private String fullQualifiedName;

    /** Parent module. */
    private String parent;

    /** Description of the module. */
    private String description;

    /** Type of the module(check/filter/filefilter). */
    private ModuleType moduleType;

    /**
     * Get name of module.
     *
     * @return name of module
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of module.
     *
     * @param name module name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get fully qualified name of module.
     *
     * @return fully qualified name of module
     */
    public String getFullQualifiedName() {
        return fullQualifiedName;
    }

    /**
     * Set fully qualified name of module.
     *
     * @param fullQualifiedName fully qualified name of module
     */
    public void setFullQualifiedName(String fullQualifiedName) {
        this.fullQualifiedName = fullQualifiedName;
    }

    /**
     * Get parent of module.
     *
     * @return parent of module
     */
    public String getParent() {
        return parent;
    }

    /**
     * Set parent of module.
     *
     * @param parent parent of module
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     * Get description of module.
     *
     * @return description of module
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description of module.
     *
     * @param description description of module
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get property list of module.
     *
     * @return property list of module
     */
    public List<ModulePropertyDetails> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    /**
     * Add a single module property to the module's property list and map both.
     *
     * @param property module property
     */
    public void addToProperties(ModulePropertyDetails property) {
        properties.add(property);
    }

    /**
     * Add a list of properties to the module's property list and map both.
     *
     * @param modulePropertyDetailsList list of module property
     */
    public void addToProperties(List<ModulePropertyDetails> modulePropertyDetailsList) {
        properties.addAll(modulePropertyDetailsList);
    }

    /**
     * Get violation message keys of the module.
     *
     * @return violation message keys of module
     */
    public List<String> getViolationMessageKeys() {
        return Collections.unmodifiableList(violationMessageKeys);
    }

    /**
     * Add a key to the violation message key list of the module.
     *
     * @param msg violation message key
     */
    public void addToViolationMessages(String msg) {
        violationMessageKeys.add(msg);
    }

    /**
     * Add a list of keys to the violation message key list of the module.
     *
     * @param msgList a list of violation message keys
     */
    public void addToViolationMessages(List<String> msgList) {
        violationMessageKeys.addAll(msgList);
    }

    /**
     * Get module type.
     *
     * @return module type
     */
    public ModuleType getModuleType() {
        return moduleType;
    }

    /**
     * Set type of module.
     *
     * @param moduleType type of module
     */
    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }
}
