////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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

import java.util.BitSet;

/**
 * Encapsulates representation of notion of expected indentation levels.
 * Provide a way to have multiple accaptable levels.
 *
 * @author o_sukhodolsky
 */
public class IndentLevel
{
    /** set of acceptable indentation levels. */
    private final BitSet mLevels = new BitSet();

    /**
     * Creates new instance with one accaptable indentation level.
     * @param aIndent accaptable indentation level.
     */
    public IndentLevel(int aIndent)
    {
        mLevels.set(aIndent);
    }

    /**
     * Creates new instance for nested structure.
     * @param aBase parent's level
     * @param aOffset offset from parent's level.
     */
    public IndentLevel(IndentLevel aBase, int aOffset)
    {
        final BitSet src = aBase.mLevels;
        for (int i = src.nextSetBit(0); i >= 0; i = src.nextSetBit(i + 1)) {
            mLevels.set(i + aOffset);
        }
    }

    /**
     * Checks wether we have more than one level.
     * @return wether we have more than one level.
     */
    public final boolean isMultiLevel()
    {
        return mLevels.cardinality() > 1;
    }

    /**
     * Checks if given indentation is accaptable.
     * @param aIndent indentation to check.
     * @return true if givent indentation is acceptable,
     *         false otherwise.
     */
    public boolean accept(int aIndent)
    {
        return (mLevels.get(aIndent));
    }

    /**
     * @param aIndent indentation to check.
     * @return true if <code>aIndent</code> less then minimal of
     *         accaptable indentation levels, false otherwise.
     */
    public boolean gt(int aIndent)
    {
        return (mLevels.nextSetBit(0) > aIndent);
    }

    /**
     * Adds one more acceptable indentation level.
     * @param aIndent new acceptable indentation.
     */
    public void addAcceptedIndent(int aIndent)
    {
        mLevels.set(aIndent);
    }

    /**
     * Adds one more acceptable indentation level.
     * @param aIndent new acceptable indentation.
     */
    public void addAcceptedIndent(IndentLevel aIndent)
    {
        mLevels.or(aIndent.mLevels);
    }

    @Override
    public String toString()
    {
        if (mLevels.cardinality() == 1) {
            return String.valueOf(mLevels.nextSetBit(0));
        }

        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = mLevels.nextSetBit(0); i >= 0;
            i = mLevels.nextSetBit(i + 1))
        {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(i);
        }
        sb.append(']');
        return sb.toString();
    }
}
