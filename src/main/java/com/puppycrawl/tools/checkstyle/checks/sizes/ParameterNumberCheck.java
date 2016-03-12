////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtility;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * Checks the number of parameters that a method or constructor has.
 * The default allowable number of parameters is 7.
 * To change the number of allowable parameters, set property max.
 * Allows to ignore number of parameters for methods with
 * &#064;{@link Override} annotation.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="ParameterNumber"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check to allow 10 parameters
 * and ignoring parameters for methods with &#064;{@link Override}
 * annotation is:
 * </p>
 * <pre>
 * &lt;module name="ParameterNumber"&gt;
 *    &lt;property name="max" value="10"/&gt;
 *    &lt;property name="ignoreOverriddenMethods" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * Java code that will be ignored:
 * <pre>
 * {@code
 *
 *  &#064;Override
 *  public void needsLotsOfParameters(int a,
 *      int b, int c, int d, int e, int f, int g, int h) {
 *      ...
 *  }
 * }
 * </pre>
 * @author Oliver Burn
 */
public class ParameterNumberCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxParam";

    /** {@link Override Override} annotation name. */
    private static final String OVERRIDE = "Override";

    /** Canonical {@link Override Override} annotation name. */
    private static final String CANONICAL_OVERRIDE = "java.lang." + OVERRIDE;

    /** Default maximum number of allowed parameters. */
    private static final int DEFAULT_MAX_PARAMETERS = 7;

    /** The maximum number of allowed parameters. */
    private int max = DEFAULT_MAX_PARAMETERS;

    /** Ignore overridden methods. */
    private boolean ignoreOverriddenMethods;

    /**
     * Sets the maximum number of allowed parameters.
     * @param max the max allowed parameters
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Ignore number of parameters for methods with
     * &#064;{@link Override} annotation.
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
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
        final int count = params.getChildCount(TokenTypes.PARAMETER_DEF);
        if (count > max && !shouldIgnoreNumberOfParameters(ast)) {
            final DetailAST name = ast.findFirstToken(TokenTypes.IDENT);
            log(name.getLineNo(), name.getColumnNo(), MSG_KEY, max, count);
        }
    }

    /** Determine whether to ignore number of parameters for the method.
     *
     * @param ast the token to process
     * @return true if this is overridden method and number of parameters should be ignored
     *         false otherwise
     */
    private boolean shouldIgnoreNumberOfParameters(DetailAST ast) {
        //if you override a method, you have no power over the number of parameters
        return ignoreOverriddenMethods
                && (AnnotationUtility.containsAnnotation(ast, OVERRIDE)
                || AnnotationUtility.containsAnnotation(ast, CANONICAL_OVERRIDE));
    }
}
