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
 * Test input for one statement per line in for loop.
 */
public final class InputOneStatementPerLineForLoop {

    /**
     * Method with for loop - multiple statements in header are allowed.
     */
    public void method() {
        int sum = 0;
        for (int i = 0, j = 0; i < 2; i++, j++) {
            sum += i + j;
        }
    }

    /**
     * Multiline for loop is legal.
     */
    public void multilineFor() {
        int sum = 0;
        for (int i = 0,
                j = 1;
                i < 2;
                i++, j--) {
            sum += i;
        }
    }

    /**
     * Forever loop is legal.
     */
    public void foreverLoop() {
        int count = 0;
        for (;;) {
            count++;
            if (count > 1) {
                break;
            }
        }
    }

    /**
     * One statement inside for block is legal.
     */
    public void singleStatementFor() {
        int one = 0;
        for (int i = 0; i < 2; i++) {
            one = i;
        }
    }
}
