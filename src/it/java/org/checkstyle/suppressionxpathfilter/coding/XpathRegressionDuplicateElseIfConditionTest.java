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

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.DuplicateElseIfConditionCheck;

public class XpathRegressionDuplicateElseIfConditionTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return DuplicateElseIfConditionCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/duplicateelseifcondition";
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolations = {
            "13:22: " + getCheckMessage(DuplicateElseIfConditionCheck.class,
                DuplicateElseIfConditionCheck.MSG_KEY, 11),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathDuplicateElseIfCondition1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/LITERAL_ELSE"
                + "/LITERAL_IF/EXPR",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathDuplicateElseIfCondition1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/LITERAL_ELSE"
                + "/LITERAL_IF/EXPR/GT[./IDENT[@text='x']]"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathDuplicateElseIfCondition1.java"),
            expectedXpathQueries,
            expectedViolations);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolations = {
            "15:22: " + getCheckMessage(DuplicateElseIfConditionCheck.class,
                DuplicateElseIfConditionCheck.MSG_KEY, 11),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathDuplicateElseIfCondition2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/LITERAL_ELSE"
                + "/LITERAL_IF/LITERAL_ELSE/LITERAL_IF/EXPR",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathDuplicateElseIfCondition2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/LITERAL_ELSE"
                + "/LITERAL_IF/LITERAL_ELSE/LITERAL_IF/EXPR/EQUAL[./IDENT[@text='x']]"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathDuplicateElseIfCondition2.java"),
            expectedXpathQueries,
            expectedViolations);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolations = {
            "13:22: " + getCheckMessage(DuplicateElseIfConditionCheck.class,
                DuplicateElseIfConditionCheck.MSG_KEY, 11),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathDuplicateElseIfCondition3']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/LITERAL_ELSE"
                + "/LITERAL_IF/EXPR",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathDuplicateElseIfCondition3']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/LITERAL_ELSE"
                + "/LITERAL_IF/EXPR/LAND[./IDENT[@text='a']]"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathDuplicateElseIfCondition3.java"),
            expectedXpathQueries,
            expectedViolations);
    }

}
