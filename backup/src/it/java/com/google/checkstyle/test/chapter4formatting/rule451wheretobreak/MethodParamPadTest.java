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

package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.MethodParamPadCheck;

public class MethodParamPadTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule451wheretobreak";
    }

    @Test
    public void testOperatorWrap() throws Exception {
        final Class<MethodParamPadCheck> clazz = MethodParamPadCheck.class;
        final String messageKeyPrevious = "line.previous";
        final String messageKeyPreceded = "ws.preceded";

        final String[] expected = {
            "83:9: " + getCheckMessage(clazz, messageKeyPrevious, "("),
            "128:13: " + getCheckMessage(clazz, messageKeyPrevious, "("),
            "130:9: " + getCheckMessage(clazz, messageKeyPrevious, "("),
            "353:15: " + getCheckMessage(clazz, messageKeyPreceded, "("),
            "358:13: " + getCheckMessage(clazz, messageKeyPrevious, "("),
        };
        final Configuration checkConfig = getModuleConfig("MethodParamPad");
        final String filePath = getPath("InputMethodParamPad.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
