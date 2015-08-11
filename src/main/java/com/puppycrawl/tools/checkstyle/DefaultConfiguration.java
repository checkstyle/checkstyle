////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

/**
 * Default implementation of the Configuration interface.
 * @author lkuehne
 */
public final class DefaultConfiguration implements Configuration {
    /** Required for serialization. */
    private static final long serialVersionUID = 1157875385356127169L;

    /** The name of this configuration */
    private final String name;

    /** the list of child Configurations */
    private final List<Configuration> children = Lists.newArrayList();

    /** the map from attribute names to attribute values */
    private final Map<String, String> attributeMap = Maps.newHashMap();

    /** the map containing custom messages. */
    private final Map<String, String> messages = Maps.newHashMap();

    /**
     * Instantiates a DefaultConfiguration.
     * @param name the name for this DefaultConfiguration.
     */
    public DefaultConfiguration(String name) {
        this.name = name;
    }

    @Override
    public String[] getAttributeNames() {
        final Set<String> keySet = attributeMap.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }

    @Override
    public String getAttribute(String attributeName) throws CheckstyleException {
        if (!attributeMap.containsKey(attributeName)) {
            throw new CheckstyleException(
                    "missing key '" + attributeName + "' in " + getName());
        }
        return attributeMap.get(attributeName);
    }

    @Override
    public Configuration[] getChildren() {
        return children.toArray(
            new Configuration[children.size()]);
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Makes a configuration a child of this configuration.
     * @param configuration the child configuration.
     */
    public void addChild(Configuration configuration) {
        children.add(configuration);
    }

    /**
     * Removes a child of this configuration.
     * @param configuration the child configuration to remove.
     */
    public void removeChild(final Configuration configuration) {
        children.remove(configuration);
    }

    /**
     * Adds an attribute to this configuration.
     * @param attributeName the name of the attribute.
     * @param value the value of the attribute.
     */
    public void addAttribute(String attributeName, String value) {
        final String current = attributeMap.put(attributeName, value);
        if (current == null) {
            attributeMap.put(attributeName, value);
        }
        else {
            attributeMap.put(attributeName, current + "," + value);
        }
    }

    /**
     * Adds a custom message to this configuration.
     * @param key the message key
     * @param value the custom message pattern
     */
    public void addMessage(String key, String value) {
        messages.put(key, value);
    }

    /**
     * Returns an unmodifiable map instance containing the custom messages
     * for this configuration.
     * @return unmodifiable map containing custom messages
     */
    @Override
    public ImmutableMap<String, String> getMessages() {
        return ImmutableMap.copyOf(messages);
    }
}
