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
import org.apache.commons.beanutils.ConversionException;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
    private Scope mScope = Scope.PRIVATE;

    /** the visibility scope where Javadoc comments shouldn't be checked **/
    private Scope mExcludeScope;

    /** the variables to ignore */
    private String mIgnoreNames;

    /** the variables to ignore pattern */
    private Pattern mIgnoreNamesRegexp;

    /**
     * Sets the scope to check.
     * @param aFrom string to get the scope from
     */
    public void setScope(String aFrom)
    {
        mScope = Scope.getInstance(aFrom);
    }

    /**
     * Set the excludeScope.
     * @param aScope a <code>String</code> value
     */
    public void setExcludeScope(String aScope)
    {
        mExcludeScope = Scope.getInstance(aScope);
    }

    /**
     * Sets the variable names to ignore in the check.
     * @param aRegexp regexp to define variable names to ignore.
     */
    public void setIgnoreNames(String aRegexp)
    {
        updateIgnoreNamesRegexp(aRegexp);
    }

    /**
     * Gets the variable names to ignore in the check.
     * @return true regexp string to define variable names to ignore.
     */
    public String getIgnoreNames()
    {
        return mIgnoreNames;
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
    public void visitToken(DetailAST aAST)
    {
        if (shouldCheck(aAST)) {
            final FileContents contents = getFileContents();
            final TextBlock cmt =
                contents.getJavadocBefore(aAST.getLineNo());

            if (cmt == null) {
                log(aAST, "javadoc.missing");
            }
        }
    }

    /**
     * Decides whether the variable name of an AST is in the ignore list.
     * @param aAST the AST to check
     * @return true if the variable name of aAST is in the ignore list.
     */
    private boolean isIgnored(DetailAST aAST)
    {
        final String name = aAST.findFirstToken(TokenTypes.IDENT).getText();
        return mIgnoreNamesRegexp != null
                && mIgnoreNamesRegexp.matcher(name).matches();
    }

    /**
     * Whether we should check this node.
     * @param aAST a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST aAST)
    {
        if (ScopeUtils.inCodeBlock(aAST) || isIgnored(aAST)) {
            return false;
        }

        final Scope scope;
        if (aAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
            scope = Scope.PUBLIC;
        }
        else {
            final DetailAST mods = aAST.findFirstToken(TokenTypes.MODIFIERS);
            final Scope declaredScope = ScopeUtils.getScopeFromMods(mods);
            scope =
                ScopeUtils.inInterfaceOrAnnotationBlock(aAST)
                    ? Scope.PUBLIC : declaredScope;
        }

        final Scope surroundingScope = ScopeUtils.getSurroundingScope(aAST);

        return scope.isIn(mScope) && surroundingScope.isIn(mScope)
            && ((mExcludeScope == null)
                || !scope.isIn(mExcludeScope)
                || !surroundingScope.isIn(mExcludeScope));
    }

    /**
     * Updates the regular expression using the supplied format.
     * Will also update the member variables.
     * @param aFormat the format of the regular expression.
     */
    private void updateIgnoreNamesRegexp(String aFormat)
    {
        mIgnoreNames = aFormat;
        try {
            if (!(aFormat == null || aFormat.length() == 0)) {
                mIgnoreNamesRegexp = Pattern.compile(aFormat);
            }
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }
}
