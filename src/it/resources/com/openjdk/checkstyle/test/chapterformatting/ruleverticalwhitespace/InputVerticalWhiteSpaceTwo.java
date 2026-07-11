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
public class InputVerticalWhiteSpaceTwo {
    // violation above ''CLASS_DEF' should be separated from previous line'
    int var1 = 0;
    int var2 = 0;

    int var3 = 0;
    enum Temp { // violation ''ENUM_DEF' should be separated from previous line'

    } // violation below ''RECORD_DEF' should be separated from previous line'
    record Order(int id, int param) {

        Order(int ids) {
            this(ids, 1);
        } // violation below ''CTOR_DEF' should be separated from previous line'
        Order(int id, int param) {
            this.id = id;
            this.param = param;
        }

    }
    interface Test { // violation ''INTERFACE_DEF' should be separated from previous line'
    }

}
