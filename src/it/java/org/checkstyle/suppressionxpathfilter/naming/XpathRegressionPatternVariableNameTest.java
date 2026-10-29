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

package org.checkstyle.suppressionxpathfilter.naming;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.naming.PatternVariableNameCheck;

public class XpathRegressionPatternVariableNameTest extends AbstractXpathTestSupport {

    private final String checkName = PatternVariableNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/naming/patternvariablename";
    }

    @Test
    public void testOne() throws Exception {
        final String defaultPattern = "^([a-z][a-zA-Z0-9]*|_)$";

        final String[] expectedViolation = {
            "13:33: " + getCheckMessage(PatternVariableNameCheck.class,
                    PatternVariableNameCheck.MSG_INVALID_PATTERN,
                    "STRING1", defaultPattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathPatternVariableNameOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='MyClass']]/SLIST/LITERAL_IF/EXPR/"
                + "LITERAL_INSTANCEOF[./IDENT[@text='o1']]/PATTERN_VARIABLE_DEF/"
                + "IDENT[@text='STRING1']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathPatternVariableNameOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String nonDefaultPattern = "^_[a-zA-Z0-9]*$";

        final String[] expectedViolation = {
            "13:34: " + getCheckMessage(PatternVariableNameCheck.class,
                    PatternVariableNameCheck.MSG_INVALID_PATTERN, "s", nonDefaultPattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathPatternVariableNameTwo']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='MyClass']]/SLIST/LITERAL_IF/EXPR/"
                + "LITERAL_INSTANCEOF[./IDENT[@text='o1']]/"
                + "PATTERN_VARIABLE_DEF/IDENT[@text='s']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathPatternVariableNameTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String nonDefaultPattern = "^[a-z](_?[a-zA-Z0-9]+)*$";

        final String[] expectedViolation = {
            "13:34: " + getCheckMessage(PatternVariableNameCheck.class,
                PatternVariableNameCheck.MSG_INVALID_PATTERN, "STR", nonDefaultPattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathPatternVariableNameThree']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='MyClass']]/SLIST/LITERAL_IF/"
                    + "EXPR/LITERAL_INSTANCEOF[./IDENT[@text='o1']]/"
                    + "PATTERN_VARIABLE_DEF/IDENT[@text='STR']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathPatternVariableNameThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testFour() throws Exception {
        final String nonDefaultPattern = "^[a-z][_a-zA-Z0-9]{2,}$";

        final String[] expectedViolation = {
            "13:34: " + getCheckMessage(PatternVariableNameCheck.class,
                PatternVariableNameCheck.MSG_INVALID_PATTERN, "st", nonDefaultPattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathPatternVariableNameFour']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='MyClass']]/SLIST/LITERAL_IF/EXPR/"
                    + "LITERAL_INSTANCEOF[./IDENT[@text='o1']]/"
                    + "PATTERN_VARIABLE_DEF/IDENT[@text='st']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathPatternVariableNameFour.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
