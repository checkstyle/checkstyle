////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.MSG_CONTAINS_TAB;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.MSG_FILE_CONTAINS_TAB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class SuppressWithPlainTextCommentFilterTest extends AbstractModuleTestSupport {

    private static final String MSG_REGEXP_EXCEEDED = "regexp.exceeded";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithplaintextcommentfilter";
    }

    @Test
    public void testFilterWithDefaultConfig() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);

        final DefaultConfiguration checkCfg = createModuleConfig(FileTabCharacterCheck.class);
        checkCfg.addAttribute("eachLine", "true");

        final String[] suppressed = {
            "5:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_FILE_CONTAINS_TAB),
            "10:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        final String[] violationMessages = {
            "5:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_FILE_CONTAINS_TAB),
            "8:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "10:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilterWithDefaultCfg.java",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, checkCfg
        );
    }

    @Test
    public void testChangeOffAndOnFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addAttribute("onCommentFormat", "cs-on");
        filterCfg.addAttribute("offCommentFormat", "cs-off");

        final DefaultConfiguration checkCfg = createModuleConfig(FileTabCharacterCheck.class);
        checkCfg.addAttribute("eachLine", "true");

        final String[] suppressed = {
            "5:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_FILE_CONTAINS_TAB),
            "10:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "11:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "13:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        final String[] violationMessage = {
            "5:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_FILE_CONTAINS_TAB),
            "8:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "10:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "11:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilterWithCustomOnAndOffComments.java",
            removeSuppressed(violationMessage, suppressed),
            filterCfg, checkCfg
        );
    }

    @Test
    public void testSuppressionCommentsInXmlFile() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addAttribute("offCommentFormat", "CS-OFF");
        filterCfg.addAttribute("onCommentFormat", "CS-ON");

        final DefaultConfiguration checkCfg = createModuleConfig(FileTabCharacterCheck.class);
        checkCfg.addAttribute("eachLine", "true");

        final String[] suppressed = {
            "7:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        final String[] violationMessages = {
            "7:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "10:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilter.xml",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, checkCfg
        );
    }

    @Test
    public void testSuppressionCommentsInPropertiesFile() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addAttribute("offCommentFormat", "# CHECKSTYLE:OFF");
        filterCfg.addAttribute("onCommentFormat", "# CHECKSTYLE:ON");

        final DefaultConfiguration checkCfg = createModuleConfig(RegexpSinglelineCheck.class);
        checkCfg.addAttribute("format", "^key[0-9]=$");

        final String[] suppressed = {
            "2: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^key[0-9]=$"),
        };

        final String[] violationMessages = {
            "2: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^key[0-9]=$"),
            "4: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^key[0-9]=$"),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilter.properties",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, checkCfg
        );
    }

    @Test
    public void testSuppressionCommentsInSqlFile() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addAttribute("offCommentFormat", "-- CHECKSTYLE OFF");
        filterCfg.addAttribute("onCommentFormat", "-- CHECKSTYLE ON");

        final DefaultConfiguration checkCfg = createModuleConfig(FileTabCharacterCheck.class);
        checkCfg.addAttribute("eachLine", "true");

        final String[] suppressed = {
            "2:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        final String[] violationMessages = {
            "2:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "5:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilter.sql",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, checkCfg
        );
    }

    @Test
    public void testSuppressionCommentsInJavaScriptFile() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addAttribute("offCommentFormat", "// CS-OFF");
        filterCfg.addAttribute("onCommentFormat", "// CS-ON");

        final DefaultConfiguration checkCfg = createModuleConfig(RegexpSinglelineCheck.class);
        checkCfg.addAttribute("format", ".*===.*");

        final String[] suppressed = {
            "2: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED, ".*===.*"),
        };

        final String[] violationMessages = {
            "2: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED, ".*===.*"),
            "5: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED, ".*===.*"),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilter.js",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, checkCfg
        );
    }

    @Test
    public void testInvalidCheckFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addAttribute("checkFormat", "e[l");
        filterCfg.addAttribute("onCommentFormat", "// cs-on");
        filterCfg.addAttribute("offCommentFormat", "// cs-off");

        final DefaultConfiguration checkCfg = createModuleConfig(FileTabCharacterCheck.class);
        checkCfg.addAttribute("eachLine", "true");

        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        final String[] violationMessages = {
            "5:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_FILE_CONTAINS_TAB),
            "8:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "10:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        try {
            verifySuppressed(
                "InputSuppressWithPlainTextCommentFilterWithCustomOnAndOffComments.java",
                removeSuppressed(violationMessages, suppressed),
                filterCfg, checkCfg
            );
            fail("CheckstyleException is expected");
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertEquals("Invalid exception message",
                "unable to parse expanded comment e[l", cause.getMessage());
        }
    }

    @Test
    public void testInvalidMessageFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addAttribute("messageFormat", "e[l");
        filterCfg.addAttribute("onCommentFormat", "// cs-on");
        filterCfg.addAttribute("offCommentFormat", "// cs-off");

        final DefaultConfiguration checkCfg = createModuleConfig(FileTabCharacterCheck.class);
        checkCfg.addAttribute("eachLine", "true");

        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        final String[] violationMessages = {
            "5:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_FILE_CONTAINS_TAB),
            "8:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "10:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        try {
            verifySuppressed(
                "InputSuppressWithPlainTextCommentFilterWithCustomOnAndOffComments.java",
                removeSuppressed(violationMessages, suppressed),
                filterCfg, checkCfg
            );
            fail("CheckstyleException is expected");
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertEquals("Invalid exception message",
                "unable to parse expanded comment e[l", cause.getMessage());
        }
    }

    @Test
    public void testAcceptNullLocalizedMessage() {
        final SuppressWithPlainTextCommentFilter filter = new SuppressWithPlainTextCommentFilter();
        final AuditEvent auditEvent = new AuditEvent(this);
        assertTrue("Filter should accept audit event", filter.accept(auditEvent));
        Assert.assertNull("File name should not be null", auditEvent.getFileName());
    }

    @Test
    public void testEqualsAndHashCodeOfTagClass() {
        final EqualsVerifierReport ev = EqualsVerifier
                .forClass(SuppressWithPlainTextCommentFilter.Suppression.class).usingGetClass()
                .report();
        assertEquals("Error: " + ev.getMessage(), EqualsVerifierReport.SUCCESS, ev);
    }

    @Test
    public void testSuppressByModuleId() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addAttribute("offCommentFormat", "CSOFF (\\w+) \\(\\w+\\)");
        filterCfg.addAttribute("onCommentFormat", "CSON (\\w+)");
        filterCfg.addAttribute("checkFormat", "$1");

        final DefaultConfiguration regexpCheckCfg = createModuleConfig(RegexpSinglelineCheck.class);
        regexpCheckCfg.addAttribute("id", "ignore");
        regexpCheckCfg.addAttribute("format", ".*[a-zA-Z][0-9].*");

        final DefaultConfiguration fileTabCheckCfg =
            createModuleConfig(FileTabCharacterCheck.class);
        fileTabCheckCfg.addAttribute("eachLine", "true");
        fileTabCheckCfg.addAttribute("id", "foo");

        final String[] suppressedViolationMessages = {
            "6: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "9: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "11: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
        };

        final String[] expectedViolationMessages = {
            "6: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "9:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "9: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "11: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "14: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilterSuppressById.java",
            removeSuppressed(expectedViolationMessages, suppressedViolationMessages),
            filterCfg, regexpCheckCfg, fileTabCheckCfg
        );
    }

    @Test
    public void testSuppressByModuleIdWithNullModuleId() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addAttribute("offCommentFormat", "CSOFF (\\w+) \\(\\w+\\)");
        filterCfg.addAttribute("onCommentFormat", "CSON (\\w+)");
        filterCfg.addAttribute("checkFormat", "$1");

        final DefaultConfiguration regexpCheckCfg = createModuleConfig(RegexpSinglelineCheck.class);
        regexpCheckCfg.addAttribute("id", "ignore");
        regexpCheckCfg.addAttribute("format", ".*[a-zA-Z][0-9].*");

        final DefaultConfiguration fileTabCheckCfg =
            createModuleConfig(FileTabCharacterCheck.class);
        fileTabCheckCfg.addAttribute("eachLine", "true");
        fileTabCheckCfg.addAttribute("id", null);

        final String[] suppressedViolationMessages = {
            "6: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "9: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "11: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            };

        final String[] expectedViolationMessages = {
            "6: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "9:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "9: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "11: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "14: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilterSuppressById.java",
            removeSuppressed(expectedViolationMessages, suppressedViolationMessages),
            filterCfg, regexpCheckCfg, fileTabCheckCfg
        );
    }

    @Test
    public void testAcceptThrowsIllegalStateExceptionAsFileNotFound() {
        final LocalizedMessage message = new LocalizedMessage(1, 1, 1, TokenTypes.CLASS_DEF,
            "messages.properties", "key", null, SeverityLevel.ERROR, null, getClass(), null);
        final String fileName = "nonexisting_file";
        final AuditEvent auditEvent = new AuditEvent(this, fileName, message);

        final SuppressWithPlainTextCommentFilter filter = new SuppressWithPlainTextCommentFilter();

        try {
            filter.accept(auditEvent);
            fail(IllegalStateException.class.getSimpleName() + " is expected");
        }
        catch (IllegalStateException ex) {
            assertEquals("Invalid exception message",
                "Cannot read source file: " + fileName, ex.getMessage());

            final Throwable cause = ex.getCause();
            assertTrue("Exception cause has invalid type",
                cause instanceof FileNotFoundException);
            assertEquals("Invalid exception message",
                fileName + " (No such file or directory)", cause.getMessage());
        }
    }

    @Test
    public void testFilterWithCustomMessageFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        final String messageFormat =
            ".*" + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB) + ".*";
        // -@cs[CheckstyleTestMakeup] need to test dynamic property
        filterCfg.addAttribute("messageFormat", messageFormat);

        final DefaultConfiguration fileTabCheckCfg =
            createModuleConfig(FileTabCharacterCheck.class);
        fileTabCheckCfg.addAttribute("eachLine", "true");

        final DefaultConfiguration regexpCheckCfg = createModuleConfig(RegexpSinglelineCheck.class);
        regexpCheckCfg.addAttribute("id", "ignore");
        regexpCheckCfg.addAttribute("format", ".*[a-zA-Z][0-9].*");

        final String[] suppressed = {
            "8:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        final String[] violationMessages = {
            "6: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "8:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "8: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "10: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "13: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilterCustomMessageFormat.java",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, fileTabCheckCfg, regexpCheckCfg
        );
    }

    @Test
    public void testFilterWithDirectory() throws IOException {
        final SuppressWithPlainTextCommentFilter filter = new SuppressWithPlainTextCommentFilter();
        final AuditEvent event = new AuditEvent(this, getPath(""), new LocalizedMessage(1, 1,
                "bundle", "key", null, SeverityLevel.ERROR, "moduleId", getClass(),
                "customMessage"));

        assertTrue("filter should accept directory", filter.accept(event));
    }

    private void verifySuppressed(String fileNameWithExtension, String[] violationMessages,
                                  Configuration... childConfigs) throws Exception {
        final DefaultConfiguration checkerConfig = createRootConfig(null);

        Arrays.stream(childConfigs).forEach(checkerConfig::addChild);

        final String fileExtension = CommonUtil.getFileExtension(fileNameWithExtension);
        checkerConfig.addAttribute("fileExtensions", fileExtension);

        verify(checkerConfig, getPath(fileNameWithExtension), violationMessages);
    }

    private static String[] removeSuppressed(String[] from, String... remove) {
        final Collection<String> coll = Arrays.stream(from).collect(Collectors.toList());
        coll.removeAll(Arrays.asList(remove));
        return coll.toArray(CommonUtil.EMPTY_STRING_ARRAY);
    }

}
