////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks that method parameter names conform to a specified pattern.
 * By using {@code accessModifiers} property it is possible
 * to specify different formats for methods at different visibility levels.
 * </p>
 * <p>
 * To validate {@code catch} parameters please use
 * <a href="https://checkstyle.org/config_naming.html#CatchParameterName">CatchParameterName</a>.
 * </p>
 * <p>
 * To validate lambda parameters please use
 * <a href="https://checkstyle.org/config_naming.html#LambdaParameterName">LambdaParameterName</a>.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specifies valid identifiers. Default value is
 * {@code "^[a-z][a-zA-Z0-9]*$"}.
 * </li>
 * <li>
 * Property {@code ignoreOverridden} - Allows to skip methods with Override annotation from
 * validation. For example, the following method's parameter will be skipped from validation,
 * if ignoreOverridden is true:
 * <pre>
 * &#64;Override
 * public boolean equals(Object o) {
 *   return super.equals(o);
 * }
 * </pre>
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code accessModifiers} - Access modifiers of methods where parameters are checked.
 * Default value is {@code public, protected, package, private}.
 * </li>
 * </ul>
 * <p>
 * An example of how to configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;ParameterName&quot;/&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   void method1(int v1) {} // OK
 *   void method2(int V2) {} // violation, name 'V2' must match pattern '^[a-z][a-zA-Z0-9]*$'
 * }
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with
 * a lower case letter, followed by letters, digits, and underscores:
 * </p>
 * <pre>
 * &lt;module name=&quot;ParameterName&quot;&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;^[a-z][_a-zA-Z0-9]+$&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   void method1(int v1) {} // OK
 *   void method2(int v_2) {} // OK
 *   void method3(int V3) {} // violation, name 'V3' must match pattern '^[a-z][_a-zA-Z0-9]+$'
 * }
 * </pre>
 * <p>
 * An example of how to configure the check to skip methods with Override annotation from
 * validation:
 * </p>
 * <pre>
 * &lt;module name=&quot;ParameterName&quot;&gt;
 *   &lt;property name=&quot;ignoreOverridden&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   void method1(int v1) {} // OK
 *   void method2(int V2) {} // violation, name 'V2' must match pattern '^[a-z][a-zA-Z0-9]*$'
 *   &#064;Override
 *   public boolean equals(Object V3) { // OK
 *       return true;
 *   }
 * }
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with a lower case letter, followed
 * by letters and digits is:
 * </p>
 * <pre>
 * &lt;module name=&quot;ParameterName&quot;&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;^[a-z][a-zA-Z0-9]+$&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   void method1(int v1) {} // OK
 *   void method2(int v_2) {} // violation, name 'v_2' must match pattern '^[a-z][a-zA-Z0-9]+$'
 *   void method3(int V3) {} // violation, name 'V3' must match pattern '^[a-z][a-zA-Z0-9]+$'
 * }
 * </pre>
 * <p>
 * The following configuration checks that the parameters always start with two lowercase
 * characters and, in addition, that public method parameters cannot be one character long:
 * </p>
 * <pre>
 * &lt;module name=&quot;ParameterName&quot;&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;^[a-z]([a-z0-9][a-zA-Z0-9]*)?$&quot;/&gt;
 *   &lt;property name=&quot;accessModifiers&quot;
 *     value=&quot;protected, package, private&quot;/&gt;
 *   &lt;message key=&quot;name.invalidPattern&quot;
 *     value=&quot;Parameter name ''{0}'' must match pattern ''{1}''&quot;/&gt;
 * &lt;/module&gt;
 * &lt;module name=&quot;ParameterName&quot;&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;^[a-z][a-z0-9][a-zA-Z0-9]*$&quot;/&gt;
 *   &lt;property name=&quot;accessModifiers&quot; value=&quot;public&quot;/&gt;
 *   &lt;message key=&quot;name.invalidPattern&quot;
 *     value=&quot;Parameter name ''{0}'' must match pattern ''{1}''&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   void method1(int v1) {} // OK
 *   protected method2(int V2) {} // violation, Parameter name 'V2'
 *                                // must match pattern '^[a-z]([a-z0-9][a-zA-Z0-9]*)?$'
 *   private method3(int a) {} // OK
 *   public method4(int b) {} // violation, Parameter name 'b'
 *                            // must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'
 * }
 * </pre>
 *
 * @since 3.0
 */
public class ParameterNameCheck extends AbstractNameCheck {

    /**
     * Allows to skip methods with Override annotation from validation. For example, the following
     * method's parameter will be skipped from validation, if ignoreOverridden is true:
     * <pre>
     * &#64;Override
     * public boolean equals(Object o) {
     *   return super.equals(o);
     * }
     * </pre>
     */
    private boolean ignoreOverridden;

    /** Access modifiers of methods where parameters are checked. */
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
     * Setter to allows to skip methods with Override annotation from validation. For example, the
     * following method's parameter will be skipped from validation, if ignoreOverridden is true:
     * <pre>
     * &#64;Override
     * public boolean equals(Object o) {
     *   return super.equals(o);
     * }
     * </pre>
     * @param ignoreOverridden Flag for skipping methods with Override annotation.
     */
    public void setIgnoreOverridden(boolean ignoreOverridden) {
        this.ignoreOverridden = ignoreOverridden;
    }

    /**
     * Setter to access modifiers of methods where parameters are checked.
     * @param accessModifiers access modifiers of methods which should be checked.
     */
    public void setAccessModifiers(AccessModifier... accessModifiers) {
        this.accessModifiers =
            Arrays.copyOf(accessModifiers, accessModifiers.length);
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
        return new int[] {TokenTypes.PARAMETER_DEF};
    }

    @Override
    protected boolean mustCheckName(DetailAST ast) {
        boolean checkName = true;
        if (ignoreOverridden && isOverriddenMethod(ast)
                || ast.getParent().getType() == TokenTypes.LITERAL_CATCH
                || ast.getParent().getParent().getType() == TokenTypes.LAMBDA
                || CheckUtil.isReceiverParameter(ast)
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
        final AccessModifier accessModifier;

        if (ScopeUtil.isInInterfaceOrAnnotationBlock(ast)) {
            accessModifier = AccessModifier.PUBLIC;
        }
        else {
            final DetailAST params = ast.getParent();
            final DetailAST meth = params.getParent();
            final DetailAST modsToken = meth.findFirstToken(TokenTypes.MODIFIERS);
            accessModifier = CheckUtil.getAccessModifierFromModifiersToken(modsToken);
        }

        return accessModifier;
    }

    /**
     * Checks whether a method has the correct access modifier to be checked.
     * @param accessModifier the access modifier of the method.
     * @return whether the method matches the expected access modifier.
     */
    private boolean matchAccessModifiers(final AccessModifier accessModifier) {
        return Arrays.stream(accessModifiers).anyMatch(modifier -> modifier == accessModifier);
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

        if (annotation.isPresent()) {
            final Optional<DetailAST> overrideToken =
                Optional.ofNullable(annotation.get().findFirstToken(TokenTypes.IDENT));
            if (overrideToken.isPresent() && "Override".equals(overrideToken.get().getText())) {
                overridden = true;
            }
        }
        return overridden;
    }

}
