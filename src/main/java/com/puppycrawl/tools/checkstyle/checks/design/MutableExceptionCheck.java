///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.design;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Ensures that exception classes (classes with names conforming to some pattern
 * and explicitly extending classes with names conforming to other
 * pattern) are immutable, that is, that they have only final fields.
 * </div>
 *
 * <p>
 * The current algorithm is very simple: it checks that all members of exception are final.
 * The user can still mutate an exception's instance (e.g. Throwable has a method called
 * {@code setStackTrace} which changes the exception's stack trace). But, at least, all
 * information provided by this exception type is unchangeable.
 * </p>
 *
 * <p>
 * Rationale: Exception instances should represent an error
 * condition. Having non-final fields not only allows the state to be
 * modified by accident and therefore mask the original condition but
 * also allows developers to accidentally forget to set the initial state.
 * In both cases, code catching the exception could draw incorrect
 * conclusions based on the state.
 * </p>
 * <ul>
 * <li>
 * Property {@code extendedClassNameFormat} - Specify pattern for extended class names.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^.*Exception$|^.*Error$|^.*Throwable$"}.
 * </li>
 * <li>
 * Property {@code format} - Specify pattern for exception class names.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^.*Exception$|^.*Error$|^.*Throwable$"}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code mutable.exception}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@FileStatefulCheck
public final class MutableExceptionCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "mutable.exception";

    /** Default value for format and extendedClassNameFormat properties. */
    private static final String DEFAULT_FORMAT = "^.*Exception$|^.*Error$|^.*Throwable$";
    /** Stack of checking information for classes. */
    private final Deque<Boolean> checkingStack = new ArrayDeque<>();
    /** Specify pattern for extended class names. */
    private Pattern extendedClassNameFormat = Pattern.compile(DEFAULT_FORMAT);
    /** Should we check current class or not. */
    private boolean checking;
    /** Specify pattern for exception class names. */
    private Pattern format = extendedClassNameFormat;

    /**
     * Setter to specify pattern for extended class names.
     *
     * @param extendedClassNameFormat a {@code String} value
     * @since 6.2
     */
    public void setExtendedClassNameFormat(Pattern extendedClassNameFormat) {
        this.extendedClassNameFormat = extendedClassNameFormat;
    }

    /**
     * Setter to specify pattern for exception class names.
     *
     * @param pattern the new pattern
     * @since 3.2
     */
    public void setFormat(Pattern pattern) {
        format = pattern;
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.CLASS_DEF, TokenTypes.VARIABLE_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CLASS_DEF:
                visitClassDef(ast);
                break;
            case TokenTypes.VARIABLE_DEF:
                visitVariableDef(ast);
                break;
            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.CLASS_DEF) {
            leaveClassDef();
        }
    }

    /**
     * Called when we start processing class definition.
     *
     * @param ast class definition node
     */
    private void visitClassDef(DetailAST ast) {
        checkingStack.push(checking);
        checking = isNamedAsException(ast) && isExtendedClassNamedAsException(ast);
    }

    /** Called when we leave class definition. */
    private void leaveClassDef() {
        checking = checkingStack.pop();
    }

    /**
     * Checks variable definition.
     *
     * @param ast variable def node for check
     */
    private void visitVariableDef(DetailAST ast) {
        if (checking && ast.getParent().getType() == TokenTypes.OBJBLOCK) {
            final DetailAST modifiersAST =
                ast.findFirstToken(TokenTypes.MODIFIERS);

            if (modifiersAST.findFirstToken(TokenTypes.FINAL) == null) {
                log(ast, MSG_KEY, ast.findFirstToken(TokenTypes.IDENT).getText());
            }
        }
    }

    /**
     * Checks that a class name conforms to specified format.
     *
     * @param ast class definition node
     * @return true if a class name conforms to specified format
     */
    private boolean isNamedAsException(DetailAST ast) {
        final String className = ast.findFirstToken(TokenTypes.IDENT).getText();
        return format.matcher(className).find();
    }

    /**
     * Checks that if extended class name conforms to specified format.
     *
     * @param ast class definition node
     * @return true if extended class name conforms to specified format
     */
    private boolean isExtendedClassNamedAsException(DetailAST ast) {
        boolean result = false;
        final DetailAST extendsClause = ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE);
        if (extendsClause != null) {
            DetailAST currentNode = extendsClause;
            while (currentNode.getLastChild() != null) {
                currentNode = currentNode.getLastChild();
            }
            final String extendedClassName = currentNode.getText();
            result = extendedClassNameFormat.matcher(extendedClassName).matches();
        }
        return result;
    }

}
