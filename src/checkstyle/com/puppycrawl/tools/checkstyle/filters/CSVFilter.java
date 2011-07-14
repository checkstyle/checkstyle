////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * <p>
 * This filter accepts an integer that matches a CSV value, where
 * each value is an integer or a range of integers.
 * </p>
 * @author Rick Giles
 * @author o_sukhodolsky
 */
class CSVFilter implements IntFilter
{
    /** filter set */
    private final Set<IntFilter> mFilters = Sets.newHashSet();

    /**
     * Adds a IntFilter to the set.
     * @param aFilter the IntFilter to add.
     */
    public void addFilter(IntFilter aFilter)
    {
        mFilters.add(aFilter);
    }

    /**
     * Returns the IntFilters of the filter set.
     * @return the IntFilters of the filter set.
     */
    protected Set<IntFilter> getFilters()
    {
        return mFilters;
    }

    /**
     * Constructs a <code>CSVFilter</code> from a CSV, Comma-Separated Values,
     * string. Each value is an integer, or a range of integers. A range of
     * integers is of the form integer-integer, such as 1-10.
     * Note: integers must be non-negative.
     * @param aPattern the CSV string.
     * @throws NumberFormatException if a component substring does not
     * contain a parsable integer.
     */
    public CSVFilter(String aPattern)
        throws NumberFormatException
    {
        final StringTokenizer tokenizer = new StringTokenizer(aPattern, ",");
        while (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken().trim();
            final int index = token.indexOf("-");
            if (index == -1) {
                final int matchValue = Integer.parseInt(token);
                addFilter(new IntMatchFilter(matchValue));
            }
            else {
                final int lowerBound =
                    Integer.parseInt(token.substring(0, index));
                final int upperBound =
                    Integer.parseInt(token.substring(index + 1));
                addFilter(new IntRangeFilter(lowerBound, upperBound));
            }
        }
    }

    /**
     * Determines whether an Integer matches a CSV integer value.
     * @param aInt the Integer to check.
     * @return true if aInt is an Integer that matches a CSV value.
     */
    public boolean accept(int aInt)
    {
        for (IntFilter filter : getFilters()) {
            if (filter.accept(aInt)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString()
    {
        return mFilters.toString();
    }

    @Override
    public int hashCode()
    {
        return mFilters.hashCode();
    }

    @Override
    public boolean equals(Object aObject)
    {
        if (aObject instanceof CSVFilter) {
            final CSVFilter other = (CSVFilter) aObject;
            return this.mFilters.equals(other.mFilters);
        }
        return false;
    }
}
