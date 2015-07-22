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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck.MSG_KEY;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class AtclauseOrderCheckTest extends BaseCheckTestSupport {

    @Test
    public void testCorrect() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(AtclauseOrderCheck.class);
        final String[] expected = {};

        verify(checkConfig, getPath("javadoc/InputCorrectAtClauseOrderCheck.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]";
        DefaultConfiguration checkConfig = createCheckConfig(AtclauseOrderCheck.class);
        final String[] expected = {
            "9: " + getCheckMessage(MSG_KEY, tagOrder),
            "11: " + getCheckMessage(MSG_KEY, tagOrder),
            "12: " + getCheckMessage(MSG_KEY, tagOrder),
            "40: " + getCheckMessage(MSG_KEY, tagOrder),
            "50: " + getCheckMessage(MSG_KEY, tagOrder),
            "51: " + getCheckMessage(MSG_KEY, tagOrder),
            "62: " + getCheckMessage(MSG_KEY, tagOrder),
            "69: " + getCheckMessage(MSG_KEY, tagOrder),
            "86: " + getCheckMessage(MSG_KEY, tagOrder),
            "87: " + getCheckMessage(MSG_KEY, tagOrder),
            "99: " + getCheckMessage(MSG_KEY, tagOrder),
            "101: " + getCheckMessage(MSG_KEY, tagOrder),
            "115: " + getCheckMessage(MSG_KEY, tagOrder),
            "123: " + getCheckMessage(MSG_KEY, tagOrder),
            "134: " + getCheckMessage(MSG_KEY, tagOrder),
            "135: " + getCheckMessage(MSG_KEY, tagOrder),
            "145: " + getCheckMessage(MSG_KEY, tagOrder),
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
        verify(checkConfig, getPath("javadoc/InputIncorrectAtClauseOrderCheck.java"), expected);
    }

    @Test
    public void testIncorrectCustom() throws Exception {
        final String tagOrder = "[@since, @version, @param, @return, @throws, @exception,"
                + " @deprecated, @see, @serial, @serialField, @serialData, @author]";
        final String customOrder = " @since,  @version, @param,@return,@throws, @exception,"
                + "@deprecated, @see,@serial,   @serialField,  @serialData,@author";

        DefaultConfiguration checkConfig = createCheckConfig(AtclauseOrderCheck.class);
        checkConfig.addAttribute("target", "CLASS_DEF");
        checkConfig.addAttribute("tagOrder", customOrder);

        final String[] expected = {
            "113: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verify(checkConfig, getPath("javadoc/InputIncorrectAtClauseOrderCheck.java"), expected);
    }
}
