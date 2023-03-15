///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.SeparatorWrapCheck.MSG_LINE_NEW;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.SeparatorWrapCheck;

public class SeparatorWrapTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule451wheretobreak";
    }

    @Test
    public void testSeparatorWrapDot() throws Exception {
        final String[] expected = {
            "28:30: " + getCheckMessage(SeparatorWrapCheck.class, "line.new", "."),
        };

        final Configuration checkConfig = getModuleConfig("SeparatorWrap", "SeparatorWrapDot");
        final String filePath = getPath("InputSeparatorWrap.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testSeparatorWrapComma() throws Exception {
        final String[] expected = {
            "31:17: " + getCheckMessage(SeparatorWrapCheck.class, "line.previous", ","),
        };

        final Configuration checkConfig = getModuleConfig("SeparatorWrap", "SeparatorWrapComma");
        final String filePath = getPath("InputSeparatorWrapComma.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testSeparatorWrapMethodRef() throws Exception {
        final String[] expected = {
            "17:49: " + getCheckMessage(SeparatorWrapCheck.class, MSG_LINE_NEW, "::"),
        };

        final Configuration checkConfig = getModuleConfig("SeparatorWrap",
                "SeparatorWrapMethodRef");
        final String filePath = getPath("InputSeparatorWrapMethodRef.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testEllipsis() throws Exception {
        final String[] expected = {
            "11:13: " + getCheckMessage(SeparatorWrapCheck.class, "line.previous", "..."),
        };

        final Configuration checkConfig = getModuleConfig("SeparatorWrap", "SeparatorWrapEllipsis");
        final String filePath = getPath("InputSeparatorWrapEllipsis.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testArrayDeclarator() throws Exception {
        final String[] expected = {
            "9:13: " + getCheckMessage(SeparatorWrapCheck.class, "line.previous", "["),
        };
        final Configuration checkConfig = getModuleConfig("SeparatorWrap",
                "SeparatorWrapArrayDeclarator");
        final String filePath = getPath("InputSeparatorWrapArrayDeclarator.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
