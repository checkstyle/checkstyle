////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.utils;

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
    public static final String UNDEFINED_PROPERTY_MESSAGE = "Property not defined: ";

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
     * Resolves all chained properties within a properties file.
     *
     * @param properties the underlying properties to use
     *                   for property resolution.
     * @return resolved properties
     * @throws CheckstyleException when chained property is not defined
     */
    public static Properties getResolvedProperties(Properties properties)
            throws CheckstyleException {
        final Set<String> unresolvedPropertyNames = properties.stringPropertyNames();
        final Properties resolvedProperties = new Properties();
        while (!unresolvedPropertyNames.isEmpty()) {
            resolveChainedProperties(unresolvedPropertyNames, properties, resolvedProperties);
        }
        return resolvedProperties;
    }

    /**
     * Resolves chained property expressions.
     *
     * @param unresolvedPropertyNames set of property names to resolve
     * @param properties the underlying properties to use
     *                   for property resolution.
     * @param resolvedProperties properties that are fully resolved
     * @throws CheckstyleException when chained property is not defined
     */
    private static void resolveChainedProperties(Set<String> unresolvedPropertyNames,
                                          Properties properties,
                                          Properties resolvedProperties)
            throws CheckstyleException {

        for (String propertyName : unresolvedPropertyNames) {
            String property = properties.getProperty(propertyName);
            final Matcher matcher = PROPERTY_VARIABLE_PATTERN.matcher(property);
            while (matcher.find()) {
                final String propertyVariableExpression = matcher.group();
                final String unresolvedProperty =
                    getPropertyNameFromExpression(propertyVariableExpression);

                if (properties.getProperty(unresolvedProperty) == null) {
                    throw new CheckstyleException(UNDEFINED_PROPERTY_MESSAGE + unresolvedProperty);
                }

                if (resolvedProperties.getProperty(unresolvedProperty) != null) {
                    final String resolvedPropertyValue =
                        resolvedProperties.getProperty(unresolvedProperty);
                    property = property.replace(propertyVariableExpression, resolvedPropertyValue);
                }
            }

            if (allChainedPropertiesAreResolved(property)) {
                resolvedProperties.setProperty(propertyName, property);
            }
        }
        unresolvedPropertyNames.removeAll(resolvedProperties.stringPropertyNames());
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
