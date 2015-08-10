////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.api.AbstractLoader;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FilterSet;

/**
 * Loads a filter chain of suppressions.
 * @author Rick Giles
 */
public final class SuppressionsLoader
    extends AbstractLoader {
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
    private final FilterSet filterChain = new FilterSet();

    /**
     * Creates a new {@code SuppressionsLoader} instance.
     * @throws ParserConfigurationException if an error occurs
     * @throws SAXException if an error occurs
     */
    private SuppressionsLoader()
        throws ParserConfigurationException, SAXException {
        super(createIdToResourceNameMap());
    }

    /**
     * Returns the loaded filter chain.
     * @return the loaded filter chain.
     */
    public FilterSet getFilterChain() {
        return filterChain;
    }

    @Override
    public void startElement(String namespaceURI,
                             String localName,
                             String qName,
                             Attributes atts)
        throws SAXException {
        if ("suppress".equals(qName)) {
            //add SuppressElement filter to the filter chain
            final String checks = atts.getValue("checks");
            final String modId = atts.getValue("id");
            if (checks == null && modId == null) {
                throw new SAXException("missing checks and id attribute");
            }
            final SuppressElement suppress;
            try {
                final String files = atts.getValue("files");
                suppress = new SuppressElement(files);
                if (modId != null) {
                    suppress.setModuleId(modId);
                }
                if (checks != null) {
                    suppress.setChecks(checks);
                }
            }
            catch (final PatternSyntaxException ignored) {
                throw new SAXException("invalid files or checks format");
            }
            final String lines = atts.getValue("lines");
            if (lines != null) {
                suppress.setLines(lines);
            }
            final String columns = atts.getValue("columns");
            if (columns != null) {
                suppress.setColumns(columns);
            }
            filterChain.addFilter(suppress);
        }
    }

    /**
     * Returns the suppression filters in a specified file.
     * @param filename name of the suppresssions file.
     * @return the filter chain of suppression elements specified in the file.
     * @throws CheckstyleException if an error occurs.
     */
    public static FilterSet loadSuppressions(String filename)
        throws CheckstyleException {
        // figure out if this is a File or a URL
        URI uri;
        try {
            final URL url = new URL(filename);
            uri = url.toURI();
        }
        catch (final MalformedURLException | URISyntaxException ignored) {
            // URL violating RFC 2396
            uri = null;
        }
        if (uri == null) {
            final File file = new File(filename);
            if (file.exists()) {
                uri = file.toURI();
            }
            else {
                // check to see if the file is in the classpath
                try {
                    final URL configUrl = SuppressionsLoader.class
                            .getResource(filename);
                    if (configUrl == null) {
                        throw new CheckstyleException("unable to find " + filename);
                    }
                    uri = configUrl.toURI();
                }
                catch (final URISyntaxException e) {
                    throw new CheckstyleException("unable to find " + filename, e);
                }
            }
        }
        final InputSource source = new InputSource(uri.toString());
        return loadSuppressions(source, filename);
    }

    /**
     * Returns the suppression filters in a specified source.
     * @param source the source for the suppressions.
     * @param sourceName the name of the source.
     * @return the filter chain of suppression elements in source.
     * @throws CheckstyleException if an error occurs.
     */
    private static FilterSet loadSuppressions(
            InputSource source, String sourceName)
        throws CheckstyleException {
        try {
            final SuppressionsLoader suppressionsLoader =
                new SuppressionsLoader();
            suppressionsLoader.parseInputSource(source);
            return suppressionsLoader.getFilterChain();
        }
        catch (final FileNotFoundException e) {
            throw new CheckstyleException("unable to find " + sourceName, e);
        }
        catch (final ParserConfigurationException | SAXException e) {
            throw new CheckstyleException("unable to parse "
                    + sourceName + " - " + e.getMessage(), e);
        }
        catch (final IOException e) {
            throw new CheckstyleException("unable to read " + sourceName, e);
        }
        catch (final NumberFormatException e) {
            throw new CheckstyleException("number format exception "
                + sourceName + " - " + e.getMessage(), e);
        }
    }

    /**
     * Creates mapping between local resources and dtd ids.
     * @return map between local resources and dtd ids.
     */
    private static Map<String, String> createIdToResourceNameMap() {
        final Map<String, String> map = Maps.newHashMap();
        map.put(DTD_PUBLIC_ID_1_0, DTD_RESOURCE_NAME_1_0);
        map.put(DTD_PUBLIC_ID_1_1, DTD_RESOURCE_NAME_1_1);
        return map;
    }
}
