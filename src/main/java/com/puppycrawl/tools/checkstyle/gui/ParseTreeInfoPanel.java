////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;

import antlr.ANTLRException;

/**
 * Displays information about a parse tree.
 * The user can change the file that is parsed and displayed
 * through a JFileChooser.
 *
 * @author Lars Kühne
 */
public class ParseTreeInfoPanel extends JPanel {
    /** For Serialisation that will never happen. */
    private static final long serialVersionUID = -4243405131202059043L;

    private final transient ParseTreeModel parseTreeModel;
    private final JTextArea jTextArea;
    private File lastDirectory;
    private File currentFile;
    private final Action reloadAction;
    private final List<Integer>   lines2position  = new ArrayList<>();

    /**
     * Create a new ParseTreeInfoPanel instance.
     */
    public ParseTreeInfoPanel() {
        setLayout(new BorderLayout());

        parseTreeModel = new ParseTreeModel(null);
        final JTreeTable treeTable = new JTreeTable(parseTreeModel);
        final JScrollPane sp = new JScrollPane(treeTable);
        add(sp, BorderLayout.NORTH);

        final JButton fileSelectionButton =
            new JButton(new FileSelectionAction());

        reloadAction = new ReloadAction();
        reloadAction.setEnabled(false);
        final JButton reloadButton = new JButton(reloadAction);

        jTextArea = new JTextArea(20, 15);
        jTextArea.setEditable(false);
        treeTable.setEditor(jTextArea);
        treeTable.setLinePositionMap(lines2position);

        final JScrollPane sp2 = new JScrollPane(jTextArea);
        add(sp2, BorderLayout.CENTER);

        final JPanel p = new JPanel(new GridLayout(1, 2));
        add(p, BorderLayout.SOUTH);
        p.add(fileSelectionButton);
        p.add(reloadButton);

        try {
            new FileDrop(sp, new FileDropListener(sp));
        }
        catch (final TooManyListenersException ignored) {
            showErrorDialog(null, "Cannot initialize Drag and Drop support");
        }

    }

    public void openAst(DetailAST parseTree, final Component parent) {
        parseTreeModel.setParseTree(parseTree);
        reloadAction.setEnabled(true);

        // clear for each new file
        getLines2position().clear();
        // starts line counting at 1
        getLines2position().add(0);
        // insert the contents of the file to the text area

        // clean the text area before inserting the lines of the new file
        if (!jTextArea.getText().isEmpty()) {
            jTextArea.replaceRange("", 0, jTextArea.getText().length());
        }

        // move back to the top of the file
        jTextArea.moveCaretPosition(0);
    }

    public void openFile(File file, final Component parent) {
        if (file != null) {
            try {
                Main.getFrame().setTitle("Checkstyle : " + file.getName());
                final FileText text = new FileText(file.getAbsoluteFile(),
                                                   getEncoding());
                final DetailAST parseTree = parseFile(text);
                parseTreeModel.setParseTree(parseTree);
                currentFile = file;
                lastDirectory = file.getParentFile();
                reloadAction.setEnabled(true);

                final String[] sourceLines = text.toLinesArray();

                // clear for each new file
                getLines2position().clear();
                // starts line counting at 1
                getLines2position().add(0);
                // insert the contents of the file to the text area
                for (String element : sourceLines) {
                    getLines2position().add(jTextArea.getText().length());
                    jTextArea.append(element + "\n");
                }

                //clean the text area before inserting the lines of the new file
                if (!jTextArea.getText().isEmpty()) {
                    jTextArea.replaceRange("", 0, jTextArea.getText()
                            .length());
                }

                // insert the contents of the file to the text area
                for (final String element : sourceLines) {
                    jTextArea.append(element + "\n");
                }

                // move back to the top of the file
                jTextArea.moveCaretPosition(0);
            }
            catch (final IOException | ANTLRException ex) {
                showErrorDialog(
                        parent,
                        "Could not parse" + file + ": " + ex.getMessage());
            }
        }
    }

    /**
     * Parses a file and returns the parse tree.
     * @param text the file to parse
     * @return the root node of the parse tree
     * @throws ANTLRException if the file is not a Java source
     */
    public static DetailAST parseFile(FileText text)
        throws ANTLRException {
        final FileContents contents = new FileContents(text);
        return TreeWalker.parse(contents);
    }

    /**
     * Returns the configured file encoding.
     * This can be set using the {@code file.encoding} system property.
     * It defaults to UTF-8.
     * @return the configured file encoding
     */
    private static String getEncoding() {
        return System.getProperty("file.encoding", "UTF-8");
    }

    private static void showErrorDialog(final Component parent, final String msg) {
        final Runnable showError = new FrameShower(parent, msg);
        SwingUtilities.invokeLater(showError);
    }

    public List<Integer> getLines2position() {
        return Collections.unmodifiableList(lines2position);
    }

    /**
     * http://findbugs.sourceforge.net/bugDescriptions.html#SW_SWING_METHODS_INVOKED_IN_SWING_THREAD
     */
    private static class FrameShower implements Runnable {
        /**
         * frame
         */
        private final Component parent;

        /**
         * frame
         */
        private final String msg;

        /**
         * contstructor
         */
        public FrameShower(Component parent, final String msg) {
            this.parent = parent;
            this.msg = msg;
        }

        /**
         * display a frame
         */
        @Override
        public void run() {
            JOptionPane.showMessageDialog(parent, msg);
        }
    }

    private static class JavaFileFilter extends FileFilter {
        @Override
        public boolean accept(File file) {
            if (file == null) {
                return false;
            }
            return file.isDirectory() || file.getName().endsWith(".java");
        }

        @Override
        public String getDescription() {
            return "Java Source Code";
        }
    }

    private class FileSelectionAction extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = -1926935338069418119L;

        public FileSelectionAction() {
            super("Select Java File");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            final JFileChooser fc = new JFileChooser(lastDirectory);
            final FileFilter filter = new JavaFileFilter();
            fc.setFileFilter(filter);
            final Component parent =
                SwingUtilities.getRoot(ParseTreeInfoPanel.this);
            fc.showDialog(parent, "Open");
            final File file = fc.getSelectedFile();
            openFile(file, parent);

        }
    }

    private class ReloadAction extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = -1021880396046355863L;

        public ReloadAction() {
            super("Reload Java File");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            final Component parent =
                SwingUtilities.getRoot(ParseTreeInfoPanel.this);
            openFile(currentFile, parent);
        }
    }

    private class FileDropListener implements FileDrop.Listener {
        private final JScrollPane mSp;

        @Override
        public void filesDropped(File... files) {
            if (files != null && files.length > 0) {
                final File file = files[0];
                openFile(file, mSp);
            }
        }

        public FileDropListener(JScrollPane aSp) {
            mSp = aSp;
        }
    }
}
