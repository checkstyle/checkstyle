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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileProcessingResultTest {
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testNonNullFileText() throws IOException {
        final String charsetName = "ISO-8859-1";
        final File file = temporaryFolder.newFile("test.java");
        final FileText fileText = new FileText(file, charsetName);
        final SortedSet<LocalizedMessage> messages = new TreeSet<>();
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        final FileProcessingResult processingResult = new FileProcessingResult(
                file.getAbsolutePath(), fileText, messages);
        assertNotNull(processingResult.getFileText());
    }

    @Test
    public void testNullFileText() {
        final SortedSet<LocalizedMessage> messages = new TreeSet<>();
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        final FileProcessingResult processingResult = new FileProcessingResult("Some file name",
                null, messages);
        assertEquals(1, processingResult.getMessages().size());
        assertNull(processingResult.getFileText());
    }
}
