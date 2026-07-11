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

package com.openjdk.checkstyle.test.chapterformatting.rulehorizontalwhitespace;

/**
 * Dummy Test class.
 */
public class InputHorizontalWhiteSpaceInvalidThree {

    long a = 0;

    // violation below ''typecast' is not followed by whitespace.'
    int b = (int)a;

    /**
     * Dummy Test method.
     */
    public void test(int x,int y) { // violation '',' is not followed by whitespace.'
    }

    /**
     * Dummy Test method.
     */
    public void method(int x,int y,int z) {
        // 2 violations above:
        // '',' is not followed by whitespace.'
        // '',' is not followed by whitespace.'

        for (int i = 0;i <= 9;i++) {
        // 2 violations above:
        // '';' is not followed by whitespace.'
        // '';' is not followed by whitespace.'
        }
    }

    /**
     * Dummy Test inner class.
     */
    class Inner {
        Inner(int e,int f) {} // violation 'not followed by whitespace'

        Inner(int e,int f,int g) {}
        // 2 violations above:
        // '',' is not followed by whitespace.'
        // '',' is not followed by whitespace.'
    }
}
