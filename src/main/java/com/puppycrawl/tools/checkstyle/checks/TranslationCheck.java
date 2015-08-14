////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;

/**
 * <p>
 * The TranslationCheck class helps to ensure the correct translation of code by
 * checking property files for consistency regarding their keys.
 * Two property files describing one and the same context are consistent if they
 * contain the same keys.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="Translation"/&gt;
 * </pre>
 * Check has a property <b>basenameSeparator</b> which allows setting separator in file names,
 * default value is '_'.
 * <p>
 * E.g.:
 * </p>
 * <p>
 * messages_test.properties //separator is '_'
 * </p>
 * <p>
 * app-dev.properties //separator is '-'
 * </p>
 * <br>
 * @author Alexandra Bunge
 * @author lkuehne
 */
public class TranslationCheck
    extends AbstractFileSetCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "translation.missingKey";

    /** Logger for TranslationCheck */
    private static final Log LOG = LogFactory.getLog(TranslationCheck.class);

    /** The property files to process. */
    private final List<File> propertyFiles = Lists.newArrayList();

    /** The separator string used to separate translation files */
    private String basenameSeparator;

    /**
     * Creates a new {@code TranslationCheck} instance.
     */
    public TranslationCheck() {
        setFileExtensions("properties");
        setBasenameSeparator("_");
    }

    @Override
    public void beginProcessing(String charset) {
        super.beginProcessing(charset);
        propertyFiles.clear();
    }

    @Override
    protected void processFiltered(File file, List<String> lines) {
        propertyFiles.add(file);
    }

    @Override
    public void finishProcessing() {
        super.finishProcessing();
        final Map<String, Set<File>> propFilesMap =
            arrangePropertyFiles(propertyFiles, basenameSeparator);
        checkPropertyFileSets(propFilesMap);
    }

    /**
     * Gets the basename (the unique prefix) of a property file. For example
     * "xyz/messages" is the basename of "xyz/messages.properties",
     * "xyz/messages_de_AT.properties", "xyz/messages_en.properties", etc.
     *
     * @param file the file
     * @param basenameSeparator the basename separator
     * @return the extracted basename
     */
    private static String extractPropertyIdentifier(final File file,
            final String basenameSeparator) {
        final String filePath = file.getPath();
        final int dirNameEnd = filePath.lastIndexOf(File.separatorChar);
        final int baseNameStart = dirNameEnd + 1;
        final int underscoreIdx = filePath.indexOf(basenameSeparator,
            baseNameStart);
        final int dotIdx = filePath.indexOf('.', baseNameStart);
        final int cutoffIdx = underscoreIdx == -1 ? dotIdx : underscoreIdx;
        return filePath.substring(0, cutoffIdx);
    }

    /**
     * Sets the separator used to determine the basename of a property file.
     * This defaults to "_"
     *
     * @param basenameSeparator the basename separator
     */
    public final void setBasenameSeparator(String basenameSeparator) {
        this.basenameSeparator = basenameSeparator;
    }

    /**
     * Arranges a set of property files by their prefix.
     * The method returns a Map object. The filename prefixes
     * work as keys each mapped to a set of files.
     * @param propFiles the set of property files
     * @param basenameSeparator the basename separator
     * @return a Map object which holds the arranged property file sets
     */
    private static Map<String, Set<File>> arrangePropertyFiles(
        List<File> propFiles, String basenameSeparator) {
        final Map<String, Set<File>> propFileMap = Maps.newHashMap();

        for (final File file : propFiles) {
            final String identifier = extractPropertyIdentifier(file,
                basenameSeparator);

            Set<File> fileSet = propFileMap.get(identifier);
            if (fileSet == null) {
                fileSet = Sets.newHashSet();
                propFileMap.put(identifier, fileSet);
            }
            fileSet.add(file);
        }
        return propFileMap;
    }

    /**
     * Loads the keys of the specified property file into a set.
     * @param file the property file
     * @return a Set object which holds the loaded keys
     */
    private Set<Object> loadKeys(File file) {
        final Set<Object> keys = Sets.newHashSet();
        InputStream inStream = null;

        try {
            // Load file and properties.
            inStream = new FileInputStream(file);
            final Properties props = new Properties();
            props.load(inStream);

            // Gather the keys and put them into a set
            final Enumeration<?> e = props.propertyNames();
            while (e.hasMoreElements()) {
                keys.add(e.nextElement());
            }
        }
        catch (final IOException e) {
            logIOException(e, file);
        }
        finally {
            Closeables.closeQuietly(inStream);
        }
        return keys;
    }

    /**
     * helper method to log an io exception.
     * @param ex the exception that occured
     * @param file the file that could not be processed
     */
    private void logIOException(IOException ex, File file) {
        String[] args = null;
        String key = "general.fileNotFound";
        if (!(ex instanceof FileNotFoundException)) {
            args = new String[] {ex.getMessage()};
            key = "general.exception";
        }
        final LocalizedMessage message =
            new LocalizedMessage(
                0,
                Definitions.CHECKSTYLE_BUNDLE,
                key,
                args,
                getId(),
                getClass(), null);
        final SortedSet<LocalizedMessage> messages = Sets.newTreeSet();
        messages.add(message);
        getMessageDispatcher().fireErrors(file.getPath(), messages);
        LOG.debug("IOException occured.", ex);
    }

    /**
     * Compares the key sets of the given property files (arranged in a map)
     * with the specified key set. All missing keys are reported.
     * @param keys the set of keys to compare with
     * @param fileMap a Map from property files to their key sets
     */
    private void compareKeySets(Set<Object> keys,
            Map<File, Set<Object>> fileMap) {
        final Set<Entry<File, Set<Object>>> fls = fileMap.entrySet();

        for (Entry<File, Set<Object>> entry : fls) {
            final File currentFile = entry.getKey();
            final MessageDispatcher dispatcher = getMessageDispatcher();
            final String path = currentFile.getPath();
            dispatcher.fireFileStarted(path);
            final Set<Object> currentKeys = entry.getValue();

            // Clone the keys so that they are not lost
            final Set<Object> keysClone = Sets.newHashSet(keys);
            keysClone.removeAll(currentKeys);

            // Remaining elements in the key set are missing in the current file
            if (!keysClone.isEmpty()) {
                for (Object key : keysClone) {
                    log(0, MSG_KEY, key);
                }
            }
            fireErrors(path);
            dispatcher.fireFileFinished(path);
        }
    }

    /**
     * Tests whether the given property files (arranged by their prefixes
     * in a Map) contain the proper keys.
     *
     * Each group of files must have the same keys. If this is not the case
     * an error message is posted giving information which key misses in
     * which file.
     *
     * @param propFiles the property files organized as Map
     */
    private void checkPropertyFileSets(Map<String, Set<File>> propFiles) {
        final Set<Entry<String, Set<File>>> entrySet = propFiles.entrySet();

        for (Entry<String, Set<File>> entry : entrySet) {
            final Set<File> files = entry.getValue();

            if (files.size() >= 2) {
                // build a map from files to the keys they contain
                final Set<Object> keys = Sets.newHashSet();
                final Map<File, Set<Object>> fileMap = Maps.newHashMap();

                for (File file : files) {
                    final Set<Object> fileKeys = loadKeys(file);
                    keys.addAll(fileKeys);
                    fileMap.put(file, fileKeys);
                }

                // check the map for consistency
                compareKeySets(keys, fileMap);
            }
        }
    }
}
