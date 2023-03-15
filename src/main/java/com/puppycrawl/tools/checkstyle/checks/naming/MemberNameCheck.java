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
 * Checks that instance variable names conform to a specified pattern.
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
 * &lt;module name=&quot;MemberName&quot;/&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   public int num1; // OK
 *   protected int num2; // OK
 *   final int num3 = 3; // OK
 *   private int num4; // OK
 *
 *   static int num5; // ignored: not an instance variable
 *   public static final int CONSTANT = 1; // ignored: not an instance variable
 *
 *   public int NUM1; // violation, name 'NUM1'
 *                    // must match pattern '^[a-z][a-zA-Z0-9]*$'
 *   protected int NUM2; // violation, name 'NUM2'
 *                       // must match pattern '^[a-z][a-zA-Z0-9]*$'
 *   final int NUM3; // violation, name 'NUM3'
 *                   // must match pattern '^[a-z][a-zA-Z0-9]*$'
 *   private int NUM4; // violation, name 'NUM4'
 *                     // must match pattern '^[a-z][a-zA-Z0-9]*$'
 * }
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with
 * {@code &quot;m&quot;}, followed by an upper case letter, and then letters
 * and digits. Also, suppress the check from being applied to protected
 * and package-private member:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;MemberName&quot;&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;^m[A-Z][a-zA-Z0-9]*$&quot;/&gt;
 *   &lt;property name=&quot;applyToProtected&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;applyToPackage&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   public int num1; // violation, name 'num1'
 *                    // must match pattern '^m[A-Z][a-zA-Z0-9]*$'
 *   protected int num2; // OK
 *   int num3; // OK
 *   private int num4; // violation, name 'num4'
 *                     // must match pattern '^m[A-Z][a-zA-Z0-9]*$'
 * }
 * </pre>
 * <p>
 * An example of how to suppress the check which is applied to
 * public and private member:
 * </p>
 * <pre>
 * &lt;module name=&quot;MemberName&quot;&gt;
 *   &lt;property name=&quot;applyToPublic&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;applyToPrivate&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   public int NUM1; // OK
 *   protected int NUM2; // violation, name 'NUM2'
 *                       // must match pattern '^[a-z][a-zA-Z0-9]*$'
 *   int NUM3; // violation, name 'NUM3'
 *             // must match pattern '^[a-z][a-zA-Z0-9]*$'
 *   private int NUM4; // OK
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
public class MemberNameCheck
    extends AbstractAccessControlNameCheck {

    /** Creates a new {@code MemberNameCheck} instance. */
    public MemberNameCheck() {
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

        return !isStatic && !ScopeUtil.isInInterfaceOrAnnotationBlock(ast)
            && !ScopeUtil.isLocalVariableDef(ast)
                && shouldCheckInScope(modifiersAST);
    }

}
