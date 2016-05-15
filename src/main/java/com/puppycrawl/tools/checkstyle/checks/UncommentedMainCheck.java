////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.regex.Pattern;

import com.google.common.base.Optional;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Detects uncommented main methods. Basically detects
 * any main method, since if it is detectable
 * that means it is uncommented.
 *
 * <pre class="body">
 * &lt;module name=&quot;UncommentedMain&quot;/&gt;
 * </pre>
 *
 * @author Michael Yui
 * @author o_sukhodolsky
 */
public class UncommentedMainCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "uncommented.main";

    /** The pattern to exclude classes from the check. */
    private String excludedClasses = "^$";
    /** Compiled regexp to exclude classes from check. */
    private Pattern excludedClassesPattern =
            CommonUtils.createPattern(excludedClasses);
    /** Current class name. */
    private String currentClass;
    /** Current package. */
    private FullIdent packageName;
    /** Class definition depth. */
    private int classDepth;

    /**
     * Set the excluded classes pattern.
     * @param excludedClasses a {@code String} value
     */
    public void setExcludedClasses(String excludedClasses) {
        this.excludedClasses = excludedClasses;
        excludedClassesPattern = CommonUtils.createPattern(excludedClasses);
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.PACKAGE_DEF,
        };
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        packageName = FullIdent.createFullIdent(null);
        currentClass = null;
        classDepth = 0;
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.CLASS_DEF) {
            if (classDepth == 1) {
                currentClass = null;
            }
            classDepth--;
        }
    }

    @Override
    public void visitToken(DetailAST ast) {

        switch (ast.getType()) {
            case TokenTypes.PACKAGE_DEF:
                visitPackageDef(ast);
                break;
            case TokenTypes.CLASS_DEF:
                visitClassDef(ast);
                break;
            case TokenTypes.METHOD_DEF:
                visitMethodDef(ast);
                break;
            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    /**
     * Sets current package.
     * @param packageDef node for package definition
     */
    private void visitPackageDef(DetailAST packageDef) {
        packageName = FullIdent.createFullIdent(packageDef.getLastChild()
                .getPreviousSibling());
    }

    /**
     * If not inner class then change current class name.
     * @param classDef node for class definition
     */
    private void visitClassDef(DetailAST classDef) {
        // we are not use inner classes because they can not
        // have static methods
        if (classDepth == 0) {
            final DetailAST ident = classDef.findFirstToken(TokenTypes.IDENT);
            currentClass = packageName.getText() + "." + ident.getText();
            classDepth++;
        }
    }

    /**
     * Checks method definition if this is
     * {@code public static void main(String[])}.
     * @param method method definition node
     */
    private void visitMethodDef(DetailAST method) {
        if (classDepth == 1
                // method not in inner class or in interface definition
                && checkClassName()
                && checkName(method)
                && checkModifiers(method)
                && checkType(method)
                && checkParams(method)) {
            log(method.getLineNo(), MSG_KEY);
        }
    }

    /**
     * Checks that current class is not excluded.
     * @return true if check passed, false otherwise
     */
    private boolean checkClassName() {
        return !excludedClassesPattern.matcher(currentClass).find();
    }

    /**
     * Checks that method name is @quot;main@quot;.
     * @param method the METHOD_DEF node
     * @return true if check passed, false otherwise
     */
    private static boolean checkName(DetailAST method) {
        final DetailAST ident = method.findFirstToken(TokenTypes.IDENT);
        return "main".equals(ident.getText());
    }

    /**
     * Checks that method has final and static modifiers.
     * @param method the METHOD_DEF node
     * @return true if check passed, false otherwise
     */
    private static boolean checkModifiers(DetailAST method) {
        final DetailAST modifiers =
            method.findFirstToken(TokenTypes.MODIFIERS);

        return modifiers.branchContains(TokenTypes.LITERAL_PUBLIC)
            && modifiers.branchContains(TokenTypes.LITERAL_STATIC);
    }

    /**
     * Checks that return type is {@code void}.
     * @param method the METHOD_DEF node
     * @return true if check passed, false otherwise
     */
    private static boolean checkType(DetailAST method) {
        final DetailAST type =
            method.findFirstToken(TokenTypes.TYPE).getFirstChild();
        return type.getType() == TokenTypes.LITERAL_VOID;
    }

    /**
     * Checks that method has only {@code String[]} or only {@code String...} param.
     * @param method the METHOD_DEF node
     * @return true if check passed, false otherwise
     */
    private static boolean checkParams(DetailAST method) {
        boolean checkPassed = false;
        final DetailAST params = method.findFirstToken(TokenTypes.PARAMETERS);

        if (params.getChildCount() == 1) {
            final DetailAST parameterType = params.getFirstChild().findFirstToken(TokenTypes.TYPE);
            final Optional<DetailAST> arrayDecl = Optional.fromNullable(
                parameterType.findFirstToken(TokenTypes.ARRAY_DECLARATOR));
            final Optional<DetailAST> varargs = Optional.fromNullable(
                params.getFirstChild().findFirstToken(TokenTypes.ELLIPSIS));

            if (arrayDecl.isPresent()) {
                checkPassed = isStringType(arrayDecl.get().getFirstChild());
            }
            else if (varargs.isPresent()) {
                checkPassed = isStringType(parameterType.getFirstChild());
            }
        }
        return checkPassed;
    }

    /**
     * Whether the type is java.lang.String.
     * @param typeAst the type to check.
     * @return true, if the type is java.lang.String.
     */
    private static boolean isStringType(DetailAST typeAst) {
        final FullIdent type = FullIdent.createFullIdent(typeAst);
        return "String".equals(type.getText())
            || "java.lang.String".equals(type.getText());
    }
}
