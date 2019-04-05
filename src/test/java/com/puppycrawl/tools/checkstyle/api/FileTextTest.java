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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;

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
            assertEquals("Invalid exception message",
                    "Unsupported charset: " + charsetName, ex.getMessage());
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
            assertEquals("Invalid exception message",
                    "any name (No such file or directory)", ex.getMessage());
        }
    }

    @Test
    public void testSupportedCharset() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                charsetName);
        assertEquals("Invalid charset name", charsetName, fileText.getCharset().name());
    }

    @Test
    public void testLineColumnBeforeCopyConstructor() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                charsetName);
        final LineColumn lineColumn = fileText.lineColumn(100);
        final FileText copy = new FileText(fileText);
        assertNotNull("LineBreaks not copied", Whitebox.getInternalState(copy, "lineBreaks"));
        assertEquals("Invalid linecolumn", lineColumn, copy.lineColumn(100));
    }

    @Test
    public void testLineColumnAfterCopyConstructor() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                charsetName);
        final FileText copy = new FileText(fileText);
        assertNull("LineBreaks not null", Whitebox.getInternalState(copy, "lineBreaks"));
        final LineColumn lineColumn = copy.lineColumn(100);
        assertEquals("Invalid line", 3, lineColumn.getLine());
        if (System.getProperty("os.name").toLowerCase(Locale.ENGLISH).startsWith("windows")) {
            assertEquals("Invalid column", 44, lineColumn.getColumn());
        }
        else {
            assertEquals("Invalid column", 46, lineColumn.getColumn());
        }
    }

    @Test
    public void testLineColumnAtTheStartOfFile() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                charsetName);
        final FileText copy = new FileText(fileText);
        final LineColumn lineColumn = copy.lineColumn(0);
        assertEquals("Invalid line", 1, lineColumn.getLine());
        assertEquals("Invalid column", 0, lineColumn.getColumn());
    }

    @Test
    public void testLines() throws IOException {
        final List<String> lines = Collections.singletonList("abc");
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                lines);
        assertArrayEquals("Invalid line", new String[] {"abc"}, fileText.toLinesArray());
    }

    @Test
    public void testFindLineBreaks() throws Exception {
        final FileText fileText = new FileText(new File("fileName"), Arrays.asList("1", "2"));

        assertArrayEquals("Invalid line breaks", new int[] {0, 2, 4},
                Whitebox.invokeMethod(fileText, "findLineBreaks"));

        final FileText fileText2 = new FileText(new File("fileName"), Arrays.asList("1", "2"));
        Whitebox.setInternalState(fileText2, "fullText", "1\n2");

        assertArrayEquals("Invalid line breaks", new int[] {0, 2, 3},
                Whitebox.invokeMethod(fileText2, "findLineBreaks"));
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

        assertArrayEquals("Invalid line breaks", lineBreaks,
                Whitebox.invokeMethod(fileText, "findLineBreaks"));
    }

}
