////
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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Detects uncommented {@code main} methods.
 * </div>
 *
 * <p>
 * Rationale: A {@code main} method is often used for debugging purposes.
 * When debugging is finished, developers often forget to remove the method,
 * which changes the API and increases the size of the resulting class or JAR file.
 * Except for the real program entry points, all {@code main} methods
 * should be removed or commented out of the sources.
 * </p>
 * <ul>
 * <li>
 * Property {@code excludedClasses} - Specify pattern for qualified names of
 * classes which are allowed to have a {@code main} method.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^$"}.
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
 * {@code uncommented.main}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@FileStatefulCheck
public class UncommentedMainCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "uncommented.main";

    /** Set of possible String array types. */
    private static final Set<String> STRING_PARAMETER_NAMES = Set.of(
        String[].class.getCanonicalName(),
        String.class.getCanonicalName(),
        String[].class.getSimpleName(),
        String.class.getSimpleName()
    );

    /**
     * Specify pattern for qualified names of classes which are allowed to
     * have a {@code main} method.
     */
    private Pattern excludedClasses = Pattern.compile("^$");
    /** Current class name. */
    private String currentClass;
    /** Current package. */
    private FullIdent packageName;
    /** Class definition depth. */
    private int classDepth;

    /**
     * Setter to specify pattern for qualified names of classes which are allowed
     * to have a {@code main} method.
     *
     * @param excludedClasses a pattern
     * @since 3.2
     */
    public void setExcludedClasses(Pattern excludedClasses) {
        this.excludedClasses = excludedClasses;
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        packageName = FullIdent.createFullIdent(null);
        classDepth = 0;
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.CLASS_DEF) {
            classDepth--;
        }
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.PACKAGE_DEF:
                visitPackageDef(ast);
                break;
            case TokenTypes.RECORD_DEF:
            case TokenTypes.CLASS_DEF:
                visitClassOrRecordDef(ast);
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
     *
     * @param packageDef node for package definition
     */
    private void visitPackageDef(DetailAST packageDef) {
        packageName = FullIdent.createFullIdent(packageDef.getLastChild()
            .getPreviousSibling());
    }

    /**
     * If not inner class then change current class name.
     *
     * @param classOrRecordDef node for class or record definition
     */
    private void visitClassOrRecordDef(DetailAST classOrRecordDef) {
        // we are not use inner classes because they can not
        // have static methods
        if (classDepth == 0) {
            final DetailAST ident = classOrRecordDef.findFirstToken(TokenTypes.IDENT);
            currentClass = packageName.getText() + "." + ident.getText();
            classDepth++;
        }
    }

    /**
     * Checks method definition if this is
     * {@code public static void main(String[])}.
     *
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
            log(method, MSG_KEY);
        }
    }

    /**
     * Checks that current class is not excluded.
     *
     * @return true if check passed, false otherwise
     */
    private boolean checkClassName() {
        return !excludedClasses.matcher(currentClass).find();
    }

    /**
     * Checks that method name is @quot;main@quot;.
     *
     * @param method the METHOD_DEF node
     * @return true if check passed, false otherwise
     */
    private static boolean checkName(DetailAST method) {
        final DetailAST ident = method.findFirstToken(TokenTypes.IDENT);
        return "main".equals(ident.getText());
    }

    /**
     * Checks that method has final and static modifiers.
     *
     * @param method the METHOD_DEF node
     * @return true if check passed, false otherwise
     */
    private static boolean checkModifiers(DetailAST method) {
        final DetailAST modifiers =
            method.findFirstToken(TokenTypes.MODIFIERS);

        return modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) != null
            && modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null;
    }

    /**
     * Checks that return type is {@code void}.
     *
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
     *
     * @param method the METHOD_DEF node
     * @return true if check passed, false otherwise
     */
    private static boolean checkParams(DetailAST method) {
        boolean checkPassed = false;
        final DetailAST params = method.findFirstToken(TokenTypes.PARAMETERS);

        if (params.getChildCount() == 1) {
            final DetailAST parameterType = params.getFirstChild().findFirstToken(TokenTypes.TYPE);
            final boolean isArrayDeclaration =
                parameterType.findFirstToken(TokenTypes.ARRAY_DECLARATOR) != null;
            final Optional<DetailAST> varargs = Optional.ofNullable(
                params.getFirstChild().findFirstToken(TokenTypes.ELLIPSIS));

            if (isArrayDeclaration || varargs.isPresent()) {
                checkPassed = isStringType(parameterType.getFirstChild());
            }
        }
        return checkPassed;
    }

    /**
     * Whether the type is java.lang.String.
     *
     * @param typeAst the type to check.
     * @return true, if the type is java.lang.String.
     */
    private static boolean isStringType(DetailAST typeAst) {
        final FullIdent type = FullIdent.createFullIdent(typeAst);
        return STRING_PARAMETER_NAMES.contains(type.getText());
    }

}
