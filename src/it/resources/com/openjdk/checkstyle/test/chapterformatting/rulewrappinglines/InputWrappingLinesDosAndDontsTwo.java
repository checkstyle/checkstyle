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

package com.openjdk.checkstyle.test.chapterformatting.rulewrappinglines;

import java.util.stream.IntStream;

public class InputWrappingLinesDosAndDontsTwo {

    public void styleGuideDosLineContinue(int that, int takes, int a, int lengthy, int is,
            int computed, int using, int thatTakes, int aLongList, int ofArguments, int complex,
            int exp, int list, int of, int arguments) {

        int[] numbers = new int[10];
        int anInteger = amethod(that, takes,
                a, lengthy, list, of, arguments);

        // Variant 2
        int anInteger1 = that * (is + computed) / using
                                + a * complex - exp;

        // Variant 3
        int anInteger2 = amethod(thatTakes,
                                aLongList,
                                ofArguments);

        // Variant 4
        double anInteger3 = IntStream.of(numbers)
                                     .mapToDouble(Math::sqrt)
                                     .sum();
    }

    public void styleGuideDontsLineContinue(int that, int takes, int a, int lengthy,
            int list, int of, int arguments) {

        // Not covered until https://github.com/checkstyle/checkstyle/issues/20714
        int anInteger = amethod(that,
                                takes,
                                a, lengthy, list,
                                of, arguments);

        if (somePredicate() || // violation ''\|\|' should be on a new line.'
            someOtherPredicate()) {
            System.out.println("Avoid");
        }
        // violation 3 lines above '.* incorrect indentation level 12, expected .* 16.'
    }

    private int amethod(int... args) { return 0; }

    private boolean somePredicate() { return false; }

    private boolean someOtherPredicate() { return false; }

}
