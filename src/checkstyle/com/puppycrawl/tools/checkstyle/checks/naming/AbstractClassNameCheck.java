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
 * regular expression and check up the abstract modifier of class.
 * </p>
 * <p>
 * Rationale: Abstract classes are convenience base class
 * implementations of interfaces, not types as such. As such
 * they should be named to indicate this. Also if names of classes
 * starts with 'Abstract' it's very convenient that they will
 * have abstract modifier.
 * </p>
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author <a href="mailto:solid.danil@gmail.com">Danil Lopatin</a>
 */
public final class AbstractClassNameCheck extends AbstractFormatCheck
{
    /** Default format for abstract class names */
    private static final String DEFAULT_FORMAT = "^Abstract.*$|^.*Factory$";

    /** allow checking 'abstract' modifiers */
    private boolean mCheckAbstractModifier;

    /** allow checking name by abstract modifier */
    private boolean mCheckName = true;

    /** Creates new instance of the check. */
    public AbstractClassNameCheck()
    {
        super(DEFAULT_FORMAT);
    }

    /**
     * Enable|Disable checking the class type.
     * @param aValue allow check abstract modifier.
     */
    public void setCheckModifier(boolean aValue)
    {
        mCheckAbstractModifier = aValue;
    }

    /**
     * Enable|Disable checking the class name if class has abstract modifier.
     * @param aValue allow check class name.
     */
    public void setCheckName(boolean aValue)
    {
        mCheckName = aValue;
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
        final String className =
            aAST.findFirstToken(TokenTypes.IDENT).getText();
        if (isAbstract(aAST)) {
            // if class has abstract modifier
            if (mCheckName && !isMatchingClassName(className)) {
                log(aAST.getLineNo(), aAST.getColumnNo(),
                    "illegal.abstract.class.name", className, getFormat());
            }
        }
        else {
            // if class without abstract modifier
            if (mCheckAbstractModifier
                    && isMatchingClassName(className))
            {
                log(aAST.getLineNo(), aAST.getColumnNo(),
                        "no.abstract.class.modifier", className);
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
