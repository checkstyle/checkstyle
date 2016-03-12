////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.UnsupportedCharsetException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.io.Closeables;

/**
 * Represents the text contents of a file of arbitrary plain text type.
 * <p>
 * This class will be passed to instances of class FileSetCheck by
 * Checker. It implements a string list to ensure backwards
 * compatibility, but can be extended in the future to allow more
 * flexible, more powerful or more efficient handling of certain
 * situations.
 * </p>
 *
 * @author Martin von Gagern
 */
public final class FileText extends AbstractList<String> {

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
     * The full text contents of the file.
     */
    private final String fullText;

    /**
     * The lines of the file, without terminators.
     */
    private final String[] lines;

    /**
     * The first position of each line within the full text.
     */
    private int[] lineBreaks;

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
        final ArrayList<String> textLines = new ArrayList<>();
        final BufferedReader reader =
            new BufferedReader(new StringReader(fullText));
        while (true) {
            final String line = reader.readLine();
            if (line == null) {
                break;
            }
            textLines.add(line);
        }
        lines = textLines.toArray(new String[textLines.size()]);
    }

    /**
     * Copy constructor.
     * @param fileText to make copy of
     */
    public FileText(FileText fileText) {
        file = fileText.file;
        charset = fileText.charset;
        fullText = fileText.fullText;
        lines = fileText.lines.clone();
        if (fileText.lineBreaks == null) {
            lineBreaks = null;
        }
        else {
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
    private FileText(File file, List<String> lines) {
        final StringBuilder buf = new StringBuilder();
        for (final String line : lines) {
            buf.append(line).append('\n');
        }
        buf.trimToSize();

        this.file = file;
        charset = null;
        fullText = buf.toString();
        this.lines = lines.toArray(new String[lines.size()]);
    }

    /**
     * Reads file using specific decoder and returns all its content as a String.
     * @param inputFile File to read
     * @param decoder Charset decoder
     * @return File's text
     * @throws IOException Unable to open or read the file
     */
    private static String readFile(final File inputFile, final CharsetDecoder decoder)
            throws IOException {
        if (!inputFile.exists()) {
            throw new FileNotFoundException(inputFile.getPath() + " (No such file or directory)");
        }
        final StringBuilder buf = new StringBuilder();
        final FileInputStream stream = new FileInputStream(inputFile);
        final Reader reader = new InputStreamReader(stream, decoder);
        try {
            final char[] chars = new char[READ_BUFFER_SIZE];
            while (true) {
                final int len = reader.read(chars);
                if (len < 0) {
                    break;
                }
                buf.append(chars, 0, len);
            }
        }
        finally {
            Closeables.closeQuietly(reader);
        }
        return buf.toString();
    }

    /**
     * Compatibility conversion.
     *
     * <p>This method can be used to convert the arguments passed to
     * {@link FileSetCheck#process(File,List)} to a FileText
     * object. If the list of lines already is a FileText, it is
     * returned as is. Otherwise, a new FileText is constructed by
     * joining the lines using line feed characters.
     *
     * @param file the name of the file
     * @param lines the lines of the text, without terminators
     * @return an object representing the denoted text file
     */
    public static FileText fromLines(File file, List<String> lines) {
        if (lines instanceof FileText) {
            return (FileText) lines;
        }
        else {
            return new FileText(file, lines);
        }
    }

    /**
     * Get the name of the file.
     * @return an object containing the name of the file
     */
    public File getFile() {
        return file;
    }

    /**
     * Get the character set which was used to read the file.
     * Will be {@code null} for a file reconstructed from its lines.
     * @return the charset used when the file was read
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * Retrieve the full text of the file.
     * @return the full text of the file
     */
    public CharSequence getFullText() {
        return fullText;
    }

    /**
     * Returns an array of all lines.
     * {@code text.toLinesArray()} is equivalent to
     * {@code text.toArray(new String[text.size()])}.
     * @return an array of all lines of the text
     */
    public String[] toLinesArray() {
        return lines.clone();
    }

    /**
     * Find positions of line breaks in the full text.
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
     * Determine line and column numbers in full text.
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
     * Retrieves a line of the text by its number.
     * The returned line will not contain a trailing terminator.
     * @param lineNo the number of the line to get, starting at zero
     * @return the line with the given number
     */
    @Override
    public String get(final int lineNo) {
        return lines[lineNo];
    }

    /**
     * Counts the lines of the text.
     * @return the number of lines in the text
     */
    @Override
    public int size() {
        return lines.length;
    }

}
