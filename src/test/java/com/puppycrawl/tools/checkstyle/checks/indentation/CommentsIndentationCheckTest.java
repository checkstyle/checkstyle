////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import static com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck.MSG_KEY_BLOCK;
import static com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck.MSG_KEY_SINGLE;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
*
* @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
*
*/
public class CommentsIndentationCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "indentation" + File.separator + filename);
    }

    @Test
    public void testSurroundingCode() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY_SINGLE, 14, 14, 12),
            "23: " + getCheckMessage(MSG_KEY_BLOCK, 24, 16, 12),
            "25: " + getCheckMessage(MSG_KEY_BLOCK, 27, 16, 12),
            "28: " + getCheckMessage(MSG_KEY_BLOCK, 31, 16, 12),
            "50: " + getCheckMessage(MSG_KEY_SINGLE, 51, 27, 23),
            "51: " + getCheckMessage(MSG_KEY_BLOCK, 53, 23, 36),
            "136: " + getCheckMessage(MSG_KEY_SINGLE, 137, 20, 16),
        };
        verify(checkConfig, getPath("InputCommentsIndentationSurroundingCode.java"), expected);
    }

    @Test
    public void testNpe() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CommentsIndentationCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputCommentsIndentationNpe.java"), expected);
    }

    @Test
    public void testTokens() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CommentsIndentationCheck.class);
        checkConfig.addAttribute("tokens", "SINGLE_LINE_COMMENT");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY_SINGLE, 14, 14, 12),
            "50: " + getCheckMessage(MSG_KEY_SINGLE, 51, 27, 23),
            "136: " + getCheckMessage(MSG_KEY_SINGLE, 137, 20, 16),
        };
        verify(checkConfig, getPath("InputCommentsIndentationSurroundingCode.java"), expected);
    }

    @Test
    public void testVisitToken() throws Exception {
        final CommentsIndentationCheck check = new CommentsIndentationCheck();
        final DetailAST methodDef = new DetailAST();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("methodStub");
        try {
            check.visitToken(methodDef);
            Assert.fail();
        }
        catch (IllegalArgumentException e) {
            final String msg = e.getMessage();
            Assert.assertEquals("Unexpected token type: methodStub", msg);
        }
    }
}
