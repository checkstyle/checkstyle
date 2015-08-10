////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
import java.util.SortedMap;
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
 * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-7.html#jls-7.6">
 * 7.6. Top Level Type Declarations</a>. If file doesn't contains
 * public class, enum or interface, top-level type is the first type in file.
 * </p>
 * <p>
 * An example of code with violations:
 * </p>
 * <pre>{@code
 * public class Foo{
 *     //methods
 * }
 *
 * class Foo2{
 *     //methods
 * }
 * }</pre>
 * <p>
 * An example of code without top-level public type:
 * </p>
 * <pre>{@code
 * class Foo{ //top-level class
 *     //methods
 * }
 *
 * class Foo2{
 *     //methods
 * }
 * }</pre>
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
 * <pre>{@code
 * public class Foo{
 *     //methods
 * }
 * }</pre>
 *
 * <p> ATTENTION: This Check does not support customization of validated tokens,
 *  so do not use the "tokens" property.
 * </p>
 *
 * @author maxvetrenko
 */
public class OneTopLevelClassCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "one.top.level.class";

    /**
     * True if a java source file contains a type
     * with a public access level modifier.
     */
    private boolean publicTypeFound;

    /** Mapping between type names and line numbers of the type declarations.*/
    private final SortedMap<Integer, String> lineNumberTypeMap = new TreeMap<>();

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    // ZERO tokens as Check do Traverse of Tree himself, he does not need to subscribed to Tokens
    @Override
    public int[] getAcceptableTokens() {
        return new int[] {};
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        publicTypeFound = false;
        lineNumberTypeMap.clear();

        DetailAST currentNode = rootAST;
        while (currentNode != null) {
            if (currentNode.getType() == TokenTypes.CLASS_DEF
                    || currentNode.getType() == TokenTypes.ENUM_DEF
                    || currentNode.getType() == TokenTypes.INTERFACE_DEF) {
                if (isPublic(currentNode)) {
                    publicTypeFound = true;
                }
                else {
                    final String typeName = currentNode
                            .findFirstToken(TokenTypes.IDENT).getText();
                    lineNumberTypeMap.put(currentNode.getLineNo(), typeName);
                }
            }
            currentNode = currentNode.getNextSibling();
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        if (!lineNumberTypeMap.isEmpty()) {
            if (!publicTypeFound) {
                // skip first top-level type.
                lineNumberTypeMap.remove(lineNumberTypeMap.firstKey());
            }

            for (Map.Entry<Integer, String> entry
                    : lineNumberTypeMap.entrySet()) {
                log(entry.getKey(), MSG_KEY, entry.getValue());
            }
        }
    }

    /**
     * Checks if a type is public.
     * @param typeDef type definition node.
     * @return true if a type has a public access level modifier.
     */
    private static boolean isPublic(DetailAST typeDef) {
        final DetailAST modifiers =
                typeDef.findFirstToken(TokenTypes.MODIFIERS);
        return modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) != null;
    }
}
