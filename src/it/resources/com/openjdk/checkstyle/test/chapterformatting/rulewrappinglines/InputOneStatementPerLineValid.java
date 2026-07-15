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

package com.openjdk.checkstyle.test.chapterformatting.rulewrappinglines;

/**
 * Test input for valid one statement per line.
 */
public final class InputOneStatementPerLineValid {

    /** Dummy variable. */
    private int one = 0;

    /** Dummy variable. */
    private int two = 0;

    /**
     * Method with proper one statement per line.
     */
    public void method() {
        int num = 0;
        int count = 1;
        num++;
        count++;
    }

    /**
     * Legal method with separate lines.
     */
    public void doLegal() {
        one = 1;
        two = 2;
    }

    /**
     * String with format inside is legal.
     */
    public void doLegalString() {
        one = 1;
        two = 2;
        System.identityHashCode("one = 1; two = 2");
    }

    /**
     * Break and continue statements.
     */
    public void loopMethod() {
        int sum = 0;
        for (int i = 0; i < 2; i++) {
            sum += i;
            if (sum > 1) {
                break;
            }
        }

        while (one < 2) {
            one++;
        }

        do {
            two++;
        } while (two < 2);
    }
}
