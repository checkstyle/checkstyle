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
        public boolean accept(File pathname)
        {
            return pathname.getPath().endsWith(".properties");
        }
    }

    /**
     * Gets the basename (the unique prefix) of a property file. For example
     * "messages" is the basename of "messages.properties",
     * "messages_de_AT.properties", "messages_en.properties", etc.
     *
     * @param fileName the file name
     * @return the extracted basename
     */
    private static String extractBaseName(String fileName)
    {
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
     * @param propFiles the set of property files
     * @return a Map object which holds the arranged property file sets
     */
    private static Map arrangePropertyFiles(Set propFiles)
    {
        Map propFileMap = new HashMap();

        for (Iterator iterator = propFiles.iterator(); iterator.hasNext();) {
            File file = (File) iterator.next();
            String fileName = file.getName();
            String baseName = extractBaseName(fileName);

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
     * @param dirs the file set to search in
     * @return the property files
     */
    private static Set getPropertyFiles(Set dirs)
    {
        Set propFiles = new HashSet();
        final PropertyFileFilter filter = new PropertyFileFilter();

        for (Iterator iterator = dirs.iterator(); iterator.hasNext();) {
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
     * @param file the property file
     * @return a Set object which holds the loaded keys
     */
    private static Set loadKeys(File file)
    {
        InputStream inputStream = null;
        Set keys = new HashSet();

        try {
            // Load file and properties.
            inputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(inputStream);

            // Gather the keys and put them into a set
            Enumeration e = properties.propertyNames();
            while (e.hasMoreElements()) {
                keys.add(e.nextElement());
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(
                    "The file " + file.getName() + " could not be found!");
        }
        catch (IOException e) {
            System.out.println("IOException occured");
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                System.out.println("IOException occured");
            }
        }
        return keys;
    }


    /**
     * Compares the key sets of the given property files (arranged in a map)
     * with the specified key set. All missing keys are reported.
     * @param keys the set of keys to compare with
     * @param fileMap a Map from property files to their key sets
     * @return the number of inconsistencies detected
     */
    private static int compareKeySets(Set keys, Map fileMap)
    {
        Set fls = fileMap.keySet();
        int res = 0;

        for (Iterator iter = fls.iterator(); iter.hasNext();) {
            File currentFile = (File) iter.next();
            // TODO: fire file started
            Set currentKeys = (Set) fileMap.get(currentFile);

            // Clone the keys so that they are not lost
            Set keysClone = new HashSet(keys);
            keysClone.removeAll(currentKeys);

            // Remaining elements in the key set are missing in the current file
            if (!keysClone.isEmpty()) {
                for (Iterator it = keysClone.iterator(); it.hasNext();) {
                    // TODO: fire errors to reporting system
                    System.out.println(currentFile.getPath()
                            + ": key \"" + (String) it.next() + "\" missing");
                    res++;
                }
            }
            // TODO: fire file finished
        }
        return res;
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
     * @return the number of inconsistencies detected
     */
    private static int checkPropertyFileSets(Map propFiles)
    {
        int res = 0;
        Set keySet = propFiles.keySet();

        for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
            String baseName = (String) iterator.next();
            Set files = (Set) propFiles.get(baseName);

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
                res = res + compareKeySets(keys, fileMap);
            }
        }
        return res;
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
    public int process(File[] files)
    {
        Set dirs = getParentDirs(files);
        Set propertyFiles = getPropertyFiles(dirs);
        final Map propFilesMap = arrangePropertyFiles(propertyFiles);
        return checkPropertyFileSets(propFilesMap);
    }



}
