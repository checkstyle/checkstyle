////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.junit.Test;


public class JavadocPackageCheckTest
    extends BaseCheckTestSupport
{
    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration aCheckConfig)
    {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    @Test
    public void testMissing() throws Exception
    {
        final Configuration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = {
            "0: Missing package-info.java file.",
        };
        verify(
            createChecker(checkConfig),
            getSrcPath("checks/javadoc/BadCls.java"),
            getSrcPath("checks/javadoc/BadCls.java"),
            expected);
    }
    @Test
    public void testBoth() throws Exception
    {
        final Configuration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = {
            "0: Legacy package.html file should be removed.",
        };
        verify(createChecker(checkConfig),
            getPath("javadoc/bothfiles/Ignored.java"),
            getPath("javadoc/bothfiles/Ignored.java"), expected);
    }

    @Test
    public void testHtmlDisallowed() throws Exception
    {
        final Configuration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = {
            "0: Missing package-info.java file.",
        };
        verify(createChecker(checkConfig),
            getPath("javadoc/pkghtml/Ignored.java"),
            getPath("javadoc/pkghtml/Ignored.java"), expected);
    }

    @Test
    public void testHtmlAllowed() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        checkConfig.addAttribute("allowLegacy", "true");
        final String[] expected = {};
        verify(createChecker(checkConfig),
            getPath("javadoc/pkghtml/Ignored.java"),
            getPath("javadoc/pkghtml/package-info.java"), expected);
    }
}
