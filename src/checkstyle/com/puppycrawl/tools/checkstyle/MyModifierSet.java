////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
package com.puppycrawl.tools.checkstyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Represents a set of modifiers and tracks the first line of the modifiers.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
class MyModifierSet
{
    /**
     * The order of modifiers as suggested in sections 8.1.1,
     * 8.3.1 and 8.4.3 of the JLS.
     */
    private static final String[] JLS_ORDER =
    {
        "public", "protected", "private", "abstract", "static", "final",
        "transient", "volatile", "synchronized", "native", "strictfp"
    };

    /** contains the modifiers **/
    private final List mModifiers = new ArrayList();
    /** the first line of the modifiers **/
    private int mFirstLineNo = Integer.MAX_VALUE;

    /**
     * Adds a modifier to the set.
     * @param aMCA the modifier to add
     **/
    void addModifier(MyCommonAST aMCA)
    {
        if (aMCA.getLineNo() < mFirstLineNo) {
            mFirstLineNo = aMCA.getLineNo();
        }

        mModifiers.add(aMCA.getText());
    }

    /** @return the number of modifiers **/
    int size()
    {
        return mModifiers.size();
    }

    /** @return the line number of the first modifier **/
    int getFirstLineNo()
    {
        return mFirstLineNo;
    }

    /** @return whether the set contains a "static". **/
    boolean containsStatic()
    {
        return mModifiers.contains("static");
    }

    /** @return whether the set contains a "final". **/
    boolean containsFinal()
    {
        return mModifiers.contains("final");
    }

    /** @return whether the set contains a "private". **/
    boolean containsPrivate()
    {
        return mModifiers.contains("private");
    }

    /** @return whether the set contains a "protected". **/
    boolean containsProtected()
    {
        return mModifiers.contains("protected");
    }

    /** @return whether the set contains a "public". **/
    boolean containsPublic()
    {
        return mModifiers.contains("public");
    }

    /** @return the visibility scope of the modifiers. */
    Scope getVisibilityScope()
    {
        if (containsPublic())
        {
            return Scope.PUBLIC;
        }
        if (containsProtected())
        {
            return Scope.PROTECTED;
        }
        if (containsPrivate())
        {
            return Scope.PRIVATE;
        }
        return Scope.PACKAGE;
    }

    /**
     * Checks if the modifiers were added in the order suggested
     * in the Java language specification.
     *
     * @return if the modifiers are a subsequence of "public protected
     * private abstract static final transient volatile synchronized
     * native strictfp".
     */
    boolean hasOrderSuggestedByJLS()
    {
        int i = 0;
        Iterator it = mModifiers.iterator();
        do
        {
            if (!it.hasNext())
            {
                return true;
            }
            final String modifier = (String) it.next();
            while (i < JLS_ORDER.length && !JLS_ORDER[i].equals(modifier))
            {
                i++;
            }
        } while (i < JLS_ORDER.length);
        return false;
    }

    /** @see Object **/
    public String toString()
    {
        final StringBuffer buf = new StringBuffer("MyModifierSet [ ");
        for (Iterator it = mModifiers.iterator(); it.hasNext(); ) {
            buf.append((String) it.next());
            buf.append(" ");
        }
        buf.append("]");
        return buf.toString();
    }
}
