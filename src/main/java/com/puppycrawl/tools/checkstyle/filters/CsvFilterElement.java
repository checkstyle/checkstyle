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

package com.puppycrawl.tools.checkstyle.filters;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * <p>
 * This filter element is immutable and accepts an integer that matches a CSV value, where
 * each value is an integer or a range of integers.
 * </p>
 */
class CsvFilterElement implements IntFilterElement {

    /** Filter set. */
    private final Set<IntFilterElement> filters = new HashSet<>();

    /**
     * Constructs a {@code CsvFilterElement} from a CSV, Comma-Separated Values,
     * string. Each value is an integer, or a range of integers. A range of
     * integers is of the form integer-integer, such as 1-10.
     * Note: integers must be non-negative.
     *
     * @param pattern the CSV string.
     * @throws NumberFormatException if a component substring does not
     *     contain a parsable integer.
     */
    /* package */ CsvFilterElement(String pattern) {
        final StringTokenizer tokenizer = new StringTokenizer(pattern, ",");
        while (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken().trim();
            final int index = token.indexOf('-');
            if (index == -1) {
                final int matchValue = Integer.parseInt(token);
                addFilter(new IntMatchFilterElement(matchValue));
            }
            else {
                final int lowerBound =
                    Integer.parseInt(token.substring(0, index));
                final int upperBound =
                    Integer.parseInt(token.substring(index + 1));
                addFilter(new IntRangeFilterElement(lowerBound, upperBound));
            }
        }
    }

    /**
     * Adds a IntFilterElement to the set.
     *
     * @param filter the IntFilterElement to add.
     */
    private void addFilter(IntFilterElement filter) {
        filters.add(filter);
    }

    /**
     * Returns the IntFilters of the filter set.
     *
     * @return the IntFilters of the filter set.
     */
    protected Set<IntFilterElement> getFilters() {
        return Collections.unmodifiableSet(filters);
    }

    /**
     * Determines whether an Integer matches a CSV integer value.
     *
     * @param intValue the Integer to check.
     * @return true if intValue is an Integer that matches a CSV value.
     */
    @Override
    public boolean accept(int intValue) {
        boolean result = false;
        for (IntFilterElement filter : getFilters()) {
            if (filter.accept(intValue)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final CsvFilterElement csvFilter = (CsvFilterElement) object;
        return Objects.equals(filters, csvFilter.filters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filters);
    }

}
