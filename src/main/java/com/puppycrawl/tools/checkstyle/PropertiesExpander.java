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

package com.puppycrawl.tools.checkstyle;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Resolves external properties from an
 * underlying {@code Properties} object.
 *
 */
public final class PropertiesExpander
    implements PropertyResolver {
    /** Var expression pattern, ie: ${config_loc}. */
    private static final Pattern VAR_EXPR_PATTERN = Pattern.compile("\\$\\{([^\\s}]+)}");

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
            String value = properties.getProperty(name);
            value = resolveValue(properties, name, value, new HashSet<>());
            values.put(name, value);
        }
    }

    private static String resolveValue(final Properties properties, final String name,
            final String value, final Set<String> resolveKeys) {
        String result = value;
        final Matcher matcher = VAR_EXPR_PATTERN.matcher(value);
        if (matcher.find()) {
            resolveKeys.add(name);
            final String key = matcher.group(1);
            String keyValue = properties.getProperty(key);
            if (keyValue != null) {
                if (!resolveKeys.contains(key)) {
                    keyValue = resolveValue(properties, key, keyValue, resolveKeys);
                }
                result = matcher.replaceFirst(Matcher.quoteReplacement(keyValue));
            }
        }
        return result;
    }

    @Override
    public String resolve(String name) {
        return values.get(name);
    }

}
