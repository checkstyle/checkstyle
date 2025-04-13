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

package com.puppycrawl.tools.checkstyle.gui;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.gui.MainFrameModel.ParseMode;

public class MainFrameModelTest extends AbstractModuleTestSupport {

    private static final String FILE_NAME_TEST_DATA = "InputMainFrameModel.java";
    private static final String FILE_NAME_NON_EXISTENT = "non-existent.file";
    private static final String FILE_NAME_NON_JAVA = "NotJavaFile.notjava";
    private static final String FILE_NAME_NON_COMPILABLE = "InputMainFrameModelIncorrectClass.java";

    private MainFrameModel model;
    private File testData;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/gui/mainframemodel";
    }

    @BeforeEach
    public void prepareTestData() throws IOException {
        model = new MainFrameModel();
        testData = new File(getPath(FILE_NAME_TEST_DATA));
    }

    @Test
    public void testParseModeEnum() {
        for (final ParseMode parseMode : ParseMode.values()) {
            switch (parseMode) {
                case PLAIN_JAVA:
                    assertWithMessage("Invalid toString result")
                        .that(parseMode.toString())
                        .isEqualTo("Plain Java");
                    break;
                case JAVA_WITH_COMMENTS:
                    assertWithMessage("Invalid toString result")
                        .that(parseMode.toString())
                        .isEqualTo("Java with comments");
                    break;
                case JAVA_WITH_JAVADOC_AND_COMMENTS:
                    assertWithMessage("Invalid toString result")
                        .that(parseMode.toString())
                        .isEqualTo("Java with comments and Javadocs");
                    break;
                default:
                    assertWithMessage("Unexpected enum value").fail();
            }
        }
    }

    @Test
    public void testOpenFileWithParseModePlainJava() throws Exception {
        // Default parse mode: Plain Java
        model.openFile(testData);
        verifyCorrectTestDataInFrameModel();

        model.setParseMode(ParseMode.PLAIN_JAVA);
        verifyCorrectTestDataInFrameModel();
    }

    @Test
    public void testOpenFileWithParseModeJavaWithComments() throws Exception {
        model.setParseMode(ParseMode.JAVA_WITH_COMMENTS);
        model.openFile(testData);

        verifyCorrectTestDataInFrameModel();
    }

    @Test
    public void testOpenFileWithParseModeJavaWithJavadocAndComments() throws Exception {
        model.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        model.openFile(testData);

        verifyCorrectTestDataInFrameModel();
    }

    @Test
    public void testOpenFileNullParameter() throws Exception {
        model.openFile(testData);

        model.openFile(null);

        // Model will not change with null input
        verifyCorrectTestDataInFrameModel();
    }

    @Test
    public void testOpenFileNullParameter2() throws Exception {
        model.openFile(null);

        assertWithMessage("Test is null")
            .that(model.getText())
            .isNull();
        assertWithMessage("Title is expected value")
            .that(model.getTitle())
            .isEqualTo("Checkstyle GUI");
        assertWithMessage("Reload action should be disabled")
                .that(model.isReloadActionEnabled())
                .isFalse();
        assertWithMessage("Current file is null")
            .that(model.getCurrentFile())
            .isNull();
    }

    @Test
    public void testOpenFileNonExistentFile() throws IOException {
        final File nonExistentFile = new File(getPath(FILE_NAME_NON_EXISTENT));

        try {
            model.openFile(nonExistentFile);

            assertWithMessage("Expected CheckstyleException is not thrown.").fail();
        }
        catch (CheckstyleException ex) {
            final String expectedMsg = String.format(Locale.ROOT,
                    "FileNotFoundException occurred while opening file %s.",
                    nonExistentFile.getPath());

            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(expectedMsg);
        }
    }

    @Test
    public void testOpenFileNonCompilableFile() throws IOException {
        final File nonCompilableFile = new File(getNonCompilablePath(FILE_NAME_NON_COMPILABLE));

        try {
            model.openFile(nonCompilableFile);

            assertWithMessage("Expected CheckstyleException is not thrown.").fail();
        }
        catch (CheckstyleException ex) {
            final String expectedMsg = String.format(Locale.ROOT,
                    "IllegalStateException occurred while parsing file %s.",
                    nonCompilableFile.getPath());

            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(expectedMsg);
        }
    }

    private void verifyCorrectTestDataInFrameModel() throws IOException {
        assertWithMessage("Invalid current file")
            .that(model.getCurrentFile())
            .isEqualTo(testData);

        final String expectedTitle = "Checkstyle GUI : " + FILE_NAME_TEST_DATA;
        assertWithMessage("Invalid model title")
            .that(model.getTitle())
            .isEqualTo(expectedTitle);

        assertWithMessage("Reload action should be enabled")
                .that(model.isReloadActionEnabled())
                .isTrue();

        final int expectedLines = 19;
        assertWithMessage("Invalid lines to position")
            .that(model.getLinesToPosition())
            .hasSize(expectedLines);

        final String testDataFileNameWithoutPostfix = FILE_NAME_TEST_DATA.replace(".java", "");
        assertWithMessage("Invalid model text: " + model.getText())
                .that(model.getText())
                .contains(testDataFileNameWithoutPostfix);

        final File expectedLastDirectory = new File(getPath(""));
        assertWithMessage("Invalid model last directory")
            .that(model.getLastDirectory())
            .isEqualTo(expectedLastDirectory);

        assertWithMessage("ParseTree table model should not be null")
            .that(model.getParseTreeTableModel())
            .isNotNull();
    }

    @Test
    public void testShouldAcceptDirectory() {
        final File directory = mock();
        when(directory.isDirectory()).thenReturn(true);
        assertWithMessage("MainFrame should accept directory")
                .that(MainFrameModel.shouldAcceptFile(directory))
                .isTrue();
    }

    @Test
    public void testShouldAcceptFile() throws IOException {
        final File javaFile = new File(getPath(FILE_NAME_TEST_DATA));
        assertWithMessage("MainFrame should accept java file")
                .that(MainFrameModel.shouldAcceptFile(javaFile))
                .isTrue();
    }

    @Test
    public void testShouldNotAcceptNonJavaFile() {
        final File nonJavaFile = mock();
        when(nonJavaFile.isDirectory()).thenReturn(false);
        when(nonJavaFile.getName()).thenReturn(FILE_NAME_NON_JAVA);
        assertWithMessage("MainFrame should not accept non-Java file")
                .that(MainFrameModel.shouldAcceptFile(nonJavaFile))
                .isFalse();
    }

    @Test
    public void testShouldNotAcceptNonExistentFile() throws IOException {
        final File nonExistentFile = new File(getPath(FILE_NAME_NON_EXISTENT));
        assertWithMessage("MainFrame should not accept non-existent file")
                .that(MainFrameModel.shouldAcceptFile(nonExistentFile))
                .isFalse();
    }

    @Test
    public void testOpenFileForUnknownParseMode() throws IOException {
        final File javaFile = new File(getPath(FILE_NAME_TEST_DATA));
        final ParseMode mock = mock();
        model.setParseMode(mock);
        final IllegalArgumentException ex =
                getExpectedThrowable(IllegalArgumentException.class,
                        () -> model.openFile(javaFile));
        assertWithMessage("Invalid error message")
                .that(ex)
                .hasMessageThat()
                .startsWith("Unknown mode:");
    }

}
