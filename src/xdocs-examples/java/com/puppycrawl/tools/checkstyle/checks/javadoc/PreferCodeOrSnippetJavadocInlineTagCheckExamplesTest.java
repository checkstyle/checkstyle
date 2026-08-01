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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.PreferCodeOrSnippetJavadocInlineTagCheck.MSG_KEY_MULTI_LINE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.PreferCodeOrSnippetJavadocInlineTagCheck.MSG_KEY_SINGLE_LINE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class PreferCodeOrSnippetJavadocInlineTagCheckExamplesTest
    extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/"
                + "prefercodeorsnippetjavadocinlinetag";
    }

    @Test
    public void testCheck() throws Exception {
        final String[] expected = {
            "13:6: " + getCheckMessage(MSG_KEY_SINGLE_LINE, "pre"),
            "29:6: " + getCheckMessage(MSG_KEY_SINGLE_LINE, "code"),
            "46:6: " + getCheckMessage(MSG_KEY_MULTI_LINE, "pre"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

}
