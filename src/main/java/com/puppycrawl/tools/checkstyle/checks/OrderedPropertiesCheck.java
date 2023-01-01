///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * <p>Detects if keys in properties files are in correct order.</p>
 * <p>
 *   Rationale: Sorted properties make it easy for people to find required properties by name
 *   in file. This makes it easier to merge. While there are no problems at runtime.
 *   This check is valuable only on files with string resources where order of lines
 *   does not matter at all, but this can be improved.
 *   E.g.: checkstyle/src/main/resources/com/puppycrawl/tools/checkstyle/messages.properties
 *   You may suppress warnings of this check for files that have a logical structure like
 *   build files or log4j configuration files. See SuppressionFilter.
 *   {@code
 *   &lt;suppress checks="OrderedProperties"
 *     files="log4j.properties|ResourceBundle/Bug.*.properties|logging.properties"/&gt;
 *   }
 * </p>
 * <p>Known limitation: The key should not contain a newline.
 * The string compare will work, but not the line number reporting.</p>
 * <ul><li>
 * Property {@code fileExtensions} - Specify file type extension of the files to check.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code .properties}.
 * </li></ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>&lt;module name="OrderedProperties"/&gt;</pre>
 * <p>Example properties file:</p>
 * <pre>
 * A =65
 * a =97
 * key =107 than nothing
 * key.sub =k is 107 and dot is 46
 * key.png =value - violation
 * </pre>
 * <p>We check order of key's only. Here we would like to use a Locale independent
 * order mechanism and binary order. The order is case-insensitive and ascending.</p>
 * <ul>
 *   <li>The capital 'A' is on 65 and the lowercase 'a' is on position 97 on the ascii table.</li>
 *   <li>Key and key.sub are in correct order here, because only keys are relevant.
 *   Therefore, on line 5 you have only "key" and nothing behind.
 *   On line 6 you have "key." The dot is on position 46 which is higher than nothing.
 *   key.png will be reported as violation because "png" comes before "sub".</li>
 * </ul>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code properties.notSorted.property}
 * </li>
 * <li>
 * {@code unable.open.cause}
 * </li>
 * </ul>
 *
 * @since 8.22
 */
@StatelessCheck
public class OrderedPropertiesCheck extends AbstractFileSetCheck {

    /**
     * Localization key for check violation.
     */
    public static final String MSG_KEY = "properties.notSorted.property";
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
    public OrderedPropertiesCheck() {
        setFileExtensions("properties");
    }

    /**
     * Processes the file and check order.
     *
     * @param file the file to be processed
     * @param fileText the contents of the file.
     */
    @Override
    protected void processFiltered(File file, FileText fileText) {
        final SequencedProperties properties = new SequencedProperties();
        try (InputStream inputStream = Files.newInputStream(file.toPath())) {
            properties.load(inputStream);
        }
        catch (IOException | IllegalArgumentException ex) {
            log(1, MSG_IO_EXCEPTION_KEY, file.getPath(), ex.getLocalizedMessage());
        }

        String previousProp = "";
        int startLineNo = 0;

        final Iterator<Object> propertyIterator = properties.keys().asIterator();

        while (propertyIterator.hasNext()) {

            final String propKey = (String) propertyIterator.next();

            if (String.CASE_INSENSITIVE_ORDER.compare(previousProp, propKey) > 0) {

                final int lineNo = getLineNumber(startLineNo, fileText, previousProp, propKey);
                log(lineNo + 1, MSG_KEY, propKey, previousProp);
                // start searching at position of the last reported validation
                startLineNo = lineNo;
            }

            previousProp = propKey;
        }
    }

    /**
     * Method returns the index number where the key is detected (starting at 0).
     * To assure that we get the correct line it starts at the point
     * of the last occurrence.
     * Also, the previousProp should be in file before propKey.
     *
     * @param startLineNo start searching at line
     * @param fileText {@link FileText} object contains the lines to process
     * @param previousProp key name found last iteration, works only if valid
     * @param propKey key name to look for
     * @return index number of first occurrence. If no key found in properties file, 0 is returned
     */
    private static int getLineNumber(int startLineNo, FileText fileText,
                                     String previousProp, String propKey) {
        final int indexOfPreviousProp = getIndex(startLineNo, fileText, previousProp);
        return getIndex(indexOfPreviousProp, fileText, propKey);
    }

    /**
     * Inner method to get the index number of the position of keyName.
     *
     * @param startLineNo start searching at line
     * @param fileText {@link FileText} object contains the lines to process
     * @param keyName key name to look for
     * @return index number of first occurrence. If no key found in properties file, 0 is returned
     */
    private static int getIndex(int startLineNo, FileText fileText, String keyName) {
        final Pattern keyPattern = getKeyPattern(keyName);
        int indexNumber = 0;
        final Matcher matcher = keyPattern.matcher("");
        for (int index = startLineNo; index < fileText.size(); index++) {
            final String line = fileText.get(index);
            matcher.reset(line);
            if (matcher.matches()) {
                indexNumber = index;
                break;
            }
        }
        return indexNumber;
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
                .replaceAll(Matcher.quoteReplacement("\\\\ ")) + "[\\s:=].*";
        return Pattern.compile(keyPatternString);
    }

    /**
     * Private property implementation that keeps order of properties like in file.
     *
     * @noinspection ClassExtendsConcreteCollection
     * @noinspectionreason ClassExtendsConcreteCollection - we require order from
     *      file to be maintained by {@code put} method
     */
    private static class SequencedProperties extends Properties {

        /** A unique serial version identifier. */
        private static final long serialVersionUID = 1L;

        /**
         * Holding the keys in the same order as in the file.
         */
        private final List<Object> keyList = new ArrayList<>();

        /**
         * Returns a copy of the keys.
         */
        @Override
        public Enumeration<Object> keys() {
            return Collections.enumeration(keyList);
        }

        /**
         * Puts the value into list by its key.
         *
         * @param key the hashtable key
         * @param value the value
         * @return the previous value of the specified key in this hashtable,
         *      or null if it did not have one
         * @throws NullPointerException - if the key or value is null
         */
        @Override
        public synchronized Object put(Object key, Object value) {
            keyList.add(key);

            return super.put(key, value);
        }
    }
}
