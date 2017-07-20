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

package com.puppycrawl.tools.checkstyle.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.gui.MainFrameModel.ParseMode;

@RunWith(PowerMockRunner.class)
public class MainFrameModelTest {

    private static final String FILE_NAME_TEST_DATA = "InputJavadocAttributesAndMethods.java";
    private static final String FILE_NAME_NON_JAVA = "NotJavaFile.notjava";
    private static final String FILE_NAME_NON_EXISTENT = "non-existent.file";
    private static final String FILE_NAME_NON_COMPILABLE = "InputIncorrectClass.java";

    private MainFrameModel model;
    private File testData;

    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/gui/" + filename;
    }

    private static String getNonCompilablePath(String filename) {
        return "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/gui/" + filename;
    }

    @Before
    public void prepareTestData() {
        model = new MainFrameModel();
        testData = new File(getPath(FILE_NAME_TEST_DATA));
    }

    @Test
    public void testParseModeEnum() {
        for (final ParseMode parseMode : ParseMode.values()) {
            switch (parseMode) {
                case PLAIN_JAVA:
                    assertEquals("Plain Java", parseMode.toString());
                    break;
                case JAVA_WITH_COMMENTS:
                    assertEquals("Java with comments", parseMode.toString());
                    break;
                case JAVA_WITH_JAVADOC_AND_COMMENTS:
                    assertEquals("Java with comments and Javadocs", parseMode.toString());
                    break;
                default:
                    fail("Unexpected enum value");
            }
        }
    }

    @Test
    public void testShouldAcceptFile() {
        final File directory = PowerMockito.mock(File.class);
        PowerMockito.when(directory.isDirectory()).thenReturn(true);
        assertTrue(MainFrameModel.shouldAcceptFile(directory));

        final File javaFile = new File(getPath(FILE_NAME_TEST_DATA));
        assertTrue(MainFrameModel.shouldAcceptFile(javaFile));

        final File nonJavaFile = PowerMockito.mock(File.class);
        PowerMockito.when(nonJavaFile.isDirectory()).thenReturn(false);
        PowerMockito.when(nonJavaFile.getName()).thenReturn(FILE_NAME_NON_JAVA);
        assertFalse(MainFrameModel.shouldAcceptFile(nonJavaFile));

        final File nonExistentFile = new File(getPath(FILE_NAME_NON_EXISTENT));
        assertFalse(MainFrameModel.shouldAcceptFile(nonExistentFile));
    }

    @Test
    public void testOpenFileWithParseModePlainJava() throws CheckstyleException {
        // Default parse mode: Plain Java
        model.openFile(testData);
        verifyCorrectTestDataInFrameModel();

        model.setParseMode(ParseMode.PLAIN_JAVA);
        verifyCorrectTestDataInFrameModel();
    }

    @Test
    public void testOpenFileWithParseModeJavaWithComments() throws CheckstyleException {
        model.setParseMode(ParseMode.JAVA_WITH_COMMENTS);
        model.openFile(testData);

        verifyCorrectTestDataInFrameModel();
    }

    @Test
    public void testOpenFileWithParseModeJavaWithJavadocAndComments() throws CheckstyleException {
        model.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        model.openFile(testData);

        verifyCorrectTestDataInFrameModel();
    }

    @Test
    @PrepareForTest(ParseMode.class)
    public void testOpenFileWithUnknownParseMode() throws CheckstyleException {
        final ParseMode unknownParseMode = PowerMockito.mock(ParseMode.class);
        Whitebox.setInternalState(unknownParseMode, "ordinal", 3);

        PowerMockito.when(unknownParseMode.toString()).thenReturn("Unknown parse mode");
        PowerMockito.mockStatic(ParseMode.class);
        PowerMockito.when(ParseMode.values()).thenReturn(
                new ParseMode[] {
                    ParseMode.PLAIN_JAVA, ParseMode.JAVA_WITH_COMMENTS,
                    ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS, unknownParseMode, });

        try {
            model.setParseMode(unknownParseMode);
            model.openFile(testData);

            fail("Expected IllegalArgumentException is not thrown.");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Unknown mode: Unknown parse mode", ex.getMessage());
        }
    }

    @Test
    public void testOpenFileNullParameter() throws CheckstyleException {
        model.openFile(testData);

        model.openFile(null);

        // Model will not change with null input
        verifyCorrectTestDataInFrameModel();
    }

    @Test
    public void testOpenFileNonExistentFile() {
        final File nonExistentFile = new File(getPath(FILE_NAME_NON_EXISTENT));

        try {
            model.openFile(nonExistentFile);

            fail("Expected CheckstyleException is not thrown.");
        }
        catch (CheckstyleException ex) {
            final String expectedMsg = String.format(Locale.ROOT,
                    "FileNotFoundException occurred while opening file %s.",
                    nonExistentFile.getPath());

            assertEquals(expectedMsg, ex.getMessage());
        }
    }

    @Test
    public void testOpenFileNonCompilableFile() {
        final File nonCompilableFile = new File(getNonCompilablePath(FILE_NAME_NON_COMPILABLE));

        try {
            model.openFile(nonCompilableFile);

            fail("Expected CheckstyleException is not thrown.");
        }
        catch (CheckstyleException ex) {
            final String expectedMsg = String.format(Locale.ROOT,
                    "NoViableAltException occurred while opening file %s.",
                    nonCompilableFile.getPath());

            assertEquals(expectedMsg, ex.getMessage());
        }
    }

    private void verifyCorrectTestDataInFrameModel() {
        assertEquals(testData, model.getCurrentFile());

        final String expectedTitle = "Checkstyle GUI : " + FILE_NAME_TEST_DATA;
        assertEquals(expectedTitle, model.getTitle());

        assertTrue(model.isReloadActionEnabled());

        final int expectedLines = 17;
        assertEquals(expectedLines, model.getLinesToPosition().size());

        final String testDataFileNameWithoutPostfix = FILE_NAME_TEST_DATA.replace(".java", "");
        assertTrue(model.getText().contains(testDataFileNameWithoutPostfix));

        final File expectedLastDirectory = new File(getPath(""));
        assertEquals(expectedLastDirectory, model.getLastDirectory());

        assertNotNull(model.getParseTreeTableModel());
    }
}
