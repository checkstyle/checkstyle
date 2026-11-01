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

import com.puppycrawl.tools.checkstyle.checks.coding.DuplicateSwitchBranchCheck;

public class XpathRegressionDuplicateSwitchBranchTest
        extends AbstractXpathTestSupport {

    private static final Class<DuplicateSwitchBranchCheck> CLASS =
            DuplicateSwitchBranchCheck.class;

    @Override
    protected String getCheckName() {
        return CLASS.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/duplicateswitchbranch";
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "14:13: " + getCheckMessage(CLASS, DuplicateSwitchBranchCheck.MSG_KEY, 12),
        };

        final String switchPath = "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateSwitchBranchOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_SWITCH";
        final List<String> expectedXpathQueries = List.of(
                switchPath + "/CASE_GROUP[./LITERAL_CASE/EXPR/NUM_INT[@text='1']]",
                switchPath + "/CASE_GROUP/LITERAL_CASE"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathDuplicateSwitchBranchOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "13:13: " + getCheckMessage(CLASS, DuplicateSwitchBranchCheck.MSG_KEY, 12),
        };

        final String switchPath = "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateSwitchBranchTwo']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_RETURN"
                + "/EXPR/LITERAL_SWITCH";
        final List<String> expectedXpathQueries = List.of(
                switchPath + "/SWITCH_RULE[./LITERAL_CASE/EXPR/STRING_LITERAL[@text='B']]",
                switchPath + "/SWITCH_RULE/LITERAL_CASE"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathDuplicateSwitchBranchTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "15:13: " + getCheckMessage(CLASS, DuplicateSwitchBranchCheck.MSG_KEY, 14),
        };

        final String switchPath = "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateSwitchBranchThree']]"
                + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='InputXpathDuplicateSwitchBranchThree']]"
                + "/SLIST/LITERAL_SWITCH";
        final List<String> expectedXpathQueries = List.of(
                switchPath + "/SWITCH_RULE[./LITERAL_CASE/EXPR/NUM_INT[@text='1']]",
                switchPath + "/SWITCH_RULE/LITERAL_CASE"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathDuplicateSwitchBranchThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
