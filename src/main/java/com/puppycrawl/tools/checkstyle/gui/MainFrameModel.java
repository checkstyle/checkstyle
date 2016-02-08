////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import antlr.ANTLRException;

import com.google.common.collect.ImmutableList;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * Model for checkstyle frame.
 * @author Vladislav Lisetskiy
 */
public class MainFrameModel {

    /** Lines to position map. */
    private final List<Integer> linesToPosition = new ArrayList<>();

    /** Parse tree model. */
    private final ParseTreeTableModel parseTreeTableModel;

    /** The file which is being parsed. */
    private File currentFile;

    /** Text for a frame's text area. */
    private String text;

    /** Title for the main frame. */
    private String title = "Checkstyle GUI";

    /** Whether the reload action is enabled. */
    private boolean reloadActionEnabled;

    /** Instantiate the model. */
    public MainFrameModel() {
        parseTreeTableModel = new ParseTreeTableModel(null);
    }

    /**
     * Get parse tree table model.
     * @return parse tree table model.
     */
    public ParseTreeTableModel getParseTreeTableModel() {
        return parseTreeTableModel;
    }

    /**
     * Get text to display in a text area.
     * @return text to display in a text area.
     */
    public String getText() {
        return text;
    }

    /**
     * @return title for the main frame.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return true if the reload action is enabled.
     */
    public boolean isReloadActionEnabled() {
        return reloadActionEnabled;
    }

    /**
     * Whether a file chooser should accept the file as a source file.
     * @param file the file to check.
     * @return true if the file should be accepted.
     */
    public static boolean shouldAcceptFile(File file) {
        return file.isDirectory() || file.getName().endsWith(".java");
    }

    /**
     * Get the directory of the last loaded file.
     * @return directory of the last loaded file.
     */
    public File getLastDirectory() {
        File lastDirectory = null;
        if (currentFile != null) {
            lastDirectory = new File(currentFile.getParent());
        }
        return lastDirectory;
    }

    /**
     * Get current file.
     * @return current file.
     */
    public File getCurrentFile() {
        return currentFile;
    }

    /**
     * Get lines to position map.
     * @return lines to position map.
     */
    public List<Integer> getLinesToPosition() {
        return ImmutableList.copyOf(linesToPosition);
    }

    /**
     * Open file and load the file.
     * @param file the file to open.
     * @throws CheckstyleException if the file can not be parsed.
     */
    public void openFile(File file) throws CheckstyleException {
        if (file != null) {
            try {
                currentFile = file;
                title = "Checkstyle GUI : " + file.getName();
                reloadActionEnabled = true;
                final DetailAST parseTree = parseFile(file);
                parseTreeTableModel.setParseTree(parseTree);
                final String[] sourceLines = getFileText(file).toLinesArray();

                // clear for each new file
                linesToPosition.clear();
                // starts line counting at 1
                linesToPosition.add(0);

                final StringBuilder sb = new StringBuilder();
                // insert the contents of the file to the text area
                for (final String element : sourceLines) {
                    linesToPosition.add(sb.length());
                    sb.append(element).append(System.lineSeparator());
                }
                text = sb.toString();
            }
            catch (IOException | ANTLRException ex) {
                final String exceptionMsg = String.format(Locale.ROOT,
                    "%s occurred while opening file %s.",
                    ex.getClass().getSimpleName(), file.getPath());
                throw new CheckstyleException(exceptionMsg, ex);
            }
        }
    }

    /**
     * Parse a file and return the parse tree.
     * @param file the file to parse.
     * @return the root node of the parse tree.
     * @throws IOException if the file could not be read.
     * @throws ANTLRException if the file is not a Java source.
     */
    public DetailAST parseFile(File file) throws IOException, ANTLRException {
        final FileText fileText = getFileText(file);
        final FileContents contents = new FileContents(fileText);
        return TreeWalker.parse(contents);
    }

    /**
     * Get FileText from a file.
     * @param file the file to get the FileText from.
     * @return the FileText.
     * @throws IOException if the file could not be read.
     */
    public FileText getFileText(File file) throws IOException {
        return new FileText(file.getAbsoluteFile(),
                System.getProperty("file.encoding", "UTF-8"));
    }
}
