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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.InappropriateJavadocBlockTagsOnTypeCheck.MSG_INAPPROPRIATE_TAG;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class InappropriateJavadocBlockTagsOnTypeCheckExamplesTest
    extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/inappropriatejavadocblocktagsontype";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "return", "MyClass1"),
            "27:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "return", "MyEnum1"),
            "40:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "return", "MyInterface1"),
            "52:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "return", "MyRecord1"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

}
