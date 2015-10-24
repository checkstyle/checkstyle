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

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ HeaderCheck.class, HeaderCheckTest.class, AbstractHeaderCheck.class })
public class HeaderCheckTest extends BaseFileSetCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "header" + File.separator + filename);
    }

    protected String getConfigPath(String filename) throws IOException {
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
            final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
            verify(checkConfig, getPath("InputRegexpHeader1.java"), expected);
        }
        catch (CheckstyleException ex) {
            // Exception is not expected
            fail();
        }
    }

    @Test
    public void testNonExistingHeaderFile() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("nonExisting.file"));
        try {
            createChecker(checkConfig);
            fail();
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage()
                    .startsWith("cannot initialize module"
                            + " com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck"
                            + " - Unable to find: "));
            assertTrue(ex.getMessage().endsWith("nonExisting.file"));
        }
    }

    @Test
    public void testInvalidCharset() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HeaderCheck.class);
        checkConfig.addAttribute("headerFile", getConfigPath("java.header"));
        checkConfig.addAttribute("charset", "XSO-8859-1");
        try {
            createChecker(checkConfig);
            fail();
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
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getConfigPath("java2.header"), expected);
    }

    @Test
    public void testSetHeaderTwice() {
        final HeaderCheck check = new HeaderCheck();
        check.setHeader("Header");
        try {
            check.setHeader("Header2");
            fail();
        }
        catch (ConversionException ex) {
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
        catch (ConversionException ex) {
            assertTrue(ex.getCause() instanceof IOException);
            assertEquals("unable to load header", ex.getMessage());
        }
    }

    @Test
    public void testIoExceptionWhenLoadingHeaderFile() throws Exception {
        final HeaderCheck check = PowerMockito.spy(new HeaderCheck());
        PowerMockito.doThrow(new IOException("expected exception")).when(check, "loadHeader",
                anyObject());

        check.setHeaderFile(getPath("InputRegexpHeader1.java"));

        final Method loadHeaderFile = AbstractHeaderCheck.class.getDeclaredMethod("loadHeaderFile");
        loadHeaderFile.setAccessible(true);
        try {
            loadHeaderFile.invoke(check);
            fail("Exception expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof CheckstyleException);
            assertEquals("unable to load header file "
                    + getPath("InputRegexpHeader1.java"), ex.getCause().getMessage());
        }
    }
}
