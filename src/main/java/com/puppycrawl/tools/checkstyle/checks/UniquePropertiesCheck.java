///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * <div>
 * Detects duplicated keys in properties files.
 * </div>
 *
 * <p>
 * Rationale: Multiple property keys usually appear after merge or rebase of
 * several branches. While there are no problems in runtime, there can be a confusion
 * due to having different values for the duplicated properties.
 * </p>
 * <ul>
 * <li>
 * Property {@code fileExtensions} - Specify the file extensions of the files to process.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code .properties}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code properties.duplicate.property}
 * </li>
 * <li>
 * {@code unable.open.cause}
 * </li>
 * </ul>
 *
 * @since 5.7
 */
@StatelessCheck
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
    protected void processFiltered(Path file, FileText fileText) {
        final UniqueProperties properties = new UniqueProperties();
        try (InputStream inputStream = Files.newInputStream(file)) { // Changed file.toPath() to file
            properties.load(inputStream);
        }
        catch (IOException ex) {
            log(1, MSG_IO_EXCEPTION_KEY, file.toString(), // Changed file.getPath() to file.toString()
                ex.getLocalizedMessage());
        }

        for (Entry<String, AtomicInteger> duplication : properties
            .getDuplicatedKeys().entrySet()) {
            final String keyName = duplication.getKey();
            final int lineNumber = getLineNumber(fileText, keyName);
            // Number of occurrences is number of duplications + 1
            log(lineNumber, MSG_KEY, keyName, duplication.getValue().get() + 1);
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
     *         file, 1 is returned
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
        // -1 as check seeks for the first duplicate occurrence in file,
        // so it cannot be the last line.
        if (lineNumber > fileText.size() - 1) {
            lineNumber = 1;
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
     * @noinspection ClassExtendsConcreteCollection
     * @noinspectionreason ClassExtendsConcreteCollection - we require custom
     *      {@code put} method to find duplicate keys
     */
    private static final class UniqueProperties extends Properties {

        /** A unique serial version identifier. */
        private static final long serialVersionUID = 1L;
        /**
         * Map, holding duplicated keys and their count. Keys are added here only if they
         * already exist in Properties' inner map.
         */
        private final Map<String, AtomicInteger> duplicatedKeys = new HashMap<>();

        /**
         * Puts the value into properties by the key specified.
         */
        @Override
        public synchronized Object put(Object key, Object value) {
            final Object oldValue = super.put(key, value);
            if (oldValue != null && key instanceof String) {
                final String keyString = (String) key;

                duplicatedKeys.computeIfAbsent(keyString, empty -> new AtomicInteger(0))
                        .incrementAndGet();
            }
            return oldValue;
        }

        /**
         * Retrieves a collections of duplicated properties keys.
         *
         * @return A collection of duplicated keys.
         */
        public Map<String, AtomicInteger> getDuplicatedKeys() {
            return new HashMap<>(duplicatedKeys);
        }

    }

}
