////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.imports;

import com.google.common.collect.ImmutableMap;

import java.io.Serializable;

/**
 * Represents the result of an access check.
 *
 * @author Oliver Burn
 */
final class AccessResult
        implements Serializable
{
    /** Numeric value for access result ALLOWED. */
    private static final int CODE_ALLOWED = 10;
    /** Numeric value for access result DISALLOWED. */
    private static final int CODE_DISALLOWED = 20;
    /** Numeric value for access result UNKNOWN. */
    private static final int CODE_UNKNOWN = 30;
    /** Label for access result ALLOWED. */
    private static final String LABEL_ALLOWED = "ALLOWED";
    /** Label for access result DISALLOWED. */
    private static final String LABEL_DISALLOWED = "DISALLOWED";
    /** Label for access result UNKNOWN. */
    private static final String LABEL_UNKNOWN = "UNKNOWN";

    /** Represents that access is allowed. */
    public static final AccessResult ALLOWED = new AccessResult(CODE_ALLOWED,
            LABEL_ALLOWED);
    /** Represents that access is disallowed. */
    public static final AccessResult DISALLOWED = new AccessResult(
            CODE_DISALLOWED, LABEL_DISALLOWED);
    /** Represents that access is unknown. */
    public static final AccessResult UNKNOWN = new AccessResult(CODE_UNKNOWN,
            LABEL_UNKNOWN);

    /** map from results names to the respective result */
    private static final ImmutableMap<String, AccessResult> NAME_TO_LEVEL;

    static {
        final ImmutableMap.Builder<String, AccessResult> builder =
            ImmutableMap.builder();
        builder.put(LABEL_ALLOWED, ALLOWED);
        builder.put(LABEL_DISALLOWED, DISALLOWED);
        builder.put(LABEL_UNKNOWN, UNKNOWN);
        NAME_TO_LEVEL = builder.build();
    }

    /** Code for the access result. */
    private final int mCode;
    /** Label for the access result. */
    private final String mLabel;

    /**
     * Constructs an instance.
     *
     * @param aCode the code for the result.
     * @param aLabel the label for the result.
     */
    private AccessResult(final int aCode, final String aLabel)
    {
        mCode = aCode;
        mLabel = aLabel.trim();
    }

    /**
     * @return the label for the result.
     */
    String getLabel()
    {
        return mLabel;
    }

    @Override
    public String toString()
    {
        return getLabel();
    }

    @Override
    public boolean equals(Object aObj)
    {
        boolean result = false;

        if ((aObj instanceof AccessResult)
                && (((AccessResult) aObj).mCode == this.mCode))
        {
            result = true;
        }

        return result;
    }

    @Override
    public int hashCode()
    {
        return mCode;
    }

    /**
     * SeverityLevel factory method.
     *
     * @param aName access result name.
     * @return the {@link AccessResult} associated with the supplied name.
     */
    public static AccessResult getInstance(String aName)
    {
        // canonicalize argument
        final String arName = aName.trim();

        final AccessResult retVal = NAME_TO_LEVEL.get(arName);
        if (retVal == null) {
            throw new IllegalArgumentException(arName);
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
        return getInstance(mLabel);
    }

}
