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

package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.Arrays;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtils;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

/**
 * <p>
 * Checks that method and <code>catch</code> parameter names conform to a format specified
 * by the format property. The format is a
 * {@link java.util.regex.Pattern regular expression}
 * and defaults to
 * <strong>^[a-z][a-zA-Z0-9]*$</strong>.
 * </p>
 * <p>The check has the following options:</p>
 * <p><b>ignoreOverridden</b> - allows to skip methods with Override annotation from
 * validation. Default values is <b>false</b> .</p>
 * <p><b>accessModifiers</b> - access modifiers of methods which should to be checked.
 * Default value is <b>public, protected, package, private</b> .</p>
 * An example of how to configure the check:
 * <pre>
 * &lt;module name="ParameterName"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with
 * a lower case letter, followed by letters, digits, and underscores:
 * </p>
 * <pre>
 * &lt;module name="ParameterName"&gt;
 *    &lt;property name="format" value="^[a-z][_a-zA-Z0-9]+$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * An example of how to configure the check to skip methods with Override annotation from
 * validation:
 * </p>
 * <pre>
 * &lt;module name="ParameterName"&gt;
 *    &lt;property name="ignoreOverridden" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Oliver Burn
 * @author Andrei Selkin
 */
public class ParameterNameCheck extends AbstractNameCheck {

    /**
     * Allows to skip methods with Override annotation from validation.
     */
    private boolean ignoreOverridden;

    /** Access modifiers of methods which should be checked. */
    private AccessModifier[] accessModifiers = {
        AccessModifier.PUBLIC,
        AccessModifier.PROTECTED,
        AccessModifier.PACKAGE,
        AccessModifier.PRIVATE,
    };

    /**
     * Creates a new {@code ParameterNameCheck} instance.
     */
    public ParameterNameCheck() {
        super("^[a-z][a-zA-Z0-9]*$");
    }

    /**
     * Sets whether to skip methods with Override annotation from validation.
     * @param ignoreOverridden Flag for skipping methods with Override annotation.
     */
    public void setIgnoreOverridden(boolean ignoreOverridden) {
        this.ignoreOverridden = ignoreOverridden;
    }

    /**
     * Sets access modifiers of methods which should be checked.
     * @param accessModifiers access modifiers of methods which should be checked.
     */
    public void setAccessModifiers(AccessModifier... accessModifiers) {
        this.accessModifiers =
            Arrays.copyOf(accessModifiers, accessModifiers.length);
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.PARAMETER_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    protected boolean mustCheckName(DetailAST ast) {
        boolean checkName = true;
        if (ignoreOverridden && isOverriddenMethod(ast)
                || ast.getParent().getType() == TokenTypes.LITERAL_CATCH
                || CheckUtils.isReceiverParameter(ast)
                || !matchAccessModifiers(getAccessModifier(ast))) {
            checkName = false;
        }
        return checkName;
    }

    /**
     * Returns the access modifier of the method/constructor at the specified AST. If
     * the method is in an interface or annotation block, the access modifier is assumed
     * to be public.
     *
     * @param ast the token of the method/constructor.
     * @return the access modifier of the method/constructor.
     */
    private static AccessModifier getAccessModifier(final DetailAST ast) {
        final DetailAST params = ast.getParent();
        final DetailAST meth = params.getParent();
        AccessModifier accessModifier = AccessModifier.PRIVATE;

        if (meth.getType() == TokenTypes.METHOD_DEF
                || meth.getType() == TokenTypes.CTOR_DEF) {
            if (ScopeUtils.isInInterfaceOrAnnotationBlock(ast)) {
                accessModifier = AccessModifier.PUBLIC;
            }
            else {
                final DetailAST modsToken = meth.findFirstToken(TokenTypes.MODIFIERS);
                accessModifier = CheckUtils.getAccessModifierFromModifiersToken(modsToken);
            }
        }

        return accessModifier;
    }

    /**
     * Checks whether a method has the correct access modifier to be checked.
     * @param accessModifier the access modifier of the method.
     * @return whether the method matches the expected access modifier.
     */
    private boolean matchAccessModifiers(final AccessModifier accessModifier) {
        return Arrays.stream(accessModifiers).anyMatch(el -> el == accessModifier);
    }

    /**
     * Checks whether a method is annotated with Override annotation.
     * @param ast method parameter definition token.
     * @return true if a method is annotated with Override annotation.
     */
    private static boolean isOverriddenMethod(DetailAST ast) {
        boolean overridden = false;

        final DetailAST parent = ast.getParent().getParent();
        final Optional<DetailAST> annotation =
            Optional.ofNullable(parent.getFirstChild().getFirstChild());

        if (annotation.isPresent()
                && annotation.get().getType() == TokenTypes.ANNOTATION) {
            final Optional<DetailAST> overrideToken =
                Optional.ofNullable(annotation.get().findFirstToken(TokenTypes.IDENT));
            if (overrideToken.isPresent() && "Override".equals(overrideToken.get().getText())) {
                overridden = true;
            }
        }
        return overridden;
    }
}
