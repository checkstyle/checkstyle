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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.api.JavadocTagInfo;

/**
 * Represents a Javadoc tag. Provides methods to query what type of tag it is.
 * @author Oliver Burn
 */
public class JavadocTag
{
    /** the line number of the tag **/
    private final int mLineNo;
    /** the column number of the tag **/
    private int mColumnNo;
    /** an optional first argument. For example the parameter name. **/
    private final String mArg1;
    /** the JavadocTagInfo representing this tag **/
    private final JavadocTagInfo mTagInfo;

    /**
     * Constructs the object.
     * @param aLine the line number of the tag
     * @param aColumn the column number of the tag
     * @param aTag the tag string
     * @param aArg1 the tag argument
     **/
    public JavadocTag(int aLine, int aColumn, String aTag, String aArg1)
    {
        mLineNo = aLine;
        mColumnNo = aColumn;
        mArg1 = aArg1;
        mTagInfo = JavadocTagInfo.fromName(aTag);
    }

    /**
     * Constructs the object.
     * @param aLine the line number of the tag
     * @param aColumn the column number of the tag
     * @param aTag the tag string
     **/
    public JavadocTag(int aLine, int aColumn, String aTag)
    {
        this(aLine, aColumn, aTag, null);
    }

    /** @return the tag string **/
    public String getTagName()
    {
        return mTagInfo.getName();
    }

    /** @return the first argument. null if not set. **/
    public String getArg1()
    {
        return mArg1;
    }

    /** @return the line number **/
    public int getLineNo()
    {
        return mLineNo;
    }

    /** @return the column number */
    public int getColumnNo()
    {
        return mColumnNo;
    }

    @Override
    public String toString()
    {
        return "{Tag = '" + getTagName() + "', lineNo = " + getLineNo()
            + ", columnNo=" + mColumnNo + ", Arg1 = '" + getArg1() + "'}";
    }

    /** @return whether the tag is an 'author' tag **/
    public boolean isAuthorTag()
    {
        return JavadocTagInfo.AUTHOR.equals(mTagInfo);
    }

    /** @return whether the tag is an 'return' tag **/
    public boolean isReturnTag()
    {
        return JavadocTagInfo.RETURN.equals(mTagInfo);
    }

    /** @return whether the tag is an 'param' tag **/
    public boolean isParamTag()
    {
        return JavadocTagInfo.PARAM.equals(mTagInfo);
    }

    /** @return whether the tag is an 'throws' or 'exception' tag **/
    public boolean isThrowsTag()
    {
        return (JavadocTagInfo.THROWS.equals(mTagInfo)
            || JavadocTagInfo.EXCEPTION.equals(mTagInfo));
    }

    /** @return whether the tag is a 'see' or 'inheritDoc' tag **/
    public boolean isSeeOrInheritDocTag()
    {
        return (JavadocTagInfo.SEE.equals(mTagInfo) || isInheritDocTag());
    }

    /** @return whether the tag is a 'inheritDoc' tag **/
    public boolean isInheritDocTag()
    {
        return JavadocTagInfo.INHERIT_DOC.equals(mTagInfo);
    }

    /** @return whether the tag can contain references to imported classes **/
    public boolean canReferenceImports()
    {
        return (JavadocTagInfo.SEE.equals(mTagInfo)
                || JavadocTagInfo.LINK.equals(mTagInfo));
    }
}

