////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks that a local variable or a parameter does not shadow
 * a field that is defined in the same class.
 *
 * @author Rick Giles
 * @version 1.0
 */
public class HiddenFieldCheck
    extends Check
{

    /** stack of sets of field names,
     * one for each class of a set of nested classes */
    private LinkedList mFieldsStack = null;
    
    /** check for shadowing parameters **/
    private boolean mCheckParameters = true;

    /**
     * Set whether to check for shadowing parameters.
     * @param aFlag a <code>Boolean</code> value
     */
    public void setCheckParameters(boolean aFlag)
    {
        mCheckParameters = aFlag;
    }
   
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.CLASS_DEF,
        };
    }
    
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree()
    {
        mFieldsStack = new LinkedList();
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
            case TokenTypes.VARIABLE_DEF:               
                processVariable(aAST);
                break;
            case TokenTypes.PARAMETER_DEF:
                if (mCheckParameters) {               
                    processVariable(aAST);
                }
                break;
            case TokenTypes.CLASS_DEF:
                //push
                mFieldsStack.addLast(new HashSet());
                break;
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */    
    public void leaveToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.CLASS_DEF) {
            //pop
            mFieldsStack.removeLast();         
        }
    }

    /**
     * Process a variable token.
     * Check whether a local variable or parameter shadows a field.
     * Store a field for later comparison with local variables and parameters.
     * @param aAST the variable token.
     */
    private void processVariable(DetailAST aAST)
    {
        if (!ScopeUtils.inInterfaceBlock(aAST)) {
            if (ScopeUtils.inCodeBlock(aAST)) {
                //local variable or parameter. Does it shadow a field?
                final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
                final String name = nameAST.getText();
                Iterator it = mFieldsStack.iterator();
                while (it.hasNext()) {
                    final HashSet aFieldsSet = (HashSet)it.next();
                    if (aFieldsSet.contains(name)) {
                        log(nameAST.getLineNo(), nameAST.getColumnNo(),
                            "hidden.field", name);
                        break;
                    }
                }
            }
            else {
                //field. Add its name to the top stack element
                final String name
                    = aAST.findFirstToken(TokenTypes.IDENT).getText();
                ((HashSet)mFieldsStack.getLast()).add(name);
            }
        }
    }
 }
