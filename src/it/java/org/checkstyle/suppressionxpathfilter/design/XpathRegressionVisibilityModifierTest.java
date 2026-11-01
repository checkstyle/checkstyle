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

package org.checkstyle.suppressionxpathfilter.design;

import static com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck.MSG_KEY;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck;

public class XpathRegressionVisibilityModifierTest extends AbstractXpathTestSupport {

    private final String checkName = VisibilityModifierCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/design/visibilitymodifier";
    }

    @Test
    public void testDefaultModifier() throws Exception {
        final String[] expectedViolation = {
            "28:9: " + getCheckMessage(VisibilityModifierCheck.class, MSG_KEY, "field"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathVisibilityModifierDefault']]"
                        + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='field']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathVisibilityModifierDefault.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testAnnotation() throws Exception {
        final String[] expectedViolation = {
            "26:12: " + getCheckMessage(VisibilityModifierCheck.class, MSG_KEY,
                    "annotatedString"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathVisibilityModifierAnnotation']]"
                        + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='annotatedString']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathVisibilityModifierAnnotation.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testAnonymousClass() throws Exception {
        final String[] expectedViolation = {
            "28:23: " + getCheckMessage(VisibilityModifierCheck.class, MSG_KEY, "field1"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathVisibilityModifierAnonymous']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='runnable']]"
                        + "/ASSIGN/EXPR/LITERAL_NEW[./IDENT[@text='Runnable']]"
                        + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='field1']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathVisibilityModifierAnonymous.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testInnerClass() throws Exception {
        final String[] expectedViolation = {
            "29:20: " + getCheckMessage(VisibilityModifierCheck.class, MSG_KEY, "field2"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathVisibilityModifierInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK/"
                        + "VARIABLE_DEF/IDENT[@text='field2']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathVisibilityModifierInner.java"),
                expectedXpathQueries, expectedViolation);
    }

}
