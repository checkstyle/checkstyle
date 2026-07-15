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

package com.openjdk.checkstyle.test.chapterformatting.rulevariabledeclarations.arraysquarebracketsattype;

/** Test case for ArrayTypeStyle (Java vs C). */
public class InputArraySquareBracketsAtType {
    private int[] javastyle = new int[0];
    private int cstyle[] = new int[0]; // violation 'Array brackets at illegal position.'

    int[] array[] = new int[2][2]; // violation 'Array brackets at illegal position.'
    int array2[][][] = new int[3][3][3];
    // 3 violations above:
    //                    'Array brackets at illegal position.'
    //                    'Array brackets at illegal position.'
    //                    'Array brackets at illegal position.'

    /** Some javadoc. */
    public static void mainJava(String[] javastyle) {}

    /** Some javadoc. */
    public static void mainC(String cstyle[]) { // violation 'Array brackets at illegal position.'
        final int[] blah = new int[0];
        final boolean isok1 = cstyle instanceof String[];
        final boolean isok2 = cstyle instanceof String[];
        final boolean isok3 = blah instanceof int[];
        int[] array[] = new int[2][2]; // violation 'Array brackets at illegal position.'
        int array2[][][] = new int[3][3][3];
        // 3 violations above:
        //                    'Array brackets at illegal position.'
        //                    'Array brackets at illegal position.'
        //                    'Array brackets at illegal position.'
    }

    /** Some javadoc. */
    public class Test {
        public Test[] variable;

        public Test[] getTests() {
            return null;
        }

        public Test[] getNewTest() {
            return null;
        }

        public Test getOldTest()[] { // violation 'Array brackets at illegal position.'
            return null;
        }

        // 2 violations 3 lines below:
        //                    'Array brackets at illegal position.'
        //                    'Array brackets at illegal position.'
        public Test getOldTests()[][] {
            return null;
        }

        public Test[] getMoreTests()[] { // violation 'Array brackets at illegal position.'
            return null;
        }

        public Test[][] getTests2() {
            return null;
        }
    }

}
