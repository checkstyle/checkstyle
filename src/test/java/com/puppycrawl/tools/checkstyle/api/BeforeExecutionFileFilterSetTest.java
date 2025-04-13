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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.util.Set;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.filefilters.BeforeExecutionExclusionFileFilter;

public class BeforeExecutionFileFilterSetTest {

    @Test
    public void testRemoveFilters() {
        final BeforeExecutionFileFilterSet filterSet = new BeforeExecutionFileFilterSet();
        final BeforeExecutionFileFilter filter = new BeforeExecutionExclusionFileFilter();
        filterSet.addBeforeExecutionFileFilter(filter);
        filterSet.removeBeforeExecutionFileFilter(filter);
        assertWithMessage("size is the same")
                .that(filterSet.getBeforeExecutionFileFilters())
                .isEmpty();
    }

    @Test
    public void testAccept() {
        final String fileName = "BAD";
        final BeforeExecutionExclusionFileFilter filter = new BeforeExecutionExclusionFileFilter();
        filter.setFileNamePattern(Pattern.compile(fileName));
        final BeforeExecutionFileFilterSet set = new BeforeExecutionFileFilterSet();
        set.addBeforeExecutionFileFilter(filter);

        assertWithMessage("Invalid accept state, should accept")
                .that(set.accept("ATest.java"))
                .isTrue();
    }

    @Test
    public void testReject() {
        final String fileName = "Test";
        final BeforeExecutionExclusionFileFilter filter = new BeforeExecutionExclusionFileFilter();
        filter.setFileNamePattern(Pattern.compile(fileName));
        final BeforeExecutionFileFilterSet set = new BeforeExecutionFileFilterSet();
        set.addBeforeExecutionFileFilter(filter);

        assertWithMessage("Invalid accept state, should not accept")
                .that(set.accept("ATest.java"))
                .isFalse();
    }

    @Test
    public void testGetFilters2() {
        final BeforeExecutionFileFilterSet filterSet = new BeforeExecutionFileFilterSet();
        filterSet.addBeforeExecutionFileFilter(new BeforeExecutionExclusionFileFilter());
        assertWithMessage("size is the same")
                .that(filterSet.getBeforeExecutionFileFilters())
                .hasSize(1);
    }

    @Test
    public void testToString2() {
        final BeforeExecutionFileFilterSet filterSet = new BeforeExecutionFileFilterSet();
        filterSet.addBeforeExecutionFileFilter(new BeforeExecutionExclusionFileFilter());
        assertWithMessage("size is the same")
                .that(filterSet.toString())
                .isNotNull();
    }

    @Test
    public void testClear() {
        final BeforeExecutionFileFilterSet filterSet = new BeforeExecutionFileFilterSet();
        filterSet.addBeforeExecutionFileFilter(new BeforeExecutionExclusionFileFilter());

        assertWithMessage("Invalid filter set size")
                .that(filterSet.getBeforeExecutionFileFilters())
                .hasSize(1);

        filterSet.clear();

        assertWithMessage("Invalid filter set size")
                .that(filterSet.getBeforeExecutionFileFilters())
                .isEmpty();
    }

    /*
      Due to low level configuration setup of BeforeExecutionFileFilterSet, conventional
      input validation cannot be done here hence, pure JUnit testing has been
      done for the time being
    */
    @Test
    public void testUnmodifiableSet() {
        final BeforeExecutionFileFilterSet filterSet = new BeforeExecutionFileFilterSet();
        final BeforeExecutionFileFilter filter = new BeforeExecutionExclusionFileFilter();
        filterSet.addBeforeExecutionFileFilter(filter);
        final Set<BeforeExecutionFileFilter> excFilterSet =
            filterSet.getBeforeExecutionFileFilters();
        final Exception ex = getExpectedThrowable(UnsupportedOperationException.class,
            () -> excFilterSet.add(filter));
        assertWithMessage("Exception message is not expected")
                .that(ex.getClass())
                .isEqualTo(UnsupportedOperationException.class);
    }

    /*
      Input based test does not call toString, but this method might be
      useful for third party integrations.
    */
    @Test
    public void testEmptyToString() {
        final BeforeExecutionFileFilterSet filterSet = new BeforeExecutionFileFilterSet();
        assertWithMessage("toString() result shouldn't be an empty string")
                .that(filterSet.toString())
                .isNotEmpty();
    }

}
