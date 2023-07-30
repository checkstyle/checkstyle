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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks that a variable has a Javadoc comment. Ignores {@code serialVersionUID} fields.
 * </p>
 * <ul>
 * <li>
 * Property {@code scope} - Specify the visibility scope where Javadoc comments are checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.Scope}.
 * Default value is {@code private}.
 * </li>
 * <li>
 * Property {@code excludeScope} - Specify the visibility scope where Javadoc
 * comments are not checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.Scope}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code ignoreNamePattern} - Specify the regexp to define variable names to ignore.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_CONSTANT_DEF">
 * ENUM_CONSTANT_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the default check:
 * </p>
 * <pre>
 * &lt;module name="JavadocVariable"/&gt;
 * </pre>
 * <p>
 * By default, this setting will report a violation if
 * there is no javadoc for any scope member.
 * </p>
 * <pre>
 * public class Test {
 *   private int a; // violation, missing javadoc for private member
 *
 *   &#47;**
 *    * Some description here
 *    *&#47;
 *   private int b; // OK
 *   protected int c; // violation, missing javadoc for protected member
 *   public int d; // violation, missing javadoc for public member
 *   &#47;*package*&#47; int e; // violation, missing javadoc for package member
 * }
 * </pre>
 * <p>
 * To configure the check for {@code public} scope:
 * </p>
 * <pre>
 * &lt;module name="JavadocVariable"&gt;
 *   &lt;property name="scope" value="public"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>This setting will report a violation if there is no javadoc for {@code public} member.</p>
 * <pre>
 * public class Test {
 *   private int a; // OK
 *
 *   &#47;**
 *    * Some description here
 *    *&#47;
 *   private int b; // OK
 *   protected int c; // OK
 *   public int d; // violation, missing javadoc for public member
 *   &#47;*package*&#47; int e; // OK
 * }
 * </pre>
 * <p>
 * To configure the check for members which are in {@code private},
 * but not in {@code protected} scope:
 * </p>
 * <pre>
 * &lt;module name="JavadocVariable"&gt;
 *   &lt;property name="scope" value="private"/&gt;
 *   &lt;property name="excludeScope" value="protected"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * This setting will report a violation if there is no javadoc for {@code private}
 * member and ignores {@code protected} member.
 * </p>
 * <pre>
 * public class Test {
 *   private int a; // violation, missing javadoc for private member
 *
 *   &#47;**
 *    * Some description here
 *    *&#47;
 *   private int b; // OK
 *   protected int c; // OK
 *   public int d; // OK
 *   &#47;*package*&#47; int e; // violation, missing javadoc for package member
 * }
 * </pre>
 * <p>
 * To ignore absence of Javadoc comments for variables with names {@code log} or {@code logger}:
 * </p>
 * <pre>
 * &lt;module name="JavadocVariable"&gt;
 *   &lt;property name="ignoreNamePattern" value="log|logger"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * This setting will report a violation if there is no javadoc for any scope
 * member and ignores members with name {@code log} or {@code logger}.
 * </p>
 * <pre>
 * public class Test {
 *   private int a; // violation, missing javadoc for private member
 *
 *   &#47;**
 *    * Some description here
 *    *&#47;
 *   private int b; // OK
 *   protected int c; // violation, missing javadoc for protected member
 *   public int d; // violation, missing javadoc for public member
 *   &#47;*package*&#47; int e; // violation, missing javadoc for package member
 *   private int log; // OK
 *   private int logger; // OK
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
 * {@code javadoc.missing}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class JavadocVariableCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_JAVADOC_MISSING = "javadoc.missing";

    /** Specify the visibility scope where Javadoc comments are checked. */
    private Scope scope = Scope.PRIVATE;

    /** Specify the visibility scope where Javadoc comments are not checked. */
    private Scope excludeScope;

    /** Specify the regexp to define variable names to ignore. */
    private Pattern ignoreNamePattern;

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
     * Setter to specify the regexp to define variable names to ignore.
     *
     * @param pattern a pattern.
     */
    public void setIgnoreNamePattern(Pattern pattern) {
        ignoreNamePattern = pattern;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };
    }

    /*
     * Skipping enum values is requested.
     * Checkstyle's issue #1669: https://github.com/checkstyle/checkstyle/issues/1669
     */
    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
        };
    }

    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @SuppressWarnings("deprecation")
    @Override
    public void visitToken(DetailAST ast) {
        if (shouldCheck(ast)) {
            final FileContents contents = getFileContents();
            final TextBlock textBlock =
                contents.getJavadocBefore(ast.getLineNo());

            if (textBlock == null) {
                log(ast, MSG_JAVADOC_MISSING);
            }
        }
    }

    /**
     * Decides whether the variable name of an AST is in the ignore list.
     *
     * @param ast the AST to check
     * @return true if the variable name of ast is in the ignore list.
     */
    private boolean isIgnored(DetailAST ast) {
        final String name = ast.findFirstToken(TokenTypes.IDENT).getText();
        return ignoreNamePattern != null && ignoreNamePattern.matcher(name).matches()
            || "serialVersionUID".equals(name);
    }

    /**
     * Whether we should check this node.
     *
     * @param ast a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST ast) {
        boolean result = false;
        if (!ScopeUtil.isInCodeBlock(ast) && !isIgnored(ast)) {
            final Scope customScope = ScopeUtil.getScope(ast);
            final Scope surroundingScope = ScopeUtil.getSurroundingScope(ast);
            result = customScope.isIn(scope) && surroundingScope.isIn(scope)
                && (excludeScope == null
                    || !customScope.isIn(excludeScope)
                    || !surroundingScope.isIn(excludeScope));
        }
        return result;
    }

}
