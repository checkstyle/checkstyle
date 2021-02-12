////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.filters;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;
import nl.jqno.equalsverifier.Warning;

public class SuppressionXpathFilterTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionxpathfilter";
    }

    @Test
    public void testAcceptOne() throws Exception {
        final boolean optional = false;
        final SuppressionXpathFilter filter =
                createSuppressionXpathFilter(getPath("InputSuppressionXpathFilterNone.xml"),
                        optional);

        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "ATest.java", null, null);

        assertTrue(filter.accept(ev),
                "TreeWalker audit event should be accepted when there are no suppressions");
    }

    @Test
    public void testAcceptTwo() throws Exception {
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(
                getPath("InputSuppressionXpathFilterIdAndQuery.xml"), optional);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "file1.java", null, null);

        assertTrue(filter.accept(ev), "TreeWalker audit event should be accepted");
    }

    @Test
    public void testAcceptOnNullFile() throws Exception {
        final String fileName = null;
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(fileName, optional);

        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "AnyJava.java", null, null);
        assertTrue(filter.accept(ev),
                "TreeWalker audit event on null file should be accepted, but was not");
    }

    @Test
    public void testNonExistentSuppressionFileWithFalseOptional() throws Exception {
        final String fileName = getPath("non_existent_suppression_file.xml");
        try {
            final boolean optional = false;
            createSuppressionXpathFilter(fileName, optional);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Unable to find: " + fileName, ex.getMessage(), "Invalid error message");
        }
    }

    @Test
    public void testExistingInvalidSuppressionFileWithTrueOptional() throws Exception {
        final String fileName = getPath("InputSuppressionXpathFilterInvalidFile.xml");
        try {
            final boolean optional = true;
            createSuppressionXpathFilter(fileName, optional);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Unable to parse " + fileName
                    + " - invalid files or checks or message format for suppress-xpath",
                    ex.getMessage(), "Invalid error message");
        }
    }

    @Test
    public void testExistingSuppressionFileWithTrueOptional() throws Exception {
        final boolean optional = true;
        final SuppressionXpathFilter filter =
                createSuppressionXpathFilter(getPath("InputSuppressionXpathFilterNone.xml"),
                        optional);

        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "AnyJava.java", null, null);

        assertTrue(filter.accept(ev), "Suppression file with true optional was not accepted");
    }

    @Test
    public void testNonExistentSuppressionFileWithTrueOptional() throws Exception {
        final String fileName = "src/test/resources/com/puppycrawl/tools/checkstyle/filters/"
                + "non_existent_suppression_file.xml";
        final boolean optional = true;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(fileName, optional);

        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "AnyFile.java", null, null);

        assertTrue(filter.accept(ev), "Should except event when suppression file does not exist");
    }

    @Test
    public void testReject() throws Exception {
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(
                        getPath("InputSuppressionXpathFilterIdAndQuery.xml"), optional);
        final File file = new File(getPath("InputSuppressionXpathFilter.java"));
        final LocalizedMessage message = new LocalizedMessage(3, 0, TokenTypes.CLASS_DEF, "", "",
                null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "file1.java",
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));

        assertFalse(filter.accept(ev), "TreeWalker audit event should be rejected");
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
        assertEquals(expected, actual, "Invalid external resource");
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
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(
                getPath("InputSuppressionXpathFilterEscape.xml"), optional);
        final File file = new File(getPath("InputSuppressionXpathFilterEscape.java"));

        final LocalizedMessage messageQuot = new LocalizedMessage(5, 37, TokenTypes.PLUS, "", "",
                null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent evQuot = new TreeWalkerAuditEvent(null,
                "Test.java", messageQuot, JavaParser.parseFile(file,
                JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse(filter.accept(evQuot), "TreeWalker audit event should be rejected");

        final LocalizedMessage messageLess = new LocalizedMessage(8, 33, TokenTypes.PLUS, "", "",
                null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent evLess = new TreeWalkerAuditEvent(null,
                "Test.java", messageLess, JavaParser.parseFile(file,
                JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse(filter.accept(evLess), "TreeWalker audit event should be rejected");

        final LocalizedMessage messageAmpersand = new LocalizedMessage(11, 38, TokenTypes.PLUS,
                "", "", null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent evAmpersand = new TreeWalkerAuditEvent(null,
                "Test.java", messageAmpersand, JavaParser.parseFile(file,
                JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse(filter.accept(evAmpersand), "TreeWalker audit event should be rejected");

        final LocalizedMessage messageGreater = new LocalizedMessage(14, 36, TokenTypes.PLUS,
                "", "", null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent evGreater = new TreeWalkerAuditEvent(null,
                "Test.java", messageGreater, JavaParser.parseFile(file,
                JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse(filter.accept(evGreater), "TreeWalker audit event should be rejected");

        final LocalizedMessage messageNewLine = new LocalizedMessage(17, 37, TokenTypes.PLUS,
                "", "", null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent evNewLine = new TreeWalkerAuditEvent(null,
                "Test.java", messageNewLine, JavaParser.parseFile(file,
                JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse(filter.accept(evNewLine), "TreeWalker audit event should be rejected");

        final LocalizedMessage messageSpecial = new LocalizedMessage(20, 37, TokenTypes.PLUS,
                "", "", null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent evSpecial = new TreeWalkerAuditEvent(null,
                "Test.java", messageSpecial, JavaParser.parseFile(file,
                JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse(filter.accept(evSpecial), "TreeWalker audit event should be rejected");

        final LocalizedMessage messageApos = new LocalizedMessage(23, 34, TokenTypes.PLUS,
                "", "", null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent evApos = new TreeWalkerAuditEvent(null,
                "Test.java", messageApos, JavaParser.parseFile(file,
                JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse(filter.accept(evApos), "TreeWalker audit event should be rejected");
    }

    @Test
    public void testFalseEncodeChar() throws Exception {
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(
                getPath("InputSuppressionXpathFilterEscape.xml"), optional);
        final File file = new File(getPath("InputSuppressionXpathFilterEscape.java"));

        final LocalizedMessage messageAmpChar = new LocalizedMessage(26, 13,
                TokenTypes.CHAR_LITERAL, "", "",
                null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent evAmpChar = new TreeWalkerAuditEvent(null,
                "Test.java", messageAmpChar, JavaParser.parseFile(file,
                JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse(filter.accept(evAmpChar), "TreeWalker audit event should be rejected");

        final LocalizedMessage messageQuoteChar = new LocalizedMessage(28, 13,
                TokenTypes.CHAR_LITERAL, "", "",
                null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent evQuoteChar = new TreeWalkerAuditEvent(null,
                "Test.java", messageQuoteChar, JavaParser.parseFile(file,
                JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse(filter.accept(evQuoteChar), "TreeWalker audit event should be rejected");

        final LocalizedMessage messageAposChar = new LocalizedMessage(30, 13,
                TokenTypes.CHAR_LITERAL, "", "",
                null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent evAposChar = new TreeWalkerAuditEvent(null,
                "Test.java", messageAposChar, JavaParser.parseFile(file,
                JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse(filter.accept(evAposChar), "TreeWalker audit event should be rejected");

        final LocalizedMessage messageLessChar = new LocalizedMessage(32, 13,
                TokenTypes.CHAR_LITERAL, "", "",
                null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent evLessChar = new TreeWalkerAuditEvent(null,
                "Test.java", messageLessChar, JavaParser.parseFile(file,
                JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse(filter.accept(evLessChar), "TreeWalker audit event should be rejected");

        final LocalizedMessage messageGreaterChar = new LocalizedMessage(34, 13,
                TokenTypes.CHAR_LITERAL, "", "",
                null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent evGreaterChar = new TreeWalkerAuditEvent(null,
                "Test.java", messageGreaterChar, JavaParser.parseFile(file,
                JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse(filter.accept(evGreaterChar), "TreeWalker audit event should be rejected");
    }
}
