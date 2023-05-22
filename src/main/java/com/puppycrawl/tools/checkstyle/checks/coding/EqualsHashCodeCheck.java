///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.HashMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

/**
 * <p>
 * Checks that classes that either override {@code equals()} or {@code hashCode()} also
 * overrides the other.
 * This check only verifies that the method declarations match {@code Object.equals(Object)} and
 * {@code Object.hashCode()} exactly to be considered an override. This check does not verify
 * invalid method names, parameters other than {@code Object}, or anything else.
 * </p>
 * <p>
 * Rationale: The contract of {@code equals()} and {@code hashCode()} requires that
 * equal objects have the same hashCode. Therefore, whenever you override
 * {@code equals()} you must override {@code hashCode()} to ensure that your class can
 * be used in hash-based collections.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;EqualsHashCode&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public static class Example1 {
 *     public int hashCode() {
 *         // code
 *     }
 *     public boolean equals(String o) { // violation, overloaded implementation of 'equals'
 *         // code
 *     }
 * }
 * public static class Example2 {
 *     public boolean equals(Object o) { // violation, no 'hashCode'
 *         // code
 *     }
 *     public boolean equals(String o) {
 *         // code
 *     }
 * }
 * public static class Example3 {
 *     public int hashCode() {
 *         // code
 *     }
 *     public boolean equals(Object o) { // OK
 *         // code
 *     }
 *     public boolean equals(String o) {
 *         // code
 *     }
 * }
 * public static class Example4 {
 *     public int hashCode() {
 *         // code
 *     }
 *     public boolean equals(java.lang.Object o) { // OK
 *         // code
 *    }
 * }
 * public static class Example5 {
 *     public static int hashCode(int i) {
 *         // code
 *     }
 *     public boolean equals(Object o) { // violation, overloaded implementation of 'hashCode'
 *         // code
 *     }
 * }
 * public static class Example6 {
 *     public int hashCode() { // violation, overloaded implementation of 'equals'
 *         // code
 *     }
 *     public static boolean equals(Object o, Object o2) {
 *         // code
 *     }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code equals.noEquals}
 * </li>
 * <li>
 * {@code equals.noHashCode}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@FileStatefulCheck
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
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.METHOD_DEF};
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
     * @return true if the {code ast} is an Equals method.
     */
    private static boolean isEqualsMethod(DetailAST ast) {
        final DetailAST modifiers = ast.getFirstChild();
        final DetailAST parameters = ast.findFirstToken(TokenTypes.PARAMETERS);

        return CheckUtil.isEqualsMethod(ast)
                && isObjectParam(parameters.getFirstChild())
                && (ast.findFirstToken(TokenTypes.SLIST) != null
                        || modifiers.findFirstToken(TokenTypes.LITERAL_NATIVE) != null);
    }

    /**
     * Determines if an AST is a valid HashCode method implementation.
     *
     * @param ast the AST to check
     * @return true if the {code ast} is a HashCode method.
     */
    private static boolean isHashCodeMethod(DetailAST ast) {
        final DetailAST modifiers = ast.getFirstChild();
        final DetailAST methodName = ast.findFirstToken(TokenTypes.IDENT);
        final DetailAST parameters = ast.findFirstToken(TokenTypes.PARAMETERS);

        return "hashCode".equals(methodName.getText())
                && parameters.getFirstChild() == null
                && (ast.findFirstToken(TokenTypes.SLIST) != null
                        || modifiers.findFirstToken(TokenTypes.LITERAL_NATIVE) != null);
    }

    /**
     * Determines if an AST is a formal param of type Object.
     *
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
                log(equalsAST, MSG_KEY_HASHCODE);
            });
        objBlockWithHashCode.forEach((key, equalsAST) -> log(equalsAST, MSG_KEY_EQUALS));
    }

}
