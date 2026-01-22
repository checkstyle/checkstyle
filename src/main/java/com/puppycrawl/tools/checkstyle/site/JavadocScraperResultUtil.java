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

import com.puppycrawl.tools.checkstyle.api.DetailNode;

/**
 * Class with result data of ClassAndPropertiesSettersJavadocScraper.
 */
public final class JavadocScraperResultUtil {
    /**
     * Map of scraped properties javadocs - name of property, javadoc detail node.
     */
    private static final Map<String, DetailNode> PROPERTIES_JAVADOC_NODES =
            new LinkedHashMap<>();

    /**
     * The javadoc of class.
     *
     * @noinspection - StaticVariableMayNotBeInitialized
     * @noinspectionreason StaticVariableMayNotBeInitialized -
     *      no initial initialization value available except null
     */
    private static DetailNode moduleJavadocNode;

    /**
     * Private utility constructor.
     */
    private JavadocScraperResultUtil() {
    }

    /**
     * Resets the fields.
     */
    /* package */ static void clearData() {
        PROPERTIES_JAVADOC_NODES.clear();
        moduleJavadocNode = null;
    }

    /**
     * Get the properties javadocs map.
     *
     * @return the javadocs.
     */
    public static Map<String, DetailNode> getPropertiesJavadocNode() {
        return Collections.unmodifiableMap(PROPERTIES_JAVADOC_NODES);
    }

    /**
     * Get the module javadoc.
     *
     * @noinspection - StaticVariableUsedBeforeInitialization
     * @noinspectionreason StaticVariableUsedBeforeInitialization -
     *      no initial initialization value available
     * @return the module's javadoc.
     */
    public static DetailNode getModuleJavadocNode() {
        return moduleJavadocNode;
    }

    /**
     * Sets the module javadoc.
     *
     * @param moduleJavadoc module's javadoc.
     */
    /* package */ static void setModuleJavadocNode(DetailNode moduleJavadoc) {
        moduleJavadocNode = moduleJavadoc;
    }

    /**
     * Sets additional property javadoc to property map.
     *
     * @param propertyName name of property.
     * @param propertyJavadoc property's javadoc.
     */
    /* package */ static void putPropertyJavadocNode(String propertyName,
                                                     DetailNode propertyJavadoc) {
        PROPERTIES_JAVADOC_NODES.put(propertyName, propertyJavadoc);
    }
}
