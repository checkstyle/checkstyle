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

package org.checkstyle.suppressionxpathfilter.coding;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.NestedTryDepthCheck;

public class XpathRegressionNestedTryDepthTest extends AbstractXpathTestSupport {

    private final String checkName = NestedTryDepthCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/nestedtrydepth";
    }

    @Test
    public void testCorrect() throws Exception {
        final String fileToProcess =
                getPath("InputXpathNestedTryDepth.java");

        final String[] expectedViolation = {
            "15:17: " + getCheckMessage(NestedTryDepthCheck.class,
                NestedTryDepthCheck.MSG_KEY, 2, 1),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNestedTryDepth']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_TRY/SLIST"
                + "/LITERAL_TRY/SLIST/LITERAL_TRY"
        );

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMax() throws Exception {
        final String fileToProcess =
            getPath("InputXpathNestedTryDepthMax.java");

        final String[] expectedViolation = {
            "17:25: " + getCheckMessage(NestedTryDepthCheck.class,
                NestedTryDepthCheck.MSG_KEY, 4, 3),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNestedTryDepthMax']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_TRY"
                + "/SLIST/LITERAL_TRY"
                + "/SLIST/LITERAL_TRY"
                + "/SLIST/LITERAL_TRY"
                + "/SLIST/LITERAL_TRY"
        );

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries,
            expectedViolation);
    }

    @Test
    public void testInnerClass() throws Exception {
        final String fileToProcess =
                getPath("InputXpathNestedTryDepthInnerClass.java");

        final String[] expectedViolation = {
            "16:21: " + getCheckMessage(NestedTryDepthCheck.class,
                NestedTryDepthCheck.MSG_KEY, 2, 1),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNestedTryDepthInnerClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_TRY/SLIST"
                + "/LITERAL_TRY/SLIST/LITERAL_TRY"
        );

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries,
                expectedViolation);
    }

}
