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
package com.puppycrawl.tools.checkstyle.checks.duplicates;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Multimap;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Performs a line-by-line comparison of all code lines and reports
 * duplicate code if a sequence of lines differs only in
 * indentation.  All import statements in Java code are ignored, any
 * other line - including javadoc, whitespace lines between methods,
 * etc. - is considered (which is why the check is called
 * <em>strict</em>).
 *
 * @author Lars K&uuml;hne
 */
public final class StrictDuplicateCodeCheck extends AbstractFileSetCheck
{
    /**
     * A prime that is used o calculate checksums of lines and blocks of lines.
     * Important that it's larger than the length of most lines to avoid hash
     * collisions.
     *
     * For a list of primes see
     * http://www.utm.edu/research/primes/lists/small/1000.txt
     */
    private static final int BIG_PRIME = 317;

    /**
     * Converts each consecutive block of {@link #mMin} lines of the
     * original source lines to a checksum that is checked against
     * to find duplicates.
     */
    private interface ChecksumGenerator
    {
        /**
         * Convert each block of the original source lines
         * to a checksum that is checked against to find duplicates
         *
         * @param aOriginalLines the original lines as they appear
         * in the source file
         *
         * @return an array of (aOriginalLines.length - mMin + 1) checksums
         */
        int[] convertLines(String[] aOriginalLines);
    }


    /**
     * Calculates checksums for text files.
     */
    private class TextfileChecksumGenerator implements ChecksumGenerator
    {
        /** {@inheritDoc} */
        public int[] convertLines(String[] aOriginalLines)
        {
            final int lineCount = aOriginalLines.length;
            final long[] checkSums = new long[lineCount];
            for (int i = 0; i < lineCount; i++) {
                final String line = aOriginalLines[i];
                checkSums[i] = calcChecksum(line);
            }
            final int retLen = Math.max(0, lineCount - mMin + 1);
            final int[] ret = new int[retLen];

            for (int i = 0; i < retLen; i++) {
                int blockChecksum = 0;
                boolean onlyEmptyLines = true;
                for (int j = 0; j < mMin; j++) {
                    if (aOriginalLines[i + j].length() > 0) {
                        onlyEmptyLines = false;
                    }
                    final long checksum = checkSums[i + j];
                    if (checksum == IGNORE) {
                        blockChecksum = IGNORE;
                        break;
                    }
                    blockChecksum += (j + 1) * BIG_PRIME * checksum;
                }
                ret[i] = onlyEmptyLines ? IGNORE : blockChecksum;
            }
            return ret;
        }

        /**
         * Computes a checksum for a a single line of text.
         * @param aLine the aLine
         * @return checksum
         */
        protected int calcChecksum(String aLine)
        {
            final int hashCode = aLine.hashCode();
            if (hashCode == IGNORE) {
                return Integer.MAX_VALUE / 2;
            }
            return hashCode;
        }
    }

    /**
     * A TextfileChecksumGenerator that also ignores imports.
     */
    private class JavaChecksumGenerator extends TextfileChecksumGenerator
    {
        // TODO: return IGNORE for lines in the header comment?
        // That would require some simple parsing...

        // we could also parse the java code using the TreeWalker
        // and then ignore everything before the CLASS_DEF...

        @Override
        protected int calcChecksum(String aLine)
        {
            // to avoid false alarms it is important that different lines
            // result in different checksums.
            if (aLine.startsWith("import ")) {
                return IGNORE;
            }
            return super.calcChecksum(aLine);
        }
    }

    /** a jakarta commons log */
    private static final Log LOG =
            LogFactory.getLog(StrictDuplicateCodeCheck.class);

    /** the checksum value to use for lines that should be ignored */
    static final int IGNORE = Integer.MIN_VALUE;

    /** default value for mMin */
    private static final int DEFAULT_MIN_DUPLICATE_LINES = 12;

    /** number of lines that have to be idential for reporting duplicates */
    private int mMin = DEFAULT_MIN_DUPLICATE_LINES;

