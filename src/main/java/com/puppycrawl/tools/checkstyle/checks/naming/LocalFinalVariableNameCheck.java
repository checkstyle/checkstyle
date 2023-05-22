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
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks that local final variable names conform to a specified pattern.
 *  A catch parameter and resources in try statements
 * are considered to be a local, final variables.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specifies valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^[a-z][a-zA-Z0-9]*$"}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PARAMETER_DEF">
 * PARAMETER_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RESOURCE">
 * RESOURCE</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="LocalFinalVariableName"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for names that are only upper case
 * letters and digits is:
 * </p>
 * <pre>
 * &lt;module name="LocalFinalVariableName"&gt;
 *   &lt;property name="format" value="^[A-Z][A-Z0-9]*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   void MyMethod() {
 *     try {
 *       final int VAR1 = 5; // OK
 *       final int var1 = 10; // violation,  name 'var1' must match pattern "^[A-Z][A-Z0-9]*$"
 *     } catch (Exception ex) {
 *       final int VAR2 = 15; // OK
 *       final int var2 = 20; // violation,  name 'var2' must match pattern "^[A-Z][A-Z0-9]*$"
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * An example of how to configure the check for names of local final parameters and
 * resources in try statements (without checks on variables):
 * </p>
 * <pre>
 * &lt;module name=&quot;LocalFinalVariableName&quot;&gt;
 *   &lt;property name="format" value="^[A-Z][A-Z0-9]*$"/&gt;
 *   &lt;property name="tokens" value="PARAMETER_DEF,RESOURCE"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   void MyMethod() {
 *     try(Scanner scanner = new Scanner()) { // violation, name 'scanner' must
 *                                            // match pattern '^[A-Z][A-Z0-9]*$'
 *       final int VAR1 = 5; // OK
 *       final int var1 = 10; // OK
 *     } catch (final Exception ex) { // violation, name 'ex'
 *                                    // must match pattern '^[A-Z][A-Z0-9]*$'
 *       final int VAR2 = 15; // OK
 *       final int var2 = 20; // OK
 *     }
 *   }
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
public class LocalFinalVariableNameCheck
    extends AbstractNameCheck {

    /** Creates a new {@code LocalFinalVariableNameCheck} instance. */
    public LocalFinalVariableNameCheck() {
        super("^[a-z][a-zA-Z0-9]*$");
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.RESOURCE,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    protected final boolean mustCheckName(DetailAST ast) {
        final DetailAST modifiersAST =
            ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isFinal = ast.getType() == TokenTypes.RESOURCE
            || modifiersAST.findFirstToken(TokenTypes.FINAL) != null;
        return isFinal && ScopeUtil.isLocalVariableDef(ast);
    }

}
