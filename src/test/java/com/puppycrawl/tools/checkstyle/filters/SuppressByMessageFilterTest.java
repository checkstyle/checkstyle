////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.filters;

import static com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck.MSG_METHOD;
import static com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck.MSG_VARIABLE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressByMessageFilterTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressbymessagefilter";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);

        final String[] expectedViolations = {
            "23:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "a", ""),
            "24:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "b", ""),
            "25:9: " + getCheckMessage(RequireThisCheck.class, MSG_METHOD, "foo", ""),
            "29:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "c", ""),
        };

        verifySuppressed(config, getPath("InputSuppressByMessageFilter.java"),
                expectedViolations);
    }

    @Test
    public void testMatchNothing() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addProperty("checkFormat", "");
        config.addProperty("messageFormat", "");

        final String[] expectedViolations = {
            "23:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "a", ""),
            "24:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "b", ""),
            "25:9: " + getCheckMessage(RequireThisCheck.class, MSG_METHOD, "foo", ""),
            "29:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "c", ""),
        };

        verifySuppressed(config, getPath("InputSuppressByMessageFilter2.java"),
                expectedViolations);
    }

    @Test
    public void testMatchCheck() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addProperty("checkFormat", "RequireThisCheck");

        verifySuppressed(config, getPath("InputSuppressByMessageFilter3.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testNoMatchCheck() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addProperty("checkFormat", "CheckThatDoesNotExist");

        final String[] expectedViolations = {
            "23:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "a", ""),
            "24:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "b", ""),
            "25:9: " + getCheckMessage(RequireThisCheck.class, MSG_METHOD, "foo", ""),
            "29:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "c", ""),
        };

        verifySuppressed(config, getPath("InputSuppressByMessageFilter4.java"),
                expectedViolations);
    }

    @Test
    public void testMatchMessageVariable() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addProperty("messageFormat", "('[a-z]{1}')");

        final String[] expectedViolations = {
            "25:9: " + getCheckMessage(RequireThisCheck.class, MSG_METHOD, "foo", ""),
        };

        verifySuppressed(config, getPath("InputSuppressByMessageFilter5.java"),
                expectedViolations);
    }

    @Test
    public void testMatchMessageMethod() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addProperty("messageFormat", "('foo')");

        final String[] expectedViolations = {
            "23:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "a", ""),
            "24:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "b", ""),
            "29:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "c", ""),
        };

        verifySuppressed(config, getPath("InputSuppressByMessageFilter6.java"),
                expectedViolations);
    }

    @Test
    public void testNoMatchMessage() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addProperty("messageFormat", "Nonsense1234Regex");

        final String[] expectedViolations = {
            "23:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "a", ""),
            "24:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "b", ""),
            "25:9: " + getCheckMessage(RequireThisCheck.class, MSG_METHOD, "foo", ""),
            "29:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "c", ""),
        };

        verifySuppressed(config, getPath("InputSuppressByMessageFilter7.java"),
                expectedViolations);
    }

    @Test
    public void testMatchCheckAndMessage() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addProperty("checkFormat", "RequireThisCheck");
        config.addProperty("messageFormat", "needs");

        verifySuppressed(config, getPath("InputSuppressByMessageFilter8.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    private void verifySuppressed(DefaultConfiguration config, String fileName,
                                  String... expectedViolations) throws Exception {
        final DefaultConfiguration requireThisConfig =
                createModuleConfig(RequireThisCheck.class);
        requireThisConfig.addProperty("validateOnlyOverlapping", "false");

        final DefaultConfiguration treewalkerConfig = createModuleConfig(TreeWalker.class);
        treewalkerConfig.addChild(requireThisConfig);
        final DefaultConfiguration checkerConfig = createRootConfig(treewalkerConfig);
        checkerConfig.addChild(config);

        verifyWithInlineConfigParser(fileName, expectedViolations);
    }
}
