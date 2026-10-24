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

package org.checkstyle.suppressionxpathfilter.sizes;

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.sizes.RecordComponentNumberCheck;

public class XpathRegressionRecordComponentNumberTest extends AbstractXpathTestSupport {

    private final String checkName = RecordComponentNumberCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/sizes/recordcomponentnumber";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expectedViolation = {
            "12:1: " + getCheckMessage(RecordComponentNumberCheck.class,
                    RecordComponentNumberCheck.MSG_KEY,
                    15, 8),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/RECORD_DEF[./IDENT[@text='InputXpathRecordComponentNumberDefault']]",
            "/COMPILATION_UNIT/RECORD_DEF[./IDENT[@text='InputXpathRecordComponentNumberDefault']]"
                    + "/MODIFIERS",
            "/COMPILATION_UNIT/RECORD_DEF[./IDENT[@text='InputXpathRecordComponentNumberDefault']]"
                    + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRecordComponentNumberDefault.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testCustomMax() throws Exception {
        final String[] expectedViolation = {
            "13:5: " + getCheckMessage(RecordComponentNumberCheck.class,
                    RecordComponentNumberCheck.MSG_KEY,
                    2, 1),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathRecordComponentNumberCustomMax']]"
                    + "/OBJBLOCK/RECORD_DEF[./IDENT[@text='MyRecord']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathRecordComponentNumberCustomMax']]"
                    + "/OBJBLOCK/RECORD_DEF[./IDENT[@text='MyRecord']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathRecordComponentNumberCustomMax']]"
                    + "/OBJBLOCK/RECORD_DEF[./IDENT[@text='MyRecord']]/MODIFIERS"
                    + "/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRecordComponentNumberCustomMax.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInnerClass() throws Exception {
        final String[] expectedViolation = {
            "14:9: " + getCheckMessage(RecordComponentNumberCheck.class,
                    RecordComponentNumberCheck.MSG_KEY,
                    13, 8),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRecordComponentNumberInnerClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/RECORD_DEF[./IDENT[@text='MyRecord']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRecordComponentNumberInnerClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/RECORD_DEF[./IDENT[@text='MyRecord']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRecordComponentNumberInnerClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/RECORD_DEF[./IDENT[@text='MyRecord']]/MODIFIERS"
                + "/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathRecordComponentNumberInnerClass.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
