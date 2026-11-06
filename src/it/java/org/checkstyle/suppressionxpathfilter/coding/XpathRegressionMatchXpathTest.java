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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.MatchXpathCheck;

public class XpathRegressionMatchXpathTest extends AbstractXpathTestSupport {

    private final String checkName = MatchXpathCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/matchxpath";
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "11:5: " + getCheckMessage(MatchXpathCheck.class, MatchXpathCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathMatchXpathOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathMatchXpathOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathMatchXpathOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "12:25: " + getCheckMessage(MatchXpathCheck.class, MatchXpathCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMatchXpathTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='func1']]"
                        + "/LITERAL_THROWS[./IDENT[@text='RuntimeException']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEncodedQuoteString() throws Exception {
        final String[] expectedViolation = {
            "11:24: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathEncodedQuoteString.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEncodedLessString() throws Exception {
        final String[] expectedViolation = {
            "11:23: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathEncodedLessString.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEncodedNewLineString() throws Exception {
        final String[] expectedViolation = {
            "11:26: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathEncodedNewLineString.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testGreaterString() throws Exception {
        final String[] expectedViolation = {
            "11:26: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathEncodedGreaterString.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEncodedAmpString() throws Exception {
        final String[] expectedViolation = {
            "11:28: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathEncodedAmpString.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEncodedAposString() throws Exception {
        final String[] expectedViolation = {
            "11:23: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathEncodedAposString.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEncodedCarriageString() throws Exception {
        final String[] expectedViolation = {
            "11:27: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathEncodedCarriageString.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEncodedAmpersandChars() throws Exception {
        final String[] expectedViolationForAmpersand = {
            "11:20: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathEncodedAmpChar.java"),
                expectedXpathQueriesForAmpersand,
                expectedViolationForAmpersand);
    }

    @Test
    public void testEncodedQuoteChar() throws Exception {
        final String[] expectedViolationsForQuote = {
            "11:21: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathEncodedQuoteChar.java"),
                expectedXpathQueriesForQuote,
                expectedViolationsForQuote);
    }

    @Test
    public void testEncodedLessChar() throws Exception {
        final String[] expectedViolationsForLess = {
            "11:21: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathEncodedLessChar.java"),
                expectedXpathQueriesForLess,
                expectedViolationsForLess);
    }

    @Test
    public void testEncodedAposChar() throws Exception {
        final String[] expectedViolationsForApos = {
            "11:21: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathEncodedAposChar.java"),
                expectedXpathQueriesForApos,
                expectedViolationsForApos);
    }

    @Test
    public void testEncodedGreaterChar() throws Exception {
        final String[] expectedViolationsForGreater = {
            "11:24: " + getCheckMessage(IllegalTokenCheck.class, IllegalTokenCheck.MSG_KEY,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathEncodedGreaterChar.java"),
                expectedXpathQueriesForGreater,
                expectedViolationsForGreater);
    }

    @Test
    public void testFollowing() throws Exception {
        final String[] expectedViolation = {
            "12:1: " + getCheckMessage(MatchXpathCheck.class, MatchXpathCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='InputXpathMatchXpathThree']]"
                        + "/OBJBLOCK/RCURLY"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMatchXpathThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
