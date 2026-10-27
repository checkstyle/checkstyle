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

import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck;

public class XpathRegressionNumericalPrefixesInfixesSuffixesCharacterCaseTest
    extends AbstractXpathTestSupport {

    private final String checkName =
        NumericalPrefixesInfixesSuffixesCharacterCaseCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter"
            + "/numericalprefixesinfixessuffixescharactercase";
    }

    @Test
    public void testIntLiteralPrefix() throws Exception {
        final String[] expectedViolation = {
            "11:13: " + getCheckMessage(
                NumericalPrefixesInfixesSuffixesCharacterCaseCheck.class,
                NumericalPrefixesInfixesSuffixesCharacterCaseCheck.MSG_PREFIX),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNumericalPrefixesInfixesSuffixesCharacterCaseOne']]"
                + "/OBJBLOCK/VARIABLE_DEF"
                + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                + "[./NUM_INT[@text='0X1A']]",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNumericalPrefixesInfixesSuffixesCharacterCaseOne']]"
                + "/OBJBLOCK/VARIABLE_DEF"
                + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                + "/NUM_INT[@text='0X1A']"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathNumericalPrefixesInfixesSuffixesCharacterCaseOne.java"),
            expectedXpathQueries,
            expectedViolation);
    }

    @Test
    public void testLongLiteralPrefix() throws Exception {
        final String[] expectedViolation = {
            "11:14: " + getCheckMessage(
                NumericalPrefixesInfixesSuffixesCharacterCaseCheck.class,
                NumericalPrefixesInfixesSuffixesCharacterCaseCheck.MSG_PREFIX),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNumericalPrefixesInfixesSuffixesCharacterCaseTwo']]"
                + "/OBJBLOCK/VARIABLE_DEF"
                + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                + "[./NUM_LONG[@text='0X10L']]",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNumericalPrefixesInfixesSuffixesCharacterCaseTwo']]"
                + "/OBJBLOCK/VARIABLE_DEF"
                + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                + "/NUM_LONG[@text='0X10L']"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathNumericalPrefixesInfixesSuffixesCharacterCaseTwo.java"),
            expectedXpathQueries,
            expectedViolation);
    }

    @Test
    public void testFloatLiteralInfixAndSuffix() throws Exception {
        final String[] expectedViolation = {
            "11:15: " + getCheckMessage(
                NumericalPrefixesInfixesSuffixesCharacterCaseCheck.class,
                NumericalPrefixesInfixesSuffixesCharacterCaseCheck.MSG_INFIX),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNumericalPrefixesInfixesSuffixesCharacterCaseThree']]"
                + "/OBJBLOCK/VARIABLE_DEF"
                + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                + "[./NUM_FLOAT[@text='1.2E3F']]",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNumericalPrefixesInfixesSuffixesCharacterCaseThree']]"
                + "/OBJBLOCK/VARIABLE_DEF"
                + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                + "/NUM_FLOAT[@text='1.2E3F']"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathNumericalPrefixesInfixesSuffixesCharacterCaseThree.java"),
            expectedXpathQueries,
            expectedViolation);
    }

    @Test
    public void testDoubleLiteralSuffix() throws Exception {
        final String[] expectedViolation = {
            "11:16: " + getCheckMessage(
                NumericalPrefixesInfixesSuffixesCharacterCaseCheck.class,
                NumericalPrefixesInfixesSuffixesCharacterCaseCheck.MSG_SUFFIX),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNumericalPrefixesInfixesSuffixesCharacterCaseFour']]"
                + "/OBJBLOCK/VARIABLE_DEF"
                + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                + "[./NUM_DOUBLE[@text='10D']]",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNumericalPrefixesInfixesSuffixesCharacterCaseFour']]"
                + "/OBJBLOCK/VARIABLE_DEF"
                + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                + "/NUM_DOUBLE[@text='10D']"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathNumericalPrefixesInfixesSuffixesCharacterCaseFour.java"),
            expectedXpathQueries,
            expectedViolation);
    }

}
