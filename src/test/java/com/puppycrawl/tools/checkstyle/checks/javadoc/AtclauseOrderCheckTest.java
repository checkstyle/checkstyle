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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class AtclauseOrderCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "javadoc" + File.separator + filename);
    }

    @Test
    public void testGetAcceptableTokens() {
        final AtclauseOrderCheck checkObj = new AtclauseOrderCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertArrayEquals(expected, checkObj.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens() {
        final AtclauseOrderCheck checkObj = new AtclauseOrderCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testCorrect() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(AtclauseOrderCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputCorrectAtClauseOrder.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]";
        final DefaultConfiguration checkConfig = createCheckConfig(AtclauseOrderCheck.class);
        final String[] expected = {
            "9: " + getCheckMessage(MSG_KEY, tagOrder),
            "11: " + getCheckMessage(MSG_KEY, tagOrder),
            "12: " + getCheckMessage(MSG_KEY, tagOrder),
            "40: " + getCheckMessage(MSG_KEY, tagOrder),
            "50: " + getCheckMessage(MSG_KEY, tagOrder),
            "51: " + getCheckMessage(MSG_KEY, tagOrder),
            "52: " + getCheckMessage(MSG_KEY, tagOrder),
            "62: " + getCheckMessage(MSG_KEY, tagOrder),
            "69: " + getCheckMessage(MSG_KEY, tagOrder),
            "86: " + getCheckMessage(MSG_KEY, tagOrder),
            "87: " + getCheckMessage(MSG_KEY, tagOrder),
            "99: " + getCheckMessage(MSG_KEY, tagOrder),
            "100: " + getCheckMessage(MSG_KEY, tagOrder),
            "101: " + getCheckMessage(MSG_KEY, tagOrder),
            "115: " + getCheckMessage(MSG_KEY, tagOrder),
            "123: " + getCheckMessage(MSG_KEY, tagOrder),
            "124: " + getCheckMessage(MSG_KEY, tagOrder),
            "134: " + getCheckMessage(MSG_KEY, tagOrder),
            "135: " + getCheckMessage(MSG_KEY, tagOrder),
            "145: " + getCheckMessage(MSG_KEY, tagOrder),
            "146: " + getCheckMessage(MSG_KEY, tagOrder),
            "153: " + getCheckMessage(MSG_KEY, tagOrder),
            "161: " + getCheckMessage(MSG_KEY, tagOrder),
            "172: " + getCheckMessage(MSG_KEY, tagOrder),
            "183: " + getCheckMessage(MSG_KEY, tagOrder),
            "185: " + getCheckMessage(MSG_KEY, tagOrder),
            "199: " + getCheckMessage(MSG_KEY, tagOrder),
            "202: " + getCheckMessage(MSG_KEY, tagOrder),
            "213: " + getCheckMessage(MSG_KEY, tagOrder),
            "223: " + getCheckMessage(MSG_KEY, tagOrder),
            "230: " + getCheckMessage(MSG_KEY, tagOrder),
            "237: " + getCheckMessage(MSG_KEY, tagOrder),
            "247: " + getCheckMessage(MSG_KEY, tagOrder),
            "248: " + getCheckMessage(MSG_KEY, tagOrder),
            "259: " + getCheckMessage(MSG_KEY, tagOrder),
            "261: " + getCheckMessage(MSG_KEY, tagOrder),
            "275: " + getCheckMessage(MSG_KEY, tagOrder),
            "277: " + getCheckMessage(MSG_KEY, tagOrder),
            "278: " + getCheckMessage(MSG_KEY, tagOrder),
            "288: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verify(checkConfig, getPath("InputIncorrectAtClauseOrder.java"), expected);
    }

    @Test
    public void testIncorrectCustom() throws Exception {

        final DefaultConfiguration checkConfig = createCheckConfig(AtclauseOrderCheck.class);
        checkConfig.addAttribute("target", "CLASS_DEF");
        final String customOrder = " @since,  @version, @param,@return,@throws, @exception,"
                + "@deprecated, @see,@serial,   @serialField,  @serialData,@author";
        checkConfig.addAttribute("tagOrder", customOrder);

        final String tagOrder = "[@since, @version, @param, @return, @throws, @exception,"
                + " @deprecated, @see, @serial, @serialField, @serialData, @author]";
        final String[] expected = {
            "113: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verify(checkConfig, getPath("InputIncorrectAtClauseOrder.java"), expected);
    }
}
