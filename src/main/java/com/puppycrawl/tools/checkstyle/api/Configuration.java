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

package com.puppycrawl.tools.checkstyle.api;

import java.io.Serializable;
import java.util.Map;

/**
 * A Configuration is used to configure a Configurable component.  The general
 * idea of Configuration/Configurable was taken from <a target="_top"
 * href="http://avalon.apache.org/closed.html">Jakarta's Avalon framework</a>.
 */
public interface Configuration extends Serializable {

    /**
     * The set of attribute names.
     *
     * @return The set of attribute names, never null.
     */
    String[] getAttributeNames();

    /**
     * The attribute value for an attribute name.
     *
     * @param name the attribute name
     * @return the value that is associated with name
     * @throws CheckstyleException if name is not a valid attribute name
     */
    String getAttribute(String name) throws CheckstyleException;

    /**
     * The set of child configurations.
     *
     * @return The set of child configurations, never null.
     */
    Configuration[] getChildren();

    /**
     * The name of this configuration.
     *
     * @return The name of this configuration.
     */
    String getName();

    /**
     * Returns an unmodifiable map instance containing the custom messages
     * for this configuration.
     *
     * @return unmodifiable map containing custom messages
     */
    Map<String, String> getMessages();

    /**
     * Returns <tt>true</tt> if its attribute contains a mapping for the specified key.
     *
     * @param key key whose presence in its attribute is to be tested
     * @return <tt>true</tt> if its attribute contains a mapping for the specified key.
     */
    boolean containsAttribute(String key);

    /**
     * Returns <tt>true</tt> if its messages contains a mapping for the specified key.
     *
     * @param key key whose presence in its messages is to be tested
     * @return <tt>true</tt> if its messages contains a mapping for the specified key.
     */
    boolean containsMessage(String key);

    /**
     * Returns the value to which the specified key is mapped in messages,
     * or {@code null} if messages contains no mapping for the key.
     *
     * @param key key whose presence in its messages is to be tested
     * @return the value to which the specified key is mapped, or
     *         {@code null} if this map contains no mapping for the key
     */
    String getMessage(String key);
}
