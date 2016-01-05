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

import antlr.ANTLRException;

import com.google.common.collect.ImmutableList;
import com.puppycrawl.tools.checkstyle.TreeWalker;
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

    /** The file which is being parsed. */
    private File currentFile;

    /**
     * Parse a file and return the parse tree.
     * @param file the file to parse.
     * @return the root node of the parse tree.
     * @throws IOException if the file could not be read.
     * @throws ANTLRException if the file is not a Java source.
     */
    public DetailAST parseFile(File file) throws IOException, ANTLRException {
        currentFile = file;
        final FileText text = getFileText(file);
        final FileContents contents = new FileContents(text);
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
     * Add a new value into lines to position map.
     * @param value Value to be added into position map.
     */
    public void addLineToPosition(int value) {
        linesToPosition.add(value);
    }

    /** Clear lines to position map. */
    public void clearLinesToPosition() {
        linesToPosition.clear();
    }

}
