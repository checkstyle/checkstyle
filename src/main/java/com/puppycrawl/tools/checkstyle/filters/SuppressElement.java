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

package com.puppycrawl.tools.checkstyle.filters;

import java.util.Objects;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * This filter processes {@link AuditEvent}
 * objects based on the criteria of file, check, module id, line, and
 * column. It rejects an AuditEvent if the following match:
 * <ul>
 *   <li>the event's file name; and</li>
 *   <li>the check name or the module identifier; and</li>
 *   <li>(optionally) the event's line is in the filter's line CSV; and</li>
 *   <li>(optionally) the check's columns is in the filter's column CSV.</li>
 * </ul>
 *
 * @author Rick Giles
 */
public class SuppressElement
    implements Filter {
    /** The regexp to match file names against. */
    private final Pattern fileRegexp;

    /** The pattern for file names. */
    private final String filePattern;

    /** The regexp to match check names against. */
    private Pattern checkRegexp;

    /** The pattern for check class names. */
    private String checkPattern;

    /** Module id filter. */
    private String moduleId;

    /** Line number filter. */
    private CsvFilter lineFilter;

    /** CSV for line number filter. */
    private String linesCsv;

    /** Column number filter. */
    private CsvFilter columnFilter;

    /** CSV for column number filter. */
    private String columnsCsv;

    /**
     * Constructs a {@code SuppressElement} for a
     * file name pattern. Must either call {@link #setColumns(String)} or
     * {@link #setModuleId(String)} before using this object.
     * @param files regular expression for names of filtered files.
     */
    public SuppressElement(String files) {
        filePattern = files;
        fileRegexp = Pattern.compile(files);
    }

    /**
     * Set the check class pattern.
     * @param checks regular expression for filtered check classes.
     */
    public void setChecks(final String checks) {
        checkPattern = checks;
        checkRegexp = CommonUtils.createPattern(checks);
    }

    /**
     * Set the module id for filtering. Cannot be null.
     * @param moduleId the id
     */
    public void setModuleId(final String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * Sets the CSV values and ranges for line number filtering.
     * E.g. "1,7-15,18".
     * @param lines CSV values and ranges for line number filtering.
     */
    public void setLines(String lines) {
        linesCsv = lines;
        if (lines == null) {
            lineFilter = null;
        }
        else {
            lineFilter = new CsvFilter(lines);
        }
    }

    /**
     * Sets the CSV values and ranges for column number filtering.
     *  E.g. "1,7-15,18".
     * @param columns CSV values and ranges for column number filtering.
     */
    public void setColumns(String columns) {
        columnsCsv = columns;
        if (columns == null) {
            columnFilter = null;
        }
        else {
            columnFilter = new CsvFilter(columns);
        }
    }

    @Override
    public boolean accept(AuditEvent event) {
        // reject if file or check module mismatch?
        if (isFileNameAndModuleNotMatching(event)) {
            return true;
        }

        // reject if no line/column matching
        return (lineFilter != null || columnFilter != null)
                && (lineFilter == null || !lineFilter.accept(event.getLine()))
                && (columnFilter == null || !columnFilter.accept(event.getColumn()));
    }

    /**
     * Is matching by file name and Check name.
     * @param event event
     * @return true is matching
     */
    private boolean isFileNameAndModuleNotMatching(AuditEvent event) {
        return event.getFileName() == null
                || !fileRegexp.matcher(event.getFileName()).find()
                || event.getLocalizedMessage() == null
                || moduleId != null && !moduleId.equals(event.getModuleId())
                || checkRegexp != null && !checkRegexp.matcher(event.getSourceName()).find();
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePattern, checkPattern, moduleId, linesCsv, columnsCsv);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final SuppressElement suppressElement = (SuppressElement) other;
        return Objects.equals(filePattern, suppressElement.filePattern)
                && Objects.equals(checkPattern, suppressElement.checkPattern)
                && Objects.equals(moduleId, suppressElement.moduleId)
                && Objects.equals(linesCsv, suppressElement.linesCsv)
                && Objects.equals(columnsCsv, suppressElement.columnsCsv);
    }
}
