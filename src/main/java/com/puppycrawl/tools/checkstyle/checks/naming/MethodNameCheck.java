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

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

/**
 * <div>
 * Checks that method names conform to a specified pattern.
 * </div>
 *
 * <p>Also, checks if a method name has the same name as the residing class.
 * The default is false (it is not allowed). It is legal in Java to have
 * method with the same name as a class. As long as a return type is specified
 * it is a method and not a constructor which it could be easily confused as.
 * Does not check-style the name of an overridden methods because the developer does not
 * have a choice in renaming such methods.
 * </p>
 *
 * <ul>
 * <li>
 * Property {@code allowClassName} - Control whether to allow a method name to have the same name
 * as the enclosing class name. Setting this property {@code false} helps to avoid
 * confusion between constructors and methods.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code applyToPackage} - Control if check should apply to package-private members.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code applyToPrivate} - Control if check should apply to private members.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code applyToProtected} - Control if check should apply to protected members.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code applyToPublic} - Control if check should apply to public members.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code format} - Sets the pattern to match valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^[a-z][a-zA-Z0-9]*$"}.
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
 * {@code method.name.equals.class.name}
 * </li>
 * <li>
 * {@code name.invalidPattern}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
public class MethodNameCheck
    extends AbstractAccessControlNameCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "method.name.equals.class.name";

    /**
     * Control whether to allow a method name to have the same name as the enclosing class name.
     * Setting this property {@code false} helps to avoid confusion
     * between constructors and methods.
     */
    private boolean allowClassName;

    /** Creates a new {@code MethodNameCheck} instance. */
    public MethodNameCheck() {
        super("^[a-z][a-zA-Z0-9]*$");
    }

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
        return new int[] {TokenTypes.METHOD_DEF,};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!AnnotationUtil.hasOverrideAnnotation(ast)) {
            // Will check the name against the format.
            super.visitToken(ast);
        }

        if (!allowClassName) {
            final DetailAST method =
                ast.findFirstToken(TokenTypes.IDENT);
            // in all cases this will be the classDef type except anon inner
            // with anon inner classes this will be the Literal_New keyword
            final DetailAST classDefOrNew = ast.getParent().getParent();
            final DetailAST classIdent =
                classDefOrNew.findFirstToken(TokenTypes.IDENT);
            // Following logic is to handle when a classIdent can not be
            // found. This is when you have a Literal_New keyword followed
            // a DOT, which is when you have:
            // new Outclass.InnerInterface(x) { ... }
            // Such a rare case, will not have the logic to handle parsing
            // down the tree looking for the first ident.
            if (classIdent != null
                && method.getText().equals(classIdent.getText())) {
                log(method, MSG_KEY, method.getText());
            }
        }
    }

    /**
     * Setter to control whether to allow a method name to have the same name
     * as the enclosing class name. Setting this property {@code false}
     * helps to avoid confusion between constructors and methods.
     *
     * @param allowClassName true to allow false to disallow
     * @since 5.0
     */
    public void setAllowClassName(boolean allowClassName) {
        this.allowClassName = allowClassName;
    }

}
