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

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.Violation;

/**
 * Raw {@code TreeWalker} event for audit.
 *
 */
public class TreeWalkerAuditEvent {

    /** Filename event associated with. **/
    private final String fileName;
    /** The file contents. */
    private final FileContents fileContents;
    /** Violation associated with the event. **/
    private final Violation violation;
    /** Root ast element. **/
    private final DetailAST rootAst;

    /**
     * Creates a new {@code TreeWalkerAuditEvent} instance.
     *
     * @param fileContents contents of the file associated with the event
     * @param fileName file associated with the event
     * @param violation the actual violation
     * @param rootAst root AST element {@link DetailAST} of the file
     */
    public TreeWalkerAuditEvent(FileContents fileContents, String fileName,
                                Violation violation, DetailAST rootAst) {
        this.fileContents = fileContents;
        this.fileName = fileName;
        this.violation = violation;
        this.rootAst = rootAst;
    }

    /**
     * Returns name of file being audited.
     *
     * @return the file name currently being audited or null if there is
     *     no relation to a file.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns contents of the file.
     *
     * @return contents of the file.
     */
    public FileContents getFileContents() {
        return fileContents;
    }

    /**
     * Gets the violation.
     *
     * @return the violation
     */
    public Violation getViolation() {
        return violation;
    }

    /**
     * Return the line number on the source file where the event occurred.
     * This may be 0 if there is no relation to a file content.
     *
     * @return an integer representing the line number in the file source code.
     */
    public int getLine() {
        return violation.getLineNo();
    }

    /**
     * Return the violation associated to the event.
     *
     * @return the violation message
     */
    public String getMessage() {
        return violation.getViolation();
    }

    /**
     * Gets the column associated with the violation.
     *
     * @return the column associated with the violation
     */
    public int getColumn() {
        return violation.getColumnNo();
    }

    /**
     * Gets the column char index associated with the violation.
     *
     * @return the column char index associated with the violation
     */
    public int getColumnCharIndex() {
        return violation.getColumnCharIndex();
    }

    /**
     * Returns id of module.
     *
     * @return the identifier of the module that generated the event. Can return
     *         null.
     */
    public String getModuleId() {
        return violation.getModuleId();
    }

    /**
     * Gets the name of the source for the violation.
     *
     * @return the name of the source for the violation
     */
    public String getSourceName() {
        return violation.getSourceName();
    }

    /**
     * Gets the token type of the violation.
     *
     * @return the token type of the violation
     */
    public int getTokenType() {
        return violation.getTokenType();
    }

    /**
     * Gets the root element of the AST tree.
     *
     * @return the root element of the AST tree
     */
    public DetailAST getRootAst() {
        return rootAst;
    }

}
