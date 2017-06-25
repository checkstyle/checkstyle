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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * The TranslationCheck class helps to ensure the correct translation of code by
 * checking locale-specific resource files for consistency regarding their keys.
 * Two locale-specific resource files describing one and the same context are consistent if they
 * contain the same keys. TranslationCheck also can check an existence of required translations
 * which must exist in project, if 'requiredTranslations' option is used.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="Translation"/&gt;
 * </pre>
 * Check has the following options:
 *
 * <p><b>baseName</b> - a base name regexp for resource bundles which contain message resources. It
 * helps the check to distinguish config and localization resources. Default value is
 * <b>^messages.*$</b>
 * <p>An example of how to configure the check to validate only bundles which base names start with
 * "ButtonLabels":
 * </p>
 * <pre>
 * &lt;module name="Translation"&gt;
 *     &lt;property name="baseName" value="^ButtonLabels.*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>To configure the check to check only files which have '.properties' and '.translations'
 * extensions:
 * </p>
 * <pre>
 * &lt;module name="Translation"&gt;
 *     &lt;property name="fileExtensions" value="properties, translations"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p><b>requiredTranslations</b> which allows to specify language codes of required translations
 * which must exist in project. Language code is composed of the lowercase, two-letter codes as
 * defined by <a href="https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes">ISO 639-1</a>.
 * Default value is <b>empty String Set</b> which means that only the existence of
 * default translation is checked. Note, if you specify language codes (or just one language
 * code) of required translations the check will also check for existence of default translation
 * files in project. ATTENTION: the check will perform the validation of ISO codes if the option
 * is used. So, if you specify, for example, "mm" for language code, TranslationCheck will rise
 * violation that the language code is incorrect.
 * <br>
 *
 * @author Alexandra Bunge
 * @author lkuehne
 * @author Andrei Selkin
 */
public class TranslationCheck extends AbstractFileSetCheck {

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

    /** Resource bundle which contains messages for TranslationCheck. */
    private static final String TRANSLATION_BUNDLE =
        "com.puppycrawl.tools.checkstyle.checks.messages";

    /**
     * A key is pointing to the warning message text for wrong language code
     * in "messages.properties" file.
     */
    private static final String WRONG_LANGUAGE_CODE_KEY = "translation.wrongLanguageCode";

    /** Logger for TranslationCheck. */
    private static final Log LOG = LogFactory.getLog(TranslationCheck.class);

    /**
     * Regexp string for default translation files.
     * For example, messages.properties.
     */
    private static final String DEFAULT_TRANSLATION_REGEXP = "^.+\\..+$";

    /**
     * Regexp pattern for bundles names wich end with language code, followed by country code and
     * variant suffix. For example, messages_es_ES_UNIX.properties.
     */
    private static final Pattern LANGUAGE_COUNTRY_VARIANT_PATTERN =
        CommonUtils.createPattern("^.+\\_[a-z]{2}\\_[A-Z]{2}\\_[A-Za-z]+\\..+$");
    /**
     * Regexp pattern for bundles names wich end with language code, followed by country code
     * suffix. For example, messages_es_ES.properties.
     */
    private static final Pattern LANGUAGE_COUNTRY_PATTERN =
        CommonUtils.createPattern("^.+\\_[a-z]{2}\\_[A-Z]{2}\\..+$");
    /**
     * Regexp pattern for bundles names wich end with language code suffix.
     * For example, messages_es.properties.
     */
    private static final Pattern LANGUAGE_PATTERN =
        CommonUtils.createPattern("^.+\\_[a-z]{2}\\..+$");

    /** File name format for default translation. */
    private static final String DEFAULT_TRANSLATION_FILE_NAME_FORMATTER = "%s.%s";
    /** File name format with language code. */
    private static final String FILE_NAME_WITH_LANGUAGE_CODE_FORMATTER = "%s_%s.%s";

    /** Formatting string to form regexp to validate required translations file names. */
    private static final String REGEXP_FORMAT_TO_CHECK_REQUIRED_TRANSLATIONS =
        "^%1$s\\_%2$s(\\_[A-Z]{2})?\\.%3$s$|^%1$s\\_%2$s\\_[A-Z]{2}\\_[A-Za-z]+\\.%3$s$";
    /** Formatting string to form regexp to validate default translations file names. */
    private static final String REGEXP_FORMAT_TO_CHECK_DEFAULT_TRANSLATIONS = "^%s\\.%s$";

    /** The files to process. */
    private final Set<File> filesToProcess = new HashSet<>();

    /** The base name regexp pattern. */
    private Pattern baseName;

