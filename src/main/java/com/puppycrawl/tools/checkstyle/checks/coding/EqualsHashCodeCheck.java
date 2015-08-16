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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Map;
import java.util.Set;

import antlr.collections.AST;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that classes that override equals() also override hashCode().
 * </p>
 * <p>
 * Rationale: The contract of equals() and hashCode() requires that
 * equal objects have the same hashCode. Hence, whenever you override
 * equals() you must override hashCode() to ensure that your class can
 * be used in collections that are hash based.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="EqualsHashCode"/&gt;
 * </pre>
 * @author lkuehne
 */
public class EqualsHashCodeCheck
        extends Check {
    // implementation note: we have to use the following members to
    // keep track of definitions in different inner classes

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "equals.noHashCode";

    /** maps OBJ_BLOCK to the method definition of equals() */
    private final Map<DetailAST, DetailAST> objBlockEquals = Maps.newHashMap();

    /** the set of OBJ_BLOCKs that contain a definition of hashCode() */
    private final Set<DetailAST> objBlockWithHashCode = Sets.newHashSet();

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        objBlockEquals.clear();
        objBlockWithHashCode.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST modifiers = ast.getFirstChild();
        final AST type = ast.findFirstToken(TokenTypes.TYPE);
        final AST methodName = ast.findFirstToken(TokenTypes.IDENT);
        final DetailAST parameters = ast.findFirstToken(TokenTypes.PARAMETERS);

        if (type.getFirstChild().getType() == TokenTypes.LITERAL_BOOLEAN
                && "equals".equals(methodName.getText())
                && modifiers.branchContains(TokenTypes.LITERAL_PUBLIC)
                && parameters.getChildCount() == 1
                && isObjectParam(parameters.getFirstChild())
            ) {
            objBlockEquals.put(ast.getParent(), ast);
        }
        else if (type.getFirstChild().getType() == TokenTypes.LITERAL_INT
                && "hashCode".equals(methodName.getText())
                && modifiers.branchContains(TokenTypes.LITERAL_PUBLIC)
                && parameters.getFirstChild() == null) { // no params
            objBlockWithHashCode.add(ast.getParent());
        }
    }

    /**
     * Determines if an AST is a formal param of type Object (or subclass).
     * @param firstChild the AST to check
     * @return true iff firstChild is a parameter of an Object type.
     */
    private static boolean isObjectParam(AST firstChild) {
        final AST modifiers = firstChild.getFirstChild();
        final AST type = modifiers.getNextSibling();
        switch (type.getFirstChild().getType()) {
            case TokenTypes.LITERAL_BOOLEAN:
            case TokenTypes.LITERAL_BYTE:
            case TokenTypes.LITERAL_CHAR:
            case TokenTypes.LITERAL_DOUBLE:
            case TokenTypes.LITERAL_FLOAT:
            case TokenTypes.LITERAL_INT:
            case TokenTypes.LITERAL_LONG:
            case TokenTypes.LITERAL_SHORT:
                return false;
            default:
                return true;
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        for (Map.Entry<DetailAST, DetailAST> detailASTDetailASTEntry : objBlockEquals.entrySet()) {
            if (!objBlockWithHashCode.contains(detailASTDetailASTEntry.getKey())) {
                final DetailAST equalsAST = detailASTDetailASTEntry.getValue();
                log(equalsAST.getLineNo(), equalsAST.getColumnNo(), MSG_KEY);
            }
        }

        objBlockEquals.clear();
        objBlockWithHashCode.clear();
    }
}
