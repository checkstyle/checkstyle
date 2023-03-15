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

import java.util.Objects;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks lambda parameter names.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specifies valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^[a-z][a-zA-Z0-9]*$"}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="LambdaParameterName"/&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * Function&lt;String, String&gt; function1 = s -&gt; s.toLowerCase(); // OK
 * Function&lt;String, String&gt; function2 = S -&gt; S.toLowerCase(); // violation, name 'S'
 *                                                // must match pattern '^[a-z][a-zA-Z0-9]*$'
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin
 * with a lower case letter, followed by letters is:
 * </p>
 * <pre>
 * &lt;module name="LambdaParameterName"&gt;
 *   &lt;property name="format" value="^[a-z]([a-zA-Z]+)*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code Example:
 * </p>
 * <pre>
 * class MyClass {
 *   Function&lt;String, String&gt; function1 = str -&gt; str.toUpperCase().trim(); // OK
 *   Function&lt;String, String&gt; function2 = _s -&gt; _s.trim(); // violation, name '_s'
 *                                              // must match pattern '^[a-z]([a-zA-Z]+)*$'
 *
 *   public boolean myMethod(String sentence) {
 *     return Stream.of(sentence.split(" "))
 *             .map(word -&gt; word.trim()) // OK
 *             .anyMatch(Word -&gt; "in".equals(Word)); // violation, name 'Word'
 *                                                      // must match pattern '^[a-z]([a-zA-Z]+)*$'
 *   }
 * }
 *
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
 * @since 8.11
 */
public class LambdaParameterNameCheck extends AbstractNameCheck {

    /** Creates new instance of {@code LambdaParameterNameCheck}. */
    public LambdaParameterNameCheck() {
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
        return new int[] {
            TokenTypes.LAMBDA,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final boolean isInSwitchRule = ast.getParent().getType() == TokenTypes.SWITCH_RULE;

        if (Objects.nonNull(ast.findFirstToken(TokenTypes.PARAMETERS))) {
            final DetailAST parametersNode = ast.findFirstToken(TokenTypes.PARAMETERS);
            TokenUtil.forEachChild(parametersNode, TokenTypes.PARAMETER_DEF, super::visitToken);
        }
        else if (!isInSwitchRule) {
            super.visitToken(ast);
        }
    }

    @Override
    protected boolean mustCheckName(DetailAST ast) {
        return true;
    }

}
