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

import static com.puppycrawl.tools.checkstyle.checks.TranslationCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.TranslationCheck.MSG_KEY_MISSING_TRANSLATION_FILE;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.SortedSet;

import com.puppycrawl.tools.checkstyle.Definitions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

@RunWith(MockitoJUnitRunner.class)
public class TranslationCheckTest extends BaseCheckTestSupport {
    @Captor
    private ArgumentCaptor<SortedSet<LocalizedMessage>> captor;

    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration config) {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(config);
        return dc;
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("checks" + File.separator + filename);
    }

    @Test
    public void testTranslation() throws Exception {
        final Configuration checkConfig = createCheckConfig(TranslationCheck.class);
        final String[] expected = {
            "0: " + getCheckMessage(MSG_KEY, "only.english"),
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
    public void testOnePropertyFileSet() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
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
    @SuppressWarnings("unchecked")
    public void testLogIoExceptionFileNotFound() throws Exception {
        //I can't put wrong file here. Checkstyle fails before check started.
        //I saw some usage of file or handling of wrong file in Checker, or somewhere
        //in checks running part. So I had to do it with reflection to improve coverage.
        final TranslationCheck check = new TranslationCheck();
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
        check.configure(checkConfig);
        final Checker checker = createChecker(checkConfig);
        check.setMessageDispatcher(checker);

        final Method loadKeys =
            check.getClass().getDeclaredMethod("getTranslationKeys", File.class);
        loadKeys.setAccessible(true);
        final Set<String> keys = (Set<String>) loadKeys.invoke(check, new File(""));
        assertTrue("Translation keys should be empty when File is not found", keys.isEmpty());
    }

    @Test
    public void testLogIoException() throws Exception {
        //I can't put wrong file here. Checkstyle fails before check started.
        //I saw some usage of file or handling of wrong file in Checker, or somewhere
        //in checks running part. So I had to do it with reflection to improve coverage.
        final TranslationCheck check = new TranslationCheck();
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
        final MessageDispatcher dispatcher = mock(MessageDispatcher.class);
        check.configure(checkConfig);
        check.setMessageDispatcher(dispatcher);

        final Method logIoException = check.getClass().getDeclaredMethod("logIoException",
                IOException.class,
                File.class);
        logIoException.setAccessible(true);
        final File file = new File("");
        logIoException.invoke(check, new IOException("test exception"), file);

        Mockito.verify(dispatcher, times(1)).fireErrors(any(String.class), captor.capture());
        assertThat(captor.getValue().first().getMessage(), endsWith("- test exception"));
    }

    @Test
    public void testDefaultTranslationFileIsMissing() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "ja,,, de, ja");

        final File[] propertyFiles = {
            new File(getPath("messages_translation_de.properties")),
            new File(getPath("messages_translation_ja.properties")),
        };

        final String[] expected = {
            "0: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
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
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "ja, de");

        final File[] propertyFiles = {
            new File(getPath("messages_translation.properties")),
            new File(getPath("messages_translation_ja.properties")),
        };

        final String[] expected = {
            "0: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
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
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "fr");

        final File[] propertyFiles = {
            new File(getPath("messages-translation_fr.properties")),
        };

        final String[] expected = {
            "0: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
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
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "fr, tr");

        final File[] propertyFiles = {
            new File(getPath("messages-translation.properties")),
            new File(getPath("messages-translation_fr.properties")),
        };

        final String[] expected = {
            "0: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
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
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "de");

        final File[] propertyFiles = {
            new File(getPath("app-dev.properties")),
            new File(getPath("app-stage.properties")),
        };

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath("app-dev.properties"),
            expected);
    }

    @Test
    public void testTranslationFileWithLanguageCountryVariantIsMissing() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "es, de");

        final File[] propertyFiles = {
            new File(getPath("messages_home.properties")),
            new File(getPath("messages_home_es_US.properties")),
            new File(getPath("messages_home_fr_CA_UNIX.properties")),
            };

        final String[] expected = {
            "0: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
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
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "es, fr");

        final File[] propertyFiles = {
            new File(getPath("messages_home.properties")),
            new File(getPath("messages_home_es_US.properties")),
            new File(getPath("messages_home_fr_CA_UNIX.properties")),
            };

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testBaseNameOption() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
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
            "0: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
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
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
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
            "0: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
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
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
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
            "0: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "ButtonLabels_ja.properties"),
        };

        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testRegexpToMatchPartOfBaseName() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "de, es, fr, ja");
        checkConfig.addAttribute("fileExtensions", "properties,translations");
        checkConfig.addAttribute("baseName", "^.*Labels.*");

        final File[] propertyFiles = {
            new File(getPath("MyLabelsI18.properties")),
            new File(getPath("MyLabelsI18_de.properties")),
            new File(getPath("MyLabelsI18_es.properties")),
        };

        final String[] expected = {
            "0: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE, "MyLabelsI18_fr.properties"),
            "0: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE, "MyLabelsI18_ja.properties"),
        };

        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath(""),
            expected);
    }

    @Test
    public void testBundlesWithSameNameButDifferentPaths() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
        checkConfig.addAttribute("requiredTranslations", "de");
        checkConfig.addAttribute("fileExtensions", "properties");
        checkConfig.addAttribute("baseName", "^.*Labels.*");

        final File[] propertyFiles = {
            new File(getPath("MyLabelsI18.properties")),
            new File(getPath("MyLabelsI18_de.properties")),
            new File(getNonCompilablePath("MyLabelsI18.properties")),
            new File(getNonCompilablePath("MyLabelsI18_de.properties")),
        };

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

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
}
