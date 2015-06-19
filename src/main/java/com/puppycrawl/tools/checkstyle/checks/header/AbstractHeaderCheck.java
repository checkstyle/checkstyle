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

package com.puppycrawl.tools.checkstyle.checks.header;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.io.Closeables;
import org.apache.commons.beanutils.ConversionException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import org.apache.commons.lang3.StringUtils;

/**
 * Abstract super class for header checks.
 * Provides support for header and headerFile properties.
 * @author o_sukhosolsky
 */
public abstract class AbstractHeaderCheck extends AbstractFileSetCheck {
    /** The file that contains the header to check against. */
    private String filename;

    /** Name of a charset to use for loading the header from a file. */
    private String charset = System.getProperty("file.encoding", "UTF-8");

    /** the lines of the header file. */
    private final List<String> readerLines = Lists.newArrayList();


    /**
     * Return the header lines to check against.
     * @return the header lines to check against.
     */
    protected ImmutableList<String> getHeaderLines() {
        return ImmutableList.copyOf(readerLines);
    }

    /**
     * Set the charset to use for loading the header from a file.
     * @param charset the charset to use for loading the header from a file
     * @throws UnsupportedEncodingException if charset is unsupported
     */
    public void setCharset(String charset) throws UnsupportedEncodingException {
        if (!Charset.isSupported(charset)) {
            final String message = "unsupported charset: '" + charset + "'";
            throw new UnsupportedEncodingException(message);
        }
        this.charset = charset;
    }

    /**
     * Set the header file to check against.
     * @param fileName the file that contains the header to check against.
     */
    public void setHeaderFile(String fileName) {
        // Handle empty param
        if (StringUtils.isBlank(fileName)) {
            return;
        }

        filename = fileName;
    }

    /**
     * Load the header from a file.
     * @throws CheckstyleException if the file cannot be loaded
     */
    private void loadHeaderFile() throws CheckstyleException {
        checkHeaderNotInitialized();
        Reader headerReader = null;
        try {
            final URI uri = resolveHeaderFile();
            headerReader = new InputStreamReader(new BufferedInputStream(
                    uri.toURL().openStream()), charset);
            loadHeader(headerReader);
        }
        catch (final IOException ex) {
            throw new CheckstyleException(
                    "unable to load header file " + filename, ex);
        }
        finally {
            Closeables.closeQuietly(headerReader);
        }
    }

    /**
     * Resolve the specified filename param to a URI.
     * @return resolved header file URI
     * @throws IOException on failure
     */
    private URI resolveHeaderFile() throws IOException {
        // figure out if this is a File or a URL
        URI uri;
        try {
            final URL url = new URL(filename);
            uri = url.toURI();
        }
        catch (final MalformedURLException ex) {
            uri = null;
        }
        catch (final URISyntaxException ex) {
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
                    final URL configUrl = AbstractHeaderCheck.class
                            .getResource(filename);
                    if (configUrl == null) {
                        throw new FileNotFoundException(filename);
                    }
                    uri = configUrl.toURI();
                }
                catch (final URISyntaxException e) {
                    throw new FileNotFoundException(filename);
                }
            }
        }
        return uri;
    }

    /**
     * Called before initializing the header.
     * @throws ConversionException if header has already been set
     */
    private void checkHeaderNotInitialized() {
        if (!readerLines.isEmpty()) {
            throw new ConversionException(
                    "header has already been set - "
                    + "set either header or headerFile, not both");
        }
    }

    /**
     * Set the header to check against. Individual lines in the header
     * must be separated by '\n' characters.
     * @param header header content to check against.
     * @throws ConversionException if the header cannot be interpreted
     */
    public void setHeader(String header) {
        if (StringUtils.isBlank(header)) {
            return;
        }

        checkHeaderNotInitialized();

        final String headerExpandedNewLines = header.replaceAll("\\\\n", "\n");

        final Reader headerReader = new StringReader(headerExpandedNewLines);
        try {
            loadHeader(headerReader);
        }
        catch (final IOException ex) {
            throw new ConversionException("unable to load header", ex);
        }
        finally {
            Closeables.closeQuietly(headerReader);
        }
    }

    /**
     * Load header to check against from a Reader into readerLines.
     * @param headerReader delivers the header to check against
     * @throws IOException if
     */
    private void loadHeader(final Reader headerReader) throws IOException {
        final LineNumberReader lnr = new LineNumberReader(headerReader);
        readerLines.clear();
        while (true) {
            final String l = lnr.readLine();
            if (l == null) {
                break;
            }
            readerLines.add(l);
        }
        postprocessHeaderLines();
    }

    /**
     * Hook method for post processing header lines.
     * This implementation does nothing.
     */
    protected void postprocessHeaderLines() {
        // No code by default
    }

    @Override
    protected final void finishLocalSetup() throws CheckstyleException {
        if (filename != null) {
            loadHeaderFile();
        }
        if (readerLines.isEmpty()) {
            throw new CheckstyleException(
                    "property 'headerFile' is missing or invalid in module "
                    + getConfiguration().getName());
        }
    }
}
