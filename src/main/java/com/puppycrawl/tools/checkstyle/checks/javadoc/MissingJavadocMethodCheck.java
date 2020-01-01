////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks for missing Javadoc comments for a method or constructor. The scope to verify is
 * specified using the {@code Scope} class and defaults to {@code Scope.PUBLIC}. To verify
 * another scope, set property scope to a different
 * <a href="https://checkstyle.org/property_types.html#scope">scope</a>.
 * </p>
 * <p>
 * Javadoc is not required on a method that is tagged with the {@code @Override} annotation.
 * However under Java 5 it is not possible to mark a method required for an interface (this
 * was <i>corrected</i> under Java 6). Hence Checkstyle supports using the convention of using
 * a single {@code {@inheritDoc}} tag instead of all the other tags.
 * </p>
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
 * Property {@code minLineCount} - Control the minimal amount of lines in method to allow no
 * documentation. Default value is {@code -1}.
 * </li>
 * <li>
 * Property {@code allowedAnnotations} - Configure the list of annotations that allow missed
 * documentation. Default value is {@code Override}.
 * </li>
 * <li>
 * Property {@code scope} - Specify the visibility scope where Javadoc comments are checked.
 * Default value is {@code public}.
 * </li>
 * <li>
 * Property {@code excludeScope} - Specify the visibility scope where Javadoc comments are
 * not checked. Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code allowMissingPropertyJavadoc} - Control whether to allow missing Javadoc on
 * accessor methods for properties (setters and getters). Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code ignoreMethodNamesRegex} - ignore method whose names are matching specified
 * regex. Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_FIELD_DEF">
 * ANNOTATION_FIELD_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the default check:
 * </p>
 * <pre>
 * &lt;module name="MissingJavadocMethod"/&gt;
 * </pre>
 * <p>
 * To configure the check for {@code private} scope:
 * </p>
 * <pre>
 * &lt;module name="MissingJavadocMethod"&gt;
 *   &lt;property name="scope" value="private"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check for methods which are in {@code private}, but not in {@code protected}
 * scope:
 * </p>
 * <pre>
 * &lt;module name="MissingJavadocMethod"&gt;
 *   &lt;property name="scope" value="private"/&gt;
 *   &lt;property name="excludeScope" value="protected"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check for ignoring methods named {@code foo(),foo1(),foo2()}, etc.:
 * </p>
 * <pre>
 * &lt;module name="MissingJavadocMethod"&gt;
 *   &lt;property name="ignoreMethodNamesRegex" value="^foo.*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @since 8.21
 */
@FileStatefulCheck
public class MissingJavadocMethodCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_JAVADOC_MISSING = "javadoc.missing";

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

    /** Configure the list of annotations that allow missed documentation. */
    private List<String> allowedAnnotations = Collections.singletonList("Override");

    /**
     * Setter to configure the list of annotations that allow missed documentation.
     * @param userAnnotations user's value.
     */
    public void setAllowedAnnotations(String... userAnnotations) {
        allowedAnnotations = Arrays.asList(userAnnotations);
    }

    /**
     * Setter to ignore method whose names are matching specified regex.
     * @param pattern a pattern.
     */
    public void setIgnoreMethodNamesRegex(Pattern pattern) {
        ignoreMethodNamesRegex = pattern;
    }

    /**
     * Setter to control the minimal amount of lines in method to allow no documentation.
     * @param value user's value.
     */
    public void setMinLineCount(int value) {
        minLineCount = value;
    }

    /**
     * Setter to control whether to allow missing Javadoc on accessor methods for properties
     * (setters and getters).
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowMissingPropertyJavadoc(final boolean flag) {
        allowMissingPropertyJavadoc = flag;
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are checked.
     *
     * @param scope a scope.
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are not checked.
     *
     * @param excludeScope a scope.
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
        };
    }

    @Override
    public final void visitToken(DetailAST ast) {
        final Scope theScope = calculateScope(ast);
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
     * @param ast the tree node for the method or constructor.
     * @return True if this method or constructor doesn't need Javadoc.
     */
    private boolean isMissingJavadocAllowed(final DetailAST ast) {
        return allowMissingPropertyJavadoc
                && (CheckUtil.isSetterMethod(ast) || CheckUtil.isGetterMethod(ast))
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
        return (ast.getType() == TokenTypes.METHOD_DEF || ast.getType() == TokenTypes.CTOR_DEF)
                && (getMethodsNumberOfLine(ast) <= minLineCount
                    || AnnotationUtil.containsAnnotation(ast, allowedAnnotations));
    }

    /**
     * Checks if the given method name matches the regex. In that case
     * we skip enforcement of javadoc for this method
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

        return (excludeScope == null
                || nodeScope != excludeScope
                && surroundingScope != excludeScope)
            && nodeScope.isIn(scope)
            && surroundingScope.isIn(scope);
    }

    /**
     * Returns the scope for the method/constructor at the specified AST. If
     * the method is in an interface or annotation block, the scope is assumed
     * to be public.
     *
     * @param ast the token of the method/constructor
     * @return the scope of the method/constructor
     */
    private static Scope calculateScope(final DetailAST ast) {
        final Scope scope;

        if (ScopeUtil.isInInterfaceOrAnnotationBlock(ast)) {
            scope = Scope.PUBLIC;
        }
        else {
            final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
            scope = ScopeUtil.getScopeFromMods(mods);
        }
        return scope;
    }

}
