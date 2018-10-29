////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
 * Checks that a variable has Javadoc comment. Ignores {@code serialVersionUID} fields.
 *
 */
@StatelessCheck
public class JavadocVariableCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_JAVADOC_MISSING = "javadoc.missing";

    /** The scope to check. */
    private Scope scope = Scope.PRIVATE;

    /** The visibility scope where Javadoc comments shouldn't be checked. **/
    private Scope excludeScope;

    /** The pattern to ignore variable name. */
    private Pattern ignoreNamePattern;

    /**
     * Sets the scope to check.
     * @param scope a scope.
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Set the excludeScope.
     * @param excludeScope a scope.
     */
    public void setExcludeScope(Scope excludeScope) {
        this.excludeScope = excludeScope;
    }

    /**
     * Sets the variable names to ignore in the check.
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
            result = customScope.isIn(scope) && surroundingScope.isIn(scope)
                && (excludeScope == null
                    || !customScope.isIn(excludeScope)
                    || !surroundingScope.isIn(excludeScope));
        }
        return result;
    }

}
