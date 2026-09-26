///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

/**
 * Custom class loader is needed to pass URLs to pretend these are loaded from the classpath
 * though we can't add/change the files for testing. The class loader is nested in this class,
 * so the custom class loader we are using is safe.
 *
 * @noinspection ClassLoaderInstantiation
 * @noinspectionreason ClassLoaderInstantiation - Custom class loader is needed to
 *      pass URLs for testing
 */
public class LocalizedMessageTest {

    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    @DefaultLocale("en")
    @Test
    public void testNullArgs() {
        final LocalizedMessage messageClass = new LocalizedMessage(Definitions.CHECKSTYLE_BUNDLE,
                DefaultLogger.class, "DefaultLogger.addException", "myfile");
        assertWithMessage("Violation should contain exception info")
                .that(messageClass.getMessage())
                .contains("Error auditing myfile");

        final LocalizedMessage nullClass = new LocalizedMessage(Definitions.CHECKSTYLE_BUNDLE,
                DefaultLogger.class, "DefaultLogger.addException");
        final String outputForNullArgs = nullClass.getMessage();
        assertWithMessage("Violation should contain exception info")
                .that(outputForNullArgs)
                .contains("Error auditing {0}");
    }

    @Test
    public void testLanguageIsValid() {
        final String language = DEFAULT_LOCALE.getLanguage();
        assumeFalse(language.isEmpty(), "Locale not set");
        assertWithMessage("Invalid language")
                .that(Locale.getISOLanguages())
                .asList()
                .contains(language);
    }

    /**
     * Verifies that the country specified with the system property {@code user.country} exists.
     */
    @Test
    public void testCountryIsValid() {
        final String country = DEFAULT_LOCALE.getCountry();
        assumeFalse(country.isEmpty(), "Locale not set");
        assertWithMessage("Invalid country")
                .that(Locale.getISOCountries())
                .asList()
                .contains(country);
    }

    @Test
    public void testMessageInFrench() {
        final LocalizedMessage violation = createSampleViolation();
        LocalizedMessage.setLocale(Locale.FRENCH);

        assertWithMessage("Invalid violation")
            .that(violation.getMessage())
            .isEqualTo("Instruction vide.");
    }

    @DefaultLocale("fr")
    @Test
    public void testEnforceEnglishLanguageBySettingUnitedStatesLocale() {
        LocalizedMessage.setLocale(Locale.US);
        final LocalizedMessage violation = createSampleViolation();

        assertWithMessage("Invalid violation")
            .that(violation.getMessage())
            .isEqualTo("Empty statement.");
    }

    @DefaultLocale("fr")
    @Test
    public void testEnforceEnglishLanguageBySettingRootLocale() {
        LocalizedMessage.setLocale(Locale.ROOT);
        final LocalizedMessage violation = createSampleViolation();

        assertWithMessage("Invalid violation")
            .that(violation.getMessage())
            .isEqualTo("Empty statement.");
    }

    private static LocalizedMessage createSampleViolation() {
        return new LocalizedMessage("com.puppycrawl.tools.checkstyle.checks.coding.messages",
                LocalizedMessage.class, "empty.statement");
    }

    @Test
    public void testArgsFieldIsSetToNullWhenArgsIsNull() {
        final LocalizedMessage message = new LocalizedMessage(Definitions.CHECKSTYLE_BUNDLE,
                DefaultLogger.class, "DefaultLogger.addException", (Object[]) null);
        final Object[] args = TestUtil.getInternalState(message, "args", Object[].class);

        assertWithMessage("Args field should be null when null args are passed")
                .that(args)
                .isNull();
    }

    @AfterEach
    public void tearDown() {
        LocalizedMessage.setLocale(DEFAULT_LOCALE);
    }

}
