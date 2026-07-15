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

public class InputHorizontalWhiteSpaceInvalidFive {
    int foo()   { // violation 'Use a single space'
        return  1; // violation 'Use a single space'
    }

    int fun1() {
        return 3;
    }

    void  fun2() {} // violation 'Use a single space'

    public void temp() {
        if  (1 > 2)  {
            // 2 violations above:
            // 'Use a single space'
            // 'Use a single space'
        }

        for  (int i = 0; i < 10;  i++)  {
            // 3 violations above:
            // 'Use a single space'
            // 'Use a single space'
            // 'Use a single space'
        }

        int a = 0;

        switch  (a)  {
            // 2 violations above:
            // 'Use a single space'
            // 'Use a single space'
            case 1:  { // violation 'Use a single space'
                break;
            }
            default:   { // violation 'Use a single space'
                break;
            }
        }
    }
}