    /**
     * Language codes of required translations for the check (de, pt, ja, etc).
     */
    private Set<String> requiredTranslations = new HashSet<>();

    /**
     * Creates a new {@code TranslationCheck} instance.
     */
    public TranslationCheck() {
        setFileExtensions("properties");
        baseName = CommonUtils.createPattern("^messages.*$");
    }

    /**
     * Sets the base name regexp pattern.
     * @param baseName base name regexp.
     */
    public void setBaseName(Pattern baseName) {
        this.baseName = baseName;
    }

    /**
     * Sets language codes of required translations for the check.
     * @param translationCodes a comma separated list of language codes.
     */
    public void setRequiredTranslations(String... translationCodes) {
        requiredTranslations = Arrays.stream(translationCodes).collect(Collectors.toSet());
        validateUserSpecifiedLanguageCodes(requiredTranslations);
    }

    /**
     * Validates the correctness of user specified language codes for the check.
     * @param languageCodes user specified language codes for the check.
     */
    private void validateUserSpecifiedLanguageCodes(Set<String> languageCodes) {
        for (String code : languageCodes) {
            if (!isValidLanguageCode(code)) {
                final LocalizedMessage msg = new LocalizedMessage(0, TRANSLATION_BUNDLE,
                        WRONG_LANGUAGE_CODE_KEY, new Object[] {code}, getId(), getClass(), null);
                final String exceptionMessage = String.format(Locale.ROOT,
                        "%s [%s]", msg.getMessage(), TranslationCheck.class.getSimpleName());
                throw new IllegalArgumentException(exceptionMessage);
            }
        }
    }

