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

package com.puppycrawl.tools.checkstyle.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
                    assertEquals("Plain Java",
                            parseMode.toString(), "Invalid toString result");
                    break;
                case JAVA_WITH_COMMENTS:
                    assertEquals("Java with comments",
                            parseMode.toString(), "Invalid toString result");
                    break;
                case JAVA_WITH_JAVADOC_AND_COMMENTS:
                    assertEquals("Java with comments and Javadocs",
                            parseMode.toString(), "Invalid toString result");
                    break;
                default:
                    fail("Unexpected enum value");
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

        assertNull(model.getText(), "Test is null");
        assertEquals("Checkstyle GUI", model.getTitle(), "Title is expected value");
        assertFalse(model.isReloadActionEnabled(), "Reload action should be disabled");
        assertNull(model.getCurrentFile(), "Current file is null");
    }

    @Test
    public void testOpenFileNonExistentFile() throws IOException {
        final File nonExistentFile = new File(getPath(FILE_NAME_NON_EXISTENT));

        try {
            model.openFile(nonExistentFile);

            fail("Expected CheckstyleException is not thrown.");
        }
        catch (CheckstyleException ex) {
            final String expectedMsg = String.format(Locale.ROOT,
                    "FileNotFoundException occurred while opening file %s.",
                    nonExistentFile.getPath());

            assertEquals(expectedMsg, ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testOpenFileNonCompilableFile() throws IOException {
        final File nonCompilableFile = new File(getNonCompilablePath(FILE_NAME_NON_COMPILABLE));

        try {
            model.openFile(nonCompilableFile);

            fail("Expected CheckstyleException is not thrown.");
        }
        catch (CheckstyleException ex) {
            final String expectedMsg = String.format(Locale.ROOT,
                    "NoViableAltException occurred while parsing file %s.",
                    nonCompilableFile.getPath());

            assertEquals(expectedMsg, ex.getMessage(), "Invalid exception message");
        }
    }

    private void verifyCorrectTestDataInFrameModel() throws IOException {
        assertEquals(testData, model.getCurrentFile(), "Invalid current file");

        final String expectedTitle = "Checkstyle GUI : " + FILE_NAME_TEST_DATA;
        assertEquals(expectedTitle, model.getTitle(), "Invalid model title");

        assertTrue(model.isReloadActionEnabled(), "Reload action should be enabled");

        final int expectedLines = 19;
        assertEquals(expectedLines, model.getLinesToPosition().size(), "Invalid lines to position");

        final String testDataFileNameWithoutPostfix = FILE_NAME_TEST_DATA.replace(".java", "");
        assertTrue(model.getText().contains(testDataFileNameWithoutPostfix),
                "Invalid model text: " + model.getText());

        final File expectedLastDirectory = new File(getPath(""));
        assertEquals(expectedLastDirectory, model.getLastDirectory(),
                "Invalid model last directory");

        assertNotNull(model.getParseTreeTableModel(), "ParseTree table model should not be null");
    }

}
