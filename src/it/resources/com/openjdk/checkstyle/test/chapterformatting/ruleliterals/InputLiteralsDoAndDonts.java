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

package com.openjdk.checkstyle.test.chapterformatting.ruleliterals;

public class InputLiteralsDoAndDonts {

    public void styleGuideDo() {
        long l = 5432L;
        int i = 0x123 + 0xABC;
        byte b = 0b1010;
        float f1 = 1 / 5432f;
        float f2 = 0.123e4f;
        double d1 = 1 / 5432d;
        double d2 = 0x1.3p2;
    }

    public void styleGuideDonts() {
        long l = 5432l; // violation 'Should use uppercase 'L'.'
        int i = 0X123 + 0xabc;
        // 2 violations above:
        // 'Numerical prefix should be in lowercase.'
        // 'Should use uppercase hexadecimal letters.'
        byte b = 0B1010; // violation 'Numerical prefix should be in lowercase.'
        float f1 = 1 / 5432F; // violation 'Numerical suffix should be in lowercase.'
        float f2 = 0.123E4f; // violation 'Numerical infix should be in lowercase.'
        double d1 = 1 / 5432D; // violation 'Numerical suffix should be in lowercase.'
        double d2 = 0x1.3P2; // violation 'Numerical infix should be in lowercase.'
    }
}
