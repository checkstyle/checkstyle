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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_FIRST_SENTENCE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_JAVADOC;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_MISSING_PERIOD;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class SummaryJavadocCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/summaryjavadoc";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "22: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "27: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "42: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "21: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "25: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "30: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "39: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "45: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "20: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "24: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "29: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "34: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "38: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "49: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }
}
