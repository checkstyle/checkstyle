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

package com.openjdk.checkstyle.test.chapterformatting.rulebraces;

/** Input file for Left Curly check - Invalid. */
public class InputBracesLeftCurlyInValid {

    public void testMethod()
    { // violation, ''{' at column 5 should be on the previous line.'

        if (true)
        { // violation, ''{' at column 9 should be on the previous line.'
            System.out.println("Hello");
        } else
        { // violation, ''{' at column 9 should be on the previous line.'
            System.out.println("World");
        }

        for (int i = 0; i < 10; i++)
        { // violation, ''{' at column 9 should be on the previous line.'
            System.out.println(i);
        }

        int temp = 0;
        while (temp == 0)
        { // violation, ''{' at column 9 should be on the previous line.'
            System.out.println("Hello");
        }

        do
        { // violation, ''{' at column 9 should be on the previous line.'
            System.out.println("Hello");
        } while (true);
    }

    void testMethod2() {
        int a = 0;
        int b = 10;
        if (a == 0
                && b == 10)
        { // violation, ''{' at column 9 should be on the previous line.'
            System.out.println("Hello");
        }
    }

    void testMethod3() {
        try {
            int temp = 0;
        } // violation, 'should be on the same line'
        catch (Exception e) {
        }
        finally {
            System.out.println("hello");
        }

        try {
            System.out.println("hello");
        } // violation, 'should be on the same line'
        catch (RuntimeException ex) {

        } // violation, 'should be on the same line'
        catch (Exception ex) {

        }

        if (true) {
        } // violation, 'should be on the same line'
        else {
        }

        int a = 0;

        if (a == 0) {

        }
        else if (a == 10) {

        }
        else if (a == 1) {

        } // violation, 'should be on the same line'
        else {

        }

        int i = 0;

        do {
            System.out.println("hello");
        } // violation, 'should be on the same line'
        while (i > 0);
    }
}
