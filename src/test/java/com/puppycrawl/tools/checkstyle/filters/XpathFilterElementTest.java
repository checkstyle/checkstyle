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

package com.puppycrawl.tools.checkstyle.filters;

import static com.google.common.truth.Truth.assertWithMessage;

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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;
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
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/xpathfilterelement";
    }

    @Test
    public void testNonMatchingFilePattern() throws Exception {
        final Pattern pattern = Pattern.compile("NonMatchingRegexp");
        final XpathFilterElement filter =
                new XpathFilterElement(pattern, null, null, null, null);
        final TreeWalkerAuditEvent ev = getEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertWithMessage("Event should be accepted")
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testNonMatchingCheckPattern() throws Exception {
        final Pattern pattern = Pattern.compile("NonMatchingRegexp");
        final XpathFilterElement filter =
                new XpathFilterElement(null, pattern, null, null, null);
        final TreeWalkerAuditEvent ev = getEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertWithMessage("Event should be accepted")
                .that(filter.accept(ev))
                .isTrue();
    }

    private TreeWalkerAuditEvent getEvent(int line, int column, int tokenType)
            throws Exception {
        final Violation message =
                new Violation(line, column, tokenType, "", "", null, null, null,
                        getClass(), null);
        return new TreeWalkerAuditEvent(fileContents, file.getName(), message,
                JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
    }

}
