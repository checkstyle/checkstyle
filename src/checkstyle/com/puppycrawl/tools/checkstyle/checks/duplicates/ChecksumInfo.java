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

import java.util.Arrays;

/**
 * Helper class for {@link StrictDuplicateCodeCheck},
 * provides block checksum information for a single file.
 *
 * @author lkuehne
 */
final class ChecksumInfo
{
    /**
     * Helper value to avoid object allocations in
     * {@link #hasChecksumOverlapsWith(ChecksumInfo)}.
     */
    private static final int[] NO_LINES = new int[0];

    /**
     * Holds the checksums from the constructor call,
     * except {@link StrictDuplicateCodeCheck#IGNORE}, sorted.
     */
    private int[] mSortedChecksums;

    /**
     * Reverse mapping from {@link #mSortedChecksums} to the checksums
     * from the constructor call.
     *
     * <code>mSortedRelevantChecksums[i] == checksums[mOrigIdx[i]]</code>
     */
    private int[] mOrigIdx;

    /**
     * Creates a new ChecksumInfo.
     *
     * @param aBlockChecksums the block checksums as caculated by
     * the {@link StrictDuplicateCodeCheck}.ChecksumGenerator
     */
    ChecksumInfo(int[] aBlockChecksums)
    {
        final int csLen = aBlockChecksums.length;
        final int[] relevant = new int[csLen];
        final int[] reverse = new int[csLen];
        int count = 0;
        for (int j = 0; j < csLen; j++) {
            final int checksum = aBlockChecksums[j];
            if (checksum != StrictDuplicateCodeCheck.IGNORE) {
                reverse[count] = j;
                relevant[count++] = checksum;
            }
        }
        mSortedChecksums = new int[count];
        mOrigIdx = new int[count];
        System.arraycopy(relevant, 0, mSortedChecksums, 0, count);
        System.arraycopy(reverse, 0, mOrigIdx, 0, count);
        sort();
    }

    /**
     * Sorts the {@link #mSortedChecksums} field and simultaneously
     * maintains the {@link mOrigIdx} mapping. The maintainance of the
     * reverse mapping is the reason why we don't simply use Arrays.sort() here.
     */
    private void sort()
    {
        // abbreviation for longish field name
        final int[] arr = mSortedChecksums;
        final int len = arr.length;

        // bubblesort will do for now. It's important that the algorithm
        // is stable, i.e. it doesn't swap equal values
        for (int i = 0; i < len; i++) {
            for (int j = i; j > 0 && arr[j] < arr[j - 1]; j--) {
                final int k = j - 1;
                // swap j and k and maintain mOrigIdx
                final int v = arr[j];
                arr[j] = arr[k];
                arr[k] = v;
                final int z = mOrigIdx[j];
                mOrigIdx[j] = mOrigIdx[k];
                mOrigIdx[k] = z;
            }
        }
    }

    /**
     * Returns whether the same checksum occurs both in this ChecksumInfo and
     * another one,
     *
     * @param aChecksumInfo the other ChecksumInfo
     * @return true iff the same checksum occurs in both ChecksumInfos
     */
    boolean hasChecksumOverlapsWith(final ChecksumInfo aChecksumInfo)
    {
        final int[] jSortedrelevantChecksums =
            aChecksumInfo.mSortedChecksums;
        final int iLen = mSortedChecksums.length;
        final int jLen = jSortedrelevantChecksums.length;

        // Both arrays are sorted, so we walk them in parallel,
        // increasing the index that points to the smaller value.
        // If the values ever become the same we have found an overlap.
        int jdx = 0;
        int idx = 0;
        while (jdx < jLen && idx < iLen) {
            final long iSum = mSortedChecksums[idx];
            final long jSum = jSortedrelevantChecksums[jdx];
            if (iSum < jSum) {
                idx += 1;
            }
            else if (iSum > jSum) {
                jdx += 1;
            }
            else {
                // files i and j contain a block with the same checksum
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the lines that start a block with a given checksum.
     *
     * @param aSum the checksum
     * @return sorted line indices
     */
    int[] findLinesWithChecksum(final int aSum)
    {
        int idx = Arrays.binarySearch(mSortedChecksums, aSum);
        if (idx < 0) {
            return NO_LINES;
        }

        // binary search might have left us in the
        // middle of a sequence of identical checksums

        // rewind
        while (idx > 0 && mSortedChecksums[idx - 1] == aSum) {
            idx -= 1;
        }
        final int start = idx;

        // forward
        int end = start + 1;
        while (end < mSortedChecksums.length
                && mSortedChecksums[end] == mSortedChecksums[end - 1])
        {
            end += 1;
        }

        // find original lines through reverse mapping
        final int[] ret = new int[end - start];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = mOrigIdx[start + i];
        }
        Arrays.sort(ret);
        return ret;
    }
}
