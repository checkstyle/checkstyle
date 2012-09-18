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
package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.google.common.collect.Sets;
import java.util.SortedSet;

/**
 * Encapsulates representation of notion of expected indentation levels.
 * Provide a way to have multiple accaptable levels.
 *
 * @author o_sukhodolsky
 */
public class IndentLevel
{
    /** set of acceptable indentation levels. */
    private final SortedSet<Integer> mLevels = Sets.newTreeSet();

    /**
     * Creates new instance with one accaptable indentation level.
     * @param aIndent accaptable indentation level.
     */
    public IndentLevel(int aIndent)
    {
        mLevels.add(aIndent);
    }

    /**
     * Creates new instance for nested structure.
     * @param aBase parent's level
     * @param aOffset offset from parent's level.
     */
    public IndentLevel(IndentLevel aBase, int aOffset)
    {
        for (Integer base : aBase.mLevels) {
            mLevels.add(base + aOffset);
        }
    }

    /**
     * Checks wether we have more than one level.
     * @return wether we have more than one level.
     */
    public final boolean isMultiLevel()
    {
        return mLevels.size() > 1;
    }

    /**
     * Checks if given indentation is accaptable.
     * @param aIndent indentation to check.
     * @return true if givent indentation is acceptable,
     *         false otherwise.
     */
    public boolean accept(int aIndent)
    {
        return (mLevels.contains(aIndent));
    }

    /**
     * @param aIndent indentation to check.
     * @return true if <code>aIndent</code> less then minimal of
     *         accaptable indentation levels, false otherwise.
     */
    public boolean gt(int aIndent)
    {
        return ((mLevels.first()).intValue() > aIndent);
    }

    /**
     * Adds one more acceptable indentation level.
     * @param aIndent new acceptable indentation.
     */
    public void addAcceptedIndent(int aIndent)
    {
        mLevels.add(aIndent);
    }

    /**
     * Adds one more acceptable indentation level.
     * @param aIndent new acceptable indentation.
     */
    public void addAcceptedIndent(IndentLevel aIndent)
    {
        mLevels.addAll(aIndent.mLevels);
    }

    @Override
    public String toString()
    {
        if (mLevels.size() == 1) {
            return mLevels.first().toString();
        }

        return mLevels.toString();
    }
}
