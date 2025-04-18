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

import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalMethodCheck.MSG_UNUSED_LOCAL_METHOD;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class UnusedLocalMethodCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unusedlocalmethod";
    }

    @Test
    public void chain() throws Exception {
        verifyWithInlineConfigParser(getPath("InputUnusedLocalMethodChainUnused.java"),
                List.of("15:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused2")));
    }

    @Test
    public void usageWithInComment() throws Exception {
        verifyWithInlineConfigParser(getPath("InputUnusedLocalMethodCheckUsageWithInComment.java"),
                List.of("29:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused2")));
    }

    @Test
    public void multi() throws Exception {
        verifyWithInlineConfigParser(getPath("InputUnusedLocalMethodMulti.java"),
            List.of(
                "15:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused1"),
                "18:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused2"),
                "21:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused3")
            ));
    }

    @Test
    public void staticDefinition() throws Exception {
        verifyWithInlineConfigParser(getPath("InputUnusedLocalMethodStatic.java"));
    }

    @Test
    public void staticRef() throws Exception {
        verifyWithInlineConfigParser(getPath("InputUnusedLocalMethodStaticReference.java"));
    }

    @Test
    public void noUnused() throws Exception {
        verifyWithInlineConfigParser(getPath("InputUnusedLocalMethodNoUnused.java"));
    }

    @Test
    public void onlyOnPrivate() throws Exception {
        verifyWithInlineConfigParser(getPath("InputUnusedLocalMethodOnlyOnPrivate.java"),
                List.of(
                    "17:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "_private"),
                    "29:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "_privateStatic")
                ));
    }

    @Test
    // https://github.com/checkstyle/checkstyle/issues/16375#issuecomment-2671577546
    @Disabled
    public void methodOverload() throws Exception {
        verifyWithInlineConfigParser(getPath("InputUnusedLocalMethodMethodOverload.java"),
            List.of("23:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused")));
    }
}

