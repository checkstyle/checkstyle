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
package com.puppycrawl.tools.checkstyle.filters;

import com.puppycrawl.tools.checkstyle.api.AbstractLoader;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FilterSet;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.regexp.RESyntaxException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Loads a filter chain of suppressions.
 * @author Rick Giles
 */
public final class SuppressionsLoader
    extends AbstractLoader
{
    /** the public ID for the configuration dtd */
    private static final String DTD_PUBLIC_ID =
        "-//Puppy Crawl//DTD Suppressions 1.0//EN";

    /** the resource for the configuration dtd */
    private static final String DTD_RESOURCE_NAME =
        "com/puppycrawl/tools/checkstyle/suppressions_1_0.dtd";

    /**
     * the filter chain to return in getAFilterChain(),
     * configured during parsing
     */
    private final FilterSet mFilterChain = new FilterSet();

    /**
     * Creates a new <code>SuppressionsLoader</code> instance.
     * @throws ParserConfigurationException if an error occurs
     * @throws SAXException if an error occurs
     */
    private SuppressionsLoader()
        throws ParserConfigurationException, SAXException
    {
        super(DTD_PUBLIC_ID, DTD_RESOURCE_NAME);
    }

    /**
     * Returns the loaded filter chain.
     * @return the loaded filter chain.
     */
    public FilterSet getFilterChain()
    {
        return mFilterChain;
    }

    /** @see org.xml.sax.helpers.DefaultHandler **/
    public void startElement(String aNamespaceURI,
                             String aLocalName,
                             String aQName,
                             Attributes aAtts)
        throws SAXException
    {
        if (aQName.equals("suppress")) {
            //add SuppressElement filter to the filter chain
            final String files = aAtts.getValue("files");
            if (files == null) {
                throw new SAXException("missing files attribute");
            }
            final String checks = aAtts.getValue("checks");
            if (checks == null) {
                throw new SAXException("missing checks attribute");
            }
            final SuppressElement suppress;
            try {
                suppress = new SuppressElement(files, checks);
            }
            catch (RESyntaxException e) {
                throw new SAXException("invalid files or checks format");
            }
            final String lines = aAtts.getValue("lines");
            if (lines != null) {
                suppress.setLines(lines);
            }
            final String columns = aAtts.getValue("columns");
            if (columns != null) {
                suppress.setColumns(columns);
            }
            mFilterChain.addFilter(suppress);
        }
    }

    /**
     * Returns the suppression filters in a specified file.
     * @param aFilename name of the suppresssions file.
     * @return the filter chain of suppression elements specified in the file.
     * @throws CheckstyleException if an error occurs.
     */
    public static FilterSet loadSuppressions(String aFilename)
        throws CheckstyleException
    {
        FileReader reader = null;
        try {
            reader = new FileReader(aFilename);
        }
        catch (FileNotFoundException e) {
            throw new CheckstyleException(
                "unable to find " + aFilename, e);
        }
        final InputSource source = new InputSource(reader);
        return loadSuppressions(source, aFilename);
    }

    /**
     * Returns the suppression filters in a specified source.
     * @param aSource the source for the suppressions.
     * @param aSourceName the name of the source.
     * @return the filter chain of suppression elements in aSource.
     * @throws CheckstyleException if an error occurs.
     */
    private static FilterSet loadSuppressions(
            InputSource aSource, String aSourceName)
        throws CheckstyleException
    {
        try {
            final SuppressionsLoader suppressionsLoader =
                new SuppressionsLoader();
            suppressionsLoader.parseInputSource(aSource);
            return suppressionsLoader.getFilterChain();
        }
        catch (FileNotFoundException e) {
            throw new CheckstyleException("unable to find " + aSourceName, e);
        }
        catch (ParserConfigurationException e) {
            throw new CheckstyleException("unable to parse " + aSourceName, e);
        }
        catch (SAXException e) {
            throw new CheckstyleException("unable to parse "
                    + aSourceName + " - " + e.getMessage(), e);
        }
        catch (IOException e) {
            throw new CheckstyleException("unable to read " + aSourceName, e);
        }
        catch (NumberFormatException e) {
            throw new CheckstyleException("number format exception "
                + aSourceName + " - " + e.getMessage(), e);
        }
    }
}
