////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks identifiers with a regular expression for a set of illegal names, such as those
 * that are restricted or contextual keywords. Examples include "yield", "record", "_", and
 * "var". Please read more at
 * <a href="https://docs.oracle.com/javase/specs/jls/se14/html/jls-3.html#jls-3.9">
 * Java Language Specification</a> to get to know more about restricted keywords. Since this
 * check uses a regular expression to specify valid identifiers, users can also prohibit the usage
 * of certain symbols, such as "$", or any non-ascii character.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specifies valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "(?i)^(?!(record|yield|var|permits|sealed|_)$).+$"}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_FIELD_DEF">
 * ANNOTATION_FIELD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PARAMETER_DEF">
 * PARAMETER_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_CONSTANT_DEF">
 * ENUM_CONSTANT_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PATTERN_VARIABLE_DEF">
 * PATTERN_VARIABLE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_COMPONENT_DEF">
 * RECORD_COMPONENT_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <p>Configuration:</p>
 * <pre>
 * &lt;module name="IllegalIdentifierName"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class TestClass {
 *     public static void main(String... args) {
 *         var var = 4; // violation, "var" should not be used as an identifier.
 *         int record = 15; // violation, "record" should not be used as an identifier.
 *         String yield = "yield"; // violation, "yield" should not be used as an identifier.
 *
 *         record Record // violation, "Record" should not be used as an identifier.
 *             (Record record) { // violation, "record" should not be used as an identifier.
 *         }
 *
 *         String yieldString = "yieldString"; // ok, part of another word
 *         record MyRecord(){} // ok, part of another word
 *         var variable = 2; // ok, part of another word
 *         String _; // violation, underscore should not be used as an identifier.
 *     }
 * }
 * </pre>
 * <p>
 * To configure the check to include "open" and "transitive" in the set of illegal identifiers:
 * </p>
 * <p>Configuration:</p>
 * <pre>
 * &lt;module name="IllegalIdentifierName"&gt;
 *     &lt;property name="format" value="(?i)^(?!(record|yield|var
 *                        |permits|sealed|open|transitive|_)$).+$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class TestClass {
 *     public static void main(String... args) {
 *
 *         int open = 4; // violation, "open" should not be used as an identifier
 *         Object transitive = "transitive"; // violation, "transitive" should not
 *                                                // be used as an identifier
 *
 *         int openInt = 4; // ok, "open" is part of another word
 *         Object transitiveObject = "transitiveObject"; // ok, "transitive" is part of another word
 *     }
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
 * @since 8.36
 */
@StatelessCheck
public class IllegalIdentifierNameCheck extends AbstractNameCheck {

    /**
     * Creates a new {@code IllegalIdentifierNameCheck} instance.
     */
    public IllegalIdentifierNameCheck() {
        super("(?i)^(?!(record|yield|var|permits|sealed|_)$).+$");
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.PATTERN_VARIABLE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.RECORD_COMPONENT_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    protected boolean mustCheckName(DetailAST ast) {
        return ast.findFirstToken(TokenTypes.IDENT) != null;
    }

}
