////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class CustomImportOrderTest extends BaseCheckTestSupport {

    private static final String MSG_SEPARATOR = "custom.import.order.line.separator";
    private static final String MSG_LEX = "custom.import.order.lex";
    private static final String MSG_ORDER = "custom.import.order";

    /** Shortcuts to make code more compact. */
    private static final String STD = CustomImportOrderCheck.STANDARD_JAVA_PACKAGE_RULE_GROUP;
    private static final String SPECIAL = CustomImportOrderCheck.SPECIAL_IMPORTS_RULE_GROUP;

    private final Class<CustomImportOrderCheck> clazz = CustomImportOrderCheck.class;

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter3filestructure" + File.separator + "rule333orderingandspacing"
                + File.separator + fileName);
    }

    @Test
    public void customImportTest1() throws Exception {

        final String[] expected = {
            "4: " + getCheckMessage(clazz, MSG_LEX, "java.awt.Button.ABORT",
                "java.io.File.createTempFile"),
            "9: " + getCheckMessage(clazz, MSG_LEX, "java.awt.Dialog", "java.awt.Frame"),
            "13: " + getCheckMessage(clazz, MSG_LEX, "java.io.File", "javax.swing.JTable"),
            "14: " + getCheckMessage(clazz, MSG_LEX, "java.io.IOException", "javax.swing.JTable"),
            "15: " + getCheckMessage(clazz, MSG_LEX, "java.io.InputStream", "javax.swing.JTable"),
            "16: " + getCheckMessage(clazz, MSG_LEX, "java.io.Reader", "javax.swing.JTable"),
            "20: " + getCheckMessage(clazz, MSG_LEX, "com.google.common.base.Ascii",
                "javax.swing.JTable"),
        };

        final Configuration checkConfig = getCheckConfig("CustomImportOrder");
        final String filePath = getPath("InputCustomImportOrder1.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void customImportTest2() throws Exception {

        final String[] expected = {
            "4: " + getCheckMessage(clazz, MSG_LEX, "java.awt.Button.ABORT",
                "java.io.File.createTempFile"),
            "9: " + getCheckMessage(clazz, MSG_LEX, "java.util.*", "java.util.StringTokenizer"),
            "11: " + getCheckMessage(clazz, MSG_LEX, "java.util.concurrent.*",
                "java.util.concurrent.AbstractExecutorService"),
            "13: " + getCheckMessage(clazz, MSG_LEX,
                "com.google.checkstyle.test.chapter2filebasic.rule21filename.*",
                "java.util.concurrent.AbstractExecutorService"),
            "14: " + getCheckMessage(clazz, MSG_LEX, "com.sun.xml.internal.xsom.impl.scd.Iterators",
                "java.util.concurrent.AbstractExecutorService"),
            "16: " + getCheckMessage(clazz, MSG_LEX, "com.google.common.reflect.*",
                "java.util.concurrent.AbstractExecutorService"),
        };

        final Configuration checkConfig = getCheckConfig("CustomImportOrder");
        final String filePath = getPath("InputCustomImportOrder2.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void customImportTest3() throws Exception {

        final String[] expected = {
            "4: " + getCheckMessage(clazz, MSG_LEX, "java.awt.Button.ABORT",
                "java.io.File.createTempFile"),
            "9: " + getCheckMessage(clazz, MSG_LEX, "java.util.*", "java.util.StringTokenizer"),
            "11: " + getCheckMessage(clazz, MSG_LEX, "java.util.concurrent.*",
                "java.util.concurrent.AbstractExecutorService"),
            "13: " + getCheckMessage(clazz, MSG_LEX,
                "com.google.checkstyle.test.chapter2filebasic.rule21filename.*",
                "java.util.concurrent.AbstractExecutorService"),
            "14: " + getCheckMessage(clazz, MSG_LEX, "com.sun.xml.internal.xsom.impl.scd.Iterators",
                "java.util.concurrent.AbstractExecutorService"),
            "16: " + getCheckMessage(clazz, MSG_LEX, "com.google.common.reflect.*",
                "java.util.concurrent.AbstractExecutorService"),
        };

        final Configuration checkConfig = getCheckConfig("CustomImportOrder");
        final String filePath = getPath("InputCustomImportOrder3.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void validTest() throws Exception {

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getCheckConfig("CustomImportOrder");
        final String filePath = getPath("InputCustomImportOrderValid.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
