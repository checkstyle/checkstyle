////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Resolves external properties from an
 * underlying {@code Properties} object.
 *
 */
public final class PropertiesExpander
    implements PropertyResolver {

    /** The underlying values. */
    private final Map<String, String> values;

    /**
     * Creates a new PropertiesExpander.
     *
     * @param properties the underlying properties to use for
     *     property resolution.
     * @throws IllegalArgumentException when properties argument is null
     */
    public PropertiesExpander(Properties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("cannot pass null");
        }
        values = new HashMap<>(properties.size());
        for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements();) {
            final String name = (String) e.nextElement();
            values.put(name, properties.getProperty(name));
        }
    }

    @Override
    public String resolve(String name) {
        return values.get(name);
    }

}
