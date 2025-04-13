////
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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.gui.MainFrameModel.ParseMode;

/**
 * Displays information about a parse tree.
 * The user can change the file that is parsed and displayed
 * using a JFileChooser.
 *
 * @noinspection MagicNumber
 * @noinspectionreason MagicNumber - "magic numbers" are required to set GUI elements
 */
public class MainFrame extends JFrame {

    /** A unique serial version identifier. */
    private static final long serialVersionUID = 7970053543351871890L;

    /** The icon to show in the OS task panel. */
    private static final String ICON = "icon.png";

    /** Checkstyle frame model. */
    private final transient MainFrameModel model = new MainFrameModel();
    /** Reload action. */
    private final ReloadAction reloadAction = new ReloadAction();
    /** Code text area. */
    private JTextArea textArea;
    /** Xpath text area. */
    private JTextArea xpathTextArea;
    /** Tree table. */
    private TreeTable treeTable;

    /** Create a new MainFrame. */
    public MainFrame() {
        createContent();
    }

    /** Create content of this MainFrame. */
    private void createContent() {
        setLayout(new BorderLayout());
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource(ICON)));

        textArea = new JTextArea(20, 15);
        textArea.setEditable(false);
        final JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        final JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new BorderLayout());
        textAreaPanel.add(textAreaScrollPane);
        textAreaPanel.add(createButtonsPanel(), BorderLayout.PAGE_END);

        treeTable = new TreeTable(model.getParseTreeTableModel());
        treeTable.setEditor(textArea);
        treeTable.setLinePositionList(model.getLinesToPosition());
        final JScrollPane treeTableScrollPane = new JScrollPane(treeTable);

        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            treeTableScrollPane, textAreaPanel);

        add(splitPane, BorderLayout.CENTER);
        splitPane.setResizeWeight(0.7);

        xpathTextArea = new JTextArea("Xpath", 7, 0);
        xpathTextArea.setName("xpathTextArea");
        xpathTextArea.setVisible(false);
        final JPanel xpathAreaPanel = new JPanel();
        xpathAreaPanel.setLayout(new BorderLayout());
        xpathAreaPanel.add(xpathTextArea);
        xpathAreaPanel.add(createXpathButtonsPanel(), BorderLayout.PAGE_END);

        treeTable.setXpathEditor(xpathTextArea);

        final Border title = BorderFactory.createTitledBorder("Xpath Query");
        xpathAreaPanel.setBorder(title);

        add(xpathAreaPanel, BorderLayout.PAGE_END);
    }

    /**
     * Create buttons panel.
     *
     * @return buttons panel.
     */
    private JPanel createButtonsPanel() {
        final JButton openFileButton = new JButton(new FileSelectionAction());
        openFileButton.setName("openFileButton");
        openFileButton.setMnemonic(KeyEvent.VK_O);
        openFileButton.setText("Open File");

        reloadAction.setEnabled(false);
        final JButton reloadFileButton = new JButton(reloadAction);
        reloadFileButton.setMnemonic(KeyEvent.VK_R);
        reloadFileButton.setText("Reload File");

        final JComboBox<ParseMode> modesCombobox = new JComboBox<>(ParseMode.values());
        modesCombobox.setName("modesCombobox");
        modesCombobox.setSelectedIndex(0);
        modesCombobox.addActionListener(event -> {
            model.setParseMode((ParseMode) modesCombobox.getSelectedItem());
            reloadAction.actionPerformed(null);
        });

        final JLabel modesLabel = new JLabel("Modes:", SwingConstants.RIGHT);
        final int leftIndentation = 10;
        modesLabel.setBorder(BorderFactory.createEmptyBorder(0, leftIndentation, 0, 0));
        modesLabel.setDisplayedMnemonic(KeyEvent.VK_M);
        modesLabel.setLabelFor(modesCombobox);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(openFileButton);
        buttonPanel.add(reloadFileButton);

        final JPanel modesPanel = new JPanel();
        modesPanel.add(modesLabel);
        modesPanel.add(modesCombobox);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(buttonPanel);
        mainPanel.add(modesPanel, BorderLayout.LINE_END);

        return mainPanel;
    }

    /**
     * Create xpath buttons panel.
     *
     * @return xpath buttons panel.
     */
    private JPanel createXpathButtonsPanel() {
        final JButton expandButton = new JButton(new ExpandCollapseAction());
        expandButton.setName("expandButton");
        expandButton.setText("Expand/Collapse");

        final JButton findNodeButton = new JButton(new FindNodeByXpathAction());
        findNodeButton.setName("findNodeButton");
        findNodeButton.setText("Find node by Xpath");

        final JPanel xpathButtonsPanel = new JPanel();
        xpathButtonsPanel.setLayout(new FlowLayout());
        xpathButtonsPanel.add(expandButton);
        xpathButtonsPanel.add(findNodeButton);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(xpathButtonsPanel, BorderLayout.LINE_START);

        return mainPanel;
    }

    /**
     * Open file and load it.
     *
     * @param sourceFile the file to open.
     */
    public void openFile(File sourceFile) {
        try {
            model.openFile(sourceFile);
            setTitle(model.getTitle());
            reloadAction.setEnabled(model.isReloadActionEnabled());
            textArea.setText(model.getText());
            treeTable.setLinePositionList(model.getLinesToPosition());
        }
        catch (final CheckstyleException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    /**
     * Handler for file selection action events.
     */
    private final class FileSelectionAction extends AbstractAction {

        /** A unique serial version identifier. */
        private static final long serialVersionUID = 1762396148873280589L;

        @Override
        public void actionPerformed(ActionEvent event) {
            final JFileChooser fileChooser = new JFileChooser(model.getLastDirectory());
            final FileFilter filter = new JavaFileFilter();
            fileChooser.setFileFilter(filter);

            final int returnCode = fileChooser.showOpenDialog(MainFrame.this);
            if (returnCode == JFileChooser.APPROVE_OPTION) {
                final File file = fileChooser.getSelectedFile();
                openFile(file);
            }
        }

    }

    /**
     * Handler for reload action events.
     */
    private final class ReloadAction extends AbstractAction {

        /** A unique serial version identifier. */
        private static final long serialVersionUID = -890320994114628011L;

        @Override
        public void actionPerformed(ActionEvent event) {
            openFile(model.getCurrentFile());
        }

    }

    /**
     * Handler for Expand and Collapse events.
     */
    private final class ExpandCollapseAction extends AbstractAction {

        /** A unique serial version identifier. */
        private static final long serialVersionUID = -890320994114628011L;

        @Override
        public void actionPerformed(ActionEvent event) {
            xpathTextArea.setVisible(!xpathTextArea.isVisible());
        }

    }

    /**
     * Handler for Find Node by Xpath Event.
     */
    private final class FindNodeByXpathAction extends AbstractAction {

        /** A unique serial version identifier. */
        private static final long serialVersionUID = -890320994114628011L;

        @Override
        public void actionPerformed(ActionEvent event) {
            treeTable.selectNodeByXpath();
        }

    }

    /**
     * Filter for Java files.
     */
    private static final class JavaFileFilter extends FileFilter {

        @Override
        public boolean accept(File file) {
            return MainFrameModel.shouldAcceptFile(file);
        }

        @Override
        public String getDescription() {
            return "Java Source File";
        }

    }

}
