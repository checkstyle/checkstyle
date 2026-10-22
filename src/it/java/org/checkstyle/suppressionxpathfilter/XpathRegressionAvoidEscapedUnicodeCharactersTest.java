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

package org.checkstyle.suppressionxpathfilter;

import static com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck.MSG_KEY;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck;

public class XpathRegressionAvoidEscapedUnicodeCharactersTest extends AbstractXpathTestSupport {

    private final String checkName = AvoidEscapedUnicodeCharactersCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expectedViolation = {
            "15:34: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathAvoidEscapedUnicodeCharactersDefault']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev2']]"
                + "/ASSIGN/EXPR[./STRING_LITERAL[@text='\\u03bcs']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathAvoidEscapedUnicodeCharactersDefault']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev2']]"
                + "/ASSIGN/EXPR/STRING_LITERAL[@text='\\u03bcs']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAvoidEscapedUnicodeCharactersDefault.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testControlCharacters() throws Exception {
        final String[] expectedViolation = {
            "15:34: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[."
                + "/IDENT[@text="
                    + "'InputXpathAvoidEscapedUnicodeCharactersControlCharacters']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                + "/ASSIGN/EXPR[./STRING_LITERAL[@text='\\u03bcs']]",
            "/COMPILATION_UNIT/CLASS_DEF[."
                + "/IDENT[@text="
                    + "'InputXpathAvoidEscapedUnicodeCharactersControlCharacters']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                + "/ASSIGN/EXPR/STRING_LITERAL[@text='\\u03bcs']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAvoidEscapedUnicodeCharactersControlCharacters.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTailComment() throws Exception {
        final String[] expectedViolation = {
            "15:34: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[."
                + "/IDENT[@text="
                    + "'InputXpathAvoidEscapedUnicodeCharactersTailComment']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                + "/ASSIGN/EXPR[./STRING_LITERAL[@text='\\u03bcs']]",
            "/COMPILATION_UNIT/CLASS_DEF[."
                + "/IDENT[@text="
                    + "'InputXpathAvoidEscapedUnicodeCharactersTailComment']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                + "/ASSIGN/EXPR/STRING_LITERAL[@text='\\u03bcs']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAvoidEscapedUnicodeCharactersTailComment.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testAllCharactersEscaped() throws Exception {
        final String[] expectedViolation = {
            "15:34: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[."
                    + "/IDENT[@text="
                        + "'InputXpathAvoidEscapedUnicodeCharactersAllEscaped']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                    + "/ASSIGN/EXPR[./STRING_LITERAL[@text='\\u03bcs']]",
                "/COMPILATION_UNIT/CLASS_DEF[."
                    + "/IDENT[@text="
                        + "'InputXpathAvoidEscapedUnicodeCharactersAllEscaped']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                    + "/ASSIGN/EXPR/STRING_LITERAL[@text='\\u03bcs']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAvoidEscapedUnicodeCharactersAllEscaped.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testNonPrintableCharacters() throws Exception {
        final String[] expectedViolation = {
            "15:34: " + getCheckMessage(AvoidEscapedUnicodeCharactersCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[."
                + "/IDENT[@text="
                    + "'InputXpathAvoidEscapedUnicodeCharactersNonPrintable']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                + "/ASSIGN/EXPR[./STRING_LITERAL[@text='\\u03bcs']]",
            "/COMPILATION_UNIT/CLASS_DEF[."
                + "/IDENT[@text="
                    + "'InputXpathAvoidEscapedUnicodeCharactersNonPrintable']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='unitAbbrev9']]"
                + "/ASSIGN/EXPR/STRING_LITERAL[@text='\\u03bcs']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAvoidEscapedUnicodeCharactersNonPrintable.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
