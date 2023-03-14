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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks the number of parameters of a method or constructor.
 * </p>
 * <ul>
 * <li>
 * Property {@code max} - Specify the maximum number of parameters allowed.
 * Type is {@code int}.
 * Default value is {@code 7}.
 * </li>
 * <li>
 * Property {@code ignoreOverriddenMethods} - Ignore number of parameters for
 * methods with {@code @Override} annotation.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="ParameterNumber"/&gt;
 * </pre>
 * <p>
 * To configure the check to allow 10 parameters for a method:
 * </p>
 * <pre>
 * &lt;module name="ParameterNumber"&gt;
 *   &lt;property name="max" value="10"/&gt;
 *   &lt;property name="tokens" value="METHOD_DEF"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to ignore number of parameters for methods with
 * {@code @Override} or {@code @java.lang.Override annotation}.
 * </p>
 * <p>
 * Rationale: developer may need to override method with many parameters from
 * 3-rd party library. In this case developer has no control over number of parameters.
 * </p>
 * <pre>
 * &lt;module name="ParameterNumber"&gt;
 *   &lt;property name="ignoreOverriddenMethods" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Java code example:
 * </p>
 * <pre>
 * &#064;Override
 * public void needsLotsOfParameters(int a,
 *     int b, int c, int d, int e, int f, int g, int h) {
 *     ...
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
 * {@code maxParam}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class ParameterNumberCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxParam";

    /** Default maximum number of allowed parameters. */
    private static final int DEFAULT_MAX_PARAMETERS = 7;

    /** Specify the maximum number of parameters allowed. */
    private int max = DEFAULT_MAX_PARAMETERS;

    /** Ignore number of parameters for methods with {@code @Override} annotation. */
    private boolean ignoreOverriddenMethods;

    /**
     * Setter to specify the maximum number of parameters allowed.
     *
     * @param max the max allowed parameters
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Setter to ignore number of parameters for methods with {@code @Override} annotation.
     *
     * @param ignoreOverriddenMethods set ignore overridden methods
     */
    public void setIgnoreOverriddenMethods(boolean ignoreOverriddenMethods) {
        this.ignoreOverriddenMethods = ignoreOverriddenMethods;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
        final int count = params.getChildCount(TokenTypes.PARAMETER_DEF);
        if (count > max && !shouldIgnoreNumberOfParameters(ast)) {
            final DetailAST name = ast.findFirstToken(TokenTypes.IDENT);
            log(name, MSG_KEY, max, count);
        }
    }

    /**
     * Determine whether to ignore number of parameters for the method.
     *
     * @param ast the token to process
     * @return true if this is overridden method and number of parameters should be ignored
     *         false otherwise
     */
    private boolean shouldIgnoreNumberOfParameters(DetailAST ast) {
        // if you override a method, you have no power over the number of parameters
        return ignoreOverriddenMethods
                && AnnotationUtil.hasOverrideAnnotation(ast);
    }

}
