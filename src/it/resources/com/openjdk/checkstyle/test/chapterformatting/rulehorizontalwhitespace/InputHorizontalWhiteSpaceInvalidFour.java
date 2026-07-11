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

import java.io.IOException;

/**
 * Dummy input.
 */
public class InputHorizontalWhiteSpaceInvalidFour {

    int x;

    /**
     * Dummy input.
     */
    public InputHorizontalWhiteSpaceInvalidFour(int n) {
    }

    /**
     * Dummy input.
     */
    public void fun() {

        boolean a = true;
        if ( a ) {
        // 2 violations above:
        // ''(' is followed by whitespace'
        // '')' is preceded with whitespace'
        }

        try {
            throw new IOException();
        } catch ( IOException e) { // violation 'is followed by whitespace'
        } catch (Exception e ) {}  // violation 'is preceded with whitespace'

        for ( int i = 0; i < x; i++ ) {
        // 2 violations above:
        // ''(' is followed by whitespace'
        // '')' is preceded with whitespace'
        }
    }

    /**
     * Dummy input.
     */
    public void fun2() {
        switch ( x) { // violation 'is followed by whitespace'
            case 2: {
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * Dummy input.
     */
    class Bar extends InputHorizontalWhiteSpaceInvalidFour {

        /**
         * Dummy input.
         */
        Bar() {
            super(1 ); // violation '')' is preceded with whitespace'
        }

        /**
         * Dummy input.
         */
        Bar(int k) {
            super( k );
            // 2 violations above:
            // ''(' is followed by whitespace'
            // '')' is preceded with whitespace'
            for ( int i = 0; i < k; i++) { // violation ''(' is followed by whitespace'
            }
        }
    }
}
