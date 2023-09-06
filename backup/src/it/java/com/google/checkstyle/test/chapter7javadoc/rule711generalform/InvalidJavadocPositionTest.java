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

package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocPositionCheck;

public class InvalidJavadocPositionTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter7javadoc/rule711generalform";
    }

    @Test
    public void testDefault() throws Exception {
        final String message = getCheckMessage(InvalidJavadocPositionCheck.class,
            "invalid.position");

        final String[] expected = {
            "1:9: " + message,
            "3:1: " + message,
            "6:1: " + message,
            "9:5: " + message,
            "14:5: " + message,
            "17:5: " + message,
            "27:9: " + message,
            "28:17: " + message,
            "29:17: " + message,
            "39:10: " + message,
            "40:19: " + message,
            "41:19: " + message,
            "42:21: " + message,
            "43:23: " + message,
            "44:23: " + message,
            "47:1: " + message,
            "52:7: " + message,
            "53:36: " + message,
            "54:1: " + message,
        };

        final Configuration checkConfig = getModuleConfig("InvalidJavadocPosition");
        final String filePath = getPath("InputInvalidJavadocPosition.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
