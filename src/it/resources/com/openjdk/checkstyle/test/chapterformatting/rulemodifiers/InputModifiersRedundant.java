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

package com.openjdk.checkstyle.test.chapterformatting.rulemodifiers;

public class InputModifiersRedundant {

    void test() {
        // violation below, 'Redundant 'final' modifier'
        try (final var a = lock()) {

        } catch (Exception e) {

        }
    }

    // violation below, 'Redundant 'abstract' modifier'
    abstract interface I {

        public int TEMP = 0; // violation, 'Redundant 'public' modifier'

        public abstract void method();
        // 2 violations above:
        //    'Redundant 'public' modifier'
        //    'Redundant 'abstract' modifier'
    }

    static enum E { // violation, 'Redundant 'static' modifier'
        A, B, C
    }

    // violation below, 'Redundant 'strictfp' modifier'
    public strictfp class Test { }

    AutoCloseable lock() {
        return null;
    }

}
