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

package com.puppycrawl.tools.checkstyle.filefilters;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck.MSG_KEY;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class BeforeExecutionExclusionFileFilterTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filefilters/beforeexecutionexclusionfilefilter";
    }

    @Test
    public void testAccept() {
        final String fileName = "BAD";
        final BeforeExecutionExclusionFileFilter filter =
                createExclusionBeforeExecutionFileFilter(fileName);

        assertWithMessage("Should accept if file does not exist")
                .that(filter.accept("ATest.java"))
                .isTrue();
    }

    @Test
    public void testAcceptOnNullFile() {
        final String fileName = null;
        final BeforeExecutionExclusionFileFilter filter =
                createExclusionBeforeExecutionFileFilter(fileName);

        assertWithMessage("Should accept if file is null")
                .that(filter.accept("AnyJava.java"))
                .isTrue();
    }

    @Test
    public void testReject() {
        final String fileName = "Test";
        final BeforeExecutionExclusionFileFilter filter =
                createExclusionBeforeExecutionFileFilter(fileName);

        assertWithMessage("Should reject file, but did not")
                .that(filter.accept("ATest.java"))
                .isFalse();
    }

    @Test
    public void testFileExclusion() throws Exception {
        final String[] filteredViolations = CommonUtil.EMPTY_STRING_ARRAY;

        final String[] unfilteredViolations = {
            "17:13: " + getCheckMessage(FinalLocalVariableCheck.class, MSG_KEY, "i"),
        };

        verifyFilterWithInlineConfigParser(
                getPath("InputBeforeExecutionExclusionFileFilter.java"),
                unfilteredViolations, filteredViolations
        );
    }

    private static BeforeExecutionExclusionFileFilter
            createExclusionBeforeExecutionFileFilter(String fileName) {
        final BeforeExecutionExclusionFileFilter exclusionBeforeExecutionFileFilter =
                new BeforeExecutionExclusionFileFilter();
        if (fileName != null) {
            exclusionBeforeExecutionFileFilter.setFileNamePattern(Pattern.compile(fileName));
        }
        return exclusionBeforeExecutionFileFilter;
    }

}
