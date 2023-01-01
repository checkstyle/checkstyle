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

package com.puppycrawl.tools.checkstyle.api;

import java.util.EventObject;

/**
 * Raw event for audit.
 * <p>
 * <i>
 * I'm not very satisfied about the design of this event since there are
 * optional methods that will return null in most of the case. This will
 * need some work to clean it up especially if we want to introduce
 * a more sequential reporting action rather than a packet
 * reporting. This will allow for example to follow the process quickly
 * in an interface or a servlet (yep, that's cool to run a check via
 * a web interface in a source repository ;-)
 * </i>
 * </p>
 *
 * @see AuditListener
 */
public final class AuditEvent
    extends EventObject {

    /** Record a version. */
    private static final long serialVersionUID = -3774725606973812736L;
    /** Filename event associated with. **/
    private final String fileName;
    /** Violation associated with the event. **/
    private final Violation violation;

    /**
     * Creates a new instance.
     *
     * @param source the object that created the event
     */
    public AuditEvent(Object source) {
        this(source, null);
    }

    /**
     * Creates a new {@code AuditEvent} instance.
     *
     * @param src source of the event
     * @param fileName file associated with the event
     */
    public AuditEvent(Object src, String fileName) {
        this(src, fileName, null);
    }

    /**
     * Creates a new {@code AuditEvent} instance.
     *
     * @param src source of the event
     * @param fileName file associated with the event
     * @param violation the actual violation
     */
    public AuditEvent(Object src, String fileName, Violation violation) {
        super(src);
        this.fileName = fileName;
        this.violation = violation;
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
     * @return the event violation
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
     * Gets the audit event severity level.
     *
     * @return the audit event severity level
     */
    public SeverityLevel getSeverityLevel() {
        SeverityLevel severityLevel = SeverityLevel.INFO;
        if (violation != null) {
            severityLevel = violation.getSeverityLevel();
        }
        return severityLevel;
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
     * Gets the violation.
     *
     * @return the violation
     */
    public Violation getViolation() {
        return violation;
    }

}
