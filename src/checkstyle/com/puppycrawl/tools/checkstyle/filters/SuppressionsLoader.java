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
package com.puppycrawl.tools.checkstyle.filters;

import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.api.AbstractLoader;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FilterSet;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

/**
 * Loads a filter chain of suppressions.
 * @author Rick Giles
 */
public final class SuppressionsLoader
    extends AbstractLoader
{
    /** the public ID for the configuration dtd */
    private static final String DTD_PUBLIC_ID_1_0 =
        "-//Puppy Crawl//DTD Suppressions 1.0//EN";
    /** the resource for the configuration dtd */
    private static final String DTD_RESOURCE_NAME_1_0 =
        "com/puppycrawl/tools/checkstyle/suppressions_1_0.dtd";
    /** the public ID for the configuration dtd */
    private static final String DTD_PUBLIC_ID_1_1 =
        "-//Puppy Crawl//DTD Suppressions 1.1//EN";
    /** the resource for the configuration dtd */
    private static final String DTD_RESOURCE_NAME_1_1 =
        "com/puppycrawl/tools/checkstyle/suppressions_1_1.dtd";

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
        super(createIdToResourceNameMap());
    }

    /**
     * Returns the loaded filter chain.
     * @return the loaded filter chain.
     */
    public FilterSet getFilterChain()
    {
        return mFilterChain;
    }

    @Override
    public void startElement(String aNamespaceURI,
                             String aLocalName,
                             String aQName,
                             Attributes aAtts)
        throws SAXException
    {
        if ("suppress".equals(aQName)) {
            //add SuppressElement filter to the filter chain
            final String files = aAtts.getValue("files");
            if (files == null) {
                throw new SAXException("missing files attribute");
            }
            final String checks = aAtts.getValue("checks");
            final String modId = aAtts.getValue("id");
            if ((checks == null) && (modId == null)) {
                throw new SAXException("missing checks and id attribute");
            }
            final SuppressElement suppress;
            try {
                suppress = new SuppressElement(files);
                if (modId != null) {
                    suppress.setModuleId(modId);
                }
                if (checks != null) {
                    suppress.setChecks(checks);
                }
            }
            catch (final PatternSyntaxException e) {
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
        InputStream fis;
        try {
            fis = new FileInputStream(aFilename);
        }
        catch (final FileNotFoundException e) {
            if (aFilename.matches("^https?://.+")) {
                // this is a URL, load it as such
                try {
                    fis = new URL(aFilename).openStream();
                }
                catch (MalformedURLException e1) {
                    throw new CheckstyleException(
                        "Invalid URL: " + aFilename, e1);
                }
                catch (IOException e1) {
                    throw new CheckstyleException(
                        "unable to read " + aFilename, e1);
                }
            }
            else {
                // check for the file in the classpath
                fis = SuppressionsLoader.class.getResourceAsStream(aFilename);
            }
            if (fis == null) {
                throw new CheckstyleException("unable to find " + aFilename, e);
            }
        }
        final InputSource source = new InputSource(fis);
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
        catch (final FileNotFoundException e) {
            throw new CheckstyleException("unable to find " + aSourceName, e);
        }
        catch (final ParserConfigurationException e) {
            throw new CheckstyleException("unable to parse " + aSourceName, e);
        }
        catch (final SAXException e) {
            throw new CheckstyleException("unable to parse "
                    + aSourceName + " - " + e.getMessage(), e);
        }
        catch (final IOException e) {
            throw new CheckstyleException("unable to read " + aSourceName, e);
        }
        catch (final NumberFormatException e) {
            throw new CheckstyleException("number format exception "
                + aSourceName + " - " + e.getMessage(), e);
        }
    }

    /**
     * Creates mapping between local resources and dtd ids.
     * @return map between local resources and dtd ids.
     */
    private static Map<String, String> createIdToResourceNameMap()
    {
        final Map<String, String> map = Maps.newHashMap();
        map.put(DTD_PUBLIC_ID_1_0, DTD_RESOURCE_NAME_1_0);
        map.put(DTD_PUBLIC_ID_1_1, DTD_RESOURCE_NAME_1_1);
        return map;
    }
}
