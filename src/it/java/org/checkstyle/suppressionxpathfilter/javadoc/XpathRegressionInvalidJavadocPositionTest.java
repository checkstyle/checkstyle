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

package org.checkstyle.suppressionxpathfilter.javadoc;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocPositionCheck;

public class XpathRegressionInvalidJavadocPositionTest extends AbstractXpathTestSupport {

    private final String checkName = InvalidJavadocPositionCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/javadoc/invalidjavadocposition";
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "11:1: " + getCheckMessage(InvalidJavadocPositionCheck.class,
                InvalidJavadocPositionCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathInvalidJavadocPositionOne']]"
                    + "/MODIFIERS/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                    + "[@text='* // warn\\n * Javadoc Comment\\n ']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInvalidJavadocPositionOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "12:1: " + getCheckMessage(InvalidJavadocPositionCheck.class,
                InvalidJavadocPositionCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathInvalidJavadocPositionTwo']]"
                    + "/OBJBLOCK/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                    + "[@text='* // warn\\n * Javadoc comment\\n ']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInvalidJavadocPositionTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "13:5: " + getCheckMessage(InvalidJavadocPositionCheck.class,
                InvalidJavadocPositionCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathInvalidJavadocPositionThree']]/"
                    + "OBJBLOCK/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                    + "[@text='* // warn\\n     * Javadoc comment\\n     ']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInvalidJavadocPositionThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testFour() throws Exception {
        final String[] expectedViolation = {
            "11:5: " + getCheckMessage(InvalidJavadocPositionCheck.class,
                InvalidJavadocPositionCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathInvalidJavadocPositionFour']]"
                + "/OBJBLOCK/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                + "[@text='* // warn\\n     * Javadoc Comment\\n     ']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInvalidJavadocPositionFour.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testFive() throws Exception {
        final String[] expectedViolation = {
            "12:9: " + getCheckMessage(InvalidJavadocPositionCheck.class,
                InvalidJavadocPositionCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathInvalidJavadocPositionFive']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                + "[@text='* // warn\\n         * Javadoc comment\\n         ']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInvalidJavadocPositionFive.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testSix() throws Exception {
        final String[] expectedViolation = {
            "12:5: " + getCheckMessage(InvalidJavadocPositionCheck.class,
                InvalidJavadocPositionCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathInvalidJavadocPositionSix']]"
                + "/OBJBLOCK/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                + "[@text='* // warn\\n     * Javadoc Comment\\n     ']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInvalidJavadocPositionSix.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
