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

package com.openjdk.checkstyle.test.chapterjavasourcefiles.rulespecialcharacters;

public class InputSpecialCharactersInvalid {
    // The short forms (e.g. \t) are commonly used and easier to recognize than
    // the corresponding longer forms (\011, \u0009).
    // \', \", \\, \t, \b, \r, \f, and \n should be preferred over corresponding
    // octal (e.g. \047) or Unicode (e.g. \u0027) escaped characters.

    private final char apostropheOctal = '\047';
    // violation above 'special escape sequence'
    // Should use: \'  (short escape for apostrophe)

    private final char quoteOctal = '\042';
    // violation above 'special escape sequence'
    // Should use: \" (short escape for quote)

    private final char slashOctal = '\134';
    // violation above 'special escape sequence'
    // Should use: \\ (short escape for backslash)

    private final char backspaceOctal = '\010';
    // violation above 'special escape sequence'
    // Should use: \b (short escape for backspace)

    private final char tabOctal = '\011';
    // violation above 'special escape sequence'
    // Should use: \t (short escape for tab)

    private final char formFeedOctal = '\014';
    // violation above 'special escape sequence'
    // Should use: \f (short escape for form feed)

    private final char carriageReturnOctal = '\015';
    // violation above 'special escape sequence'
    // Should use: \r (short escape for carriage return)

    private final char newLineOctal = '\012';
    // violation above 'special escape sequence'
    // Should use: \n (short escape for newline)

    private final char escapedLetter = '\u0041';
    // violation above 'Unicode escape(s) usage should be avoided.'
    // Should use: 'A' (plain ASCII character instead of Unicode escape)

    private final char spaceOctal = '\040';
    // violation above 'special escape sequence'

}
