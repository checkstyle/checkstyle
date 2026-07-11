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

package com.openjdk.checkstyle.test.chapterformatting.rulewrappingexpressions;

import java.util.Arrays;

public class InputWrappingExpressionsInvalid {
    void test() {
        int x = 1 + // violation ''\+' should be on a new line.'
                2 - // violation ''-' should be on a new line.'
                3
                -
                4;
        x = x + 2;
        boolean y = true
                &&
                false;
        y = true && // violation ''&&' should be on a new line.'
                false;
        y = false
                && true;
        Arrays.sort(null, String
                    ::
                    compareToIgnoreCase);
        Arrays.sort(null, String:: // violation ''::' should be on a new line.'
                    compareToIgnoreCase);
        Arrays.sort(null, String
                    ::compareToIgnoreCase);
    }

    String typeGuardAfterParenthesizedTrueIfStatement2(Object p) {
        Object o = p;
        if (o != null && // violation ''&&' should be on a new line.'
                o instanceof Integer i && // violation ''&&' should be on a new line.'
                        i == 0) {
            return "true";
        } else if (o != null && // violation ''&&' should be on a new line.'
                o instanceof Integer i && // violation ''&&' should be on a new line.'
                        i == 2 && // violation ''&&' should be on a new line.'
                (o = i) != null) {
            return "second";
        } else {
            return "any";
        }
    }
}
