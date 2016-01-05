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

package com.puppycrawl.tools.checkstyle.api;

import java.util.EventObject;

/**
 * Raw event for audit.
 * <p>
 * <i>
 * I'm not very satisfied about the design of this event since there are
 * optional methods that will return null in most of the case. This will
 * need some work to clean it up especially if we want to introduce
 * a more sequential reporting action rather than a packet error
 * reporting. This will allow for example to follow the process quickly
 * in an interface or a servlet (yep, that's cool to run a check via
 * a web interface in a source repository ;-)
 * </i>
 * </p>
 *
 * @author <a href="mailto:stephane.bailliez@wanadoo.fr">Stephane Bailliez</a>
 * @see AuditListener
 */
public final class AuditEvent
    extends EventObject {
    /** Record a version. */
    private static final long serialVersionUID = -3774725606973812736L;
    /** Filename event associated with. **/
    private final String fileName;
    /** Message associated with the event. **/
    private final LocalizedMessage localizedMessage;

    /**
     * Creates a new instance.
     * @param source the object that created the event
     */
    public AuditEvent(Object source) {
        this(source, null);
    }

    /**
     * Creates a new {@code AuditEvent} instance.
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
     * @param localizedMessage the actual message
     */
    public AuditEvent(Object src, String fileName, LocalizedMessage localizedMessage) {
        super(src);
        this.fileName = fileName;
        this.localizedMessage = localizedMessage;
    }

    /**
     * @return the file name currently being audited or null if there is
     *     no relation to a file.
     */
    public String getFileName() {
        return fileName;
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
     * Gets the audit event severity level.
     * @return the audit event severity level
     */
    public SeverityLevel getSeverityLevel() {
        if (localizedMessage == null) {
            return SeverityLevel.INFO;
        }
        else {
            return localizedMessage.getSeverityLevel();
        }
    }

    /**
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

    /**
     * Gets the localized message.
     * @return the localized message
     */
    public LocalizedMessage getLocalizedMessage() {
        return localizedMessage;
    }
}
