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

public class InputSpecialCharactersValid {

    private final char apostrophe = '\'';
    private final char quote = '"';
    private final char slash = '\\';
    private final char backspace = '\b';
    private final char tab = '\t';
    private final char formFeed = '\f';
    private final char carriageReturn = '\r';
    private final char newLine = '\n';
    private final char space = ' ';

    // Unicode escapes (for reference, shown as plain text):
    // apostrophe: \\u0027, quote: \\u0022, backslash: \\u005c
    // tab: \\u0009, backspace: \\u0008, carriage return: \\u000d
    // form feed: \\u000c, newline: \\u000a, space: \\u0020

}
