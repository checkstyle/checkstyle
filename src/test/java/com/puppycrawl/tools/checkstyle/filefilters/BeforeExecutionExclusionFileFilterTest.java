////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.filefilters;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
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

        assertTrue(filter.accept("ATest.java"), "Should accept if file does not exist");
    }

    @Test
    public void testAcceptOnNullFile() {
        final String fileName = null;
        final BeforeExecutionExclusionFileFilter filter =
                createExclusionBeforeExecutionFileFilter(fileName);

        assertTrue(filter.accept("AnyJava.java"), "Should accept if file is null");
    }

    @Test
    public void testReject() {
        final String fileName = "Test";
        final BeforeExecutionExclusionFileFilter filter =
                createExclusionBeforeExecutionFileFilter(fileName);

        assertFalse(filter.accept("ATest.java"), "Should reject file, but did not");
    }

    @Test
    public void testRejectBadFile() throws Exception {
        final DefaultConfiguration filterConfig =
                createModuleConfig(BeforeExecutionExclusionFileFilter.class);
        filterConfig.addAttribute("fileNamePattern", "IncorrectClass\\.java");

        final DefaultConfiguration checkConfig = createModuleConfig(FinalLocalVariableCheck.class);
        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);
        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);
        checkerConfig.addChild(filterConfig);

        final String[] violations = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkerConfig,
                getNonCompilablePath("InputBeforeExecutionExclusionFileFilterIncorrectClass.java"),
                violations);
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
