///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package org.checkstyle.suppressionxpathfilter;

import static com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck.MSG_KEY;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck;

public class XpathRegressionAvoidEscapedUnicodeCharactersTest extends AbstractXpathTestSupport {
    private final String checkName = AvoidEscapedUnicodeCharactersCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDefault() throws Exception {
        final File fileToProcess = new File(getPath(
            "SuppressionXpathRegressionAvoidEscapedUnicodeCharactersDefault.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AvoidEscapedUnicodeCharactersCheck.class);

        final String[] expectedViolation = {
            "4:34: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='SuppressionXpathRegressionAvoidEscapedUnicodeCharactersDefault']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev2']]"
                + "/ASSIGN/EXPR[./STRING_LITERAL[@text='\\u03bcs']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='SuppressionXpathRegressionAvoidEscapedUnicodeCharactersDefault']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev2']]"
                + "/ASSIGN/EXPR/STRING_LITERAL[@text='\\u03bcs']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testControlCharacters() throws Exception {
        final File fileToProcess = new File(getPath(
            "SuppressionXpathRegressionAvoidEscapedUnicodeCharactersControlCharacters.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AvoidEscapedUnicodeCharactersCheck.class);
        moduleConfig.addProperty("allowEscapesForControlCharacters", "true");

        final String[] expectedViolation = {
            "4:34: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[."
                + "/IDENT[@text="
                    + "'SuppressionXpathRegressionAvoidEscapedUnicodeCharactersControlCharacters']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                + "/ASSIGN/EXPR[./STRING_LITERAL[@text='\\u03bcs']]",
            "/COMPILATION_UNIT/CLASS_DEF[."
                + "/IDENT[@text="
                    + "'SuppressionXpathRegressionAvoidEscapedUnicodeCharactersControlCharacters']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                + "/ASSIGN/EXPR/STRING_LITERAL[@text='\\u03bcs']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTailComment() throws Exception {
        final File fileToProcess = new File(getPath(
            "SuppressionXpathRegressionAvoidEscapedUnicodeCharactersTailComment.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AvoidEscapedUnicodeCharactersCheck.class);
        moduleConfig.addProperty("allowByTailComment", "true");

        final String[] expectedViolation = {
            "4:45: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[."
                + "/IDENT[@text="
                    + "'SuppressionXpathRegressionAvoidEscapedUnicodeCharactersTailComment']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                + "/ASSIGN/EXPR[./STRING_LITERAL[@text='\\u03bcs']]",
            "/COMPILATION_UNIT/CLASS_DEF[."
                + "/IDENT[@text="
                    + "'SuppressionXpathRegressionAvoidEscapedUnicodeCharactersTailComment']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                + "/ASSIGN/EXPR/STRING_LITERAL[@text='\\u03bcs']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testAllCharactersEscaped() throws Exception {
        final File fileToProcess = new File(getPath(
            "SuppressionXpathRegressionAvoidEscapedUnicodeCharactersAllEscaped.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AvoidEscapedUnicodeCharactersCheck.class);
        moduleConfig.addProperty("allowIfAllCharactersEscaped", "true");

        final String[] expectedViolation = {
            "4:34: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[."
                    + "/IDENT[@text="
                        + "'SuppressionXpathRegressionAvoidEscapedUnicodeCharactersAllEscaped']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                    + "/ASSIGN/EXPR[./STRING_LITERAL[@text='\\u03bcs']]",
                "/COMPILATION_UNIT/CLASS_DEF[."
                    + "/IDENT[@text="
                        + "'SuppressionXpathRegressionAvoidEscapedUnicodeCharactersAllEscaped']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                    + "/ASSIGN/EXPR/STRING_LITERAL[@text='\\u03bcs']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testNonPrintableCharacters() throws Exception {
        final File fileToProcess = new File(getPath(
            "SuppressionXpathRegressionAvoidEscapedUnicodeCharactersNonPrintable.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AvoidEscapedUnicodeCharactersCheck.class);
        moduleConfig.addProperty("allowNonPrintableEscapes", "true");

        final String[] expectedViolation = {
            "4:34: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[."
                + "/IDENT[@text="
                    + "'SuppressionXpathRegressionAvoidEscapedUnicodeCharactersNonPrintable']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                + "/ASSIGN/EXPR[./STRING_LITERAL[@text='\\u03bcs']]",
            "/COMPILATION_UNIT/CLASS_DEF[."
                + "/IDENT[@text="
                    + "'SuppressionXpathRegressionAvoidEscapedUnicodeCharactersNonPrintable']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                + "/ASSIGN/EXPR/STRING_LITERAL[@text='\\u03bcs']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
