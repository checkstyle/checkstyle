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

package com.openjdk.checkstyle.test.chapterformatting.rulewrappingmethoddeclarations;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class InputWrappingMethodDoAndDonts {
    class Do {
        public void someMethod(String aString,
                       List<Integer> aList,
                       Map<String, String> aMap,
                       int anInt,
                       long aLong,
                       Set<Number> aSet,
                       double aDouble) {
        }

        public void someMethod1(String aString, List<Integer> aList,
                Map<String, String> aMap, int anInt, long aLong,
                double aDouble, long aLongs) {
        }

        public void someMethod2(String aString,
                       List<Map<Integer, StringBuffer>> aListOfMaps,
                       Map<String, String> aMap)
                throws IllegalArgumentException {
        }

        public void someMethod3(String aString, List<Integer> aList,
                Map<String, String> aMap, int anInt)
                        throws IllegalArgumentException {
        }

    }

    class Donts {

        // Not covered until https://github.com/checkstyle/checkstyle/issues/20638
        public void someMethod(String aString,
                       List<Integer> aList,
                       Map<String, String> aMap,
                       int anInt, long aLong,
                       Set<Number> aSet,
                       double aDouble) {
        }

        // not covered until https://github.com/checkstyle/checkstyle/issues/20639
        public void someMethod2(String aString,
                       List<Map<Integer, StringBuffer>> aListOfMaps,
                       Map<String, String> aMap) throws InterruptedException {
        }

        // not covered until https://github.com/checkstyle/checkstyle/issues/20639
        public void someMethod3(String aString,
                       List<Integer> aList,
                       Map<String, String> aMap)
                       throws IllegalArgumentException {
        }

    }
}
