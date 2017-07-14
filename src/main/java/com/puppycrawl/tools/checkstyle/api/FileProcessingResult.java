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

package com.puppycrawl.tools.checkstyle.api;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Represents the file processing results.
 *
 * @author Timur Tibeyev
 */
public class FileProcessingResult {
    /** The contents of the file. */
    private final FileText fileText;

    /** The sorted set of messages. */
    private final SortedSet<LocalizedMessage> messages;

    /** The file path. */
    private final String filePath;

    /**
     * Creates a new {@code FileProcessingResult} instance.
     *
     * @param filePath absolute path to the file
     * @param fileText the contents of the file
     * @param messages the sorted set of messages
     */
    public FileProcessingResult(String filePath, FileText fileText,
                                SortedSet<LocalizedMessage> messages) {
        this.filePath = filePath;
        if (fileText == null) {
            this.fileText = null;
        }
        else {
            this.fileText = new FileText(fileText);
        }
        final SortedSet<LocalizedMessage> validCopy = new TreeSet<>(messages);
        this.messages = Collections.unmodifiableSortedSet(validCopy);
    }

    /**
     * Returns the contents of the file.
     * @return an object containing the full text of the file
     */
    public FileText getFileText() {
        return fileText;
    }

    /**
     * Returns the sorted set of messages.
     * @return the sorted set of messages
     */
    public SortedSet<LocalizedMessage> getMessages() {
        final SortedSet<LocalizedMessage> validCopy = new TreeSet<>(messages);
        return Collections.unmodifiableSortedSet(validCopy);
    }

    /**
     * Returns the file path.
     * @return the file path
     */
    public String getFilePath() {
        return filePath;
    }
}
