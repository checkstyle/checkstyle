////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001  Oliver Burn
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

import java.util.Map;
import java.util.HashMap;

/**
 * Represents a Java visibility scope.
 *
 * @author <a href="mailto:lkuehne@users.sourceforge.net">Lars Kühne</a>
 */
final class Scope implements Comparable
{
    /** poor man's enum for nothing scope */
    private static final int SCOPECODE_NOTHING = 0;
    /** poor man's enum for public scope */
    private static final int SCOPECODE_PUBLIC = 1;
    /** poor man's enum for protected scope */
    private static final int SCOPECODE_PROTECTED = 2;
    /** poor man's enum for package scope */
    private static final int SCOPECODE_PACKAGE = 3;
    /** poor man's enum for private scope */
    private static final int SCOPECODE_PRIVATE = 4;
    /** poor man's enum for anonymous inner class scope */
    private static final int SCOPECODE_ANONINNER = 5;

    /** none scopename */
    private static final String SCOPENAME_NOTHING = "nothing";
    /** public scopename */
    private static final String SCOPENAME_PUBLIC = "public";
    /** protected scopename */
    private static final String SCOPENAME_PROTECTED = "protected";
    /** package scopename */
    private static final String SCOPENAME_PACKAGE = "package";
    /** private scopename */
    private static final String SCOPENAME_PRIVATE = "private";
    /** anon inner scopename */
    private static final String SCOPENAME_ANONINNER = "anoninner";

    /** nothing scope */
    static final Scope NOTHING =
        new Scope(SCOPECODE_NOTHING, SCOPENAME_NOTHING);

    /** public scope */
    static final Scope PUBLIC =
        new Scope(SCOPECODE_PUBLIC, SCOPENAME_PUBLIC);

    /** protected scope */
    static final Scope PROTECTED =
        new Scope(SCOPECODE_PROTECTED, SCOPENAME_PROTECTED);

    /** package scope */
    static final Scope PACKAGE =
        new Scope(SCOPECODE_PACKAGE, SCOPENAME_PACKAGE);

    /** private scope */
    static final Scope PRIVATE =
        new Scope(SCOPECODE_PRIVATE, SCOPENAME_PRIVATE);

    /** anon inner scope */
    static final Scope ANONINNER =
        new Scope(SCOPECODE_ANONINNER, SCOPENAME_ANONINNER);

    /** map from scope names to the respective Scope */
    private static final Map NAME_TO_SCOPE = new HashMap();
    static
    {
        NAME_TO_SCOPE.put(SCOPENAME_NOTHING, NOTHING);
        NAME_TO_SCOPE.put(SCOPENAME_PUBLIC, PUBLIC);
        NAME_TO_SCOPE.put(SCOPENAME_PROTECTED, PROTECTED);
        NAME_TO_SCOPE.put(SCOPENAME_PACKAGE, PACKAGE);
        NAME_TO_SCOPE.put(SCOPENAME_PRIVATE, PRIVATE);
        NAME_TO_SCOPE.put(SCOPENAME_ANONINNER, ANONINNER);
    };

    /** the SCOPECODE_XYZ value of this scope. */
    private final int mCode;

    /** the name of this scope. */
    private final String mName;

    /**
     * @see Object
     */
    public String toString()
    {
        return "Scope[" + mCode + " (" + mName + ")]";
    }

    /**
     * @return the name of this scope.
     */
    String getName()
    {
        return mName;
    }

    /**
     * @see Comparable
     */
    public int compareTo(Object aObject)
    {
        Scope scope = (Scope) aObject;
        return this.mCode - scope.mCode;
    }

    /**
     * Checks if this scope is a subscope of another scope.
     * Example: PUBLIC is a subscope of PRIVATE.
     *
     * @param aScope a <code>Scope</code> value
     * @return if <code>this</code> is a subscope of <code>aScope</code>.
     */
    boolean isIn(Scope aScope)
    {
        return (compareTo(aScope) <= 0);
    }

    /**
     * Creates a new <code>Scope</code> instance.
     *
     * @param aCode one of the SCOPECODE_XYZ values.
     * @param aName one of the SCOPENAME_XYZ values.
     */
    private Scope(int aCode, String aName)
    {
        mCode = aCode;
        mName = aName;
    }

    /**
     * Scope factory method.
     *
     * @param aScopeName scope name, such as "nothing", "public", etc.
     * @return the <code>Scope</code> associated with <code>aScopeName</code>
     */
    static Scope getInstance(String aScopeName)
    {
        // canonicalize argument
        String scopeName = aScopeName.toLowerCase();

        final Scope retVal = (Scope) NAME_TO_SCOPE.get(scopeName);
        if (retVal == null) {
            throw new IllegalArgumentException(scopeName);
        }
        return retVal;
    }
}
