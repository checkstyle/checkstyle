////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2007  Oliver Burn
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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Severity level for a check violation.
 * <p>
 * Each violation of an audit check is assigned one of the severity levels
 * defined here.
 *
 * @author David Schneider
 */
public final class SeverityLevel implements Comparable, Serializable
{
    /**  Numeric value for severity level IGNORE     */
    private static final int SEVERITYCODE_IGNORE = 10;
    /**  Numeric value for severity level INFO     */
    private static final int SEVERITYCODE_INFO = 20;
    /**  Numeric value for severity level WARNING     */
    private static final int SEVERITYCODE_WARNING = 30;
    /**  Numeric value for severity level ERROR     */
    private static final int SEVERITYCODE_ERROR = 40;


    /**  Name for severity level IGNORE */
    private static final String SEVERITYNAME_IGNORE = "ignore";
    /**  Name for severity level INFO */
    private static final String SEVERITYNAME_INFO = "info";
    /**  Name for severity level WARNING */
    private static final String SEVERITYNAME_WARNING = "warning";
    /**  Name for severity level ERROR */
    private static final String SEVERITYNAME_ERROR = "error";

    /** Severity level: ignore.  This is the lowest severity level.  */
    public static final SeverityLevel IGNORE =
        new SeverityLevel(SEVERITYCODE_IGNORE, SEVERITYNAME_IGNORE);

    /** Severity level: informational.  */
    public static final SeverityLevel INFO =
        new SeverityLevel(SEVERITYCODE_INFO, SEVERITYNAME_INFO);

    /** Severity level: warning. */
    public static final SeverityLevel WARNING =
        new SeverityLevel(SEVERITYCODE_WARNING, SEVERITYNAME_WARNING);

    /** Severity level: error.  This is the highest severity level.  */
    public static final SeverityLevel ERROR =
        new SeverityLevel(SEVERITYCODE_ERROR, SEVERITYNAME_ERROR);

    /** map from level names to the respective level */
    private static final Map NAME_TO_LEVEL = new HashMap();
    static {
        NAME_TO_LEVEL.put(SEVERITYNAME_IGNORE, IGNORE);
        NAME_TO_LEVEL.put(SEVERITYNAME_INFO, INFO);
        NAME_TO_LEVEL.put(SEVERITYNAME_WARNING, WARNING);
        NAME_TO_LEVEL.put(SEVERITYNAME_ERROR, ERROR);
    }

    /** the SEVERITYCODE_XYZ value of this severity level. */
    private final int mCode;

    /** the name of this severity level. */
    private final String mName;

    /**
     * {@inheritDoc}
     */
    public String toString()
    {
        return "Severity[" + mCode + " (" + mName + ")]";
    }

    /**
     * @return the name of this severity level.
     */
    public String getName()
    {
        return mName;
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo(Object aObject)
    {
        final SeverityLevel severity = (SeverityLevel) aObject;
        return this.mCode - severity.mCode;
    }

    /**
     *  The equals method.
     *
     *  @param aObj  Object to compare to.
     *
     *  @return  <code>true</code> means equal, <code>false</code> means
     *            not equal.
     */
    public boolean equals(Object aObj)
    {
        boolean result = false;

        if ((aObj instanceof SeverityLevel)
            && (((SeverityLevel) aObj).mCode == this.mCode))
        {
            result = true;
        }

        return result;
    }

    /**
     *  The hashCode method.
     *
     *  @return  hash code for the object.
     */
    public int hashCode()
    {
        return mCode;
    }

    /**
     * Creates a new <code>SeverityLevel</code> instance.
     *
     * @param aCode one of the SEVERITYCODE_XYZ values.
     * @param aName one of the SEVERITYNAME_XYZ values.
     */
    private SeverityLevel(int aCode, String aName)
    {
        mCode = aCode;
        mName = aName;
    }

    /**
     * SeverityLevel factory method.
     *
     * @param aSeverityName severity name, such as "ignore", "info", etc.
     * @return the <code>SeverityLevel</code> associated with
     *          <code>aSeverityName</code>
     */
    public static SeverityLevel getInstance(String aSeverityName)
    {
        // canonicalize argument
        final String severityName = aSeverityName.trim().toLowerCase();

        final SeverityLevel retVal =
            (SeverityLevel) NAME_TO_LEVEL.get(severityName);
        if (retVal == null) {
            throw new IllegalArgumentException(severityName);
        }
        return retVal;
    }

    /**
     * Ensures that we don't get multiple instances of one SeverityLevel
     * during deserialization. See Section 3.6 of the Java Object
     * Serialization Specification for details.
     *
     * @return the serialization replacement object
     */
    private Object readResolve()
    {
        return getInstance(mName);
    }
}
