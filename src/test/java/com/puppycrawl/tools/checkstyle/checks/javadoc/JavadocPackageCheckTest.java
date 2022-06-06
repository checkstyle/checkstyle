///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck.MSG_LEGACY_PACKAGE_HTML;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck.MSG_PACKAGE_INFO;

import java.io.File;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

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
            "1: " + getCheckMessage(MSG_PACKAGE_INFO),
        };
        verify(
            checkConfig,
            getPath("InputJavadocPackageBadCls.java"),
            expected);
    }

    @Test
    public void testMissingWithAllowLegacy() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocPackageCheck.class);
        checkConfig.addProperty("allowLegacy", "true");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_PACKAGE_INFO),
        };
        verify(
            checkConfig,
            getPath("InputJavadocPackageBadCls2.java"),
            expected);
    }

    @Test
    public void testWithMultipleFiles() throws Exception {
        final Configuration checkConfig = createModuleConfig(JavadocPackageCheck.class);
        final String path1 = getPath("InputJavadocPackageNoJavadoc.java");
        final String path2 = getPath("InputJavadocPackageBadTag.java");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_PACKAGE_INFO),
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
            "1: " + getCheckMessage(MSG_LEGACY_PACKAGE_HTML),
        };
        verify(checkConfig,
            getPath("bothfiles" + File.separator + "InputJavadocPackageBothIgnored.java"),
            expected);
    }

    @Test
    public void testHtmlDisallowed() throws Exception {
        final Configuration checkConfig = createModuleConfig(JavadocPackageCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_PACKAGE_INFO),
        };
        verify(checkConfig,
            getPath("pkghtml" + File.separator + "InputJavadocPackageHtmlIgnored.java"), expected);
    }

    @Test
    public void testHtmlAllowed() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocPackageCheck.class);
        checkConfig.addProperty("allowLegacy", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(createChecker(checkConfig),
            getPath("pkghtml" + File.separator + "InputJavadocPackageHtmlIgnored2.java"),
            getPath("pkghtml" + File.separator + "package-info.java"), expected);
    }

    @Test
    public void testAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocPackageCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getPath("annotation"
                    + File.separator + "package-info.java"), expected);
    }

    /**
     * Using direct call to check here because there is no other way
     * to reproduce exception with invalid canonical path.
     */
    @Test
    public void testCheckstyleExceptionIfFailedToGetCanonicalPathToFile() {
        final JavadocPackageCheck check = new JavadocPackageCheck();
        final File fileWithInvalidPath = new File("\u0000\u0000\u0000");
        final FileText mockFileText = new FileText(fileWithInvalidPath, Collections.emptyList());
        final String expectedExceptionMessage =
                "Exception while getting canonical path to file " + fileWithInvalidPath.getPath();
        try {
            check.processFiltered(fileWithInvalidPath, mockFileText);
            assertWithMessage("CheckstyleException expected to be thrown").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message. Expected: " + expectedExceptionMessage)
                .that(ex.getMessage())
                .isEqualTo(expectedExceptionMessage);
        }
    }

    @Test
    public void testNonJava() throws Exception {
        final Configuration checkConfig = createModuleConfig(JavadocPackageCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(
            checkConfig,
            getPath("InputJavadocPackageNotJava.txt"),
            expected);
    }

    @Test
    public void testWithFileWithoutParent() throws Exception {
        final DefaultConfiguration moduleConfig = createModuleConfig(JavadocPackageCheck.class);
        final String path = getPath("annotation" + File.separator + "package-info.java");
        final File fileWithoutParent = new MockFile(path);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(createChecker(moduleConfig), new File[] {fileWithoutParent}, path, expected);
    }

    private static class MockFile extends File {

        /** A unique serial version identifier. */
        private static final long serialVersionUID = 7550724727327435271L;

        /* package */ MockFile(String path) {
            super(path);
        }

        /** This method is overridden to emulate a file without parent. */
        @Override
        public String getParent() {
            return null;
        }

    }

}
