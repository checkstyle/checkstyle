////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.ScopeUtils;
import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks that a variable has Javadoc comment.
 *
 * @author Oliver Burn
 */
public class JavadocVariableCheck
    extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String JAVADOC_MISSING = "javadoc.missing";

    /** the scope to check */
    private Scope scope = Scope.PRIVATE;

    /** the visibility scope where Javadoc comments shouldn't be checked **/
    private Scope excludeScope;

    /** the pattern to ignore variable name */
    private Pattern ignoreNamePattern;

    /**
     * Sets the scope to check.
     * @param from string to get the scope from
     */
    public void setScope(String from) {
        scope = Scope.getInstance(from);
    }

    /**
     * Set the excludeScope.
     * @param excludeScope a {@code String} value
     */
    public void setExcludeScope(String excludeScope) {
        this.excludeScope = Scope.getInstance(excludeScope);
    }

    /**
     * Sets the variable names to ignore in the check.
     * @param regexp regular expression to define variable names to ignore.
     * @throws org.apache.commons.beanutils.ConversionException if unable to create Pattern object.
     */
    public void setIgnoreNamePattern(String regexp) {
        ignoreNamePattern = Utils.createPattern(regexp);
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (shouldCheck(ast)) {
            final FileContents contents = getFileContents();
            final TextBlock cmt =
                contents.getJavadocBefore(ast.getLineNo());

            if (cmt == null) {
                log(ast, JAVADOC_MISSING);
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
        return ignoreNamePattern != null
                && ignoreNamePattern.matcher(name).matches();
    }

    /**
     * Whether we should check this node.
     * @param ast a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST ast) {
        if (ScopeUtils.inCodeBlock(ast) || isIgnored(ast)) {
            return false;
        }

        final Scope customScope;
        if (ast.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
            customScope = Scope.PUBLIC;
        }
        else {
            final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
            final Scope declaredScope = ScopeUtils.getScopeFromMods(mods);
            customScope =
                ScopeUtils.inInterfaceOrAnnotationBlock(ast)
                    ? Scope.PUBLIC : declaredScope;
        }

        final Scope surroundingScope = ScopeUtils.getSurroundingScope(ast);

        return customScope.isIn(scope) && surroundingScope.isIn(scope)
            && (excludeScope == null
                || !customScope.isIn(excludeScope)
                || !surroundingScope.isIn(excludeScope));
    }
}
