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
    /** public scope. */
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
     * @param scope a <code>Scope</code> value
     * @return if <code>this</code> is a subscope of <code>scope</code>.
     */
    public boolean isIn(Scope scope)
    {
        return (compareTo(scope) <= 0);
    }

    /**
     * Scope factory method.
     *
     * @param scopeName scope name, such as "nothing", "public", etc.
     * @return the <code>Scope</code> associated with <code>scopeName</code>
     */
    public static Scope getInstance(String scopeName)
    {
        return valueOf(Scope.class, scopeName.trim().toUpperCase());
    }
}
