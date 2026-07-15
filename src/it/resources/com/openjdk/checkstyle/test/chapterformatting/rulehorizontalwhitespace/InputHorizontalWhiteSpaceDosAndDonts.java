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

package com.openjdk.checkstyle.test.chapterformatting.rulehorizontalwhitespace;

import java.util.function.IntUnaryOperator;

/** Test Inputs from style guide. */
public class InputHorizontalWhiteSpaceDosAndDonts {
    boolean isFlagSet(String txt) {
        return true;
    }

    public void styleGuideDo() {
        int someInt;
        String myString;
        char aChar;
        long sixtyfourFlags;

        if (isFlagSet("GO")) {
            System.out.println("hello");
        }
        IntUnaryOperator inc = x -> x + 1;

        init: {
            int tempo = 0;
        }
    }

    public void styleGuideDonts() {
        int    someInt; // violation 'Use a single space to separate non-whitespace characters.'
        String myString;
        char   aChar; // violation 'Use a single space to separate non-whitespace characters.'
        long   sixtyfourFlags;
        // violation above 'Use a single space to separate non-whitespace characters.'

        if ( isFlagSet( "GO" ) ) {
            // 4 violations above:
            // ''(' is followed by whitespace.'
            // ''(' is followed by whitespace.'
            // '')' is preceded with whitespace.'
            // '')' is preceded with whitespace.'
            System.out.println("hello");
        }
        IntUnaryOperator inc = x ->x + 1;
        // violation above ''->' is not followed by whitespace.'

        init : { // violation '':' is preceded with whitespace.'
            int tempo = 0;
        }
    }
}
