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

package org.checkstyle.suppressionxpathfilter.coding;

import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.NoFinalizerCheck;

public class XpathRegressionNoFinalizerTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return NoFinalizerCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/nofinalizer";
    }

    @Test
    public void testMain() throws Exception {
        final String[] expectedViolation = {
            "14:5: " + getCheckMessage(NoFinalizerCheck.class,
                    NoFinalizerCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoFinalizerMain']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='finalize']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoFinalizerMain']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='finalize']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoFinalizerMain']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='finalize']]/MODIFIERS/LITERAL_PROTECTED"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNoFinalizerMain.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInner() throws Exception {
        final String[] expectedViolation = {
            "16:9: " + getCheckMessage(NoFinalizerCheck.class,
                    NoFinalizerCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoFinalizerInner']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK/"
                + "METHOD_DEF[./IDENT[@text='finalize']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoFinalizerInner']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK/"
                + "METHOD_DEF[./IDENT[@text='finalize']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoFinalizerInner']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK/"
                + "METHOD_DEF[./IDENT[@text='finalize']]/MODIFIERS/"
                + "ANNOTATION[./IDENT[@text='Override']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoFinalizerInner']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK/"
                + "METHOD_DEF[./IDENT[@text='finalize']]/MODIFIERS/"
                + "ANNOTATION[./IDENT[@text='Override']]/AT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNoFinalizerInner.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInEnum() throws Exception {
        final String[] expectedViolation = {
            "11:9: " + getCheckMessage(NoFinalizerCheck.class,
                    NoFinalizerCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/INTERFACE_DEF"
                + "[./IDENT[@text='InputXpathNoFinalizerInEnum']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='finalize']]",
                "/COMPILATION_UNIT/INTERFACE_DEF"
                + "[./IDENT[@text='InputXpathNoFinalizerInEnum']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='finalize']]/MODIFIERS",
                "/COMPILATION_UNIT/INTERFACE_DEF"
                + "[./IDENT[@text='InputXpathNoFinalizerInEnum']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='finalize']]/MODIFIERS/LITERAL_PROTECTED"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNoFinalizerInEnum.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
