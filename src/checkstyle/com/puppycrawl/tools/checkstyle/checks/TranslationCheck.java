////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
import java.io.FileFilter;
import java.util.Set;
import java.util.HashSet;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Enumeration;

import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.Defn;

/**
 * The TranslationCheck class helps to ensure the correct translation of code by
 * checking property files for consistency regarding their keys.
 * Two property files describing one and the same context are consistent if they
 * contain the same keys.
 *
 * @author Alexandra Bunge
 * @author lkuehne
 */
public class TranslationCheck extends AbstractFileSetCheck
{
    /**
     * Filter for properties files.
     */
    private static class PropertyFileFilter implements FileFilter
    {
        /** @see FileFilter */
        public boolean accept(File aPathname)
        {
            return aPathname.getPath().endsWith(".properties");
        }
    }

    /**
     * Gets the basename (the unique prefix) of a property file. For example
     * "messages" is the basename of "messages.properties",
     * "messages_de_AT.properties", "messages_en.properties", etc.
     *
     * @param aFile the file
     * @return the extracted basename
     */
    private static String extractBaseName(File aFile)
    {
        String fileName = aFile.getPath();
        int k = fileName.indexOf("_");
        if (k != -1) {
            return fileName.substring(0, k);
        }
        else {
            return fileName.substring(0, fileName.indexOf("."));
        }
    }


    /**
     * Arranges a set of property files by their prefix.
     * The method returns a Map object. The filename prefixes
     * work as keys each mapped to a set of files.
     * @param aPropFiles the set of property files
     * @return a Map object which holds the arranged property file sets
     */
    private static Map arrangePropertyFiles(Set aPropFiles)
    {
        Map propFileMap = new HashMap();

        for (Iterator iterator = aPropFiles.iterator(); iterator.hasNext();) {
            File file = (File) iterator.next();
            String baseName = extractBaseName(file);

            Set fileSet = (Set) propFileMap.get(baseName);
            if (fileSet == null) {
                fileSet = new HashSet();
                propFileMap.put(baseName, fileSet);
            }
            fileSet.add(file);
        }
        return propFileMap;
    }


    /**
     * Searches for all files with suffix ".properties" in the
     * given set of directories.
     * @param aDirs the file set to search in
     * @return the property files
     */
    private static Set getPropertyFiles(Set aDirs)
    {
        Set propFiles = new HashSet();
        final PropertyFileFilter filter = new PropertyFileFilter();

        for (Iterator iterator = aDirs.iterator(); iterator.hasNext();) {
            File dir = (File) iterator.next();
            File[] propertyFiles = dir.listFiles(filter);
            for (int j = 0; j < propertyFiles.length; j++) {
                File propertyFile = propertyFiles[j];
                propFiles.add(propertyFile);
            }
        }
        return propFiles;
    }

    /**
     * Loads the keys of the specified property file into a set.
     * @param aFile the property file
     * @return a Set object which holds the loaded keys
     */
    private Set loadKeys(File aFile)
    {
        InputStream inputStream = null;
        Set keys = new HashSet();

        try {
            // Load file and properties.
            inputStream = new FileInputStream(aFile);
            Properties properties = new Properties();
            properties.load(inputStream);

            // Gather the keys and put them into a set
            Enumeration e = properties.propertyNames();
            while (e.hasMoreElements()) {
                keys.add(e.nextElement());
            }
        }
        catch (IOException e) {
            logIOException(e, aFile);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                logIOException(e, aFile);
            }
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
        LocalizedMessage message =
                new LocalizedMessage(0, Defn.CHECKSTYLE_BUNDLE, key, args);
        LocalizedMessage[] messages = new LocalizedMessage[]{message};
        getMessageDispatcher().fireErrors(aFile.getPath(), messages);
    }


    /**
     * Compares the key sets of the given property files (arranged in a map)
     * with the specified key set. All missing keys are reported.
     * @param aKeys the set of keys to compare with
     * @param aFileMap a Map from property files to their key sets
     */
    private void compareKeySets(Set aKeys, Map aFileMap)
    {
        Set fls = aFileMap.keySet();

        for (Iterator iter = fls.iterator(); iter.hasNext();) {
            File currentFile = (File) iter.next();
            final MessageDispatcher dispatcher = getMessageDispatcher();
            final String path = currentFile.getPath();
            dispatcher.fireFileStarted(path);
            Set currentKeys = (Set) aFileMap.get(currentFile);

            // Clone the keys so that they are not lost
            Set keysClone = new HashSet(aKeys);
            keysClone.removeAll(currentKeys);

            // Remaining elements in the key set are missing in the current file
            if (!keysClone.isEmpty()) {
                for (Iterator it = keysClone.iterator(); it.hasNext();) {
                    Object[] key = new Object[]{it.next()};
                    LocalizedMessage[] errors = new LocalizedMessage[1];
                    final String bundle =
                        getClass().getPackage().getName() + ".messages";
                    errors[0] = new LocalizedMessage(
                            0, bundle, "translation.missingKey", key);
                    getMessageDispatcher().fireErrors(path, errors);
                }
            }
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
    private void checkPropertyFileSets(Map aPropFiles)
    {
        Set keySet = aPropFiles.keySet();

        for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
            String baseName = (String) iterator.next();
            Set files = (Set) aPropFiles.get(baseName);

            if (files.size() >= 2) {
                // build a map from files to the keys they contain
                Set keys = new HashSet();
                Map fileMap = new HashMap();

                for (Iterator iter = files.iterator(); iter.hasNext();) {
                    File file = (File) iter.next();
                    Set fileKeys = loadKeys(file);
                    keys.addAll(fileKeys);
                    fileMap.put(file, fileKeys);
                }

                // check the map for consistency
                compareKeySets(keys, fileMap);
            }
        }
    }


    /**
     * This method searches for property files in the specified file array
     * and checks whether the key usage is consistent.
     *
     * Two property files which have the same prefix should use the same
     * keys. If this is not the case the missing keys are reported.
     *
     * @see com.puppycrawl.tools.checkstyle.api.FileSetCheck
     */
    public void process(File[] aFiles)
    {
        Set dirs = getParentDirs(aFiles);
        Set propertyFiles = getPropertyFiles(dirs);
        final Map propFilesMap = arrangePropertyFiles(propertyFiles);
        checkPropertyFileSets(propFilesMap);
    }



}
