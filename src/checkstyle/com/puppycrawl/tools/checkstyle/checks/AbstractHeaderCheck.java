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

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Utils;

import org.apache.commons.beanutils.ConversionException;
import org.apache.regexp.RE;

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

        checkHeaderNotInitialized();

        // load the file
        Reader headerReader = null;
        try {
            headerReader = new FileReader(aFileName);
            loadHeader(headerReader);
        }
        catch (IOException ex) {
            throw new ConversionException(
                    "unable to load header file " + aFileName, ex);
        }
        finally {
            if (headerReader != null) {
                try {
                    headerReader.close();
                }
                catch (IOException ex) {
                    throw new ConversionException(
                            "unable to close header file " + aFileName, ex);
                }
            }
        }
    }

    /**
     * Set the header to check against. Individual lines in the header
     * must be separated by '\n' characters.
     * @param aHeader header content to check against.
     * @throws ConversionException if the header cannot be interpreted
     */
    public void setHeader(String aHeader)
    {
        if ((aHeader == null) || (aHeader.trim().length() == 0)) {
            return;
        }

        checkHeaderNotInitialized();

        // in JDK 1.4 we'd simply do aHeader.replaceAll("\\\\n", "\n");
        final RE re = Utils.getRE("\\\\n");
        final String headerExpandedNewLines = re.subst(aHeader, "\n");

        final Reader headerReader = new StringReader(headerExpandedNewLines);
        try {
            loadHeader(headerReader);
        }
        catch (IOException ex) {
            throw new ConversionException(
                    "unable to load header", ex);
        }
        finally {
            try {
                headerReader.close();
            }
            catch (IOException ex) {
                // shouldn't happen with StringReader
                throw new ConversionException(
                        "unable to close header", ex);
            }
        }

    }

    /**
     * Called before initializing the header.
     * @throws ConversionException if header has already been set
     */
    private void checkHeaderNotInitialized()
    {
        if (mHeaderLines != null) {
            throw new ConversionException(
                    "header has already been set - "
                    + "set either header or headerFile, not both");
        }
    }

    /**
     * Load header to check against from a Reader into mHeaderLines.
     * @param aHeaderReader delivers the header to check against
     * @throws IOException if
     */
    private void loadHeader(final Reader aHeaderReader) throws IOException
    {
        final LineNumberReader lnr = new LineNumberReader(aHeaderReader);
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
