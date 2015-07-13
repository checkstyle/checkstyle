////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Filter;
import org.apache.commons.beanutils.ConversionException;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * This filter processes {@link com.puppycrawl.tools.checkstyle.api.AuditEvent}
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
    /** the regexp to match file names against */
    private final transient Pattern fileRegexp;

    /** the pattern for file names*/
    private final String filePattern;

    /** the regexp to match check names against */
    private transient Pattern checkRegexp;

    /** the pattern for check class names*/
    private String checkPattern;

    /** module id filter. */
    private String moduleId;

    /** line number filter */
    private transient CSVFilter lineFilter;

    /** CSV for line number filter */
    private String linesCSV;

    /** column number filter */
    private transient CSVFilter columnFilter;

    /** CSV for column number filter */
    private String columnsCSV;

    /**
     * Constructs a <code>SuppressElement</code> for a
     * file name pattern. Must either call {@link #setColumns(String)} or
     * {@link #setModuleId(String)} before using this object.
     * @param files regular expression for names of filtered files.
     * @throws ConversionException if unable to create Pattern object.
     */
    public SuppressElement(String files)
        throws ConversionException {
        filePattern = files;
        fileRegexp = Pattern.compile(files);
    }

    /**
     * Set the check class pattern.
     * @param checks regular expression for filtered check classes.
     * @throws ConversionException if unable to create Pattern object
     */
    public void setChecks(final String checks)
        throws ConversionException {
        checkPattern = checks;
        checkRegexp = Utils.createPattern(checks);
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
        linesCSV = lines;
        if (lines != null) {
            lineFilter = new CSVFilter(lines);
        }
        else {
            lineFilter = null;
        }
    }

    /**
     * Sets the CSV values and ranges for column number filtering.
     *  E.g. "1,7-15,18".
     * @param columns CSV values and ranges for column number filtering.
     */
    public void setColumns(String columns) {
        columnsCSV = columns;
        if (columns != null) {
            columnFilter = new CSVFilter(columns);
        }
        else {
            columnFilter = null;
        }
    }

    /** {@inheritDoc} */
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
     * is matching by file name and Check name
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
        return Objects.hash(filePattern, checkPattern, moduleId, linesCSV, columnsCSV);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SuppressElement that = (SuppressElement) o;
        return Objects.equals(filePattern, that.filePattern)
                && Objects.equals(checkPattern, that.checkPattern)
                && Objects.equals(moduleId, that.moduleId)
                && Objects.equals(linesCSV, that.linesCSV)
                && Objects.equals(columnsCSV, that.columnsCSV);
    }
}
