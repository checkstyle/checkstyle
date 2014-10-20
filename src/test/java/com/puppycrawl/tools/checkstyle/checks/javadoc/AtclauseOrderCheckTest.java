////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class AtclauseOrderCheckTest extends BaseCheckTestSupport
{

    @Test
    public void testCorrect() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(AtclauseOrderCheck.class);
        final String[] expected = {};

        verify(checkConfig, getPath("javadoc/InputCorrectAtClauseOrderCheck.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception
    {
        final String tagOrder = "'[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]'.";
        DefaultConfiguration checkConfig = createCheckConfig(AtclauseOrderCheck.class);
        final String[] expected = {
            "9: At-clauses have to appear in the order " + tagOrder,
            "11: At-clauses have to appear in the order " + tagOrder,
            "12: At-clauses have to appear in the order " + tagOrder,
            "40: At-clauses have to appear in the order " + tagOrder,
            "50: At-clauses have to appear in the order " + tagOrder,
            "51: At-clauses have to appear in the order " + tagOrder,
            "62: At-clauses have to appear in the order " + tagOrder,
            "69: At-clauses have to appear in the order " + tagOrder,
            "86: At-clauses have to appear in the order " + tagOrder,
            "87: At-clauses have to appear in the order " + tagOrder,
            "99: At-clauses have to appear in the order " + tagOrder,
            "101: At-clauses have to appear in the order " + tagOrder,
            "115: At-clauses have to appear in the order " + tagOrder,
            "123: At-clauses have to appear in the order " + tagOrder,
            "134: At-clauses have to appear in the order " + tagOrder,
            "135: At-clauses have to appear in the order " + tagOrder,
            "145: At-clauses have to appear in the order " + tagOrder,
            "153: At-clauses have to appear in the order " + tagOrder,
            "161: At-clauses have to appear in the order " + tagOrder,
            "172: At-clauses have to appear in the order " + tagOrder,
            "183: At-clauses have to appear in the order " + tagOrder,
            "185: At-clauses have to appear in the order " + tagOrder,
            "199: At-clauses have to appear in the order " + tagOrder,
            "202: At-clauses have to appear in the order " + tagOrder,
            "213: At-clauses have to appear in the order " + tagOrder,
            "223: At-clauses have to appear in the order " + tagOrder,
            "230: At-clauses have to appear in the order " + tagOrder,
            "237: At-clauses have to appear in the order " + tagOrder,
            "247: At-clauses have to appear in the order " + tagOrder,
            "248: At-clauses have to appear in the order " + tagOrder,
            "259: At-clauses have to appear in the order " + tagOrder,
            "261: At-clauses have to appear in the order " + tagOrder,
            "275: At-clauses have to appear in the order " + tagOrder,
            "277: At-clauses have to appear in the order " + tagOrder,
            "278: At-clauses have to appear in the order " + tagOrder,
            "288: At-clauses have to appear in the order " + tagOrder,
        };
        verify(checkConfig, getPath("javadoc/InputIncorrectAtClauseOrderCheck.java"), expected);
    }

    @Test
    public void testIncorrectCustom() throws Exception
    {
        final String tagOrder = "'[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]'.";
        DefaultConfiguration checkConfig = createCheckConfig(AtclauseOrderCheck.class);
        checkConfig.addAttribute("target", "CLASS_DEF");

        final String[] expected = {
            "9: At-clauses have to appear in the order " + tagOrder,
            "11: At-clauses have to appear in the order " + tagOrder,
            "12: At-clauses have to appear in the order " + tagOrder,
            "115: At-clauses have to appear in the order " + tagOrder,
        };
        verify(checkConfig, getPath("javadoc/InputIncorrectAtClauseOrderCheck.java"), expected);
    }
}
