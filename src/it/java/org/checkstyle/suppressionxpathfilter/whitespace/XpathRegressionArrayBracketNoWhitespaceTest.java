///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter.whitespace;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck;

public class XpathRegressionArrayBracketNoWhitespaceTest extends AbstractXpathTestSupport {

    private final String checkName = ArrayBracketNoWhitespaceCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    protected String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/whitespace/arraybracketnowhitespace";
    }

    @Test
    public void testArrayBracketNoWhitespacePreceded() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathArrayBracketNoWhitespacePreceded.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ArrayBracketNoWhitespaceCheck.class);

        final String[] expectedViolation = {
            "4:9: " + getCheckMessage(ArrayBracketNoWhitespaceCheck.class,
                    ArrayBracketNoWhitespaceCheck.MSG_WS_PRECEDED, "["),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathArrayBracketNoWhitespacePreceded']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='bad']]/TYPE/ARRAY_DECLARATOR"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testArrayBracketNoWhitespaceFollowed() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathArrayBracketNoWhitespaceFollowed.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ArrayBracketNoWhitespaceCheck.class);

        final String[] expectedViolation = {
            "6:25: " + getCheckMessage(ArrayBracketNoWhitespaceCheck.class,
                    ArrayBracketNoWhitespaceCheck.MSG_WS_FOLLOWED, "["),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathArrayBracketNoWhitespaceFollowed']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='bad']]/SLIST/VARIABLE_DEF[./IDENT[@text='offsets']]"
                + "/ASSIGN/EXPR/LITERAL_NEW/ARRAY_DECLARATOR/LBRACK"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testArrayBracketNoWhitespaceNotFollowed() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathArrayBracketNoWhitespaceNotFollowed.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ArrayBracketNoWhitespaceCheck.class);

        final String[] expectedViolation = {
            "6:26: " + getCheckMessage(ArrayBracketNoWhitespaceCheck.class,
                    ArrayBracketNoWhitespaceCheck.MSG_WS_NOT_FOLLOWED, "]"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathArrayBracketNoWhitespaceNotFollowed']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='bad']]/SLIST/VARIABLE_DEF[./IDENT[@text='total']]"
                + "/ASSIGN/EXPR/PLUS[./NUM_INT[@text='5']]/INDEX_OP[./IDENT[@text='arr']]/RBRACK"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
