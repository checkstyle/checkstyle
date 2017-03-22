////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck.MSG_LEGACY_PACKAGE_HTML;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck.MSG_PACKAGE_INFO;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class JavadocPackageCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "javadoc" + File.separator + filename);
    }

    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration config) {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(config);
        return dc;
    }

    @Test
    public void testMissing() throws Exception {
        final Configuration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = {
            "0: " + getCheckMessage(MSG_PACKAGE_INFO),
        };
        verify(
            createChecker(checkConfig),
            getPath("InputBadCls.java"),
            getPath("InputBadCls.java"),
            expected);
    }

    @Test
    public void testMissingWithAllowLegacy() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        checkConfig.addAttribute("allowLegacy", "true");
        final String[] expected = {
            "0: " + getCheckMessage(MSG_PACKAGE_INFO),
        };
        verify(
            createChecker(checkConfig),
            getPath("InputBadCls.java"),
            getPath("InputBadCls.java"),
            expected);
    }

    @Test
    public void testWithMultipleFiles() throws Exception {
        final Configuration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String path1 = getPath("InputNoJavadoc.java");
        final String path2 = getPath("InputBadTag.java");
        final String[] expected = {
            "0: " + getCheckMessage(MSG_PACKAGE_INFO),
        };
        verify(
            createChecker(checkConfig),
            new File[] {new File(path1), new File(path2)},
            path1,
            expected);
    }

    @Test
    public void testBoth() throws Exception {
        final Configuration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = {
            "0: " + getCheckMessage(MSG_LEGACY_PACKAGE_HTML),
        };
        verify(createChecker(checkConfig),
            getPath("bothfiles" + File.separator + "InputIgnored.java"),
            getPath("bothfiles" + File.separator + "InputIgnored.java"), expected);
    }

    @Test
    public void testHtmlDisallowed() throws Exception {
        final Configuration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = {
            "0: " + getCheckMessage(MSG_PACKAGE_INFO),
        };
        verify(createChecker(checkConfig),
            getPath("pkghtml" + File.separator + "InputIgnored.java"),
            getPath("pkghtml" + File.separator + "InputIgnored.java"), expected);
    }

    @Test
    public void testHtmlAllowed() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        checkConfig.addAttribute("allowLegacy", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(createChecker(checkConfig),
            getPath("pkghtml" + File.separator + "InputIgnored.java"),
            getPath("pkghtml" + File.separator + "package-info.java"), expected);
    }

    @Test
    public void testAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(createChecker(checkConfig),
            getPath("pkginfo" + File.separator + "annotation"
                    + File.separator + "package-info.java"),
            getPath("pkginfo" + File.separator + "annotation"
                    + File.separator + "package-info.java"), expected);
    }
}
