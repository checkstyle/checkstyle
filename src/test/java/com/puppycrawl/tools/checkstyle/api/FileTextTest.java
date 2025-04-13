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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class FileTextTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/api/filetext";
    }

    @Test
    public void testUnsupportedCharset() throws IOException {
        // just to make UT coverage 100%
        final String charsetName = "STRANGE_CHARSET";
        final File file = new File("any name");
        try {
            final Object test = new FileText(file, charsetName);
            assertWithMessage("UnsupportedEncodingException is expected but got %s", test)
                    .fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Invalid exception message")
                    .that(ex)
                    .hasMessageThat()
                    .isEqualTo("Unsupported charset: " + charsetName);
        }
    }

    @Test
    public void testFileNotFound() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final File file = new File("any name");
        try {
            final Object test = new FileText(file, charsetName);
            assertWithMessage("FileNotFoundException is expected but got " + test)
                    .fail();
        }
        catch (FileNotFoundException ex) {
            assertWithMessage("Invalid exception message")
                    .that(ex)
                    .hasMessageThat()
                    .isEqualTo("any name (No such file or directory)");
        }
    }

    @Test
    public void testSupportedCharset() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                charsetName);
        assertWithMessage("Invalid charset name")
                .that(fileText.getCharset().name())
                .isEqualTo(charsetName);
    }

    @Test
    public void testLineColumnBeforeCopyConstructor() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                charsetName);
        final LineColumn lineColumn = fileText.lineColumn(100);
        final FileText copy = new FileText(fileText);
        assertWithMessage("LineBreaks not copied")
                .that(TestUtil.<int[]>getInternalState(copy, "lineBreaks"))
                .isNotNull();
        final LineColumn actual = copy.lineColumn(100);
        assertWithMessage("Invalid linecolumn")
                .that(actual)
                .isEqualTo(lineColumn);
    }

    @Test
    public void testLineColumnAfterCopyConstructor() throws IOException {
        final Charset charset = StandardCharsets.ISO_8859_1;
        final String filepath = getPath("InputFileTextImportControl.xml");
        final FileText fileText = new FileText(new File(filepath), charset.name());
        final FileText copy = new FileText(fileText);
        assertWithMessage("LineBreaks not null")
                .that(TestUtil.<int[]>getInternalState(copy, "lineBreaks"))
                .isNull();
        final LineColumn lineColumn = copy.lineColumn(100);
        assertWithMessage("Invalid line")
                .that(lineColumn.getLine())
                .isEqualTo(3);
        if (CheckUtil.CRLF.equals(CheckUtil.getLineSeparatorForFile(filepath, charset))) {
            assertWithMessage("Invalid column")
                    .that(lineColumn.getColumn())
                    .isEqualTo(44);
        }
        else {
            assertWithMessage("Invalid column")
                    .that(lineColumn.getColumn())
                    .isEqualTo(46);
        }
    }

    @Test
    public void testLineColumnAtTheStartOfFile() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                charsetName);
        final FileText copy = new FileText(fileText);
        final LineColumn lineColumn = copy.lineColumn(0);
        assertWithMessage("Invalid line")
                .that(lineColumn.getLine())
                .isEqualTo(1);
        assertWithMessage("Invalid column")
                .that(lineColumn.getColumn())
                .isEqualTo(0);
    }

    @Test
    public void testLines() throws IOException {
        final List<String> lines = Collections.singletonList("abc");
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                lines);
        assertWithMessage("Invalid line")
                .that(fileText.toLinesArray())
                .isEqualTo(new String[] {"abc"});
    }

    @Test
    public void testFindLineBreaks() throws Exception {
        final FileText fileText = new FileText(new File("fileName"), Arrays.asList("1", "2"));

        assertWithMessage("Invalid line breaks")
                .that(TestUtil.<int[]>invokeMethod(fileText, "findLineBreaks"))
                .isEqualTo(new int[] {0, 2, 4});

        final FileText fileText2 = new FileText(new File("fileName"), Arrays.asList("1", "2"));
        TestUtil.setInternalState(fileText2, "fullText", "1\n2");

        assertWithMessage("Invalid line breaks")
                .that(TestUtil.<int[]>invokeMethod(fileText2, "findLineBreaks"))
                .isEqualTo(new int[] {0, 2, 3});
    }

    /**
     * Reflection is the only way to test that a field is cached since we can't
     * access the field directly or receive notice when the field is
     * initialized.
     *
     * @throws Exception if there is an error.
     */
    @Test
    public void testFindLineBreaksCache() throws Exception {
        final FileText fileText = new FileText(new File("fileName"), Collections.emptyList());
        final int[] lineBreaks = {5};
        TestUtil.setInternalState(fileText, "lineBreaks", lineBreaks);
        // produces NPE if used
        TestUtil.setInternalState(fileText, "fullText", null);

        assertWithMessage("Invalid line breaks")
                .that(TestUtil.<int[]>invokeMethod(fileText, "findLineBreaks"))
                .isEqualTo(lineBreaks);
    }

    @Test
    public void testCharsetAfterCopyConstructor() throws IOException {
        final Charset charset = StandardCharsets.ISO_8859_1;
        final String filepath = getPath("InputFileTextImportControl.xml");
        final FileText fileText = new FileText(new File(filepath), charset.name());
        final FileText copy = new FileText(fileText);
        assertWithMessage("Should not be null")
                .that(copy.getCharset()).isNotNull();
    }
}
