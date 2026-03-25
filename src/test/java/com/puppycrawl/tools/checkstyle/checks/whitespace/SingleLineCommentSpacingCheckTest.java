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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.SingleLineCommentSpacingCheck.MSG_KEY_AFTER;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.SingleLineCommentSpacingCheck.MSG_KEY_BEFORE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SingleLineCommentSpacingCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/singlelinecommentspacing";
    }

    @Test
    public void testGetRequiredTokens() {
        final SingleLineCommentSpacingCheck check = new SingleLineCommentSpacingCheck();
        final int[] expected = {TokenTypes.SINGLE_LINE_COMMENT};

        assertWithMessage("Invalid required tokens")
                .that(check.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testIsCommentNodesRequired() {
        final SingleLineCommentSpacingCheck check = new SingleLineCommentSpacingCheck();

        assertWithMessage("Comment nodes should be required")
                .that(check.isCommentNodesRequired())
                .isTrue();
    }

    @Test
    public void testValid() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(SingleLineCommentSpacingCheck.class);

        verify(checkConfig, getPath("InputSingleLineCommentSpacingValid.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testInvalid() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(SingleLineCommentSpacingCheck.class);
        final String[] expected = {
            "12:22: " + getCheckMessage(MSG_KEY_BEFORE),
            "12:25: " + getCheckMessage(MSG_KEY_AFTER),
            "13:31: " + getCheckMessage(MSG_KEY_AFTER),
            "14:28: " + getCheckMessage(MSG_KEY_BEFORE),
        };

        verify(checkConfig, getPath("InputSingleLineCommentSpacingInvalid.java"), expected);
    }
}
