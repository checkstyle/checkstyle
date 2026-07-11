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

import java.util.List;

public class InputWrappingExpressionsMethodChainValid {

    public List<Integer> method(List<Integer> list) {
        return list
                .stream()
                .toList();
    }

    public List<Integer> method2(List<Integer> list) {
        return list.stream().toList();
    }

    class Inner {
        class Inner2 {
        }
        Inner.
                Inner2 inner = new Inner2();
        Inner.
                Inner2 inner2 = new Inner().
                                new Inner2();
    }

    public void myMethod() {
        java.lang.
                String text = java.
                lang.
                String.CASE_INSENSITIVE_ORDER
                .toString();
    }

    public void multiLineStrings() {
        String[] rules = """
                Welcome, %s!
                Rule 1: Always write clean code.
                Rule 2: Don't forget your semicolons.
                Rule 3: Keep it simple.
                """
                .formatted("user")
                .lines()
                .filter(line -> line.contains("Rule"))
                .toArray(String[]::new);

        String rules2 = """
                Welcome, %s!
                Rule 1: Always write clean code.
                Rule 2: Don't forget your semicolons.
                Rule 3: Keep it simple.
                """.formatted("user");
    }
}
