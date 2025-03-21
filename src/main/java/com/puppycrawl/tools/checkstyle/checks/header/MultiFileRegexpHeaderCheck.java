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
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public static final int VALID_LINE_HEADER_CHECKER = -1;

    private final List<HeaderFileMetadata> headerFilesMetadata = new ArrayList<>();

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
        if (headerFilesMetadata.isEmpty()) {
            return Set.of();
        }
        return headerFilesMetadata.stream()
                .map(HeaderFileMetadata::getHeaderFile)
                .map(URI::toString)
                .collect(Collectors.toCollection(LinkedHashSet::new));
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

    private void logFirstMismatch(FileText fileText) {
        for (HeaderFileMetadata metadata : headerFilesMetadata) {
            // If the file header has less than patterns, then return the first line that does not match
            if (metadata.getHeaderPatterns().size() > fileText.size()) {
                log(1, MSG_HEADER_MISSING);
                return;
            }
            int mismatchLine = findFirstMismatch(fileText, metadata);
            if (mismatchLine != VALID_LINE_HEADER_CHECKER) {
                if (mismatchLine >= metadata.getLineContents().size()) {
                    log(mismatchLine + 1, MSG_HEADER_MISMATCH, metadata.getHeaderFile());
                    return;
                }
                String lineContent = metadata.getLineContents().get(mismatchLine);
                if (lineContent.isEmpty()) {
                    log(mismatchLine + 1, MSG_HEADER_MISMATCH, EMPTY_LINE_PATTERN);
                    return;
                }
                log(mismatchLine + 1, MSG_HEADER_MISMATCH, metadata.getLineContents().get(mismatchLine));
                return;
            }
        }
    }

    private int findFirstMismatch(FileText fileText, HeaderFileMetadata headerFileMetadata) {
        int fileSize = fileText.size();
        List<Pattern> headerPatterns = headerFileMetadata.getHeaderPatterns();

        BitSet lineMatches = new BitSet(headerPatterns.size());
        boolean allEmpty = true;

        for (int i = 0; i < headerPatterns.size(); i++) {
            if (i < fileSize) {
                if (headerPatterns.get(i).matcher(fileText.get(i)).matches()) {
                    lineMatches.set(i);
                }
            } else {
                if (headerPatterns.get(i).pattern().isEmpty()) {
                    lineMatches.set(i);
                }
            }
            allEmpty &= lineMatches.get(i);
        }

        if (allEmpty || lineMatches.cardinality() == headerPatterns.size()) {
            return VALID_LINE_HEADER_CHECKER;
        }

        for (int i = 0; i < fileSize; i++) {
            if (i < headerPatterns.size() && !headerPatterns.get(i).matcher(fileText.get(i)).find()) {
                return i;
            }
        }

        return VALID_LINE_HEADER_CHECKER;
    }


    // Build a temporary metadata object to store the header file and its patterns along with the line contents
    // This will reduce the I/O operations to read the header file
    private static final class HeaderFileMetadata {
        private final URI headerFile;
        private final List<Pattern> headerPatterns;

        private final List<String> lineContents;

        private HeaderFileMetadata(URI headerFile, List<Pattern> headerPatterns, List<String> lineContents) {
            this.headerFile = headerFile;
            this.headerPatterns = headerPatterns;
            this.lineContents = lineContents;
        }

        public URI getHeaderFile() {
            return headerFile;
        }

        public List<Pattern> getHeaderPatterns() {
            return Collections.unmodifiableList(headerPatterns);
        }

        public List<String> getLineContents() {
            return Collections.unmodifiableList(lineContents);
        }

        public static HeaderFileMetadata of(String headerFile) {
            if (CommonUtil.isBlank(headerFile)) {
                throw new IllegalArgumentException("Header file is not set");
            }
            try {
                URI uri = CommonUtil.getUriByFilename(headerFile);
                List<String> readerLines = getLines(headerFile, uri);
                List<Pattern> patterns = readerLines.stream()
                        .map(line -> line.isEmpty() ? BLANK_LINE : Pattern.compile(line))
                        .collect(Collectors.toList());
                return new HeaderFileMetadata(uri, patterns, readerLines);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error reading or corrupted header file: " + headerFile, e);
            }
        }
    }

    private static List<String> getLines(String headerFile, URI uri) {
        List<String> readerLines = new ArrayList<>();
        try (Reader headerReader = new InputStreamReader(new BufferedInputStream(uri.toURL().openStream()),
                StandardCharsets.UTF_8)) {
            try (LineNumberReader lnr = new LineNumberReader(headerReader)) {
                String line;
                do {
                    line = lnr.readLine();
                    if (line != null) {
                        readerLines.add(line);
                    }
                } while (line != null);
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