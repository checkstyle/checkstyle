///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Default implementation of the Configuration interface.
 */
public final class DefaultConfiguration implements Configuration {

    /** A unique serial version identifier. */
    private static final long serialVersionUID = 1157875385356127169L;

    /** Constant for optimization. */
    private static final Configuration[] EMPTY_CONFIGURATION_ARRAY = new Configuration[0];

    /** The name of this configuration. */
    private final String name;

    /** The list of child Configurations. */
    private final List<@NonNull Configuration> children = new ArrayList<>();

    /** The map from property names to property values. */
    private final Map<String, String> propertyMap = new HashMap<>();

    /** The map containing custom messages. */
    private final Map<String, String> messages = new HashMap<>();

    /** The thread mode configuration. */
    private final ThreadModeSettings threadModeSettings;

    /**
     * Instantiates a DefaultConfiguration.
     *
     * @param name the name for this DefaultConfiguration.
     */
    public DefaultConfiguration(String name) {
        this(name, ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE);
    }

    /**
     * Instantiates a DefaultConfiguration.
     *
     * @param name the name for this DefaultConfiguration.
     * @param threadModeSettings the thread mode configuration.
     */
    public DefaultConfiguration(String name,
        ThreadModeSettings threadModeSettings) {
        this.name = name;
        this.threadModeSettings = threadModeSettings;
    }

    @Override
    public String[] getAttributeNames() {
        return getPropertyNames();
    }

    @Override
    public String getAttribute(String attributeName) throws CheckstyleException {
        return getProperty(attributeName);
    }

    @Override
    public String[] getPropertyNames() {
        final Set<String> keySet = propertyMap.keySet();
        return keySet.toArray(CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Override
    public String getProperty(String propertyName) throws CheckstyleException {
        if (!propertyMap.containsKey(propertyName)) {
            throw new CheckstyleException(
                    "missing key '" + propertyName + "' in " + name);
        }
        return propertyMap.get(propertyName);
    }

    @Override
    public Configuration[] getChildren() {
        return children.toArray(
                EMPTY_CONFIGURATION_ARRAY);
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Makes a configuration a child of this configuration.
     *
     * @param configuration the child configuration.
     */
    public void addChild(Configuration configuration) {
        children.add(configuration);
    }

    /**
     * Removes a child of this configuration.
     *
     * @param configuration the child configuration to remove.
     */
    public void removeChild(final Configuration configuration) {
        children.remove(configuration);
    }

    /**
     * Adds n property to this configuration.
     *
     * @param attributeName the name of the property.
     * @param value the value of the property.
     * @deprecated This shall be removed in future releases. Please use
     *      {@code addProperty(String propertyName, String value)} instead.
     */
    @Deprecated(since = "8.45")
    public void addAttribute(String attributeName, String value) {
        addProperty(attributeName, value);
    }

    /**
     * Adds n property to this configuration.
     *
     * @param propertyName the name of the property.
     * @param value the value of the property.
     */
    public void addProperty(String propertyName, String value) {
        final String current = propertyMap.get(propertyName);
        final String newValue;
        if (current == null) {
            newValue = value;
        }
        else {
            newValue = current + "," + value;
        }
        propertyMap.put(propertyName, newValue);
    }

    /**
     * Adds a custom message to this configuration.
     *
     * @param key the message key
     * @param value the custom message pattern
     */
    public void addMessage(String key, String value) {
        messages.put(key, value);
    }

    /**
     * Returns an unmodifiable map instance containing the custom messages
     * for this configuration.
     *
     * @return unmodifiable map containing custom messages
     */
    @Override
    public Map<String, String> getMessages() {
        return new HashMap<>(messages);
    }

    /**
     * Gets the thread mode configuration.
     *
     * @return the thread mode configuration.
     */
    public ThreadModeSettings getThreadModeSettings() {
        return threadModeSettings;
    }

}
