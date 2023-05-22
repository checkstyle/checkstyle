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

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks that {@code static}, non-{@code final} variable names conform to a specified pattern.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specifies valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^[a-z][a-zA-Z0-9]*$"}.
 * </li>
 * <li>
 * Property {@code applyToPublic} - Controls whether to apply the check to public member.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code applyToProtected} - Controls whether to apply the check to protected member.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code applyToPackage} - Controls whether to apply the check to package-private member.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code applyToPrivate} - Controls whether to apply the check to private member.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;StaticVariableName&quot;/&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   public static int goodStatic = 2; // OK
 *   private static int BadStatic = 2; // violation, name 'BadStatic'
 *                                     // must match pattern '^[a-z][a-zA-Z0-9]*$'
 * }
 * </pre>
 * <p>
 * An example of how to suppress the check to public and protected types is:
 * </p>
 * <pre>
 * &lt;module name=&quot;StaticVariableName&quot;&gt;
 *   &lt;property name=&quot;applyToPublic&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;applyToProtected&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   public static int GoodStatic1 = 2; // OK
 *   protected static int GoodStatic2 = 2; //OK
 *   private static int goodStatic = 2 // OK
 *   private static int BadStatic = 2; // violation, name 'BadStatic'
 *                                     // must match pattern '^[a-z][a-zA-Z0-9]*$'
 * }
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with
 * a lower case letter, followed by letters, digits, and underscores.
 * Also, suppress the check from being applied to private and package-private types:
 * </p>
 * <pre>
 * &lt;module name=&quot;StaticVariableName&quot;&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;^[a-z](_?[a-zA-Z0-9]+)*$&quot;/&gt;
 *   &lt;property name=&quot;applyToPrivate&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;applyToPackage&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   public static int good_static = 2; // OK
 *   public static int Bad_Static = 2; // violation, name 'Bad_Static'
 *                                     // must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
 *   private static int Good_Static1 = 2; // OK
 *   static int Good_Static2 = 2; // OK
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
 * {@code name.invalidPattern}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
public class StaticVariableNameCheck
    extends AbstractAccessControlNameCheck {

    /** Creates a new {@code StaticVariableNameCheck} instance. */
    public StaticVariableNameCheck() {
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
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    protected final boolean mustCheckName(DetailAST ast) {
        final DetailAST modifiersAST =
            ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isStatic = modifiersAST.findFirstToken(TokenTypes.LITERAL_STATIC) != null;
        final boolean isFinal = modifiersAST.findFirstToken(TokenTypes.FINAL) != null;

        return isStatic
                && !isFinal
                && shouldCheckInScope(modifiersAST)
                && !ScopeUtil.isInInterfaceOrAnnotationBlock(ast);
    }

}
