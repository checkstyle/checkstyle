///
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
///

package com.puppycrawl.tools.checkstyle.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Represents the text contents of a file of arbitrary plain text type.
 *
 * <p>
 * This class will be passed to instances of class FileSetCheck by
 * Checker.
 * </p>
 *
 */
public final class FileText {

    /**
     * The number of characters to read in one go.
     */
    private static final int READ_BUFFER_SIZE = 1024;

    /**
     * Regular expression pattern matching all line terminators.
     */
    private static final Pattern LINE_TERMINATOR = Pattern.compile("\\n|\\r\\n?");

    // For now, we always keep both full text and lines array.
    // In the long run, however, the one passed at initialization might be
    // enough, while the other could be lazily created when requested.
    // This would save memory but cost CPU cycles.

    /**
     * The name of the file.
     * {@code null} if no file name is available for whatever reason.
     */
    private final File file;

    /**
     * The charset used to read the file.
     * {@code null} if the file was reconstructed from a list of lines.
     */
    private final Charset charset;

    /**
     * The lines of the file, without terminators.
     */
    private final String[] lines;

    /**
     * The full text contents of the file.
     *
     * @noinspection FieldMayBeFinal
     * @noinspectionreason FieldMayBeFinal - field is not final to ease reaching full test coverage.
     */
    private String fullText;

    /**
     * The first position of each line within the full text.
     */
    private int[] lineBreaks;

    /**
     * Copy constructor.
     *
     * @param fileText to make copy of
     */
    public FileText(FileText fileText) {
        file = fileText.file;
        charset = fileText.charset;
        fullText = fileText.fullText;
        lines = fileText.lines.clone();
        if (fileText.lineBreaks != null) {
            lineBreaks = fileText.lineBreaks.clone();
        }
    }

    /**
     * Compatibility constructor.
     *
     * <p>This constructor reconstructs the text of the file by joining
     * lines with linefeed characters. This process does not restore
     * the original line terminators and should therefore be avoided.
     *
     * @param file the name of the file
     * @param lines the lines of the text, without terminators
     * @throws NullPointerException if the lines array is null
     */
    public FileText(File file, List<String> lines) {
        final StringBuilder buf = new StringBuilder(1024);
        for (final String line : lines) {
            buf.append(line).append('\n');
        }

        this.file = file;
        charset = null;
        fullText = buf.toString();
        this.lines = lines.toArray(CommonUtil.EMPTY_STRING_ARRAY);
    }

    /**
     * Creates a new file text representation.
     *
     * <p>The file will be read using the specified encoding, replacing
     * malformed input and unmappable characters with the default
     * replacement character.
     *
     * @param file the name of the file
     * @param charsetName the encoding to use when reading the file
     * @throws NullPointerException if the text is null
     * @throws IllegalStateException if the charset is not supported.
     * @throws IOException if the file could not be read
     */
    public FileText(File file, String charsetName) throws IOException {
        this.file = file;

        // We use our own decoder, to be sure we have complete control
        // about replacements.
        final CharsetDecoder decoder;
        try {
            charset = Charset.forName(charsetName);
            decoder = charset.newDecoder();
            decoder.onMalformedInput(CodingErrorAction.REPLACE);
            decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
        }
        catch (final UnsupportedCharsetException ex) {
            final String message = "Unsupported charset: " + charsetName;
            throw new IllegalStateException(message, ex);
        }

        fullText = readFile(file, decoder);

        // Use the BufferedReader to break down the lines as this
        // is about 30% faster than using the
        // LINE_TERMINATOR.split(fullText, -1) method
        try (BufferedReader reader = new BufferedReader(new StringReader(fullText))) {
            final ArrayList<String> textLines = new ArrayList<>();
            while (true) {
                final String line = reader.readLine();
                if (line == null) {
                    break;
                }
                textLines.add(line);
            }
            lines = textLines.toArray(CommonUtil.EMPTY_STRING_ARRAY);
        }
    }

    /**
     * Reads file using specific decoder and returns all its content as a String.
     *
     * @param inputFile File to read
     * @param decoder Charset decoder
     * @return File's text
     * @throws IOException Unable to open or read the file
     * @throws FileNotFoundException when inputFile does not exist
     */
    private static String readFile(final File inputFile, final CharsetDecoder decoder)
            throws IOException {
        if (!inputFile.exists()) {
            throw new FileNotFoundException(inputFile.getPath() + " (No such file or directory)");
        }
        final StringBuilder buf = new StringBuilder(1024);
        final InputStream stream = Files.newInputStream(inputFile.toPath());
        try (Reader reader = new InputStreamReader(stream, decoder)) {
            final char[] chars = new char[READ_BUFFER_SIZE];
            while (true) {
                final int len = reader.read(chars);
                if (len == -1) {
                    break;
                }
                buf.append(chars, 0, len);
            }
        }
        return buf.toString();
    }

    /**
     * Retrieves a line of the text by its number.
     * The returned line will not contain a trailing terminator.
     *
     * @param lineNo the number of the line to get, starting at zero
     * @return the line with the given number
     */
    public String get(final int lineNo) {
        return lines[lineNo];
    }

    /**
     * Get the name of the file.
     *
     * @return an object containing the name of the file
     */
    public File getFile() {
        return file;
    }

    /**
     * Get the character set which was used to read the file.
     * Will be {@code null} for a file reconstructed from its lines.
     *
     * @return the charset used when the file was read
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * Retrieve the full text of the file.
     *
     * @return the full text of the file
     */
    public CharSequence getFullText() {
        return fullText;
    }

    /**
     * Returns an array of all lines.
     * {@code text.toLinesArray()} is equivalent to
     * {@code text.toArray(new String[text.size()])}.
     *
     * @return an array of all lines of the text
     */
    public String[] toLinesArray() {
        return lines.clone();
    }

    /**
     * Determine line and column numbers in full text.
     *
     * @param pos the character position in the full text
     * @return the line and column numbers of this character
     */
    public LineColumn lineColumn(int pos) {
        final int[] lineBreakPositions = findLineBreaks();
        int lineNo = Arrays.binarySearch(lineBreakPositions, pos);
        if (lineNo < 0) {
            // we have: lineNo = -(insertion point) - 1
            // we want: lineNo =  (insertion point) - 1
            lineNo = -lineNo - 2;
        }
        final int startOfLine = lineBreakPositions[lineNo];
        final int columnNo = pos - startOfLine;
        // now we have lineNo and columnNo, both starting at zero.
        return new LineColumn(lineNo + 1, columnNo);
    }

    /**
     * Find positions of line breaks in the full text.
     *
     * @return an array giving the first positions of each line.
     */
    private int[] findLineBreaks() {
        if (lineBreaks == null) {
            final int[] lineBreakPositions = new int[size() + 1];
            lineBreakPositions[0] = 0;
            int lineNo = 1;
            final Matcher matcher = LINE_TERMINATOR.matcher(fullText);
            while (matcher.find()) {
                lineBreakPositions[lineNo] = matcher.end();
                lineNo++;
            }
            if (lineNo < lineBreakPositions.length) {
                lineBreakPositions[lineNo] = fullText.length();
            }
            lineBreaks = lineBreakPositions;
        }
        return lineBreaks;
    }

    /**
     * Counts the lines of the text.
     *
     * @return the number of lines in the text
     */
    public int size() {
        return lines.length;
    }

}
