////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2007  Oliver Burn
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

package com.puppycrawl.tools.checkstyle.checks.header;

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Abstract super class for header checks.
 * Provides support for headerFile property.
 * @author o_sukhosolsky
 */
public abstract class AbstractHeaderCheck extends Check
{
    /** information about the expected header file. */
    private HeaderInfo mHeaderInfo = createHeaderInfo();

    /**
     * Return the header lines to check against.
     * @return the header lines to check against.
     */
    protected String[] getHeaderLines()
    {
        return mHeaderInfo.getHeaderLines();
    }

    /**
     * Abstract factory method to create an unconfigured
     * header info bean. Note that the actual type of the
     * return value can be subclass specific.
     *
     * @return a header info bean for this check.
     */
    protected abstract HeaderInfo createHeaderInfo();

    /**
     * Return the header info to check against.
     * @return the header info to check against.
     */
    protected HeaderInfo getHeaderInfo()
    {
        return mHeaderInfo;
    }

    /**
     * Set the header file to check against.
     * @param aFileName the file that contains the header to check against.
     * @throws ConversionException if the file cannot be loaded
     */
    public void setHeaderFile(String aFileName)
        throws ConversionException
    {
        mHeaderInfo.setHeaderFile(aFileName);
    }

    /**
     * Set the header to check against. Individual lines in the header
     * must be separated by '\n' characters.
     * @param aHeader header content to check against.
     * @throws ConversionException if the header cannot be interpreted
     */
    public void setHeader(String aHeader)
    {
        mHeaderInfo.setHeader(aHeader);
    }

    /**
     * Checks that required args were specified.
     * @throws CheckstyleException {@inheritDoc}
     * @see com.puppycrawl.tools.checkstyle.api.AutomaticBean#finishLocalSetup
     */
    @Override
    protected final void finishLocalSetup() throws CheckstyleException
    {
        if (mHeaderInfo.getHeaderLines() == null) {
            throw new CheckstyleException(
                    "property 'headerFile' is missing or invalid in module "
                    + getConfiguration().getName());
        }
    }

    /** {@inheritDoc} */
    @Override
    public final int[] getDefaultTokens()
    {
        return new int[0];
    }

}
