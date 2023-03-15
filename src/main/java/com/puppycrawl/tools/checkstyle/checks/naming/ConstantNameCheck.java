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
 * Checks that constant names conform to a specified pattern.
 * A <em>constant</em> is a <strong>static</strong> and <strong>final</strong>
 * field or an interface/annotation field, except
 * <strong>serialVersionUID</strong> and <strong>serialPersistentFields
 * </strong>.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specifies valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"}.
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
 * &lt;module name="ConstantName"/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class MyClass {
 *   public final static int FIRST_CONSTANT1 = 10; // OK
 *   protected final static int SECOND_CONSTANT2 = 100; // OK
 *   final static int third_Constant3 = 1000; // violation, name 'third_Constant3' must
 *                                           // match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'
 *   private final static int fourth_Const4 = 50; // violation, name 'fourth_Const4' must match
 *                                                 // pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'
 * }
 * </pre>
 * <p>
 * The following configuration apart from names allowed by default allows {@code log}
 * or {@code logger}:
 * </p>
 * <pre>
 * &lt;module name=&quot;ConstantName&quot;&gt;
 *   &lt;property name=&quot;format&quot;
 *     value=&quot;^log(ger)?$|^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   final static int log = 10; // OK
 *   final static int logger = 50; // OK
 *   final static int logMYSELF = 10; // violation, name 'logMYSELF' must match
 *                                    // pattern '^log(ger)?$|^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'
 *   final static int loggerMYSELF = 5; // violation, name 'loggerMYSELF' must match
 *                                      // pattern '^log(ger)?$|^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'
 *   final static int MYSELF = 100; // OK
 *   final static int myselfConstant = 1; // violation, name 'myselfConstant' must match pattern
 *                                        // '^log(ger)?$|^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'
 * }
 * </pre>
 * <p>
 * The following configuration skip validation on
 * public constant field and protected constant field.
 * </p>
 * <pre>
 * &lt;module name=&quot;ConstantName&quot;&gt;
 *   &lt;property name="applyToPublic" value="false"/&gt;
 *   &lt;property name="applyToProtected" value="false"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   public final static int firstConstant = 10; // OK
 *   protected final static int secondConstant = 100; // OK
 *   final static int thirdConstant = 1000; // violation, name 'thirdConstant' must
 *                                          // match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'
 *   private final static int fourthConstant = 50; // violation, name 'fourthConstant' must match
 *                                                 // pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'
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
public class ConstantNameCheck
    extends AbstractAccessControlNameCheck {

    /** Creates a new {@code ConstantNameCheck} instance. */
    public ConstantNameCheck() {
        super("^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$");
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
        boolean returnValue = false;

        final DetailAST modifiersAST =
            ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isStaticFinal =
            modifiersAST.findFirstToken(TokenTypes.LITERAL_STATIC) != null
                && modifiersAST.findFirstToken(TokenTypes.FINAL) != null
            || ScopeUtil.isInAnnotationBlock(ast)
            || ScopeUtil.isInInterfaceBlock(ast);
        if (isStaticFinal && shouldCheckInScope(modifiersAST)
                        && !ScopeUtil.isInCodeBlock(ast)) {
            // Handle the serialVersionUID and serialPersistentFields constants
            // which are used for Serialization. Cannot enforce rules on it. :-)
            final DetailAST nameAST = ast.findFirstToken(TokenTypes.IDENT);
            if (!"serialVersionUID".equals(nameAST.getText())
                && !"serialPersistentFields".equals(nameAST.getText())) {
                returnValue = true;
            }
        }

        return returnValue;
    }

}
