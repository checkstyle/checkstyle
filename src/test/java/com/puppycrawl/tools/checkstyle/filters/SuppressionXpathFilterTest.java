////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;
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

        assertWithMessage(
                    "TreeWalker audit event should be accepted when there are no suppressions")
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testAcceptTwo() throws Exception {
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(
                getPath("InputSuppressionXpathFilterIdAndQuery.xml"), optional);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "file1.java", null, null);

        assertWithMessage("TreeWalker audit event should be accepted")
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testAcceptOnNullFile() throws Exception {
        final String fileName = null;
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(fileName, optional);

        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "AnyJava.java", null, null);
        assertWithMessage("TreeWalker audit event on null file should be accepted, but was not")
                .that(filter.accept(ev))
                .isTrue();
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
        final boolean optional = true;
        final SuppressionXpathFilter filter =
                createSuppressionXpathFilter(getPath("InputSuppressionXpathFilterNone.xml"),
                        optional);

        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "AnyJava.java", null, null);

        assertWithMessage("Suppression file with true optional was not accepted")
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testNonExistentSuppressionFileWithTrueOptional() throws Exception {
        final String fileName = "src/test/resources/com/puppycrawl/tools/checkstyle/filters/"
                + "non_existent_suppression_file.xml";
        final boolean optional = true;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(fileName, optional);

        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "AnyFile.java", null, null);

        assertWithMessage("Should except event when suppression file does not exist")
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testReject() throws Exception {
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(
                        getPath("InputSuppressionXpathFilterIdAndQuery.xml"), optional);
        final File file = new File(getPath("InputSuppressionXpathFilter.java"));
        final Violation message = new Violation(3, 0, TokenTypes.CLASS_DEF, "", "",
                null, null, "777", getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "file1.java",
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(ev))
                .isFalse();
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
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(
                getPath("InputSuppressionXpathFilterEscapeString.xml"), optional);
        final File file = new File(getPath("InputSuppressionXpathFilterEscapeString.java"));

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(8, 23, TokenTypes.STRING_LITERAL, file)))
                .isFalse();

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(10, 22, TokenTypes.STRING_LITERAL, file)))
                .isFalse();

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(12, 27, TokenTypes.STRING_LITERAL, file)))
                .isFalse();

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(14, 25, TokenTypes.STRING_LITERAL, file)))
                .isFalse();

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(16, 25, TokenTypes.STRING_LITERAL, file)))
                .isFalse();

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(18, 25, TokenTypes.STRING_LITERAL, file)))
                .isFalse();

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(20, 22, TokenTypes.STRING_LITERAL, file)))
                .isFalse();

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(22, 28, TokenTypes.STRING_LITERAL, file)))
                .isFalse();

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(24, 28, TokenTypes.STRING_LITERAL, file)))
                .isFalse();
    }

    @Test
    public void testFalseEncodeChar() throws Exception {
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(
                getPath("InputSuppressionXpathFilterEscapeChar.xml"), optional);
        final File file = new File(getPath("InputSuppressionXpathFilterEscapeChar.java"));

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(8, 13, TokenTypes.CHAR_LITERAL, file)))
                .isFalse();

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(10, 13, TokenTypes.CHAR_LITERAL, file)))
                .isFalse();

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(12, 13, TokenTypes.CHAR_LITERAL, file)))
                .isFalse();

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(14, 13, TokenTypes.CHAR_LITERAL, file)))
                .isFalse();

        assertWithMessage("TreeWalker audit event should be rejected")
                .that(filter.accept(createTreeWalkerAudit(16, 13, TokenTypes.CHAR_LITERAL, file)))
                .isFalse();
    }

    private static TreeWalkerAuditEvent createTreeWalkerAudit(int lineNo, int columnNo,
                                                              int tokenTypes, File file)
            throws IOException, CheckstyleException {
        final Violation message = new Violation(lineNo, columnNo, tokenTypes, "", "",
                null, null, "777", SuppressionXpathFilterTest.class, null);
        return new TreeWalkerAuditEvent(null, "Test.java", message,
                JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
    }
}
