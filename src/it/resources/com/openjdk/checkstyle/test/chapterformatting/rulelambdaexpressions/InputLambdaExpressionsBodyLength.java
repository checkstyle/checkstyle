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

package com.openjdk.checkstyle.test.chapterformatting.rulelambdaexpressions;

/**
 * Test input for lambda body length with violations.
 */
public class InputLambdaExpressionsBodyLength {

    /**
     * Illegal method - lambda expression body exceeds 10 lines.
     */
    public void doIllegal() {
        Runnable r = () -> { // violation 'Lambda body length is 12 lines (max allowed is 10).'
            int a = 1;
            int b = 2;
            int c = 3;
            int d = 4;
            int e = 5;
            int f = 6;
            int g = 7;
            int h = 8;
            int i = 9;
            int j = 10;
        };
    }

    /**
     * Simple legal method.
     */
    public void doLegal() {
        Runnable r = () -> {
            int a = 1;
            int b = 2;
            int c = 3;
            int d = 4;
            int f = 6;
            int g = 7;
            int h = 8;
            int i = 9;
        };
    }
}
