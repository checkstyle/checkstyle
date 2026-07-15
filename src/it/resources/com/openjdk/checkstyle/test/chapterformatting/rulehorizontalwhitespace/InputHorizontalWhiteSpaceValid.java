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

/** Valid input class for whitespace. */
public class InputHorizontalWhiteSpaceValid {
    int y = 0;
    int a = 4;

    /** Test Method. */
    void example() {
        int b = 0;
        switch (b) {
            case 1: {
                System.out.println("hello");
                break;
            }
            default: {
                break;
            }
        }

        Runnable noop = () -> {};

        try {
        } catch (Exception e) {}

        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        for (char item : vowels) {}

        for (int i = 100; i > 10; i--) {}

        if (a < b) {
            System.out.println("True");
        }

        int w = 4;
    }

    /** Test record. */
    record MyRecord3() {
        void method() {
            final int a = 1;
            int b = 1;
            b = 1;
        }
    }

    /** Test Method. */
    void method() {
        outerLoop:
        for (int i = 0; i < 10; i++) {
            int j = 0;
            while (j == 0) {
                break outerLoop;
            }
            j++;
        }
    }
}
