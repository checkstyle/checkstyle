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

package com.puppycrawl.tools.checkstyle.api;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public class FileTextTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/api/filetext";
    }

    @Test
    public void testUnsupportedCharset() throws IOException {
        // just to make UT coverage 100%
        final String charsetName = "STRANGE_CHARSET";
        try {
            final Object test = new FileText(new File("any name"), charsetName);
            fail("UnsupportedEncodingException is expected but got " + test);
        }
        catch (IllegalStateException ex) {
            assertEquals("Unsupported charset: " + charsetName, ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testFileNotFound() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        try {
            final Object test = new FileText(new File("any name"), charsetName);
            fail("FileNotFoundException is expected but got " + test);
        }
        catch (FileNotFoundException ex) {
            assertEquals("any name (No such file or directory)", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testSupportedCharset() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                charsetName);
        assertEquals(charsetName, fileText.getCharset().name(), "Invalid charset name");
    }

    @Test
    public void testLineColumnBeforeCopyConstructor() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                charsetName);
        final LineColumn lineColumn = fileText.lineColumn(100);
        final FileText copy = new FileText(fileText);
        assertNotNull(Whitebox.getInternalState(copy, "lineBreaks"), "LineBreaks not copied");
        final LineColumn actual = copy.lineColumn(100);
        assertEquals(lineColumn, actual, "Invalid linecolumn");
    }

    @Test
    public void testLineColumnAfterCopyConstructor() throws IOException {
        final Charset charset = StandardCharsets.ISO_8859_1;
        final String filepath = getPath("InputFileTextImportControl.xml");
        final FileText fileText = new FileText(new File(filepath), charset.name());
        final FileText copy = new FileText(fileText);
        assertNull(Whitebox.getInternalState(copy, "lineBreaks"), "LineBreaks not null");
        final LineColumn lineColumn = copy.lineColumn(100);
        assertEquals(3, lineColumn.getLine(), "Invalid line");
        if (CheckUtil.CRLF.equals(CheckUtil.getLineSeparatorForFile(filepath, charset))) {
            assertEquals(44, lineColumn.getColumn(), "Invalid column");
        }
        else {
            assertEquals(46, lineColumn.getColumn(), "Invalid column");
        }
    }

    @Test
    public void testLineColumnAtTheStartOfFile() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                charsetName);
        final FileText copy = new FileText(fileText);
        final LineColumn lineColumn = copy.lineColumn(0);
        assertEquals(1, lineColumn.getLine(), "Invalid line");
        assertEquals(0, lineColumn.getColumn(), "Invalid column");
    }

    @Test
    public void testLines() throws IOException {
        final List<String> lines = Collections.singletonList("abc");
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                lines);
        assertArrayEquals(new String[] {"abc"}, fileText.toLinesArray(), "Invalid line");
    }

    @Test
    public void testFindLineBreaks() throws Exception {
        final FileText fileText = new FileText(new File("fileName"), Arrays.asList("1", "2"));

        assertArrayEquals(new int[] {0, 2, 4},
                Whitebox.invokeMethod(fileText, "findLineBreaks"), "Invalid line breaks");

        final FileText fileText2 = new FileText(new File("fileName"), Arrays.asList("1", "2"));
        Whitebox.setInternalState(fileText2, "fullText", "1\n2");

        assertArrayEquals(new int[] {0, 2, 3},
                Whitebox.invokeMethod(fileText2, "findLineBreaks"), "Invalid line breaks");
    }

    /**
     * Reflection is the only way to test that a field is cached since we can't
     * access the field directly or receive notice when the field is
     * initialized.
     * @throws Exception if there is an error.
     */
    @Test
    public void testFindLineBreaksCache() throws Exception {
        final FileText fileText = new FileText(new File("fileName"), Collections.emptyList());
        final int[] lineBreaks = {5};
        Whitebox.setInternalState(fileText, "lineBreaks", lineBreaks);
        // produces NPE if used
        Whitebox.setInternalState(fileText, "fullText", (Object) null);

        assertArrayEquals(lineBreaks,
                Whitebox.invokeMethod(fileText, "findLineBreaks"), "Invalid line breaks");
    }

}
