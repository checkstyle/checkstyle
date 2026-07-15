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

// violation 4 lines below 'Line is longer than 100 characters (found 103).'
/**
 * This is a short Javadoc comment.
 * This is a very long url https://verylongurlexamplebutitdoesnotthrowanyerrorbecausetheURLisignoredbylineLengthCheck.com
 * ThisJavadocCommentIsAReallyLongWordThatExceedsDefaultLineLimitOfHundredCharactersSoitwillgiveserror.
 */
public class InputWrappingLinesLineLimit {
    void testMethod(String str) {
        System.out.println("This is a short line.");

        System.out.println("This line is long and exceeds the default limit of 100 characters............");
        // violation above 'Line is longer than 100 characters (found 108).'

        String str1 = """
                    This is a very really long string that exceeds the limit it will gives violation..........................
                    """;
        // violation 2 lines above 'Line is longer than 100 characters (found 126).'

        String str2 = """
                    This is a very really long string that is exactly equal 100 so no violation....
                    """;
    }
}
