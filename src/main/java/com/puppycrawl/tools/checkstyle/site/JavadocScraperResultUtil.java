///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.site;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class with result data of ClassAndPropertiesSettersJavadocScraper.
 */
public final class JavadocScraperResultUtil {

    /**
     * Map of scraped properties details - name of property, property details object.
     */
    private static final Map<String, PropertyDetails> PROPERTIES_DETAILS =
             new LinkedHashMap<>();

    /**
     * The since version of the module.
     */
    private static String moduleSinceVersion = "";

    /**
     * The description of the module.
     */
    private static String moduleDescription = "";

    /**
     * The notes of the module.
     */
    private static String moduleNotes = "";

    /**
     * Private utility constructor.
     */
    private JavadocScraperResultUtil() {
    }

    /**
     * Resets the fields.
     */
    public static void clearData() {
        PROPERTIES_DETAILS.clear();
        moduleSinceVersion = "";
        moduleDescription = "";
        moduleNotes = "";
    }

    /**
     * Get the properties details map.
     *
     * @return the details map.
     */
    public static Map<String, PropertyDetails> getPropertiesDetails() {
        return Collections.unmodifiableMap(PROPERTIES_DETAILS);
    }

    /**
     * Get the module's since version.
     *
     * @return the module's since version.
     */
    public static String getModuleSinceVersion() {
        return moduleSinceVersion;
    }

    /**
     * Sets the module's since version.
     *
     * @param sinceVersion module's since version.
     */
    /* package */ static void setModuleSinceVersion(String sinceVersion) {
        moduleSinceVersion = sinceVersion;
    }

    /**
     * Get the module's description.
     *
     * @return the module's description.
     */
    public static String getModuleDescription() {
        return moduleDescription;
    }

    /**
     * Sets the module's description.
     *
     * @param description module's description.
     */
    /* package */ static void setModuleDescription(String description) {
        moduleDescription = description;
    }

    /**
     * Get the module's notes.
     *
     * @return the module's notes.
     */
    public static String getModuleNotes() {
        return moduleNotes;
    }

    /**
     * Sets the module's notes.
     *
     * @param notes module's notes.
     */
    /* package */ static void setModuleNotes(String notes) {
        moduleNotes = notes;
    }

    /**
     * Sets additional property details to property map.
     *
     * @param propertyName name of property.
     * @param details property's details.
     */
    /* package */ static void putPropertyDetails(String propertyName,
                                                 PropertyDetails details) {
        PROPERTIES_DETAILS.put(propertyName, details);
    }

}
