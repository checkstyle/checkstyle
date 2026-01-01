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

package org.checkstyle.suppressionxpathfilter.annotation;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideOnRecordAccessorCheck;

public class XpathRegressionMissingOverrideOnRecordAccessorTest
        extends AbstractXpathTestSupport {

    private final String checkName = MissingOverrideOnRecordAccessorCheck.class.getSimpleName();

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/annotation/missingoverrideonrecordaccessor";
    }

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testSimple() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathMissingOverrideOnRecordAccessorSimple.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MissingOverrideOnRecordAccessorCheck.class);

        final String[] expectedViolation = {
            "5:5: " + getCheckMessage(MissingOverrideOnRecordAccessorCheck.class,
                MissingOverrideOnRecordAccessorCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/RECORD_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideOnRecordAccessorSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='name']]",
            "/COMPILATION_UNIT/RECORD_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideOnRecordAccessorSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='name']]/MODIFIERS",
            "/COMPILATION_UNIT/RECORD_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideOnRecordAccessorSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='name']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testNested() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathMissingOverrideOnRecordAccessorNested.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MissingOverrideOnRecordAccessorCheck.class);

        final String[] expectedViolation = {
            "12:9: " + getCheckMessage(MissingOverrideOnRecordAccessorCheck.class,
                MissingOverrideOnRecordAccessorCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/RECORD_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideOnRecordAccessorNested']]"
                + "/OBJBLOCK/RECORD_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='value']]",
            "/COMPILATION_UNIT/RECORD_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideOnRecordAccessorNested']]"
                + "/OBJBLOCK/RECORD_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='value']]/MODIFIERS",
            "/COMPILATION_UNIT/RECORD_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideOnRecordAccessorNested']]"
                + "/OBJBLOCK/RECORD_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='value']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testInterface() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathMissingOverrideOnRecordAccessorInterface.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MissingOverrideOnRecordAccessorCheck.class);

        final String[] expectedViolation = {
            "9:5: " + getCheckMessage(MissingOverrideOnRecordAccessorCheck.class,
                MissingOverrideOnRecordAccessorCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/RECORD_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideOnRecordAccessorInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='name']]",
            "/COMPILATION_UNIT/RECORD_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideOnRecordAccessorInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='name']]/MODIFIERS",
            "/COMPILATION_UNIT/RECORD_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideOnRecordAccessorInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='name']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
