////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;

/**
 * This enum represents access modifiers.
 * Access modifiers names are taken from JLS:
 * https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.6
 *
 * @author Andrei Selkin
 */
public enum AccessModifier {
    /** Public access modifier. */
    PUBLIC,
    /** Protected access modifier. */
    PROTECTED,
    /** Package access modifier. */
    PACKAGE,
    /** Private access modifier. */
    PRIVATE;

    @Override
    public String toString() {
        return getName();
    }

    private String getName() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    /**
     * Retrieves the converter that converts strings to AccessModifier.
     * This implementation does not care whether the array elements contain characters like '_'.
     * The normal {@link ArrayConverter} class has problems with this character.
     * @return the converter.
     */
    public static Converter getBeanConverter() {
        return new Converter() {
            @SuppressWarnings({"unchecked", "rawtypes"})
            @Override
            public Object convert(Class type, Object value) {
                // Converts to a String and trims it for the tokenizer.
                final StringTokenizer tokenizer = new StringTokenizer(
                    value.toString().trim(), ",");
                final List<AccessModifier> result = new ArrayList<>();

                while (tokenizer.hasMoreTokens()) {
                    final String token = tokenizer.nextToken();
                    result.add(getInstance(token.trim()));
                }

                return result.toArray(new AccessModifier[result.size()]);
            }
        };
    }

    /**
     * Factory method which returns an AccessModifier instance that corresponds to the
     * given access modifier name represented as a {@link String}.
     * The access modifier name can be formatted both as lower case or upper case string.
     * For example, passing PACKAGE or package as a modifier name
     * will return {@link AccessModifier#PACKAGE}.
     *
     * @param modifierName access modifier name represented as a {@link String}.
     * @return the AccessModifier associated with given access modifier name.
     */
    private static AccessModifier getInstance(String modifierName) {
        return valueOf(AccessModifier.class, modifierName.trim().toUpperCase(Locale.ENGLISH));
    }
}
