////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * Checks the uniqueness of property keys (left from equal sign) in the
 * properties file.
 *
 * @author Pavel Baranchikov
 */
public class UniquePropertiesCheck extends AbstractFileSetCheck {

    /**
     * Localization key for check violation.
     */
    public static final String MSG_KEY = "properties.duplicate.property";
    /**
     * Localization key for IO exception occurred on file open.
     */
    public static final String MSG_IO_EXCEPTION_KEY = "unable.open.cause";

    /**
     * Pattern matching single space.
     */
    private static final Pattern SPACE_PATTERN = Pattern.compile(" ");

    /**
     * Construct the check with default values.
     */
    public UniquePropertiesCheck() {
        setFileExtensions("properties");
    }

    @Override
    protected void processFiltered(File file, FileText fileText) {
        final UniqueProperties properties = new UniqueProperties();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
        }
        catch (IOException ex) {
            log(0, MSG_IO_EXCEPTION_KEY, file.getPath(),
                    ex.getLocalizedMessage());
        }
        finally {
            Closeables.closeQuietly(fileInputStream);
        }

        for (Entry<String> duplication : properties
                .getDuplicatedKeys().entrySet()) {
            final String keyName = duplication.getElement();
            final int lineNumber = getLineNumber(fileText, keyName);
            // Number of occurrences is number of duplications + 1
            log(lineNumber, MSG_KEY, keyName, duplication.getCount() + 1);
        }
    }

    /**
     * Method returns line number the key is detected in the checked properties
     * files first.
     *
     * @param fileText
     *            {@link FileText} object contains the lines to process
     * @param keyName
     *            key name to look for
     * @return line number of first occurrence. If no key found in properties
     *         file, 0 is returned
     */
    private static int getLineNumber(FileText fileText, String keyName) {
        final Pattern keyPattern = getKeyPattern(keyName);
        int lineNumber = 1;
        final Matcher matcher = keyPattern.matcher("");
        for (int index = 0; index < fileText.size(); index++) {
            final String line = fileText.get(index);
            matcher.reset(line);
            if (matcher.matches()) {
                break;
            }
            ++lineNumber;
        }
        if (lineNumber > fileText.size()) {
            lineNumber = 0;
        }
        return lineNumber;
    }

    /**
     * Method returns regular expression pattern given key name.
     *
     * @param keyName
     *            key name to look for
     * @return regular expression pattern given key name
     */
    private static Pattern getKeyPattern(String keyName) {
        final String keyPatternString = "^" + SPACE_PATTERN.matcher(keyName)
                .replaceAll(Matcher.quoteReplacement("\\\\ ")) + "[\\s:=].*$";
        return Pattern.compile(keyPatternString);
    }

    /**
     * Properties subclass to store duplicated property keys in a separate map.
     *
     * @author Pavel Baranchikov
     * @noinspection ClassExtendsConcreteCollection, SerializableHasSerializationMethods
     */
    private static class UniqueProperties extends Properties {
        private static final long serialVersionUID = 1L;
        /**
         * Multiset, holding duplicated keys. Keys are added here only if they
         * already exist in Properties' inner map.
         */
        private final Multiset<String> duplicatedKeys = HashMultiset
                .create();

        /**
         * Puts the value into properties by the key specified.
         * @noinspection UseOfPropertiesAsHashtable
         */
        @Override
        public synchronized Object put(Object key, Object value) {
            final Object oldValue = super.put(key, value);
            if (oldValue != null && key instanceof String) {
                final String keyString = (String) key;
                duplicatedKeys.add(keyString);
            }
            return oldValue;
        }

        /**
         * Retrieves a collections of duplicated properties keys.
         *
         * @return A collection of duplicated keys.
         */
        public Multiset<String> getDuplicatedKeys() {
            return ImmutableMultiset.copyOf(duplicatedKeys);
        }
    }
}
