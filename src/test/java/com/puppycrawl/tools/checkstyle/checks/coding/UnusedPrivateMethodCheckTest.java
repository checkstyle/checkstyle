/// ////////////////////////////////////////////////////////////////////////////////////////////
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
/// ////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedPrivateMethodCheck.MSG_UNUSED_LOCAL_METHOD;

public class UnusedPrivateMethodCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unusedprivatemethod";
    }

    @Test
    public void UnusedLocalMethod() throws Exception {
        verifyWithInlineConfigParser(getPath("UnusedLocalMethod.java"), new String[]{
                "8:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused"),
        });
    }

    @Test
    public void UnusedLocalMethodChain() throws Exception {
        verifyWithInlineConfigParser(getPath("UnusedLocalMethodChain.java"), new String[]{
                "15:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused"),
        });
    }

    @Test
    public void UnusedLocalMethodChainUnused() throws Exception {
        verifyWithInlineConfigParser(getPath("UnusedLocalMethodChainUnused.java"), new String[]{
                "19:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused2"),
        });
    }

    @Test
    public void UnusedLocalMethodMulti() throws Exception {
        verifyWithInlineConfigParser(getPath("UnusedLocalMethodMulti.java"), new String[]{
                "15:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused1"),
                "18:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused2"),
                "21:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused3"),
        });
    }

    @Test
    public void UnusedLocalMethodNoUnused() throws Exception {
        verifyWithInlineConfigParser(getPath("UnusedLocalMethodNoUnused.java"));
    }

    @Test
    public void UnusedLocalMethodOnlyOnPrivateMethodScope() throws Exception {
        verifyWithInlineConfigParser(getPath("UnusedLocalMethodOnlyOnPrivateMethodScope.java"), new String[]{
                "14:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unusedPrivate"),
        });
    }

    @Test
    @Disabled
    public void UnusedLocalMethodOverload() throws Exception {
        verifyWithInlineConfigParser(getPath("UnusedLocalMethodOverload.java"), new String[]{
                "15:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused"),
        });
    }

    @Test
    @Disabled
    public void UnusedLocalMethodOverloadUnused() throws Exception {
        verifyWithInlineConfigParser(getPath("UnusedLocalMethodOverloadUnused.java"), new String[]{
                "15:5: " + getCheckMessage(MSG_UNUSED_LOCAL_METHOD, "unused"),
        });
    }


}
