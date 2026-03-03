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

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyAloneOrEmptyCheck;

public class XpathRegressionRightCurlyAloneOrEmptyTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return RightCurlyAloneOrEmptyCheck.class.getSimpleName();
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathRightCurlyAloneOrEmpty.java"));
        final DefaultConfiguration moduleConfig =
            createModuleConfig(RightCurlyAloneOrEmptyCheck.class);
        final String[] expectedViolation = {
            "5:20: " + getCheckMessage(RightCurlyAloneOrEmptyCheck.class,
                RightCurlyAloneOrEmptyCheck.MSG_KEY_LINE_ALONE, "}", 20),
        };
        final List<String> expectedXpath = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyAloneOrEmpty']]"
                + "/OBJBLOCK/ANNOTATION_DEF[./IDENT[@text='TestAnnotation']]"
                + "/OBJBLOCK/RCURLY"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpath);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathRightCurlyAloneOrEmpty2.java"));
        final DefaultConfiguration moduleConfig =
            createModuleConfig(RightCurlyAloneOrEmptyCheck.class);
        final String[] expectedViolation = {
            "5:20: " + getCheckMessage(RightCurlyAloneOrEmptyCheck.class,
                RightCurlyAloneOrEmptyCheck.MSG_KEY_LINE_ALONE, "}", 20),
        };
        final List<String> expectedXpath = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyAloneOrEmpty2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                + "/SLIST/RCURLY"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpath);
    }

}
