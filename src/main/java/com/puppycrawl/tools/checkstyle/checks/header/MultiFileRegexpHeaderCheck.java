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

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Checks that the header of a source file matches a specified header pattern.
 * The header can be specified as a list of strings or read from a file. Each line
 * of the header is treated as a regular expression against which each line of the
 * file header is checked.
 */
@FileStatefulCheck
public class MultiFileRegexpHeaderCheck extends AbstractFileSetCheck implements ExternalResourceHolder {
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_HEADER_MISSING = "header.missing";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_HEADER_MISMATCH = "header.mismatch";

    /**
     * Regex pattern for a blank line.
     **/
    private static final String EMPTY_LINE_PATTERN = "^$";

    /**
     * Compiled regex pattern for a blank line.
     **/
    private static final Pattern BLANK_LINE = Pattern.compile(EMPTY_LINE_PATTERN);

    /**
     * Constant indicating that no header line mismatch was found.
     */
    public static final int VALID_LINE_HEADER_CHECKER = -1;

    /**
     * List of metadata objects for each configured header file,
     * containing patterns and line contents.
     */
    private final List<HeaderFileMetadata> headerFilesMetadata = new ArrayList<>();

    /**
     * Sets the comma-separated list of header files to check against.
     *
     * @param headerFiles comma-separated list of header files
     * @throws IllegalArgumentException if headerFiles is null or empty
     */
    public void setHeaderFiles(String headerFiles) {
        if (CommonUtil.isBlank(headerFiles)) {
            throw new IllegalArgumentException("headerFiles cannot be null or empty");
        }
        String[] files = headerFiles.split(",");
        for (String headerFile : files) {
            headerFilesMetadata.add(HeaderFileMetadata.of(headerFile));
        }
    }

