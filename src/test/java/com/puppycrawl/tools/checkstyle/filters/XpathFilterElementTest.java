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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import net.sf.saxon.Configuration;
import net.sf.saxon.sxpath.XPathEvaluator;
import net.sf.saxon.sxpath.XPathExpression;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class XpathFilterElementTest extends AbstractModuleTestSupport {

    private File file;

    @BeforeEach
    public void setUp() throws Exception {
        file = new File(getPath("InputXpathFilterElementSuppressByXpath.java"));
    }

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/xpathfilterelement";
    }

    /**
     * This test should remain as a low level pure unit test, purely for coverage.
     * It tests defensive null-guard in {@link XpathFilterElement#accept} that can never
     * be reached through the pipeline, as it always produces non-null filenames and violations.
     */
    @Test
    public void testNullFileName() {
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "Test", null, null, null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null,
                null, null, null);
        assertWithMessage("Event should be accepted")
                .that(filter.accept(ev))
                .isTrue();
    }

    /**
     * This test should remain as a low level pure unit test, purely for coverage.
     * It tests defensive null-guard in {@link XpathFilterElement#accept} that can never
     * be reached through the pipeline, as it always produces non-null filenames and violations.
     */
    @Test
    public void testNullViolation() {
        final XpathFilterElement filter = new XpathFilterElement(
                "InputXpathFilterElementSuppressByXpath", "Test", null, null, null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null,
                file.getName(), null, null);
        assertWithMessage("Event should be accepted")
                .that(filter.accept(ev))
                .isTrue();
    }

    /**
     * This test should remain as a low level pure unit test.
     * It uses {@link EqualsVerifier} library to validate {@code equals()}
     * and {@code hashCode()} methods on all edge cases. This is a very
     * technical method with implementation by strict rules, not related
     * to checkstyle's target of validation.
     */
    @Test
    public void testEqualsAndHashCode() throws Exception {
        final XPathEvaluator xpathEvaluator = new XPathEvaluator(Configuration.newConfiguration());
        final EqualsVerifierReport ev = EqualsVerifier.forClass(XpathFilterElement.class)
            .withPrefabValues(XPathExpression.class,
                xpathEvaluator.createExpression("//METHOD_DEF"),
                xpathEvaluator.createExpression("//VARIABLE_DEF"))
                .usingGetClass()
                .withIgnoredFields("xpathExpression", "isEmptyConfig")
                .report();
        assertWithMessage("Error: %s", ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

}
