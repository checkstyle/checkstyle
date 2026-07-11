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

package com.openjdk.checkstyle.test.chapterformatting.ruleimportstatements.importorder;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.util.Arrays;
// violation above 'Import statement for 'java.util.Arrays' violates
// the configured import group order.'

/**
 * Invalid: javax.* imports appear before java.* imports.
 * According to OpenJDK style, java.* should come before javax.*
 */
public final class InputImportOrderWrongPackageOrder {
    /** Dummy constant. */
    public static final int MAX_VALUE = Integer.MAX_VALUE;

    /**
     * Private constructor to prevent instantiation.
     */
    private InputImportOrderWrongPackageOrder() {
    }

    /**
     * Demonstrates wrong package ordering.
     */
    public static void demonstrate() {
        final JFrame frame = new JFrame();
        final JButton button = new JButton("Test");
        final String[] arr = Arrays.copyOf(new String[]{"a"}, 5);
    }
}
