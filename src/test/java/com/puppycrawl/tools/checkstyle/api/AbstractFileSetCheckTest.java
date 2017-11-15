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
import java.lang.reflect.Field;
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

        assertEquals("Invalid message", "File should not be empty.",
            firstFileMessages.first().getMessage());

        final Field field = AbstractFileSetCheck.class.getDeclaredField("MESSAGE_COLLECTOR");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        final SortedSet<LocalizedMessage> internalMessages =
                ((ThreadLocal<SortedSet<LocalizedMessage>>) field.get(null)).get();
        assertTrue("Internal message should be empty, but was not", internalMessages.isEmpty());

        final File secondFile = new File("inputAbstractFileSetCheck.txt");
        final List<String> lines = Arrays.asList("key=value", "ext=tmp");
        final SortedSet<LocalizedMessage> secondFileMessages =
            check.process(secondFile, new FileText(secondFile, lines));

        assertTrue("Message should be empty, but was not", secondFileMessages.isEmpty());
    }

    @Test
    public void testProcessException() throws Exception {
        final ExceptionFileSetCheck check = new ExceptionFileSetCheck();
        check.configure(new DefaultConfiguration("filesetcheck"));
        check.setFileExtensions("tmp");
        final File firstFile = new File("inputAbstractFileSetCheck.tmp");

        try {
            check.process(firstFile, new FileText(firstFile, Collections.emptyList()));
            fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            // exception is expected
        }

        final Field field = AbstractFileSetCheck.class.getDeclaredField("MESSAGE_COLLECTOR");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        final SortedSet<LocalizedMessage> internalMessages =
                ((ThreadLocal<SortedSet<LocalizedMessage>>) field.get(null)).get();
        assertEquals("Internal message should only have 1", 1, internalMessages.size());

        // again to prove only 1 violation exists
        final File secondFile = new File("inputAbstractFileSetCheck.tmp");
        try {
            check.process(secondFile, new FileText(secondFile, Collections.emptyList()));
            fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            // exception is expected
        }

        @SuppressWarnings("unchecked")
        final SortedSet<LocalizedMessage> internalMessages2 =
            ((ThreadLocal<SortedSet<LocalizedMessage>>) field.get(null)).get();
        assertEquals("Internal message should only have 1 again", 1, internalMessages2.size());
    }

    @Test
    public void testGetFileExtention() {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        check.setFileExtensions("tmp", ".java");
        final String[] expectedExtentions = {".tmp", ".java"};

        Assert.assertArrayEquals("Invalid extensions",
                expectedExtentions, check.getFileExtensions());
    }

    /**
     * This javadoc exists only to suppress Intellij Idea inspection
     */
    @Test
    public void testSetExtentionThrowsExceptionWhenTheyAreNull() {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        try {
            check.setFileExtensions((String[]) null);
            fail("Expected exception.");
        }
        catch (IllegalArgumentException exception) {
            assertEquals("Invalid exception message",
                    "Extensions array can not be null", exception.getMessage());
        }
    }

    @Test
    public void testGetMessageDispatcher() {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        final Checker checker = new Checker();
        check.setMessageDispatcher(checker);

        assertEquals("Invalid message dispatcher", checker, check.getMessageDispatcher());
    }

    private static class DummyFileSetCheck extends AbstractFileSetCheck {
        private static final String MSG_KEY = "File should not be empty.";

        @Override
        protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
            if (fileText.size() == 0) {
                log(1, MSG_KEY);
            }
        }
    }

    private static class ExceptionFileSetCheck extends AbstractFileSetCheck {
        private static final String MSG_KEY = "Test.";
        private int count = 1;

        @Override
        protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
            log(count, MSG_KEY);
            count++;
            throw new IllegalArgumentException("Test");
        }
    }
}
