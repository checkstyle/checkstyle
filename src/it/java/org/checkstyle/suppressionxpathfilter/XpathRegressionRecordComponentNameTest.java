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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.RecordComponentNameCheck;

public class XpathRegressionRecordComponentNameTest extends AbstractXpathTestSupport {

    private final String checkName = RecordComponentNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDefault() throws Exception {
        final File fileToProcess = new File(getNonCompilablePath(
            "InputXpathRecordComponentNameDefault.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(RecordComponentNameCheck.class);

        final String[] expectedViolation = {
            "6:56: " + getCheckMessage(RecordComponentNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN,
                "_value", "^[a-z][a-zA-Z0-9]*$"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/RECORD_DEF[./IDENT[@text='InputXpathRecordComponentNameDefault']]"
                + "/RECORD_COMPONENTS/RECORD_COMPONENT_DEF/IDENT[@text='_value']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testFormat() throws Exception {
        final File fileToProcess = new File(getNonCompilablePath(
            "InputXpathRecordComponentNameFormat.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(RecordComponentNameCheck.class);
        moduleConfig.addProperty("format", "^_[a-z][a-zA-Z0-9]*$");

        final String[] expectedViolation = {
            "9:32: " + getCheckMessage(RecordComponentNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN,
                "otherValue", "^_[a-z][a-zA-Z0-9]*$"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRecordComponentNameFormat']]/OBJBLOCK"
                + "/RECORD_DEF[./IDENT[@text='MyRecord']]"
                + "/RECORD_COMPONENTS/RECORD_COMPONENT_DEF/IDENT[@text='otherValue']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
