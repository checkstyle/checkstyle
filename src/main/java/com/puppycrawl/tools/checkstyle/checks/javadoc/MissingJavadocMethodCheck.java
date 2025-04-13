///
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Checks for missing Javadoc comments for a method or constructor. The scope to verify is
 * specified using the {@code Scope} class and defaults to {@code Scope.PUBLIC}. To verify
 * another scope, set property scope to a different
 * <a href="https://checkstyle.org/property_types.html#Scope">scope</a>.
 * </div>
 *
 * <p>
 * Javadoc is not required on a method that is tagged with the {@code @Override} annotation.
 * However, under Java 5 it is not possible to mark a method required for an interface (this
 * was <i>corrected</i> under Java 6). Hence, Checkstyle supports using the convention of using
 * a single {@code {@inheritDoc}} tag instead of all the other tags.
 * </p>
 *
 * <p>
 * For getters and setters for the property {@code allowMissingPropertyJavadoc}, the methods must
 * match exactly the structures below.
 * </p>
 * <pre>
 * public void setNumber(final int number)
 * {
 *     mNumber = number;
 * }
 *
 * public int getNumber()
 * {
 *     return mNumber;
 * }
 *
 * public boolean isSomething()
 * {
 *     return false;
 * }
 * </pre>
 * <ul>
 * <li>
 * Property {@code allowMissingPropertyJavadoc} - Control whether to allow missing Javadoc on
 * accessor methods for properties (setters and getters).
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowedAnnotations} - Configure annotations that allow missed
 * documentation.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code Override}.
 * </li>
 * <li>
 * Property {@code excludeScope} - Specify the visibility scope where Javadoc comments are
 * not checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.Scope}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code ignoreMethodNamesRegex} - Ignore method whose names are matching specified
 * regex.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code minLineCount} - Control the minimal amount of lines in method to allow no
 * documentation.
 * Type is {@code int}.
 * Default value is {@code -1}.
 * </li>
 * <li>
 * Property {@code scope} - Specify the visibility scope where Javadoc comments are checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.Scope}.
 * Default value is {@code public}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_FIELD_DEF">
 * ANNOTATION_FIELD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMPACT_CTOR_DEF">
 * COMPACT_CTOR_DEF</a>.
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
 * {@code javadoc.missing}
 * </li>
 * </ul>
 *
 * @since 8.21
 */
