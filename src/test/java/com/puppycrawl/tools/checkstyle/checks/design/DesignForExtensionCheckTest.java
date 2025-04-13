///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.design.DesignForExtensionCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class DesignForExtensionCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/designforextension";
    }

    @Test
    public void testGetRequiredTokens() {
        final DesignForExtensionCheck checkObj = new DesignForExtensionCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testIt() throws Exception {
        final String[] expected = {
            "50:5: " + getCheckMessage(MSG_KEY, "InputDesignForExtension", "doh"),
            "104:9: " + getCheckMessage(MSG_KEY, "anotherNonFinalClass", "someMethod"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtension.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final DesignForExtensionCheck obj = new DesignForExtensionCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertWithMessage("Default acceptable tokens are invalid")
            .that(obj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testOverridableMethods() throws Exception {
        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_KEY, "A", "foo1"),
            "38:9: " + getCheckMessage(MSG_KEY, "A", "foo8"),
            "43:9: " + getCheckMessage(MSG_KEY, "A", "foo9"),
            "50:9: " + getCheckMessage(MSG_KEY, "A", "foo10"),
            "57:9: " + getCheckMessage(MSG_KEY, "A", "foo11"),
            "62:9: " + getCheckMessage(MSG_KEY, "A", "foo12"),
            "69:9: " + getCheckMessage(MSG_KEY, "A", "foo13"),
            "76:9: " + getCheckMessage(MSG_KEY, "A", "foo14"),
            "98:9: " + getCheckMessage(MSG_KEY, "A", "foo22"),
            "104:9: " + getCheckMessage(MSG_KEY, "A", "foo23"),
            "113:9: " + getCheckMessage(MSG_KEY, "A", "foo25"),
            "118:9: " + getCheckMessage(MSG_KEY, "A", "foo26"),
            "125:9: " + getCheckMessage(MSG_KEY, "A", "foo27"),
            "137:9: " + getCheckMessage(MSG_KEY, "A", "foo29"),
            "159:9: " + getCheckMessage(MSG_KEY, "A", "foo31"),
            "170:9: " + getCheckMessage(MSG_KEY, "A", "foo33"),
            "176:9: " + getCheckMessage(MSG_KEY, "A", "foo34"),
            "198:9: " + getCheckMessage(MSG_KEY, "A", "foo39"),
            "205:9: " + getCheckMessage(MSG_KEY, "A", "foo41"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionOverridableMethods.java"), expected);
    }

    @Test
    public void testIgnoredAnnotationsOption() throws Exception {
        final String[] expected = {
            "39:5: "
                + getCheckMessage(MSG_KEY, "InputDesignForExtensionIgnoredAnnotations", "foo1"),
            "149:5: "
                + getCheckMessage(MSG_KEY, "InputDesignForExtensionIgnoredAnnotations", "foo21"),
            "154:5: "
                + getCheckMessage(MSG_KEY, "InputDesignForExtensionIgnoredAnnotations", "setAge"),
            "169:5: "
                + getCheckMessage(MSG_KEY, "InputDesignForExtensionIgnoredAnnotations", "foo24"),
            "176:5: "
                + getCheckMessage(MSG_KEY, "InputDesignForExtensionIgnoredAnnotations", "dontUse4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionIgnoredAnnotations.java"), expected);
    }

    @Test
    public void testIgnoreAnnotationsOptionWithMultipleAnnotations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionMultipleAnnotations.java"), expected);
    }

    @Test
    public void testNativeMethods() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_KEY, "InputDesignForExtensionNativeMethods", "foo1"),
            "32:5: " + getCheckMessage(MSG_KEY, "InputDesignForExtensionNativeMethods", "foo6"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionNativeMethods.java"), expected);
    }

    @Test
    public void testDesignForExtensionRecords() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputDesignForExtensionRecords.java"), expected);
    }

    @Test
    public void testRequiredJavadocPhrase() throws Exception {
        final String className = "InputDesignForExtensionRequiredJavadocPhrase";
        final String[] expected = {
            "41:5: " + getCheckMessage(MSG_KEY, className, "foo5"),
            "48:5: " + getCheckMessage(MSG_KEY, className, "foo8"),
            "51:5: " + getCheckMessage(MSG_KEY, className, "foo9"),
            "67:5: " + getCheckMessage(MSG_KEY, className, "foo12"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionRequiredJavadocPhrase.java"), expected);
    }

    @Test
    public void testRequiredJavadocPhraseMultiLine() throws Exception {
        final String className = "InputDesignForExtensionRequiredJavadocPhraseMultiLine";
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_KEY, className, "foo2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionRequiredJavadocPhraseMultiLine.java"),
            expected);
    }

    @Test
    public void testInterfaceMemberScopeIsPublic() throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_KEY, "Inner", "getProperty"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionInterfaceMemberScopeIsPublic.java"),
            expected);
    }

}
