////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.UnsupportedCharsetException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the text contents of a file of arbitrary plain text type.
 * <p>
 * This class will be passed to instances of class FileSetCheck by
 * Checker. It implements a string list to ensure backwards
 * compatibility, but can be extended in the future to allow more
 * flexible, more powerful or more efficient handling of certain
 * situations.
 *
 * @author Martin von Gagern
 */
public final class FileText extends AbstractList<String>
{

    /**
     * The number of characters to read in one go.
     */
    private static final int READ_BUFFER_SIZE = 1024;

    /**
     * Regular expression pattern matching all line terminators.
     */
    private static final Pattern LINE_TERMINATOR =
        Utils.getPattern("\\n|\\r\\n?");

    // For now, we always keep both full text and lines array.
    // In the long run, however, the one passed at initialization might be
    // enough, while the other could be lazily created when requested.
    // This would save memory but cost CPU cycles.

    /**
     * The name of the file.
     * <code>null</code> if no file name is available for whatever reason.
     */
    private final File mFile;

    /**
     * The charset used to read the file.
     * <code>null</code> if the file was reconstructed from a list of lines.
     */
    private final Charset mCharset;

    /**
     * The full text contents of the file.
     */
    private final CharSequence mFullText;

    /**
     * The lines of the file, without terminators.
     */
    private final String[] mLines;

    /**
     * The first position of each line within the full text.
     */
    private int[] mLineBreaks;

    /**
     * Creates a new file text representation.
     *
     * The file will be read using the specified encoding, replacing
     * malformed input and unmappable characters with the default
     * replacement character.
     *
     * @param aFile the name of the file
     * @param aCharsetName the encoding to use when reading the file
     * @throws NullPointerException if the text is null
     * @throws IOException if the file could not be read
     */
    public FileText(File aFile, String aCharsetName) throws IOException
    {
        mFile = aFile;

        // We use our own decoder, to be sure we have complete control
        // about replacements.
        final CharsetDecoder decoder;
        try {
            mCharset = Charset.forName(aCharsetName);
            decoder = mCharset.newDecoder();
            decoder.onMalformedInput(CodingErrorAction.REPLACE);
            decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
        }
        catch (final UnsupportedCharsetException ex) {
            final String message = "Unsuppored charset: " + aCharsetName;
            final UnsupportedEncodingException ex2;
            ex2 = new UnsupportedEncodingException(message);
            ex2.initCause(ex);
            throw ex2;
        }

        final char[] chars = new char[READ_BUFFER_SIZE];
        final StringBuilder buf = new StringBuilder();
        final FileInputStream stream = new FileInputStream(aFile);
        final Reader reader = new InputStreamReader(stream, decoder);
        try {
            while (true) {
                final int len = reader.read(chars);
                if (len < 0) {
                    break;
                }
                buf.append(chars, 0, len);
            }
        }
        finally {
            Utils.closeQuietly(reader);
        }
        // buf.trimToSize(); // could be used instead of toString().
        mFullText = buf.toString();

        final String[] lines = LINE_TERMINATOR.split(mFullText, -1);
        if (lines.length > 0 && lines[lines.length - 1].length() == 0) {
            // drop empty line after last newline
            mLines = new String[lines.length - 1];
            System.arraycopy(lines, 0, mLines, 0, lines.length - 1);
        }
        else {
            // no newline at end, so we keep the last line as is
            mLines = lines;
        }
    }

    /**
     * Compatibility constructor.
     *
     * This constructor reconstructs the text of the file by joining
     * lines with linefeed characters. This process does not restore
     * the original line terminators and should therefore be avoided.
     *
     * @param aFile the name of the file
     * @param aLines the lines of the text, without terminators
     * @throws NullPointerException if the lines array is null
     */
    private FileText(File aFile, List<String> aLines)
    {
        final StringBuilder buf = new StringBuilder();
        for (final String line : aLines) {
            buf.append(line).append('\n');
        }
        buf.trimToSize();

        mFile = aFile;
        mCharset = null;
        mFullText = buf;
        mLines = aLines.toArray(new String[aLines.size()]);
    }

