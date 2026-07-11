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

public class InputOrderOfConstructorsAndOverloadedMethodsOne {
    int l;

    InputOrderOfConstructorsAndOverloadedMethodsOne() {}

    InputOrderOfConstructorsAndOverloadedMethodsOne(String s, int x, int y) {}

    // comments between constructors are allowed.
    InputOrderOfConstructorsAndOverloadedMethodsOne(int x) {}
    // violation above 'Constructors should be ordered by increasing parameter count.'

    int a = 0; // violation, Field declaration is in wrong order

    // violation 2 lines below """Constructors should be grouped together.
    // The last grouped constructor is declared at line '35'."""
    InputOrderOfConstructorsAndOverloadedMethodsOne(String s, int x) {}
    // violation above 'Constructors should be ordered by increasing parameter count.'

    private enum ExampleEnum {

        ONE, TWO, THREE;

        ExampleEnum() {}

        void foo() {}

        // violation 2 lines below """Constructors should be grouped together.
        // The last grouped constructor is declared at line '49'."""
        ExampleEnum(int x, int y) {}
        // violation above 'Constructor definition in wrong order.'

        // violation 2 lines below """Constructors should be grouped together.
        // The last grouped constructor is declared at line '49'."""
        ExampleEnum(String s, int x) {}
        // violation above 'Constructor definition in wrong order.'
    }

    class InputWithOrderedCtors {
        InputWithOrderedCtors() {}

        InputWithOrderedCtors(String s) {}

        InputWithOrderedCtors(int x) {}

        InputWithOrderedCtors(String s, int x) {}
    }
}
