////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.Test;

import antlr.NoViableAltException;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class SuppressionsStringPrinterTest extends AbstractTreeTestSupport {

    private static final String EOL = System.getProperty("line.separator");

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/suppressionsstringprinter";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(
                isUtilsClassHasPrivateConstructor(SuppressionsStringPrinter.class, true),
                "Constructor is not private");
    }

    @Test
    public void testCorrect() throws Exception {
        final String expected = "/CLASS_DEF[./IDENT[@text='InputSuppressionsStringPrinter']]" + EOL
                + "/CLASS_DEF[./IDENT[@text='InputSuppressionsStringPrinter']]/MODIFIERS" + EOL
                + "/CLASS_DEF[./IDENT[@text='InputSuppressionsStringPrinter']]/MODIFIERS"
                + "/LITERAL_PUBLIC" + EOL;

        final File input = new File(getPath("InputSuppressionsStringPrinter.java"));
        final String lineAndColumnNumber = "3:1";
        final int tabWidth = 2;
        final String result = SuppressionsStringPrinter.printSuppressions(input,
                lineAndColumnNumber, tabWidth);

        assertEquals(expected, result, "Invalid xpath queries");
    }

    @Test
    public void testCustomTabWidth() throws Exception {
        final String expected = "/CLASS_DEF[./IDENT[@text='InputSuppressionsStringPrinter']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='toString']]" + EOL
                + "/CLASS_DEF[./IDENT[@text='InputSuppressionsStringPrinter']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='toString']]/MODIFIERS" + EOL
                + "/CLASS_DEF[./IDENT[@text='InputSuppressionsStringPrinter']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='toString']]/MODIFIERS/LITERAL_PUBLIC" + EOL;

        final File input = new File(getPath("InputSuppressionsStringPrinter.java"));
        final String lineAndColumnNumber = "5:13";
        final int tabWidth = 4;
        final String result = SuppressionsStringPrinter.printSuppressions(input,
                lineAndColumnNumber, tabWidth);

        assertEquals(expected, result, "Invalid xpath queries");
    }

    @Test
    public void testCustomTabWidthEmptyResult() throws Exception {
        final File input = new File(getPath("InputSuppressionsStringPrinter.java"));
        final String lineAndColumnNumber = "5:13";
        final int tabWidth = 6;
        final String result = SuppressionsStringPrinter.printSuppressions(input,
                lineAndColumnNumber, tabWidth);
        assertEquals(EOL, result, "Invalid xpath queries");
    }

    @Test
    public void testInvalidLineAndColumnNumberParameter() throws Exception {
        final File input = new File(getPath("InputSuppressionsStringPrinter.java"));
        final String invalidLineAndColumnNumber = "abc-432";
        final int tabWidth = 2;
        try {
            SuppressionsStringPrinter.printSuppressions(input,
                    invalidLineAndColumnNumber, tabWidth);
            fail("exception expected");
        }
        catch (IllegalStateException ex) {
            assertEquals("abc-432 does not match valid format 'line:column'.",
                    ex.getMessage(), "Invalid exception message");
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
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertSame(NoViableAltException.class, ex.getCause().getClass(), "Invalid class");
            assertEquals(input.getAbsolutePath() + ":2:1: unexpected token: classD",
                    ex.getCause().toString(), "Invalid exception message");
        }
    }

}
