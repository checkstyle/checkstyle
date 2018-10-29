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

package com.puppycrawl.tools.checkstyle.api;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AbstractCheckTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/api/abstractcheck";
    }

    @Test
    public void testGetRequiredTokens() {
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return CommonUtil.EMPTY_INT_ARRAY;
            }

            @Override
            public int[] getAcceptableTokens() {
                return getDefaultTokens();
            }

            @Override
            public int[] getRequiredTokens() {
                return getDefaultTokens();
            }
        };
        // Eventually it will become clear abstract method
        Assert.assertArrayEquals("Invalid number of tokens, should be empty",
                CommonUtil.EMPTY_INT_ARRAY, check.getRequiredTokens());
    }

    @Test
    public void testGetAcceptable() {
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return CommonUtil.EMPTY_INT_ARRAY;
            }

            @Override
            public int[] getAcceptableTokens() {
                return getDefaultTokens();
            }

            @Override
            public int[] getRequiredTokens() {
                return getDefaultTokens();
            }
        };
        // Eventually it will become clear abstract method
        Assert.assertArrayEquals("Invalid number of tokens, should be empty",
                CommonUtil.EMPTY_INT_ARRAY, check.getAcceptableTokens());
    }

    @Test
    public void testVisitToken() {
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return CommonUtil.EMPTY_INT_ARRAY;
            }

            @Override
            public int[] getAcceptableTokens() {
                return CommonUtil.EMPTY_INT_ARRAY;
            }

            @Override
            public int[] getRequiredTokens() {
                return CommonUtil.EMPTY_INT_ARRAY;
            }
        };
        final AbstractCheck checkSpy = spy(check);
        // Eventually it will become clear abstract method
        checkSpy.visitToken(null);

        verify(checkSpy, times(1)).visitToken(null);
    }

    @Test
    public void testGetLine() throws Exception {
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return CommonUtil.EMPTY_INT_ARRAY;
            }

            @Override
            public int[] getAcceptableTokens() {
                return getDefaultTokens();
            }

            @Override
            public int[] getRequiredTokens() {
                return getDefaultTokens();
            }
        };
        check.setFileContents(new FileContents(new FileText(
            new File(getPath("InputAbstractCheckTestFileContents.java")),
            Charset.defaultCharset().name())));

        Assert.assertEquals("Invalid line content", " * I'm a javadoc", check.getLine(3));
    }

    @Test
    public void testGetTabWidth() {
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return CommonUtil.EMPTY_INT_ARRAY;
            }

            @Override
            public int[] getAcceptableTokens() {
                return getDefaultTokens();
            }

            @Override
            public int[] getRequiredTokens() {
                return getDefaultTokens();
            }
        };
        final int tabWidth = 4;
        check.setTabWidth(tabWidth);

        Assert.assertEquals("Invalid tab width", tabWidth, check.getTabWidth());
    }

    @Test
    public void testGetClassLoader() {
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return CommonUtil.EMPTY_INT_ARRAY;
            }

            @Override
            public int[] getAcceptableTokens() {
                return getDefaultTokens();
            }

            @Override
            public int[] getRequiredTokens() {
                return getDefaultTokens();
            }
        };
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        check.setClassLoader(classLoader);

        Assert.assertEquals("Invalid classloader", classLoader, check.getClassLoader());
    }

    @Test
    public void testGetAcceptableTokens() {
        final int[] defaultTokens = {TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF};
        final int[] acceptableTokens = {TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF};
        final int[] requiredTokens = {TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF};
        final AbstractCheck check = new AbstractCheck() {
            @Override
            public int[] getDefaultTokens() {
                return defaultTokens;
            }

            @Override
            public int[] getAcceptableTokens() {
                return acceptableTokens;
            }

            @Override
            public int[] getRequiredTokens() {
                return requiredTokens;
            }
        };

        Assert.assertArrayEquals("Invalid default tokens",
                defaultTokens, check.getDefaultTokens());
        Assert.assertArrayEquals("Invalid acceptable tokens",
                defaultTokens, check.getAcceptableTokens());
        Assert.assertArrayEquals("Invalid required tokens",
                requiredTokens, check.getRequiredTokens());
    }

    @Test
    public void testClearMessages() {
        final AbstractCheck check = new DummyAbstractCheck();

        check.log(1, "key", "args");
        Assert.assertEquals("Invalid message size", 1, check.getMessages().size());
        check.clearMessages();
        Assert.assertEquals("Invalid message size", 0, check.getMessages().size());
    }

    private static final class DummyAbstractCheck extends AbstractCheck {

        private static final int[] DUMMY_ARRAY = {6};

        @Override
        public int[] getDefaultTokens() {
            return Arrays.copyOf(DUMMY_ARRAY, 1);
        }

        @Override
        public int[] getAcceptableTokens() {
            return Arrays.copyOf(DUMMY_ARRAY, 1);
        }

        @Override
        public int[] getRequiredTokens() {
            return Arrays.copyOf(DUMMY_ARRAY, 1);
        }

        @Override
        protected Map<String, String> getCustomMessages() {
            final Map<String, String> messages = new HashMap<>();
            messages.put("key", "value");
            return messages;
        }

    }

}
