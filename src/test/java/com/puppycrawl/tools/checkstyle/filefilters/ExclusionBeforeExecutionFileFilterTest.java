////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class ExclusionBeforeExecutionFileFilterTest extends AbstractModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filefilters";
    }

    @Test
    public void testAccept() {
        final String fileName = "BAD";
        final BeforeExecutionExclusionFileFilter filter =
                createExclusionBeforeExecutionFileFilter(fileName);

        assertTrue("Should accept if file does not exist", filter.accept("ATest.java"));
    }

    @Test
    public void testAcceptOnNullFile() {
        final String fileName = null;
        final BeforeExecutionExclusionFileFilter filter =
                createExclusionBeforeExecutionFileFilter(fileName);

        assertTrue("Should accept if file is null", filter.accept("AnyJava.java"));
    }

    @Test
    public void testReject() {
        final String fileName = "Test";
        final BeforeExecutionExclusionFileFilter filter =
                createExclusionBeforeExecutionFileFilter(fileName);

        assertFalse("Should reject file, but did not", filter.accept("ATest.java"));
    }

    @Test
    public void testRejectBadFile() throws Exception {
        final DefaultConfiguration filterConfig =
                createBeforeExecutionFileFilterConfig(BeforeExecutionExclusionFileFilter.class);
        filterConfig.addAttribute("fileNamePattern", "IncorrectClass\\.java");

        final String[] violations = CommonUtils.EMPTY_STRING_ARRAY;
        verify(createChecker(filterConfig), getNonCompilablePath("InputIncorrectClass.java"),
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

    private static DefaultConfiguration createBeforeExecutionFileFilterConfig(Class<?> aClass) {
        return new DefaultConfiguration(aClass.getName());
    }

    @Override
    protected DefaultConfiguration createTreeWalkerConfig(Configuration config) {
        final DefaultConfiguration dc =
                new DefaultConfiguration("configuration");
        dc.addChild(config);
        return dc;
    }
}
