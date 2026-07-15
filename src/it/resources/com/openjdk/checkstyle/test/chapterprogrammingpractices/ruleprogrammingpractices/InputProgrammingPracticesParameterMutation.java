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

package com.openjdk.checkstyle.test.chapterprogrammingpractices.ruleprogrammingpractices;

import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;

public class InputProgrammingPracticesParameterMutation {

    // violation below 'Assignment of parameter 'a' is not allowed.'
    IntPredicate obj = a -> ++a == 12;
    IntBinaryOperator obj2 = (int a, int b) -> {
        a++;     // violation 'Assignment of parameter 'a' is not allowed.'
        b += 12; // violation 'Assignment of parameter 'b' is not allowed.'
        return a + b;
    };

    IntPredicate obj3 = a -> {
        int b = a;
        return ++b == 12;
    };

    int methodOne(int parameter) {
        if (parameter <= 0) {
            throw new IllegalArgumentException("A positive value is expected");
        }
        // violation below 'Assignment of parameter 'parameter' is not allowed.'
        parameter -= 2;
        return parameter;
    }

    int methodTwo(int parameter) {
        if (parameter <= 0) {
            throw new IllegalArgumentException("A positive value is expected");
        }
        int local = parameter;
        local -= 2;
        return local;
    }
}
