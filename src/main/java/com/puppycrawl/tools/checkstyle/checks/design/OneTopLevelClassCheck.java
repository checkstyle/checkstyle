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
package com.puppycrawl.tools.checkstyle.checks.design;

import java.util.Map;
import java.util.TreeMap;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 *
 * Checks that each top-level class, interface
 * or enum resides in a source file of its own.
 * <p>
 * Official description of a 'top-level' term:<a
 * href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-7.html#jls-7.6">
 * 7.6. Top Level Type Declarations</a>. If file doesn't contains
 * public class, enum or interface, top-level type is the first type in file.
 * </p>
 * <p>
 * An example of code with violations:
 * </p>
 * <pre><code>
 * public class Foo{
 *     //methods
 * }
 *
 * class Foo2{
 *     //methods
 * }
 * </code></pre>
 * <p>
 * An example of code without top-level public type:
 * </p>
 * <pre><code>
 * class Foo{ //top-level class
 *     //methods
 * }
 *
 * class Foo2{
 *     //methods
 * }
 * </code></pre>
 * <p>
 * An example of check's configuration:
 * </p>
 * <pre>
 * &lt;module name="OneTopLevelClass"/&gt;
 * </pre>
 *
 * <p>
 * An example of code without violations:
 * </p>
 * <pre><code>
 * public class Foo{
 *     //methods
 * }
 * </code></pre>
 *
 * @author maxvetrenko
 */
public class OneTopLevelClassCheck extends Check
{

    /**
     * True if a java source file contains a type
     * with a public access level modifier.
     */
    private boolean mPublicTypeFound;

    /** Mapping between type names and line numbers of the type declarations.*/
    private TreeMap<Integer, String> mLineNumberTypeMap =
            new TreeMap<Integer, String>();

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {};
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        DetailAST currentNode = aRootAST;
        while (currentNode != null) {
            if (currentNode.getType() == TokenTypes.CLASS_DEF
                    || currentNode.getType() == TokenTypes.ENUM_DEF
                    || currentNode.getType() == TokenTypes.INTERFACE_DEF)
            {
                if (isPublic(currentNode)) {
                    mPublicTypeFound = true;
                }

                else {
                    final String typeName = currentNode.
                            findFirstToken(TokenTypes.IDENT).getText();
                    mLineNumberTypeMap.put(currentNode.getLineNo(), typeName);
                }
            }
            currentNode = currentNode.getNextSibling();
        }
    }

    @Override
    public void finishTree(DetailAST aRootAST)
    {
        if (!mPublicTypeFound && !mLineNumberTypeMap.isEmpty()) {
            // skip first top-level type.
            mLineNumberTypeMap.remove(mLineNumberTypeMap.firstKey());
        }

        for (Map.Entry<Integer, String> entry
                : mLineNumberTypeMap.entrySet())
        {
            log(entry.getKey(), "one.top.level.class", entry.getValue());
        }

        mLineNumberTypeMap.clear();
        mPublicTypeFound = false;
    }

    /**
     * Checks if a type is public.
     * @param aTypeDef type definition node.
     * @return true if a type has a public access level modifier.
     */
    private boolean isPublic(DetailAST aTypeDef)
    {
        final DetailAST modifiers =
                aTypeDef.findFirstToken(TokenTypes.MODIFIERS);
        return modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) != null;
    }
}
