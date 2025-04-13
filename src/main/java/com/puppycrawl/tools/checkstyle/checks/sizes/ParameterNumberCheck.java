////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.sizes;

import java.util.Collections;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks the number of parameters of a method or constructor.
 * </div>
 * <ul>
 * <li>
 * Property {@code ignoreAnnotatedBy} - Ignore methods and constructors
 * annotated with the specified annotation(s).
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code ignoreOverriddenMethods} - Ignore number of parameters for
 * methods with {@code @Override} annotation.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code max} - Specify the maximum number of parameters allowed.
 * Type is {@code int}.
 * Default value is {@code 7}.
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
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
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
     * Ignore methods and constructors annotated with the specified annotation(s).
     */
    private Set<String> ignoreAnnotatedBy = Collections.emptySet();

    /**
     * Setter to specify the maximum number of parameters allowed.
     *
     * @param max the max allowed parameters
     * @since 3.0
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Setter to ignore number of parameters for methods with {@code @Override} annotation.
     *
     * @param ignoreOverriddenMethods set ignore overridden methods
     * @since 6.2
     */
    public void setIgnoreOverriddenMethods(boolean ignoreOverriddenMethods) {
        this.ignoreOverriddenMethods = ignoreOverriddenMethods;
    }

    /**
     * Setter to ignore methods and constructors annotated with the specified annotation(s).
     *
     * @param annotationNames specified annotation(s)
     * @since 10.15.0
     */
    public void setIgnoreAnnotatedBy(String... annotationNames) {
        ignoreAnnotatedBy = Set.of(annotationNames);
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
     * Determine whether to ignore number of parameters.
     *
     * @param ast the token to process
     * @return true if number of parameters should be ignored.
     */
    private boolean shouldIgnoreNumberOfParameters(DetailAST ast) {
        return isIgnoredOverriddenMethod(ast) || isAnnotatedByIgnoredAnnotations(ast);
    }

    /**
     * Checks if method is overridden and should be ignored.
     *
     * @param ast method definition to check
     * @return true if method is overridden and should be ignored.
     */
    private boolean isIgnoredOverriddenMethod(DetailAST ast) {
        return ignoreOverriddenMethods && AnnotationUtil.hasOverrideAnnotation(ast);
    }

    /**
     * Checks if method or constructor is annotated by ignored annotation(s).
     *
     * @param ast method or constructor definition to check
     * @return true if annotated by ignored annotation(s).
     */
    private boolean isAnnotatedByIgnoredAnnotations(DetailAST ast) {
        return AnnotationUtil.containsAnnotation(ast, ignoreAnnotatedBy);
    }

}
