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
package com.puppycrawl.tools.checkstyle.api;

/**
 * Represents a Java visibility scope.
 *
 * @author Lars Kühne
 * @author Travis Schneeberger
 */
public enum Scope
{
    /** nothing scope. */
    NOTHING,
    /** protected scope. */
    PUBLIC,
    /** protected scope. */
    PROTECTED,
    /** package or default scope. */
    PACKAGE,
    /** private scope. */
    PRIVATE,
    /** anonymous inner scope. */
    ANONINNER;

    @Override
    public String toString()
    {
        return getName();
    }

    /**
     * @return the name of this severity level.
     */
    public String getName()
    {
        return name().toLowerCase();
    }

    /**
     * Checks if this scope is a subscope of another scope.
     * Example: PUBLIC is a subscope of PRIVATE.
     *
     * @param aScope a <code>Scope</code> value
     * @return if <code>this</code> is a subscope of <code>aScope</code>.
     */
    public boolean isIn(Scope aScope)
    {
        return (compareTo(aScope) <= 0);
    }

    /**
     * Scope factory method.
     *
     * @param aScopeName scope name, such as "nothing", "public", etc.
     * @return the <code>Scope</code> associated with <code>aScopeName</code>
     */
    public static Scope getInstance(String aScopeName)
    {
        return valueOf(Scope.class, aScopeName.trim().toUpperCase());
    }
}
