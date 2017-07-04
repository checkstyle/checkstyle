////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import java.util.HashMap;
import java.util.Map;

import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtils;

/**
 * <p>
 * Checks that classes that either override {@code equals()} or {@code hashCode()} also
 * overrides the other.
 * This checks only verifies that the method declarations match {@link Object#equals(Object)} and
 * {@link Object#hashCode()} exactly to be considered an override. This check does not verify
 * invalid method names, parameters other than {@code Object}, or anything else.
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
        extends AbstractCheck {
    // implementation note: we have to use the following members to
    // keep track of definitions in different inner classes

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_HASHCODE = "equals.noHashCode";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_EQUALS = "equals.noEquals";

    /** Maps OBJ_BLOCK to the method definition of equals(). */
    private final Map<DetailAST, DetailAST> objBlockWithEquals = new HashMap<>();

    /** Maps OBJ_BLOCKs to the method definition of hashCode(). */
    private final Map<DetailAST, DetailAST> objBlockWithHashCode = new HashMap<>();

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
        objBlockWithEquals.clear();
        objBlockWithHashCode.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isEqualsMethod(ast)) {
            objBlockWithEquals.put(ast.getParent(), ast);
        }
        else if (isHashCodeMethod(ast)) {
            objBlockWithHashCode.put(ast.getParent(), ast);
        }
    }

    /**
     * Determines if an AST is a valid Equals method implementation.
     *
     * @param ast the AST to check
     * @return true if the {code ast} is a Equals method.
     */
    private static boolean isEqualsMethod(DetailAST ast) {
        final DetailAST modifiers = ast.getFirstChild();
        final DetailAST parameters = ast.findFirstToken(TokenTypes.PARAMETERS);

        return CheckUtils.isEqualsMethod(ast)
                && modifiers.branchContains(TokenTypes.LITERAL_PUBLIC)
                && isObjectParam(parameters.getFirstChild())
                && (ast.branchContains(TokenTypes.SLIST)
                        || modifiers.branchContains(TokenTypes.LITERAL_NATIVE));
    }

    /**
     * Determines if an AST is a valid HashCode method implementation.
     *
     * @param ast the AST to check
     * @return true if the {code ast} is a HashCode method.
     */
    private static boolean isHashCodeMethod(DetailAST ast) {
        final DetailAST modifiers = ast.getFirstChild();
        final AST type = ast.findFirstToken(TokenTypes.TYPE);
        final AST methodName = ast.findFirstToken(TokenTypes.IDENT);
        final DetailAST parameters = ast.findFirstToken(TokenTypes.PARAMETERS);

        return type.getFirstChild().getType() == TokenTypes.LITERAL_INT
                && "hashCode".equals(methodName.getText())
                && modifiers.branchContains(TokenTypes.LITERAL_PUBLIC)
                && !modifiers.branchContains(TokenTypes.LITERAL_STATIC)
                && parameters.getFirstChild() == null
                && (ast.branchContains(TokenTypes.SLIST)
                        || modifiers.branchContains(TokenTypes.LITERAL_NATIVE));
    }

    /**
     * Determines if an AST is a formal param of type Object.
     * @param paramNode the AST to check
     * @return true if firstChild is a parameter of an Object type.
     */
    private static boolean isObjectParam(DetailAST paramNode) {
        final DetailAST typeNode = paramNode.findFirstToken(TokenTypes.TYPE);
        final FullIdent fullIdent = FullIdent.createFullIdentBelow(typeNode);
        final String name = fullIdent.getText();
        return "Object".equals(name) || "java.lang.Object".equals(name);
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        objBlockWithEquals
            .entrySet().stream().filter(detailASTDetailASTEntry -> {
                return objBlockWithHashCode.remove(detailASTDetailASTEntry.getKey()) == null;
            }).forEach(detailASTDetailASTEntry -> {
                final DetailAST equalsAST = detailASTDetailASTEntry.getValue();
                log(equalsAST.getLineNo(), equalsAST.getColumnNo(), MSG_KEY_HASHCODE);
            });
        objBlockWithHashCode.entrySet().forEach(detailASTDetailASTEntry -> {
            final DetailAST equalsAST = detailASTDetailASTEntry.getValue();
            log(equalsAST.getLineNo(), equalsAST.getColumnNo(), MSG_KEY_EQUALS);
        });

        objBlockWithEquals.clear();
        objBlockWithHashCode.clear();
    }
}
