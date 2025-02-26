///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck.MSG_UNUSED_LOCAL_VARIABLE;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck.MSG_UNUSED_NAMED_LOCAL_VARIABLE;

public class UnusedPrivateMethodTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unusedprivatemethod";
    }

    @Test
    public void testUnusedLocalVariable() throws Exception {
        verifyWithInlineConfigParser(getPath("InputUnusedLocalVariable.java"));
    }

    @Test
    public void testUnusedLocalVariable2() throws Exception {
        final String[] expected = {
            "27:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "sameName"),
            "28:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "31:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "testInLambdas"),
            "33:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "coding"),
            "34:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE,
                    "InputUnusedLocalVariable"),
            "50:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "54:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "65:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "67:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "71:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "p"),
            "81:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "f"),
            "84:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "foo"),
            "91:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariable.java"), expected);
    }

}
