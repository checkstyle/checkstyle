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
 * Test input for multiple statements per line.
 */
public final class InputOneStatementPerLineMultiple {
    /** Dummy variable. */
    private int one = 0;

    /** Dummy variable. */
    private int two = 0;

    /**
     * Method with multiple violations.
     */
    public void method() {
        int num = 0;
        int count = 1; count++; // violation 'Only one statement'
        num++; count++; // violation 'Only one statement per line allowed.'
    }

    /**
     * Method with while loop violation.
     */
    public void whileMethod() {
        while (one < 2) {
            one++; two--; // violation 'Only one statement per line allowed.'
        }
    }

    /**
     * Method with if statement violation.
     */
    public void ifMethod() {
        if (one == 1) {
            one++; two++; // violation 'Only one statement per line allowed.'
        }
    }

    /**
     * Method with method call violations.
     */
    public void callMethod() {
        toString(); toString(); // violation 'Only one statement'
    }
}
