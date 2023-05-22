///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Resolves chained properties from a user-defined property file.
 */
public final class ChainedPropertyUtil {

    /**
     * Used to report undefined property in exception message.
     */
    public static final String UNDEFINED_PROPERTY_MESSAGE = "Undefined property: ";

    /**
     * Property variable expression pattern, matches property variables such as {@code ${basedir}}.
     */
    private static final Pattern PROPERTY_VARIABLE_PATTERN = Pattern.compile("\\$\\{([^\\s}]+)}");

    /**
     * Prevent instantiation.
     */
    private ChainedPropertyUtil() {
    }

    /**
     * Accepts user defined properties and returns new properties
     * with all chained properties resolved.
     *
     * @param properties the underlying properties to use
     *                   for property resolution.
     * @return resolved properties
     * @throws CheckstyleException when chained property is not defined
     */
    public static Properties getResolvedProperties(Properties properties)
            throws CheckstyleException {
        final Set<String> unresolvedPropertyNames =
            new HashSet<>(properties.stringPropertyNames());
        Iterator<String> unresolvedPropertyIterator = unresolvedPropertyNames.iterator();
        final Map<Object, Object> comparisonProperties = new Properties();

        while (unresolvedPropertyIterator.hasNext()) {
            final String propertyName = unresolvedPropertyIterator.next();
            String propertyValue = properties.getProperty(propertyName);
            final Matcher matcher = PROPERTY_VARIABLE_PATTERN.matcher(propertyValue);

            while (matcher.find()) {
                final String propertyVariableExpression = matcher.group();
                final String unresolvedPropertyName =
                    getPropertyNameFromExpression(propertyVariableExpression);

                final String resolvedPropertyValue =
                    properties.getProperty(unresolvedPropertyName);

                if (resolvedPropertyValue != null) {
                    propertyValue = propertyValue.replace(propertyVariableExpression,
                        resolvedPropertyValue);
                    properties.setProperty(propertyName, propertyValue);
                }
            }

            if (allChainedPropertiesAreResolved(propertyValue)) {
                unresolvedPropertyIterator.remove();
            }

            if (!unresolvedPropertyIterator.hasNext()) {

                if (comparisonProperties.equals(properties)) {
                    // At this point, we will have not resolved any properties in two iterations,
                    // so unresolvable properties exist.
                    throw new CheckstyleException(UNDEFINED_PROPERTY_MESSAGE
                        + unresolvedPropertyNames);
                }
                comparisonProperties.putAll(properties);
                unresolvedPropertyIterator = unresolvedPropertyNames.iterator();
            }

        }
        return properties;
    }

    /**
     * Gets an unresolved property name from a property variable expression
     * by stripping the preceding '${' and trailing '}'.
     *
     * @param variableExpression the full property variable expression
     * @return property name
     */
    private static String getPropertyNameFromExpression(String variableExpression) {
        final int propertyStartIndex = variableExpression.lastIndexOf('{') + 1;
        final int propertyEndIndex = variableExpression.lastIndexOf('}');
        return variableExpression.substring(propertyStartIndex, propertyEndIndex);
    }

    /**
     * Checks if all chained properties have been resolved. Essentially,
     * this means that there exist no matches for PROPERTY_VARIABLE_PATTERN in the
     * property value string.
     *
     * @param propertyValue the property value to check
     * @return true if all chained properties are resolved
     */
    private static boolean allChainedPropertiesAreResolved(String propertyValue) {
        return !PROPERTY_VARIABLE_PATTERN.matcher(propertyValue).find();
    }
}
