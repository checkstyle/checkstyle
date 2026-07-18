///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_FIRST_SENTENCE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_JAVADOC;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_MISSING_PERIOD;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class SummaryJavadocCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    /**
     * Creates a new {@code SummaryJavadocCheckExamplesTest} instance.
     */
    public SummaryJavadocCheckExamplesTest() {
        // no code by default
    }

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/summaryjavadoc";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "13:6: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "17:6: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "28:6: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "37:6: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "16:6: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "20:6: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "31:6: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "35:6: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "40:6: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "15:6: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "19:6: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "25:6: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "34:6: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

}
