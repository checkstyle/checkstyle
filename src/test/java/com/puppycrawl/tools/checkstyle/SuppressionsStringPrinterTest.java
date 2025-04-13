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

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class SuppressionsStringPrinterTest extends AbstractTreeTestSupport {

    private static final String EOL = System.getProperty("line.separator");

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/suppressionsstringprinter";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(SuppressionsStringPrinter.class))
                .isTrue();
    }

    @Test
    public void testCorrect() throws Exception {
        final String expected = "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputSuppressionsStringPrinter']]" + EOL
                + "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputSuppressionsStringPrinter']]"
                + "/MODIFIERS" + EOL
                + "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputSuppressionsStringPrinter']]"
                + "/MODIFIERS/LITERAL_PUBLIC" + EOL;

        final File input = new File(getPath("InputSuppressionsStringPrinter.java"));
        final String lineAndColumnNumber = "3:1";
        final int tabWidth = 2;
        final String result = SuppressionsStringPrinter.printSuppressions(input,
                lineAndColumnNumber, tabWidth);

        assertWithMessage("Invalid xpath queries")
            .that(result)
            .isEqualTo(expected);
    }

    @Test
    public void testCustomTabWidth() throws Exception {
        final String expected = "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputSuppressionsStringPrinter']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='toString']]" + EOL
                + "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputSuppressionsStringPrinter']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='toString']]/MODIFIERS" + EOL
                + "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputSuppressionsStringPrinter']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='toString']]/MODIFIERS/LITERAL_PUBLIC" + EOL;

        final File input = new File(getPath("InputSuppressionsStringPrinter.java"));
        final String lineAndColumnNumber = "5:13";
        final int tabWidth = 4;
        final String result = SuppressionsStringPrinter.printSuppressions(input,
                lineAndColumnNumber, tabWidth);

        assertWithMessage("Invalid xpath queries")
            .that(result)
            .isEqualTo(expected);
    }

    @Test
    public void testCustomTabWidthEmptyResult() throws Exception {
        final File input = new File(getPath("InputSuppressionsStringPrinter.java"));
        final String lineAndColumnNumber = "5:13";
        final int tabWidth = 6;
        final String result = SuppressionsStringPrinter.printSuppressions(input,
                lineAndColumnNumber, tabWidth);
        assertWithMessage("Invalid xpath queries")
            .that(result)
            .isEqualTo(EOL);
    }

    @Test
    public void testInvalidLineAndColumnNumberParameter() throws Exception {
        final File input = new File(getPath("InputSuppressionsStringPrinter.java"));
        final String invalidLineAndColumnNumber = "abc-432";
        final int tabWidth = 2;
        try {
            SuppressionsStringPrinter.printSuppressions(input,
                    invalidLineAndColumnNumber, tabWidth);
            assertWithMessage("exception expected").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("abc-432 does not match valid format 'line:column'.");
        }
    }

    @Test
    public void testParseFileTextThrowable() throws Exception {
        final File input = new File(getNonCompilablePath("InputSuppressionsStringPrinter.java"));
        final String lineAndColumnNumber = "2:3";
        final int tabWidth = 2;
        try {
            SuppressionsStringPrinter.printSuppressions(input,
                    lineAndColumnNumber, tabWidth);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid class")
                .that(ex.getCause())
                .isInstanceOf(IllegalStateException.class);
            assertWithMessage("Invalid exception message")
                .that(ex.getCause().toString())
                .isEqualTo(IllegalStateException.class.getName()
                            + ": 2:0: no viable alternative at input 'classD'");
        }
    }

}
