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

package org.checkstyle.suppressionxpathfilter.whitespace;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketWhitespaceCheck;

public class XpathRegressionArrayBracketWhitespaceTest extends AbstractXpathTestSupport {

    private final String checkName = ArrayBracketWhitespaceCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/whitespace/arraybracketwhitespace";
    }

    @Test
    public void testLbrackPreceded() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathArrayBracketWhitespaceDefault.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ArrayBracketWhitespaceCheck.class);

        final String[] expectedViolation = {
            "4:9: " + getCheckMessage(ArrayBracketWhitespaceCheck.class,
                    ArrayBracketWhitespaceCheck.MSG_WS_PRECEDED, "["),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathArrayBracketWhitespaceDefault']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='bad']]/TYPE/ARRAY_DECLARATOR"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testLbrackFollowed() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathArrayBracketWhitespaceFollowed.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ArrayBracketWhitespaceCheck.class);

        final String[] expectedViolation = {
            "5:16: " + getCheckMessage(ArrayBracketWhitespaceCheck.class,
                    ArrayBracketWhitespaceCheck.MSG_WS_FOLLOWED, "["),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathArrayBracketWhitespaceFollowed']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='x']]/ASSIGN/EXPR",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathArrayBracketWhitespaceFollowed']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='x']]/ASSIGN/EXPR"
                + "/INDEX_OP[./IDENT[@text='arr']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testRbrackPreceded() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathArrayBracketWhitespaceRbrackPreceded.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ArrayBracketWhitespaceCheck.class);

        final String[] expectedViolation = {
            "5:19: " + getCheckMessage(ArrayBracketWhitespaceCheck.class,
                    ArrayBracketWhitespaceCheck.MSG_WS_PRECEDED, "]"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathArrayBracketWhitespaceRbrackPreceded']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='x']]/ASSIGN/EXPR"
                + "/INDEX_OP[./IDENT[@text='arr']]/RBRACK"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
