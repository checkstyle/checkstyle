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
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import antlr.ANTLRException;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.CommentManager;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * Displays information about a parse tree.
 * The user can change the file that is parsed and displayed
 * through a JFileChooser.
 *
 * @author Lars Kühne
 */
public class ParseTreeInfoPanel extends JPanel
{
    private JTreeTable mTreeTable;
    private ParseTreeModel mParseTreeModel;

    private static class JavaFileFilter extends FileFilter
    {
        public boolean accept(File f)
        {
            if (f == null) {
                return false;
            }
            return f.isDirectory() || f.getName().endsWith(".java");
        }

        public String getDescription()
        {
            return "Java Source Code";
        }
    }

    private class FileSectionAction extends AbstractAction
    {
        public FileSectionAction()
        {
            super("Select Java File");
        }

        public void actionPerformed(ActionEvent e)
        {
            JFileChooser fc = new JFileChooser();
            FileFilter filter = new JavaFileFilter();
            fc.setFileFilter(filter);
            final Component parent = SwingUtilities.getRoot(ParseTreeInfoPanel.this);
            fc.showDialog(parent, "Open");
            File file = fc.getSelectedFile();
            if (file != null) {
                try {
                    DetailAST parseTree = parseFile(file.getAbsolutePath());
                    mParseTreeModel.setParseTree(parseTree);
                }
                catch (IOException ex) {
                    JOptionPane.showMessageDialog(parent,
                            "Could not open " + file + ": " + ex.getMessage());
                }
                catch (ANTLRException ex) {
                    JOptionPane.showMessageDialog(parent,
                            "Could not parse " + file + ": " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Parses a file and returns the parse tree.
     * @param aFileName the file to parse
     * @return the root node of the parse tree
     * @throws IOException if the file cannot be opened
     * @throws ANTLRException if the file is not a Java source
     */
    public static DetailAST parseFile(String aFileName)
        throws IOException, ANTLRException
    {
        final String[] lines = Utils.getLines(aFileName);
        final CommentManager cmgr = new CommentManager(lines);
        return Checker.parse(lines, aFileName, cmgr);
    }

    /**
     * Create a new ParseTreeInfoPanel instance.
     */
    public ParseTreeInfoPanel()
    {
        setLayout(new BorderLayout());

        DetailAST treeRoot = null;
        mParseTreeModel = new ParseTreeModel(treeRoot);
        mTreeTable = new JTreeTable(mParseTreeModel);
        JScrollPane sp = new JScrollPane(mTreeTable);
        this.add(sp, BorderLayout.CENTER);
        final JButton fileSelectionButton = new JButton(new FileSectionAction());

        this.add(fileSelectionButton, BorderLayout.SOUTH);
    }

}
