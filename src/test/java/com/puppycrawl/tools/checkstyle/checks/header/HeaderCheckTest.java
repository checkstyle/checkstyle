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

package com.puppycrawl.tools.checkstyle.checks.header;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck.MSG_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck.MSG_MISSING;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.URI;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedConstruction;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class HeaderCheckTest extends AbstractModuleTestSupport {

    @TempDir
    public File temporaryFolder;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/header/header";
    }

    @Test
    public void testStaticHeader() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputHeaderjava.header"));
        checkConfig.addProperty("ignoreLines", "");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISSING),
        };
        verify(checkConfig, getPath("InputHeader.java"), expected);
    }

    @Test
    public void testNoHeader() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);

        createChecker(checkConfig);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputHeaderRegexp.java"), expected);
    }

    @Test
    public void testWhitespaceHeader() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("header", "\n    \n");

        createChecker(checkConfig);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputHeaderRegexp.java"), expected);
    }

    @Test
    public void testNonExistentHeaderFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("nonExistent.file"));
        final CheckstyleException ex = getExpectedThrowable(CheckstyleException.class,
                () -> createChecker(checkConfig));
        assertWithMessage("Invalid exception message")
                .that(ex)
                .hasMessageThat()
                        .startsWith("cannot initialize module"
                            + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                            + " - illegal value ");
        assertWithMessage("Invalid cause exception message")
                .that(ex)
                .hasCauseThat()
                .hasCauseThat()
                .hasCauseThat()
                .hasMessageThat()
                        .startsWith("Unable to find: ");
    }

    @Test
    public void testInvalidCharset() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputHeaderjava.header"));
        checkConfig.addProperty("charset", "XSO-8859-1");
        final CheckstyleException ex = getExpectedThrowable(CheckstyleException.class,
                () -> createChecker(checkConfig));
        assertWithMessage("Invalid exception message")
                .that(ex)
                .hasMessageThat()
                        .isEqualTo("cannot initialize module"
                                + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                                + " - Cannot set property 'charset' to 'XSO-8859-1'");
        assertWithMessage("Invalid cause exception message")
                .that(ex)
                .hasCauseThat()
                .hasCauseThat()
                .hasCauseThat()
                .hasMessageThat()
                        .startsWith("unsupported charset: 'XSO-8859-1'");
    }

    @Test
    public void testEmptyFilename() {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("headerFile", "");
        final CheckstyleException ex = getExpectedThrowable(CheckstyleException.class,
                () -> createChecker(checkConfig));
        assertWithMessage("Invalid exception message")
                .that(ex)
                .hasMessageThat()
                        .isEqualTo("cannot initialize module"
                                + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                                + " - Cannot set property 'headerFile' to ''");
        assertWithMessage("Invalid cause exception message")
                .that(ex)
                .hasCauseThat()
                .hasCauseThat()
                .hasCauseThat()
                .hasMessageThat()
                        .isEqualTo("property 'headerFile' is missing or invalid in module"
                                + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck");
    }

    @Test
    public void testNullFilename() {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("headerFile", null);
        final CheckstyleException ex = getExpectedThrowable(CheckstyleException.class,
                () -> createChecker(checkConfig));
        assertWithMessage("Invalid exception message")
                .that(ex)
                .hasMessageThat()
                        .isEqualTo("cannot initialize module"
                                + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                                + " - Cannot set property 'headerFile' to 'null'");
    }

    @Test
    public void testNotMatch() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputHeaderjava.header"));
        checkConfig.addProperty("ignoreLines", "");
        final String[] expected = {
            "2: " + getCheckMessage(MSG_MISMATCH,
                    "// checkstyle: Checks Java source code and other text files for adherence to a"
                        + " set of rules."),
        };
        verify(checkConfig, getPath("InputHeaderjava2.header"), expected);
    }

    @Test
    public void testIgnore() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputHeaderjava.header"));
        checkConfig.addProperty("ignoreLines", "2");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputHeaderjava2.header"), expected);
    }

    @Test
    public void testSetHeaderTwice() {
        final HeaderCheck check = new HeaderCheck();
        check.setHeader("Header");
        final IllegalArgumentException ex =
                getExpectedThrowable(IllegalArgumentException.class,
                        () -> check.setHeader("Header2"));
        assertWithMessage("Invalid exception message")
                .that(ex)
                .hasMessageThat()
                        .isEqualTo("header has already been set - "
                                + "set either header or headerFile, not both");
    }

    @Test
    public void testIoExceptionWhenLoadingHeaderFile() throws Exception {
        final HeaderCheck check = new HeaderCheck();
        check.setHeaderFile(new URI("test://bad"));

        final ReflectiveOperationException ex =
                getExpectedThrowable(ReflectiveOperationException.class,
                        () -> TestUtil.invokeMethod(check, "loadHeaderFile"));
        assertWithMessage("Invalid exception cause message")
            .that(ex)
                .hasCauseThat()
                    .hasMessageThat()
                    .startsWith("unable to load header file ");
    }

    @Test
    public void testCacheHeaderFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputHeaderjava.header"));

        final DefaultConfiguration checkerConfig = createRootConfig(checkConfig);
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISSING),
        };

        verify(checkerConfig, getPath("InputHeader.java"), expected);
        // One more time to use cache.
        verify(checkerConfig, getPath("InputHeader.java"), expected);
    }

    @Test
    public void testCacheHeaderWithoutFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("header", "Test");

        final DefaultConfiguration checkerConfig = createRootConfig(checkConfig);
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISMATCH, "Test"),
        };

        verify(checkerConfig, getPath("InputHeader.java"), expected);
    }

    @Test
    public void testIgnoreLinesSorted() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputHeaderjava.header"));
        checkConfig.addProperty("ignoreLines", "4,2,3");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputHeaderjava3.header"), expected);
    }

    @Test
    public void testLoadHeaderFileTwice() {
        final HeaderCheck check = new HeaderCheck();
        check.setHeader("Header");
        final ReflectiveOperationException ex =
                getExpectedThrowable(ReflectiveOperationException.class,
                        () -> TestUtil.invokeMethod(check, "loadHeaderFile"));
        assertWithMessage("Invalid exception cause message")
                .that(ex)
                .hasCauseThat()
                        .hasMessageThat()
                                .isEqualTo("header has already been set - "
                                    + "set either header or headerFile, not both");
    }

    @Test
    public void testHeaderIsValidWithBlankLines() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputHeaderjava.blank-lines.header"));
        verify(checkConfig, getPath("InputHeaderBlankLines.java"));
    }

    @Test
    public void testHeaderIsValidWithBlankLinesBlockStyle() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputHeaderjava.blank-lines2.header"));
        verify(checkConfig, getPath("InputHeaderBlankLines2.java"));
    }

    @Test
    public void testExternalResource() throws Exception {
        final HeaderCheck check = new HeaderCheck();
        final URI uri = CommonUtil.getUriByFilename(getPath("InputHeaderjava.header"));
        check.setHeaderFile(uri);
        final Set<String> results = check.getExternalResourceLocations();
        assertWithMessage("Invalid result size")
            .that(results.size())
            .isEqualTo(1);
        assertWithMessage("Invalid resource location")
            .that(results.iterator().next())
            .isEqualTo(uri.toASCIIString());
    }

    @Test
    public void testIoExceptionWhenLoadingHeader() {
        final HeaderCheck check = new HeaderCheck();
        try (MockedConstruction<LineNumberReader> mocked = mockConstruction(
                LineNumberReader.class, (mock, context) -> {
                    when(mock.readLine()).thenThrow(IOException.class);
                })) {
            final IllegalArgumentException ex =
                    getExpectedThrowable(IllegalArgumentException.class,
                            () -> check.setHeader("header"));
            assertWithMessage("Invalid exception cause")
                    .that(ex)
                    .hasCauseThat()
                            .isInstanceOf(IOException.class);
            assertWithMessage("Invalid exception message")
                    .that(ex)
                    .hasMessageThat()
                            .isEqualTo("unable to load header");
        }
    }

}
