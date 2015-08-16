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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.JAVADOC_MISSED_HTML_CLOSE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.JAVADOC_WRONG_SINGLETON_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.PARSE_ERROR_MESSAGE_KEY;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.UNRECOGNIZED_ANTLR_ERROR_MESSAGE_KEY;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AbstractJavadocCheckTest extends BaseCheckTestSupport {

    @Test
    public void testNumberFormatException() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TempCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(PARSE_ERROR_MESSAGE_KEY, 52, "no viable "
                + "alternative at input '<ul><li>a' {@link EntityEntry} (by way of {@link #;' "
                + "while parsing HTML_TAG"),
        };
        verify(checkConfig, getPath("javadoc/InputTestNumberFomatException.java"), expected);
    }

    @Test
    public void testCustomTag() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TempCheck.class);
        final String[] expected = {
            "4: " + getCheckMessage(UNRECOGNIZED_ANTLR_ERROR_MESSAGE_KEY, 4, "null"),
        };
        verify(checkConfig, getPath("javadoc/InputCustomTag.java"), expected);
    }

    @Test
    public void testParsingErrors() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TempCheck.class);
        final String[] expected = {
            "4: " + getCheckMessage(JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "8: " + getCheckMessage(JAVADOC_WRONG_SINGLETON_TAG, 35, "img"),
        };
        verify(checkConfig, getPath("javadoc/InputParsingErrors.java"), expected);
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

        final String[] expected = {
        };
        verify(checker, getPath("javadoc/InputCorrectJavaDocParagraphCheck.java"), expected);
    }

    public static class TempCheck extends AbstractJavadocCheck {

        @Override
        public int[] getDefaultJavadocTokens() {
            return null;
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
}
