///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.gui;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import com.puppycrawl.tools.checkstyle.AbstractGuiTestSupport;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class MainFrameTest extends AbstractGuiTestSupport {

    private static final String TEST_FILE_NAME = "InputMainFrame.java";
    private static final String NON_EXISTENT_FILE_NAME = "non-existent.file";

    private MainFrame mainFrame;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/gui/mainframe";
    }

    @BeforeEach
    public void prepare() {
        mainFrame = new MainFrame();
    }

    @AfterEach
    public void tearDown() {
        Arrays.stream(mainFrame.getOwnedWindows())
                .forEach(Window::dispose);
    }

    @Test
    public void testOpenFile() throws IOException {
        mainFrame.openFile(new File(getPath(TEST_FILE_NAME)));
        assertWithMessage("Unexpected frame title")
                .that(mainFrame.getTitle())
                .isEqualTo("Checkstyle GUI : " + TEST_FILE_NAME);
    }

    /**
     * Test for opening a non-existent file.
     * {@code JOptionPane} is mocked to prevent showing a modal message box.
     *
     * @throws IOException if I/O exception occurs while forming the path.
     */
    @Test
    public void testOpenNonExistentFile() throws IOException {
        final File file = new File(getPath(NON_EXISTENT_FILE_NAME));
        try (MockedStatic<JOptionPane> optionPane = mockStatic(JOptionPane.class)) {
            mainFrame.openFile(file);
            optionPane.verify(() -> {
                JOptionPane.showMessageDialog(any(Component.class),
                        startsWith("FileNotFoundException occurred while opening file"));
            });
        }
        final MainFrameModel model = TestUtil.getInternalState(mainFrame, "model");
        assertWithMessage("Unexpected current file")
                .that(model.getCurrentFile())
                .isEqualTo(file);
    }

    @Test
    public void testChangeMode() {
        final JComboBox<MainFrameModel.ParseMode> modesCombobox =
                findComponentByName(mainFrame, "modesCombobox");
        modesCombobox.setSelectedIndex(MainFrameModel.ParseMode.JAVA_WITH_COMMENTS.ordinal());
        final MainFrameModel model = TestUtil.getInternalState(mainFrame, "model");
        final MainFrameModel.ParseMode parseMode = TestUtil.getInternalState(model, "parseMode");
        assertWithMessage("Unexpected parse mode")
                .that(parseMode)
                .isEqualTo(MainFrameModel.ParseMode.JAVA_WITH_COMMENTS);
    }

    /**
     * Test for opening a file with the "Open File" button.
     * {@code JFileChooser} is mocked to prevent showing a modal dialog box.
     *
     * @throws IOException if I/O exception occurs while forming the path.
     */
    @Test
    public void testOpenFileButton() throws IOException {
        final JButton openFileButton = findComponentByName(mainFrame, "openFileButton");
        final File testFile = new File(getPath(TEST_FILE_NAME));
        try (MockedConstruction<JFileChooser> mocked = mockConstruction(
                JFileChooser.class, (mock, context) -> {
                    when(mock.showOpenDialog(mainFrame)).thenReturn(JFileChooser.APPROVE_OPTION);
                    when(mock.getSelectedFile()).thenReturn(testFile);
                })) {
            openFileButton.doClick();
        }
        assertWithMessage("Unexpected frame title")
                .that(mainFrame.getTitle())
                .isEqualTo("Checkstyle GUI : " + TEST_FILE_NAME);
    }

    /**
     * Test for the {@link FileFilter} passed to {@code JFileChooser} to gain 100% coverage.
     * {@code JFileChooser} is mocked to obtain an instance of {@code JavaFileFilter} class.
     */
    @Test
    public void testFileFilter() {
        final JButton openFileButton = findComponentByName(mainFrame, "openFileButton");
        try (MockedConstruction<JFileChooser> mocked = mockConstruction(
                JFileChooser.class, (mock, context) -> {
                    when(mock.showOpenDialog(mainFrame)).thenReturn(JFileChooser.CANCEL_OPTION);
                    when(mock.getFileFilter()).thenCallRealMethod();
                    doCallRealMethod().when(mock).setFileFilter(any(FileFilter.class));
                })) {
            openFileButton.doClick();

            final FileFilter fileFilter = mocked.constructed().get(0).getFileFilter();
            assertWithMessage("The file should be accepted")
                    .that(fileFilter.accept(new File(TEST_FILE_NAME)))
                    .isTrue();
            assertWithMessage("Unexpected frame title")
                    .that(fileFilter.getDescription())
                    .isEqualTo("Java Source File");
        }
    }

    @Test
    public void testExpandButton() {
        final JButton expandButton = findComponentByName(mainFrame, "expandButton");
        final JTextArea xpathTextArea = findComponentByName(mainFrame, "xpathTextArea");
        expandButton.doClick();
        assertWithMessage("The XPath text area should be visible")
                .that(xpathTextArea.isVisible())
                .isTrue();
        expandButton.doClick();
        assertWithMessage("The XPath text area should be hidden")
                .that(xpathTextArea.isVisible())
                .isFalse();
    }

    @Test
    public void testFindNodeButton() throws IOException {
        mainFrame.openFile(new File(getPath(TEST_FILE_NAME)));
        final JButton findNodeButton = findComponentByName(mainFrame, "findNodeButton");
        final JTextArea xpathTextArea = findComponentByName(mainFrame, "xpathTextArea");
        findNodeButton.doClick();
        assertWithMessage("Unexpected XPath text area text")
                .that(xpathTextArea.getText())
                .isEqualTo("No elements matching XPath query 'Xpath' found.");
    }

}
