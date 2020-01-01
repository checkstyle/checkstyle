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

package com.puppycrawl.tools.checkstyle.checks.header;

import static com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck.MSG_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck.MSG_MISSING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.net.URI;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
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
        checkConfig.addAttribute("headerFile", getPath("InputHeaderjava.header"));
        checkConfig.addAttribute("ignoreLines", "");
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
        checkConfig.addAttribute("header", "\n    \n");

        createChecker(checkConfig);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputHeaderRegexp.java"), expected);
    }

    @Test
    public void testNonExistentHeaderFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("nonExistent.file"));
        try {
            createChecker(checkConfig);
            fail("CheckstyleException is expected");
        }
        catch (CheckstyleException ex) {
            final String messageStart = "cannot initialize module"
                + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                + " - illegal value ";
            final String causeMessageStart = "Unable to find: ";

            assertTrue(ex.getMessage().startsWith(messageStart),
                    "Invalid exception message, should start with: " + messageStart);
            assertTrue(
                    ex.getCause().getCause().getCause().getMessage().startsWith(causeMessageStart),
                    "Invalid exception message, should start with: " + causeMessageStart);
        }
    }

    @Test
    public void testInvalidCharset() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputHeaderjava.header"));
        checkConfig.addAttribute("charset", "XSO-8859-1");
        try {
            createChecker(checkConfig);
            fail("CheckstyleException is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                    + " - Cannot set property 'charset' to 'XSO-8859-1'",
                    ex.getMessage(), "Invalid exception message");
            assertEquals("unsupported charset: 'XSO-8859-1'",
                    ex.getCause().getCause().getCause().getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testEmptyFilename() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", "");
        try {
            createChecker(checkConfig);
            fail("Checker creation should not succeed with invalid headerFile");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                    + " - Cannot set property 'headerFile' to ''",
                    ex.getMessage(), "Invalid exception message");
            assertEquals("property 'headerFile' is missing or invalid in module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck",
                    ex.getCause().getCause().getCause().getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testNullFilename() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", null);
        try {
            createChecker(checkConfig);
            fail("Checker creation should not succeed with null headerFile");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                    + " - Cannot set property 'headerFile' to 'null'",
                    ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testNotMatch() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputHeaderjava.header"));
        checkConfig.addAttribute("ignoreLines", "");
        final String[] expected = {
            "2: " + getCheckMessage(MSG_MISMATCH,
                    "// checkstyle: Checks Java source code for adherence to a set of rules."),
        };
        verify(checkConfig, getPath("InputHeaderjava2.header"), expected);
    }

    @Test
    public void testIgnore() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputHeaderjava.header"));
        checkConfig.addAttribute("ignoreLines", "2");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputHeaderjava2.header"), expected);
    }

    @Test
    public void testSetHeaderTwice() {
        final HeaderCheck check = new HeaderCheck();
        check.setHeader("Header");
        try {
            check.setHeader("Header2");
            fail("ConversionException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("header has already been set - "
                    + "set either header or headerFile, not both", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testIoExceptionWhenLoadingHeaderFile() throws Exception {
        final HeaderCheck check = new HeaderCheck();
        check.setHeaderFile(new URI("test://bad"));

        try {
            Whitebox.invokeMethod(check, "loadHeaderFile");
            fail("Exception expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().startsWith("unable to load header file "),
                    "Invalid exception cause message");
        }
    }

    @Test
    public void testCacheHeaderFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputHeaderjava.header"));

        final DefaultConfiguration checkerConfig = createRootConfig(checkConfig);
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

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
        checkConfig.addAttribute("header", "Test");

        final DefaultConfiguration checkerConfig = createRootConfig(checkConfig);
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISMATCH, "Test"),
        };

        verify(checkerConfig, getPath("InputHeader.java"), expected);
    }

    @Test
    public void testIgnoreLinesSorted() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputHeaderjava.header"));
        checkConfig.addAttribute("ignoreLines", "4,2,3");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputHeaderjava3.header"), expected);
    }

    @Test
    public void testLoadHeaderFileTwice() throws Exception {
        final HeaderCheck check = new HeaderCheck();
        check.setHeader("Header");
        try {
            Whitebox.invokeMethod(check, "loadHeaderFile");
            fail("ConversionException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("header has already been set - "
                    + "set either header or headerFile, not both", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testHeaderIsValidWithBlankLines() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputHeaderjava.blank-lines.header"));
        verify(checkConfig, getPath("InputHeaderBlankLines.java"));
    }

    @Test
    public void testHeaderIsValidWithBlankLinesBlockStyle() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputHeaderjava.blank-lines2.header"));
        verify(checkConfig, getPath("InputHeaderBlankLines2.java"));
    }

    @Test
    public void testExternalResource() throws Exception {
        final HeaderCheck check = new HeaderCheck();
        final URI uri = CommonUtil.getUriByFilename(getPath("InputHeaderjava.header"));
        check.setHeaderFile(uri);
        final Set<String> results = check.getExternalResourceLocations();
        assertEquals(1, results.size(), "Invalid result size");
        assertEquals(uri.toString(), results.iterator().next(), "Invalid resource location");
    }
}
