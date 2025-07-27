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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_CLOSE_QUOTES_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_INDENTATION_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_OPEN_QUOTES_ERROR;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class TextBlockGoogleStyleFormattingCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/textblockgooglestyleformatting";
    }

    @Test
    public void testTextBlockFormat() throws Exception {
        final String[] expected = {
            "15:37: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "15:37: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "27:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "27:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "35:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "35:16: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "43:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "44:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "49:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "51:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "56:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "57:34: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "70:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting.java"), expected);
    }

    @Test
    public void testTextBlockIndentation() throws Exception {
        final String[] expected = {
            "13:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "24:11: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "36:9: " + getCheckMessage(MSG_INDENTATION_ERROR),
        };
        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormattingIndentation.java"), expected);
    }
}
