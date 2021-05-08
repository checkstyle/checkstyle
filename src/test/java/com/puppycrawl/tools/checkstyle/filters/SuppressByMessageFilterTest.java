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
            "14:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "a", ""),
            "15:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "b", ""),
            "16:9: " + getCheckMessage(RequireThisCheck.class, MSG_METHOD, "foo", ""),
            "20:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "c", ""),
        };

        verifySuppressed(config, getPath("InputSuppressByMessageFilter.java"),
                expectedViolations);
    }

    @Test
    public void testMatchNothing() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addAttribute("checkFormat", "");
        config.addAttribute("messageFormat", "");

        final String[] expectedViolations = {
            "14:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "a", ""),
            "15:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "b", ""),
            "16:9: " + getCheckMessage(RequireThisCheck.class, MSG_METHOD, "foo", ""),
            "20:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "c", ""),
        };

        verifySuppressed(config, getPath("InputSuppressByMessageFilter.java"),
                expectedViolations);
    }

    @Test
    public void testMatchCheck() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addAttribute("checkFormat", "RequireThisCheck");

        verifySuppressed(config, getPath("InputSuppressByMessageFilter.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testNoMatchCheck() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addAttribute("checkFormat", "CheckThatDoesNotExist");

        final String[] expectedViolations = {
            "14:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "a", ""),
            "15:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "b", ""),
            "16:9: " + getCheckMessage(RequireThisCheck.class, MSG_METHOD, "foo", ""),
            "20:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "c", ""),
        };

        verifySuppressed(config, getPath("InputSuppressByMessageFilter.java"),
                expectedViolations);
    }

    @Test
    public void testMatchMessageVariable() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addAttribute("messageFormat", "('[a-z]{1}')");

        final String[] expectedViolations = {
            "16:9: " + getCheckMessage(RequireThisCheck.class, MSG_METHOD, "foo", ""),
        };

        verifySuppressed(config, getPath("InputSuppressByMessageFilter.java"),
                expectedViolations);
    }

    @Test
    public void testMatchMessageMethod() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addAttribute("messageFormat", "('foo')");

        final String[] expectedViolations = {
            "14:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "a", ""),
            "15:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "b", ""),
            "20:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "c", ""),
        };

        verifySuppressed(config, getPath("InputSuppressByMessageFilter.java"),
                expectedViolations);
    }

    @Test
    public void testNoMatchMessage() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addAttribute("messageFormat", "Nonsense1234Regex");

        final String[] expectedViolations = {
            "14:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "a", ""),
            "15:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "b", ""),
            "16:9: " + getCheckMessage(RequireThisCheck.class, MSG_METHOD, "foo", ""),
            "20:9: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE, "c", ""),
        };

        verifySuppressed(config, getPath("InputSuppressByMessageFilter.java"),
                expectedViolations);
    }

    @Test
    public void testMatchCheckAndMessage() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressByMessageFilter.class);
        config.addAttribute("checkFormat", "RequireThisCheck");
        config.addAttribute("messageFormat", "needs");

        verifySuppressed(config, getPath("InputSuppressByMessageFilter.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    private void verifySuppressed(DefaultConfiguration config, String fileName,
                                  String... expectedViolations) throws Exception {
        final DefaultConfiguration requireThisConfig =
                createModuleConfig(RequireThisCheck.class);
        requireThisConfig.addAttribute("validateOnlyOverlapping", "false");

        final DefaultConfiguration treewalkerConfig = createModuleConfig(TreeWalker.class);
        treewalkerConfig.addChild(requireThisConfig);
        final DefaultConfiguration checkerConfig = createRootConfig(treewalkerConfig);
        checkerConfig.addChild(config);

        verify(checkerConfig, fileName, expectedViolations);
    }
}
