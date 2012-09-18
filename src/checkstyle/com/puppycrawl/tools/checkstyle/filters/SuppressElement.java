////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * This filter processes {@link com.puppycrawl.tools.checkstyle.api.AuditEvent}
 * objects based on the criteria of file, check, module id, line, and
 * column. It rejects an AuditEvent if the following match:
 * <ul>
 *   <li>the event's file name; and
 *   <li>the check name or the module identifier; and
 *   <li>(optionally) the event's line is in the filter's line CSV; and
 *   <li>(optionally) the check's columns is in the filter's column CSV.
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
    private final Pattern mFileRegexp;

    /** the pattern for file names*/
    private final String mFilePattern;

    /** the regexp to match check names against */
    private Pattern mCheckRegexp;

    /** the pattern for check class names*/
    private String mCheckPattern;

    /** module id filter. */
    private String mModuleId;

    /** line number filter */
    private CSVFilter mLineFilter;

    /** CSV for line number filter */
    private String mLinesCSV;

    /** column number filter */
    private CSVFilter mColumnFilter;

    /** CSV for column number filter */
    private String mColumnsCSV;

    /**
     * Constructs a <code>SuppressElement</code> for a
     * file name pattern. Must either call {@link #setColumns(String)} or
     * {@link #setModuleId(String)} before using this object.
     * @param aFiles regular expression for names of filtered files.
     * @throws PatternSyntaxException if there is an error.
     */
    public SuppressElement(String aFiles)
        throws PatternSyntaxException
    {
        mFilePattern = aFiles;
        mFileRegexp = Utils.getPattern(aFiles);
    }

    /**
     * Set the check class pattern.
     * @param aChecks regular expression for filtered check classes.
     */
    public void setChecks(final String aChecks)
    {
        mCheckPattern = aChecks;
        mCheckRegexp = Utils.getPattern(aChecks);
    }

    /**
     * Set the module id for filtering. Cannot be null.
     * @param aModuleId the id
     */
    public void setModuleId(final String aModuleId)
    {
        mModuleId = aModuleId;
    }
    /**
     * Sets the CSV values and ranges for line number filtering.
     * E.g. "1,7-15,18".
     * @param aLines CSV values and ranges for line number filtering.
     */
    public void setLines(String aLines)
    {
        mLinesCSV = aLines;
        if (aLines != null) {
            mLineFilter = new CSVFilter(aLines);
        }
        else {
            mLineFilter = null;
        }
    }

    /**
     * Sets the CSV values and ranges for column number filtering.
     *  E.g. "1,7-15,18".
     * @param aColumns CSV values and ranges for column number filtering.
     */
    public void setColumns(String aColumns)
    {
        mColumnsCSV = aColumns;
        if (aColumns != null) {
            mColumnFilter = new CSVFilter(aColumns);
        }
        else {
            mColumnFilter = null;
        }
    }

    /** {@inheritDoc} */
    public boolean accept(AuditEvent aEvent)
    {
        // file and check match?
        if ((aEvent.getFileName() == null)
                || !mFileRegexp.matcher(aEvent.getFileName()).find()
                || (aEvent.getLocalizedMessage() == null)
                || ((mModuleId != null) && !mModuleId.equals(aEvent
                        .getModuleId()))
                || ((mCheckRegexp != null) && !mCheckRegexp.matcher(
                        aEvent.getSourceName()).find()))
        {
            return true;
        }

        // reject if no line/column matching
        if ((mLineFilter == null) && (mColumnFilter == null)) {
            return false;
        }

        // reject if line matches a line CSV value.
        if (mLineFilter != null) {
            if (mLineFilter.accept(aEvent.getLine())) {
                return false;
            }
        }

        // reject if column matches a column CSV value.
        if (mColumnFilter != null) {
            if (mColumnFilter.accept(aEvent.getColumn())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "SupressElement[files=" + mFilePattern + ",checks="
            + mCheckPattern + ",lines=" + mLinesCSV + ",columns="
            + mColumnsCSV + "]";
    }

    @Override
    public int hashCode()
    {
        int result = HASH_MULT * mFilePattern.hashCode();
        if (mCheckPattern != null) {
            result = HASH_MULT * result + mCheckPattern.hashCode();
        }
        if (mModuleId != null) {
            result = HASH_MULT * result + mModuleId.hashCode();
        }
        if (mLinesCSV != null) {
            result = HASH_MULT * result + mLinesCSV.hashCode();
        }
        if (mColumnsCSV != null) {
            result = HASH_MULT * result + mColumnsCSV.hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(Object aObject)
    {
        if (aObject instanceof SuppressElement) {
            final SuppressElement other = (SuppressElement) aObject;

            // same file pattern?
            if (!this.mFilePattern.equals(other.mFilePattern)) {
                return false;
            }

            // same check pattern?
            if (mCheckPattern != null) {
                if (!mCheckPattern.equals(other.mCheckPattern)) {
                    return false;
                }
            }
            else if (other.mCheckPattern != null) {
                return false;
            }

            // same module id?
            if (mModuleId != null) {
                if (!mModuleId.equals(other.mModuleId)) {
                    return false;
                }
            }
            else if (other.mModuleId != null) {
                return false;
            }

            // same line number filter?
            if (mLineFilter != null) {
                if (!mLineFilter.equals(other.mLineFilter)) {
                    return false;
                }
            }
            else if (other.mLineFilter != null) {
                return false;
            }

            // same column number filter?
            if (mColumnFilter != null) {
                if (!mColumnFilter.equals(other.mColumnFilter)) {
                    return false;
                }
            }
            else if (other.mColumnFilter != null) {
                return false;
            }

            // everything is the same
            return true;
        }
        return false;
    }
}
