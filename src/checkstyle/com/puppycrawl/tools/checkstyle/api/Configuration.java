////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import com.google.common.collect.ImmutableMap;

import java.io.Serializable;


/**
 * A Configuration is used to configure a Configurable component.  The general
 * idea of Configuration/Configurable was taken from <a target="_top"
 * href="http://jakarta.apache.org/avalon/">Jakarta's Avalon framework</a>.
 * @author lkuehne
 */
public interface Configuration extends Serializable
{
    /**
     * The set of attribute names.
     * @return The set of attribute names, never null.
     */
    String[] getAttributeNames();

    /**
     * The attribute value for an attribute name.
     * @param aName the attribute name
     * @return the value that is associated with aName
     * @throws CheckstyleException if aName is not a valid attribute name
     */
    String getAttribute(String aName) throws CheckstyleException;

    /**
     * The set of child configurations.
     * @return The set of child configurations, never null.
     */
    Configuration[] getChildren();

    /**
     * The name of this configuration.
     * @return The name of this configuration.
     */
    String getName();

    /**
     * Returns an unmodifiable map instance containing the custom messages
     * for this configuration.
     * @return unmodifiable map containing custom messages
     */
    ImmutableMap<String, String> getMessages();
}
