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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.DeclarationCollector;

/**
 * <p>Checks that code doesn't rely on the &quot;this&quot; default.
 * That is references to instance variables and methods of the present
 * object are explicitly of the form &quot;this.varName&quot; or
 * &quot;this.methodName(args)&quot;.
 *</p>
 * <p>Examples of use:
 * <pre>
 * &lt;module name=&quot;RequireThis&quot;/&gt;
 * </pre>
 * An example of how to configure to check <code>this</code> qualifier for
 * methods only:
 * <pre>
 * &lt;module name=&quot;RequireThis&quot;&gt;
 *   &lt;property name=&quot;checkFields&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;checkMethods&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * </p>
 * <p>Limitations: I'm not currently doing anything about static variables
 * or catch-blocks.  Static methods invoked on a class name seem to be OK;
 * both the class name and the method name have a DOT parent.
 * Non-static methods invoked on either this or a variable name seem to be
 * OK, likewise.</p>
 * <p>Much of the code for this check was cribbed from Rick Giles's
 * <code>HiddenFieldCheck</code>.</p>
 *
 * @author Stephen Bloch
 * @author o_sukhodolsky
 */
public class RequireThisCheck extends DeclarationCollector
{
    /** whether we should check fields usage. */
    private boolean mCheckFields = true;
    /** whether we should check methods usage. */
    private boolean mCheckMethods = true;

    /**
     * Setter for checkFields property.
     * @param aCheckFields should we check fields usage or not.
     */
    public void setCheckFields(boolean aCheckFields)
    {
        mCheckFields = aCheckFields;
    }
    /**
     * @return true if we should check fields usage false overwise.
     */
    public boolean getCheckFields()
    {
        return mCheckFields;
    }

    /**
     * Setter for checkMethods property.
     * @param aCheckMethods should we check methods usage or not.
     */
    public void setCheckMethods(boolean aCheckMethods)
    {
        mCheckMethods = aCheckMethods;
    }
    /**
     * @return true if we should check methods usage false overwise.
     */
    public boolean getCheckMethods()
    {
        return mCheckMethods;
    }

    /** Creates new instance of the check. */
    public RequireThisCheck()
    {
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.IDENT,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.SLIST,
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        super.visitToken(aAST);
        if (aAST.getType() == TokenTypes.IDENT) {
            processIDENT(aAST);
        }
    } // end visitToken

    /**
     * Checks if a given IDENT is method call or field name which
     * require explicit <code>this</code> qualifier.
     *
     * @param aAST IDENT to check.
     */
    private void processIDENT(DetailAST aAST)
    {
        final int parentType = aAST.getParent().getType();

        if (parentType == TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR
            || parentType == TokenTypes.ANNOTATION
            || parentType == TokenTypes.ANNOTATION_FIELD_DEF)
        {
            //cannot refer to 'this' from annotations
            return;
        }

        // let's check method calls
        if (parentType == TokenTypes.METHOD_CALL) {
            if (mCheckMethods) {
                log(aAST, "require.this.method", aAST.getText());
            }
            return;
        }

        // let's check fields
        if (!mCheckFields) {
            // we shouldn't check fields
            return;
        }

        if (ScopeUtils.getSurroundingScope(aAST) == null) {
            // it is not a class or inteface it's
            // either import or package
            // we shouldn't checks this
            return;
        }

        if ((parentType == TokenTypes.DOT)
            && (aAST.getPreviousSibling() != null))
        {
            // it's the method name in a method call; no problem
            return;
        }
        if ((parentType == TokenTypes.TYPE)
            || (parentType == TokenTypes.LITERAL_NEW))
        {
            // it's a type name; no problem
            return;
        }
        if ((parentType == TokenTypes.VARIABLE_DEF)
            || (parentType == TokenTypes.CTOR_DEF)
            || (parentType == TokenTypes.METHOD_DEF)
            || (parentType == TokenTypes.CLASS_DEF)
            || (parentType == TokenTypes.ENUM_DEF)
            || (parentType == TokenTypes.INTERFACE_DEF)
            || (parentType == TokenTypes.PARAMETER_DEF))
        {
            // it's being declared; no problem
            return;
        }

        final String name = aAST.getText();
        if (isClassField(name)) {
            log(aAST, "require.this.variable", name);
        }
    }
} // end class RequireThis