    /** the basedir to strip off in filenames */
    private String mBasedir;

    /**
     * The checksums of all files that are currently checked.
     * Dimension one: file index
     * Dimension two: block start line
     */
    private int[][] mLineBlockChecksums;

    /**
     * helper to speed up searching algorithm, holds the checksums from
     * {@link #mLineBlockChecksums} except {@link #IGNORE}, sorted.
     */
    private ChecksumInfo[] mChecksumInfo;

    /** files that are currently checked */
    private final List<File> mFiles = Lists.newArrayList();

    /**
     * A SoftReference cache for the trimmed lines of a file path.
     */
    private final Map<String, String[]> mTrimmedLineCache =
        new MapMaker().softValues().makeMap();

    // fields required only for statistics

    /** total number of duplicates found */
    private int mDuplicates;
    /** the charset used to load files. */
    private String mCharset;

    /** Creates a new instance of this class. */
    public StrictDuplicateCodeCheck()
    {
    }

    /**
     * Sets the minimum number of lines that must be equivalent
     * before the check complains.
     *
     * @param aMin the number of lines that must be equal before
     * triggering a 'duplicate code' message.
     */
    public void setMin(int aMin)
    {
        if (aMin < 1) {
            throw new IllegalArgumentException("min must be 1 or higher");
        }
        mMin = aMin;
    }

    /** @param aBasedir the base directory to strip off in filenames */
    public void setBasedir(String aBasedir)
    {
        mBasedir = aBasedir;
    }

    @Override
    public void beginProcessing(String aCharset)
    {
        super.beginProcessing(aCharset);
        mCharset = aCharset;
        mFiles.clear();
    }

    @Override
    protected void processFiltered(File aFile, List<String> aLines)
    {
        mFiles.add(aFile);
    }

    @Override
    public void finishProcessing()
    {
        super.finishProcessing();
        final long start = System.currentTimeMillis();
        mDuplicates = 0;
        mLineBlockChecksums = new int[mFiles.size()][];
        mChecksumInfo = new ChecksumInfo[mFiles.size()];

        if (LOG.isDebugEnabled()) {
            LOG.debug("Reading " + mFiles.size() + " input files");
        }

        for (int i = 0; i < mFiles.size(); i++) {
            final File file = mFiles.get(i);
            try {
                final String[] lines = getTrimmedLines(file);
                final ChecksumGenerator transformer =
                    findChecksumGenerator(file);
                mLineBlockChecksums[i] = transformer.convertLines(lines);
            }
            catch (final IOException ex) {
                LOG.error("Cannot access " + file + " ("
                          + ex.getMessage() + "), ignoring", ex);
                // TODO: better to throw an exception here?
                // it would be best to throw IOException from process(),
                // but interface definition doesn't allow that...
                mLineBlockChecksums = new int[0][0];
            }
        }
        fillSortedRelevantChecksums();

        final long endReading = System.currentTimeMillis();
        findDuplicates();
        final long endSearching = System.currentTimeMillis();

        dumpStats(start, endReading, endSearching);

        mLineBlockChecksums = null;
        mChecksumInfo = null;
    }

    /**
     * Finds the Checksum generator for a given file.
     *
     * @param aFile the file to check for duplicates
     * @return a generator to use for aFile
     */
    private ChecksumGenerator findChecksumGenerator(File aFile)
    {
        if (aFile.getName().endsWith(".java")) {
            return new JavaChecksumGenerator();
        }
        // TODO: Not sure what to do with binary files such as gifs
        return new TextfileChecksumGenerator();
    }

    /**
     * Dump out statistics data on stderr.
     * @param aStart start time
     * @param aEndReading end time of reading phsical files
     * @param aEndSearching end time duplicate analysis
     */
    private void dumpStats(long aStart, long aEndReading, long aEndSearching)
    {
        if (LOG.isDebugEnabled()) {
            final long initTime = aEndReading - aStart;
            final long workTime = aEndSearching - aEndReading;
            LOG.debug("files = " + mFiles.size());
            LOG.debug("duplicates = " + mDuplicates);
            LOG.debug("Runtime = " + initTime + " + " + workTime);
        }
    }

