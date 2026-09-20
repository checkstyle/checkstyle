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

import java.io.File;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.DuplicateConditionIfChainCheck;

public class XpathRegressionDuplicateConditionIfChainTest extends AbstractXpathTestSupport {

    private final String checkName = DuplicateConditionIfChainCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/duplicateconditionifchain";
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathDuplicateConditionIfChain.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(DuplicateConditionIfChainCheck.class);
        final String[] expectedViolation = {
            "11:22: " + getCheckMessage(DuplicateConditionIfChainCheck.class,
                    DuplicateConditionIfChainCheck.MSG_KEY, "9"),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateConditionIfChain']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                        + "/SLIST/LITERAL_IF/LITERAL_ELSE/LITERAL_IF"
                        + "/EXPR",
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateConditionIfChain']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                        + "/SLIST/LITERAL_IF/LITERAL_ELSE/LITERAL_IF"
                        + "/EXPR/GT[./IDENT[@text='x']]"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathDuplicateConditionIfChainTwo.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(DuplicateConditionIfChainCheck.class);
        final String[] expectedViolation = {
            "13:26: " + getCheckMessage(DuplicateConditionIfChainCheck.class,
                    DuplicateConditionIfChainCheck.MSG_KEY, "9"),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateConditionIfChainTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bar']]"
                        + "/SLIST/LITERAL_IF/LITERAL_ELSE/LITERAL_IF/LITERAL_ELSE/LITERAL_IF"
                        + "/EXPR",
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateConditionIfChainTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bar']]"
                        + "/SLIST/LITERAL_IF/LITERAL_ELSE/LITERAL_IF/LITERAL_ELSE/LITERAL_IF"
                        + "/EXPR/LAND"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathDuplicateConditionIfChainThree.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(DuplicateConditionIfChainCheck.class);
        final String[] expectedViolation = {
            "13:20: " + getCheckMessage(DuplicateConditionIfChainCheck.class,
                    DuplicateConditionIfChainCheck.MSG_KEY, "9"),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateConditionIfChainThree']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='baz']]"
                        + "/SLIST/LITERAL_IF/LITERAL_ELSE/LITERAL_IF/LITERAL_ELSE/LITERAL_IF"
                        + "/EXPR[./IDENT[@text='cond1']]",
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateConditionIfChainThree']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='baz']]"
                        + "/SLIST/LITERAL_IF/LITERAL_ELSE/LITERAL_IF/LITERAL_ELSE/LITERAL_IF"
                        + "/EXPR/IDENT[@text='cond1']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
