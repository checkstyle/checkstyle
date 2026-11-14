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
import com.puppycrawl.tools.checkstyle.checks.coding.DuplicateMapOrSetKeyCheck;

public class XpathRegressionDuplicateMapOrSetKeyTest extends AbstractXpathTestSupport {

    private final String checkName = DuplicateMapOrSetKeyCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/duplicatemaporsetkey";
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathDuplicateMapOrSetKey.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(DuplicateMapOrSetKeyCheck.class);
        final String[] expectedViolation = {
            "11:26: " + getCheckMessage(DuplicateMapOrSetKeyCheck.class,
                    DuplicateMapOrSetKeyCheck.MSG_KEY, "11"),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateMapOrSetKey']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                        + "/SLIST/EXPR/METHOD_CALL/ELIST/EXPR[./STRING_LITERAL[@text='a']]",
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateMapOrSetKey']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                        + "/SLIST/EXPR/METHOD_CALL/ELIST/EXPR/STRING_LITERAL[@text='a']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathDuplicateMapOrSetKeyTwo.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(DuplicateMapOrSetKeyCheck.class);
        final String[] expectedViolation = {
            "11:33: " + getCheckMessage(DuplicateMapOrSetKeyCheck.class,
                    DuplicateMapOrSetKeyCheck.MSG_KEY, "11"),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateMapOrSetKeyTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bar']]"
                        + "/SLIST/EXPR/METHOD_CALL/ELIST/EXPR[./STRING_LITERAL[@text='k1']]",
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateMapOrSetKeyTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bar']]"
                        + "/SLIST/EXPR/METHOD_CALL/ELIST/EXPR/STRING_LITERAL[@text='k1']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathDuplicateMapOrSetKeyThree.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(DuplicateMapOrSetKeyCheck.class);
        final String[] expectedViolation = {
            "13:23: " + getCheckMessage(DuplicateMapOrSetKeyCheck.class,
                    DuplicateMapOrSetKeyCheck.MSG_KEY, "12"),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateMapOrSetKeyThree']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='baz']]"
                        + "/SLIST/EXPR/METHOD_CALL/ELIST/EXPR/METHOD_CALL"
                        + "/ELIST/EXPR[./STRING_LITERAL[@text='key']]",
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathDuplicateMapOrSetKeyThree']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='baz']]"
                        + "/SLIST/EXPR/METHOD_CALL/ELIST/EXPR/METHOD_CALL"
                        + "/ELIST/EXPR/STRING_LITERAL[@text='key']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}

