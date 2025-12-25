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
import com.puppycrawl.tools.checkstyle.checks.whitespace.TypeBodyPaddingCheck;

public class XpathRegressionTypeBodyPaddingTest extends AbstractXpathTestSupport {

    private final String checkName = TypeBodyPaddingCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    protected String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/whitespace/typebodypadding";
    }

    @Test
    public void testEmptyEnding() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathTypeBodyPaddingEmpty.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(TypeBodyPaddingCheck.class);
        moduleConfig.addProperty("allowEmpty", "false");
        moduleConfig.addProperty("ending", "true");
        moduleConfig.addProperty("starting", "false");

        final String[] expectedViolation = {
            "3:46: " + getCheckMessage(TypeBodyPaddingCheck.class,
                    TypeBodyPaddingCheck.MSG_END_REQUIRED),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathTypeBodyPaddingEmpty']]"
                        + "/OBJBLOCK/RCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testEmptyStarting() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathTypeBodyPaddingEmpty.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(TypeBodyPaddingCheck.class);
        moduleConfig.addProperty("allowEmpty", "false");

        final String[] expectedViolation = {
            "3:45: " + getCheckMessage(TypeBodyPaddingCheck.class,
                    TypeBodyPaddingCheck.MSG_START_REQUIRED),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathTypeBodyPaddingEmpty']]"
                        + "/OBJBLOCK",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathTypeBodyPaddingEmpty']]"
                        + "/OBJBLOCK/LCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testEnding() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathTypeBodyPaddingEnding.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(TypeBodyPaddingCheck.class);
        moduleConfig.addProperty("ending", "true");

        final String[] expectedViolation = {
            "6:1: " + getCheckMessage(TypeBodyPaddingCheck.class,
                TypeBodyPaddingCheck.MSG_END_REQUIRED),
        };
        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathTypeBodyPaddingEnding']]"
                + "/OBJBLOCK/RCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testStarting() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathTypeBodyPaddingStarting.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(TypeBodyPaddingCheck.class);

        final String[] expectedViolation = {
            "3:48: " + getCheckMessage(TypeBodyPaddingCheck.class,
                    TypeBodyPaddingCheck.MSG_START_REQUIRED),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathTypeBodyPaddingStarting']]"
                        + "/OBJBLOCK",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathTypeBodyPaddingStarting']]"
                        + "/OBJBLOCK/LCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testInner() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathTypeBodyPaddingInner.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(TypeBodyPaddingCheck.class);
        moduleConfig.addProperty("skipInner", "false");

        final String[] expectedViolation = {
            "7:24: " + getCheckMessage(TypeBodyPaddingCheck.class,
                    TypeBodyPaddingCheck.MSG_START_REQUIRED),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathTypeBodyPaddingInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='inner']]/OBJBLOCK",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathTypeBodyPaddingInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='inner']]/OBJBLOCK/LCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testLocal() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathTypeBodyPaddingLocal.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(TypeBodyPaddingCheck.class);
        moduleConfig.addProperty("skipLocal", "false");

        final String[] expectedViolation = {
            "8:26: " + getCheckMessage(TypeBodyPaddingCheck.class,
                    TypeBodyPaddingCheck.MSG_START_REQUIRED),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathTypeBodyPaddingLocal']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myMethod']]"
                        + "/SLIST/CLASS_DEF[./IDENT[@text='LocalClass']]/OBJBLOCK",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathTypeBodyPaddingLocal']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myMethod']]"
                        + "/SLIST/CLASS_DEF[./IDENT[@text='LocalClass']]/OBJBLOCK/LCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
