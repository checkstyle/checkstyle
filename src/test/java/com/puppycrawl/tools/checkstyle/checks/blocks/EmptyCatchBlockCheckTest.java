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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.EmptyCatchBlockCheck.MSG_KEY_CATCH_BLOCK_EMPTY;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 *
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 *
 */
public class EmptyCatchBlockCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "blocks" + File.separator + "emptycatchblock" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final EmptyCatchBlockCheck checkObj = new EmptyCatchBlockCheck();
        final int[] expected = {TokenTypes.LITERAL_CATCH};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EmptyCatchBlockCheck.class);
        final String[] expected = {
            "35: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "42: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
        };
        verify(checkConfig, getPath("InputEmptyCatchBlockDefault.java"), expected);
    }

    @Test
    public void testWithUserSetValues() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EmptyCatchBlockCheck.class);
        checkConfig.addAttribute("exceptionVariableName", "expected|ignore|myException");
        checkConfig.addAttribute("commentFormat", "This is expected");
        final String[] expected = {
            "35: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "63: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "97: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "186: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "195: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "214: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "230: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "239: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
        };
        verify(checkConfig, getPath("InputEmptyCatchBlockDefault.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final EmptyCatchBlockCheck constantNameCheckObj = new EmptyCatchBlockCheck();
        final int[] actual = constantNameCheckObj.getAcceptableTokens();
        final int[] expected = {TokenTypes.LITERAL_CATCH };
        assertArrayEquals(expected, actual);
    }
}
