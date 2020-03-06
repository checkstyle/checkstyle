////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks that each top-level class, interface
 * or enum resides in a source file of its own.
 * Official description of a 'top-level' term:
 * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-7.html#jls-7.6">
 * 7.6. Top Level Type Declarations</a>. If file doesn't contains
 * public class, enum or interface, top-level type is the first type in file.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;OneTopLevelClass&quot;/&gt;
 * </pre>
 * <p>
 * <b>ATTENTION:</b> This Check does not support customization of validated tokens,
 * so do not use the "tokens" property.
 * </p>
 * <p>
 * An example of code with violations:
 * </p>
 * <pre>
 * public class Foo { // OK, first top-level class
 *   // methods
 * }
 *
 * class Foo2 { // violation, second top-level class
 *   // methods
 * }
 * </pre>
 * <p>
 * An example of code without public top-level type:
 * </p>
 * <pre>
 * class Foo { // OK, first top-level class
 *   // methods
 * }
 *
 * class Foo2 { // violation, second top-level class
 *   // methods
 * }
 * </pre>
 * <p>
 * An example of code without violations:
 * </p>
 * <pre>
 * public class Foo { // OK, only one top-level class
 *   // methods
 * }
 * </pre>
 *
 * @since 5.8
 */
@FileStatefulCheck
public class OneTopLevelClassCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "one.top.level.class";

    /** Compare DetailAST nodes by line and then column number to make a unique ordering. */
    private static final Comparator<DetailAST> LINE_AND_COL_COMPARATOR =
            Comparator.comparingInt(DetailAST::getLineNo).thenComparingInt(DetailAST::getColumnNo);

    /**
     * True if a java source file contains a type
     * with a public access level modifier.
     */
    private boolean publicTypeFound;

    /** Mapping between type names and DetailAST nodes of the type declarations. */
    private final SortedMap<DetailAST, String> lineNumberTypeMap =
            new TreeMap<>(LINE_AND_COL_COMPARATOR);

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    // ZERO tokens as Check do Traverse of Tree himself, he does not need to subscribed to Tokens
    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
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
                    lineNumberTypeMap.put(currentNode, typeName);
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

            for (Map.Entry<DetailAST, String> entry
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
