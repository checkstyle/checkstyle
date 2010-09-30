////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractFormatCheck;

/**
 * <p>
 * Ensures that the names of abstract classes conforming to some
 * regular expression.
 * </p>
 * <p>
 * Rationale: Abstract classes are convenience base class
 * implementations of interfaces, not types as such. As such
 * they should be named to indicate this.
 * </p>
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 */
public final class AbstractClassNameCheck extends AbstractFormatCheck
{
    /** Defualt format for abstract class names */
    private static final String DEFAULT_FORMAT = "^Abstract.*$|^.*Factory$";

    /** Creates new instance of the check. */
    public AbstractClassNameCheck()
    {
        super(DEFAULT_FORMAT);
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[]{TokenTypes.CLASS_DEF};
    }

    @Override
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.CLASS_DEF:
            visitClassDef(aAST);
            break;
        default:
            throw new IllegalStateException(aAST.toString());
        }
    }

    /**
     * Checks class definition.
     * @param aAST class definition for check.
     */
    private void visitClassDef(DetailAST aAST)
    {
        if (isAbstract(aAST)) {
            final String className =
                aAST.findFirstToken(TokenTypes.IDENT).getText();

            if (!isMatchingClassName(className)) {
                log(aAST.getLineNo(), aAST.getColumnNo(),
                    "illegal.abstract.class.name", className, getFormat());
            }
        }
    }

    /**
     * @param aAST class definition for check.
     * @return true if a given class declared as abstract.
     */
    private boolean isAbstract(DetailAST aAST)
    {
        final DetailAST abstractAST = aAST.findFirstToken(TokenTypes.MODIFIERS)
            .findFirstToken(TokenTypes.ABSTRACT);

        return abstractAST != null;
    }

    /**
     * @param aClassName class name for check.
     * @return true if class name matches format of abstract class names.
     */
    private boolean isMatchingClassName(String aClassName)
    {
        return getRegexp().matcher(aClassName).find();
    }
}
