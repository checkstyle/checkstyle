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

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocPositionCheck;

public class XpathRegressionInvalidJavadocPositionTest extends AbstractXpathTestSupport {

    private final String checkName = InvalidJavadocPositionCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionInvalidJavadocPositionOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(InvalidJavadocPositionCheck.class);

        final String[] expectedViolation = {
            "4:1: " + getCheckMessage(InvalidJavadocPositionCheck.class,
                InvalidJavadocPositionCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionInvalidJavadocPositionOne']]"
                    + "/MODIFIERS/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                    + "[@text='* // warn\\n * Javadoc Comment\\n ']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionInvalidJavadocPositionTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(InvalidJavadocPositionCheck.class);

        final String[] expectedViolation = {
            "5:1: " + getCheckMessage(InvalidJavadocPositionCheck.class,
                InvalidJavadocPositionCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='SuppressionXpathRegressionInvalidJavadocPositionTwo']]"
                    + "/OBJBLOCK/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                    + "[@text='* // warn\\n * Javadoc comment\\n ']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionInvalidJavadocPositionThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(InvalidJavadocPositionCheck.class);

        final String[] expectedViolation = {
            "6:5: " + getCheckMessage(InvalidJavadocPositionCheck.class,
                InvalidJavadocPositionCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionInvalidJavadocPositionThree']]/"
                    + "OBJBLOCK/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                    + "[@text='* // warn\\n     * Javadoc comment\\n     ']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionInvalidJavadocPositionFour.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(InvalidJavadocPositionCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(InvalidJavadocPositionCheck.class,
                InvalidJavadocPositionCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionInvalidJavadocPositionFour']]"
                + "/OBJBLOCK/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                + "[@text='* // warn\\n     * Javadoc Comment\\n     ']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testFive() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionInvalidJavadocPositionFive.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(InvalidJavadocPositionCheck.class);

        final String[] expectedViolation = {
            "5:9: " + getCheckMessage(InvalidJavadocPositionCheck.class,
                InvalidJavadocPositionCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionInvalidJavadocPositionFive']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                + "[@text='* // warn\\n         * Javadoc comment\\n         ']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testSix() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionInvalidJavadocPositionSix.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(InvalidJavadocPositionCheck.class);

        final String[] expectedViolation = {
            "5:5: " + getCheckMessage(InvalidJavadocPositionCheck.class,
                InvalidJavadocPositionCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionInvalidJavadocPositionSix']]"
                + "/OBJBLOCK/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                + "[@text='* // warn\\n     * Javadoc Comment\\n     ']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