    /**
     * Compatibility conversion.
     *
     * This method can be used to convert the arguments passed to
     * {@link FileSetCheck#process(File,List)} to a FileText
     * object. If the list of lines already is a FileText, it is
     * returned as is. Otherwise, a new FileText is constructed by
     * joining the lines using line feed characters.
     *
     * @param aFile the name of the file
     * @param aLines the lines of the text, without terminators
     * @return an object representing the denoted text file
     */
    public static FileText fromLines(File aFile, List<String> aLines)
    {
        return (aLines instanceof FileText)
            ? (FileText) aLines
            : new FileText(aFile, aLines);
    }

    /**
     * Get the name of the file.
     * @return an object containing the name of the file
     */
    public File getFile()
    {
        return mFile;
    }

    /**
     * Get the character set which was used to read the file.
     * Will be <code>null</code> for a file reconstructed from its lines.
     * @return the charset used when the file was read
     */
    public Charset getCharset()
    {
        return mCharset;
    }

    /**
     * Get the binary contents of the file.
     * The returned object must not be modified.
     * @return a buffer containing the bytes making up the file
     * @throws IOException if the bytes could not be read from the file
     */
    public ByteBuffer getBytes() throws IOException
    {
        // We might decide to cache file bytes in the future.
        if (mFile == null) {
            return null;
        }
        if (mFile.length() > Integer.MAX_VALUE) {
            throw new IOException("File too large.");
        }
        byte[] bytes = new byte[(int) mFile.length() + 1];
        final FileInputStream stream = new FileInputStream(mFile);
        try {
            int fill = 0;
            while (true) {
                if (fill >= bytes.length) {
                    // shouldn't happen, but it might nevertheless
                    final byte[] newBytes = new byte[bytes.length * 2 + 1];
                    System.arraycopy(bytes, 0, newBytes, 0, fill);
                    bytes = newBytes;
                }
                final int len = stream.read(bytes, fill,
                                            bytes.length - fill);
                if (len == -1) {
                    break;
                }
                fill += len;
            }
            return ByteBuffer.wrap(bytes, 0, fill).asReadOnlyBuffer();
        }
        finally {
            Utils.closeQuietly(stream);
        }
    }

    /**
     * Retrieve the full text of the file.
     * @return the full text of the file
     */
    public CharSequence getFullText()
    {
        return mFullText;
    }

    /**
     * Returns an array of all lines.
     * {@code text.toLinesArray()} is equivalent to
     * {@code text.toArray(new String[text.size()])}.
     * @return an array of all lines of the text
     */
    public String[] toLinesArray()
    {
        return mLines.clone();
    }

    /**
     * Find positions of line breaks in the full text.
     * @return an array giving the first positions of each line.
     */
    private int[] lineBreaks()
    {
        if (mLineBreaks == null) {
            final int[] lineBreaks = new int[size() + 1];
            lineBreaks[0] = 0;
            int lineNo = 1;
            final Matcher matcher = LINE_TERMINATOR.matcher(mFullText);
            while (matcher.find()) {
                lineBreaks[lineNo++] = matcher.end();
            }
            if (lineNo < lineBreaks.length) {
                lineBreaks[lineNo++] = mFullText.length();
            }
            if (lineNo != lineBreaks.length) {
                throw new ConcurrentModificationException("Text changed.");
            }
            mLineBreaks = lineBreaks;
        }
        return mLineBreaks;
    }

    /**
     * Determine line and column numbers in full text.
     * @param aPos the character position in the full text
     * @return the line and column numbers of this character
     */
    public LineColumn lineColumn(int aPos)
    {
        final int[] lineBreaks = lineBreaks();
        int lineNo = Arrays.binarySearch(lineBreaks, aPos);
        if (lineNo < 0) {
            // we have: lineNo = -(insertion point) - 1
            // we want: lineNo =  (insertion point) - 1
            lineNo = -lineNo - 2;
        }
        final int startOfLine = lineBreaks[lineNo];
        final int columnNo = aPos - startOfLine;
        // now we have lineNo and columnNo, both starting at zero.
        return new LineColumn(lineNo + 1, columnNo);
    }

    /**
     * Retrieves a line of the text by its number.
     * The returned line will not contain a trailing terminator.
     * @param aLineNo the number of the line to get, starting at zero
     * @return the line with the given number
     */
    @Override
    public String get(final int aLineNo)
    {
        return mLines[aLineNo];
    }

    /**
     * Counts the lines of the text.
     * @return the number of lines in the text
     */
    @Override
    public int size()
    {
        return mLines.length;
    }

}
