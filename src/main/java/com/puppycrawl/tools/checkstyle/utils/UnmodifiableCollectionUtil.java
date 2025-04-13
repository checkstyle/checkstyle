////
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
///

package com.puppycrawl.tools.checkstyle.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <div>Note: it simply wraps the existing JDK methods to provide a workaround
 * for Pitest survival on mutation for removal of immutable wrapping,
 * see #13127 for more details.
 * </div>
 *
 */
public final class UnmodifiableCollectionUtil {

    /**
     * Private constructor for UnmodifiableCollectionUtil.
     *
     */
    private UnmodifiableCollectionUtil() {
    }

    /**
     * Creates an unmodifiable list based on the provided collection.
     *
     * @param collection the collection to create an unmodifiable list from
     * @param <T> the type of elements in the set
     * @return an unmodifiable list containing the elements from the provided collection
     */
    public static <T> List<T> unmodifiableList(List<T> collection) {
        return Collections.unmodifiableList(collection);
    }

    /**
     * Returns an unmodifiable view of a List containing elements of a specific type.
     *
     * @param items The List of items to make unmodifiable.
     * @param elementType The Class object representing the type of elements in the list.
     * @param <S> The generic type of elements in the input Collection.
     * @param <T> The type of elements in the resulting unmodifiable List.
     * @return An unmodifiable List containing elements of the specified type.
     */
    public static <S, T> List<T> unmodifiableList(Collection<S> items, Class<T> elementType) {
        return items.stream()
            .map(elementType::cast)
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Creates a copy of array.
     *
     * @param array Array to create a copy of
     * @param length length of array
     * @param <T> The type of array
     * @return copy of array
     */
    public static <T> T[] copyOfArray(T[] array, int length) {
        return Arrays.copyOf(array, length);
    }

    /**
     * Creates a copy of Map.
     *
     * @param map map to create a copy of
     * @param <K> the type of keys in the map
     * @param <V> the type of values in the map
     * @return an immutable copy of the input map
     */
    public static <K, V> Map<K, V> copyOfMap(Map<? extends K, ? extends V> map) {
        return Map.copyOf(map);
    }

    /**
     * Returns an immutable set containing only the specified object.
     *
     * @param obj the type of object in the set
     * @param <T> the type of object
     * @return immutable set
     */
    public static <T> Set<T> singleton(T obj) {
        return Collections.singleton(obj);
    }
}
