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

package com.openjdk.checkstyle.test.chapterformatting.ruleredundantparenthesis;

import java.util.List;

/**
 * Test input for the redundant parenthesis rule for return statement.
 */
public final class InputRedundantParenForReturn {

    public int test() {
        String val = "1";
        return (val.equals("1") ? 1 : 0); // violation, 'Unnecessary parentheses*'
    }

    public List<Integer> test1(List<Integer> list) {

        return (list  // violation, 'Unnecessary parentheses*'
                .stream()
                .filter(num -> num % 2 == 0)
                .toList());
    }

    public int test2() {
        return ((10 * 4) + 5); // violation, 'Unnecessary parentheses*'
    }

    public int test3() {
        return 1 + 2;
    }

}
