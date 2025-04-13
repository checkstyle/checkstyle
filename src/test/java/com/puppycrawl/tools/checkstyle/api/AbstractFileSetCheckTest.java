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

public class AbstractFileSetCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/api/abstractfileset";
    }

    @Test
    public void testTabWidth() {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        check.setTabWidth(12345);
        assertWithMessage("expected tab width")
                .that(check.getTabWidth())
                .isEqualTo(12345);
    }

    @Test
    public void testFileContents() {
        final FileContents contents = new FileContents(
                new FileText(new File("inputAbstractFileSetCheck.tmp"), Collections.emptyList()));
        final DummyFileSetCheck check = new DummyFileSetCheck();
        check.setFileContents(contents);
        assertWithMessage("expected file contents")
                .that(check.getFileContents())
                .isSameInstanceAs(contents);
    }

    @Test
    public void testProcessSequential() throws Exception {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        check.configure(new DefaultConfiguration("filesetcheck"));
        check.setFileExtensions("tmp");
        final File firstFile = new File("inputAbstractFileSetCheck.tmp");
        final SortedSet<Violation> firstFileMessages =
            check.process(firstFile, new FileText(firstFile, Collections.emptyList()));

        assertWithMessage("Invalid message")
                .that(firstFileMessages.first().getViolation())
                .isEqualTo("File should not be empty.");

        final SortedSet<Violation> internalMessages =
                check.getViolations();
        assertWithMessage("Internal message should be empty, but was not")
                .that(internalMessages)
                .isEmpty();

        final File secondFile = new File("inputAbstractFileSetCheck.txt");
        final List<String> lines = Arrays.asList("key=value", "ext=tmp");
        final SortedSet<Violation> secondFileMessages =
            check.process(secondFile, new FileText(secondFile, lines));

        assertWithMessage("Message should be empty, but was not")
                .that(secondFileMessages)
                .isEmpty();
    }

    @Test
    public void testNotProcessed() throws Exception {
        final ExceptionFileSetCheck check = new ExceptionFileSetCheck();
        check.setFileExtensions("java");
        final File firstFile = new File("inputAbstractFileSetCheck.tmp");

        check.process(firstFile, new FileText(firstFile, Collections.emptyList()));

        final SortedSet<Violation> internalMessages =
                check.getViolations();
        assertWithMessage("Internal message should be empty")
                .that(internalMessages)
                .isEmpty();
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
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (IllegalArgumentException ex) {
            // exception is expected
            assertWithMessage("Invalid exception message")
                    .that(ex.getMessage())
                    .isEqualTo("Test");
        }

        final SortedSet<Violation> internalViolations =
                check.getViolations();
        assertWithMessage("Internal violation should only have 1")
                .that(internalViolations)
                .hasSize(1);

        // again to prove only 1 violation exists
        final File secondFile = new File("inputAbstractFileSetCheck.tmp");
        final FileText fileText2 = new FileText(secondFile, Collections.emptyList());
        try {
            check.process(secondFile, fileText2);
            assertWithMessage("Exception is expected")
                .fail();
        }
        catch (IllegalArgumentException ex) {
            // exception is expected
            assertWithMessage("Invalid exception message")
                    .that(ex.getMessage())
                    .isEqualTo("Test");
        }

        final SortedSet<Violation> internalViolations2 =
            check.getViolations();
        assertWithMessage("Internal violation should only have 1 again")
                .that(internalViolations2)
                .hasSize(1);
    }

    @Test
    public void testGetFileExtension() {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        check.setFileExtensions("tmp", ".java");
        final String[] expectedExtensions = {".tmp", ".java"};

        assertWithMessage("Invalid extensions")
                .that(check.getFileExtensions())
                .isEqualTo(expectedExtensions);
    }

    /**
     * This javadoc exists only to suppress IntelliJ IDEA inspection.
     */
    @Test
    public void testSetExtensionThrowsExceptionWhenTheyAreNull() {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        try {
            check.setFileExtensions((String[]) null);
            assertWithMessage("Expected exception.")
                .fail();
        }
        catch (IllegalArgumentException exception) {
            assertWithMessage("Invalid exception message")
                    .that(exception.getMessage())
                    .isEqualTo("Extensions array can not be null");
        }
    }

    @Test
    public void testLineColumnLog() throws Exception {
        final ViolationFileSetCheck check = new ViolationFileSetCheck();
        check.configure(new DefaultConfiguration("filesetcheck"));
        final File file = new File(getPath("InputAbstractFileSetLineColumn.java"));
        final FileText theText = new FileText(file.getAbsoluteFile(),
                StandardCharsets.UTF_8.name());
        final SortedSet<Violation> internalViolations = check.process(file, theText);

        assertWithMessage("Internal violation should only have 1")
                .that(internalViolations)
                .hasSize(1);

        final Violation violation = internalViolations.iterator().next();
        assertWithMessage("expected line")
                .that(violation.getLineNo())
                .isEqualTo(1);
        assertWithMessage("expected column")
                .that(violation.getColumnNo())
                .isEqualTo(1);
    }

    @Test
    public void testGetMessageDispatcher() {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        final Checker checker = new Checker();
        check.setMessageDispatcher(checker);

        assertWithMessage("Invalid message dispatcher")
                .that(check.getMessageDispatcher())
                .isSameInstanceAs(checker);
    }

    @Test
    public void testCheck() throws Exception {
        final String[] expected = {
            "1:1: Violation.",
        };
        verifyWithInlineConfigParser(getPath("InputAbstractFileSetLineColumn.java"), expected);
    }

    @Test
    public void testMultiFileFireErrors() throws Exception {
        final MultiFileViolationFileSetCheck check = new MultiFileViolationFileSetCheck();
        check.configure(new DefaultConfiguration("filesetcheck"));
        final ViolationDispatcher dispatcher = new ViolationDispatcher();
        check.setMessageDispatcher(dispatcher);

        check.finishProcessing();

        assertWithMessage("Invalid fileName reported")
                .that(dispatcher.name)
                .isEqualTo("fileName");

        assertWithMessage("errors should only have 1")
                .that(dispatcher.errorList)
                .hasSize(1);

        final Violation violation = dispatcher.errorList.iterator().next();
        assertWithMessage("expected line")
                .that(violation.getLineNo())
                .isEqualTo(1);
        assertWithMessage("expected column")
                .that(violation.getColumnNo())
                .isEqualTo(0);

        // re-running erases previous errors

        check.finishProcessing();

        assertWithMessage("errors should still have 1 after re-run")
                .that(dispatcher.errorList)
                .hasSize(1);
        assertWithMessage("finishProcessing was called twice")
                .that(check.finishProcessingCount)
                .isEqualTo(2);
    }

    /**
     * S2384 - Mutable members should not be stored or returned directly.
     * Inspection is valid, a pure unit test is required as this condition can't be recreated in
     * a test with checks and input file as none of the checks try to modify the fileExtensions.
     */
    @Test
    public void testCopiedArrayIsReturned() {
        final DummyFileSetCheck check = new DummyFileSetCheck();
        check.setFileExtensions(".tmp");
        assertWithMessage("Extensions should be copied")
            .that(check.getFileExtensions())
            .isNotSameInstanceAs(check.getFileExtensions());
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
            log(1, 0, MSG_KEY);
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

    }

}
