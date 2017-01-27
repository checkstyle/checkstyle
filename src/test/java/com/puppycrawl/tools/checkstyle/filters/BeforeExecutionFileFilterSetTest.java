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

package com.puppycrawl.tools.checkstyle.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilterSet;
import com.puppycrawl.tools.checkstyle.filefilters.BeforeExecutionExclusionFileFilter;

public class BeforeExecutionFileFilterSetTest {
    @Test
    public void testAccept() {
        final String fileName = "BAD";
        final BeforeExecutionExclusionFileFilter filter = new BeforeExecutionExclusionFileFilter();
        filter.setFileNamePattern(Pattern.compile(fileName));
        final BeforeExecutionFileFilterSet set = new BeforeExecutionFileFilterSet();
        set.addBeforeExecutionFileFilter(filter);

        assertTrue(set.accept("ATest.java"));
    }

    @Test
    public void testReject() {
        final String fileName = "Test";
        final BeforeExecutionExclusionFileFilter filter = new BeforeExecutionExclusionFileFilter();
        filter.setFileNamePattern(Pattern.compile(fileName));
        final BeforeExecutionFileFilterSet set = new BeforeExecutionFileFilterSet();
        set.addBeforeExecutionFileFilter(filter);

        assertFalse(set.accept("ATest.java"));
    }

    @Test
    public void testGetFilters2() {
        final BeforeExecutionFileFilterSet filterSet = new BeforeExecutionFileFilterSet();
        filterSet.addBeforeExecutionFileFilter(new BeforeExecutionExclusionFileFilter());
        assertEquals("size is the same", 1, filterSet.getBeforeExecutionFileFilters().size());
    }

    @Test
    public void testToString2() {
        final BeforeExecutionFileFilterSet filterSet = new BeforeExecutionFileFilterSet();
        filterSet.addBeforeExecutionFileFilter(new BeforeExecutionExclusionFileFilter());
        assertNotNull("size is the same", filterSet.toString());
    }
}
