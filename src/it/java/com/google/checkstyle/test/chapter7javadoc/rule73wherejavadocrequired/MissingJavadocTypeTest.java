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

package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck.MSG_JAVADOC_MISSING;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingJavadocTypeTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter7javadoc/rule73wherejavadocrequired";
    }

    @Test
    public void testJavadocType() throws Exception {

        final String[] expected = {
            "3:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "5:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "9:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "13:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "14:9: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "17:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "21:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "25:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "29:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "33:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final Configuration checkConfig = getModuleConfig("MissingJavadocType");
        final String filePath = getPath("InputMissingJavadocTypeCheck.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testJavadocTypeNoViolations() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("MissingJavadocType");
        final String filePath = getPath("InputMissingJavadocTypeCheckNoViolations.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
