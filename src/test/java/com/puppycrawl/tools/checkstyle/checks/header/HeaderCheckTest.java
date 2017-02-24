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

package com.puppycrawl.tools.checkstyle.checks.header;

import static com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck.MSG_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck.MSG_MISSING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;

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

import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.BriefUtLogger;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ HeaderCheck.class, HeaderCheckTest.class, AbstractHeaderCheck.class })
public class HeaderCheckTest extends BaseFileSetCheckTestSupport {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "header" + File.separator + filename);
    }

    private String getConfigPath(String filename) throws IOException {
        return super.getPath("configs" + File.separator + filename);
    }

    @Test
    public void testStaticHeader() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getConfigPath("java.header"));
        checkConfig.addAttribute("ignoreLines", "");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISSING),
        };
        verify(checkConfig, getPath("InputHeader.java"), expected);
    }

    @Test
    public void testNoHeader() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HeaderCheck.class);
        try {
            createChecker(checkConfig);
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
            verify(checkConfig, getPath("InputRegexpHeader1.java"), expected);
        }
        catch (CheckstyleException ex) {
            // Exception is not expected
            fail("Exception is not expected");
        }
    }

    @Test
    public void testWhitespaceHeader() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("header", "\n    \n");
        try {
            createChecker(checkConfig);
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
            verify(checkConfig, getPath("InputRegexpHeader1.java"), expected);
        }
        catch (CheckstyleException ex) {
            // Exception is not expected
            fail("Exception is not expected");
        }
    }

    @Test
    public void testNonExistingHeaderFile() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("nonExisting.file"));
        try {
            createChecker(checkConfig);
            fail("CheckstyleException is expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage()
                    .startsWith("cannot initialize module"
                            + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                            + " - illegal value "));
            assertTrue(ex.getCause().getCause().getCause().getMessage()
                    .startsWith("Unable to find: "));
        }
    }

    @Test
    public void testInvalidCharset() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getConfigPath("java.header"));
        checkConfig.addAttribute("charset", "XSO-8859-1");
        try {
            createChecker(checkConfig);
            fail("CheckstyleException is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                    + " - Cannot set property 'charset' to 'XSO-8859-1' in module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck",
                    ex.getMessage());
        }
    }

    @Test
    public void testEmptyFilename() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", "");
        try {
            createChecker(checkConfig);
            fail("Checker creation should not succeed with invalid headerFile");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                    + " - Cannot set property 'headerFile' to '' in module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck",
                    ex.getMessage());
        }
    }

    @Test
    public void testNullFilename() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", null);
        try {
            createChecker(checkConfig);
            fail("Checker creation should not succeed with null headerFile");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                    + " - Cannot set property 'headerFile' to 'null' in module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck",
                    ex.getMessage());
        }
    }

    @Test
    public void testNotMatch() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getConfigPath("java.header"));
        checkConfig.addAttribute("ignoreLines", "");
        final String[] expected = {
            "2: " + getCheckMessage(MSG_MISMATCH,
                    "// checkstyle: Checks Java source code for adherence to a set of rules."),
        };
        verify(checkConfig, getConfigPath("java2.header"), expected);
    }

    @Test
    public void testIgnore() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getConfigPath("java.header"));
        checkConfig.addAttribute("ignoreLines", "2");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getConfigPath("java2.header"), expected);
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
                    + "set either header or headerFile, not both", ex.getMessage());
        }
    }

    @Test
    public void testIoExceptionWhenLoadingHeader() throws Exception {
        final HeaderCheck check = PowerMockito.spy(new HeaderCheck());
        PowerMockito.doThrow(new IOException("expected exception")).when(check, "loadHeader",
                anyObject());

        try {
            check.setHeader("header");
            fail("Exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertTrue(ex.getCause() instanceof IOException);
            assertEquals("unable to load header", ex.getMessage());
        }
    }

    @Test
    public void testIoExceptionWhenLoadingHeaderFile() throws Exception {
        final HeaderCheck check = PowerMockito.spy(new HeaderCheck());
        PowerMockito.doThrow(new IOException("expected exception")).when(check, "loadHeader",
                anyObject());

        check.setHeaderFile(CommonUtils.getUriByFilename(getPath("InputRegexpHeader1.java")));

        final Method loadHeaderFile = AbstractHeaderCheck.class.getDeclaredMethod("loadHeaderFile");
        loadHeaderFile.setAccessible(true);
        try {
            loadHeaderFile.invoke(check);
            fail("Exception expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof CheckstyleException);
            assertTrue(ex.getCause().getMessage().startsWith("unable to load header file "));
        }
    }

    @Test
    public void testCacheHeaderFile() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getConfigPath("java.header"));

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyle_checks");
        checkerConfig.addChild(checkConfig);
        checkerConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISSING),
        };

        verify(checker, getPath("InputHeader.java"), expected);
        // One more time to use cache.
        verify(checker, getPath("InputHeader.java"), expected);

    }

    @Test
    public void testCacheHeaderWithoutFile() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("header", "Test");

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyle_checks");
        checkerConfig.addChild(checkConfig);
        checkerConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final String[] expected = {
            "1: " + getCheckMessage(MSG_MISMATCH, "Test"),
        };

        verify(checker, getPath("InputHeader.java"), expected);
    }
}
