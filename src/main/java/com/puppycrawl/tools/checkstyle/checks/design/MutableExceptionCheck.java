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

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractFormatCheck;

/**
 * <p> Ensures that exceptions (classes with names conforming to some regular
 * expression and explicitly extending classes with names conforming to other
 * regular expression) are immutable. That is, they have only final fields.</p>
 * <p> Rationale: Exception instances should represent an error
 * condition. Having non final fields not only allows the state to be
 * modified by accident and therefore mask the original condition but
 * also allows developers to accidentally forget to initialise state
 * thereby leading to code catching the exception to draw incorrect
 * conclusions based on the state.</p>
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 */
public final class MutableExceptionCheck extends AbstractFormatCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "mutable.exception";

    /** Default value for format and extendedClassNameFormat properties. */
    private static final String DEFAULT_FORMAT = "^.*Exception$|^.*Error$|^.*Throwable$";
    /** Pattern for class name that is being extended */
    private String extendedClassNameFormat;
    /** Stack of checking information for classes. */
    private final Deque<Boolean> checkingStack = new ArrayDeque<>();
    /** Should we check current class or not. */
    private boolean checking;

    /** Creates new instance of the check. */
    public MutableExceptionCheck() {
        super(DEFAULT_FORMAT);
        setExtendedClassNameFormat(DEFAULT_FORMAT);
    }

    /**
     * Sets the format of extended class name to the specified regular expression.
     * @param extendedClassNameFormat a {@code String} value
     */
    public void setExtendedClassNameFormat(String extendedClassNameFormat) {
        this.extendedClassNameFormat = extendedClassNameFormat;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.CLASS_DEF, TokenTypes.VARIABLE_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.CLASS_DEF, TokenTypes.VARIABLE_DEF};
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
     * @param ast class definition node
     */
    private void visitClassDef(DetailAST ast) {
        checkingStack.push(checking ? Boolean.TRUE : Boolean.FALSE);
        checking = isNamedAsException(ast) && isExtendedClassNamedAsException(ast);
    }

    /** Called when we leave class definition. */
    private void leaveClassDef() {
        checking = checkingStack.pop();
    }

    /**
     * Checks variable definition.
     * @param ast variable def node for check
     */
    private void visitVariableDef(DetailAST ast) {
        if (checking && ast.getParent().getType() == TokenTypes.OBJBLOCK) {
            final DetailAST modifiersAST =
                ast.findFirstToken(TokenTypes.MODIFIERS);

            if (modifiersAST.findFirstToken(TokenTypes.FINAL) == null) {
                log(ast.getLineNo(),  ast.getColumnNo(), MSG_KEY,
                        ast.findFirstToken(TokenTypes.IDENT).getText());
            }
        }
    }

    /**
     * @param ast class definition node
     * @return true if a class name conforms to specified format
     */
    private boolean isNamedAsException(DetailAST ast) {
        final String className = ast.findFirstToken(TokenTypes.IDENT).getText();
        return getRegexp().matcher(className).find();
    }

    /**
     * @param ast class definition node
     * @return true if extended class name conforms to specified format
     */
    private boolean isExtendedClassNamedAsException(DetailAST ast) {
        final DetailAST extendsClause = ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE);
        if (extendsClause != null) {
            DetailAST currentNode = extendsClause;
            while (currentNode.getType() != TokenTypes.IDENT) {
                currentNode = currentNode.getLastChild();
            }
            final String extendedClassName = currentNode.getText();
            return extendedClassName.matches(extendedClassNameFormat);
        }
        return false;
    }
}
