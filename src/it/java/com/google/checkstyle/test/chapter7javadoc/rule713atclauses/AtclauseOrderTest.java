///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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
    public void testCorrect1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("AtclauseOrder");
        final String filePath = getPath("InputCorrectAtClauseOrderCheck1.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCorrect2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("AtclauseOrder");
        final String filePath = getPath("InputCorrectAtClauseOrderCheck2.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCorrect3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("AtclauseOrder");
        final String filePath = getPath("InputCorrectAtClauseOrderCheck3.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testIncorrect1() throws Exception {
        final String tagOrder = "[@param, @return, @throws, @deprecated]";
        final String msg = getCheckMessage(AtclauseOrderCheck.class, "at.clause.order", tagOrder);

        final String[] expected = {
            "40: " + msg,
            "51: " + msg,
            "73: " + msg,
            "74: " + msg,
            "84: " + msg,
            "85: " + msg,
            "98: " + msg,
            "101: " + msg,
            "112: " + msg,
        };

        final Configuration checkConfig = getModuleConfig("AtclauseOrder");
        final String filePath = getPath("InputIncorrectAtClauseOrderCheck1.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testIncorrect2() throws Exception {
        final String tagOrder = "[@param, @return, @throws, @deprecated]";
        final String msg = getCheckMessage(AtclauseOrderCheck.class, "at.clause.order", tagOrder);

        final String[] expected = {
            "19: " + msg,
            "26: " + msg,
            "59: " + msg,
            "67: " + msg,
            "78: " + msg,
            "85: " + msg,
            "92: " + msg,
        };

        final Configuration checkConfig = getModuleConfig("AtclauseOrder");
        final String filePath = getPath("InputIncorrectAtClauseOrderCheck2.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testIncorrect3() throws Exception {
        final String tagOrder = "[@param, @return, @throws, @deprecated]";
        final String msg = getCheckMessage(AtclauseOrderCheck.class, "at.clause.order", tagOrder);

        final String[] expected = {
            "20: " + msg,
            "21: " + msg,
            "33: " + msg,
            "35: " + msg,
            "58: " + msg,
            "69: " + msg,
            "71: " + msg,
            "86: " + msg,
            "87: " + msg,
            "98: " + msg,
            "100: " + msg,
        };

        final Configuration checkConfig = getModuleConfig("AtclauseOrder");
        final String filePath = getPath("InputIncorrectAtClauseOrderCheck3.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
