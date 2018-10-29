////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ HeaderCheck.class, HeaderCheckTest.class, AbstractHeaderCheck.class })
public class HeaderCheckTest extends AbstractModuleTestSupport {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

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

            assertTrue("Invalid exception message, should start with: " + messageStart,
                ex.getMessage().startsWith(messageStart));
            assertTrue("Invalid exception message, should start with: " + causeMessageStart,
                ex.getCause().getCause().getCause().getMessage().startsWith(causeMessageStart));
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
            assertEquals("Invalid exception message", "cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                    + " - Cannot set property 'charset' to 'XSO-8859-1' in module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck",
                    ex.getMessage());
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
            assertEquals("Invalid exception message", "cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                    + " - Cannot set property 'headerFile' to '' in module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck",
                    ex.getMessage());
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
            assertEquals("Invalid exception message", "cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                    + " - Cannot set property 'headerFile' to 'null' in module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck",
                    ex.getMessage());
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
            assertEquals("Invalid exception message", "header has already been set - "
                    + "set either header or headerFile, not both", ex.getMessage());
        }
    }

    @Test
    public void testIoExceptionWhenLoadingHeader() throws Exception {
        final HeaderCheck check = PowerMockito.spy(new HeaderCheck());
        PowerMockito.doThrow(new IOException("expected exception")).when(check, "loadHeader",
                any());

        try {
            check.setHeader("header");
            fail("Exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertTrue("Invalid exception cause", ex.getCause() instanceof IOException);
            assertEquals("Invalid exception message", "unable to load header", ex.getMessage());
        }
    }

    @Test
    public void testIoExceptionWhenLoadingHeaderFile() throws Exception {
        final HeaderCheck check = PowerMockito.spy(new HeaderCheck());
        PowerMockito.doThrow(new IOException("expected exception")).when(check, "loadHeader",
                any());

        check.setHeaderFile(CommonUtil.getUriByFilename(getPath("InputHeaderRegexp.java")));

        final Method loadHeaderFile = AbstractHeaderCheck.class.getDeclaredMethod("loadHeaderFile");
        loadHeaderFile.setAccessible(true);
        try {
            loadHeaderFile.invoke(check);
            fail("Exception expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue("Invalid exception cause", ex.getCause() instanceof CheckstyleException);
            assertTrue("Invalid exception cause message",
                ex.getCause().getMessage().startsWith("unable to load header file "));
        }
    }

    @Test
    public void testCacheHeaderFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputHeaderjava.header"));

        final DefaultConfiguration checkerConfig = createRootConfig(checkConfig);
        final File cacheFile = temporaryFolder.newFile();
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
        final File cacheFile = temporaryFolder.newFile();
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
            assertEquals("Invalid exception message", "header has already been set - "
                    + "set either header or headerFile, not both", ex.getMessage());
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
}
