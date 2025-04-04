///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.header;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;

/**
 * Abstract super class for header checks.
 * Provides support for header and headerFile properties.
 */
public abstract class AbstractHeaderCheck extends AbstractFileSetCheck
    implements ExternalResourceHolder {

    /** Pattern to detect occurrences of '\n' in text. */
    private static final Pattern ESCAPED_LINE_FEED_PATTERN = Pattern.compile("\\\\n");

    /** The lines of the header file. */
    private final List<String> readerLines = new ArrayList<>();

    /** Specify the name of the file containing the required header. */
    private URI headerFile;

    /** Specify the character encoding to use when reading the headerFile. */
    @XdocsPropertyType(PropertyType.STRING)
    private Charset charset;

    /**
     * Hook method for post-processing header lines.
     * This implementation does nothing.
     */
    protected abstract void postProcessHeaderLines();

    /**
     * Return the header lines to check against.
     *
     * @return the header lines to check against.
     */
    protected List<String> getHeaderLines() {
        return List.copyOf(readerLines);
    }

    /**
     * Setter to specify the character encoding to use when reading the headerFile.
     *
     * @param charset the charset name to use for loading the header from a file
     */
    public void setCharset(String charset) {
        this.charset = createCharset(charset);
    }

    /**
     * Setter to specify the name of the file containing the required header.
     *
     * @param uri the uri of the header to load.
     * @throws CheckstyleException if fileName is empty.
     */
    public void setHeaderFile(URI uri) throws CheckstyleException {
        if (uri == null) {
            throw new CheckstyleException(
                "property 'headerFile' is missing or invalid in module "
                    + getConfiguration().getName());
        }

        headerFile = uri;
    }

    /**
     * Load the header from a file.
     *
     * @throws CheckstyleException if the file cannot be loaded
     */
    private void loadHeaderFile() throws CheckstyleException {
        checkHeaderNotInitialized();
        try (Reader headerReader = new InputStreamReader(new BufferedInputStream(
                    headerFile.toURL().openStream()), charset)) {
            loadHeader(headerReader);
        }
        catch (final IOException ex) {
            throw new CheckstyleException(
                    "unable to load header file " + headerFile, ex);
        }
    }

    /**
     * Called before initializing the header.
     *
     * @throws IllegalArgumentException if header has already been set
     */
    private void checkHeaderNotInitialized() {
        if (!readerLines.isEmpty()) {
            throw new IllegalArgumentException(
                    "header has already been set - "
                    + "set either header or headerFile, not both");
        }
    }

    /**
     * Creates charset by name.
     *
     * @param name charset name
     * @return created charset
     * @throws UnsupportedCharsetException if charset is unsupported
     */
    private static Charset createCharset(String name) {
        if (!Charset.isSupported(name)) {
            final String message = "unsupported charset: '" + name + "'";
            throw new UnsupportedCharsetException(message);
        }
        return Charset.forName(name);
    }

    /**
     * Specify the required header specified inline.
     * Individual header lines must be separated by the string
     * {@code "\n"}(even on platforms with a different line separator).
     *
     * @param header header content to check against.
     * @throws IllegalArgumentException if the header cannot be interpreted
     */
    public void setHeader(String header) {
        if (!StringUtils.isBlank(header)) {
            checkHeaderNotInitialized();

            final String headerExpandedNewLines = ESCAPED_LINE_FEED_PATTERN
                    .matcher(header).replaceAll("\n");

            try (Reader headerReader = new StringReader(headerExpandedNewLines)) {
                loadHeader(headerReader);
            }
            catch (final IOException ex) {
                throw new IllegalArgumentException("unable to load header", ex);
            }
        }
    }

    /**
     * Load header to check against from a Reader into readerLines.
     *
     * @param headerReader delivers the header to check against
     * @throws IOException if
     */
    private void loadHeader(final Reader headerReader) throws IOException {
        try (LineNumberReader lnr = new LineNumberReader(headerReader)) {
            String line;
            do {
                line = lnr.readLine();
                if (line != null) {
                    readerLines.add(line);
                }
            } while (line != null);
            postProcessHeaderLines();
        }
    }

    @Override
    protected final void finishLocalSetup() throws CheckstyleException {
        if (headerFile != null) {
            loadHeaderFile();
        }
    }

    @Override
    public Set<String> getExternalResourceLocations() {
        final Set<String> result;

        if (headerFile == null) {
            result = Set.of();
        }
        else {
            result = Set.of(headerFile.toASCIIString());
        }

        return result;
    }

}
