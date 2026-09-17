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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck;

public class XpathRegressionOneStatementPerLineTest extends AbstractXpathTestSupport {

    private final String checkName = OneStatementPerLineCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/onestatementperline";
    }

    @Test
    public void testClassFields() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathOneStatementPerLineClassFields.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(OneStatementPerLineCheck.class);

        final String[] expectedViolation = {
            "4:12: " + getCheckMessage(OneStatementPerLineCheck.class,
                OneStatementPerLineCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathOneStatementPerLineClassFields']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='j']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathOneStatementPerLineClassFields']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='j']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathOneStatementPerLineClassFields']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='j']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathOneStatementPerLineClassFields']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='j']]/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testForLoopBlock() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathOneStatementPerLineForLoopBlock.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(OneStatementPerLineCheck.class);

        final String[] expectedViolation = {
            "9:33: " + getCheckMessage(OneStatementPerLineCheck.class,
                OneStatementPerLineCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathOneStatementPerLineForLoopBlock']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='foo5']]/SLIST/LITERAL_FOR/SLIST"
                + "/EXPR/POST_INC/IDENT[@text='var2']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testMethodBody() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathOneStatementPerLineMethodBody.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(OneStatementPerLineCheck.class);

        final String[] expectedViolation = {
            "5:20: " + getCheckMessage(OneStatementPerLineCheck.class,
                OneStatementPerLineCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathOneStatementPerLineMethodBody']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='b']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathOneStatementPerLineMethodBody']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='b']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathOneStatementPerLineMethodBody']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='b']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathOneStatementPerLineMethodBody']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='b']]/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
