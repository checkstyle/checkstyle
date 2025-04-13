///
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
///

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
        assertWithMessage("Invalid number of tokens, should be empty")
                .that(check.getRequiredTokens())
                .isEmpty();
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
        assertWithMessage("Invalid number of tokens, should be empty")
                .that(check.getAcceptableTokens())
                .isEmpty();
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

        assertWithMessage("unexpected result")
                .that(check.isCommentNodesRequired())
                .isFalse();
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
        assertWithMessage("unexpected result")
                .that(check.getTokenNames())
                .containsExactly("IDENT, EXPR, ELIST");
    }

    @Test
    public void testVisitToken() {
        final VisitCounterCheck check = new VisitCounterCheck();
        // Eventually it will become clear abstract method
        check.visitToken(null);

        assertWithMessage("expected call count")
                .that(check.count)
                .isEqualTo(1);
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

        assertWithMessage("Invalid line content")
                .that(check.getLine(9))
                .isEqualTo(" * I'm a javadoc");
    }

    @Test
    public void testGetLineCodePoints() throws Exception {
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
        final FileContents fileContents = new FileContents(new FileText(
                new File(getPath("InputAbstractCheckTestFileContents.java")),
                Charset.defaultCharset().name()));
        check.setFileContents(fileContents);

        final int[] expectedCodePoints = "    public int getVariable() {".codePoints().toArray();
        assertWithMessage("Invalid line content")
                .that(check.getLineCodePoints(18))
                .isEqualTo(expectedCodePoints);
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

        assertWithMessage("Invalid tab width")
                .that(check.getTabWidth())
                .isEqualTo(tabWidth);
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

        assertWithMessage("Invalid file contents")
                .that(check.getFileContents())
                .isEqualTo(fileContents);
        assertWithMessage("Invalid lines")
                .that(check.getLines())
                .isEqualTo(lines);
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

        assertWithMessage("Invalid default tokens")
                .that(check.getDefaultTokens())
                .isEqualTo(defaultTokens);
        assertWithMessage("Invalid acceptable tokens")
                .that(check.getAcceptableTokens())
                .isEqualTo(acceptableTokens);
        assertWithMessage("Invalid required tokens")
                .that(check.getRequiredTokens())
                .isEqualTo(requiredTokens);
    }

    @Test
    public void testClearViolations() {
        final AbstractCheck check = new DummyAbstractCheck();

        check.log(1, "key", "args");
        assertWithMessage("Invalid violation size")
                .that(check.getViolations())
                .hasSize(1);
        check.clearViolations();
        assertWithMessage("Invalid violation size")
                .that(check.getViolations())
                .isEmpty();
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

        assertWithMessage("Internal violation should only have 2")
                .that(internalViolations)
                .hasSize(2);

        final Iterator<Violation> iterator = internalViolations.iterator();

        final Violation firstViolation = iterator.next();
        assertWithMessage("expected line")
                .that(firstViolation.getLineNo())
                .isEqualTo(1);
        assertWithMessage("expected column")
                .that(firstViolation.getColumnNo())
                .isEqualTo(0);

        final Violation secondViolation = iterator.next();
        assertWithMessage("expected line")
                .that(secondViolation.getLineNo())
                .isEqualTo(1);
        assertWithMessage("expected column")
                .that(secondViolation.getColumnNo())
                .isEqualTo(6);
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

        assertWithMessage("Internal violation should only have 1")
                .that(internalViolations)
                .hasSize(1);

        final Violation firstViolation = internalViolations.iterator().next();
        assertWithMessage("expected line")
                .that(firstViolation.getLineNo())
                .isEqualTo(1);
        assertWithMessage("expected column")
                .that(firstViolation.getColumnNo())
                .isEqualTo(5);
    }

    @Test
    public void testCheck() throws Exception {
        final String[] expected = {
            "6:1: Violation.",
        };
        verifyWithInlineConfigParser(getPath("InputAbstractCheckTestFileContents.java"), expected);
    }

    /**
     * S2384 - Mutable members should not be stored or returned directly.
     * Sonarqube rule is valid, a pure unit test is required as this condition can't be recreated in
     * a test with checks and input file as none of the checks try to modify the tokens.
     */
    @Test
    public void testTokensAreUnmodifiable() {
        final DummyAbstractCheck check = new DummyAbstractCheck();
        final Set<String> tokenNameSet = check.getTokenNames();
        final Exception ex = getExpectedThrowable(UnsupportedOperationException.class,
                () -> tokenNameSet.add(""));
        assertWithMessage("Exception class is not expected")
                .that(ex.getClass())
                .isEqualTo(UnsupportedOperationException.class);
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
