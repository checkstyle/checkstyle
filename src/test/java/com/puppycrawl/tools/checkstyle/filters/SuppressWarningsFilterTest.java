////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck;

public class SuppressWarningsFilterTest
    extends BaseCheckTestSupport {
    private static String[] sAllMessages = {
        "22:45: Name 'I' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "24:17: Name 'J' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "25:17: Name 'K' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "29:17: Name 'L' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "29:32: Name 'X' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "33:30: Name 'm' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        "34:30: Name 'n' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        "39:17: More than 7 parameters (found 8).",
        "45:9: Catching 'Exception' is not allowed.",
        "56:9: Catching 'Exception' is not allowed.",
    };

    @Test
    public void testNone() throws Exception {
        final DefaultConfiguration filterConfig = null;
        final String[] suppressed = {};
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressWarningsFilter.class);
        final String[] suppressed = {
            "24:17: Name 'J' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "29:17: Name 'L' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "33:30: Name 'm' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "39:17: More than 7 parameters (found 8).",
            "56:9: Catching 'Exception' is not allowed.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    public static DefaultConfiguration createFilterConfig(Class<?> classObj) {
        return new DefaultConfiguration(classObj.getName());
    }

    protected void verifySuppressed(Configuration aFilterConfig,
        String[] aSuppressed) throws Exception {
        verify(createChecker(aFilterConfig),
            getPath("filters/InputSuppressWarningsFilter.java"),
            removeSuppressed(sAllMessages, aSuppressed));
    }

    @Override
    protected Checker createChecker(Configuration filterConfig)
        throws Exception {
        final DefaultConfiguration checkerConfig =
            new DefaultConfiguration("configuration");
        final DefaultConfiguration checksConfig =
            createCheckConfig(TreeWalker.class);
        final DefaultConfiguration holderConfig =
            createCheckConfig(SuppressWarningsHolder.class);
        holderConfig.addAttribute("aliasList",
            "com.puppycrawl.tools.checkstyle.checks.sizes."
                + "ParameterNumberCheck=paramnum");
        checksConfig.addChild(holderConfig);
        checksConfig.addChild(createCheckConfig(MemberNameCheck.class));
        checksConfig.addChild(createCheckConfig(ConstantNameCheck.class));
        checksConfig.addChild(createCheckConfig(ParameterNumberCheck.class));
        checksConfig.addChild(createCheckConfig(IllegalCatchCheck.class));
        checkerConfig.addChild(checksConfig);
        if (filterConfig != null) {
            checkerConfig.addChild(filterConfig);
        }
        final Checker checker = new Checker();
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread()
            .getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefLogger(stream));
        return checker;
    }

    private static String[] removeSuppressed(String[] from, String[] remove) {
        final Collection<String> coll =
            Lists.newArrayList(Arrays.asList(from));
        coll.removeAll(Arrays.asList(remove));
        return coll.toArray(new String[coll.size()]);
    }
}
