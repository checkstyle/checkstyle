////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.TranslationCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.TranslationCheck.MSG_KEY_MISSING_TRANSLATION_FILE;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.powermock.reflect.Whitebox;
import org.w3c.dom.Node;

import com.google.common.collect.ImmutableMap;
import com.puppycrawl.tools.checkstyle.AbstractXmlTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.XMLLogger;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.internal.utils.XmlUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class TranslationCheckTest extends AbstractXmlTestSupport {

    @TempDir
    public File temporaryFolder;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/translation";
    }

    @Test
    public void testTranslation() throws Exception {
        final Configuration checkConfig = createModuleConfig(TranslationCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY, "only.english"),
        };
        final File[] propertyFiles = {
            new File(getPath("messages_test_de.properties")),
            new File(getPath("messages_test.properties")),
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath("messages_test_de.properties"),
            expected);
    }

    @Test
    public void testDifferentBases() throws Exception {
        final Configuration checkConfig = createModuleConfig(TranslationCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY, "only.english"),
        };
        final File[] propertyFiles = {
            new File(getPath("messages_test_de.properties")),
            new File(getPath("messages_test.properties")),
            new File(getPath("messages_translation.properties")),
            new File(getPath("messages_translation_de.properties")),
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath("messages_test_de.properties"),
            expected);
    }

    @Test
    public void testDifferentPaths() throws Exception {
        final File file = new File(temporaryFolder, "messages_test_de.properties");
        try (Writer writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            final String content = "hello=Hello\ncancel=Cancel";
            writer.write(content);
        }
        final Configuration checkConfig = createModuleConfig(TranslationCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "messages_test.properties"),
        };
        final File[] propertyFiles = {
            file,
            new File(getPath("messages_test.properties")),
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            file.getParent(),
            expected);
    }

    /**
     * Even when we pass several files to AbstractModuleTestSupport#verify,
     * the check processes it during one run, so we cannot reproduce situation
     * when TranslationCheck#beginProcessing called several times during single run.
     * So, we have to use reflection to check this particular case.
     *
     * @throws Exception when code tested throws exception
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testStateIsCleared() throws Exception {
        final File fileToProcess = new File(
                getPath("InputTranslationCheckFireErrors_de.properties")
        );
        final String charset = StandardCharsets.UTF_8.name();
        final TranslationCheck check = new TranslationCheck();
        check.beginProcessing(charset);
        check.processFiltered(fileToProcess, new FileText(fileToProcess, charset));
        check.beginProcessing(charset);
        final Field field = check.getClass().getDeclaredField("filesToProcess");
        field.setAccessible(true);

        assertTrue(((Collection<File>) field.get(check)).isEmpty(),
                "Stateful field is not cleared on beginProcessing");
    }

    @Test
    public void testFileExtension() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("baseName", "^InputTranslation.*$");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final File[] propertyFiles = {
            new File(getPath("InputTranslation_de.txt")),
        };
        verify(createChecker(checkConfig),
            propertyFiles,
            getPath("InputTranslation_de.txt"),
            expected);
    }

    @Test
    public void testLogOutput() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "ja,de");
        checkConfig.addAttribute("baseName", "^InputTranslation.*$");
        final Checker checker = createChecker(checkConfig);
        checker.setBasedir(getPath(""));
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XMLLogger logger = new XMLLogger(out, AutomaticBean.OutputStreamOptions.NONE);
        checker.addListener(logger);

        final String defaultProps = getPath("InputTranslationCheckFireErrors.properties");
        final String translationProps = getPath("InputTranslationCheckFireErrors_de.properties");

        final File[] propertyFiles = {
            new File(defaultProps),
            new File(translationProps),
        };

        final String line = "1: ";
        final String firstErrorMessage = getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                "InputTranslationCheckFireErrors_ja.properties");
        final String secondErrorMessage = getCheckMessage(MSG_KEY, "anotherKey");

        verify(checker, propertyFiles, ImmutableMap.of(
            ":1", Collections.singletonList(" " + firstErrorMessage),
            "InputTranslationCheckFireErrors_de.properties",
                Collections.singletonList(line + secondErrorMessage)));

        verifyXml(getPath("ExpectedTranslationLog.xml"), out,
            TranslationCheckTest::isFilenamesEqual,
            firstErrorMessage, secondErrorMessage);
    }

    @Test
    public void testOnePropertyFileSet() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final File[] propertyFiles = {
            new File(getPath("app-dev.properties")),
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath("app-dev.properties"),
            expected);
    }

    @Test
    public void testLogIoExceptionFileNotFound() throws Exception {
        // I can't put wrong file here. Checkstyle fails before check started.
        // I saw some usage of file or handling of wrong file in Checker, or somewhere
        // in checks running part. So I had to do it with reflection to improve coverage.
        final TranslationCheck check = new TranslationCheck();
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        final TestMessageDispatcher dispatcher = new TestMessageDispatcher();
        check.configure(checkConfig);
        check.setMessageDispatcher(dispatcher);

        final Set<String> keys = Whitebox.invokeMethod(check, "getTranslationKeys",
                new File(".no.such.file"));
        assertTrue(keys.isEmpty(), "Translation keys should be empty when File is not found");

        assertEquals(1, dispatcher.savedErrors.size(), "expected number of errors to fire");
        final LocalizedMessage localizedMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE, "general.fileNotFound",
                null, null, getClass(), null);
        assertEquals(localizedMessage.getMessage(),
                dispatcher.savedErrors.iterator().next().getMessage(), "Invalid message");
    }

    @Test
    public void testLogIoException() throws Exception {
        // I can't put wrong file here. Checkstyle fails before check started.
        // I saw some usage of file or handling of wrong file in Checker, or somewhere
        // in checks running part. So I had to do it with reflection to improve coverage.
        final TranslationCheck check = new TranslationCheck();
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        final TestMessageDispatcher dispatcher = new TestMessageDispatcher();
        check.configure(checkConfig);
        check.setMessageDispatcher(dispatcher);

        final Exception exception = new IOException("test exception");
        Whitebox.invokeMethod(check, "logException", exception, new File(""));

        assertEquals(1, dispatcher.savedErrors.size(), "expected number of errors to fire");
        final LocalizedMessage localizedMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE, "general.exception",
                new String[] {exception.getMessage()}, null, getClass(), null);
        assertEquals(localizedMessage.getMessage(),
                dispatcher.savedErrors.iterator().next().getMessage(), "Invalid message");
    }

    @Test
    public void testLogIllegalArgumentException() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("baseName", "^bad.*$");
        final String[] expected = {
            "0: " + new LocalizedMessage(1, Definitions.CHECKSTYLE_BUNDLE, "general.exception",
                new String[] {"Malformed \\uxxxx encoding." }, null, getClass(), null).getMessage(),
            "1: " + getCheckMessage(MSG_KEY, "test"),
        };
        final File[] propertyFiles = {
            new File(getPath("bad.properties")),
            new File(getPath("bad_es.properties")),
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath("bad.properties"),
            expected);
    }

    @Test
    public void testDefaultTranslationFileIsMissing() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "ja,,, de, ja");

        final File[] propertyFiles = {
            new File(getPath("messages_translation_de.properties")),
            new File(getPath("messages_translation_ja.properties")),
        };

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "messages_translation.properties"),
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testTranslationFilesAreMissing() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "ja, de");

        final File[] propertyFiles = {
            new File(getPath("messages_translation.properties")),
            new File(getPath("messages_translation_ja.properties")),
        };

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "messages_translation_de.properties"),
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testBaseNameWithSeparatorDefaultTranslationIsMissing() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "fr");

        final File[] propertyFiles = {
            new File(getPath("messages-translation_fr.properties")),
        };

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "messages-translation.properties"),
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testBaseNameWithSeparatorTranslationsAreMissing() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "fr, tr");

        final File[] propertyFiles = {
            new File(getPath("messages-translation.properties")),
            new File(getPath("messages-translation_fr.properties")),
        };

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "messages-translation_tr.properties"),
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testIsNotMessagesBundle() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "de");

        final File[] propertyFiles = {
            new File(getPath("app-dev.properties")),
            new File(getPath("app-stage.properties")),
        };

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath("app-dev.properties"),
            expected);
    }

    @Test
    public void testTranslationFileWithLanguageCountryVariantIsMissing() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "es, de");

        final File[] propertyFiles = {
            new File(getPath("messages_home.properties")),
            new File(getPath("messages_home_es_US.properties")),
            new File(getPath("messages_home_fr_CA_UNIX.properties")),
            };

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "messages_home_de.properties"),
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testTranslationFileWithLanguageCountryVariantArePresent() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "es, fr");

        final File[] propertyFiles = {
            new File(getPath("messages_home.properties")),
            new File(getPath("messages_home_es_US.properties")),
            new File(getPath("messages_home_fr_CA_UNIX.properties")),
            };

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testBaseNameOption() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "de, es, fr, ja");
        checkConfig.addAttribute("baseName", "^.*Labels$");

        final File[] propertyFiles = {
            new File(getPath("ButtonLabels.properties")),
            new File(getPath("ButtonLabels_de.properties")),
            new File(getPath("ButtonLabels_es.properties")),
            new File(getPath("ButtonLabels_fr_CA_UNIX.properties")),
            new File(getPath("messages_home.properties")),
            new File(getPath("messages_home_es_US.properties")),
            new File(getPath("messages_home_fr_CA_UNIX.properties")),
        };

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "ButtonLabels_ja.properties"),
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testFileExtensions() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "de, es, fr, ja");
        checkConfig.addAttribute("fileExtensions", "properties,translation");
        checkConfig.addAttribute("baseName", "^.*(Titles|Labels)$");

        final File[] propertyFiles = {
            new File(getPath("ButtonLabels.properties")),
            new File(getPath("ButtonLabels_de.properties")),
            new File(getPath("ButtonLabels_es.properties")),
            new File(getPath("ButtonLabels_fr_CA_UNIX.properties")),
            new File(getPath("PageTitles.translation")),
            new File(getPath("PageTitles_de.translation")),
            new File(getPath("PageTitles_es.translation")),
            new File(getPath("PageTitles_fr.translation")),
            new File(getPath("PageTitles_ja.translation")),
        };

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "ButtonLabels_ja.properties"),
        };

        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testEqualBaseNamesButDifferentExtensions() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "de, es, fr, ja");
        checkConfig.addAttribute("fileExtensions", "properties,translations");
        checkConfig.addAttribute("baseName", "^.*Labels$");

        final File[] propertyFiles = {
            new File(getPath("ButtonLabels.properties")),
            new File(getPath("ButtonLabels_de.properties")),
            new File(getPath("ButtonLabels_es.properties")),
            new File(getPath("ButtonLabels_fr_CA_UNIX.properties")),
            new File(getPath("ButtonLabels.translations")),
            new File(getPath("ButtonLabels_ja.translations")),
            new File(getPath("ButtonLabels_es.translations")),
            new File(getPath("ButtonLabels_fr_CA_UNIX.translations")),
            new File(getPath("ButtonLabels_de.translations")),
        };

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "ButtonLabels_ja.properties"),
        };

        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testEqualBaseNamesButDifferentExtensions2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "de, es");
        checkConfig.addAttribute("fileExtensions", "properties, translations");
        checkConfig.addAttribute("baseName", "^.*Labels$");

        final File[] propertyFiles = {
            new File(getPath("ButtonLabels.properties")),
            new File(getPath("ButtonLabels_de.properties")),
            new File(getPath("ButtonLabels_es.properties")),
            new File(getPath("ButtonLabels_ja.translations")),
        };

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "ButtonLabels.translations"),
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "ButtonLabels_de.translations"),
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "ButtonLabels_es.translations"),
        };

        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testRegexpToMatchPartOfBaseName() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "de, es, fr, ja");
        checkConfig.addAttribute("fileExtensions", "properties,translations");
        checkConfig.addAttribute("baseName", "^.*Labels.*");

        final File[] propertyFiles = {
            new File(getPath("MyLabelsI18.properties")),
            new File(getPath("MyLabelsI18_de.properties")),
            new File(getPath("MyLabelsI18_es.properties")),
        };

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE, "MyLabelsI18_fr.properties"),
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE, "MyLabelsI18_ja.properties"),
        };

        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testBundlesWithSameNameButDifferentPaths() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "de");
        checkConfig.addAttribute("fileExtensions", "properties");
        checkConfig.addAttribute("baseName", "^.*Labels.*");

        final File[] propertyFiles = {
            new File(getPath("MyLabelsI18.properties")),
            new File(getPath("MyLabelsI18_de.properties")),
            new File(getNonCompilablePath("MyLabelsI18.properties")),
            new File(getNonCompilablePath("MyLabelsI18_de.properties")),
        };

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testWrongUserSpecifiedLanguageCodes() {
        final TranslationCheck check = new TranslationCheck();
        try {
            check.setRequiredTranslations("11");
            fail("IllegalArgumentException is expected. Specified language code is incorrect.");
        }
        catch (IllegalArgumentException ex) {
            final String exceptionMessage = ex.getMessage();
            assertThat("Error message is unexpected",
                    exceptionMessage, containsString("11"));
            assertThat("Error message is unexpected",
                    exceptionMessage, endsWith("[TranslationCheck]"));
        }
    }

    /**
     * Compare two file names.
     *
     * @param expected expected node
     * @param actual actual node
     * @return true if file names match
     */
    private static boolean isFilenamesEqual(Node expected, Node actual) {
        // order is not always maintained here for an unknown reason.
        // File names can appear in different orders depending on the OS and VM.
        // This ensures we pick up the correct file based on its name and the
        // number of children it has.
        return !"file".equals(expected.getNodeName())
            || XmlUtil.getNameAttributeOfNode(expected)
            .equals(XmlUtil.getNameAttributeOfNode(actual))
            && XmlUtil.getChildrenElements(expected).size() == XmlUtil
            .getChildrenElements(actual).size();
    }

    private static class TestMessageDispatcher implements MessageDispatcher {

        private Set<LocalizedMessage> savedErrors;

        @Override
        public void fireFileStarted(String fileName) {
            throw new IllegalStateException(fileName);
        }

        @Override
        public void fireFileFinished(String fileName) {
            throw new IllegalStateException(fileName);
        }

        @Override
        public void fireErrors(String fileName, SortedSet<LocalizedMessage> errors) {
            savedErrors = new TreeSet<>(errors);
        }

    }

}
