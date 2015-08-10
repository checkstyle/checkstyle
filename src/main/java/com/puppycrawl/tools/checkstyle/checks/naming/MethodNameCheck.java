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

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.AnnotationUtility;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that method names conform to a format specified
 * by the format property. The format is a
 * {@link java.util.regex.Pattern regular expression}
 * and defaults to
 * <strong>^[a-z][a-zA-Z0-9]*$</strong>.
 * </p>
 *
 * <p>
 * Also, checks if a method name has the same name as the residing class.
 * The default is false (it is not allowed).  It is legal in Java to have
 * method with the same name as a class.  As long as a return type is specified
 * it is a method and not a constructor which it could be easily confused as.
 * <h3>Does not check-style the name of an overriden methods</h3> because the developer does not
 * have a choice in renaming such methods.
 *
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="MethodName"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with
 * a lower case letter, followed by letters, digits, and underscores is:
 * </p>
 * <pre>
 * &lt;module name="MethodName"&gt;
 *    &lt;property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * An example of how to configure the check to allow method names
 * to be equal to the residing class name is:
 * </p>
 * <pre>
 * &lt;module name="MethodName"&gt;
 *    &lt;property name="allowClassName" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Oliver Burn
 * @author Travis Schneeberger
 * @author Utkarsh Srivastava
 */
public class MethodNameCheck
    extends AbstractAccessControlNameCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "method.name.equals.class.name";

    /**
     * {@link Override Override} annotation name.
     */
    private static final String OVERRIDE = "Override";

    /**
     * Canonical {@link Override Override} annotation name.
     */
    private static final String CANONICAL_OVERRIDE = "java.lang." + OVERRIDE;

    /**
     * for allowing method name to be the same as the class name.
     */
    private boolean allowClassName;

    /** Creates a new {@code MethodNameCheck} instance. */
    public MethodNameCheck() {
        super("^[a-z][a-zA-Z0-9]*$");
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.METHOD_DEF, };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.METHOD_DEF, };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!AnnotationUtility.containsAnnotation(ast, OVERRIDE)
            && !AnnotationUtility.containsAnnotation(ast, CANONICAL_OVERRIDE)) {
            super.visitToken(ast); // Will check the name against the format.
        }

        if (!allowClassName) {
            final DetailAST method =
                ast.findFirstToken(TokenTypes.IDENT);
            //in all cases this will be the classDef type except anon inner
            //with anon inner classes this will be the Literal_New keyword
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
                log(method.getLineNo(), method.getColumnNo(),
                    MSG_KEY, method.getText());
            }
        }
    }

    /**
     * Sets the property for allowing a method to be the same name as a class.
     * @param allowClassName true to allow false to disallow
     */
    public void setAllowClassName(boolean allowClassName) {
        this.allowClassName = allowClassName;
    }
}
