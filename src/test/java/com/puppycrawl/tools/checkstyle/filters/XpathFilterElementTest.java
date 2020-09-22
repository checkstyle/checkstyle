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

package com.puppycrawl.tools.checkstyle.filters;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import net.sf.saxon.Configuration;
import net.sf.saxon.sxpath.XPathEvaluator;
import net.sf.saxon.sxpath.XPathExpression;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class XpathFilterElementTest extends AbstractModuleTestSupport {

    private File file;
    private FileContents fileContents;

    @BeforeEach
    public void setUp() throws Exception {
        file = new File(getPath("InputXpathFilterElementSuppressByXpath.java"));
        fileContents = new FileContents(new FileText(file,
                StandardCharsets.UTF_8.name()));
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/xpathfilterelement";
    }

    @Test
    public void testMatching() throws Exception {
        final String xpath = "/CLASS_DEF[./IDENT[@text='InputXpathFilterElementSuppressByXpath']]";
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "Test", null, null, xpath);
        final TreeWalkerAuditEvent ev = getEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertFalse(filter.accept(ev), "Event should be rejected");
    }

    @Test
    public void testNonMatchingTokenType() throws Exception {
        final String xpath = "//METHOD_DEF[./IDENT[@text='countTokens']]";
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "Test", null, null, xpath);
        final TreeWalkerAuditEvent ev = getEvent(4, 4,
                TokenTypes.CLASS_DEF);
        assertTrue(filter.accept(ev), "Event should be accepted");
    }

    @Test
    public void testNonMatchingLineNumber() throws Exception {
        final String xpath = "/CLASS_DEF[./IDENT[@text='InputXpathFilterElementSuppressByXpath']]";
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "Test", null, null, xpath);
        final TreeWalkerAuditEvent ev = getEvent(100, 0,
                TokenTypes.CLASS_DEF);
        assertTrue(filter.accept(ev), "Event should be accepted");
    }

    @Test
    public void testNonMatchingColumnNumber() throws Exception {
        final String xpath = "/CLASS_DEF[./IDENT[@text='InputXpathFilterElementSuppressByXpath']]";
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "Test", null, null, xpath);
        final TreeWalkerAuditEvent ev = getEvent(3, 100,
                TokenTypes.CLASS_DEF);
        assertTrue(filter.accept(ev), "Event should be accepted");
    }

    @Test
    public void testComplexQuery() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@text='pi'] and "
                + "../../IDENT[@text='countTokens']] "
                + "| //VARIABLE_DEF[./IDENT[@text='someVariable'] and ../../IDENT[@text='sum']]";
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "Test", null, null, xpath);
        final TreeWalkerAuditEvent eventOne = getEvent(5, 8,
                TokenTypes.VARIABLE_DEF);
        final TreeWalkerAuditEvent eventTwo = getEvent(10, 4,
                TokenTypes.VARIABLE_DEF);
        final TreeWalkerAuditEvent eventThree = getEvent(15, 8,
                TokenTypes.VARIABLE_DEF);
        assertFalse(filter.accept(eventOne), "Event should be rejected");
        assertTrue(filter.accept(eventTwo), "Event should be accepted");
        assertFalse(filter.accept(eventThree), "Event should be rejected");
    }

    @Test
    public void testInvalidCheckRegexp() {
        try {
            final Object test = new XpathFilterElement(
                    ".*", "e[l", ".*", "moduleId", "query");
            fail("Exception is expected but got " + test);
        }
        catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("Failed to initialise regular expression"),
                    "Message should be: Failed to initialise regular expression");
        }
    }

    @Test
    public void testIncorrectQuery() {
        final String xpath = "1@#";
        try {
            final Object test = new XpathFilterElement("InputXpathFilterElementSuppressByXpath",
                    "Test", null, null, xpath);
            fail("Exception is expected but got " + test);
        }
        catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("Unexpected xpath query"),
                    "Message should be: Unexpected xpath query");
        }
    }

    @Test
    public void testNoQuery() throws Exception {
        final TreeWalkerAuditEvent event = getEvent(15, 8,
                TokenTypes.VARIABLE_DEF);
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "Test", null, null, null);
        assertFalse(filter.accept(event), "Event should be accepted");
    }

    @Test
    public void testNullFileName() {
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "Test", null, null, null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null,
                null, null, null);
        assertTrue(filter.accept(ev), "Event should be accepted");
    }

    @Test
    public void testNonMatchingFileRegexp() throws Exception {
        final XpathFilterElement filter =
                new XpathFilterElement("NonMatchingRegexp", "Test", null, null, null);
        final TreeWalkerAuditEvent ev = getEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertTrue(filter.accept(ev), "Event should be accepted");
    }

    @Test
    public void testNonMatchingFilePattern() throws Exception {
        final Pattern pattern = Pattern.compile("NonMatchingRegexp");
        final XpathFilterElement filter =
                new XpathFilterElement(pattern, null, null, null, null);
        final TreeWalkerAuditEvent ev = getEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertTrue(filter.accept(ev), "Event should be accepted");
    }

    @Test
    public void testNonMatchingCheckRegexp() throws Exception {
        final XpathFilterElement filter =
                new XpathFilterElement(null, "NonMatchingRegexp", null, null, null);
        final TreeWalkerAuditEvent ev = getEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertTrue(filter.accept(ev), "Event should be accepted");
    }

    @Test
    public void testNonMatchingCheckPattern() throws Exception {
        final Pattern pattern = Pattern.compile("NonMatchingRegexp");
        final XpathFilterElement filter =
                new XpathFilterElement(null, pattern, null, null, null);
        final TreeWalkerAuditEvent ev = getEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertTrue(filter.accept(ev), "Event should be accepted");
    }

    @Test
    public void testNullLocalizedMessage() {
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "Test", null, null, null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null,
                file.getName(), null, null);
        assertTrue(filter.accept(ev), "Event should be accepted");
    }

    @Test
    public void testNonMatchingModuleId() throws Exception {
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "Test", null, "id19", null);
        final LocalizedMessage message =
                new LocalizedMessage(3, 0, TokenTypes.CLASS_DEF, "", "", null, null, "id20",
                        getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
        assertTrue(filter.accept(ev), "Event should be accepted");
    }

    @Test
    public void testMatchingModuleId() throws Exception {
        final String xpath = "/CLASS_DEF[./IDENT[@text='InputXpathFilterElementSuppressByXpath']]";
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "Test", null, "id19", xpath);
        final LocalizedMessage message =
                new LocalizedMessage(3, 0, TokenTypes.CLASS_DEF, "", "", null, null, "id19",
                        getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse(filter.accept(ev), "Event should be rejected");
    }

    @Test
    public void testNonMatchingChecks() throws Exception {
        final String xpath = "NON_MATCHING_QUERY";
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "NonMatchingRegexp", null, "id19", xpath);
        final LocalizedMessage message =
                new LocalizedMessage(3, 0, TokenTypes.CLASS_DEF, "", "", null, null, "id19",
                        getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
        assertTrue(filter.accept(ev), "Event should be accepted");
    }

    @Test
    public void testNonMatchingFileNameModuleIdAndCheck() throws Exception {
        final String xpath = "NON_MATCHING_QUERY";
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", null, null, null, xpath);
        final TreeWalkerAuditEvent ev = getEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertTrue(filter.accept(ev), "Event should be accepted");
    }

    @Test
    public void testNullModuleIdAndNonMatchingChecks() throws Exception {
        final String xpath = "NON_MATCHING_QUERY";
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "NonMatchingRegexp", null, null, xpath);
        final TreeWalkerAuditEvent ev = getEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertTrue(filter.accept(ev), "Event should be accepted");
    }

    @Test
    public void testDecideByMessage() throws Exception {
        final LocalizedMessage message = new LocalizedMessage(1, 0, TokenTypes.CLASS_DEF, "", "",
                null, null, null, getClass(), "Test");
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
        final XpathFilterElement filter1 = new XpathFilterElement(null, null, "Test", null, null);
        final XpathFilterElement filter2 = new XpathFilterElement(null, null, "Bad", null, null);
        assertFalse(filter1.accept(ev), "Message match");
        assertTrue(filter2.accept(ev), "Message not match");
    }

    @Test
    public void testThrowException() {
        final String xpath = "/CLASS_DEF[@text='InputXpathFilterElementSuppressByXpath']";
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "Test", null, null, xpath);
        final LocalizedMessage message =
                new LocalizedMessage(3, 0, TokenTypes.CLASS_DEF, "", "", null, null, "id19",
                        getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents,
                file.getName(), message, null);
        try {
            filter.accept(ev);
            fail("Exception is expected");
        }
        catch (IllegalStateException ex) {
            assertTrue(ex.getMessage().contains("Cannot initialize context and evaluate query"),
                    "Exception message does not match expected one");
        }
    }

    @Test
    public void testEqualsAndHashCode() throws Exception {
        final XPathEvaluator xpathEvaluator = new XPathEvaluator(Configuration.newConfiguration());
        final EqualsVerifierReport ev = EqualsVerifier.forClass(XpathFilterElement.class)
            .withPrefabValues(XPathExpression.class,
                xpathEvaluator.createExpression("//METHOD_DEF"),
                xpathEvaluator.createExpression("//VARIABLE_DEF"))
                .usingGetClass()
                .withIgnoredFields("fileRegexp", "checkRegexp", "messageRegexp", "xpathExpression")
                .report();
        assertWithMessage("Error: " + ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

    private TreeWalkerAuditEvent getEvent(int line, int column, int tokenType)
            throws Exception {
        final LocalizedMessage message =
                new LocalizedMessage(line, column, tokenType, "", "", null, null, null,
                        getClass(), null);
        return new TreeWalkerAuditEvent(fileContents, file.getName(), message,
                JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
    }

}
