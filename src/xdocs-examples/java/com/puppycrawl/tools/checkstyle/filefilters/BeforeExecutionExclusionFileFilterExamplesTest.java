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

package com.puppycrawl.tools.checkstyle.filefilters;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;
import com.puppycrawl.tools.checkstyle.internal.testmodules.DebugAuditAdapter;

@Disabled("until https://github.com/checkstyle/checkstyle/issues/13345")
public class BeforeExecutionExclusionFileFilterExamplesTest
        extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filefilters/beforeexecutionexclusionfilefilter";
    }

    @Test
    public void testExample1() throws Exception {
        final String fileWithConfig = getNonCompilablePath("Example1.java");
        final String fileNotToStart = getNonCompilablePath("module-info.java");

        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(fileWithConfig);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();

        final Checker checker = createChecker(parsedConfig);
        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        checker.addListener(auditAdapter);

        execute(checker, fileWithConfig, fileNotToStart);

        final int expectedNumFilesStarted = 1;
        assertWithMessage("Unexpected number of files started")
                .that(auditAdapter.getNumFilesStarted())
                .isEqualTo(expectedNumFilesStarted);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example2.txt"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example3.txt"), expected);
    }
}
