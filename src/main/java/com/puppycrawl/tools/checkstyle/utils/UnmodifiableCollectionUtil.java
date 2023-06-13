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

package com.puppycrawl.tools.checkstyle.utils;

import java.util.Collections;
import java.util.Set;

/**
 * Utility class for working with unmodifiable collections.
 *
 * <p>
 * This class provides a method to create an unmodifiable set using the JDK's
 * {@link Collections#unmodifiableSet(Set)} method. It is intended to be used
 * as a workaround for the Pitest error.
 * </p>
 *
 * <p>Note: This class does not introduce any new functionality,
 * it simply wraps the existing JDK method to provide a workaround for the Pitest error.
 * </p>
 */
public final class UnmodifiableCollectionUtil {

    /**
     * Private constructor for UnmodifiableCollectionUtil.
     *
     */
    private UnmodifiableCollectionUtil() {
    }

    /**
     * Creates an unmodifiable set based on the provided collection.
     *
     * @param collection the collection to create an unmodifiable set from
     * @param <T> the type of elements in the set
     * @return an unmodifiable set containing the elements from the provided collection
     */
    public static <T> Set<T> unmodifiableSet(Set<T> collection) {
        return Collections.unmodifiableSet(collection);
    }
}
