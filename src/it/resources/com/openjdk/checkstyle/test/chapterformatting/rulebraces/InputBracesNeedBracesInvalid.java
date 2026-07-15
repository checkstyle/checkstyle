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

public class InputBracesNeedBracesInvalid {
    String obj = new String();
    String value = new String();
    int counter = 1;
    int count = 0;
    int num = 12;
    String o = "O";

    public boolean test() {
        if (obj.equals(num)) return true;
        // violation above, ''if' construct must use '{}'s.'
        if (true) {
            count = 2;
        } else
            // violation above, ''else' construct must use '{}'s.'
            return false;
        for (int i = 0; i < 5; i++) {
            ++count;
        }
        do // violation, ''do' construct must use '{}'s.'
            ++count;
        while (false);
        for (int j = 0; j < 10; j++);
        // violation above, ''for' construct must use '{}'s.'
        for (int i = 0; i < 10; value.charAt(12));
        // violation above, ''for' construct must use '{}'s.'
        while (counter < 10)
            // violation above, ''while' construct must use '{}'s.'
            ++count;

        while (value.charAt(12) < 5);
        // violation above, ''while' construct must use '{}'s.'

        return true;
    }
}
