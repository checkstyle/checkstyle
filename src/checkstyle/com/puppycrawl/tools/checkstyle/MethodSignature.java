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
package com.puppycrawl.tools.checkstyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the signature for a method. Actually this is a lie as it only
 * represents the method name, parameters, exceptions and line number of
 * the name.
 * It does not have the return type or modifiers.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
class MethodSignature
{
    /** the parameters **/
    private final List mParams = new ArrayList();
    /** the throws **/
    private List mThrows = new ArrayList();
    /** the name **/
    private MyCommonAST mName;
    /** the modifiers **/
    private final MyModifierSet mModSet = new MyModifierSet();
    /** the return type **/
    private MyCommonAST mReturnType;

    /**
     * Adds a parameter.
     * @param aParam the parameter details.
     **/
    void addParam(LineText aParam)
    {
        mParams.add(aParam);
    }

    /** @return the parameters as a List that can be modified **/
    List getParams()
    {
        return new ArrayList(mParams);
    }

    /**
     * Sets the list of throws.
     * @param aThrows the throws
     **/
    void setThrows(List aThrows)
    {
        mThrows = aThrows;
    }

    /** @return the list of throws as a List that can be modified **/
    List getThrows()
    {
        return new ArrayList(mThrows);
    }

    /**
     * @param aName method name
     */
    void setName(MyCommonAST aName)
    {
        mName = aName;
    }

    /** @return method name **/
    MyCommonAST getName()
    {
        return mName;
    }

    /** @return the <code>MyModifierSet</code> for the method **/
    MyModifierSet getModSet()
    {
        return mModSet;
    }

    /** @return the first line of the method signature **/
    int getFirstLineNo()
    {
        return (mModSet.size() > 0)
            ? mModSet.getFirstLineNo()
            : mName.getLineNo();
    }

    /** @return the return type **/
    MyCommonAST getReturnType()
    {
        return mReturnType;
    }

    /**
     * Set the return type
     * @param aReturnType the return type
     */
    void setReturnType(MyCommonAST aReturnType)
    {
        mReturnType = aReturnType;
    }

    /** @return whether the method is a function **/
    boolean isFunction()
    {
        return isConstructor()
            ? false
            : !"void".equals(mReturnType.getText().trim());
    }

    /** @return whether the method is really a constructor **/
    boolean isConstructor()
    {
        return (mReturnType == null);
    }
}
