/// ////////////////////////////////////////////////////////////////////////////////////////////
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
/// ////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilterSet;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Utility class for Checker-related operations.
 */
final class CheckerUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private CheckerUtil() {
        // Private constructor to prevent instantiation.
    }

    /**
     * Extracts a localized message from Checkstyle's properties files.
     *
     * @param messageKey the key for the message in the messages.properties file
     * @param sourceClass the source class requesting the message (used for package resolution)
     * @param args variable arguments for message formatting
     * @return the formatted localized message string
     */
    /* default */
    public static String getLocalizedMessage(String messageKey,
                                             Class<? extends Checker> sourceClass,
                                             Object... args) {
        return new LocalizedMessage(
            Definitions.CHECKSTYLE_BUNDLE,
            sourceClass,
            messageKey,
            args).getMessage();
    }

    /**
     * Determines whether a file should be skipped based on its cache status and file filters.
     *
     * @param fileName the full path of the file to check
     * @param timestamp the last modification time of the file
     * @param cacheFile the cache implementation (may be null)
     * @param fileFilters the set of file filters to check against
     * @param baseDir the base directory for relative path calculation
     * @return if the file should be skipped (cached or filtered)
     */
    public static boolean isCached(String fileName,
                                   long timestamp,
                                   PropertyCacheFile cacheFile,
                                   BeforeExecutionFileFilterSet fileFilters,
                                   String baseDir) {
        return cache(
            cacheFile != null
                && cacheFile.isInCache(fileName, timestamp)
                || !fileFilters.accept(CommonUtil.relativizePath(baseDir, fileName)),
            cacheFile,
            fileName,
            timestamp
        );
    }

    /**
     * Updates the cache for files that should be processed.
     *
     * @param skip      to skip caching
     * @param cacheFile the cache implementation (may be null)
     * @param fileName  the full path of the file to cache
     * @param timestamp the last modification time to store in the cache
     * @return the original {@code skip} parameter value
     */
    /* default */
    public static boolean cache(boolean skip,
                                PropertyCacheFile cacheFile,
                                String fileName,
                                long timestamp) {
        if (!skip && cacheFile != null) {
            cacheFile.put(fileName, timestamp);
        }
        return skip;
    }
}
