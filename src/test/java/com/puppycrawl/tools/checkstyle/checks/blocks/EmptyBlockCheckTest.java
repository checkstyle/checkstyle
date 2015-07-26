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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck.MSG_KEY_BLOCK_EMPTY;
import static com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck.MSG_KEY_BLOCK_NO_STMT;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class EmptyBlockCheckTest
    extends BaseCheckTestSupport {
    @Test
    public void testDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EmptyBlockCheck.class);
        final String[] expected = {
            "75:13: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "77:17: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "79:13: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "82:17: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "178:5: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "206:29: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "208:41: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "219:12: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testText()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EmptyBlockCheck.class);
        checkConfig.addAttribute("option", BlockOption.TEXT.toString());
        final String[] expected = {
            "75:13: " + getCheckMessage(MSG_KEY_BLOCK_EMPTY, "try"),
            "77:17: " + getCheckMessage(MSG_KEY_BLOCK_EMPTY, "finally"),
            "178:5: " + getCheckMessage(MSG_KEY_BLOCK_EMPTY, "INSTANCE_INIT"),
            "206:29: " + getCheckMessage(MSG_KEY_BLOCK_EMPTY, "synchronized"),
            "219:12: " + getCheckMessage(MSG_KEY_BLOCK_EMPTY, "STATIC_INIT"),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testStatement()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EmptyBlockCheck.class);
        checkConfig.addAttribute("option", BlockOption.STMT.toString());
        final String[] expected = {
            "75:13: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "77:17: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "79:13: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "82:17: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "178:5: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "206:29: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "208:41: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "219:12: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void allowEmptyLoops() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(EmptyBlockCheck.class);
        checkConfig.addAttribute("option", BlockOption.STMT.toString());
        checkConfig.addAttribute("tokens", "LITERAL_TRY, LITERAL_CATCH,"
                + "LITERAL_FINALLY, LITERAL_DO, LITERAL_IF,"
                + "LITERAL_ELSE, INSTANCE_INIT, STATIC_INIT, LITERAL_SWITCH");
        final String[] expected = {
            "16:29: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "19:42: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "22:29: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
            "23:28: " + getCheckMessage(MSG_KEY_BLOCK_NO_STMT),
        };
        verify(checkConfig, getPath("InputSemantic2.java"), expected);
    }

    @Test
    public void allowEmptyLoopsText() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(EmptyBlockCheck.class);
        checkConfig.addAttribute("option", BlockOption.TEXT.toString());
        checkConfig.addAttribute("tokens", "LITERAL_TRY, LITERAL_CATCH,"
                + "LITERAL_FINALLY, LITERAL_DO, LITERAL_IF,"
                + "LITERAL_ELSE, INSTANCE_INIT, STATIC_INIT, LITERAL_SWITCH");
        final String[] expected = {
            "16:29: " + getCheckMessage(MSG_KEY_BLOCK_EMPTY, "if"),
            "19:42: " + getCheckMessage(MSG_KEY_BLOCK_EMPTY, "if"),
            "22:29: " + getCheckMessage(MSG_KEY_BLOCK_EMPTY, "if"),
            "23:28: " + getCheckMessage(MSG_KEY_BLOCK_EMPTY, "switch"),
        };
        verify(checkConfig, getPath("InputSemantic2.java"), expected);
    }
}
