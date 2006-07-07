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

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
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
     * Converts each of the original source lines
     * to a checksum that is checked against to find duplicates.
     */
    private interface ChecksumGenerator
    {
        /**
         * Convert each of the original source lines
         * to a checksum that is checked against to find duplicates
         * Typically this involves stripping whitespace.
         * @param aOriginalLines the original lines as they appear in the source
         * @return the converted line or null if the line should be ignored
         */
        long[] convertLines(String[] aOriginalLines);
    }


    /**
     * Calculates checksums for text files.
     * Removes leading and trainling whitespace.
     */
    private class TextfileChecksumGenerator implements ChecksumGenerator
    {
        /** {@inheritDoc} */
        public long[] convertLines(String[] aOriginalLines)
        {
            final long[] checkSums = new long[aOriginalLines.length];
            for (int i = 0; i < aOriginalLines.length; i++) {
                final String line = aOriginalLines[i].trim();
                checkSums[i] = calcChecksum(line);
            }
            return checkSums;
        }

        /**
         * Computes a checksum for a a single line of text.
         * @param aLine the aLine
         * @return checksum
         */
        protected long calcChecksum(String aLine)
        {
            // important that it's larger than the length of most lines
            // see http://www.utm.edu/research/primes/lists/small/1000.txt
            final int bigPrime = 317;

            // TODO: Not sure that this algorithm makes it
            // sufficiently improbable to get false alarms
            long result = 0;
            for (int i = 0; i < aLine.length(); i++) {
                final long c = aLine.charAt(i);
                result += bigPrime * i + c;
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

    /** the checksums of all files that are currently checked */
    private long[][] mLineChecksums;

    /** helper to speed up searching algorithm */
    private long[][] mSortedRelevantChecksums;

    /** files that are currently checked */
    private File[] mFiles;

    // fields required only for statistics

    /** total number of duplicates found */
    private int mDuplicates;

    /** lines of code that have been checked */
    private int mLoc;

    /** number of chache misses */
    private long mCacheMisses;

    /** number of cache hits */
    private long mCacheHits;

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
        mLoc = 0;
        mDuplicates = 0;
        mFiles = filter(aFiles);
        mLineChecksums = new long[mFiles.length][];
        mSortedRelevantChecksums = new long[mFiles.length][];

        if (LOG.isDebugEnabled()) {
            LOG.debug("Reading input files");
        }

        for (int i = 0; i < mFiles.length; i++) {
            try {
                final File file = mFiles[i];
                final String[] lines =
                    Utils.getLines(file.getPath(), getCharset());
                final ChecksumGenerator transformer =
                    findChecksumGenerator(file);
                mLineChecksums[i] = transformer.convertLines(lines);
            }
            catch (IOException ex) {
                LOG.error("Cannot access files to check, giving up: "
                        + ex.getMessage(), ex);
                // TODO: better to throw an exception here?
                // it would be best to throw IOException from process(),
                // but interface definition doesn't allow that...
                mLineChecksums = new long[0][0];
            }
        }
        fillSortedRelevantChecksums();

        final long endReading = System.currentTimeMillis();
        findDuplicates();
        final long endSearching = System.currentTimeMillis();

        dumpStats(start, endReading, endSearching);

        mLineChecksums = null;
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
        if (LOG.isDebugEnabled()) {
            final long cacheLookups = mCacheHits + mCacheMisses;
            final long initTime = aEndReading - aStart;
            final long workTime = aEndSearching - aEndReading;
            LOG.debug("cache hits = " + mCacheHits + "/" + cacheLookups);
            LOG.debug("files = " + mFiles.length);
            LOG.debug("loc = " + mLoc);
            LOG.debug("duplicates = " + mDuplicates);
            LOG.debug("Runtime = " + initTime + " + " + workTime);
        }
    }

    /**
     * filters and sorts the relevant lines and stores the result
     * in sortedRelevantChecksums during the setup phase.
     * That data is later used in a binary search to find out
     * if it is worth investigating a file for duplicates of a block.
     * If one of the lines in the block does not occur in the other file
     * at all, we can skip that file quickly.
     */
    private void fillSortedRelevantChecksums()
    {
        for (int i = 0; i < mLineChecksums.length; i++) {
            int count = 0;
            final long[] checksums = mLineChecksums[i];
            final long[] relevant = new long[checksums.length];
            for (int j = 0; j < checksums.length; j++) {
                long checksum = checksums[j];
                if (checksum != IGNORE) {
                    relevant[count++] = checksum;
                }
            }
            Arrays.sort(relevant, 0, count);
            long[] result = new long[count];
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
        // somewhere near O(mMax * LOC^2).

        // It may be possible to do this *much* smarter,
        // but I don't have the Knuth bible at hand right now :-)

        // OK, prepare for some nested loops... :-(

        for (int i = 0; i < mFiles.length; i++) {

            final String path = mFiles[i].getPath();

            getMessageCollector().reset();
            final MessageDispatcher dispatcher = getMessageDispatcher();
            dispatcher.fireFileStarted(path);

            mLoc += mLineChecksums[i].length;
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
        final int iFileLength = mLineChecksums[aI].length;

        // build up some supporting data structures
        final boolean[] iLineOccurInJ = new boolean[iFileLength];
        for (int iLine = 0; iLine < iFileLength; iLine++) {
            iLineOccurInJ[iLine] = (Arrays.binarySearch(
                mSortedRelevantChecksums[aJ], mLineChecksums[aI][iLine]) >= 0);
        }

        // go through all the lines in iFile and check if the following
        // mMin lines occur in jFile
        for (int iLine = 0; iLine < iFileLength - mMin; iLine++) {

            // fast exit if one of the lines does not occur in jFile at all
            boolean fastExit = false;
            final int kLimit = iFileLength - iLine;
            for (int k = 0; k < Math.min(mMin, kLimit); k++) {
                if (!iLineOccurInJ[iLine + k]) {
                    fastExit = true;
                    break;
                }
            }

            if (!fastExit) {
                // all lines do occur -> brute force searching
                mCacheMisses += 1;
                iLine = findDuplicateFromLine(aI, aJ, iLine);
            }
            else {
                mCacheHits += 1;
            }
        }
    }

    /**
     * Find and report a duplicate of the code starting from line aILine
     * in file aI in the file aJ
     * @param aI index of file that contains the candidate code
     * @param aJ index of file that is searched for a dup of the candidate
     * @param aILine starting line of the candidate in aI
     * @return the next line in file i where
     * starting to search will make sense
     */
    private int findDuplicateFromLine(int aI, int aJ, int aILine)
    {
        // Using something more advanced like Boyer-Moore might be a
        // good idea...

        final int iFileLength = mLineChecksums[aI].length;
        final int jFileLength = mLineChecksums[aJ].length;

        for (int jLine = 0; jLine < jFileLength - mMin; jLine++) {

            if ((aI == aJ) && (aILine == jLine)) {
                continue;
            }

            int equivalent = 0;
            while ((aILine + equivalent < iFileLength)
                    && (jLine + equivalent < jFileLength)
                    && (mLineChecksums[aI][aILine + equivalent] != IGNORE)
                    && (mLineChecksums[aI][aILine + equivalent]
                       == mLineChecksums[aJ][jLine + equivalent]))
            {
                equivalent += 1;
            }

            if (((aI != aJ) || (aILine < jLine)) && (equivalent >= mMin)) {
                reportDuplicate(equivalent, aILine, mFiles[aJ], jLine);
                aILine += equivalent; // skip to end of equivalent section
            }
        }
        return aILine;
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
