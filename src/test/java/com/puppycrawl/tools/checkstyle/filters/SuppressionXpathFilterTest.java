///
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
///

package com.puppycrawl.tools.checkstyle.filters;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;
import nl.jqno.equalsverifier.Warning;

public class SuppressionXpathFilterTest extends AbstractModuleTestSupport {

    private static final String PATTERN = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";

    private static final String[] ALL_MESSAGES = {
        "20:29: " + getCheckMessage(ConstantNameCheck.class,
                                    MSG_INVALID_PATTERN, "bad_name", PATTERN),
    };

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionxpathfilter";
    }

    @Test
    public void testAcceptOne() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifyFilterWithInlineConfigParser(getPath("InputSuppressionXpathFilterAcceptOne.java"),
                                           ALL_MESSAGES,
                                           removeSuppressed(ALL_MESSAGES, suppressed));
    }

    @Test
    public void testAcceptTwo() throws Exception {
        final String[] expected = {
            "20:29: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                                        "different_name_than_suppression", PATTERN),
        };
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifyFilterWithInlineConfigParser(getPath("InputSuppressionXpathFilterAcceptTwo.java"),
                                           expected, removeSuppressed(expected, suppressed));
    }

    @Test
    public void testAcceptOnNullFile() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathFilterAcceptOnNullFile.java"), ALL_MESSAGES,
            removeSuppressed(ALL_MESSAGES, suppressed));
    }

    @Test
    public void testNonExistentSuppressionFileWithFalseOptional() throws Exception {
        final String fileName = getPath("non_existent_suppression_file.xml");
        try {
            final boolean optional = false;
            createSuppressionXpathFilter(fileName, optional);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid error message")
                .that(ex.getMessage())
                .isEqualTo("Unable to find: " + fileName);
        }
    }

    @Test
    public void testExistingInvalidSuppressionFileWithTrueOptional() throws Exception {
        final String fileName = getPath("InputSuppressionXpathFilterInvalidFile.xml");
        try {
            final boolean optional = true;
            createSuppressionXpathFilter(fileName, optional);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid error message")
                .that(ex.getMessage())
                .isEqualTo("Unable to parse " + fileName
                    + " - invalid files or checks or message format for suppress-xpath");
        }
    }

    @Test
    public void testExistingSuppressionFileWithTrueOptional() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathFilterAcceptWithOptionalTrue.java"), ALL_MESSAGES,
            removeSuppressed(ALL_MESSAGES, suppressed));
    }

    @Test
    public void testNonExistentSuppressionFileWithTrueOptional() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathFilterNonExistentFileWithTrueOptional.java"),
            ALL_MESSAGES, removeSuppressed(ALL_MESSAGES, suppressed));
    }

    @Test
    public void testReject() throws Exception {
        final String[] suppressed = {
            "20:29: " + getCheckMessage(ConstantNameCheck.class,
                                        MSG_INVALID_PATTERN, "bad_name", PATTERN),
        };
        verifyFilterWithInlineConfigParser(getPath("InputSuppressionXpathFilterReject.java"),
                                           ALL_MESSAGES,
                                           removeSuppressed(ALL_MESSAGES, suppressed));
    }

    @Test
    public void testEqualsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(SuppressionXpathFilter.class)
                .usingGetClass()
                .withIgnoredFields("file", "optional", "configuration")
                .suppress(Warning.NONFINAL_FIELDS).report();
        assertWithMessage("Error: " + ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

    @Test
    public void testExternalResource() throws Exception {
        final boolean optional = false;
        final String fileName = getPath("InputSuppressionXpathFilterIdAndQuery.xml");
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(fileName, optional);
        final Set<String> expected = Collections.singleton(fileName);
        final Set<String> actual = filter.getExternalResourceLocations();
        assertWithMessage("Invalid external resource")
            .that(actual)
            .isEqualTo(expected);
    }

    private static SuppressionXpathFilter createSuppressionXpathFilter(String fileName,
                                                                       boolean optional)
            throws CheckstyleException {
        final SuppressionXpathFilter suppressionXpathFilter = new SuppressionXpathFilter();
        suppressionXpathFilter.setFile(fileName);
        suppressionXpathFilter.setOptional(optional);
        suppressionXpathFilter.finishLocalSetup();
        return suppressionXpathFilter;
    }

    @Test
    public void testFalseEncodeString() throws Exception {
        final String pattern = "[^a-zA-z0-9]*";
        final String[] expected = {
            "17:24: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "19:23: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "21:28: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "23:26: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "25:26: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "27:26: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "29:23: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "31:29: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "33:29: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
        };

        final String[] suppressed = {
            "17:24: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "19:23: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "21:28: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "23:26: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "25:26: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "27:26: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "29:23: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
        };

        verifyFilterWithInlineConfigParser(getPath("InputSuppressionXpathFilterEscapeString.java"),
                                           expected, removeSuppressed(expected, suppressed));
    }

    @Test
    public void testFalseEncodeChar() throws Exception {
        final String pattern = "[^a-zA-z0-9]*";
        final String[] expected = {
            "17:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "19:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "21:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "23:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "25:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
        };

        final String[] suppressed = {
            "21:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "23:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
            "25:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
        };

        verifyFilterWithInlineConfigParser(getPath("InputSuppressionXpathFilterEscapeChar.java"),
                                           expected, removeSuppressed(expected, suppressed));
    }

    @Test
    public void testXpathSuppression() throws Exception {
        for (int test = 1; test <= 4; test++) {

            final String[] expected = {
                "20:29: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                        "different_name_than_suppression", PATTERN),
            };
            final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
            final String path = "InputSuppressionXpathFilter" + test + ".java";
            verifyFilterWithInlineConfigParser(getPath(path),
                    expected, removeSuppressed(expected, suppressed));
        }
    }

    @Test
    public void testXpathSuppression2() throws Exception {
        final String pattern = "[^a-zA-z0-9]*";
        final String[] expected = {
            "18:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
        };

        final String[] suppressed = {
            "18:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
        };

        verifyFilterWithInlineConfigParser(getPath("InputSuppressionXpathFilter5.java"),
                                           expected, removeSuppressed(expected, suppressed));
    }

    @Test
    public void testXpathSuppression3() throws Exception {
        final String pattern = "[^a-zA-z0-9]*";
        final String[] expected = {
            "18:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
        };

        final String[] suppressed = {
            "18:14: " + getCheckMessage(IllegalTokenTextCheck.class, MSG_KEY, pattern),
        };

        verifyFilterWithInlineConfigParser(getPath("InputSuppressionXpathFilter6.java"),
                                           expected, removeSuppressed(expected, suppressed));
    }

    @Test
    public void testXpathSuppression4() throws Exception {
        final String[] suppressed = {
            "20:29: " + getCheckMessage(ConstantNameCheck.class,
                                        MSG_INVALID_PATTERN, "bad_name", PATTERN),
        };
        verifyFilterWithInlineConfigParser(getPath("InputSuppressionXpathFilter7.java"),
                                           ALL_MESSAGES,
                                           removeSuppressed(ALL_MESSAGES, suppressed));
    }
}
