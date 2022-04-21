////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
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
 * <p>
 * Checks for missing Javadoc comments for class, enum, interface, and annotation interface
 * definitions. The scope to verify is specified using the {@code Scope} class and defaults
 * to {@code Scope.PUBLIC}. To verify another scope, set property scope to one of the
 * {@code Scope} constants.
 * </p>
 * <ul>
 * <li>
 * Property {@code scope} - specify the visibility scope where Javadoc comments are checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.Scope}.
 * Default value is {@code public}.
 * </li>
 * <li>
 * Property {@code excludeScope} - specify the visibility scope where Javadoc comments are not
 * checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.Scope}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code skipAnnotations} - specify annotations that allow missed
 * documentation. Only short names are allowed, e.g. {@code Generated}.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code Generated}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the default check to make sure all public class, enum, interface, and annotation
 * interface, definitions have javadocs:
 * </p>
 * <pre>
 * &lt;module name="MissingJavadocType"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class PublicClass {} // violation
 * private class PublicClass {}
 * protected class PublicClass {}
 * class PackagePrivateClass {}
 * </pre>
 * <p>
 * To configure the check for {@code private} scope:
 * </p>
 * <pre>
 * &lt;module name="MissingJavadocType"&gt;
 *   &lt;property name="scope" value="private"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class PublicClass {} // violation
 * private class PublicClass {} // violation
 * protected class PublicClass {} // violation
 * class PackagePrivateClass {} // violation
 * </pre>
 * <p>
 * To configure the check for {@code private} classes only:
 * </p>
 * <pre>
 * &lt;module name="MissingJavadocType"&gt;
 *   &lt;property name="scope" value="private"/&gt;
 *   &lt;property name="excludeScope" value="package"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class PublicClass {}
 * private class PublicClass {} // violation
 * protected class PublicClass {}
 * class PackagePrivateClass {}
 * </pre>
 * <p>
 * Example that allows missing comments for classes annotated with {@code @SpringBootApplication}
 * and {@code @Configuration}:
 * </p>
 * <pre>
 * &#64;SpringBootApplication // no violations about missing comment on class
 * public class Application {}
 *
 * &#64;Configuration // no violations about missing comment on class
 * class DatabaseConfiguration {}
 * </pre>
 * <p>
 * Use following configuration:
 * </p>
 * <pre>
 * &lt;module name="MissingJavadocType"&gt;
 *   &lt;property name="skipAnnotations" value="SpringBootApplication,Configuration"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.missing}
 * </li>
 * </ul>
 *
 * @since 8.20
 */
@StatelessCheck
public class MissingJavadocTypeCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_JAVADOC_MISSING = "javadoc.missing";

    /** Specify the visibility scope where Javadoc comments are checked. */
    private Scope scope = Scope.PUBLIC;
    /** Specify the visibility scope where Javadoc comments are not checked. */
    private Scope excludeScope;

    /**
     * Specify annotations that allow missed documentation.
     * Only short names are allowed, e.g. {@code Generated}.
     */
    private Set<String> skipAnnotations = Set.of("Generated");

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

    /**
     * Setter to specify annotations that allow missed documentation.
     * Only short names are allowed, e.g. {@code Generated}.
     *
     * @param userAnnotations user's value.
     */
    public void setSkipAnnotations(String... userAnnotations) {
        skipAnnotations = Set.of(userAnnotations);
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @SuppressWarnings("deprecation")
    @Override
    public void visitToken(DetailAST ast) {
        if (shouldCheck(ast)) {
            final FileContents contents = getFileContents();
            final int lineNo = ast.getLineNo();
            final TextBlock textBlock = contents.getJavadocBefore(lineNo);
            if (textBlock == null) {
                log(ast, MSG_JAVADOC_MISSING);
            }
        }
    }

    /**
     * Whether we should check this node.
     *
     * @param ast a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST ast) {
        final Scope customScope = ScopeUtil.getScope(ast);
        final Scope surroundingScope = ScopeUtil.getSurroundingScope(ast);

        return customScope.isIn(scope)
            && (surroundingScope == null || surroundingScope.isIn(scope))
            && (excludeScope == null
                || !customScope.isIn(excludeScope)
                || surroundingScope != null
                && !surroundingScope.isIn(excludeScope))
            && !AnnotationUtil.containsAnnotation(ast, skipAnnotations);
    }

}
