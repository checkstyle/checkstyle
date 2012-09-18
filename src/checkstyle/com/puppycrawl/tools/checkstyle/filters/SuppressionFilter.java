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
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.FilterSet;

/**
 * <p>
 * This filter accepts AuditEvents according to file, check, line, and
 * column, as specified in a suppression file.
 * </p>
 * @author Rick Giles
 */
public class SuppressionFilter
    extends AutomaticBean
    implements Filter
{
    /** set of individual suppresses */
    private FilterSet mFilters = new FilterSet();

    /**
     * Loads the suppressions for a file.
     * @param aFileName name of the suppressions file.
     * @throws CheckstyleException if there is an error.
     */
    public void setFile(String aFileName)
        throws CheckstyleException
    {
        mFilters = SuppressionsLoader.loadSuppressions(aFileName);
    }

    /** {@inheritDoc} */
    public boolean accept(AuditEvent aEvent)
    {
        return mFilters.accept(aEvent);
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
        if (aObject instanceof SuppressionFilter) {
            final SuppressionFilter other = (SuppressionFilter) aObject;
            return this.mFilters.equals(other.mFilters);
        }
        return false;
    }
}
