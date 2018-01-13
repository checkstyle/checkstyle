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

package com.puppycrawl.tools.checkstyle;

import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_JAVADOC_MISSED_HTML_CLOSE;
import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_JAVADOC_PARSE_RULE_ERROR;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.ParseErrorMessage;

public class DetailNodeTreeStringPrinterTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/detailnodetreestringprinter";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue("Constructor is not private",
                isUtilsClassHasPrivateConstructor(DetailNodeTreeStringPrinter.class, true));
    }

    @Test
    public void testParseFile() throws Exception {
        verifyJavadocTree(getPath("ExpectedDetailNodeTreeStringPrinterJavadocComment.txt"),
                getPath("InputDetailNodeTreeStringPrinterJavadocComment.javadoc"));
    }

    @Test
    public void testParseFileWithError() throws Exception {
        try {
            DetailNodeTreeStringPrinter.printFileAst(
                    new File(getPath("InputDetailNodeTreeStringPrinterJavadocWithError.javadoc")));
            Assert.fail("Javadoc parser didn't fail on missing end tag");
        }
        catch (IllegalArgumentException ex) {
            final String expected =
                    new ParseErrorMessage(0, MSG_JAVADOC_MISSED_HTML_CLOSE, 1, "qwe").toString();
            assertEquals("Generated and expected parse error messages don't match",
                    expected, ex.getMessage());
        }
    }

    @Test
    public void testNoUnnecessaryTextInJavadocAst() throws Exception {
        verifyJavadocTree(
                getPath("ExpectedDetailNodeTreeStringPrinterNoUnnecessaryTextInJavadocAst.txt"),
                getPath("InputDetailNodeTreeStringPrinterNoUnnecessaryTextInJavadocAst.javadoc"));
    }

    @Test
    public void testUnescapedJavaCodeWithGenericsInJavadoc() throws Exception {
        try {
            DetailNodeTreeStringPrinter.printFileAst(new File(
                    getPath("InputDetailNodeTreeStringPrinter"
                            + "UnescapedJavaCodeWithGenericsInJavadoc.javadoc")));
            Assert.fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            final String expected = new ParseErrorMessage(35, MSG_JAVADOC_MISSED_HTML_CLOSE, 7,
                    "parsing").toString();
            assertEquals("Generated and expected parse error messages don't match",
                    expected, ex.getMessage());
        }
    }

    @Test
    public void testNoViableAltException() throws Exception {
        try {
            DetailNodeTreeStringPrinter.printFileAst(new File(getPath(
                    "InputDetailNodeTreeStringPrinterNoViableAltException.javadoc")));
            Assert.fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            final String expected =
                    new ParseErrorMessage(0, MSG_JAVADOC_PARSE_RULE_ERROR,
                            9, "no viable alternative at input '<<'", "HTML_ELEMENT").toString();
            assertEquals("Generated and expected parse error messages don't match",
                    expected, ex.getMessage());
        }
    }

    @Test
    public void testHtmlTagCloseBeforeTagOpen() throws Exception {
        try {
            DetailNodeTreeStringPrinter.printFileAst(new File(getPath(
                    "InputDetailNodeTreeStringPrinterHtmlTagCloseBeforeTagOpen.javadoc"
            )));
            Assert.fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            final String expected =
                    new ParseErrorMessage(0, MSG_JAVADOC_PARSE_RULE_ERROR,
                            4, "no viable alternative at input '</tag'", "HTML_ELEMENT").toString();
            assertEquals("Generated and expected parse error messages don't match",
                    expected, ex.getMessage());
        }
    }

    @Test
    public void testWrongHtmlTagOrder() throws Exception {
        try {
            DetailNodeTreeStringPrinter.printFileAst(new File(getPath(
                    "InputDetailNodeTreeStringPrinterWrongHtmlTagOrder.javadoc"
            )));
            Assert.fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            final String expected =
                    new ParseErrorMessage(0, MSG_JAVADOC_MISSED_HTML_CLOSE, 10, "tag2").toString();
            assertEquals("Generated and expected parse error messages don't match",
                    expected, ex.getMessage());
        }
    }

    @Test
    public void testOmittedStartTagForHtmlElement() throws Exception {
        try {
            DetailNodeTreeStringPrinter.printFileAst(new File(getPath(
                    "InputDetailNodeTreeStringPrinterOmittedStartTagForHtmlElement.javadoc"
            )));
            Assert.fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            final String expected =
                    new ParseErrorMessage(0, MSG_JAVADOC_MISSED_HTML_CLOSE, 3, "a").toString();
            assertEquals("Generated and expected parse error messages don't match",
                    expected, ex.getMessage());
        }
    }

}