    /**
     * filters and sorts the relevant lines and stores the result
     * in sortedRelevantChecksums during the setup phase.
     * That data is later used in a binary search to find out
     * if it is worth investigating a file for duplicates of a block.
     * If the block's checksum does not occur in the other file
     * at all, we can skip that file quickly.
     */
    private void fillSortedRelevantChecksums()
    {
        for (int i = 0; i < mLineBlockChecksums.length; i++) {
            final int[] checksums = mLineBlockChecksums[i];
            mChecksumInfo[i] = new ChecksumInfo(checksums);
        }
    }

    /**
     * finds duplicate lines in mFiles,
     * using a textsearch algorithm to find reoccuring
     * patters in the lineChecksums.
     */
    private void findDuplicates()
    {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Analysis phase");
        }

        // It's been a while since my CS degree, but I think this is
        // somewhere near O(LOC^2).

        // It may be possible to do this *much* smarter,
        // but I don't have the Knuth bible at hand right now :-)

        final int len = mFiles.size();
        for (int i = 0; i < len; i++) {

            final String path = mFiles.get(i).getPath();
            getMessageCollector().reset();
            final MessageDispatcher dispatcher = getMessageDispatcher();
            dispatcher.fireFileStarted(path);

            for (int j = 0; j <= i; j++) {
                findDuplicatesInFiles(i, j);
            }

            fireErrors(path);
            dispatcher.fireFileFinished(path);
        }
    }

    /**
     * Compare two files and search for duplicates.
     * @param aI mLineChecksums index of the first file to compare
     * @param aJ mLineChecksums index of the seconds file to compare
     */
    private void findDuplicatesInFiles(int aI, int aJ)
    {
        final ChecksumInfo iChecksumInfo = mChecksumInfo[aI];
        final ChecksumInfo jChecksumInfo = mChecksumInfo[aJ];
        if (!iChecksumInfo.hasChecksumOverlapsWith(jChecksumInfo)) {
            return;
        }

        final int[] iLineBlockChecksums = mLineBlockChecksums[aI];
        final int iBlockCount = iLineBlockChecksums.length;

        // blocks of duplicate code might be longer than 'min'. We need to
        // remember the line combinations where we must ignore identical blocks
        // because we have already reported them for an earlier blockIdx.
        final Multimap<Integer, Integer> ignorePairs =
            ArrayListMultimap.create();

        // go through all the blocks in iFile and
        // check if the following mMin lines occur in jFile
        for (int iLine = 0; iLine < iBlockCount; iLine++) {

            final int iSum = iLineBlockChecksums[iLine];
            final int[] jLines = jChecksumInfo.findLinesWithChecksum(iSum);
            // detailed analysis only if the iLine block occurs in jFile at all
            if (jLines.length > 0) {
                findDuplicateFromLine(aI, aJ, iLine, jLines, ignorePairs);
            }
        }
    }

    /**
     * Find and report a duplicate of the code starting from line aILine
     * in file aI in the file aJ. The caller has already ensured that
     * there are at least mMax duplicate lines, this method mainly analyzes
     * how far the block of duplicates extends.
     *
     * @param aI index of file that contains the candidate code
     * @param aJ index of file that is searched for a dup of the candidate
     * @param aILine starting line of the candidate in aI
     * @param aJLines lines in file aJ that have the same checksum as aILine
     * @param aIgnore Bag from iLine to jLines, an entry indicates that
     * this line i/j-combination has already been reported as part of another
     * viloation
     */
    private void findDuplicateFromLine(
        final int aI, final int aJ, final int aILine,
        final int[] aJLines, final Multimap<Integer, Integer> aIgnore)
    {
        // Using something more advanced like Boyer-Moore might be a
        // good idea...

        final int[] iCheckSums = mLineBlockChecksums[aI];
        final int[] jCheckSums = mLineBlockChecksums[aJ];
        final long checkSum = iCheckSums[aILine];

        for (int jLine : aJLines) {

            if (aI == aJ && aILine >= jLine) {
                continue;
            }

            if (jCheckSums[jLine] != checkSum) {
                continue;
            }

            final Collection<Integer> ignoreEntries = aIgnore.get(aILine);
            // avoid Integer constructor whenever we can
            if (ignoreEntries != null) {
                if (ignoreEntries.contains(jLine)) {
                    continue;
                }
            }

            final int duplicateLines =
                verifiyDuplicateLines(aI, aJ, aILine, jLine);
            if (duplicateLines >= mMin) {
                reportDuplicate(duplicateLines, aILine, mFiles.get(aJ), jLine);
                final int extend = duplicateLines - mMin;
                for (int i = 0; i < extend; i++) {
                    final int offset = (i + 1);
                    aIgnore.put(aILine + offset, jLine + offset);
                }
            }
        }
    }

    /**
     * Verifies the number of lines that are equal.
     * Note that block checksums might be equal for blocks that in fact
     * are different, so we must check the actual file content again.
     *
     * @param aI file index i
     * @param aJ file index j
     * @param aIStartLine start line of potential duplicate code in file i
     * @param aJStartLine start line of potential duplicate code in file j
     * @return the number of verified equal lines
     */
    private int verifiyDuplicateLines(
        int aI, int aJ, int aIStartLine, int aJStartLine)
    {
        final File iFile = mFiles.get(aI);
        final File jFile = mFiles.get(aJ);
        try {
            final String[] iLines = getTrimmedLines(iFile);
            final String[] jLines = getTrimmedLines(jFile);

            int verified = 0;
            int i = aIStartLine;
            int j = aJStartLine;
            while (i < iLines.length && j < jLines.length
                && iLines[i++].equals(jLines[j++]))
            {
                verified += 1;
            }
            return verified;
        }
        catch (IOException ex) {
            LOG.error("Unable to verify potential duplicate for "
                + iFile + " and " + jFile, ex);
            return 0;
        }
    }


    /**
     * Returns the trimmed lines of a given file.
     * Caches the results, so when memory is available
     * we try to avoid reading the file repeatedly.
     *
     * @param aFile the file
     * @return the lines in aFile after applying {@link String#trim()}
     * @throws IOException if the file content cannot be read
     */
    private String[] getTrimmedLines(File aFile) throws IOException
    {
        final String path = aFile.getPath();
        final String[] cachedLines = mTrimmedLineCache.get(path);
        if (cachedLines != null) {
            return cachedLines;
        }
        final String charset = mCharset;
        final FileText text = new FileText(aFile, charset);
        final String[] lines = getTrimmed(text.toLinesArray());
        mTrimmedLineCache.put(path, lines);
        return lines;
    }

    /**
     * Applies {@link String#trim()} on each String in a given array.
     * @param aLines the original Strings
     * @return the converted Strings after applying {@link String#trim()}
     */
    private String[] getTrimmed(String[] aLines)
    {
        final String[] ret = new String[aLines.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = aLines[i].trim();
        }
        return ret;
    }

    /**
     * Dumps out a duplicate report.
     * @param aEquivalent number of equivalent lines
     * @param aILine location of duplicate code
     * within file that is currently checked
     * @param aJFile the other file that contains the duplicate
     * @param aJLine location of duplicate code within aJFile
     */
    private void reportDuplicate(
            int aEquivalent, int aILine, File aJFile, int aJLine)
    {
        final String fileName =
                Utils.getStrippedFileName(mBasedir, aJFile.getPath());
        log(aILine + 1, "duplicates.lines", aEquivalent, fileName, aJLine + 1);
        mDuplicates += 1;
    }

}
