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

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Test input for lambda body length with no violations.
 */
public class InputLambdaExpressionsBodyLengthValid {

    /**
     * Valid block body within the limit.
     */
    public void sumLegal() {
        BiFunction<Integer, Integer, String> checkSum = (a, b) -> {
            int sum = a + b;
            if (sum > 100) {
                return "That is a large number: " + sum;
            } else {
                return "That is a small number: " + sum;
            }
        };
    }

    /**
     * A short, single-expression lambda without braces.
     */
    public void testShortExpressionLambda() {
        Function<Integer, Integer> square = x -> x * x;
    }

    /**
     * Simple legal method.
     */
    public void doLegal() {
        Runnable r = () -> {
            int a = 1;
            int b = 2;
            int c = 3;
            int e = 5;
            int f = 6;
            int g = 7;
            int h = 8;
            int i = 9;
        };
    }
}
