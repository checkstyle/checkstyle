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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.design.DesignForExtensionCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class DesignForExtensionCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
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
    public void testMethods() throws Exception {
        final String[] expected = {
            "51:5: " + getCheckMessage(MSG_KEY, "InputDesignForExtensionMethods", "doh"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionMethods.java"), expected);
    }

    @Test
    public void testInnerClasses() throws Exception {
        final String[] expected = {
            "53:9: " + getCheckMessage(MSG_KEY, "anotherNonFinalClass", "someMethod"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionInnerClasses.java"), expected);
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
    public void testOverridableMethodsComments() throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_KEY, "A", "foo1"),
            "40:9: " + getCheckMessage(MSG_KEY, "A", "foo8"),
            "46:9: " + getCheckMessage(MSG_KEY, "A", "foo9"),
            "54:9: " + getCheckMessage(MSG_KEY, "A", "foo10"),
            "62:9: " + getCheckMessage(MSG_KEY, "A", "foo11"),
            "68:9: " + getCheckMessage(MSG_KEY, "A", "foo12"),
            "76:9: " + getCheckMessage(MSG_KEY, "A", "foo13"),
            "84:9: " + getCheckMessage(MSG_KEY, "A", "foo14"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionOverridableMethodsComments.java"), expected);
    }

    @Test
    public void testOverridableMethodsAnnotationsAndComments() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY, "A", "foo22"),
            "23:9: " + getCheckMessage(MSG_KEY, "A", "foo23"),
            "33:9: " + getCheckMessage(MSG_KEY, "A", "foo25"),
            "39:9: " + getCheckMessage(MSG_KEY, "A", "foo26"),
            "47:9: " + getCheckMessage(MSG_KEY, "A", "foo27"),
            "60:9: " + getCheckMessage(MSG_KEY, "A", "foo29"),
            "83:9: " + getCheckMessage(MSG_KEY, "A", "foo31"),
            "95:9: " + getCheckMessage(MSG_KEY, "A", "foo33"),
            "102:9: " + getCheckMessage(MSG_KEY, "A", "foo34"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionOverridableMethodsAnnotationsAndComments.java"),
                expected);
    }

    @Test
    public void testOverridableMethodsPackagePrivateAndClasses() throws Exception {
        final String[] expected = {
            "31:9: " + getCheckMessage(MSG_KEY, "A", "foo39"),
            "39:9: " + getCheckMessage(MSG_KEY, "A", "foo41"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionOverridableMethodsPackagePrivateAndClasses.java"),
                expected);
    }

    @Test
    public void testIgnoredAnnotationsOptionOverrideAndDeprecated() throws Exception {
        final String[] expected = {
            "40:5: "
                + getCheckMessage(MSG_KEY,
                    "InputDesignForExtensionIgnoredAnnotationsOverrideAndDeprecated", "foo1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionIgnoredAnnotationsOverrideAndDeprecated.java"),
                expected);
    }

    @Test
    public void testIgnoredAnnotationsOptionCustomAndMultiple() throws Exception {
        final String[] expected = {
            "61:5: "
                + getCheckMessage(MSG_KEY,
                    "InputDesignForExtensionIgnoredAnnotationsCustomAndMultiple", "foo21"),
            "67:5: "
                + getCheckMessage(MSG_KEY,
                    "InputDesignForExtensionIgnoredAnnotationsCustomAndMultiple", "setAge"),
            "83:5: "
                + getCheckMessage(MSG_KEY,
                    "InputDesignForExtensionIgnoredAnnotationsCustomAndMultiple", "foo24"),
            "91:5: "
                + getCheckMessage(MSG_KEY,
                    "InputDesignForExtensionIgnoredAnnotationsCustomAndMultiple", "dontUse4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionIgnoredAnnotationsCustomAndMultiple.java"),
                expected);
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
            "14:5: " + getCheckMessage(MSG_KEY, "InputDesignForExtensionNativeMethods", "foo1"),
            "31:5: " + getCheckMessage(MSG_KEY, "InputDesignForExtensionNativeMethods", "foo6"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionNativeMethods.java"), expected);
    }

    @Test
    public void testDesignForExtensionRecords() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionRecords.java"), expected);
    }

    @Test
    public void testRequiredJavadocPhrase() throws Exception {
        final String className = "InputDesignForExtensionRequiredJavadocPhrase";
        final String[] expected = {
            "42:5: " + getCheckMessage(MSG_KEY, className, "foo5"),
            "50:5: " + getCheckMessage(MSG_KEY, className, "foo8"),
            "54:5: " + getCheckMessage(MSG_KEY, className, "foo9"),
            "71:5: " + getCheckMessage(MSG_KEY, className, "foo12"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionRequiredJavadocPhrase.java"), expected);
    }

    @Test
    public void testRequiredJavadocPhraseMultiLine() throws Exception {
        final String className = "InputDesignForExtensionRequiredJavadocPhraseMultiLine";
        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_KEY, className, "foo2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionRequiredJavadocPhraseMultiLine.java"),
            expected);
    }

    @Test
    public void testInterfaceMemberScopeIsPublic() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY, "Inner", "getProperty"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionInterfaceMemberScopeIsPublic.java"),
            expected);
    }

}
