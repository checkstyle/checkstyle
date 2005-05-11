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
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.Utils;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.ConversionException;

/**
 * <p> Abstract class for checks that verify strings using a <a
 * href="http://jakarta.apache.org/regexp/apidocs/org/apache/regexp/RE.html">
 * regular expression</a>.  It provides support for setting the regular
 * expression using the property name <code>format</code>.  </p>
 *
 * @author Oliver Burn
 * @version 1.0
 */
public abstract class AbstractFormatCheck
    extends Check
{
    /** the regexp to match against */
    private Pattern mRegexp;
    /** the format string of the regexp */
    private String mFormat;

    /**
     * Creates a new <code>AbstractFormatCheck</code> instance.
     * @param aDefaultFormat default format
     * @throws ConversionException unable to parse aDefaultFormat
     */
    public AbstractFormatCheck(String aDefaultFormat)
        throws ConversionException
    {
        setFormat(aDefaultFormat);
    }

    /**
     * Set the format to the specified regular expression.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setFormat(String aFormat)
        throws ConversionException
    {
        try {
            mRegexp = Utils.getPattern(aFormat);
            mFormat = aFormat;
        }
        catch (PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }

    /** @return the regexp to match against */
    public Pattern getRegexp()
    {
        return mRegexp;
    }

    /** @return the regexp format */
    public String getFormat()
    {
        return mFormat;
    }
}