    /**
     * Checks whether user specified language code is correct (is contained in available locales).
     * @param userSpecifiedLanguageCode user specified language code.
     * @return true if user specified language code is correct.
     */
    private static boolean isValidLanguageCode(final String userSpecifiedLanguageCode) {
        boolean valid = false;
        final Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            if (userSpecifiedLanguageCode.equals(locale.toString())) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    @Override
    public void beginProcessing(String charset) {
        super.beginProcessing(charset);
        filesToProcess.clear();
    }

    @Override
    protected void processFiltered(File file, FileText fileText) {
        // We just collecting files for processing at finishProcessing()
        filesToProcess.add(file);
    }

    @Override
    public void finishProcessing() {
        super.finishProcessing();

        final Set<ResourceBundle> bundles = groupFilesIntoBundles(filesToProcess, baseName);
        for (ResourceBundle currentBundle : bundles) {
            checkExistenceOfDefaultTranslation(currentBundle);
            checkExistenceOfRequiredTranslations(currentBundle);
            checkTranslationKeys(currentBundle);
        }
    }

    /**
     * Checks an existence of default translation file in the resource bundle.
     * @param bundle resource bundle.
     */
    private void checkExistenceOfDefaultTranslation(ResourceBundle bundle) {
        final Optional<String> fileName = getMissingFileName(bundle, null);
        if (fileName.isPresent()) {
            logMissingTranslation(bundle.getPath(), fileName.get());
        }
    }

    /**
     * Checks an existence of translation files in the resource bundle.
     * The name of translation file begins with the base name of resource bundle which is followed
     * by '_' and a language code (country and variant are optional), it ends with the extension
     * suffix.
     * @param bundle resource bundle.
     */
    private void checkExistenceOfRequiredTranslations(ResourceBundle bundle) {
        for (String languageCode : requiredTranslations) {
            final Optional<String> fileName = getMissingFileName(bundle, languageCode);
            if (fileName.isPresent()) {
                logMissingTranslation(bundle.getPath(), fileName.get());
            }
        }
    }

    /**
     * Returns the name of translation file which is absent in resource bundle or Guava's Optional,
     * if there is not missing translation.
     * @param bundle resource bundle.
     * @param languageCode language code.
     * @return the name of translation file which is absent in resource bundle or Guava's Optional,
     *         if there is not missing translation.
     */
    private static Optional<String> getMissingFileName(ResourceBundle bundle, String languageCode) {
        final String fileNameRegexp;
        final boolean searchForDefaultTranslation;
        final String extension = bundle.getExtension();
        final String baseName = bundle.getBaseName();
        if (languageCode == null) {
            searchForDefaultTranslation = true;
            fileNameRegexp = String.format(Locale.ROOT, REGEXP_FORMAT_TO_CHECK_DEFAULT_TRANSLATIONS,
                    baseName, extension);
        }
        else {
            searchForDefaultTranslation = false;
            fileNameRegexp = String.format(Locale.ROOT,
                REGEXP_FORMAT_TO_CHECK_REQUIRED_TRANSLATIONS, baseName, languageCode, extension);
        }
        Optional<String> missingFileName = Optional.empty();
        if (!bundle.containsFile(fileNameRegexp)) {
            if (searchForDefaultTranslation) {
                missingFileName = Optional.of(String.format(Locale.ROOT,
                        DEFAULT_TRANSLATION_FILE_NAME_FORMATTER, baseName, extension));
            }
            else {
                missingFileName = Optional.of(String.format(Locale.ROOT,
                        FILE_NAME_WITH_LANGUAGE_CODE_FORMATTER, baseName, languageCode, extension));
            }
        }
        return missingFileName;
    }

    /**
     * Logs that translation file is missing.
     * @param filePath file path.
     * @param fileName file name.
     */
    private void logMissingTranslation(String filePath, String fileName) {
        final MessageDispatcher dispatcher = getMessageDispatcher();
        dispatcher.fireFileStarted(filePath);
        log(0, MSG_KEY_MISSING_TRANSLATION_FILE, fileName);
        fireErrors(filePath);
        dispatcher.fireFileFinished(filePath);
    }

    /**
     * Groups a set of files into bundles.
     * Only files, which names match base name regexp pattern will be grouped.
     * @param files set of files.
     * @param baseNameRegexp base name regexp pattern.
     * @return set of ResourceBundles.
     */
    private static Set<ResourceBundle> groupFilesIntoBundles(Set<File> files,
                                                             Pattern baseNameRegexp) {
        final Set<ResourceBundle> resourceBundles = new HashSet<>();
        for (File currentFile : files) {
            final String fileName = currentFile.getName();
            final String baseName = extractBaseName(fileName);
            final Matcher baseNameMatcher = baseNameRegexp.matcher(baseName);
            if (baseNameMatcher.matches()) {
                final String extension = CommonUtils.getFileExtension(fileName);
                final String path = getPath(currentFile.getAbsolutePath());
                final ResourceBundle newBundle = new ResourceBundle(baseName, path, extension);
                final Optional<ResourceBundle> bundle = findBundle(resourceBundles, newBundle);
                if (bundle.isPresent()) {
                    bundle.get().addFile(currentFile);
                }
                else {
                    newBundle.addFile(currentFile);
                    resourceBundles.add(newBundle);
                }
            }
        }
        return resourceBundles;
    }

    /**
     * Searches for specific resource bundle in a set of resource bundles.
     * @param bundles set of resource bundles.
     * @param targetBundle target bundle to search for.
     * @return Guava's Optional of resource bundle (present if target bundle is found).
     */
    private static Optional<ResourceBundle> findBundle(Set<ResourceBundle> bundles,
                                                       ResourceBundle targetBundle) {
        Optional<ResourceBundle> result = Optional.empty();
        for (ResourceBundle currentBundle : bundles) {
            if (targetBundle.getBaseName().equals(currentBundle.getBaseName())
                    && targetBundle.getExtension().equals(currentBundle.getExtension())
                    && targetBundle.getPath().equals(currentBundle.getPath())) {
                result = Optional.of(currentBundle);
                break;
            }
        }
        return result;
    }

    /**
     * Extracts the base name (the unique prefix) of resource bundle from translation file name.
     * For example "messages" is the base name of "messages.properties",
     * "messages_de_AT.properties", "messages_en.properties", etc.
     * @param fileName the fully qualified name of the translation file.
     * @return the extracted base name.
     */
    private static String extractBaseName(String fileName) {
        final String regexp;
        final Matcher languageCountryVariantMatcher =
            LANGUAGE_COUNTRY_VARIANT_PATTERN.matcher(fileName);
        final Matcher languageCountryMatcher = LANGUAGE_COUNTRY_PATTERN.matcher(fileName);
        final Matcher languageMatcher = LANGUAGE_PATTERN.matcher(fileName);
        if (languageCountryVariantMatcher.matches()) {
            regexp = LANGUAGE_COUNTRY_VARIANT_PATTERN.pattern();
        }
        else if (languageCountryMatcher.matches()) {
            regexp = LANGUAGE_COUNTRY_PATTERN.pattern();
        }
        else if (languageMatcher.matches()) {
            regexp = LANGUAGE_PATTERN.pattern();
        }
        else {
            regexp = DEFAULT_TRANSLATION_REGEXP;
        }
        // We use substring(...) instead of replace(...), so that the regular expression does
        // not have to be compiled each time it is used inside 'replace' method.
        final String removePattern = regexp.substring("^.+".length(), regexp.length());
        return fileName.replaceAll(removePattern, "");
    }

    /**
     * Extracts path from a file name which contains the path.
     * For example, if file nam is /xyz/messages.properties, then the method
     * will return /xyz/.
     * @param fileNameWithPath file name which contains the path.
     * @return file path.
     */
    private static String getPath(String fileNameWithPath) {
        return fileNameWithPath
            .substring(0, fileNameWithPath.lastIndexOf(File.separator));
    }

    /**
     * Checks resource files in bundle for consistency regarding their keys.
     * All files in bundle must have the same key set. If this is not the case
     * an error message is posted giving information which key misses in which file.
     * @param bundle resource bundle.
     */
    private void checkTranslationKeys(ResourceBundle bundle) {
        final Set<File> filesInBundle = bundle.getFiles();
        if (filesInBundle.size() > 1) {
            // build a map from files to the keys they contain
            final Set<String> allTranslationKeys = new HashSet<>();
            final SetMultimap<File, String> filesAssociatedWithKeys = HashMultimap.create();
            for (File currentFile : filesInBundle) {
                final Set<String> keysInCurrentFile = getTranslationKeys(currentFile);
                allTranslationKeys.addAll(keysInCurrentFile);
                filesAssociatedWithKeys.putAll(currentFile, keysInCurrentFile);
            }
            checkFilesForConsistencyRegardingTheirKeys(filesAssociatedWithKeys, allTranslationKeys);
        }
    }

    /**
     * Compares th the specified key set with the key sets of the given translation files (arranged
     * in a map). All missing keys are reported.
     * @param fileKeys a Map from translation files to their key sets.
     * @param keysThatMustExist the set of keys to compare with.
     */
    private void checkFilesForConsistencyRegardingTheirKeys(SetMultimap<File, String> fileKeys,
                                                            Set<String> keysThatMustExist) {
        for (File currentFile : fileKeys.keySet()) {
            final MessageDispatcher dispatcher = getMessageDispatcher();
            final String path = currentFile.getPath();
            dispatcher.fireFileStarted(path);
            final Set<String> currentFileKeys = fileKeys.get(currentFile);
            final Set<String> missingKeys = keysThatMustExist.stream()
                .filter(e -> !currentFileKeys.contains(e)).collect(Collectors.toSet());
            if (!missingKeys.isEmpty()) {
                for (Object key : missingKeys) {
                    log(0, MSG_KEY, key);
                }
            }
            fireErrors(path);
            dispatcher.fireFileFinished(path);
        }
    }

    /**
     * Loads the keys from the specified translation file into a set.
     * @param file translation file.
     * @return a Set object which holds the loaded keys.
     */
    private Set<String> getTranslationKeys(File file) {
        Set<String> keys = new HashSet<>();
        InputStream inStream = null;
        try {
            inStream = new FileInputStream(file);
            final Properties translations = new Properties();
            translations.load(inStream);
            keys = translations.stringPropertyNames();
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
        final SortedSet<LocalizedMessage> messages = new TreeSet<>();
        messages.add(message);
        getMessageDispatcher().fireErrors(file.getPath(), messages);
        LOG.debug("IOException occurred.", exception);
    }

    /** Class which represents a resource bundle. */
    private static class ResourceBundle {
        /** Bundle base name. */
        private final String baseName;
        /** Common extension of files which are included in the resource bundle. */
        private final String extension;
        /** Common path of files which are included in the resource bundle. */
        private final String path;
        /** Set of files which are included in the resource bundle. */
        private final Set<File> files;

        /**
         * Creates a ResourceBundle object with specific base name, common files extension.
         * @param baseName bundle base name.
         * @param path common path of files which are included in the resource bundle.
         * @param extension common extension of files which are included in the resource bundle.
         */
        ResourceBundle(String baseName, String path, String extension) {
            this.baseName = baseName;
            this.path = path;
            this.extension = extension;
            files = new HashSet<>();
        }

        public String getBaseName() {
            return baseName;
        }

        public String getPath() {
            return path;
        }

        public String getExtension() {
            return extension;
        }

        public Set<File> getFiles() {
            return Collections.unmodifiableSet(files);
        }

        /**
         * Adds a file into resource bundle.
         * @param file file which should be added into resource bundle.
         */
        public void addFile(File file) {
            files.add(file);
        }

        /**
         * Checks whether a resource bundle contains a file which name matches file name regexp.
         * @param fileNameRegexp file name regexp.
         * @return true if a resource bundle contains a file which name matches file name regexp.
         */
        public boolean containsFile(String fileNameRegexp) {
            boolean containsFile = false;
            for (File currentFile : files) {
                if (Pattern.matches(fileNameRegexp, currentFile.getName())) {
                    containsFile = true;
                    break;
                }
            }
            return containsFile;
        }
    }
}
