////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

/**
 * Represents a Javadoc tag. Provides methods to query what type of tag it is.
 * @author Oliver Burn
 **/
class JavadocTag
{
    /** the line number of the tag **/
    private final int mLineNo;
    /** the tag string **/
    private final String mTag;
    /** an optional first argument. For example the parameter name. **/
    private final String mArg1;

    /**
     * Constructs the object.
     * @param aLine the line number of the tag
     * @param aTag the tag string
     * @param aArg1 the tag argument
     **/
    JavadocTag(int aLine, String aTag, String aArg1)
    {
        mLineNo = aLine;
        mTag = aTag;
        mArg1 = aArg1;
    }

    /**
     * Constructs the object.
     * @param aLine the line number of the tag
     * @param aTag the tag string
     **/
    JavadocTag(int aLine, String aTag)
    {
        this(aLine, aTag, null);
    }

    /** @return the tag string **/
    String getTag()
    {
        return mTag;
    }

    /** @return the first argument. null if not set. **/
    String getArg1()
    {
        return mArg1;
    }

    /** @return the line number **/
    int getLineNo()
    {
        return mLineNo;
    }

    /** @return a string representation of the object **/
    public String toString()
    {
        return "{Tag = '" + getTag() + "', lineNo = " + getLineNo()
            + ", Arg1 = '" + getArg1() + "'}";
    }

    /** @return whether the tag is an 'author' tag **/
    boolean isAuthorTag()
    {
        return "author".equals(getTag());
    }

    /** @return whether the tag is an 'return' tag **/
    boolean isReturnTag()
    {
        return "return".equals(getTag());
    }

    /** @return whether the tag is an 'param' tag **/
    boolean isParamTag()
    {
        return "param".equals(getTag());
    }

    /** @return whether the tag is an 'throws' or 'exception' tag **/
    boolean isThrowsTag()
    {
        return ("throws".equals(getTag()) || "exception".equals(getTag()));
    }

    /** @return whether the tag is a 'see' or 'inheritDoc' tag **/
    boolean isSeeOrInheritDocTag()
    {
        return ("see".equals(getTag()) || isInheritDocTag());
    }

    /** @return whether the tag is a 'inheritDoc' tag **/
    boolean isInheritDocTag()
    {
        return "inheritDoc".equals(getTag());
    }
}

