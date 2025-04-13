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

/** Simple POJO class module's property details. */
public final class ModulePropertyDetails {

    /** Name of property. */
    private String name;

    /** Type of property. */
    private String type;

    /** Default value of property. */
    private String defaultValue;

    /**
     * This property is java type that plugins can use to validate user input, it is used when
     * 'type' field is "String". It is used for special cases such as regexp and tokenSet.
     */
    private String validationType;

    /** Description of property. */
    private String description;

    /**
     * Get name of property.
     *
     * @return name of property
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of property.
     *
     * @param name name of property
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get type of property.
     *
     * @return property type
     */
    public String getType() {
        return type;
    }

    /**
     * Set property type.
     *
     * @param type property type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get default value of property.
     *
     * @return default value of property
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Set default value of property.
     *
     * @param defaultValue default value of property
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Get validation type of property.
     *
     * @return validation type of property
     */
    public String getValidationType() {
        return validationType;
    }

    /**
     * Set validation type of property.
     *
     * @param validationType validation type of property
     */
    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }

    /**
     * Get description of property.
     *
     * @return property description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description of property.
     *
     * @param description property description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
