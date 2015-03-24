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

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.Utils;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConversionException;

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
    implements Filter
{
    /** hash function multiplicand */
    private static final int HASH_MULT = 29;

    /** the regexp to match file names against */
    private final Pattern fileRegexp;

    /** the pattern for file names*/
    private final String filePattern;

    /** the regexp to match check names against */
    private Pattern checkRegexp;

    /** the pattern for check class names*/
    private String checkPattern;

    /** module id filter. */
    private String moduleId;

    /** line number filter */
    private CSVFilter lineFilter;

    /** CSV for line number filter */
    private String linesCSV;

    /** column number filter */
    private CSVFilter columnFilter;

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
        throws ConversionException
    {
        filePattern = files;
        fileRegexp = Pattern.compile(files);
    }

    /**
     * Set the check class pattern.
     * @param checks regular expression for filtered check classes.
     * @throws ConversionException if unable to create Pattern object
     */
    public void setChecks(final String checks)
        throws ConversionException
    {
        checkPattern = checks;
        checkRegexp = Utils.createPattern(checks);
    }

    /**
     * Set the module id for filtering. Cannot be null.
     * @param moduleId the id
     */
    public void setModuleId(final String moduleId)
    {
        this.moduleId = moduleId;
    }
    /**
     * Sets the CSV values and ranges for line number filtering.
     * E.g. "1,7-15,18".
     * @param lines CSV values and ranges for line number filtering.
     */
    public void setLines(String lines)
    {
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
    public void setColumns(String columns)
    {
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
    public boolean accept(AuditEvent event)
    {
        // file and check match?
        if (event.getFileName() == null
                || !fileRegexp.matcher(event.getFileName()).find()
                || event.getLocalizedMessage() == null
                || moduleId != null && !moduleId.equals(event
                        .getModuleId())
                || checkRegexp != null && !checkRegexp.matcher(
                        event.getSourceName()).find())
        {
            return true;
        }

        // reject if no line/column matching
        if (lineFilter == null && columnFilter == null) {
            return false;
        }

        if (lineFilter != null && lineFilter.accept(event.getLine())) {
            return false;
        }

        if (columnFilter != null && columnFilter.accept(event.getColumn())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "SuppressElement[files=" + filePattern + ",checks="
            + checkPattern + ",lines=" + linesCSV + ",columns="
            + columnsCSV + "]";
    }

    @Override
    public int hashCode()
    {
        int result = HASH_MULT * filePattern.hashCode();
        if (checkPattern != null) {
            result = HASH_MULT * result + checkPattern.hashCode();
        }
        if (moduleId != null) {
            result = HASH_MULT * result + moduleId.hashCode();
        }
        if (linesCSV != null) {
            result = HASH_MULT * result + linesCSV.hashCode();
        }
        if (columnsCSV != null) {
            result = HASH_MULT * result + columnsCSV.hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof SuppressElement) {
            final SuppressElement other = (SuppressElement) object;

            // same file pattern?
            if (!this.filePattern.equals(other.filePattern)) {
                return false;
            }

            // same check pattern?
            if (checkPattern != null) {
                if (!checkPattern.equals(other.checkPattern)) {
                    return false;
                }
            }
            else if (other.checkPattern != null) {
                return false;
            }

            // same module id?
            if (moduleId != null) {
                if (!moduleId.equals(other.moduleId)) {
                    return false;
                }
            }
            else if (other.moduleId != null) {
                return false;
            }

            // same line number filter?
            if (lineFilter != null) {
                if (!lineFilter.equals(other.lineFilter)) {
                    return false;
                }
            }
            else if (other.lineFilter != null) {
                return false;
            }

            // same column number filter?
            if (columnFilter != null) {
                if (!columnFilter.equals(other.columnFilter)) {
                    return false;
                }
            }
            else if (other.columnFilter != null) {
                return false;
            }

            // everything is the same
            return true;
        }
        return false;
    }
}
