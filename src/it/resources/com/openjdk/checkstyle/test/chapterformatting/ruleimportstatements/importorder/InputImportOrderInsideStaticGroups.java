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

import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static org.mockito.Mockito.mock;
import static java.util.Collections.emptyList;
// violation above """Import statement for 'java.util.Collections.emptyList'
// violates the configured import group order."""

import javax.swing.JButton;
// violation above """Import 'javax.swing.JButton' violates the configured
// relative order between static and non-static import"""

/**
 * Test file for static import order.
 */
public final class InputImportOrderInsideStaticGroups {
    /** Dummy constant. */
    public static final double TAU = 2 * PI;

    final JButton button = new JButton("Click");

    List<String> list2 = emptyList();

    List<String> mockList = mock(List.class);

    int a = abs(1);

}
