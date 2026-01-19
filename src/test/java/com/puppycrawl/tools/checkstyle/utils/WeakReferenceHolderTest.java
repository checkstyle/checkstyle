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

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

public class WeakReferenceHolderTest {

    @Test
    public void testDefaultConstructor() {
        final WeakReferenceHolder<String> holder = new WeakReferenceHolder<>();
        assertWithMessage("Default constructor should hold null reference")
                .that(holder.get())
                .isNull();
    }

    @Test
    public void testConstructorWithObject() {
        final String testObject = "test";
        final WeakReferenceHolder<String> holder = new WeakReferenceHolder<>(testObject);
        assertWithMessage("Constructor with object should hold the object")
                .that(holder.get())
                .isEqualTo(testObject);
    }

    @Test
    public void testGetWithNonNullObject() {
        final Integer testObject = 42;
        final WeakReferenceHolder<Integer> holder = new WeakReferenceHolder<>(testObject);
        assertWithMessage("get() should return the referenced object")
                .that(holder.get())
                .isEqualTo(testObject);
    }

    @Test
    public void testGetWithNullObject() {
        final WeakReferenceHolder<String> holder = new WeakReferenceHolder<>(null);
        assertWithMessage("get() should return null for null reference")
                .that(holder.get())
                .isNull();
    }

    @Test
    public void testLazyUpdateWithDifferentObject() {
        final String initialObject = "initial";
        final String newObject = "new";
        final WeakReferenceHolder<String> holder = new WeakReferenceHolder<>(initialObject);

        holder.lazyUpdate(newObject, null);

        assertWithMessage("lazyUpdate should update reference to new object")
                .that(holder.get())
                .isEqualTo(newObject);
    }

    @Test
    public void testLazyUpdateWithSameObjectReference() {
        final String sameObject = "same";
        final WeakReferenceHolder<String> holder = new WeakReferenceHolder<>(sameObject);

        holder.lazyUpdate(sameObject, null);

        assertWithMessage("lazyUpdate should not change reference when object is the same")
                .that(holder.get())
                .isSameInstanceAs(sameObject);
    }

    @Test
    public void testLazyUpdateWithNull() {
        final String initialObject = "initial";
        final WeakReferenceHolder<String> holder = new WeakReferenceHolder<>(initialObject);

        holder.lazyUpdate(null, null);

        assertWithMessage("lazyUpdate should update reference to null")
                .that(holder.get())
                .isNull();
    }

    @Test
    public void testLazyUpdateFromNullToObject() {
        final String newObject = "new";
        final WeakReferenceHolder<String> holder = new WeakReferenceHolder<>();

        holder.lazyUpdate(newObject, null);

        assertWithMessage("lazyUpdate should update reference from null to object")
                .that(holder.get())
                .isEqualTo(newObject);
    }

    @Test
    public void testLazyUpdateWithCallback() {
        final String initialObject = "initial";
        final String newObject = "new";
        final WeakReferenceHolder<String> holder = new WeakReferenceHolder<>(initialObject);
        final boolean[] callbackCalled = {false};

        holder.lazyUpdate(newObject, () -> callbackCalled[0] = true);

        assertWithMessage("lazyUpdate should call callback when object changes")
                .that(callbackCalled[0])
                .isTrue();
        assertWithMessage("lazyUpdate should update reference")
                .that(holder.get())
                .isEqualTo(newObject);
    }

    @Test
    public void testLazyUpdateWithCallbackNotCalledWhenSameObject() {
        final String sameObject = "same";
        final WeakReferenceHolder<String> holder = new WeakReferenceHolder<>(sameObject);
        final boolean[] callbackCalled = {false};

        holder.lazyUpdate(sameObject, () -> callbackCalled[0] = true);

        assertWithMessage("lazyUpdate should not call callback when object is the same")
                .that(callbackCalled[0])
                .isFalse();
    }

    @Test
    public void testLazyUpdateWithNullCallback() {
        final String initialObject = "initial";
        final String newObject = "new";
        final WeakReferenceHolder<String> holder = new WeakReferenceHolder<>(initialObject);

        holder.lazyUpdate(newObject, null);

        assertWithMessage("lazyUpdate should work with null callback")
                .that(holder.get())
                .isEqualTo(newObject);
    }

    @Test
    public void testLazyUpdateWithEqualButDifferentObjects() {
        final Object object1 = new Object();
        final Object object2 = new Object();
        final WeakReferenceHolder<Object> holder = new WeakReferenceHolder<>(object1);
        final boolean[] callbackCalled = {false};

        holder.lazyUpdate(object2, () -> callbackCalled[0] = true);

        assertWithMessage("lazyUpdate should call callback for equal but different objects")
                .that(callbackCalled[0])
                .isTrue();
        assertWithMessage("lazyUpdate should update to new object reference")
                .that(holder.get())
                .isSameInstanceAs(object2);
    }

    @Test
    public void testLazyUpdateWithNullToNull() {
        final WeakReferenceHolder<String> holder = new WeakReferenceHolder<>(null);
        final boolean[] callbackCalled = {false};

        holder.lazyUpdate(null, () -> callbackCalled[0] = true);

        assertWithMessage("lazyUpdate should not call callback when updating null to null")
                .that(callbackCalled[0])
                .isFalse();
        assertWithMessage("Reference should remain null")
                .that(holder.get())
                .isNull();
    }

    @Test
    public void testMultipleUpdates() {
        final WeakReferenceHolder<Integer> holder = new WeakReferenceHolder<>(1);
        final int[] updateCount = {0};

        holder.lazyUpdate(2, () -> updateCount[0]++);
        holder.lazyUpdate(3, () -> updateCount[0]++);
        holder.lazyUpdate(3, () -> updateCount[0]++);
        holder.lazyUpdate(4, () -> updateCount[0]++);

        assertWithMessage("Callback should be called for each unique update")
                .that(updateCount[0])
                .isEqualTo(3);
        assertWithMessage("Final value should be the last updated value")
                .that(holder.get())
                .isEqualTo(4);
    }

}
