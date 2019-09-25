////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AbstractCheckTest extends AbstractModuleTestSupport {

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
        assertArrayEquals("Invalid number of tokens, should be empty",
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
        assertArrayEquals("Invalid number of tokens, should be empty",
                CommonUtil.EMPTY_INT_ARRAY, check.getAcceptableTokens());
    }

    @Test
    public void testCommentNodes() {
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

        assertFalse("unexpected result", check.isCommentNodesRequired());
    }

    @Test
    public void testTokenNames() {
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

        check.setTokens("IDENT, EXPR, ELIST");
        assertArrayEquals("unexpected result",
            new String[] {"IDENT, EXPR, ELIST"},
            check.getTokenNames().toArray());
    }

    @Test
    public void testVisitToken() {
        final VisitCounterCheck check = new VisitCounterCheck();
        // Eventually it will become clear abstract method
        check.visitToken(null);

        assertEquals("expected call count", 1, check.count);
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

        assertEquals("Invalid line content", " * I'm a javadoc", check.getLine(3));
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

        assertEquals("Invalid tab width", tabWidth, check.getTabWidth());
    }

    @Test
    public void testFileContents() {
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
        final String[] lines = {"test"};
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList(lines)));
        check.setFileContents(fileContents);

        assertSame("Invalid file contents", fileContents, check.getFileContents());
        assertArrayEquals("Invalid lines", lines, check.getLines());
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

        assertEquals("Invalid classloader", classLoader, check.getClassLoader());
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

        assertArrayEquals("Invalid default tokens",
                defaultTokens, check.getDefaultTokens());
        assertArrayEquals("Invalid acceptable tokens",
                defaultTokens, check.getAcceptableTokens());
        assertArrayEquals("Invalid required tokens",
                requiredTokens, check.getRequiredTokens());
    }

    @Test
    public void testClearMessages() {
        final AbstractCheck check = new DummyAbstractCheck();

        check.log(1, "key", "args");
        assertEquals("Invalid message size", 1, check.getMessages().size());
        check.clearMessages();
        assertEquals("Invalid message size", 0, check.getMessages().size());
    }

    @Test
    public void testLineColumnLog() throws Exception {
        final ViolationCheck check = new ViolationCheck();
        check.configure(new DefaultConfiguration("check"));
        final File file = new File("fileName");
        final FileText theText = new FileText(file, Collections.singletonList("test123"));

        check.setFileContents(new FileContents(theText));
        check.clearMessages();
        check.visitToken(null);

        final SortedSet<LocalizedMessage> internalMessages = check.getMessages();

        assertEquals("Internal message should only have 2", 2, internalMessages.size());

        final Iterator<LocalizedMessage> iterator = internalMessages.iterator();

        final LocalizedMessage firstMessage = iterator.next();
        assertEquals("expected line", 1, firstMessage.getLineNo());
        assertEquals("expected column", 0, firstMessage.getColumnNo());

        final LocalizedMessage secondMessage = iterator.next();
        assertEquals("expected line", 1, secondMessage.getLineNo());
        assertEquals("expected column", 6, secondMessage.getColumnNo());
    }

    @Test
    public void testAstLog() throws Exception {
        final ViolationAstCheck check = new ViolationAstCheck();
        check.configure(new DefaultConfiguration("check"));
        final File file = new File("fileName");
        final FileText theText = new FileText(file, Collections.singletonList("test123"));

        check.setFileContents(new FileContents(theText));
        check.clearMessages();

        final DetailAST ast = new DetailAstImpl();
        ast.setLineNo(1);
        ast.setColumnNo(4);
        check.visitToken(ast);

        final SortedSet<LocalizedMessage> internalMessages = check.getMessages();

        assertEquals("Internal message should only have 1", 1, internalMessages.size());

        final LocalizedMessage firstMessage = internalMessages.iterator().next();
        assertEquals("expected line", 1, firstMessage.getLineNo());
        assertEquals("expected column", 5, firstMessage.getColumnNo());
    }

    @Test
    public void testCheck() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ViolationAstCheck.class);

        final String[] expected = {
            "1:1: Violation.",
        };
        verify(checkConfig, getPath("InputAbstractCheckTestFileContents.java"), expected);
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

    private static final class VisitCounterCheck extends AbstractCheck {

        private int count;

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

        @Override
        public void visitToken(DetailAST ast) {
            super.visitToken(ast);
            count++;
        }
    }

    private static class ViolationCheck extends AbstractCheck {

        private static final String MSG_KEY = "Violation.";

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

        @Override
        public void visitToken(DetailAST ast) {
            log(1, 5, MSG_KEY);
            log(1, MSG_KEY);
        }

    }

    private static class ViolationAstCheck extends AbstractCheck {

        private static final String MSG_KEY = "Violation.";

        @Override
        public int[] getDefaultTokens() {
            return getRequiredTokens();
        }

        @Override
        public int[] getAcceptableTokens() {
            return getRequiredTokens();
        }

        @Override
        public int[] getRequiredTokens() {
            return new int[] {
                TokenTypes.PACKAGE_DEF,
            };
        }

        @Override
        public void visitToken(DetailAST ast) {
            log(ast, MSG_KEY);
        }

    }

}
