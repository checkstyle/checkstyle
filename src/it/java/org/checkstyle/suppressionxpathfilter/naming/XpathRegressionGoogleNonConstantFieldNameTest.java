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

package org.checkstyle.suppressionxpathfilter.naming;

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.naming.GoogleNonConstantFieldNameCheck;

public class XpathRegressionGoogleNonConstantFieldNameTest extends AbstractXpathTestSupport {

    private static final Class<GoogleNonConstantFieldNameCheck> CLASS =
            GoogleNonConstantFieldNameCheck.class;

    @Override
    protected String getCheckName() {
        return CLASS.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/naming/googlenonconstantfieldname";
    }

    @Test
    public void testNonConstantFieldName() throws Exception {
        final String[] expectedViolation = {
            "11:9: " + getCheckMessage(CLASS,
                    GoogleNonConstantFieldNameCheck.MSG_KEY_INVALID_FORMAT,
                    "Foo"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathGoogleNonConstantFieldName']]"
                + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='Foo']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathGoogleNonConstantFieldName.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testInnerClassField() throws Exception {
        final String[] expectedViolation = {
            "12:14: " + getCheckMessage(CLASS,
                    GoogleNonConstantFieldNameCheck.MSG_KEY_INVALID_FORMAT,
                    "gradle_9_5_1"),
        };
        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/INTERFACE_DEF"
                + "[./IDENT[@text='InputXpathGoogleNonConstantFieldNameInnerClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='gradle_9_5_1']"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathGoogleNonConstantFieldNameInnerClass.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testAnonymousClassField() throws Exception {
        final String[] expectedViolation = {
            "12:13: " + getCheckMessage(CLASS,
                    GoogleNonConstantFieldNameCheck.MSG_KEY_INVALID_FORMAT,
                    "f"),
        };
        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathGoogleNonConstantFieldNameAnonymousClass']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='run']]/ASSIGN/EXPR"
                + "/LITERAL_NEW[./IDENT[@text='Runnable']]/OBJBLOCK/VARIABLE_DEF/IDENT[@text='f']"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathGoogleNonConstantFieldNameAnonymousClass.java"),
                expectedXpathQueries, expectedViolation);
    }

}
