////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * <p>
 * This filter accepts AuditEvents according to file, check, line, and
 * column conditions. It rejects an AuditEvent if the event's file
 * name and check name match the filter's file name and check name
 * patterns, and the event's line is in the filter's line CSV or the
 * check's columns is in the filter's column CSV.
 * </p>
 * @author Rick Giles
 */
public class SuppressElement
    implements Filter
{
    /** hash function multiplicand */
    private static final int HASH_MULT = 29;

    /** the regexp to match file names against */
    private Pattern mFileRegexp;

    /** the pattern for file names*/
    private String mFilePattern;

    /** the regexp to match check names against */
    private Pattern mCheckRegexp;

    /** the pattern for check class names*/
    private String mCheckPattern;

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
     * file name pattern and and a check class pattern.
     * @param aFiles regular expression for names of filtered files.
     * @param aChecks regular expression for filtered check classes.
     * @throws PatternSyntaxException if there is an error.
     */
    public SuppressElement(String aFiles, String aChecks)
        throws PatternSyntaxException
    {
        mFilePattern = aFiles;
        mFileRegexp = Utils.getPattern(aFiles);
        mCheckPattern = aChecks;
        mCheckRegexp = Utils.getPattern(aChecks);
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

    /** @see com.puppycrawl.tools.checkstyle.api.Filter */
    public boolean accept(AuditEvent aEvent)
    {
        // file and check match?
        if ((aEvent.getFileName() == null)
            || !mFileRegexp.matcher(aEvent.getFileName()).find()
            || (aEvent.getLocalizedMessage() == null)
            || !mCheckRegexp.matcher(aEvent.getSourceName()).find())
        {
            return true;
        }

        // reject if no line/column matching
        if ((mLineFilter == null) && (mColumnFilter == null)) {
            return false;
        }

        // reject if line matches a line CSV value.
        if (mLineFilter != null) {
            final Integer line = new Integer(aEvent.getLine());
            if (mLineFilter.accept(line)) {
                return false;
            }
        }

        // reject if column matches a column CSV value.
        if (mColumnFilter != null) {
            final Integer column = new Integer(aEvent.getColumn());
            if (mColumnFilter.accept(column)) {
                return false;
            }
        }
        return true;
    }

    /** @see java.lang.Object#toString() */
    public String toString()
    {
        return "SupressElement[files=" + mFilePattern + ",checks="
            + mCheckPattern + ",lines=" + mLinesCSV + ",columns="
            + mColumnsCSV + "]";
    }

    /** @see java.lang.Object#hashCode() */
    public int hashCode()
    {
        int result = HASH_MULT * mFilePattern.hashCode()
            + mCheckPattern.hashCode();
        if (mLinesCSV != null) {
            result = HASH_MULT * result + mLinesCSV.hashCode();
        }
        if (mColumnsCSV != null) {
            result = HASH_MULT * result + mColumnsCSV.hashCode();
        }
        return result;
    }

    /** @see java.lang.Object#equals(java.lang.Object) */
    public boolean equals(Object aObject)
    {
        if (aObject instanceof SuppressElement) {
            final SuppressElement other = (SuppressElement) aObject;

            // same file pattern?
            if (!this.mFilePattern.equals(other.mFilePattern)) {
                return false;
            }

            // same check pattern?
            if (!this.mCheckPattern.equals(other.mCheckPattern)) {
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
