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
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Splitter;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
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
 * Check has the following properties:
 *
 * <p><b>basenameSeparator</b> which allows setting separator in file names,
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
 *
 * <p><b>requiredTranslations</b> which allows to specify language codes of
 * required translations which must exist in project. The check looks only for
 * messages bundles which names contain the word 'messages'.
 * Language code is composed of the lowercase, two-letter codes as defined by
 * <a href="http://www.fatbellyman.com/webstuff/language_codes_639-1/">ISO 639-1</a>.
 * Default value is <b>empty String Set</b> which means that only the existence of
 * default translation is checked.
 * Note, if you specify language codes (or just one language code) of required translations
 * the check will also check for existence of default translation files in project.
 * <br>
 * @author Alexandra Bunge
 * @author lkuehne
 * @author Andrei Selkin
 */
public class TranslationCheck
    extends AbstractFileSetCheck {

    /**
     * A key is pointing to the warning message text for missing key
     * in "messages.properties" file.
     */
    public static final String MSG_KEY = "translation.missingKey";

    /**
     * A key is pointing to the warning message text for missing translation file
     * in "messages.properties" file.
     */
    public static final String MSG_KEY_MISSING_TRANSLATION_FILE =
        "translation.missingTranslationFile";

    /** Logger for TranslationCheck. */
    private static final Log LOG = LogFactory.getLog(TranslationCheck.class);

    /** The property files to process. */
    private final List<File> propertyFiles = Lists.newArrayList();

    /** The separator string used to separate translation files. */
    private String basenameSeparator;

    /**
     * Language codes of required translations for the check (de, pt, ja, etc).
     */
    private SortedSet<String> requiredTranslations = ImmutableSortedSet.of();

    /**
     * Creates a new {@code TranslationCheck} instance.
     */
    public TranslationCheck() {
        setFileExtensions("properties");
        basenameSeparator = "_";
    }

    /**
     * Sets language codes of required translations for the check.
     * @param translationCodes a comma separated list of language codes.
     */
    public void setRequiredTranslations(String translationCodes) {
        requiredTranslations = Sets.newTreeSet(Splitter.on(',')
            .trimResults().omitEmptyStrings().split(translationCodes));
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
        final SetMultimap<String, File> propFilesMap =
            arrangePropertyFiles(propertyFiles, basenameSeparator);
        checkExistenceOfTranslations(propFilesMap);
        checkPropertyFileSets(propFilesMap);
    }

    /**
     * Checks existence of translation files (arranged in a map)
     * for each resource bundle in project.
     * @param translations the translation files bundles organized as Map.
     */
    private void checkExistenceOfTranslations(SetMultimap<String, File> translations) {
        for (String fullyQualifiedBundleName : translations.keySet()) {
            final String bundleBaseName = extractName(fullyQualifiedBundleName);
            if (bundleBaseName.contains("messages")) {
                final Set<File> filesInBundle = translations.get(fullyQualifiedBundleName);
                checkExistenceOfDefaultTranslation(filesInBundle);
                checkExistenceOfRequiredTranslations(filesInBundle);
            }
        }
    }

    /**
     * Checks an existence of default translation file in
     * a set of files in resource bundle. The name of this file
     * begins with the full name of the resource bundle and ends
     * with the extension suffix.
     * @param filesInResourceBundle a set of files in resource bundle.
     */
    private void checkExistenceOfDefaultTranslation(Set<File> filesInResourceBundle) {
        final String fullBundleName = getFullBundleName(filesInResourceBundle);
        final String extension = getFileExtensions()[0];
        final String defaultTranslationFileName = fullBundleName + extension;

        final boolean missing = isMissing(defaultTranslationFileName, filesInResourceBundle);
        if (missing) {
            logMissingTranslation(defaultTranslationFileName);
        }
    }

    /**
     * Checks existence of translation files in a set of files
     * in resource bundle. If there is no translation file
     * with required language code, there will be a violation.
     * The name of translation file begins with the full name
     * of resource bundle which is followed by '_' and language code,
     * it ends with the extension suffix.
     * @param filesInResourceBundle a set of files in resource bundle.
     */
    private void checkExistenceOfRequiredTranslations(Set<File> filesInResourceBundle) {
        final String fullBundleName = getFullBundleName(filesInResourceBundle);
        final String extension = getFileExtensions()[0];

        for (String languageCode : requiredTranslations) {
            final String translationFileName =
                fullBundleName + '_' + languageCode + extension;

            final boolean missing = isMissing(translationFileName, filesInResourceBundle);
            if (missing) {
                final String missingTranslationFileName =
                    formMissingTranslationName(fullBundleName, languageCode);
                logMissingTranslation(missingTranslationFileName);
            }
        }
    }

    /**
     * Gets full name of resource bundle.
     * Full name of resource bundle consists of bundle path and
     * full base name.
     * @param filesInResourceBundle a set of files in resource bundle.
     * @return full name of resource bundle.
     */
    private String getFullBundleName(Set<File> filesInResourceBundle) {
        final String fullBundleName;

        final File firstTranslationFile = Collections.min(filesInResourceBundle);
        final String translationPath = firstTranslationFile.getPath();
        final String extension = getFileExtensions()[0];

        final Pattern pattern = Pattern.compile("^.+_[a-z]{2}"
            + extension + "$");
        final Matcher matcher = pattern.matcher(translationPath);
        if (matcher.matches()) {
            fullBundleName = translationPath
                .substring(0, translationPath.lastIndexOf('_'));
        }
        else {
            fullBundleName = translationPath
                .substring(0, translationPath.lastIndexOf('.'));
        }
        return fullBundleName;
    }

    /**
     * Checks whether file is missing in resource bundle.
     * @param fileName file name.
     * @param filesInResourceBundle a set of files in resource bundle.
     * @return true if file is missing.
     */
    private static boolean isMissing(String fileName, Set<File> filesInResourceBundle) {
        boolean missing = false;
        for (File file : filesInResourceBundle) {
            final String currentFileName = file.getPath();
            missing = !currentFileName.equals(fileName);
            if (!missing) {
                break;
            }
        }
        return missing;
    }

    /**
     * Forms a name of translation file which is missing.
     * @param fullBundleName full bundle name.
     * @param languageCode language code.
     * @return name of translation file which is missing.
     */
    private String formMissingTranslationName(String fullBundleName, String languageCode) {
        final String extension = getFileExtensions()[0];
        return String.format(Locale.ROOT, "%s_%s%s", fullBundleName, languageCode, extension);
    }

    /**
     * Logs that translation file is missing.
     * @param fullyQualifiedFileName fully qualified file name.
     */
    private void logMissingTranslation(String fullyQualifiedFileName) {
        final String filePath = extractPath(fullyQualifiedFileName);

        final MessageDispatcher dispatcher = getMessageDispatcher();
        dispatcher.fireFileStarted(filePath);

        log(0, MSG_KEY_MISSING_TRANSLATION_FILE, extractName(fullyQualifiedFileName));

        fireErrors(filePath);
        dispatcher.fireFileFinished(filePath);
    }

    /**
     * Extracts path from fully qualified file name.
     * @param fullyQualifiedFileName fully qualified file name.
     * @return file path.
     */
    private static String extractPath(String fullyQualifiedFileName) {
        return fullyQualifiedFileName
            .substring(0, fullyQualifiedFileName.lastIndexOf(File.separator));
    }

    /**
     * Extracts short file name from fully qualified file name.
     * @param fullyQualifiedFileName fully qualified file name.
     * @return short file name.
     */
    private static String extractName(String fullyQualifiedFileName) {
        return fullyQualifiedFileName
            .substring(fullyQualifiedFileName.lastIndexOf(File.separator) + 1);
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
    private static String extractPropertyIdentifier(File file, String basenameSeparator) {
        final String filePath = file.getPath();
        final int dirNameEnd = filePath.lastIndexOf(File.separatorChar);
        final int baseNameStart = dirNameEnd + 1;
        final int underscoreIdx = filePath.indexOf(basenameSeparator,
            baseNameStart);
        final int dotIdx = filePath.indexOf('.', baseNameStart);
        final int cutoffIdx;

        if (underscoreIdx == -1) {
            cutoffIdx = dotIdx;
        }
        else {
            cutoffIdx = underscoreIdx;
        }
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
    private static SetMultimap<String, File> arrangePropertyFiles(
        List<File> propFiles, String basenameSeparator) {
        final SetMultimap<String, File> propFileMap = HashMultimap.create();

        for (final File file : propFiles) {
            final String identifier = extractPropertyIdentifier(file,
                basenameSeparator);

            final Set<File> fileSet = propFileMap.get(identifier);
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
            final Enumeration<?> element = props.propertyNames();
            while (element.hasMoreElements()) {
                keys.add(element.nextElement());
            }
        }
        catch (final IOException ex) {
            logIoException(ex, file);
        }
        finally {
            Closeables.closeQuietly(inStream);
        }
        return keys;
    }

    /**
     * Helper method to log an io exception.
     * @param exception the exception that occurred
     * @param file the file that could not be processed
     */
    private void logIoException(IOException exception, File file) {
        String[] args = null;
        String key = "general.fileNotFound";
        if (!(exception instanceof FileNotFoundException)) {
            args = new String[] {exception.getMessage()};
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
        LOG.debug("IOException occurred.", exception);
    }

    /**
     * Compares the key sets of the given property files (arranged in a map)
     * with the specified key set. All missing keys are reported.
     * @param keys the set of keys to compare with
     * @param fileMap a Map from property files to their key sets
     */
    private void compareKeySets(Set<Object> keys,
            SetMultimap<File, Object> fileMap) {

        for (File currentFile : fileMap.keySet()) {
            final MessageDispatcher dispatcher = getMessageDispatcher();
            final String path = currentFile.getPath();
            dispatcher.fireFileStarted(path);
            final Set<Object> currentKeys = fileMap.get(currentFile);

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
     * <p>Each group of files must have the same keys. If this is not the case
     * an error message is posted giving information which key misses in
     * which file.
     *
     * @param propFiles the property files organized as Map
     */
    private void checkPropertyFileSets(SetMultimap<String, File> propFiles) {

        for (String key : propFiles.keySet()) {
            final Set<File> files = propFiles.get(key);

            if (files.size() >= 2) {
                // build a map from files to the keys they contain
                final Set<Object> keys = Sets.newHashSet();
                final SetMultimap<File, Object> fileMap = HashMultimap.create();

                for (File file : files) {
                    final Set<Object> fileKeys = loadKeys(file);
                    keys.addAll(fileKeys);
                    fileMap.putAll(file, fileKeys);
                }

                // check the map for consistency
                compareKeySets(keys, fileMap);
            }
        }
    }
}
