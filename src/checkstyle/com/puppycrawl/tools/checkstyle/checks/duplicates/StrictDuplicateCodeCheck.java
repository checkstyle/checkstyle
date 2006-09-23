////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;

import org.apache.commons.collections.ReferenceMap;
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
        long[] convertLines(String[] aOriginalLines);
    }


    /**
     * Calculates checksums for text files.
     */
    private class TextfileChecksumGenerator implements ChecksumGenerator
    {
        /** {@inheritDoc} */
        public long[] convertLines(String[] aOriginalLines)
        {
            final int lineCount = aOriginalLines.length;
            final long[] checkSums = new long[lineCount];
            for (int i = 0; i < lineCount; i++) {
                final String line = aOriginalLines[i];
                checkSums[i] = calcChecksum(line);
            }
            final int retLen = Math.max(0, lineCount - mMin + 1);
            final long[] ret = new long[retLen];

            for (int i = 0; i < retLen; i++) {
                long blockChecksum = 0;
                for (int j = 0; j < mMin; j++) {
                    final long checksum = checkSums[i + j];
                    if (checksum == IGNORE) {
                        blockChecksum = IGNORE;
                        break;
                    }
                    blockChecksum += (j + 1) * BIG_PRIME * checksum;
                }
                ret[i] = blockChecksum;
            }
            return ret;
        }

        /**
         * Computes a checksum for a a single line of text.
         * @param aLine the aLine
         * @return checksum
         */
        protected long calcChecksum(String aLine)
        {
            // TODO: Not sure that this algorithm makes it
            // sufficiently improbable to get false alarms
            long result = 0;
            for (int i = 0; i < aLine.length(); i++) {
                final long c = aLine.charAt(i);
                result += BIG_PRIME * (i + 1) + c;
            }
            return result;
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


        /**
         * computes a checksum for a aLine. to avoid false alarms it is
         * important that different lines result in different checksums.
         * @param aLine the aLine
         * @return checksum
         */
        protected long calcChecksum(String aLine)
        {
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
    private static final long IGNORE = Long.MIN_VALUE;

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
    private long[][] mLineBlockChecksums;

    /**
     * helper to speed up searching algorithm, holds the checksums from
     * {@link #mLineBlockChecksums} except {@link #IGNORE}, sorted.
     */
    private long[][] mSortedRelevantChecksums;

    /** files that are currently checked */
    private File[] mFiles;

    /**
     * A SoftReference cache for the trimmed lines of a file path,
     */
    private Map mTrimmedLineCache = new ReferenceMap();

    // fields required only for statistics

    /** total number of duplicates found */
    private int mDuplicates;

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

    /**
     * {@inheritDoc}
     */
    public synchronized void process(File[] aFiles)
    {
        final long start = System.currentTimeMillis();
        mDuplicates = 0;
        mFiles = filter(aFiles);
        mLineBlockChecksums = new long[mFiles.length][];
        mSortedRelevantChecksums = new long[mFiles.length][];

        if (LOG.isDebugEnabled()) {
            LOG.debug("Reading " + mFiles.length + " input files");
        }

        for (int i = 0; i < mFiles.length; i++) {
            final File file = mFiles[i];
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
                mLineBlockChecksums = new long[0][0];
            }
        }
        fillSortedRelevantChecksums();

        final long endReading = System.currentTimeMillis();
        findDuplicates();
        final long endSearching = System.currentTimeMillis();

        dumpStats(start, endReading, endSearching);

        mLineBlockChecksums = null;
        mSortedRelevantChecksums = null;
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
        if (LOG.isInfoEnabled()) {
            final long initTime = aEndReading - aStart;
            final long workTime = aEndSearching - aEndReading;
            LOG.debug("files = " + mFiles.length);
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
            int count = 0;
            final long[] checksums = mLineBlockChecksums[i];
            final long[] relevant = new long[checksums.length];
            for (int j = 0; j < checksums.length; j++) {
                final long checksum = checksums[j];
                if (checksum != IGNORE) {
                    relevant[count++] = checksum;
                }
            }
            Arrays.sort(relevant, 0, count);
            final long[] result = new long[count];
            System.arraycopy(relevant, 0, result, 0, count);
            mSortedRelevantChecksums[i] = result;
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

        for (int i = 0; i < mFiles.length; i++) {

            final String path = mFiles[i].getPath();
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
        final long[] iLineBlockChecksums = mLineBlockChecksums[aI];
        final long[] jSortedBlockChecksums = mSortedRelevantChecksums[aJ];
        final int iBlockCount = iLineBlockChecksums.length;

        // go through all the blocks in iFile and
        // check if the following mMin lines occur in jFile
        for (int blockIdx = 0; blockIdx < iBlockCount; blockIdx++) {

            // detailed analysis only if the block does occur in jFile at all
            if (Arrays.binarySearch(
                    jSortedBlockChecksums,
                    iLineBlockChecksums[blockIdx]) >= 0)
            {
                blockIdx = findDuplicateFromLine(aI, aJ, blockIdx);
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
     * @return the next block index in file i where
     * starting to search will make sense
     */
    private int findDuplicateFromLine(
        final int aI, final int aJ, final int aILine)
    {
        // Using something more advanced like Boyer-Moore might be a
        // good idea...

        final long checkSum = mLineBlockChecksums[aI][aILine];

        final int iBlockCount = mLineBlockChecksums[aI].length;
        final int jBlockCount = mLineBlockChecksums[aJ].length;

        for (int jBlock = 0; jBlock < jBlockCount; jBlock++) {

            if (aI == aJ && aILine >= jBlock) {
                continue;
            }

            if (mLineBlockChecksums[aJ][jBlock] != checkSum) {
                continue;
            }

            int duplicateLines = verifiyDuplicateLines(aI, aJ, aILine, jBlock);
            if (duplicateLines >= mMin) {
                reportDuplicate(duplicateLines, aILine, mFiles[aJ], jBlock);

                // skip to end of equivalent section
                return aILine + duplicateLines;
            }
        }
        return aILine;
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
        final File iFile = mFiles[aI];
        final File jFile = mFiles[aJ];
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
        final String[] cachedLines = (String[]) mTrimmedLineCache.get(path);
        if (cachedLines != null) {
            return cachedLines;
        }
        final String charset = getCharset();
        final String[] lines = getTrimmed(Utils.getLines(path, charset));
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
        String[] ret = new String[aLines.length];
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
        final Integer dupLines = new Integer(aEquivalent);
        final Integer startLine = new Integer(aJLine + 1);
        final String fileName =
                Utils.getStrippedFileName(mBasedir, aJFile.getPath());
        log(aILine + 1, "duplicates.lines",
                new Object[]{dupLines, fileName, startLine});
        mDuplicates += 1;
    }

}
