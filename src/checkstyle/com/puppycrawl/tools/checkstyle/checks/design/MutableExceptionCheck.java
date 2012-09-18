////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractFormatCheck;

/**
 * <p> Ensures that exceptions (defined as any class name conforming
 * to some regular expression) are immutable. That is, have only final
 * fields.</p>
 * <p> Rationale: Exception instances should represent an error
 * condition. Having non final fields not only allows the state to be
 * modified by accident and therefore mask the original condition but
 * also allows developers to accidentally forget to initialise state
 * thereby leading to code catching the exception to draw incorrect
 * conclusions based on the state.</p>
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 */
public final class MutableExceptionCheck extends AbstractFormatCheck
{
    /** Default value for format property. */
    private static final String DEFAULT_FORMAT = "^.*Exception$|^.*Error$";
    /** Stack of checking information for classes. */
    private final FastStack<Boolean> mCheckingStack = FastStack.newInstance();
    /** Should we check current class or not. */
    private boolean mChecking;

    /** Creates new instance of the check. */
    public MutableExceptionCheck()
    {
        super(DEFAULT_FORMAT);
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.CLASS_DEF, TokenTypes.VARIABLE_DEF};
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
        case TokenTypes.VARIABLE_DEF:
            visitVariableDef(aAST);
            break;
        default:
            throw new IllegalStateException(aAST.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.CLASS_DEF:
            leaveClassDef();
            break;
        default:
            // Do nothing
        }
    }

    /**
     * Called when we start processing class definition.
     * @param aAST class definition node
     */
    private void visitClassDef(DetailAST aAST)
    {
        mCheckingStack.push(mChecking ? Boolean.TRUE : Boolean.FALSE);
        mChecking =
            isExceptionClass(aAST.findFirstToken(TokenTypes.IDENT).getText());
    }

    /** Called when we leave class definition. */
    private void leaveClassDef()
    {
        mChecking = (mCheckingStack.pop()).booleanValue();
    }

    /**
     * Checks variable definition.
     * @param aAST variable def node for check.
     */
    private void visitVariableDef(DetailAST aAST)
    {
        if (mChecking && (aAST.getParent().getType() == TokenTypes.OBJBLOCK)) {
            final DetailAST modifiersAST =
                aAST.findFirstToken(TokenTypes.MODIFIERS);

            if (!(modifiersAST.findFirstToken(TokenTypes.FINAL) != null)) {
                log(aAST.getLineNo(),  aAST.getColumnNo(), "mutable.exception",
                        aAST.findFirstToken(TokenTypes.IDENT).getText());
            }
        }
    }

    /**
     * @param aClassName class name to check
     * @return true if a given class name confirms specified format
     */
    private boolean isExceptionClass(String aClassName)
    {
        return getRegexp().matcher(aClassName).find();
    }
}
