///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AtclauseOrderTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter7javadoc/rule713atclauses";
    }

    @Test
    public void testCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("AtclauseOrder");
        final String filePath = getPath("InputCorrectAtClauseOrderCheck.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String tagOrder = "[@param, @return, @throws, @deprecated]";
        final String msg = getCheckMessage(AtclauseOrderCheck.class, "at.clause.order", tagOrder);

        final String[] expected = {
            "40: " + msg,
            "51: " + msg,
            "62: " + msg,
            "69: " + msg,
            "86: " + msg,
            "87: " + msg,
            "99: " + msg,
            "101: " + msg,
            "123: " + msg,
            "124: " + msg,
            "134: " + msg,
            "135: " + msg,
            "153: " + msg,
            "161: " + msg,
            "172: " + msg,
            "183: " + msg,
            "185: " + msg,
            "199: " + msg,
            "202: " + msg,
            "213: " + msg,
            "223: " + msg,
            "230: " + msg,
            "237: " + msg,
            "247: " + msg,
            "248: " + msg,
            "259: " + msg,
            "261: " + msg,
        };

        final Configuration checkConfig = getModuleConfig("AtclauseOrder");
        final String filePath = getPath("InputIncorrectAtClauseOrderCheck.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
