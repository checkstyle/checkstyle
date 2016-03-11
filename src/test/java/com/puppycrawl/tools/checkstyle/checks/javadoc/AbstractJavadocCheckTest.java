////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.MSG_JAVADOC_MISSED_HTML_CLOSE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.MSG_JAVADOC_WRONG_SINGLETON_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.MSG_KEY_PARSE_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.MSG_KEY_UNRECOGNIZED_ANTLR_ERROR;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.TestUtils;
import com.puppycrawl.tools.checkstyle.utils.BlockCommentPosition;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class AbstractJavadocCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "javadoc" + File.separator + filename);
    }

    @Test
    public void testNumberFormatException() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TempCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY_PARSE_ERROR, 52, "no viable "
                + "alternative at input '<ul><li>a' {@link EntityEntry} (by way of {@link #;' "
                + "while parsing HTML_TAG"),
        };
        verify(checkConfig, getPath("InputTestNumberFormatException.java"), expected);
    }

    @Test
    public void testCustomTag() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TempCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputCustomTag.java"), expected);
    }

    @Test
    public void testParsingErrors() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TempCheck.class);
        final String[] expected = {
            "4: " + getCheckMessage(MSG_JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "8: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 35, "img"),
        };
        verify(checkConfig, getPath("InputParsingErrors.java"), expected);
    }

    @Test
    public void testWithMultipleChecks() throws Exception {
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        final DefaultConfiguration checksConfig = createCheckConfig(TreeWalker.class);
        checksConfig.addChild(createCheckConfig(AtclauseOrderCheck.class));
        checksConfig.addChild(createCheckConfig(JavadocParagraphCheck.class));
        checkerConfig.addChild(checksConfig);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);

        verify(checker, getPath("InputCorrectJavaDocParagraph.java"));
    }

    @Test
    public void testAntlrError() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TempCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY_UNRECOGNIZED_ANTLR_ERROR, 0, null),
        };
        verify(checkConfig, getPath("InputTestInvalidAtSeeReference.java"), expected);
    }

    @Test
    public void testCheckReuseAfterParseErrorWithFollowingAntlrErrorInTwoFiles() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TempCheck.class);
        final Map<String, List<String>> expectedMessages = new LinkedHashMap<>(2);
        expectedMessages.put(getPath("InputParsingErrors.java"), asList(
            "4: " + getCheckMessage(MSG_JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "8: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 35, "img")
        ));
        expectedMessages.put(getPath("InputTestInvalidAtSeeReference.java"), singletonList(
            "3: " + getCheckMessage(MSG_KEY_UNRECOGNIZED_ANTLR_ERROR, 0, null)
        ));
        verify(createChecker(checkConfig), new File[] {
            new File(getPath("InputParsingErrors.java")),
            new File(getPath("InputTestInvalidAtSeeReference.java")), }, expectedMessages);
    }

    @Test
    public void testCheckReuseAfterParseErrorWithFollowingAntlrErrorInSingleFile()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TempCheck.class);
        final String[] expected = {
            "4: " + getCheckMessage(MSG_JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "7: " + getCheckMessage(MSG_KEY_UNRECOGNIZED_ANTLR_ERROR, 4, null),
        };
        verify(checkConfig, getPath("InputTestUnclosedTagAndInvalidAtSeeReference.java"), expected);
    }

    @Test
    public void testPosition()
        throws Exception {
        JavadocCatchCheck.clearCounter();
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocCatchCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocPosition.java"), expected);
        Assert.assertEquals(58, JavadocCatchCheck.javadocsNumber);
    }

    @Test
    public void testPositionWithSinglelineComments()
        throws Exception {
        JavadocCatchCheck.clearCounter();
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocCatchCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocPositionWithSinglelineComments.java"), expected);
        Assert.assertEquals(58, JavadocCatchCheck.javadocsNumber);
    }

    @Test
    public void testPositionOnlyComments()
        throws Exception {
        JavadocCatchCheck.clearCounter();
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocCatchCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocPositionOnlyComments.java"), expected);
        Assert.assertEquals(0, JavadocCatchCheck.javadocsNumber);
    }

    @Test
    public void testBlockCommentPositionHasPrivateConstr() throws Exception {
        TestUtils.assertUtilsClassHasPrivateConstructor(BlockCommentPosition.class);
    }

    private static class TempCheck extends AbstractJavadocCheck {

        @Override
        public int[] getDefaultJavadocTokens() {
            return CommonUtils.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getAcceptableTokens() {
            return new int[] {TokenTypes.BLOCK_COMMENT_BEGIN };
        }

        @Override
        public int[] getRequiredTokens() {
            return new int[] {TokenTypes.BLOCK_COMMENT_BEGIN };
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            // do nothing
        }
    }

    private static class JavadocCatchCheck extends AbstractJavadocCheck {
        private static int javadocsNumber;

        static void clearCounter() {
            javadocsNumber = 0;
        }

        @Override
        public int[] getDefaultJavadocTokens() {
            return new int[]{JavadocTokenTypes.JAVADOC};
        }

        @Override
        public int[] getAcceptableTokens() {
            return new int[] {TokenTypes.BLOCK_COMMENT_BEGIN };
        }

        @Override
        public int[] getRequiredTokens() {
            return new int[] {TokenTypes.BLOCK_COMMENT_BEGIN };
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            Assert.assertEquals(ast.toString(), "Javadoc<EOF>", ast.getText());
            javadocsNumber++;
        }
    }
}
