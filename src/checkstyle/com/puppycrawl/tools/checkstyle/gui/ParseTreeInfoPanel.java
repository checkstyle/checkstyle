////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
import antlr.ANTLRException;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * Displays information about a parse tree.
 * The user can change the file that is parsed and displayed
 * through a JFileChooser.
 *
 * @author Lars Kühne
 */
public class ParseTreeInfoPanel extends JPanel
{
    /** For Serialisation that will never happen. */
    private static final long serialVersionUID = -4243405131202059043L;
    private final JTreeTable mTreeTable;
    private final ParseTreeModel mParseTreeModel;
    private final JTextArea mJTextArea;
    private File mLastDirectory = null;
    private File mCurrentFile = null;
    private final Action reloadAction;

    private static class JavaFileFilter extends FileFilter
    {
        @Override
        public boolean accept(File f)
        {
            if (f == null) {
                return false;
            }
            return f.isDirectory() || f.getName().endsWith(".java");
        }

        @Override
        public String getDescription()
        {
            return "Java Source Code";
        }
    }

    private class FileSelectionAction extends AbstractAction
    {
        /**
         *
         */
        private static final long serialVersionUID = -1926935338069418119L;

        public FileSelectionAction()
        {
            super("Select Java File");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        }

        public void actionPerformed(ActionEvent e)
        {
            final JFileChooser fc = new JFileChooser( mLastDirectory );
            final FileFilter filter = new JavaFileFilter();
            fc.setFileFilter(filter);
            final Component parent =
                SwingUtilities.getRoot(ParseTreeInfoPanel.this);
            fc.showDialog(parent, "Open");
            final File file = fc.getSelectedFile();
            openFile(file, parent);

        }
    }

    private class ReloadAction extends AbstractAction
    {
        /**
         *
         */
        private static final long serialVersionUID = -1021880396046355863L;

        public ReloadAction()
        {
            super("Reload Java File");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
        }

        public void actionPerformed(ActionEvent e)
        {
            final Component parent =
                SwingUtilities.getRoot(ParseTreeInfoPanel.this);
            openFile(mCurrentFile, parent);
        }
    }


    private class FileDropListener implements FileDrop.Listener
    {
        private final JScrollPane mSp;

        public void filesDropped(File[] files)
        {
            if ((files != null) && (files.length > 0))
            {
                final File file = files[0];
                openFile(file, mSp);
            }
        }

        public FileDropListener(JScrollPane aSp)
        {
            mSp = aSp;
        }
    }


    public void openFile(File aFile, final Component aParent)
    {
        if (aFile != null) {
            try {
                Main.frame.setTitle("Checkstyle : " + aFile.getName());
                final FileText text = new FileText(aFile.getAbsoluteFile(),
                                                   getEncoding());
                final DetailAST parseTree = parseFile(text);
                mParseTreeModel.setParseTree(parseTree);
                mCurrentFile = aFile;
                mLastDirectory = aFile.getParentFile();
                reloadAction.setEnabled(true);

                final String[] sourceLines = text.toLinesArray();
                //clean the text area before inserting the lines of the new file
                if (mJTextArea.getText().length() != 0) {
                    mJTextArea.replaceRange("", 0, mJTextArea.getText()
                            .length());
                }

                // insert the contents of the file to the text area
                for (final String element : sourceLines) {
                    mJTextArea.append(element + "\n");
                }

                // move back to the top of the file
                mJTextArea.moveCaretPosition(0);
            }
            catch (final IOException ex) {
                showErrorDialog(
                        aParent,
                        "Could not open " + aFile + ": " + ex.getMessage());
            }
            catch (final ANTLRException ex) {
                showErrorDialog(
                        aParent,
                        "Could not parse " + aFile + ": " + ex.getMessage());
            }
        }
    }

    /**
     * Parses a file and returns the parse tree.
     * @param aFileName the file to parse
     * @return the root node of the parse tree
     * @throws IOException if the file cannot be opened
     * @throws ANTLRException if the file is not a Java source
     * @deprecated Use {@link #parseFile(FileText)} instead
     */
    @Deprecated
    public static DetailAST parseFile(String aFileName)
        throws IOException, ANTLRException
    {
        return parseFile(new FileText(new File(aFileName), getEncoding()));
    }

    /**
     * Parses a file and returns the parse tree.
     * @param aText the file to parse
     * @return the root node of the parse tree
     * @throws IOException if the file cannot be opened
     * @throws ANTLRException if the file is not a Java source
     */
    public static DetailAST parseFile(FileText aText)
        throws ANTLRException
    {
        final FileContents contents = new FileContents(aText);
        return TreeWalker.parse(contents);
    }

    /**
     * Returns the configured file encoding.
     * This can be set using the {@code file.encoding} system property.
     * It defaults to UTF-8.
     * @return the configured file encoding
     */
    private static String getEncoding()
    {
        return System.getProperty("file.encoding", "UTF-8");
    }

    /**
     * Create a new ParseTreeInfoPanel instance.
     */
    public ParseTreeInfoPanel()
    {
        setLayout(new BorderLayout());

        final DetailAST treeRoot = null;
        mParseTreeModel = new ParseTreeModel(treeRoot);
        mTreeTable = new JTreeTable(mParseTreeModel);
        final JScrollPane sp = new JScrollPane(mTreeTable);
        this.add(sp, BorderLayout.NORTH);

        final JButton fileSelectionButton =
            new JButton(new FileSelectionAction());

        reloadAction = new ReloadAction();
        reloadAction.setEnabled(false);
        final JButton reloadButton = new JButton(reloadAction);

        mJTextArea = new JTextArea(20, 15);
        mJTextArea.setEditable(false);

        final JScrollPane sp2 = new JScrollPane(mJTextArea);
        this.add(sp2, BorderLayout.CENTER);

        final JPanel p = new JPanel(new GridLayout(1,2));
        this.add(p, BorderLayout.SOUTH);
        p.add(fileSelectionButton);
        p.add(reloadButton);

        try {
            // TODO: creating an object for the side effect of the constructor
            // and then ignoring the object looks strange.
            new FileDrop(sp, new FileDropListener(sp));
        }
        catch (final TooManyListenersException ex)
        {
           showErrorDialog(null, "Cannot initialize Drag and Drop support");
        }

    }

    private void showErrorDialog(final Component parent, final String msg)
    {
        final Runnable showError = new Runnable()
        {
            public void run()
            {
                JOptionPane.showMessageDialog(parent, msg);
            }
        };
        SwingUtilities.invokeLater(showError);
    }

}

