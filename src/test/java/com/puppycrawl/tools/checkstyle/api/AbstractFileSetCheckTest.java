////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;

public class AbstractFileSetCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/api/abstractfileset";
    }

    @Test
    public void testTabWidth() {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        check.setTabWidth(12345);
        assertEquals(12345, check.getTabWidth(), "expected tab width");
    }

    @Test
    public void testFileContents() {
        final FileContents contents = new FileContents(
                new FileText(new File("inputAbstractFileSetCheck.tmp"), Collections.emptyList()));
        final DummyFileSetCheck check = new DummyFileSetCheck();
        check.setFileContents(contents);
        assertSame(contents, check.getFileContents(), "expected file contents");
    }

    @Test
    public void testProcessSequential() throws Exception {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        check.configure(new DefaultConfiguration("filesetcheck"));
        check.setFileExtensions("tmp");
        final File firstFile = new File("inputAbstractFileSetCheck.tmp");
        final SortedSet<Violation> firstFileMessages =
            check.process(firstFile, new FileText(firstFile, Collections.emptyList()));

        assertEquals("File should not be empty.",
            firstFileMessages.first().getViolation(), "Invalid message");

        final SortedSet<Violation> internalMessages =
                check.getViolations();
        assertTrue(internalMessages.isEmpty(), "Internal message should be empty, but was not");

        final File secondFile = new File("inputAbstractFileSetCheck.txt");
        final List<String> lines = Arrays.asList("key=value", "ext=tmp");
        final SortedSet<Violation> secondFileMessages =
            check.process(secondFile, new FileText(secondFile, lines));

        assertTrue(secondFileMessages.isEmpty(), "Message should be empty, but was not");
    }

    @Test
    public void testNotProcessed() throws Exception {
        final ExceptionFileSetCheck check = new ExceptionFileSetCheck();
        check.setFileExtensions("java");
        final File firstFile = new File("inputAbstractFileSetCheck.tmp");

        check.process(firstFile, new FileText(firstFile, Collections.emptyList()));

        final SortedSet<Violation> internalMessages =
                check.getViolations();
        assertTrue(internalMessages.isEmpty(), "Internal message should be empty");
    }

    @Test
    public void testProcessException() throws Exception {
        final ExceptionFileSetCheck check = new ExceptionFileSetCheck();
        check.configure(new DefaultConfiguration("filesetcheck"));
        check.setFileExtensions("tmp");
        final File firstFile = new File("inputAbstractFileSetCheck.tmp");

        final FileText fileText = new FileText(firstFile, Collections.emptyList());
        try {
            check.process(firstFile, fileText);
            fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            // exception is expected
            assertEquals("Test", ex.getMessage(), "Invalid exception message");
        }

        final SortedSet<Violation> internalViolations =
                check.getViolations();
        assertEquals(1, internalViolations.size(), "Internal violation should only have 1");

        // again to prove only 1 violation exists
        final File secondFile = new File("inputAbstractFileSetCheck.tmp");
        final FileText fileText2 = new FileText(secondFile, Collections.emptyList());
        try {
            check.process(secondFile, fileText2);
            fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            // exception is expected
            assertEquals("Test", ex.getMessage(), "Invalid exception message");
        }

        final SortedSet<Violation> internalViolations2 =
            check.getViolations();
        assertEquals(1, internalViolations2.size(), "Internal violation should only have 1 again");
    }

    @Test
    public void testGetFileExtension() {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        check.setFileExtensions("tmp", ".java");
        final String[] expectedExtensions = {".tmp", ".java"};

        assertArrayEquals(expectedExtensions, check.getFileExtensions(), "Invalid extensions");
    }

    /**
     * This javadoc exists only to suppress IntelliJ IDEA inspection.
     */
    @Test
    public void testSetExtensionThrowsExceptionWhenTheyAreNull() {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        try {
            check.setFileExtensions((String[]) null);
            fail("Expected exception.");
        }
        catch (IllegalArgumentException exception) {
            assertEquals("Extensions array can not be null", exception.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testLineColumnLog() throws Exception {
        final ViolationFileSetCheck check = new ViolationFileSetCheck();
        check.configure(new DefaultConfiguration("filesetcheck"));
        final File file = new File(getPath("InputAbstractFileSetLineColumn.txt"));
        final FileText theText = new FileText(file.getAbsoluteFile(),
                StandardCharsets.UTF_8.name());
        final SortedSet<Violation> internalViolations = check.process(file, theText);

        assertEquals(1, internalViolations.size(), "Internal violation should only have 1");

        final Violation violation = internalViolations.iterator().next();
        assertEquals(1, violation.getLineNo(), "expected line");
        assertEquals(6, violation.getColumnNo(), "expected column");
    }

    @Test
    public void testGetMessageDispatcher() {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        final Checker checker = new Checker();
        check.setMessageDispatcher(checker);

        assertEquals(checker, check.getMessageDispatcher(), "Invalid message dispatcher");
    }

    @Test
    public void testCheck() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ViolationFileSetCheck.class);

        final String[] expected = {
            "1:6: Violation.",
        };
        verify(checkConfig, getPath("InputAbstractFileSetLineColumn.txt"), expected);
    }

    @Test
    public void testMultiFileFireErrors() throws Exception {
        final MultiFileViolationFileSetCheck check = new MultiFileViolationFileSetCheck();
        check.configure(new DefaultConfiguration("filesetcheck"));
        final ViolationDispatcher dispatcher = new ViolationDispatcher();
        check.setMessageDispatcher(dispatcher);

        check.finishProcessing();

        assertEquals("fileName", dispatcher.name, "Invalid fileName reported");

        assertEquals(1, dispatcher.errorList.size(), "errors should only have 1");

        final Violation violation = dispatcher.errorList.iterator().next();
        assertEquals(1, violation.getLineNo(), "expected line");
        assertEquals(0, violation.getColumnNo(), "expected column");

        // re-running erases previous errors

        check.finishProcessing();

        assertEquals(1, dispatcher.errorList.size(), "errors should still have 1 after re-run");
        assertEquals(2, check.finishProcessingCount, "finishProcessing was called twice");
    }

    public static class DummyFileSetCheck extends AbstractFileSetCheck {

        private static final String MSG_KEY = "File should not be empty.";

        @Override
        protected void processFiltered(File file, FileText fileText) {
            if (fileText.size() == 0) {
                log(1, MSG_KEY);
            }
        }

    }

    public static class ViolationFileSetCheck extends AbstractFileSetCheck {

        private static final String MSG_KEY = "Violation.";

        @Override
        protected void processFiltered(File file, FileText fileText) {
            log(1, 5, MSG_KEY);
        }

    }

    public static class MultiFileViolationFileSetCheck extends AbstractFileSetCheck {

        private static final String MSG_KEY = "Violation.";
        private int finishProcessingCount;

        @Override
        protected void processFiltered(File file, FileText fileText) {
            // no code needed
        }

        @Override
        public void finishProcessing() {
            final String fileName = "fileName";

            log(1, MSG_KEY + finishProcessingCount);
            fireErrors(fileName);

            finishProcessingCount++;
        }

    }

    public static class ExceptionFileSetCheck extends AbstractFileSetCheck {

        private static final String MSG_KEY = "Test.";
        private int count = 1;

        @Override
        protected void processFiltered(File file, FileText fileText) {
            log(count, MSG_KEY);
            count++;
            throw new IllegalArgumentException("Test");
        }

    }

    public static class ViolationDispatcher implements MessageDispatcher {
        private String name;
        private SortedSet<Violation> errorList;

        @Override
        public void fireFileStarted(String fileName) {
            // no code needed
        }

        @Override
        public void fireFileFinished(String fileName) {
            // no code needed
        }

        @Override
        public void fireErrors(String fileName, SortedSet<Violation> errors) {
            name = fileName;
            errorList = new TreeSet<>(errors);
        }

        @Override
        public void fireCheckStarted(AbstractCheck check) {
            // no code needed
        }

        @Override
        public void fireCheckFinished(AbstractCheck check) {
            // no code needed
        }

        @Override
        public void fireParseStarted(TreeWalker treeWalker) {
            // no code needed
        }

        @Override
        public void fireParseFinished(TreeWalker treeWalker) {
            // no code needed
        }

        @Override
        public void fireParseJavaDocStarted(AbstractJavadocCheck abstractJavadocCheck) {
            // no code needed
        }

        @Override
        public void fireParseJavaDocFinished(AbstractJavadocCheck abstractJavadocCheck) {
            // no code needed
        }

        @Override
        public void fireCustomStarted(String source) {
            // no code needed
        }

        @Override
        public void fireCustomFinished(String source) {
            // no code needed
        }

        @Override
        public void fireFilterStarted(Filter filter) {
            // no code needed
        }

        @Override
        public void fireFilterFinished(Filter filter) {
            // no code needed
        }

        @Override
        public void fireBeforeExecutionFileFilterStarted(BeforeExecutionFileFilter filter) {
            // no code needed
        }

        @Override
        public void fireBeforeExecutionFileFilterFinished(BeforeExecutionFileFilter filter) {
            // no code needed
        }

        @Override
        public void fireFileSetStarted(FileSetCheck fsc, String fileName) {
            // no code needed
        }

        @Override
        public void fireFileSetFinished(FileSetCheck fsc, String fileName) {
            // no code needed
        }

        @Override
        public void fireTreeWalkerFilterStarted(TreeWalkerFilter filter) {
            // no code needed
        }

        @Override
        public void fireTreeWalkerFilterFinished(TreeWalkerFilter filter) {
            // no code needed
        }

    }

}
