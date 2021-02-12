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
import java.io.IOException;
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

    private LocalizedMessage getMessageForStringLiteral(int lineNo, int coloumnNo) {
        return new LocalizedMessage(lineNo, coloumnNo, TokenTypes.PLUS, "", "",
                null, null, "777", getClass(), null);
    }

    private LocalizedMessage getMessageForCharLiteral(int lineNo, int coloumnNo) {
        return new LocalizedMessage(lineNo, coloumnNo, TokenTypes.CHAR_LITERAL, "", "",
                null, null, "777", getClass(), null);
    }

    private TreeWalkerAuditEvent getTreeWalkerAuditForString(LocalizedMessage messageQuot,
                                                             File file)
            throws IOException, CheckstyleException {
        return new TreeWalkerAuditEvent(null, "Test.java", messageQuot,
                JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
    }

    @Test
    public void testFalseEncodeString() throws Exception {
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(
                getPath("InputSuppressionXpathFilterEscapeString.xml"), optional);
        final File file = new File(getPath("InputSuppressionXpathFilterEscapeString.java"));

        final LocalizedMessage messageQuot = getMessageForStringLiteral(8, 37);

        assertFalse(filter.accept(getTreeWalkerAuditForString(messageQuot, file)),
                "TreeWalker audit event should be rejected");

        final LocalizedMessage messageLess = getMessageForStringLiteral(11, 33);
        assertFalse(filter.accept(getTreeWalkerAuditForString(messageLess, file)),
                "TreeWalker audit event should be rejected");

        final LocalizedMessage messageAmpersand = getMessageForStringLiteral(14, 38);
        assertFalse(filter.accept(getTreeWalkerAuditForString(messageAmpersand, file)),
                "TreeWalker audit event should be rejected");

        final LocalizedMessage messageGreater = getMessageForStringLiteral(17, 36);
        assertFalse(filter.accept(getTreeWalkerAuditForString(messageGreater, file)),
                "TreeWalker audit event should be rejected");

        final LocalizedMessage messageNewLine = getMessageForStringLiteral(20, 37);
        assertFalse(filter.accept(getTreeWalkerAuditForString(messageNewLine, file)),
                "TreeWalker audit event should be rejected");

        final LocalizedMessage messageSpecial = getMessageForStringLiteral(23, 37);
        assertFalse(filter.accept(getTreeWalkerAuditForString(messageSpecial, file)),
                "TreeWalker audit event should be rejected");

        final LocalizedMessage messageApos = getMessageForStringLiteral(26, 34);
        assertFalse(filter.accept(getTreeWalkerAuditForString(messageApos, file)),
                "TreeWalker audit event should be rejected");
    }

    @Test
    public void testFalseEncodeChar() throws Exception {
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(
                getPath("InputSuppressionXpathFilterEscapeChar.xml"), optional);
        final File file = new File(getPath("InputSuppressionXpathFilterEscapeChar.java"));

        final LocalizedMessage messageAmpChar = getMessageForCharLiteral(8, 13);
        assertFalse(filter.accept(getTreeWalkerAuditForString(messageAmpChar, file)),
                "TreeWalker audit event should be rejected");

        final LocalizedMessage messageQuoteChar = getMessageForCharLiteral(10, 13);
        assertFalse(filter.accept(getTreeWalkerAuditForString(messageQuoteChar, file)),
                "TreeWalker audit event should be rejected");

        final LocalizedMessage messageAposChar = getMessageForCharLiteral(12, 13);
        assertFalse(filter.accept(getTreeWalkerAuditForString(messageAposChar, file)),
                "TreeWalker audit event should be rejected");

        final LocalizedMessage messageLessChar = getMessageForCharLiteral(14, 13);
        assertFalse(filter.accept(getTreeWalkerAuditForString(messageLessChar, file)),
                "TreeWalker audit event should be rejected");

        final LocalizedMessage messageGreaterChar = getMessageForCharLiteral(16, 13);
        assertFalse(filter.accept(getTreeWalkerAuditForString(messageGreaterChar, file)),
                "TreeWalker audit event should be rejected");
    }
}
