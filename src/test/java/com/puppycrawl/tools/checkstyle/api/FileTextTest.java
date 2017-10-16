////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Closeables.class)
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
    public void testSupportedCharset() throws IOException {
        //check if reader finally closed
        mockStatic(Closeables.class);
        doNothing().when(Closeables.class);
        Closeables.closeQuietly(any(Reader.class));

        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                charsetName);
        assertEquals("Invalid charset name", charsetName, fileText.getCharset().name());

        verifyStatic(times(2));
        Closeables.closeQuietly(any(Reader.class));
    }

    @Test
    public void testLineColumnBeforeCopyConstructor() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                charsetName);
        final LineColumn lineColumn = fileText.lineColumn(100);
        final FileText copy = new FileText(fileText);
        assertEquals("Invalid linecolumn", lineColumn, copy.lineColumn(100));
    }

    @Test
    public void testLineColumnAfterCopyConstructor() throws IOException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();
        final FileText fileText = new FileText(new File(getPath("InputFileTextImportControl.xml")),
                charsetName);
        final FileText copy = new FileText(fileText);
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
}
