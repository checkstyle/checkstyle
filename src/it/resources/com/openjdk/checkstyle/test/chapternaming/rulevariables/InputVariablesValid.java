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

package com.openjdk.checkstyle.test.chapternaming.rulevariables;

import java.util.stream.Stream;

public class InputVariablesValid {

    static int itStatic1 = 2;
    protected static int itStatic2 = 2;
    private static int itStatic3 = 2;
    static int itStatic4 = 2;
    static int itStatic5 = 2;
    private static int itStatic6 = 2;

    public int num1;
    protected int num2;
    int num3;
    private int num4;

    public void method1(int var) {

        try {
            int temp;
            String str;
        } catch (Exception ex) {
            System.out.println("Hello");
        }
    }

    public void method2() {
        final String var = "hello";
        final int myVar = 42;
    }

    public boolean myMethod(String sentence) {
        return Stream.of(sentence.split(" "))
               .map(word -> word.trim())
               .anyMatch(words -> "in".equals(words));
    }

    void foo(Object o1) {
        if (o1 instanceof String string) { }
        if (o1 instanceof Integer num) { }
        if (o1 instanceof Integer nums) { }
    }

    record Rec2(String values) {}

}
