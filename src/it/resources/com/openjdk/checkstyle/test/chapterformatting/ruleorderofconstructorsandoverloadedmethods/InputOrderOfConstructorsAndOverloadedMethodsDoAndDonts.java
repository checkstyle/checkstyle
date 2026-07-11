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

public class InputOrderOfConstructorsAndOverloadedMethodsDoAndDonts {

    class Do {
        Do() {
            this(10);
        }

        Do(int capacity) {
            this(capacity, 10.0);
        }

        Do(int capacity, double loadFactor) {
        }
    }

    class Donts {

        void logValue(int i) {
            System.out.println(i);
        }

        void setValue(int i) {
            System.out.println(i);
        }

        // violation 2 lines below """All overloaded methods should be placed next
        //  to each other. Previous overloaded method located at line '44'."""
        void logValue(double d) {
            System.out.println(d);
        }

        // violation 2 lines below """All overloaded methods should be placed next
        //  to each other. Previous overloaded method located at line '48'."""
        void setValue(double d) {
            System.out.println(d);
        }
    }
}
