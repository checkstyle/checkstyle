///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_JAVADOC_MISSED_HTML_CLOSE;
import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_JAVADOC_PARSE_RULE_ERROR;
import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_JAVADOC_WRONG_SINGLETON_TAG;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.ParseErrorMessage;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

class DetailNodeTreeStringPrinterTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/detailnodetreestringprinter";
    }

    @Test
    void isProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(DetailNodeTreeStringPrinter.class))
                .isTrue();
    }

    @Test
    void parseFile() throws Exception {
        verifyJavadocTree(getPath("ExpectedDetailNodeTreeStringPrinterJavadocComment.txt"),
                getPath("InputDetailNodeTreeStringPrinterJavadocComment.javadoc"));
    }

    @Test
    void parseFileWithError() throws Exception {
        final File file = new File(
                getPath("InputDetailNodeTreeStringPrinterJavadocWithError.javadoc"));
        try {
            DetailNodeTreeStringPrinter.printFileAst(file);
            assertWithMessage("Javadoc parser didn't fail on missing end tag").fail();
        }
        catch (IllegalArgumentException exc) {
            final String expected = TestUtil.invokeStaticMethod(DetailNodeTreeStringPrinter.class,
                    "getParseErrorMessage",
                    new ParseErrorMessage(0, MSG_JAVADOC_MISSED_HTML_CLOSE, 1, "qwe"));
            assertWithMessage("Generated and expected parse error messages don't match")
                .that(exc.getMessage())
                .isEqualTo(expected);
        }
    }

    @Test
    void noUnnecessaryTextInJavadocAst() throws Exception {
        verifyJavadocTree(
                getPath("ExpectedDetailNodeTreeStringPrinterNoUnnecessaryTextInJavadocAst.txt"),
                getPath("InputDetailNodeTreeStringPrinterNoUnnecessaryTextInJavadocAst.javadoc"));
    }

    @Test
    void missedHtmlTagParseErrorMessage() throws Exception {
        final String actual = TestUtil.invokeStaticMethod(DetailNodeTreeStringPrinter.class,
                "getParseErrorMessage",
                new ParseErrorMessage(35, MSG_JAVADOC_MISSED_HTML_CLOSE, 7, "xyz"));
        final LocalizedMessage violation = new LocalizedMessage(
                "com.puppycrawl.tools.checkstyle.checks.javadoc.messages",
                DetailNodeTreeStringPrinter.class,
                MSG_JAVADOC_MISSED_HTML_CLOSE,
                7,
                "xyz");
        final String expected = "[ERROR:35] " + violation.getMessage();
        assertWithMessage("Javadoc parse error violation for missed HTML tag "
                + "doesn't meet expectations")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    void parseErrorMessage() throws Exception {
        final String actual = TestUtil.invokeStaticMethod(DetailNodeTreeStringPrinter.class,
                "getParseErrorMessage",
                new ParseErrorMessage(10, MSG_JAVADOC_PARSE_RULE_ERROR,
                        9, "no viable alternative at input ' xyz'", "SOME_JAVADOC_ELEMENT"));
        final LocalizedMessage violation = new LocalizedMessage(
                "com.puppycrawl.tools.checkstyle.checks.javadoc.messages",
                DetailNodeTreeStringPrinter.class,
                MSG_JAVADOC_PARSE_RULE_ERROR,
                9,
                "no viable alternative at input ' xyz'", "SOME_JAVADOC_ELEMENT");
        final String expected = "[ERROR:10] " + violation.getMessage();
        assertWithMessage("Javadoc parse error violation doesn't meet expectations")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    void wrongSingletonParseErrorMessage() throws Exception {
        final String actual = TestUtil.invokeStaticMethod(DetailNodeTreeStringPrinter.class,
                "getParseErrorMessage",
                new ParseErrorMessage(100, MSG_JAVADOC_WRONG_SINGLETON_TAG,
                        9, "tag"));
        final LocalizedMessage violation = new LocalizedMessage(
                "com.puppycrawl.tools.checkstyle.checks.javadoc.messages",
                DetailNodeTreeStringPrinter.class,
                MSG_JAVADOC_WRONG_SINGLETON_TAG,
                9,
                "tag");
        final String expected = "[ERROR:100] " + violation.getMessage();
        assertWithMessage("Javadoc parse error violation for void elements with close tag "
                    + "doesn't meet expectations")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    void unescapedJavaCodeWithGenericsInJavadoc() throws Exception {
        final File file = new File(
                getPath("InputDetailNodeTreeStringPrinter"
                        + "UnescapedJavaCodeWithGenericsInJavadoc.javadoc"));
        try {
            DetailNodeTreeStringPrinter.printFileAst(file);
            assertWithMessage("Exception is expected").fail();
        }
        catch (IllegalArgumentException exc) {
            final String expected = TestUtil.invokeStaticMethod(DetailNodeTreeStringPrinter.class,
                    "getParseErrorMessage",
                    new ParseErrorMessage(35, MSG_JAVADOC_MISSED_HTML_CLOSE, 7, "parsing"));
            assertWithMessage("Generated and expected parse error messages don't match")
                .that(exc.getMessage())
                .isEqualTo(expected);
        }
    }

    @Test
    void noViableAltException() throws Exception {
        final File file = new File(
                getPath("InputDetailNodeTreeStringPrinterNoViableAltException.javadoc"));
        try {
            DetailNodeTreeStringPrinter.printFileAst(file);
            assertWithMessage("Exception is expected").fail();
        }
        catch (IllegalArgumentException exc) {
            final String expected = TestUtil.invokeStaticMethod(DetailNodeTreeStringPrinter.class,
                    "getParseErrorMessage",
                    new ParseErrorMessage(0, MSG_JAVADOC_PARSE_RULE_ERROR,
                            9, "no viable alternative at input '<<'", "HTML_ELEMENT"));
            assertWithMessage("Generated and expected parse error messages don't match")
                .that(exc.getMessage())
                .isEqualTo(expected);
        }
    }

    @Test
    void htmlTagCloseBeforeTagOpen() throws Exception {
        final File file = new File(
                getPath("InputDetailNodeTreeStringPrinterHtmlTagCloseBeforeTagOpen.javadoc"));
        try {
            DetailNodeTreeStringPrinter.printFileAst(file);
            assertWithMessage("Exception is expected").fail();
        }
        catch (IllegalArgumentException exc) {
            final String expected = TestUtil.invokeStaticMethod(DetailNodeTreeStringPrinter.class,
                    "getParseErrorMessage",
                    new ParseErrorMessage(0, MSG_JAVADOC_PARSE_RULE_ERROR,
                            4, "no viable alternative at input '</tag'", "HTML_ELEMENT"));
            assertWithMessage("Generated and expected parse error messages don't match")
                .that(exc.getMessage())
                .isEqualTo(expected);
        }
    }

    @Test
    void wrongHtmlTagOrder() throws Exception {
        final File file = new File(
                getPath("InputDetailNodeTreeStringPrinterWrongHtmlTagOrder.javadoc"));
        try {
            DetailNodeTreeStringPrinter.printFileAst(file);
            assertWithMessage("Exception is expected").fail();
        }
        catch (IllegalArgumentException exc) {
            final String expected = TestUtil.invokeStaticMethod(DetailNodeTreeStringPrinter.class,
                    "getParseErrorMessage",
                    new ParseErrorMessage(0, MSG_JAVADOC_MISSED_HTML_CLOSE, 10, "tag2"));
            assertWithMessage("Generated and expected parse error messages don't match")
                .that(exc.getMessage())
                .isEqualTo(expected);
        }
    }

    @Test
    void omittedStartTagForHtmlElement() throws Exception {
        final File file = new File(
                getPath("InputDetailNodeTreeStringPrinterOmittedStartTagForHtmlElement.javadoc"));
        try {
            DetailNodeTreeStringPrinter.printFileAst(file);
            assertWithMessage("Exception is expected").fail();
        }
        catch (IllegalArgumentException exc) {
            final String expected = TestUtil.invokeStaticMethod(DetailNodeTreeStringPrinter.class,
                    "getParseErrorMessage",
                    new ParseErrorMessage(0, MSG_JAVADOC_MISSED_HTML_CLOSE, 3, "a"));
            assertWithMessage("Generated and expected parse error messages don't match")
                .that(exc.getMessage())
                .isEqualTo(expected);
        }
    }

}
