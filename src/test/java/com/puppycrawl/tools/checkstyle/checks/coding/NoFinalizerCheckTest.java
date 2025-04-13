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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.NoFinalizerCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * NoFinalizerCheck test.
 *
 */
public class NoFinalizerCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/nofinalizer";
    }

    @Test
    public void testGetAcceptableTokens() {
        final NoFinalizerCheck noFinalizerCheck =
                new NoFinalizerCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};

        assertWithMessage("Default acceptable tokens are invalid")
            .that(noFinalizerCheck.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testHasFinalizer()
            throws Exception {
        final String[] expected = {
            "11:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoFinalizerHasFinalizer.java"), expected);
    }

    @Test
    public void testHasNoFinalizer()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNoFinalizerFallThrough.java"), expected);
    }

    @Test
    public void testHasNoFinalizerTryWithResource()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputNoFinalizerFallThrough.java"), expected);
    }

}
