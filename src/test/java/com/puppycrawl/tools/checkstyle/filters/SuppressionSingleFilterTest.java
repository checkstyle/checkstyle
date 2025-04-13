///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.filters;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressionSingleFilterTest extends AbstractModuleTestSupport {

    private static final String FORMAT = "TODO$";
    private static final String MESSAGE = getCheckMessage(RegexpSinglelineCheck.class,
        "regexp.exceeded", FORMAT);
    private static final String[] ALL_MESSAGES = {
        "25: " + MESSAGE,
    };

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionsinglefilter";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] suppressed = {
            "25: " + MESSAGE,
        };
        verifySuppressedWithParser(getPath("InputSuppressionSingleFilter2.java"), suppressed);
    }

    @Test
    public void testMatching() throws Exception {
        final String[] suppressed = {
            "25: " + MESSAGE,
        };
        verifySuppressedWithParser(getPath("InputSuppressionSingleFilter3.java"), suppressed);
    }

    @Test
    public void testNonMatchingLineNumber() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressedWithParser(getPath("InputSuppressionSingleFilter4.java"), suppressed);
    }

    @Test
    public void testNonMatchingColumnNumber() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressedWithParser(getPath("InputSuppressionSingleFilter5.java"), suppressed);
    }

    @Test
    public void testNonMatchingFileRegexp() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressedWithParser(getPath("InputSuppressionSingleFilter6.java"), suppressed);
    }

    @Test
    public void testNonMatchingModuleId() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressedWithParser(getPath("InputSuppressionSingleFilter7.java"), suppressed);
    }

    @Test
    public void testMatchingModuleId() throws Exception {
        final String[] suppressed = {
            "25: " + MESSAGE,
        };
        verifySuppressedWithParser(getPath("InputSuppressionSingleFilter10.java"), suppressed);
    }

    @Test
    public void testNonMatchingChecks() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressedWithParser(getPath("InputSuppressionSingleFilter8.java"), suppressed);
    }

    @Test
    public void testNotMatchingMessage() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressedWithParser(getPath("InputSuppressionSingleFilter9.java"), suppressed);
    }

    @Test
    public void testMatchMessage() throws Exception {
        final String[] suppressed = {
            "25: " + MESSAGE,
        };
        verifySuppressedWithParser(getPath("InputSuppressionSingleFilter.java"), suppressed);
    }

    private void verifySuppressedWithParser(String fileName, String... suppressed)
            throws Exception {
        verifyFilterWithInlineConfigParser(fileName, ALL_MESSAGES,
                                           removeSuppressed(ALL_MESSAGES, suppressed));
    }

}
