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
package com.openjdk.checkstyle.test.chapterformatting.ruleverticalwhitespace;

// violation 2 lines above ''package' should be separated from previous line'
import java.util.List;
public class InputVerticalWhiteSpaceOne {
    // violation above ''CLASS_DEF' should be separated from previous line'
    int var1 = 10;
    List<Integer> list; // violation below ''CTOR_DEF' should be separated from previous line'
    InputVerticalWhiteSpaceOne() {}
    InputVerticalWhiteSpaceOne(int var2) {}
    // violation above ''CTOR_DEF' should be separated from previous line'

    void method() {}
    void method1() {} // violation ''METHOD_DEF' should be separated from previous line'
    { // violation ''INSTANCE_INIT' should be separated from previous line'
        int a = 0;
    }
    static { // violation ''STATIC_INIT' should be separated from previous line'
    }

    static {
    }

}
