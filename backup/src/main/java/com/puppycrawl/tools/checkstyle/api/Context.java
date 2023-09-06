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

package com.puppycrawl.tools.checkstyle.api;

import java.util.Collection;

/**
 * A context to be used in subcomponents. The general idea of
 * Context/Contextualizable was taken from <a target="_top"
 * href="https://avalon.apache.org/closed.html">Jakarta's Avalon framework</a>.
 *
 * @see Contextualizable
 */
public interface Context {

    /**
     * Searches for the value with the specified attribute key in this context.
     *
     * @param key the attribute key.
     * @return the value in this context with the specified attribute key value.
     */
    Object get(String key);

    /**
     * Returns the names of all attributes of this context.
     *
     * @return the names of all attributes of this context.
     */
    Collection<String> getAttributeNames();

}
