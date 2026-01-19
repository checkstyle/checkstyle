///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

import java.lang.ref.WeakReference;

import javax.annotation.Nullable;

/**
 * A wrapper class for {@link WeakReference} that provides a convenient way
 * to manage weak references to objects.
 *
 * <p>
 * This class encapsulates the creation and retrieval of weak references,
 * simplifying the common pattern of storing and accessing weakly referenced objects.
 * </p>
 *
 * @param <T> the type of the referenced object
 */
public final class WeakReferenceHolder<T> {

    /** The weak reference to the object. */
    private WeakReference<T> reference;

    /**
     * Constructs a new {@code WeakReferenceHolder} with no initial reference.
     */
    public WeakReferenceHolder() {
        reference = new WeakReference<>(null);
    }

    /**
     * Constructs a new {@code WeakReferenceHolder} with the specified object.
     *
     * @param object the object to hold a weak reference to
     */
    public WeakReferenceHolder(T object) {
        reference = new WeakReference<>(object);
    }

    /**
     * Returns the object held by this weak reference, or {@code null} if
     * the object has been garbage collected.
     *
     * @return the referenced object, or {@code null} if it has been collected
     */
    public T get() {
        return reference.get();
    }

    /**
     * Updates the referenced object only if the new object is different
     * from the currently referenced object. After updating, runs the
     * specified callback if provided.
     *
     * @param newObject the new object to reference; may be {@code null}
     * @param afterUpdate a callback to run after updating the reference; may be {@code null}
     */
    public void lazyUpdate(@Nullable T newObject, @Nullable Runnable afterUpdate) {
        final T previous = reference.get();
        if (previous != newObject) {
            reference = new WeakReference<>(newObject);
            if (afterUpdate != null) {
                afterUpdate.run();
            }
        }
    }

}
