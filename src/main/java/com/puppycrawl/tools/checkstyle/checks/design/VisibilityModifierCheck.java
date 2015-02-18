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
package com.puppycrawl.tools.checkstyle.checks.design;

import antlr.collections.AST;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.beanutils.ConversionException;

/**
 * Checks visibility of class members. Only static final members may be public,
 * other class members must be private unless allowProtected/Package is set.
 * <p>
 * Public members are not flagged if the name matches the public
 * member regular expression (contains "^serialVersionUID$" by
 * default).
 * </p>
 * Rationale: Enforce encapsulation.
 *
 * @author lkuehne
 */
public class VisibilityModifierCheck
    extends Check
{
    /** whether protected members are allowed */
    private boolean protectedAllowed;

    /** whether package visible members are allowed */
    private boolean packageAllowed;

    /**
     * pattern for public members that should be ignored.  Note:
     * Earlier versions of checkstyle used ^f[A-Z][a-zA-Z0-9]*$ as the
     * default to allow CMP for EJB 1.1 with the default settings.
     * With EJB 2.0 it is not longer necessary to have public access
     * for persistent fields.
     */
    private String publicMemberFormat = "^serialVersionUID$";

    /** regexp for public members that should be ignored */
    private Pattern publicMemberPattern;

    /** Create an instance. */
    public VisibilityModifierCheck()
    {
        setPublicMemberPattern(publicMemberFormat);
    }

    /** @return whether protected members are allowed */
    public boolean isProtectedAllowed()
    {
        return protectedAllowed;
    }

    /**
     * Set whether protected members are allowed.
     * @param protectedAllowed whether protected members are allowed
     */
    public void setProtectedAllowed(boolean protectedAllowed)
    {
        this.protectedAllowed = protectedAllowed;
    }

    /** @return whether package visible members are allowed */
    public boolean isPackageAllowed()
    {
        return packageAllowed;
    }

    /**
     * Set whether package visible members are allowed.
     * @param packageAllowed whether package visible members are allowed
     */
    public void setPackageAllowed(boolean packageAllowed)
    {
        this.packageAllowed = packageAllowed;
    }

    /**
     * Set the pattern for public members to ignore.
     * @param pattern pattern for public members to ignore.
     */
    public void setPublicMemberPattern(String pattern)
    {
        try {
            publicMemberPattern = Utils.getPattern(pattern);
            publicMemberFormat = pattern;
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + pattern, e);
        }
    }

    /**
     * @return the regexp for public members to ignore.
     */
    private Pattern getPublicMemberRegexp()
    {
        return publicMemberPattern;
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {TokenTypes.VARIABLE_DEF, TokenTypes.OBJBLOCK};
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        if ((ast.getType() != TokenTypes.VARIABLE_DEF)
            || (ast.getParent().getType() != TokenTypes.OBJBLOCK))
        {
            return;
        }

        final DetailAST varNameAST = getVarNameAST(ast);
        final String varName = varNameAST.getText();
        final boolean inInterfaceOrAnnotationBlock =
            ScopeUtils.inInterfaceOrAnnotationBlock(ast);
        final Set<String> mods = getModifiers(ast);
        final String declaredScope = getVisibilityScope(mods);
        final String variableScope =
             inInterfaceOrAnnotationBlock ? "public" : declaredScope;

        if (!("private".equals(variableScope)
                || inInterfaceOrAnnotationBlock // implicitly static and final
                || (mods.contains("static") && mods.contains("final"))
                || ("package".equals(variableScope) && isPackageAllowed())
                || ("protected".equals(variableScope) && isProtectedAllowed())
                || ("public".equals(variableScope)
                   && getPublicMemberRegexp().matcher(varName).find())))
        {
            log(varNameAST.getLineNo(), varNameAST.getColumnNo(),
                    "variable.notPrivate", varName);
        }
    }

    /**
     * Returns the variable name in a VARIABLE_DEF AST.
     * @param variableDefAST an AST where type == VARIABLE_DEF AST.
     * @return the variable name in variableDefAST
     */
    private DetailAST getVarNameAST(DetailAST variableDefAST)
    {
        DetailAST ast = variableDefAST.getFirstChild();
        while (ast != null) {
            final DetailAST nextSibling = ast.getNextSibling();
            if (ast.getType() == TokenTypes.TYPE) {
                return nextSibling;
            }
            ast = nextSibling;
        }
        return null;
    }

    /**
     * Returns the set of modifier Strings for a VARIABLE_DEF AST.
     * @param variableDefAST AST for a vraiable definition
     * @return the set of modifier Strings for variableDefAST
     */
    private Set<String> getModifiers(DetailAST variableDefAST)
    {
        final AST modifiersAST = variableDefAST.getFirstChild();
        if (modifiersAST.getType() != TokenTypes.MODIFIERS) {
            throw new IllegalStateException("Strange parse tree");
        }
        final Set<String> retVal = Sets.newHashSet();
        AST modifier = modifiersAST.getFirstChild();
        while (modifier != null) {
            retVal.add(modifier.getText());
            modifier = modifier.getNextSibling();
        }
        return retVal;

    }

    /**
     * Returns the visibility scope specified with a set of modifiers.
     * @param modifiers the set of modifier Strings
     * @return one of "public", "private", "protected", "package"
     */
    private String getVisibilityScope(Set<String> modifiers)
    {
        final String[] explicitModifiers = {"public", "private", "protected"};
        for (final String candidate : explicitModifiers) {
            if (modifiers.contains(candidate)) {
                return candidate;
            }
        }
        return "package";
    }
}
