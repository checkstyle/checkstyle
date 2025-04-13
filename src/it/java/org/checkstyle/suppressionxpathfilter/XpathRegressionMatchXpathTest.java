///
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
///

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.MatchXpathCheck;

public class XpathRegressionMatchXpathTest extends AbstractXpathTestSupport {

    private final String checkName = MatchXpathCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MatchXpathCheck.class);
        moduleConfig.addProperty("query", "//METHOD_DEF[./IDENT[@text='test']]");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(MatchXpathCheck.class, MatchXpathCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathMatchXpathOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathMatchXpathOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathMatchXpathOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MatchXpathCheck.class);
        moduleConfig.addProperty("query", "//LITERAL_THROWS[./IDENT[@text='Throwable' or "
                + "@text='RuntimeException' or ends-with(@text, 'Error')]]");

        final String[] expectedViolation = {
            "4:25: " + getCheckMessage(MatchXpathCheck.class, MatchXpathCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMatchXpathTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='func1']]"
                        + "/LITERAL_THROWS[./IDENT[@text='RuntimeException']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testEncodedQuoteString() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathEncodedQuoteString.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);
        moduleConfig.addProperty("tokens", "STRING_LITERAL");

        final String[] expectedViolation = {
            "4:24: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
                    "\"\\\"testOne\\\"\""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMatchXpathEncodedQuoteString']]/"
                    + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='quoteChar']]/ASSIGN/EXPR"
                    + "[./STRING_LITERAL[@text='\\&quot;testOne\\&quot;']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMatchXpathEncodedQuoteString']]/"
                    + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='quoteChar']]/ASSIGN/EXPR"
                    + "/STRING_LITERAL[@text='\\&quot;testOne\\&quot;']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testEncodedLessString() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathEncodedLessString.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);
        moduleConfig.addProperty("tokens", "STRING_LITERAL");

        final String[] expectedViolation = {
            "4:23: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
                    "\"<testTwo\""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMatchXpathEncodedLessString']]/"
                    + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='lessChar']]/ASSIGN/EXPR"
                    + "[./STRING_LITERAL[@text='&lt;testTwo']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMatchXpathEncodedLessString']]/"
                    + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='lessChar']]/ASSIGN/EXPR/"
                    + "STRING_LITERAL[@text='&lt;testTwo']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testEncodedNewLineString() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathEncodedNewLineString.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);
        moduleConfig.addProperty("tokens", "STRING_LITERAL");

        final String[] expectedViolation = {
            "4:26: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
                    "\"testFive\\n\""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMatchXpathEncodedNewLineString']]/"
                    + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='newLineChar']]/ASSIGN/EXPR"
                    + "[./STRING_LITERAL[@text='testFive\\n']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMatchXpathEncodedNewLineString']]/"
                    + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='newLineChar']]/ASSIGN/EXPR"
                    + "/STRING_LITERAL[@text='testFive\\n']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testGreaterString() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathEncodedGreaterString.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);
        moduleConfig.addProperty("tokens", "STRING_LITERAL");

        final String[] expectedViolation = {
            "4:26: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
                    "\">testFour\""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMatchXpathEncodedGreaterString']]/"
                    + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='greaterChar']]/ASSIGN/EXPR"
                    + "[./STRING_LITERAL[@text='&gt;testFour']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMatchXpathEncodedGreaterString']]/"
                    + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='greaterChar']]/ASSIGN/EXPR"
                    + "/STRING_LITERAL[@text='&gt;testFour']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testEncodedAmpString() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathEncodedAmpString.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);
        moduleConfig.addProperty("tokens", "STRING_LITERAL");

        final String[] expectedViolation = {
            "4:28: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
                    "\"&testThree\""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMatchXpathEncodedAmpString']]/"
                    + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='ampersandChar']]/ASSIGN/EXPR"
                    + "[./STRING_LITERAL[@text='&amp;testThree']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMatchXpathEncodedAmpString']]/"
                    + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='ampersandChar']]/ASSIGN/EXPR"
                    + "/STRING_LITERAL[@text='&amp;testThree']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testEncodedAposString() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathEncodedAposString.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);
        moduleConfig.addProperty("tokens", "STRING_LITERAL");

        final String[] expectedViolation = {
            "4:23: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
                    "\"'SingleQuoteOnBothSide'\""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMatchXpathEncodedAposString']]/"
                    + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='aposChar']]/ASSIGN/EXPR"
                    + "[./STRING_LITERAL[@text='&apos;&apos;SingleQuoteOnBothSide&apos;&apos;']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMatchXpathEncodedAposString']]/"
                    + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='aposChar']]/ASSIGN/EXPR"
                    + "/STRING_LITERAL[@text='&apos;&apos;SingleQuoteOnBothSide&apos;&apos;']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testEncodedCarriageString() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathMatchXpathEncodedCarriageString.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);
        moduleConfig.addProperty("tokens", "STRING_LITERAL");

        final String[] expectedViolation = {
            "4:27: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
                    "\"carriageCharAtEnd\\r\""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMatchXpathEncodedCarriageString"
                        + "']]/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='carriageChar']]/ASSIGN"
                        + "/EXPR[./STRING_LITERAL[@text='carriageCharAtEnd\\r']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMatchXpathEncodedCarriageString"
                        + "']]/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='carriageChar']]/ASSIGN"
                        + "/EXPR/STRING_LITERAL[@text='carriageCharAtEnd\\r']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testEncodedAmpersandChars() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathEncodedAmpChar.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);
        moduleConfig.addProperty("tokens", "CHAR_LITERAL");

        final String[] expectedViolationForAmpersand = {
            "4:20: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
                    "'&'"),
        };

        final List<String> expectedXpathQueriesForAmpersand = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMatchXpathEncodedAmpChar']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='ampChar']]/ASSIGN/EXPR"
                        + "[./CHAR_LITERAL[@text='&apos;&apos;&amp;&apos;&apos;']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMatchXpathEncodedAmpChar']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='ampChar']]/ASSIGN/EXPR"
                        + "/CHAR_LITERAL[@text='&apos;&apos;&amp;&apos;&apos;']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolationForAmpersand,
                expectedXpathQueriesForAmpersand);
    }

    @Test
    public void testEncodedQuoteChar() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathEncodedQuoteChar.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);
        moduleConfig.addProperty("tokens", "CHAR_LITERAL");

        final String[] expectedViolationsForQuote = {
            "4:21: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
                    "'\\\"'"),
        };

        final List<String> expectedXpathQueriesForQuote = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMatchXpathEncodedQuoteChar']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='quotChar']]/ASSIGN/EXPR"
                        + "[./CHAR_LITERAL[@text='&apos;&apos;\\&quot;&apos;&apos;']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMatchXpathEncodedQuoteChar']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='quotChar']]/ASSIGN/EXPR/"
                        + "CHAR_LITERAL[@text='&apos;&apos;\\&quot;&apos;&apos;']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolationsForQuote,
                expectedXpathQueriesForQuote);
    }

    @Test
    public void testEncodedLessChar() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathEncodedLessChar.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);
        moduleConfig.addProperty("tokens", "CHAR_LITERAL");

        final String[] expectedViolationsForLess = {
            "4:21: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
                    "'<'"),
        };

        final List<String> expectedXpathQueriesForLess = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMatchXpathEncodedLessChar']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='lessChar']]/ASSIGN/EXPR"
                        + "[./CHAR_LITERAL[@text='&apos;&apos;&lt;&apos;&apos;']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMatchXpathEncodedLessChar']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='lessChar']]/ASSIGN/EXPR/"
                        + "CHAR_LITERAL[@text='&apos;&apos;&lt;&apos;&apos;']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolationsForLess,
                expectedXpathQueriesForLess);
    }

    @Test
    public void testEncodedAposChar() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathEncodedAposChar.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);
        moduleConfig.addProperty("tokens", "CHAR_LITERAL");

        final String[] expectedViolationsForApos = {
            "4:21: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
                    "'\\''"),
        };

        final List<String> expectedXpathQueriesForApos = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMatchXpathEncodedAposChar']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='aposChar']]/ASSIGN/EXPR"
                        + "[./CHAR_LITERAL[@text='&apos;&apos;\\&apos;&apos;&apos;&apos;']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMatchXpathEncodedAposChar']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='aposChar']]/ASSIGN/EXPR/"
                        + "CHAR_LITERAL[@text='&apos;&apos;\\&apos;&apos;&apos;&apos;']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolationsForApos,
                expectedXpathQueriesForApos);
    }

    @Test
    public void testEncodedGreaterChar() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathEncodedGreaterChar.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);
        moduleConfig.addProperty("tokens", "CHAR_LITERAL");

        final String[] expectedViolationsForGreater = {
            "4:24: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
                    "'>'"),
        };

        final List<String> expectedXpathQueriesForGreater = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='"
                        + "InputXpathMatchXpathEncodedGreaterChar']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='greaterChar']]/ASSIGN/EXPR"
                        + "[./CHAR_LITERAL[@text='&apos;&apos;&gt;&apos;&apos;']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='"
                        + "InputXpathMatchXpathEncodedGreaterChar']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='greaterChar']]/ASSIGN/EXPR/"
                        + "CHAR_LITERAL[@text='&apos;&apos;&gt;&apos;&apos;']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolationsForGreater,
                expectedXpathQueriesForGreater);
    }

    @Test
    public void testFollowing() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMatchXpathThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MatchXpathCheck.class);
        moduleConfig.addProperty("query",
                "//METHOD_DEF/following::*[1]");

        final String[] expectedViolation = {
            "5:1: " + getCheckMessage(MatchXpathCheck.class, MatchXpathCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathMatchXpathThree']]"
                        + "/OBJBLOCK/RCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
