///
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

package com.puppycrawl.tools.checkstyle.api;

/**
 * A Component that needs context information from its container(parent object) to work.
 * The container will create a Context object and pass it to this
 * Contextualizable. Contextualization will occur before configuration.
 * Note: Configuring of object mean copy user defined properties to object.
 * Contextualizing is inheriting some properties from parent that are provided by user
 * to parent object or created by parent object.
 * The general idea of Context/Contextualizable was taken from <a target="_top"
 * href="https://avalon.apache.org/closed.html">Jakarta's Avalon framework</a>.
 */
@FunctionalInterface
public interface Contextualizable {

    /**
     * Sets the context for this Component.
     *
     * @param context the context.
     * @throws CheckstyleException if there is a contextualization error.
     */
    void contextualize(Context context) throws CheckstyleException;

}
