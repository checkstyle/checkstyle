////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck.MSG_JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder;
import com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressWarningsFilterTest
    extends AbstractModuleTestSupport {

    private static final String[] ALL_MESSAGES = {
        "16: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "17: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "19: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "22:45: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "I", "^[a-z][a-zA-Z0-9]*$"),
        "24:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
        "25:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "K", "^[a-z][a-zA-Z0-9]*$"),
        "29:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
        "29:32: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "X", "^[a-z][a-zA-Z0-9]*$"),
        "33:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "34:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "n", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "39:17: "
            + getCheckMessage(ParameterNumberCheck.class, ParameterNumberCheck.MSG_KEY, 7, 8),
        "45:9: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "56:9: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "61: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "71: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "76: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "77: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "83: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "84: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "90: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "91: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "97: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
    };

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswarningsfilter";
    }

    @Test
    public void testNone() throws Exception {
        final DefaultConfiguration filterConfig = null;
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWarningsFilter.class);
        final String[] suppressed = {
            "24:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
            "29:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
            "33:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "39:17: "
                + getCheckMessage(ParameterNumberCheck.class, ParameterNumberCheck.MSG_KEY, 7, 8),
            "56:9: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "71: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "77: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "84: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "91: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    private void verifySuppressed(Configuration moduleConfig,
            String... aSuppressed)
            throws Exception {
        verifySuppressed(moduleConfig, getPath("InputSuppressWarningsFilter.java"),
               ALL_MESSAGES, aSuppressed);
    }

    private void verifySuppressed(Configuration moduleConfig, String fileName,
            String[] expectedViolations, String... suppressedViolations) throws Exception {
        final DefaultConfiguration holderConfig =
            createModuleConfig(SuppressWarningsHolder.class);
        holderConfig.addAttribute("aliasList",
            "com.puppycrawl.tools.checkstyle.checks.sizes."
                + "ParameterNumberCheck=paramnum");

        final DefaultConfiguration memberNameCheckConfig =
                createModuleConfig(MemberNameCheck.class);
        memberNameCheckConfig.addAttribute("id", "ignore");

        final DefaultConfiguration constantNameCheckConfig =
            createModuleConfig(ConstantNameCheck.class);
        constantNameCheckConfig.addAttribute("id", "");

        final DefaultConfiguration uncommentedMainCheckConfig =
            createModuleConfig(UncommentedMainCheck.class);
        uncommentedMainCheckConfig.addAttribute("id", "ignore");

        final DefaultConfiguration treewalkerConfig =
                createModuleConfig(TreeWalker.class);
        treewalkerConfig.addChild(holderConfig);
        treewalkerConfig.addChild(memberNameCheckConfig);
        treewalkerConfig.addChild(constantNameCheckConfig);
        treewalkerConfig.addChild(createModuleConfig(ParameterNumberCheck.class));
        treewalkerConfig.addChild(createModuleConfig(IllegalCatchCheck.class));
        treewalkerConfig.addChild(uncommentedMainCheckConfig);

        final DefaultConfiguration missingJavadocConfig =
                createModuleConfig(MissingJavadocTypeCheck.class);
        missingJavadocConfig.addAttribute("scope", "private");
        treewalkerConfig.addChild(missingJavadocConfig);

        final DefaultConfiguration checkerConfig =
                createRootConfig(treewalkerConfig);
        if (moduleConfig != null) {
            checkerConfig.addChild(moduleConfig);
        }

        verify(checkerConfig,
                fileName,
            removeSuppressed(expectedViolations, suppressedViolations));
    }

    private static String[] removeSuppressed(String[] from, String... remove) {
        final Collection<String> coll = Arrays.stream(from).collect(Collectors.toList());
        coll.removeAll(Arrays.asList(remove));
        return coll.toArray(CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testSuppressById() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWarningsFilter.class);
        final String[] suppressedViolationMessages = {
            "6:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "8: "
                + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        };
        final String[] expectedViolationMessages = {
            "3: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "6:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "8: "
                + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        };

        verifySuppressed(filterConfig, getPath("InputSuppressWarningsFilterById.java"),
                expectedViolationMessages, suppressedViolationMessages);
    }

}
