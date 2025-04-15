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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks the header of a source file against multiple header files that contain a
 * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/regex/Pattern.html">
 * pattern</a> for each line of the source header.
 * </div>
 * <ul>
 * <li>
 * Property {@code fileExtensions} - Specify the file extensions of the files to process.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code headerFiles} - Specify a comma-separated list of files containing
 * the required headers.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code header.mismatch}
 * </li>
 * <li>
 * {@code header.missing}
 * </li>
 * </ul>
 *
 * @since 10.24.0
 */
@FileStatefulCheck
public class MultiFileRegexpHeaderCheck
        extends AbstractFileSetCheck implements ExternalResourceHolder {
    /**
     * Constant indicating that no header line mismatch was found.
     */
    public static final int VALID_LINE_HEADER_CHECKER = -1;

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
     * List of metadata objects for each configured header file,
     * containing patterns and line contents.
     */
    private final List<HeaderFileMetadata> headerFilesMetadata = new ArrayList<>();

    /**
     * Specify a comma-separated list of files containing the required headers.
     */
    @XdocsPropertyType(PropertyType.STRING)
    private String headerFiles;

    /**
     * Setter to specify a comma-separated list of files containing the required headers.
     *
     * @param headerFiles comma-separated list of header files
     * @throws IllegalArgumentException if headerFiles is null or empty
     * @since 10.24.0
     */
    public void setHeaderFiles(String headerFiles) {
        if (CommonUtil.isBlank(headerFiles)) {
            throw new IllegalArgumentException("headerFiles cannot be null or empty");
        }
        final String[] files = headerFiles.split(",");
        for (String headerFile : files) {
            headerFilesMetadata.add(HeaderFileMetadata.createFromFile(headerFile));
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
            final int mismatchLine = findFirstMismatch(fileText, headerFile);
            if (-1 == mismatchLine) {
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
        // Find the first mismatch across all header files
        int lineToLog = 1;
        String messageKey = MSG_HEADER_MISSING;
        String messageArg = null;
        boolean violationLogged = false;

        for (HeaderFileMetadata metadata : headerFilesMetadata) {
            if (!violationLogged) {
                if (metadata.getHeaderPatterns().size() > fileText.size()) {
                    // Header has more lines than the file
                    log(lineToLog, messageKey, messageArg);
                    violationLogged = true;
                }
                else {
                    final int mismatchLine = findFirstMismatch(fileText, metadata);
                    lineToLog = mismatchLine + 1;
                    messageKey = MSG_HEADER_MISMATCH;
                    final String lineContent = metadata.getLineContents().get(mismatchLine);
                    if (lineContent.isEmpty()) {
                        messageArg = EMPTY_LINE_PATTERN;
                    }
                    else {
                        messageArg = lineContent;
                    }
                    log(lineToLog, messageKey, messageArg);
                    violationLogged = true;
                }
            }
        }
    }

    /**
     * Finds the first line in the file text that does not match the corresponding header pattern.
     *
     * @param fileText the text of the file being checked
     * @param headerFileMetadata the metadata containing header patterns
     * @return the line number of the first mismatch,
     *         or {@link #VALID_LINE_HEADER_CHECKER} if valid
     */
    private static int findFirstMismatch(FileText fileText,
                                         HeaderFileMetadata headerFileMetadata) {
        final int fileSize = fileText.size();
        final List<Pattern> headerPatterns = headerFileMetadata.getHeaderPatterns();
        final int headerPatternSize = headerPatterns.size();
        boolean allPatternsMatch = true;

        // First pass: check matches
        for (int index = 0; index < headerPatternSize; index++) {
            final boolean matched;
            if (index < fileSize) {
                matched = headerPatterns.get(index).matcher(fileText.get(index)).matches();
            }
            else {
                matched = headerPatterns.get(index).pattern().isBlank();
            }
            allPatternsMatch = allPatternsMatch && matched;
        }

        // If not all patterns match, find the first mismatch
        int result = VALID_LINE_HEADER_CHECKER;
        if (!allPatternsMatch) {
            for (int index = 0; index < fileSize && index < headerPatternSize; index++) {
                if (!headerPatterns.get(index).matcher(fileText.get(index)).matches()) {
                    result = index;
                    break;
                }
            }
        }

        return result;
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
        final List<String> readerLines = new ArrayList<>();
        try (LineNumberReader lnr = new LineNumberReader(
                new InputStreamReader(
                        new BufferedInputStream(uri.toURL().openStream()),
                        StandardCharsets.UTF_8)
        )) {
            String line;
            do {
                line = lnr.readLine();
                if (line != null) {
                    readerLines.add(line);
                }
            } while (line != null);
        }
        catch (final IOException ex) {
            throw new IllegalArgumentException("unable to load header file " + headerFile, ex);
        }

        if (readerLines.isEmpty()) {
            throw new IllegalArgumentException("Header file is empty: " + headerFile);
        }
        return readerLines;
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
         * Initializes the metadata holder.
         *
         * @param headerFile URI of the header file
         * @param headerPatterns compiled regex patterns for header lines
         * @param lineContents raw lines from the header file
         */
        private HeaderFileMetadata(
                URI headerFile, List<Pattern> headerPatterns, List<String> lineContents
        ) {
            this.headerFile = headerFile;
            this.headerPatterns = headerPatterns;
            this.lineContents = lineContents;
        }

        /**
         * Creates a HeaderFileMetadata instance by reading and processing
         * the specified header file.
         *
         * @param headerFile path to the header file
         * @return HeaderFileMetadata instance
         * @throws IllegalArgumentException if the header file is invalid or cannot be read
         */
        public static HeaderFileMetadata createFromFile(String headerFile) {
            if (CommonUtil.isBlank(headerFile)) {
                throw new IllegalArgumentException("Header file is not set");
            }
            try {
                final URI uri = CommonUtil.getUriByFilename(headerFile);
                final List<String> readerLines = getLines(headerFile, uri);
                final List<Pattern> patterns = new ArrayList<>();
                for (String line : readerLines) {
                    final Pattern result;
                    if (line.isEmpty()) {
                        result = BLANK_LINE;
                    }
                    else {
                        result = Pattern.compile(validateRegex(line));
                    }
                    patterns.add(result);
                }
                return new HeaderFileMetadata(uri, patterns, readerLines);
            }
            catch (CheckstyleException ex) {
                throw new IllegalArgumentException(
                        "Error reading or corrupted header file: " + headerFile, ex);
            }
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
         * Ensures that the given input string is a valid regular expression.
         *
         * <p>This method validates that the input is a correctly formatted regex string
         * and will throw a PatternSyntaxException if it's invalid.
         *
         * @param input the string to be treated as a regex pattern
         * @return the validated regex pattern string
         * @throws IllegalArgumentException if the pattern is not a valid regex
         */
        private static String validateRegex(String input) {
            // Attempt to compile the pattern to check its validity
            // This will throw PatternSyntaxException if the pattern is invalid
            try {
                Pattern.compile(input);
                return input;
            }
            catch (final PatternSyntaxException ex) {
                throw new IllegalArgumentException("Invalid regex pattern: " + input, ex);
            }
        }
    }
}
