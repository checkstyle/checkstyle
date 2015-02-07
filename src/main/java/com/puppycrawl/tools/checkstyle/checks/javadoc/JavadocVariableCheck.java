////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.regex.Pattern;

/**
 * Checks that a variable has Javadoc comment.
 *
 * @author Oliver Burn
 * @version 1.0
 */
public class JavadocVariableCheck
    extends Check
{
    /** the scope to check */
    private Scope scope = Scope.PRIVATE;

    /** the visibility scope where Javadoc comments shouldn't be checked **/
    private Scope excludeScope;

    /** the regular expression to ignore variable name */
    private String ignoreNameRegexp;

    /** the pattern to ignore variable name */
    private Pattern ignoreNamePattern;

    /**
     * Sets the scope to check.
     * @param from string to get the scope from
     */
    public void setScope(String from)
    {
        scope = Scope.getInstance(from);
    }

    /**
     * Set the excludeScope.
     * @param scope a <code>String</code> value
     */
    public void setExcludeScope(String scope)
    {
        excludeScope = Scope.getInstance(scope);
    }

    /**
     * Sets the variable names to ignore in the check.
     * @param regexp regexp to define variable names to ignore.
     */
    public void setIgnoreNamePattern(String regexp)
    {
        ignoreNameRegexp = regexp;
        if (!(regexp == null || regexp.length() == 0)) {
            ignoreNamePattern = Pattern.compile(regexp);
        }
        else {
            ignoreNamePattern = null;
        }
    }

    /**
     * Gets the variable names to ignore in the check.
     * @return true regexp string to define variable names to ignore.
     */
    public String getIgnoreNamePattern()
    {
        return ignoreNameRegexp;
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        if (shouldCheck(ast)) {
            final FileContents contents = getFileContents();
            final TextBlock cmt =
                contents.getJavadocBefore(ast.getLineNo());

            if (cmt == null) {
                log(ast, "javadoc.missing");
            }
        }
    }

    /**
     * Decides whether the variable name of an AST is in the ignore list.
     * @param ast the AST to check
     * @return true if the variable name of ast is in the ignore list.
     */
    private boolean isIgnored(DetailAST ast)
    {
        final String name = ast.findFirstToken(TokenTypes.IDENT).getText();
        return ignoreNamePattern != null
                && ignoreNamePattern.matcher(name).matches();
    }

    /**
     * Whether we should check this node.
     * @param ast a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST ast)
    {
        if (ScopeUtils.inCodeBlock(ast) || isIgnored(ast)) {
            return false;
        }

        final Scope scope;
        if (ast.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
            scope = Scope.PUBLIC;
        }
        else {
            final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
            final Scope declaredScope = ScopeUtils.getScopeFromMods(mods);
            scope =
                ScopeUtils.inInterfaceOrAnnotationBlock(ast)
                    ? Scope.PUBLIC : declaredScope;
        }

        final Scope surroundingScope = ScopeUtils.getSurroundingScope(ast);

        return scope.isIn(this.scope) && surroundingScope.isIn(this.scope)
            && ((excludeScope == null)
                || !scope.isIn(excludeScope)
                || !surroundingScope.isIn(excludeScope));
    }
}
