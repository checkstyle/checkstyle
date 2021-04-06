////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;

import org.junit.jupiter.api.Test;

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
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, check.getRequiredTokens(),
                "Invalid number of tokens, should be empty");
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
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, check.getAcceptableTokens(),
                "Invalid number of tokens, should be empty");
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

        assertFalse(check.isCommentNodesRequired(), "unexpected result");
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
        assertArrayEquals(new String[] {"IDENT, EXPR, ELIST"},
            check.getTokenNames().toArray(), "unexpected result");
    }

    @Test
    public void testVisitToken() {
        final VisitCounterCheck check = new VisitCounterCheck();
        // Eventually it will become clear abstract method
        check.visitToken(null);

        assertEquals(1, check.count, "expected call count");
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

        assertEquals(" * I'm a javadoc", check.getLine(3), "Invalid line content");
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

        assertEquals(tabWidth, check.getTabWidth(), "Invalid tab width");
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

        assertSame(fileContents, check.getFileContents(), "Invalid file contents");
        assertArrayEquals(lines, check.getLines(), "Invalid lines");
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

        assertArrayEquals(defaultTokens, check.getDefaultTokens(), "Invalid default tokens");
        assertArrayEquals(defaultTokens, check.getAcceptableTokens(), "Invalid acceptable tokens");
        assertArrayEquals(requiredTokens, check.getRequiredTokens(), "Invalid required tokens");
    }

    @Test
    public void testClearViolations() {
        final AbstractCheck check = new DummyAbstractCheck();

        check.log(1, "key", "args");
        assertEquals(1, check.getViolations().size(), "Invalid violation size");
        check.clearViolations();
        assertEquals(0, check.getViolations().size(), "Invalid violation size");
    }

    @Test
    public void testLineColumnLog() throws Exception {
        final ViolationCheck check = new ViolationCheck();
        check.configure(new DefaultConfiguration("check"));
        final File file = new File("fileName");
        final FileText theText = new FileText(file, Collections.singletonList("test123"));

        check.setFileContents(new FileContents(theText));
        check.clearViolations();
        check.visitToken(null);

        final SortedSet<Violation> internalViolations = check.getViolations();

        assertEquals(2, internalViolations.size(), "Internal violation should only have 2");

        final Iterator<Violation> iterator = internalViolations.iterator();

        final Violation firstViolation = iterator.next();
        assertEquals(1, firstViolation.getLineNo(), "expected line");
        assertEquals(0, firstViolation.getColumnNo(), "expected column");

        final Violation secondViolation = iterator.next();
        assertEquals(1, secondViolation.getLineNo(), "expected line");
        assertEquals(6, secondViolation.getColumnNo(), "expected column");
    }

    @Test
    public void testAstLog() throws Exception {
        final ViolationAstCheck check = new ViolationAstCheck();
        check.configure(new DefaultConfiguration("check"));
        final File file = new File("fileName");
        final FileText theText = new FileText(file, Collections.singletonList("test123"));

        check.setFileContents(new FileContents(theText));
        check.clearViolations();

        final DetailAstImpl ast = new DetailAstImpl();
        ast.setLineNo(1);
        ast.setColumnNo(4);
        check.visitToken(ast);

        final SortedSet<Violation> internalViolations = check.getViolations();

        assertEquals(1, internalViolations.size(), "Internal violation should only have 1");

        final Violation firstViolation = internalViolations.iterator().next();
        assertEquals(1, firstViolation.getLineNo(), "expected line");
        assertEquals(5, firstViolation.getColumnNo(), "expected column");
    }

    @Test
    public void testCheck() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ViolationAstCheck.class);

        final String[] expected = {
            "1:1: Violation.",
        };
        verify(checkConfig, getPath("InputAbstractCheckTestFileContents.java"), expected);
    }

    public static final class DummyAbstractCheck extends AbstractCheck {

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

    public static final class VisitCounterCheck extends AbstractCheck {

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

    public static class ViolationCheck extends AbstractCheck {

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

    public static class ViolationAstCheck extends AbstractCheck {

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
