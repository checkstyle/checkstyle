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
package com.puppycrawl.tools.checkstyle.checks.header;

import com.google.common.collect.ImmutableList;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Utils;

import org.apache.commons.beanutils.ConversionException;

/**
 * Abstract super class for header checks.
 * Provides support for header and headerFile properties.
 * @author o_sukhosolsky
 */
public abstract class AbstractHeaderCheck extends AbstractFileSetCheck
{
    /** The file that contains the header to check against. */
    private String mFilename;

    /** Name of a charset to use for loading the header from a file. */
    private String mCharset = System.getProperty("file.encoding", "UTF-8");

    /** the lines of the header file. */
    private final List<String> mHeaderLines = Lists.newArrayList();


    /**
     * Return the header lines to check against.
     * @return the header lines to check against.
     */
    protected ImmutableList<String> getHeaderLines()
    {
        return ImmutableList.copyOf(mHeaderLines);
    }

    /**
     * Set the charset to use for loading the header from a file.
     * @param aCharset the charset to use for loading the header from a file
     * @throws UnsupportedEncodingException if aCharset is unsupported
     */
    public void setCharset(String aCharset) throws UnsupportedEncodingException
    {
        if (!Charset.isSupported(aCharset)) {
            final String message = "unsupported charset: '" + aCharset + "'";
            throw new UnsupportedEncodingException(message);
        }
        mCharset = aCharset;
    }

    /**
     * Set the header file to check against.
     * @param aFileName the file that contains the header to check against.
     */
    public void setHeaderFile(String aFileName)
    {
        // Handle empty param
        if ((aFileName == null) || (aFileName.trim().length() == 0)) {
            return;
        }

        mFilename = aFileName;
    }

    /**
     * Load the header from a file.
     * @throws CheckstyleException if the file cannot be loaded
     */
    private void loadHeaderFile() throws CheckstyleException
    {
        checkHeaderNotInitialized();
        Reader headerReader = null;
        try {
            headerReader = new InputStreamReader(new BufferedInputStream(
                    new FileInputStream(mFilename)), mCharset);
            loadHeader(headerReader);
        }
        catch (final IOException ex) {
            throw new CheckstyleException(
                    "unable to load header file " + mFilename, ex);
        }
        finally {
            Utils.closeQuietly(headerReader);
        }
    }

    /**
     * Called before initializing the header.
     * @throws ConversionException if header has already been set
     */
    private void checkHeaderNotInitialized()
    {
        if (!mHeaderLines.isEmpty()) {
            throw new ConversionException(
                    "header has already been set - "
                    + "set either header or headerFile, not both");
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

        final String headerExpandedNewLines = aHeader.replaceAll("\\\\n", "\n");

        final Reader headerReader = new StringReader(headerExpandedNewLines);
        try {
            loadHeader(headerReader);
        }
        catch (final IOException ex) {
            throw new ConversionException("unable to load header", ex);
        }
        finally {
            Utils.closeQuietly(headerReader);
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
        mHeaderLines.clear();
        while (true) {
            final String l = lnr.readLine();
            if (l == null) {
                break;
            }
            mHeaderLines.add(l);
        }
        postprocessHeaderLines();
    }

    /**
     * Hook method for post processing header lines.
     * This implementation does nothing.
     */
    protected void postprocessHeaderLines()
    {
    }

    @Override
    protected final void finishLocalSetup() throws CheckstyleException
    {
        if (mFilename != null) {
            loadHeaderFile();
        }
        if (mHeaderLines.isEmpty()) {
            throw new CheckstyleException(
                    "property 'headerFile' is missing or invalid in module "
                    + getConfiguration().getName());
        }
    }
}
