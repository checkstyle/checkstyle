////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import org.junit.Test;

import com.google.checkstyle.test.base.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class CustomImportOrderTest extends AbstractModuleTestSupport {

    /** Shortcuts to make code more compact. */
    private static final String MSG_LINE_SEPARATOR = CustomImportOrderCheck.MSG_LINE_SEPARATOR;
    private static final String MSG_LEX = CustomImportOrderCheck.MSG_LEX;
    private static final String MSG_NONGROUP_EXPECTED =
        CustomImportOrderCheck.MSG_NONGROUP_EXPECTED;

    private static final String STATIC = CustomImportOrderCheck.STATIC_RULE_GROUP;

    private final Class<CustomImportOrderCheck> clazz = CustomImportOrderCheck.class;

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter3filestructure/rule333orderingandspacing";
    }

    @Test
    public void testCustomImport1() throws Exception {
        final String[] expected = {
            "4: " + getCheckMessage(clazz, MSG_LEX, "java.awt.Button.ABORT",
                "java.io.File.createTempFile"),
            "6: " + getCheckMessage(clazz, MSG_LINE_SEPARATOR, "java.awt.Button"),
            "8: " + getCheckMessage(clazz, MSG_LEX, "java.awt.Dialog", "java.awt.Frame"),
            "12: " + getCheckMessage(clazz, MSG_LEX, "java.io.File", "javax.swing.JTable"),
            "13: " + getCheckMessage(clazz, MSG_LEX, "java.io.IOException", "javax.swing.JTable"),
            "14: " + getCheckMessage(clazz, MSG_LEX, "java.io.InputStream", "javax.swing.JTable"),
            "15: " + getCheckMessage(clazz, MSG_LEX, "java.io.Reader", "javax.swing.JTable"),
            "17: " + getCheckMessage(clazz, MSG_LEX, "com.google.common.base.Ascii",
                "javax.swing.JTable"),
        };

        final Configuration checkConfig = getModuleConfig("CustomImportOrder");
        final String filePath = getPath("InputCustomImportOrder1.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCustomImport2() throws Exception {
        final String[] expected = {
            "4: " + getCheckMessage(clazz, MSG_LEX, "java.awt.Button.ABORT",
                "java.io.File.createTempFile"),
            "9: " + getCheckMessage(clazz, MSG_LEX, "java.util.*", "java.util.StringTokenizer"),
            "11: " + getCheckMessage(clazz, MSG_LEX, "java.util.concurrent.*",
                "java.util.concurrent.AbstractExecutorService"),
            "13: " + getCheckMessage(clazz, MSG_LEX,
                "com.google.checkstyle.test.chapter2filebasic.rule21filename.*",
                "java.util.concurrent.AbstractExecutorService"),
            "14: " + getCheckMessage(clazz, MSG_LEX,
                "com.google.checkstyle.test.chapter3filestructure.rule3sourcefile.*",
                "java.util.concurrent.AbstractExecutorService"),
            "16: " + getCheckMessage(clazz, MSG_LEX, "com.google.common.reflect.*",
                "java.util.concurrent.AbstractExecutorService"),
        };

        final Configuration checkConfig = getModuleConfig("CustomImportOrder");
        final String filePath = getPath("InputCustomImportOrder2.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCustomImport3() throws Exception {
        final String[] expected = {
            "4: " + getCheckMessage(clazz, MSG_LINE_SEPARATOR, "java.awt.Dialog"),
            "5: " + getCheckMessage(clazz, MSG_NONGROUP_EXPECTED, STATIC,
                "javax.swing.WindowConstants.*"),
            "7: " + getCheckMessage(clazz, MSG_LEX,
                "com.google.checkstyle.test.chapter2filebasic.rule21filename.*", "java.awt.Dialog"),
            "8: " + getCheckMessage(clazz, MSG_LEX, "com.google.common.reflect.*",
                "java.awt.Dialog"),
            "9: " + getCheckMessage(clazz, MSG_LEX,
                "com.google.checkstyle.test.chapter3filestructure.rule3sourcefile.*",
                "java.awt.Dialog"),
            "11: " + getCheckMessage(clazz, MSG_NONGROUP_EXPECTED, STATIC,
                "java.io.File.createTempFile"),
            "13: " + getCheckMessage(clazz, MSG_LEX, "java.util.*", "java.util.StringTokenizer"),
            "15: " + getCheckMessage(clazz, MSG_LEX, "java.util.concurrent.*",
                "java.util.concurrent.AbstractExecutorService"),
        };

        final Configuration checkConfig = getModuleConfig("CustomImportOrder");
        final String filePath = getPath("InputCustomImportOrder3.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testValid() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("CustomImportOrder");
        final String filePath = getPath("InputCustomImportOrderValid.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testValidGoogleStyleOrderOfImports() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("CustomImportOrder");
        final String filePath = getPath("InputCustomImportOrderNoImports.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
