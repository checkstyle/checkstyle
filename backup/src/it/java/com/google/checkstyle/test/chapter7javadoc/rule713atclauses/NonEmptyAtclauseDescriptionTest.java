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
import com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck;

public class NonEmptyAtclauseDescriptionTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter7javadoc/rule713atclauses";
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final String msg = getCheckMessage(NonEmptyAtclauseDescriptionCheck.class,
            "non.empty.atclause");

        final String[] expected = {
            "34: " + msg,
            "35: " + msg,
            "36: " + msg,
            "37: " + msg,
            "38: " + msg,
            "39: " + msg,
            "48: " + msg,
            "49: " + msg,
            "50: " + msg,
            "51: " + msg,
            "52: " + msg,
        };

        final Configuration checkConfig = getModuleConfig("NonEmptyAtclauseDescription");
        final String filePath = getPath("InputNonEmptyAtclauseDescriptionCheck.java");

        final Integer[] warnList = getLineNumbersFromExpected(expected);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testSpaceSequence() throws Exception {
        final String msg = getCheckMessage(NonEmptyAtclauseDescriptionCheck.class,
            "non.empty.atclause");

        final String[] expected = {
            "27: " + msg,
            "28: " + msg,
            "29: " + msg,
            "38: " + msg,
            "39: " + msg,
            "40: " + msg,
        };

        final Configuration checkConfig = getModuleConfig("NonEmptyAtclauseDescription");
        final String filePath = getPath("InputNonEmptyAtclauseDescriptionCheckSpaceSeq.java");

        final Integer[] warnList = getLineNumbersFromExpected(expected);
        verify(checkConfig, filePath, expected, warnList);
    }

    /**
     * Gets line numbers with violations from an array with expected messages.
     * This is used as using "warn" comments in input files would affect the work
     * of the Check.
     *
     * @param expected an array with expected messages.
     * @return Integer array with numbers of lines with violations.
     */
    private static Integer[] getLineNumbersFromExpected(String... expected) {
        final Integer[] result = new Integer[expected.length];
        for (int i = 0; i < expected.length; i++) {
            result[i] = Integer.valueOf(expected[i].substring(0, expected[i].indexOf(':')));
        }
        return result;
    }

}
