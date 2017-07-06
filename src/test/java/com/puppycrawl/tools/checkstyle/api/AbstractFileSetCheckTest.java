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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class AbstractFileSetCheckTest {

    @Test
    public void testProcessSequential() throws Exception {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        check.configure(new DefaultConfiguration("filesetcheck"));
        check.setFileExtensions("tmp");
        final File firstFile = new File("inputAbstractFileSetCheck.tmp");
        final SortedSet<LocalizedMessage> firstFileMessages =
            check.process(firstFile, new FileText(firstFile, Collections.emptyList()));
        final File secondFile = new File("inputAbstractFileSetCheck.txt");
        final List<String> lines = Arrays.asList("key=value", "ext=tmp");
        final SortedSet<LocalizedMessage> secondFileMessages =
            check.process(secondFile, new FileText(secondFile, lines));

        assertEquals("File should not be empty.",
            firstFileMessages.first().getMessage());
        assertTrue(secondFileMessages.isEmpty());
    }

    @Test
    public void testGetFileExtention() throws Exception {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        check.setFileExtensions("tmp", ".java");
        final String[] expectedExtentions = {".tmp", ".java"};

        Assert.assertArrayEquals(expectedExtentions, check.getFileExtensions());
    }

    @Test
    @SuppressWarnings("NullArgumentToVariableArgMethod")
    public void testSetExtentionThrowsExceptionWhenTheyAreNull() throws Exception {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        try {
            check.setFileExtensions(null);
            fail("Expected exception.");
        }
        catch (IllegalArgumentException exception) {
            assertEquals("Extensions array can not be null", exception.getMessage());
        }
    }

    @Test
    public void testGetMessageDispatcher() throws Exception {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        final Checker checker = new Checker();
        check.setMessageDispatcher(checker);

        assertEquals(checker, check.getMessageDispatcher());
    }

    private static class DummyFileSetCheck extends AbstractFileSetCheck {
        private static final String MSG_KEY = "File should not be empty.";

        @Override
        protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
            final List<String> lines = fileText.getLines();
            if (lines.isEmpty()) {
                log(1, MSG_KEY);
            }
        }
    }
}
