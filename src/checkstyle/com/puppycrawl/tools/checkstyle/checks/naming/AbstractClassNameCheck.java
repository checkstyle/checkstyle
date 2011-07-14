////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
 * regular expression and check that <code>abstract</code> modifier exists.
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

    /** whether to ignore checking the modifier */
    private boolean mIgnoreModifier;

    /** whether to ignore checking the name */
    private boolean mIgnoreName;

    /** Creates new instance of the check. */
    public AbstractClassNameCheck()
    {
        super(DEFAULT_FORMAT);
    }

    /**
     * Whether to ignore checking for the <code>abstract</code> modifier.
     * @param aValue new value
     */
    public void setIgnoreModifier(boolean aValue)
    {
        mIgnoreModifier = aValue;
    }

    /**
     * Whether to ignore checking the name.
     * @param aValue new value.
     */
    public void setIgnoreName(boolean aValue)
    {
        mIgnoreName = aValue;
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
        if (TokenTypes.CLASS_DEF == aAST.getType()) {
            visitClassDef(aAST);
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
            if (!mIgnoreName && !isMatchingClassName(className)) {
                log(aAST.getLineNo(), aAST.getColumnNo(),
                    "illegal.abstract.class.name", className, getFormat());
            }
        }
        else if (!mIgnoreModifier && isMatchingClassName(className)) {
            log(aAST.getLineNo(), aAST.getColumnNo(),
                "no.abstract.class.modifier", className);
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
