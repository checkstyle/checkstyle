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

package com.openjdk.checkstyle.test.chapterformatting.rulejavadoc;

public class InputJavadocOne {

    // violation below 'Summary javadoc is missing.'
    /**
     *
     */
    class Temp{
    }

    /**
     * There should be a short sentence at start.
     *
     * @return a string
     */
    public String method1() {
        return "abc";
    }

    // violation below 'Summary javadoc is missing.'
    /**
     * @return a string
     */
    public String method2() {
        return "";
    }

    // violation 2 lines below 'Summary javadoc is missing.'
    /**
     * {@summary  }
     */
    public String method3() {
        return "";
    }

    // violation 2 lines below 'Summary javadoc is missing.'
    /**
     * {@summary <p> </p>}
     */
    public String method4() {
        return "";
    }

    /**
     * {@summary <p>This is a javadoc with period.<p/>}
     */
    public void method5() {}

    // violation below 'First sentence of Javadoc is missing an ending period.'
    /**
     * Summary sentence should end with a period
     */
    public void method6() {}
}
