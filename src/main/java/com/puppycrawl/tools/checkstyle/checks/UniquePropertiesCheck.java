////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;

/**
 * Checks the uniqueness of property keys (left from equal sign) in the
 * properties file.
 *
 * @author Pavel Baranchikov
 */
public class UniquePropertiesCheck extends AbstractFileSetCheck
{

    /**
     * Localization key for check violation.
     */
    public static final String MSG_KEY = "properties.duplicateproperty";
    /**
     * Localization key for IO exception occurred on file open.
     */
    public static final String IO_EXCEPTION_KEY = "unable.open.cause";

    /**
     * Construct the check with default values.
     */
    public UniquePropertiesCheck()
    {
        super.setFileExtensions(new String[]
        {"properties"});
    }

    @Override
    protected void processFiltered(File aFile, List<String> aLines)
    {
        final UniqueProperties properties = new UniqueProperties();

        try {
            // As file is already read, there should not be any exceptions.
            properties.load(new FileInputStream(aFile));
        }
        catch (IOException e) {
            log(0, IO_EXCEPTION_KEY, aFile.getPath(),
                    e.getLocalizedMessage());
        }

        for (Entry<String> duplication : properties
                .getDuplicatedStrings().entrySet())
        {
            final String keyName = duplication.getElement();
            final int lineNumber = getLineNumber(aLines, keyName);
            // Number of occurrences is number of duplications + 1
            log(lineNumber, MSG_KEY, keyName, duplication.getCount() + 1);
        }
    }

    /**
     * Method returns line number the key is detected in the checked properties
     * files first.
     *
     * @param aLines
     *            properties file lines list
     * @param aKeyNane
     *            key name to look for
     * @return line number of first occurrence. If no key found in properties
     *         file, 0 is returned
     */
    protected int getLineNumber(List<String> aLines, String aKeyNane)
    {
        final String keyPatternString =
                "^" + aKeyNane.replace(" ", "\\\\ ") + "[\\s:=].*$";
        final Pattern keyPattern = Pattern.compile(keyPatternString);
        int lineNumber = 1;
        final Matcher matcher = keyPattern.matcher("");
        for (String line : aLines) {
            matcher.reset(line);
            if (matcher.matches()) {
                break;
            }
            ++lineNumber;
        }
        if (lineNumber > aLines.size()) {
            lineNumber = 0;
        }
        return lineNumber;
    }

    /**
     * Properties subclass to store duplicated property keys in a separate map.
     *
     * @author Pavel Baranchikov
     */
    private static class UniqueProperties extends Properties
    {
        /**
         * Default serial version id.
         */
        private static final long serialVersionUID = 1L;
        /**
         * Multiset, holding duplicated keys. Keys are added here only if they
         * already exist in Properties' inner map.
         */
        private final Multiset<String> mDuplicatedStrings = HashMultiset
                .create();

        @Override
        public synchronized Object put(Object aKey, Object aValue)
        {
            final Object oldValue = super.put(aKey, aValue);
            if (oldValue != null && aKey instanceof String) {
                final String keyString = (String) aKey;
                mDuplicatedStrings.add(keyString);
            }
            return oldValue;
        }

        public Multiset<String> getDuplicatedStrings()
        {
            return mDuplicatedStrings;
        }
    }
}