@FileStatefulCheck
public class MissingJavadocMethodCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_JAVADOC_MISSING = "javadoc.missing";

    /** Maximum children allowed in setter/getter. */
    private static final int SETTER_GETTER_MAX_CHILDREN = 7;

    /** Pattern matching names of getter methods. */
    private static final Pattern GETTER_PATTERN = Pattern.compile("^(is|get)[A-Z].*");

    /** Pattern matching names of setter methods. */
    private static final Pattern SETTER_PATTERN = Pattern.compile("^set[A-Z].*");

    /** Maximum nodes allowed in a body of setter. */
    private static final int SETTER_BODY_SIZE = 3;

    /** Default value of minimal amount of lines in method to allow no documentation.*/
    private static final int DEFAULT_MIN_LINE_COUNT = -1;

    /** Specify the visibility scope where Javadoc comments are checked. */
    private Scope scope = Scope.PUBLIC;

    /** Specify the visibility scope where Javadoc comments are not checked. */
    private Scope excludeScope;

    /** Control the minimal amount of lines in method to allow no documentation.*/
    private int minLineCount = DEFAULT_MIN_LINE_COUNT;

    /**
     * Control whether to allow missing Javadoc on accessor methods for
     * properties (setters and getters).
     */
    private boolean allowMissingPropertyJavadoc;

    /** Ignore method whose names are matching specified regex. */
    private Pattern ignoreMethodNamesRegex;

    /** Configure annotations that allow missed documentation. */
    private Set<String> allowedAnnotations = Set.of("Override");

    /**
     * Setter to configure annotations that allow missed documentation.
     *
     * @param userAnnotations user's value.
     * @since 8.21
     */
    public void setAllowedAnnotations(String... userAnnotations) {
        allowedAnnotations = Set.of(userAnnotations);
    }

    /**
     * Setter to ignore method whose names are matching specified regex.
     *
     * @param pattern a pattern.
     * @since 8.21
     */
    public void setIgnoreMethodNamesRegex(Pattern pattern) {
        ignoreMethodNamesRegex = pattern;
    }

    /**
     * Setter to control the minimal amount of lines in method to allow no documentation.
     *
     * @param value user's value.
     * @since 8.21
     */
    public void setMinLineCount(int value) {
        minLineCount = value;
    }

    /**
     * Setter to control whether to allow missing Javadoc on accessor methods for properties
     * (setters and getters).
     *
     * @param flag a {@code Boolean} value
     * @since 8.21
     */
    public void setAllowMissingPropertyJavadoc(final boolean flag) {
        allowMissingPropertyJavadoc = flag;
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are checked.
     *
     * @param scope a scope.
     * @since 8.21
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are not checked.
     *
     * @param excludeScope a scope.
     * @since 8.21
     */
    public void setExcludeScope(Scope excludeScope) {
        this.excludeScope = excludeScope;
    }

    @Override
    public final int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @SuppressWarnings("deprecation")
    @Override
    public final void visitToken(DetailAST ast) {
        final Scope theScope = ScopeUtil.getScope(ast);
        if (shouldCheck(ast, theScope)) {
            final FileContents contents = getFileContents();
            final TextBlock textBlock = contents.getJavadocBefore(ast.getLineNo());

            if (textBlock == null && !isMissingJavadocAllowed(ast)) {
                log(ast, MSG_JAVADOC_MISSING);
            }
        }
    }

    /**
     * Some javadoc.
     *
     * @param methodDef Some javadoc.
     * @return Some javadoc.
     */
    private static int getMethodsNumberOfLine(DetailAST methodDef) {
        final int numberOfLines;
        final DetailAST lcurly = methodDef.getLastChild();
        final DetailAST rcurly = lcurly.getLastChild();

        if (lcurly.getFirstChild() == rcurly) {
            numberOfLines = 1;
        }
        else {
            numberOfLines = rcurly.getLineNo() - lcurly.getLineNo() - 1;
        }
        return numberOfLines;
    }

    /**
     * Checks if a missing Javadoc is allowed by the check's configuration.
     *
     * @param ast the tree node for the method or constructor.
     * @return True if this method or constructor doesn't need Javadoc.
     */
    private boolean isMissingJavadocAllowed(final DetailAST ast) {
        return allowMissingPropertyJavadoc
                && (isSetterMethod(ast) || isGetterMethod(ast))
            || matchesSkipRegex(ast)
            || isContentsAllowMissingJavadoc(ast);
    }

    /**
     * Checks if the Javadoc can be missing if the method or constructor is
     * below the minimum line count or has a special annotation.
     *
     * @param ast the tree node for the method or constructor.
     * @return True if this method or constructor doesn't need Javadoc.
     */
    private boolean isContentsAllowMissingJavadoc(DetailAST ast) {
        return ast.getType() != TokenTypes.ANNOTATION_FIELD_DEF
                && (getMethodsNumberOfLine(ast) <= minLineCount
                    || AnnotationUtil.containsAnnotation(ast, allowedAnnotations));
    }

    /**
     * Checks if the given method name matches the regex. In that case
     * we skip enforcement of javadoc for this method
     *
     * @param methodDef {@link TokenTypes#METHOD_DEF METHOD_DEF}
     * @return true if given method name matches the regex.
     */
    private boolean matchesSkipRegex(DetailAST methodDef) {
        boolean result = false;
        if (ignoreMethodNamesRegex != null) {
            final DetailAST ident = methodDef.findFirstToken(TokenTypes.IDENT);
            final String methodName = ident.getText();

            final Matcher matcher = ignoreMethodNamesRegex.matcher(methodName);
            if (matcher.matches()) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Whether we should check this node.
     *
     * @param ast a given node.
     * @param nodeScope the scope of the node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST ast, final Scope nodeScope) {
        final Scope surroundingScope = ScopeUtil.getSurroundingScope(ast);

        return nodeScope != excludeScope
                && surroundingScope != excludeScope
                && nodeScope.isIn(scope)
                && surroundingScope.isIn(scope);
    }

    /**
     * Returns whether an AST represents a getter method.
     *
     * @param ast the AST to check with
     * @return whether the AST represents a getter method
     */
    public static boolean isGetterMethod(final DetailAST ast) {
        boolean getterMethod = false;

        // Check have a method with exactly 7 children which are all that
        // is allowed in a proper getter method which does not throw any
        // exceptions.
        if (ast.getType() == TokenTypes.METHOD_DEF
                && ast.getChildCount() == SETTER_GETTER_MAX_CHILDREN) {
            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
            final String name = type.getNextSibling().getText();
            final boolean matchesGetterFormat = GETTER_PATTERN.matcher(name).matches();

            final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
            final boolean noParams = params.getChildCount(TokenTypes.PARAMETER_DEF) == 0;

            if (matchesGetterFormat && noParams) {
                // Now verify that the body consists of:
                // SLIST -> RETURN
                // RCURLY
                final DetailAST slist = ast.findFirstToken(TokenTypes.SLIST);

                if (slist != null) {
                    final DetailAST expr = slist.getFirstChild();
                    getterMethod = expr.getType() == TokenTypes.LITERAL_RETURN;
                }
            }
        }
        return getterMethod;
    }

    /**
     * Returns whether an AST represents a setter method.
     *
     * @param ast the AST to check with
     * @return whether the AST represents a setter method
     */
    public static boolean isSetterMethod(final DetailAST ast) {
        boolean setterMethod = false;

        // Check have a method with exactly 7 children which are all that
        // is allowed in a proper setter method which does not throw any
        // exceptions.
        if (ast.getType() == TokenTypes.METHOD_DEF
                && ast.getChildCount() == SETTER_GETTER_MAX_CHILDREN) {
            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
            final String name = type.getNextSibling().getText();
            final boolean matchesSetterFormat = SETTER_PATTERN.matcher(name).matches();

            final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
            final boolean singleParam = params.getChildCount(TokenTypes.PARAMETER_DEF) == 1;

            if (matchesSetterFormat && singleParam) {
                // Now verify that the body consists of:
                // SLIST -> EXPR -> ASSIGN
                // SEMI
                // RCURLY
                final DetailAST slist = ast.findFirstToken(TokenTypes.SLIST);

                if (slist != null && slist.getChildCount() == SETTER_BODY_SIZE) {
                    final DetailAST expr = slist.getFirstChild();
                    setterMethod = expr.getFirstChild().getType() == TokenTypes.ASSIGN;
                }
            }
        }
        return setterMethod;
    }
}
