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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Collections;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class JavadocPackageCheckTest
    extends AbstractModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocpackage";
    }

    @Test
    public void testMissing() throws Exception {
        final Configuration checkConfig = createModuleConfig(JavadocPackageCheck.class);
        final String[] expected = {
            "0: " + getCheckMessage(MSG_PACKAGE_INFO),
        };
        verify(
            createChecker(checkConfig),
            getPath("InputJavadocPackageBadCls.java"),
            getPath("InputJavadocPackageBadCls.java"),
            expected);
    }

    @Test
    public void testMissingWithAllowLegacy() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocPackageCheck.class);
        checkConfig.addAttribute("allowLegacy", "true");
        final String[] expected = {
            "0: " + getCheckMessage(MSG_PACKAGE_INFO),
        };
        verify(
            createChecker(checkConfig),
            getPath("InputJavadocPackageBadCls.java"),
            getPath("InputJavadocPackageBadCls.java"),
            expected);
    }

    @Test
    public void testWithMultipleFiles() throws Exception {
        final Configuration checkConfig = createModuleConfig(JavadocPackageCheck.class);
        final String path1 = getPath("InputJavadocPackageNoJavadoc.java");
        final String path2 = getPath("InputJavadocPackageBadTag.java");
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
        final Configuration checkConfig = createModuleConfig(JavadocPackageCheck.class);
        final String[] expected = {
            "0: " + getCheckMessage(MSG_LEGACY_PACKAGE_HTML),
        };
        verify(createChecker(checkConfig),
            getPath("bothfiles" + File.separator + "InputJavadocPackageBothIgnored.java"),
            getPath("bothfiles"
            + File.separator + "InputJavadocPackageBothIgnored.java"), expected);
    }

    @Test
    public void testHtmlDisallowed() throws Exception {
        final Configuration checkConfig = createModuleConfig(JavadocPackageCheck.class);
        final String[] expected = {
            "0: " + getCheckMessage(MSG_PACKAGE_INFO),
        };
        verify(createChecker(checkConfig),
            getPath("pkghtml" + File.separator + "InputJavadocPackageHtmlIgnored.java"),
            getPath("pkghtml" + File.separator + "InputJavadocPackageHtmlIgnored.java"), expected);
    }

    @Test
    public void testHtmlAllowed() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocPackageCheck.class);
        checkConfig.addAttribute("allowLegacy", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(createChecker(checkConfig),
            getPath("pkghtml" + File.separator + "InputJavadocPackageHtmlIgnored.java"),
            getPath("pkghtml" + File.separator + "package-info.java"), expected);
    }

    @Test
    public void testAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocPackageCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(createChecker(checkConfig),
            getPath("annotation"
                    + File.separator + "package-info.java"),
            getPath("annotation"
                    + File.separator + "package-info.java"), expected);
    }

    /**
     * Test require readable file with no parent to be used.
     * Usage of Mockito.spy() is the only way to satisfy these requirements
     * without the need to create new file in current working directory.
     *
     * @throws Exception if error occurs
     */
    @Test
    public void testWithFileWithoutParent() throws Exception {
        final DefaultConfiguration moduleConfig = createModuleConfig(JavadocPackageCheck.class);
        final File fileWithoutParent = spy(new File(getPath("noparentfile"
                    + File.separator + "package-info.java")));
        when(fileWithoutParent.getParent()).thenReturn(null);
        when(fileWithoutParent.getParentFile()).thenReturn(null);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(createChecker(moduleConfig),
                new File[] {fileWithoutParent},
                getPath("annotation"
                    + File.separator + "package-info.java"), expected);
    }

    /**
     * Using direct call to check here because there is no other way
     * to reproduce exception with invalid canonical path.
     *
     * @throws Exception if error occurs
     */
    @Test
    public void testCheckstyleExceptionIfFailedToGetCanonicalPathToFile() throws Exception {
        final JavadocPackageCheck check = new JavadocPackageCheck();
        final File fileWithInvalidPath = new File("\u0000\u0000\u0000");
        final FileText mockFileText = new FileText(fileWithInvalidPath, Collections.emptyList());
        final String expectedExceptionMessage =
                "Exception while getting canonical path to file " + fileWithInvalidPath.getPath();
        try {
            check.processFiltered(fileWithInvalidPath, mockFileText);
            fail("CheckstyleException expected to be thrown");
        }
        catch (CheckstyleException ex) {
            assertEquals("Invalid exception message. Expected: " + expectedExceptionMessage,
                    expectedExceptionMessage, ex.getMessage());
        }
    }
}
