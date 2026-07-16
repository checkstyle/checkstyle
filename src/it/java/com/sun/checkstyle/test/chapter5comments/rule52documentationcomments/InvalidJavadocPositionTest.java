///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.sun.checkstyle.test.chapter5comments.rule52documentationcomments;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocPositionCheck;
import com.sun.checkstyle.test.base.AbstractSunModuleTestSupport;

public class InvalidJavadocPositionTest extends AbstractSunModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/sun/checkstyle/test/chapter5comments/rule52documentationcomments";
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
            "15:5: " + message,
            "18:5: " + message,
            "28:9: " + message,
            "29:17: " + message,
            "30:17: " + message,
            "40:10: " + message,
            "41:19: " + message,
            "42:19: " + message,
            "43:21: " + message,
            "44:23: " + message,
            "45:23: " + message,
            "49:1: " + message,
            "54:7: " + message,
            "55:36: " + message,
            "56:1: " + message,
        };

        final Configuration checkConfig = getModuleConfig("InvalidJavadocPosition");
        final String filePath = getPath("InputInvalidJavadocPosition.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
