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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks that a variable has a Javadoc comment. Ignores {@code serialVersionUID} fields.
 * </p>
 * <ul>
 * <li>
 * Property {@code accessModifiers} - Specify the access modifiers where Javadoc comments are
 * checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption[]}.
 * Default value is {@code public, protected, package, private}.
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
 * By default, this setting will report a violation
 * if there is no javadoc for a member with any access modifier.
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
 * To configure the check for {@code package} and {@code private} variables:
 * </p>
 * <pre>
 * &lt;module name="JavadocVariable"&gt;
 *   &lt;property name="accessModifiers" value="package,private"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * This setting will report a violation if there is no javadoc for {@code package}
 * or {@code private} members.
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
 * This setting will report a violation if there is no javadoc for a
 * member with any scope and ignores members with name {@code log} or {@code logger}.
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

    /** Specify the access modifiers where Javadoc comments are checked. */
    private AccessModifierOption[] accessModifiers = {
        AccessModifierOption.PUBLIC,
        AccessModifierOption.PROTECTED,
        AccessModifierOption.PACKAGE,
        AccessModifierOption.PRIVATE,
    };

    /** Specify the regexp to define variable names to ignore. */
    private Pattern ignoreNamePattern;

    /**
     * Setter to specify the access modifiers where Javadoc comments are checked.
     *
     * @param accessModifiers access modifiers of variables which should be checked.
     */
    public void setAccessModifiers(AccessModifierOption... accessModifiers) {
        this.accessModifiers =
            Arrays.copyOf(accessModifiers, accessModifiers.length);
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
            Scope customScope = Scope.PUBLIC;
            if (ast.getType() != TokenTypes.ENUM_CONSTANT_DEF
                    && !ScopeUtil.isInInterfaceOrAnnotationBlock(ast)) {
                final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
                customScope = ScopeUtil.getScopeFromMods(mods);
            }

            final Scope surroundingScope = ScopeUtil.getSurroundingScope(ast);

            final Scope effectiveScope;
            if (surroundingScope.isIn(customScope)) {
                effectiveScope = customScope;
            }
            else {
                effectiveScope = surroundingScope;
            }
            result = matchAccessModifiers(toAccessModifier(effectiveScope));
        }
        return result;
    }

    /**
     * Checks whether a variable has the correct access modifier to be checked.
     *
     * @param accessModifier the access modifier of the variable.
     * @return whether the variable matches the expected access modifier.
     */
    private boolean matchAccessModifiers(final AccessModifierOption accessModifier) {
        return Arrays.stream(accessModifiers)
            .anyMatch(modifier -> modifier == accessModifier);
    }

    /**
     * Converts a {@link Scope} to {@link AccessModifierOption}. {@code Scope.NOTHING} and {@code
     * Scope.ANONINNER} are converted to {@code AccessModifierOption.PUBLIC}.
     *
     * @param scope Scope to be converted.
     * @return the corresponding AccessModifierOption.
     */
    private static AccessModifierOption toAccessModifier(Scope scope) {
        final AccessModifierOption accessModifier;
        switch (scope) {
            case PROTECTED:
                accessModifier = AccessModifierOption.PROTECTED;
                break;
            case PACKAGE:
                accessModifier = AccessModifierOption.PACKAGE;
                break;
            case PRIVATE:
                accessModifier = AccessModifierOption.PRIVATE;
                break;
            case NOTHING:
            case ANONINNER:
            case PUBLIC:
            default:
                accessModifier = AccessModifierOption.PUBLIC;
        }
        return accessModifier;
    }

}
