////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.beanutils.ConversionException;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * <p>Checks that a local variable or a parameter does not shadow
 * a field that is defined in the same class.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="HiddenField"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check so that it checks variables but not
 * parameters is:
 * </p>
 * <pre>
 * &lt;module name="HiddenField"&gt;
 *    &lt;property name="tokens" value="VARIABLE_DEF"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Rick Giles
 * @version 1.0
 */
public class HiddenFieldCheck
    extends Check
{
    /** stack of sets of field names,
     * one for each class of a set of nested classes */
    private LinkedList mFieldsStack = null;

    /** the regexp to match against */
    private RE mRegexp = null;
    /** the format string of the regexp */
    private String mIgnoreFormat;

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
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getRequiredTokens()
    {
        return new int[] {
            TokenTypes.CLASS_DEF,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree(DetailAST aRootAST)
    {
        mFieldsStack = new LinkedList();
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.CLASS_DEF) {
            //find and push fields
            final HashSet fieldSet = new HashSet(); //fields container
            //add fields to container
            final DetailAST objBlock =
                aAST.findFirstToken(TokenTypes.OBJBLOCK);
            DetailAST child = (DetailAST) objBlock.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.VARIABLE_DEF) {
                    final String name =
                        child.findFirstToken(TokenTypes.IDENT).getText();
                    fieldSet.add(name);
                }
                child = (DetailAST) child.getNextSibling();
            }
            mFieldsStack.addLast(fieldSet); //push container
        }
        else {
            //must be VARIABLE_DEF or PARAMETER_DEF
            processVariable(aAST);
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
            if (ScopeUtils.isLocalVariableDef(aAST)
                || (aAST.getType() == TokenTypes.PARAMETER_DEF))
            {
                //local variable or parameter. Does it shadow a field?
                final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
                final String name = nameAST.getText();
                final Iterator it = mFieldsStack.iterator();
                while (it.hasNext()) {
                    final HashSet aFieldsSet = (HashSet) it.next();
                    if (aFieldsSet.contains(name)) {
                        if ((mRegexp == null) || (!getRegexp().match(name))) {
                            log(nameAST.getLineNo(), nameAST.getColumnNo(),
                                "hidden.field", name);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Set the ignore format to the specified regular expression.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setIgnoreFormat(String aFormat)
        throws ConversionException
    {
        try {
            mRegexp = Utils.getRE(aFormat);
            mIgnoreFormat = aFormat;
        }
        catch (RESyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }

    /** @return the regexp to match against */
    public RE getRegexp()
    {
        return mRegexp;
    }
}
