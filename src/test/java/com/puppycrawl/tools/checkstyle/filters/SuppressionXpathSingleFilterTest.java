////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class SuppressionXpathSingleFilterTest
        extends AbstractModuleTestSupport {

    private File file;
    private FileContents fileContents;

    @Before
    public void setUp() throws Exception {
        file = new File(getPath("InputSuppressionXpathSingleFilter.java"));
        fileContents = new FileContents(new FileText(file,
                StandardCharsets.UTF_8.name()));
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionxpathsinglefilter";
    }

    @Test
    public void testMatching() throws Exception {
        final String xpath = "/CLASS_DEF[@text='InputSuppressionXpathSingleFilter']";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertFalse("Event should be rejected", filter.accept(ev));
    }

    @Test
    public void testNonMatchingTokenType() throws Exception {
        final String xpath = "//METHOD_DEF[@text='countTokens']";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertTrue("Event should be accepted", filter.accept(ev));
    }

    @Test
    public void testNonMatchingLineNumber() throws Exception {
        final String xpath = "/CLASS_DEF[@text='InputSuppressionXpathSingleFilter']";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(100, 0,
                TokenTypes.CLASS_DEF);
        assertTrue("Event should be accepted", filter.accept(ev));
    }

    @Test
    public void testNonMatchingColumnNumber() throws Exception {
        final String xpath = "/CLASS_DEF[@text='InputSuppressionXpathSingleFilter']";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(3, 100,
                TokenTypes.CLASS_DEF);
        assertTrue("Event should be accepted", filter.accept(ev));
    }

    @Test
    public void testComplexQuery() throws Exception {
        final String xpath = "//VARIABLE_DEF[@text='pi' and "
                + "../..[@text='countTokens']] "
                + "| //VARIABLE_DEF[@text='someVariable' and ../..[@text='sum']]";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent eventOne = createEvent(5, 8,
                TokenTypes.VARIABLE_DEF);
        final TreeWalkerAuditEvent eventTwo = createEvent(10, 4,
                TokenTypes.VARIABLE_DEF);
        final TreeWalkerAuditEvent eventThree = createEvent(15, 8,
                TokenTypes.VARIABLE_DEF);
        assertFalse("Event should be rejected", filter.accept(eventOne));
        assertTrue("Event should be accepted", filter.accept(eventTwo));
        assertFalse("Event should be rejected", filter.accept(eventThree));
    }

    @Test
    public void testIncorrectQuery() {
        final String xpath = "1@#";
        try {
            final Object test = createSuppressionXpathSingleFilter(
                    "InputSuppressionXpathSingleFilter", "Test",
                    null, null, xpath);
            fail("Exception was expected but got " + test);
        }
        catch (IllegalArgumentException ex) {
            assertTrue("Message should be: Unexpected xpath query",
                    ex.getMessage().contains("Incorrect xpath query"));
        }
    }

    @Test
    public void testNoQuery() throws Exception {
        final TreeWalkerAuditEvent event = createEvent(15, 8,
                TokenTypes.VARIABLE_DEF);
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, null);
        assertFalse("Event should be accepted", filter.accept(event));
    }

    @Test
    public void testNullFileName() {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null,
                null, null, null);
        assertTrue("Event should be accepted", filter.accept(ev));
    }

    @Test
    public void testNonMatchingFileRegexp() throws Exception {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("NonMatchingRegexp", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertTrue("Event should be accepted", filter.accept(ev));
    }

    @Test
    public void testNullLocalizedMessage() {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null,
                file.getName(), null, null);
        assertTrue("Event should be accepted", filter.accept(ev));
    }

    @Test
    public void testNonMatchingModuleId() throws Exception {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, "id19", xpath);
        final LocalizedMessage message =
                new LocalizedMessage(3, 0, TokenTypes.CLASS_DEF, "", "",
                        null, null, "id20",
                        getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
        assertTrue("Event should be accepted", filter.accept(ev));
    }

    @Test
    public void testMatchingModuleId() throws Exception {
        final String xpath = "/CLASS_DEF[@text='InputSuppressionXpathSingleFilter']";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, "id19", xpath);
        final LocalizedMessage message =
                new LocalizedMessage(3, 0, TokenTypes.CLASS_DEF, "",
                        "", null, null, "id19",
                        getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
        assertFalse("Event should be rejected", filter.accept(ev));
    }

    @Test
    public void testNonMatchingChecks() throws Exception {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter = createSuppressionXpathSingleFilter(
                "InputSuppressionXpathSingleFilter", "NonMatchingRegexp",
                null, "id19", xpath);
        final LocalizedMessage message =
                new LocalizedMessage(3, 0, TokenTypes.CLASS_DEF, "",
                        "", null, null, "id19",
                        getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
        assertTrue("Event should be accepted", filter.accept(ev));
    }

    @Test
    public void testNonMatchingFileNameModuleIdAndCheck() throws Exception {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter",
                        null, null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertTrue("Event should be accepted", filter.accept(ev));
    }

    @Test
    public void testNullModuleIdAndNonMatchingChecks() throws Exception {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter = createSuppressionXpathSingleFilter(
                "InputSuppressionXpathSingleFilter", "NonMatchingRegexp", null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertTrue("Event should be accepted", filter.accept(ev));
    }

    @Test
    public void testDecideByMessage() throws Exception {
        final LocalizedMessage message = new LocalizedMessage(0, 0,
                TokenTypes.CLASS_DEF, "", "",
                null, null, null, getClass(), "Test");
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
        final SuppressionXpathSingleFilter filter1 = createSuppressionXpathSingleFilter(
                null, null, "Test", null, null);
        final SuppressionXpathSingleFilter filter2 = createSuppressionXpathSingleFilter(
                null, null, "Bad", null, null);
        assertFalse("Message match", filter1.accept(ev));
        assertTrue("Message not match", filter2.accept(ev));
    }

    @Test
    public void testThrowException() {
        final String xpath = "/CLASS_DEF[@text='InputSuppressionXpathSingleFilter']";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter",
                        "Test", null, null, xpath);
        final LocalizedMessage message =
                new LocalizedMessage(3, 0, TokenTypes.CLASS_DEF, "",
                        "", null, null, "id19",
                        getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents,
                file.getName(), message, null);
        try {
            filter.accept(ev);
            fail("Exception is expected");
        }
        catch (IllegalStateException ex) {
            assertTrue("Exception message does not match expected one",
                    ex.getMessage().contains("Cannot initialize context and evaluate query"));
        }
    }

    private static SuppressionXpathSingleFilter createSuppressionXpathSingleFilter(
            String files, String checks, String message, String moduleId, String query) {
        final SuppressionXpathSingleFilter filter = new SuppressionXpathSingleFilter();
        filter.setFiles(files);
        filter.setChecks(checks);
        filter.setMessage(message);
        filter.setId(moduleId);
        filter.setQuery(query);
        filter.finishLocalSetup();
        return filter;
    }

    private TreeWalkerAuditEvent createEvent(int line, int column, int tokenType)
            throws Exception {
        final LocalizedMessage message =
                new LocalizedMessage(line, column, tokenType, "", "", null, null, null,
                        getClass(), null);
        return new TreeWalkerAuditEvent(fileContents, file.getName(), message,
                JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
    }

}
