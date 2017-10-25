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

package com.puppycrawl.tools.checkstyle.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class SuppressionXpathFilterTest extends AbstractModuleTestSupport {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

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

        assertTrue("TreeWalker audit event should be accepted when there are no suppressions",
                filter.accept(ev));
    }

    @Test
    public void testAcceptTwo() throws Exception {
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(
                getPath("InputSuppressionXpathFilterIdAndQuery.xml"), optional);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "file1.java", null, null);

        assertTrue("TreeWalker audit event should be accepted",
                filter.accept(ev));
    }

    @Test
    public void testAcceptOnNullFile() throws Exception {
        final String fileName = null;
        final boolean optional = false;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(fileName, optional);

        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "AnyJava.java", null, null);
        assertTrue("TreeWalker audit event on null file should be accepted, but was not",
                filter.accept(ev));
    }

    @Test
    public void testNonExistingSuppressionFileWithFalseOptional() throws Exception {
        final String fileName = getPath("non_existing_suppression_file.xml");
        try {
            final boolean optional = false;
            createSuppressionXpathFilter(fileName, optional);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Invalid error message",
                    "Unable to find: " + fileName, ex.getMessage());
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
            assertEquals("Invalid error message", "Unable to parse " + fileName
                    + " - invalid files or checks or message format for suppress-xpath",
                    ex.getMessage());
        }
    }

    @Test
    public void testExistingSuppressionFileWithTrueOptional() throws Exception {
        final boolean optional = true;
        final SuppressionXpathFilter filter =
                createSuppressionXpathFilter(getPath("InputSuppressionXpathFilterNone.xml"),
                        optional);

        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "AnyJava.java", null, null);

        assertTrue("Suppression file with true optional was not accepted",
                filter.accept(ev));
    }

    @Test
    public void testNonExistingSuppressionFileWithTrueOptional() throws Exception {
        final String fileName = "src/test/resources/com/puppycrawl/tools/checkstyle/filters/"
                + "non_existing_suppression_file.xml";
        final boolean optional = true;
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(fileName, optional);

        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null, "AnyFile.java", null, null);

        assertTrue("Should except event when suppression file does not exist",
                filter.accept(ev));
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
                message, TestUtil.parseFile(file));

        assertFalse("TreeWalker audit event should be rejected",
                filter.accept(ev));
    }

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier
                .forClass(SuppressionXpathFilter.class)
                .usingGetClass()
                .withIgnoredFields("file", "optional", "configuration")
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void testExternalResource() throws Exception {
        final boolean optional = false;
        final String fileName = getPath("InputSuppressionXpathFilterIdAndQuery.xml");
        final SuppressionXpathFilter filter = createSuppressionXpathFilter(fileName, optional);
        assertEquals("Invalid external resource",
                Collections.singleton(fileName),
                filter.getExternalResourceLocations());
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
}
