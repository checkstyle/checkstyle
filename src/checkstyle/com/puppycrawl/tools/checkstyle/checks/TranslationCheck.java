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
package com.puppycrawl.tools.checkstyle.checks;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.Defn;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

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
 * @author Alexandra Bunge
 * @author lkuehne
 */
public class TranslationCheck
    extends AbstractFileSetCheck
{
    /** The property files to process. */
    private final List<File> mPropertyFiles = Lists.newArrayList();

    /**
     * Creates a new <code>TranslationCheck</code> instance.
     */
    public TranslationCheck()
    {
        setFileExtensions(new String[]{"properties"});
    }

    @Override
    public void beginProcessing(String aCharset)
    {
        super.beginProcessing(aCharset);
        mPropertyFiles.clear();
    }

    @Override
    protected void processFiltered(File aFile, List<String> aLines)
    {
        mPropertyFiles.add(aFile);
    }

    @Override
    public void finishProcessing()
    {
        super.finishProcessing();
        final Map<String, Set<File>> propFilesMap =
            arrangePropertyFiles(mPropertyFiles);
        checkPropertyFileSets(propFilesMap);
    }

    /**
     * Gets the basename (the unique prefix) of a property file. For example
     * "xyz/messages" is the basename of "xyz/messages.properties",
     * "xyz/messages_de_AT.properties", "xyz/messages_en.properties", etc.
     *
     * @param aFile the file
     * @return the extracted basename
     */
    private static String extractPropertyIdentifier(final File aFile)
    {
        final String filePath = aFile.getPath();
        final int dirNameEnd = filePath.lastIndexOf(File.separatorChar);
        final int baseNameStart = dirNameEnd + 1;
        final int underscoreIdx = filePath.indexOf('_', baseNameStart);
        final int dotIdx = filePath.indexOf('.', baseNameStart);
        final int cutoffIdx = (underscoreIdx != -1) ? underscoreIdx : dotIdx;
        return filePath.substring(0, cutoffIdx);
    }

    /**
     * Arranges a set of property files by their prefix.
     * The method returns a Map object. The filename prefixes
     * work as keys each mapped to a set of files.
     * @param aPropFiles the set of property files
     * @return a Map object which holds the arranged property file sets
     */
    private static Map<String, Set<File>> arrangePropertyFiles(
        List<File> aPropFiles)
    {
        final Map<String, Set<File>> propFileMap = Maps.newHashMap();

        for (final File f : aPropFiles) {
            final String identifier = extractPropertyIdentifier(f);

            Set<File> fileSet = propFileMap.get(identifier);
            if (fileSet == null) {
                fileSet = Sets.newHashSet();
                propFileMap.put(identifier, fileSet);
            }
            fileSet.add(f);
        }
        return propFileMap;
    }

    /**
     * Loads the keys of the specified property file into a set.
     * @param aFile the property file
     * @return a Set object which holds the loaded keys
     */
    private Set<Object> loadKeys(File aFile)
    {
        final Set<Object> keys = Sets.newHashSet();
        InputStream inStream = null;

        try {
            // Load file and properties.
            inStream = new FileInputStream(aFile);
            final Properties props = new Properties();
            props.load(inStream);

            // Gather the keys and put them into a set
            final Enumeration<?> e = props.propertyNames();
            while (e.hasMoreElements()) {
                keys.add(e.nextElement());
            }
        }
        catch (final IOException e) {
            logIOException(e, aFile);
        }
        finally {
            Utils.closeQuietly(inStream);
        }
        return keys;
    }

    /**
     * helper method to log an io exception.
     * @param aEx the exception that occured
     * @param aFile the file that could not be processed
     */
    private void logIOException(IOException aEx, File aFile)
    {
        String[] args = null;
        String key = "general.fileNotFound";
        if (!(aEx instanceof FileNotFoundException)) {
            args = new String[] {aEx.getMessage()};
            key = "general.exception";
        }
        final LocalizedMessage message =
            new LocalizedMessage(
                0,
                Defn.CHECKSTYLE_BUNDLE,
                key,
                args,
                getId(),
                this.getClass(), null);
        final TreeSet<LocalizedMessage> messages = Sets.newTreeSet();
        messages.add(message);
        getMessageDispatcher().fireErrors(aFile.getPath(), messages);
        Utils.getExceptionLogger().debug("IOException occured.", aEx);
    }


    /**
     * Compares the key sets of the given property files (arranged in a map)
     * with the specified key set. All missing keys are reported.
     * @param aKeys the set of keys to compare with
     * @param aFileMap a Map from property files to their key sets
     */
    private void compareKeySets(Set<Object> aKeys,
            Map<File, Set<Object>> aFileMap)
    {
        final Set<Entry<File, Set<Object>>> fls = aFileMap.entrySet();

        for (Entry<File, Set<Object>> entry : fls) {
            final File currentFile = entry.getKey();
            final MessageDispatcher dispatcher = getMessageDispatcher();
            final String path = currentFile.getPath();
            dispatcher.fireFileStarted(path);
            final Set<Object> currentKeys = entry.getValue();

            // Clone the keys so that they are not lost
            final Set<Object> keysClone = Sets.newHashSet(aKeys);
            keysClone.removeAll(currentKeys);

            // Remaining elements in the key set are missing in the current file
            if (!keysClone.isEmpty()) {
                for (Object key : keysClone) {
                    log(0, "translation.missingKey", key);
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
     * @param aPropFiles the property files organized as Map
     */
    private void checkPropertyFileSets(Map<String, Set<File>> aPropFiles)
    {
        final Set<Entry<String, Set<File>>> entrySet = aPropFiles.entrySet();

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