    @Override
    public Set<String> getExternalResourceLocations() {
        return headerFilesMetadata.stream()
                .map(HeaderFileMetadata::getHeaderFile)
                .map(URI::toASCIIString)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    protected void processFiltered(File file, FileText fileText) {
        boolean isValid = false;
        for (HeaderFileMetadata headerFile : headerFilesMetadata) {
            int mismatchLine = findFirstMismatch(fileText, headerFile);
            if (mismatchLine == -1) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            logFirstMismatch(fileText);
        }
    }

    /**
     * Logs the first line mismatch between the file text and the header patterns.
     *
     * @param fileText the text of the file being checked
     */
    private void logFirstMismatch(FileText fileText) {
        for (HeaderFileMetadata metadata : headerFilesMetadata) {
            // If the file header has fewer lines than patterns, log missing header
            if (metadata.getHeaderPatterns().size() > fileText.size()) {
                log(1, MSG_HEADER_MISSING);
                break;
            }

            int mismatchLine = findFirstMismatch(fileText, metadata);
            if (mismatchLine != VALID_LINE_HEADER_CHECKER) {
                String lineContent = metadata.getLineContents().get(mismatchLine);
                if (lineContent.isEmpty()) {
                    log(mismatchLine + 1, MSG_HEADER_MISMATCH, EMPTY_LINE_PATTERN);
                } else {
                    log(mismatchLine + 1, MSG_HEADER_MISMATCH, lineContent);
                }
                break;
            }
        }
    }

    /**
     * Finds the first line in the file text that does not match the corresponding header pattern.
     *
     * @param fileText the text of the file being checked
     * @param headerFileMetadata the metadata containing header patterns
     * @return the line number of the first mismatch, or {@link #VALID_LINE_HEADER_CHECKER} if valid
     */
    private static int findFirstMismatch(FileText fileText, HeaderFileMetadata headerFileMetadata) {
        int fileSize = fileText.size();
        List<Pattern> headerPatterns = headerFileMetadata.getHeaderPatterns();

        BitSet lineMatches = new BitSet(headerPatterns.size());
        boolean allEmpty = true;

        // First pass: check matches and build lineMatches
        for (int i = 0; i < headerPatterns.size(); i++) {
            if (i < fileSize) {
                if (headerPatterns.get(i).matcher(fileText.get(i)).matches()) {
                    lineMatches.set(i);
                }
            } else {
                if (headerPatterns.get(i).pattern().isBlank()) {
                    lineMatches.set(i);
                }
            }
            allEmpty = allEmpty && lineMatches.get(i);
        }

        // Only proceed to find mismatch if not all patterns match or are empty
        int result = VALID_LINE_HEADER_CHECKER;
        if (!allEmpty && lineMatches.cardinality() != headerPatterns.size()) {
            for (int i = 0; i < fileSize && i < headerPatterns.size(); i++) {
                if (!headerPatterns.get(i).matcher(fileText.get(i)).find()) {
                    result = i;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Metadata holder for a header file, storing its URI, compiled patterns, and line contents.
     */
    private static final class HeaderFileMetadata {
        /** URI of the header file. */
        private final URI headerFile;
        /** Compiled regex patterns for each line of the header. */
        private final List<Pattern> headerPatterns;
        /** Raw line contents of the header file. */
        private final List<String> lineContents;

        /**
         * Constructs a HeaderFileMetadata instance.
         *
         * @param headerFile URI of the header file
         * @param headerPatterns compiled regex patterns for header lines
         * @param lineContents raw lines from the header file
         */
        private HeaderFileMetadata(URI headerFile, List<Pattern> headerPatterns, List<String> lineContents) {
            this.headerFile = headerFile;
            this.headerPatterns = headerPatterns;
            this.lineContents = lineContents;
        }

        /**
         * Returns the URI of the header file.
         *
         * @return header file URI
         */
        public URI getHeaderFile() {
            return headerFile;
        }

        /**
         * Returns an unmodifiable list of compiled header patterns.
         *
         * @return header patterns
         */
        public List<Pattern> getHeaderPatterns() {
            return Collections.unmodifiableList(headerPatterns);
        }

        /**
         * Returns an unmodifiable list of raw header line contents.
         *
         * @return header lines
         */
        public List<String> getLineContents() {
            return Collections.unmodifiableList(lineContents);
        }

        /**
         * Creates a HeaderFileMetadata instance by reading and processing the specified header file.
         *
         * @param headerFile path to the header file
         * @return HeaderFileMetadata instance
         * @throws IllegalArgumentException if the header file is invalid or cannot be read
         */
        public static HeaderFileMetadata of(String headerFile) {
            if (CommonUtil.isBlank(headerFile)) {
                throw new IllegalArgumentException("Header file is not set");
            }
            try {
                URI uri = CommonUtil.getUriByFilename(headerFile);
                List<String> readerLines = getLines(headerFile, uri);
                List<Pattern> patterns = readerLines.stream()
                        .map(line -> {
                            if (line.isEmpty()) {
                                return BLANK_LINE;
                            }
                            return Pattern.compile(line);
                        })
                        .collect(Collectors.toList());
                return new HeaderFileMetadata(uri, patterns, readerLines);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error reading or corrupted header file: " + headerFile, e);
            }
        }
    }

    /**
     * Reads all lines from the specified header file URI.
     *
     * @param headerFile path to the header file (for error messages)
     * @param uri URI of the header file
     * @return list of lines read from the header file
     * @throws IllegalArgumentException if the file cannot be read or is empty
     */
    static List<String> getLines(String headerFile, URI uri) {
        List<String> readerLines = new ArrayList<>();
        try (LineNumberReader lnr = new LineNumberReader(
                new InputStreamReader(
                        new BufferedInputStream(uri.toURL().openStream()),
                        StandardCharsets.UTF_8)
        )) {
            while (lnr.readLine() != null) {
                String line = lnr.readLine();
                readerLines.add(line);
            }
        } catch (final IOException ex) {
            throw new IllegalArgumentException("unable to load header file " + headerFile, ex);
        }

        if (readerLines.isEmpty()) {
            throw new IllegalArgumentException("Header file is empty: " + headerFile);
        }
        return readerLines;
    }
}