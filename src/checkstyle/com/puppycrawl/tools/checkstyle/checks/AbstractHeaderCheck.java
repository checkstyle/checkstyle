////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2004  Oliver Burn
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

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import org.apache.commons.beanutils.ConversionException;

/**
 * Abstract super class for header checks.
 * Provides support for headerFile property.
 * @author o_sukhosolsky
 */
public abstract class AbstractHeaderCheck extends Check
{
    /** the lines of the header file. */
    private String[] mHeaderLines;

    /**
     * Return the header lines to check against.
     * @return the header lines to check against.
     */
    protected String[] getHeaderLines()
    {
        return mHeaderLines;
    }

    /**
     * Set the header file to check against.
     * @param aFileName the file that contains the header to check against.
     * @throws ConversionException if the file cannot be loaded
     */
    public void setHeaderFile(String aFileName)
        throws ConversionException
    {
        // Handle empty param
        if ((aFileName == null) || (aFileName.trim().length() == 0)) {
            return;
        }

        // load the file
        try {
            final LineNumberReader lnr =
                    new LineNumberReader(new FileReader(aFileName));
            final ArrayList lines = new ArrayList();
            while (true) {
                final String l = lnr.readLine();
                if (l == null) {
                    break;
                }
                lines.add(l);
            }
            mHeaderLines = (String[]) lines.toArray(new String[0]);
        }
        catch (IOException ex) {
            throw new ConversionException(
                    "unable to load header file " + aFileName, ex);
        }

    }

    /**
     * Checks that required args were specified.
     * @see com.puppycrawl.tools.checkstyle.api.AutomaticBean#finishLocalSetup
     */
    protected final void finishLocalSetup() throws CheckstyleException
    {
        if (mHeaderLines == null) {
            throw new CheckstyleException(
                    "property 'headerFile' is missing or invalid in module "
                    + getConfiguration().getName());
        }
    }

    /** {@inheritDoc} */
    public final int[] getDefaultTokens()
    {
        return new int[0];
    }
}
