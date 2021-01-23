////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.RecordTypeParameterNameCheck;

public class XpathRegressionRecordTypeParameterNameTest extends AbstractXpathTestSupport {

    private final String checkName = RecordTypeParameterNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(getNonCompilablePath(
                "SuppressionXpathRegressionRecordTypeParameterName1.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(RecordTypeParameterNameCheck.class);

        final String pattern = "^[A-Z]$";

        final String[] expectedViolation = {
            "7:15: " + getCheckMessage(RecordTypeParameterNameCheck.class,
                    AbstractNameCheck.MSG_INVALID_PATTERN, "foo", pattern),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/RECORD_DEF[./IDENT[@text='Other']]/"
                    + "TYPE_PARAMETERS/TYPE_PARAMETER[./IDENT[@text='foo']]",
            "/RECORD_DEF[./IDENT[@text='Other']]/TYPE_PARAMETERS/"
                    + "TYPE_PARAMETER/IDENT[@text='foo']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(getNonCompilablePath(
                "SuppressionXpathRegressionRecordTypeParameterName2.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(RecordTypeParameterNameCheck.class);

        final String pattern = "^[A-Z]$";

        final String[] expectedViolation = {
            "4:44: " + getCheckMessage(RecordTypeParameterNameCheck.class,
                    AbstractNameCheck.MSG_INVALID_PATTERN, "t", pattern),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/RECORD_DEF[./IDENT[@text='InputRecordTypeParameterName']]"
                    + "/TYPE_PARAMETERS/TYPE_PARAMETER[./IDENT[@text='t']]",
            "/RECORD_DEF[./IDENT[@text='InputRecordTypeParameterName']]"
                    + "/TYPE_PARAMETERS/TYPE_PARAMETER/IDENT[@text='t']"
                );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
