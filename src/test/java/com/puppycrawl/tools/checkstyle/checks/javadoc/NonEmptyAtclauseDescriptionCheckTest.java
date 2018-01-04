////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NonEmptyAtclauseDescriptionCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/nonemptyatclausedescription";
    }

    @Test
    public void testGetAcceptableTokens() {
        final NonEmptyAtclauseDescriptionCheck checkObj =
            new NonEmptyAtclauseDescriptionCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertArrayEquals("Default acceptable tokens are invalid",
            expected, checkObj.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens() {
        final NonEmptyAtclauseDescriptionCheck checkObj =
            new NonEmptyAtclauseDescriptionCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertArrayEquals("Default required tokens are invalid",
            expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testCheck()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NonEmptyAtclauseDescriptionCheck.class);
        final String[] expected = {
            //this is a case with description that is sequences of spaces
            "26: " + getCheckMessage(MSG_KEY),
            //this is a case with description that is sequences of spaces
            "27: " + getCheckMessage(MSG_KEY),
            //this is a case with description that is sequences of spaces
            "28: " + getCheckMessage(MSG_KEY),
            //this is a case with description that is sequences of spaces
            "37: " + getCheckMessage(MSG_KEY),
            //this is a case with description that is sequences of spaces
            "38: " + getCheckMessage(MSG_KEY),
            //this is a case with description that is sequences of spaces
            "39: " + getCheckMessage(MSG_KEY),
            "75: " + getCheckMessage(MSG_KEY),
            "76: " + getCheckMessage(MSG_KEY),
            "77: " + getCheckMessage(MSG_KEY),
            "78: " + getCheckMessage(MSG_KEY),
            "79: " + getCheckMessage(MSG_KEY),
            "80: " + getCheckMessage(MSG_KEY),
            "89: " + getCheckMessage(MSG_KEY),
            "90: " + getCheckMessage(MSG_KEY),
            "91: " + getCheckMessage(MSG_KEY),
            "92: " + getCheckMessage(MSG_KEY),
            "93: " + getCheckMessage(MSG_KEY),
            "120: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputNonEmptyAtclauseDescription.java"), expected);
    }

}
