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

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

/**
 * Raw {@code TreeWalker} event for audit.
 *
 * @author Timur Tibeyev
 */
public class TreeWalkerAuditEvent {
    /** Filename event associated with. **/
    private final String fileName;
    /** The file contents. */
    private final FileContents fileContents;
    /** Message associated with the event. **/
    private final LocalizedMessage localizedMessage;

    /**
     * Creates a new {@code TreeWalkerAuditEvent} instance.
     *
     * @param fileContents contents of the file associated with the event
     * @param fileName file associated with the event
     * @param localizedMessage the actual message
     */
    public TreeWalkerAuditEvent(FileContents fileContents, String fileName,
                                LocalizedMessage localizedMessage) {
        this.fileContents = fileContents;
        this.fileName = fileName;
        this.localizedMessage = localizedMessage;
    }

    /**
     * Returns name of file being audited.
     * @return the file name currently being audited or null if there is
     *     no relation to a file.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns contents of the file.
     * @return contents of the file.
     */
    public FileContents getFileContents() {
        return fileContents;
    }

    /**
     * Gets the localized message.
     * @return the localized message
     */
    public LocalizedMessage getLocalizedMessage() {
        return localizedMessage;
    }

    /**
     * Return the line number on the source file where the event occurred.
     * This may be 0 if there is no relation to a file content.
     * @return an integer representing the line number in the file source code.
     */
    public int getLine() {
        return localizedMessage.getLineNo();
    }

    /**
     * Return the message associated to the event.
     * @return the event message
     */
    public String getMessage() {
        return localizedMessage.getMessage();
    }

    /**
     * Gets the column associated with the message.
     * @return the column associated with the message
     */
    public int getColumn() {
        return localizedMessage.getColumnNo();
    }

    /**
     * Returns id of module.
     * @return the identifier of the module that generated the event. Can return
     *         null.
     */
    public String getModuleId() {
        return localizedMessage.getModuleId();
    }

    /**
     * Gets the name of the source for the message.
     * @return the name of the source for the message
     */
    public String getSourceName() {
        return localizedMessage.getSourceName();
    }
}
