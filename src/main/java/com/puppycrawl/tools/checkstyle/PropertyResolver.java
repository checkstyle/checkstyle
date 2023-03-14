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

package com.puppycrawl.tools.checkstyle;

/**
 * Resolves properties in module configurations.
 *
 * <p>The {@link ConfigurationLoader} uses a PropertyResolver to
 * resolve the values of external properties like {@code ${basename}}
 * that occur in the configuration file.
 *
 * @see ConfigurationLoader
 */
@FunctionalInterface
public interface PropertyResolver {

    /**
     * Resolves a property name to its value.
     *
     * @param name the name of the property.
     * @return the value that is associated with {@code propertyName}.
     */
    String resolve(String name);

}
