///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
 * <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/regex/Pattern.html">
 * pattern</a> for each line of the source header.
 * </div>
 *
 * @since 10.24.0
 */
@FileStatefulCheck
public class MultiFileRegexpHeaderCheck
        extends AbstractFileSetCheck implements ExternalResourceHolder {
    /**
     * Constant indicating that no header line mismatch was found.
     */
    public static final int MISMATCH_CODE = -1;

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_HEADER_MISSING = "multi.file.regexp.header.missing";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_HEADER_MISMATCH = "multi.file.regexp.header.mismatch";

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
     * If a file's header matches none, the violation references
     * the first file in this list. Users can order files to set
     * a preferred header for such reporting.
     */
    @XdocsPropertyType(PropertyType.STRING)
    private String headerFiles;

    /**
     * Setter to specify a comma-separated list of files containing the required headers.
     * If a file's header matches none, the violation references
     * the first file in this list. Users can order files to set
     * a preferred header for such reporting.
     *
     * @param headerFiles comma-separated list of header files
     * @throws IllegalArgumentException if headerFiles is null or empty
     * @since 10.24.0
     */
    public void setHeaderFiles(String... headerFiles) {
        final String[] files;
        if (headerFiles == null) {
            files = CommonUtil.EMPTY_STRING_ARRAY;
        }
        else {
            files = headerFiles.clone();
        }

        headerFilesMetadata.clear();

        for (final String headerFile : files) {
            headerFilesMetadata.add(HeaderFileMetadata.createFromFile(headerFile));
        }
    }

    /**
     * Returns a comma-separated string of all configured header file paths.
     *
     * @return A comma-separated string of all configured header file paths,
     *         or an empty string if no header files are configured or none have valid paths.
     */
    public String getConfiguredHeaderPaths() {
        return headerFilesMetadata.stream()
                .map(HeaderFileMetadata::headerFilePath)
                .collect(Collectors.joining(", "));
    }

    @Override
    public Set<String> getExternalResourceLocations() {
        return headerFilesMetadata.stream()
                .map(HeaderFileMetadata::headerFileUri)
                .map(URI::toASCIIString)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    protected void processFiltered(File file, FileText fileText) {
        if (!headerFilesMetadata.isEmpty()) {
            final List<MatchResult> matchResult = headerFilesMetadata.stream()
                    .map(headerFile -> matchHeader(fileText, headerFile))
                    .toList();

            if (matchResult.stream().noneMatch(MatchResult::isMatching)) {
                final MatchResult mismatch = matchResult.getFirst();
                final String allConfiguredHeaderPaths = getConfiguredHeaderPaths();
                log(mismatch.lineNumber(), mismatch.messageKey(),
                        mismatch.messageArg(), allConfiguredHeaderPaths);
            }
        }
    }

    /**
     * Analyzes if the file text matches the header file patterns and generates a detailed result.
     *
     * @param fileText the text of the file being checked
     * @param headerFile the header file metadata to check against
     * @return a MatchResult containing the result of the analysis
     */
    private static MatchResult matchHeader(FileText fileText, HeaderFileMetadata headerFile) {
        final int fileSize = fileText.size();
        final List<Pattern> headerPatterns = headerFile.headerPatterns();
        final int headerPatternSize = headerPatterns.size();

        int mismatchLine = MISMATCH_CODE;
        int index;
        for (index = 0; index < headerPatternSize && index < fileSize; index++) {
            if (!headerPatterns.get(index).matcher(fileText.get(index)).find()) {
                mismatchLine = index;
                break;
            }
        }
        if (index < headerPatternSize) {
            mismatchLine = index;
        }

        final MatchResult matchResult;
        if (mismatchLine == MISMATCH_CODE) {
            matchResult = MatchResult.matching();
        }
        else {
            matchResult = createMismatchResult(headerFile, fileText, mismatchLine);
        }
        return matchResult;
    }

    /**
     * Creates a MatchResult for a mismatch case.
     *
     * @param headerFile the header file metadata
     * @param fileText the text of the file being checked
     * @param mismatchLine the line number of the mismatch (0-based)
     * @return a MatchResult representing the mismatch
     */
    private static MatchResult createMismatchResult(HeaderFileMetadata headerFile,
                                                    FileText fileText, int mismatchLine) {
        final String messageKey;
        final int lineToLog;
        final String messageArg;

        if (headerFile.headerPatterns().size() > fileText.size()) {
            messageKey = MSG_HEADER_MISSING;
            lineToLog = 1;
            messageArg = headerFile.headerFilePath();
        }
        else {
            messageKey = MSG_HEADER_MISMATCH;
            lineToLog = mismatchLine + 1;
            final String lineContent = headerFile.lineContents().get(mismatchLine);
            if (lineContent.isEmpty()) {
                messageArg = EMPTY_LINE_PATTERN;
            }
            else {
                messageArg = lineContent;
            }
        }
        return MatchResult.mismatch(lineToLog, messageKey, messageArg);
    }

    /**
     * Reads all lines from the specified header file URI.
     *
     * @param headerFile path to the header file (for error messages)
     * @param uri URI of the header file
     * @return list of lines read from the header file
     * @throws IllegalArgumentException if the file cannot be read or is empty
     */
    public static List<String> getLines(String headerFile, URI uri) {
        final List<String> readerLines = new ArrayList<>();
        try (LineNumberReader lineReader = new LineNumberReader(
                new InputStreamReader(
                        new BufferedInputStream(uri.toURL().openStream()),
                        StandardCharsets.UTF_8)
        )) {
            String line;
            do {
                line = lineReader.readLine();
                if (line != null) {
                    readerLines.add(line);
                }
            } while (line != null);
        }
        catch (final IOException exc) {
            throw new IllegalArgumentException("unable to load header file " + headerFile, exc);
        }

        if (readerLines.isEmpty()) {
            throw new IllegalArgumentException("Header file is empty: " + headerFile);
        }
        return readerLines;
    }

    /**
     * Metadata holder for a header file, storing its URI, compiled patterns, and line contents.
     *
     * @param headerFileUri URI of the header file
     * @param headerFilePath original path string of the header file
     * @param headerPatterns compiled regex patterns for header lines
     * @param lineContents raw lines from the header file
     */
    private record HeaderFileMetadata(
            URI headerFileUri,
            String headerFilePath,
            List<Pattern> headerPatterns,
            List<String> lineContents) {

        /**
         * Creates a HeaderFileMetadata instance by reading and processing
         * the specified header file.
         *
         * @param headerPath path to the header file
         * @return HeaderFileMetadata instance
         * @throws IllegalArgumentException if the header file is invalid or cannot be read
         */
        /* package */ static HeaderFileMetadata createFromFile(String headerPath) {
            if (CommonUtil.isBlank(headerPath)) {
                throw new IllegalArgumentException("Header file is not set");
            }
            try {
                final URI uri = CommonUtil.getUriByFilename(headerPath);
                final List<String> readerLines = getLines(headerPath, uri);
                final List<Pattern> patterns = readerLines.stream()
                        .map(HeaderFileMetadata::createPatternFromLine)
                        .toList();
                return new HeaderFileMetadata(uri, headerPath, patterns, readerLines);
            }
            catch (CheckstyleException exc) {
                throw new IllegalArgumentException(
                        "Error reading or corrupted header file: " + headerPath, exc);
            }
        }

        /**
         * Creates a Pattern object from a line of text.
         *
         * @param line the line to create a pattern from
         * @return the compiled Pattern
         */
        private static Pattern createPatternFromLine(String line) {
            final Pattern result;
            if (line.isEmpty()) {
                result = BLANK_LINE;
            }
            else {
                result = Pattern.compile(validateRegex(line));
            }
            return result;
        }

        /**
         * Returns an unmodifiable list of compiled header patterns.
         *
         * @return header patterns
         */
        @Override
        public List<Pattern> headerPatterns() {
            return List.copyOf(headerPatterns);
        }

        /**
         * Returns an unmodifiable list of raw header line contents.
         *
         * @return header lines
         */
        @Override
        public List<String> lineContents() {
            return List.copyOf(lineContents);
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
            try {
                Pattern.compile(input);
                return input;
            }
            catch (final PatternSyntaxException exc) {
                throw new IllegalArgumentException("Invalid regex pattern: " + input, exc);
            }
        }
    }

    /**
     * Represents the result of a header match check, containing information about any mismatch.
     *
     * @param isMatching whether the header matched
     * @param lineNumber line number of mismatch (1-based)
     * @param messageKey message key for violation
     * @param messageArg message argument
     */
    private record MatchResult(
            boolean isMatching,
            int lineNumber,
            String messageKey,
            String messageArg) {

        /**
         * Creates a matching result.
         *
         * @return a matching result
         */
        /* package */ static MatchResult matching() {
            return new MatchResult(true, 0, null, null);
        }

        /**
         * Creates a mismatch result.
         *
         * @param lineNumber the line number where mismatch occurred (1-based)
         * @param messageKey the message key for the violation
         * @param messageArg the argument for the message
         * @return a mismatch result
         */
        /* package */ static MatchResult mismatch(int lineNumber, String messageKey,
                                           String messageArg) {
            return new MatchResult(false, lineNumber, messageKey, messageArg);
        }
    }
}
