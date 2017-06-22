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

package com.puppycrawl.tools.checkstyle.api;

import java.io.File;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class AbstractCheckTest {
    private static final String INPUT_FOLDER =
        "src/test/resources/com/puppycrawl/tools/checkstyle/api/abstractcheck/";

    @Test
    public void testGetRequiredTokens() {
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return CommonUtils.EMPTY_INT_ARRAY;
            }
        };
        // Eventually it will become clear abstract method
        Assert.assertArrayEquals(CommonUtils.EMPTY_INT_ARRAY, check.getRequiredTokens());
    }

    @Test
    public void testGetAcceptable() {
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return CommonUtils.EMPTY_INT_ARRAY;
            }
        };
        // Eventually it will become clear abstract method
        Assert.assertArrayEquals(CommonUtils.EMPTY_INT_ARRAY, check.getAcceptableTokens());
    }

    @Test
    public void testVisitToken() {
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return CommonUtils.EMPTY_INT_ARRAY;
            }

            @Override
            public int[] getAcceptableTokens() {
                return CommonUtils.EMPTY_INT_ARRAY;
            }

            @Override
            public int[] getRequiredTokens() {
                return CommonUtils.EMPTY_INT_ARRAY;
            }
        };
        // Eventually it will become clear abstract method
        check.visitToken(null);
    }

    @Test
    public void testGetLine() throws Exception {
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return CommonUtils.EMPTY_INT_ARRAY;
            }
        };
        check.setFileContents(new FileContents(new FileText(
            new File(INPUT_FOLDER + "InputAbstractCheckTestFileContence.java"),
            Charset.defaultCharset().name())));

        Assert.assertEquals(" * I'm a javadoc", check.getLine(3));
    }

    @Test
    public void testGetTabWidth() throws Exception {
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return CommonUtils.EMPTY_INT_ARRAY;
            }
        };
        final int tabWidth = 4;
        check.setTabWidth(tabWidth);

        Assert.assertEquals(tabWidth, check.getTabWidth());
    }

    @Test
    public void testGetClassLoader() throws Exception {
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return CommonUtils.EMPTY_INT_ARRAY;
            }
        };
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        check.setClassLoader(classLoader);

        Assert.assertEquals(classLoader, check.getClassLoader());
    }

    @Test
    public void testGetAcceptableTokens() throws Exception {
        final int[] defaultTokens = {TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF};
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return defaultTokens;
            }
        };

        Assert.assertNotEquals(defaultTokens, check.getAcceptableTokens());
        Assert.assertArrayEquals(defaultTokens, check.getAcceptableTokens());
    }
}
