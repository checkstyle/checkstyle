/*
 * Copyright (c) XXXX, YYYY, Some company and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details.
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact
 * or visit www.example.com if you need additional information or have any
 * questions.
 */

package com.openjdk.checkstyle.test.chapterformatting.ruleorderofconstructorsandoverloadedmethods;

public class InputOrderOfConstructorsAndOverloadedMethodsTwo {

    void same(int i) {}

    void same(String s, int i, int k) {}
    // comments between overloaded methods are allowed.

    void same(String s) {}
    // violation above, 'Overloaded methods should be ordered by increasing parameter count.'

    void same(int i, String s) {}
    // violation above, 'Overloaded methods should be ordered by increasing parameter count.'

    void notSame() {}

    interface NotSame{}

    void otherSame(String s) {}

    void foo() {}

    // violation 2 lines below """All overloaded methods should be placed next
    //  to each other. Previous overloaded method located at line '44'."""
    void otherSame(String s, int i) {}

    public enum ExampleEnum {
        VALUE_ONE,
        VALUE_TWO;

        public void example() {}

        public void example(String s, int i) {}

        void foo() {}

        // violation 2 lines below """All overloaded methods should be placed next
        //  to each other. Previous overloaded method located at line '58'."""
        public void example(String s) {}
        // violation above, 'Overloaded methods should be ordered by increasing parameter count.'
    }
}
