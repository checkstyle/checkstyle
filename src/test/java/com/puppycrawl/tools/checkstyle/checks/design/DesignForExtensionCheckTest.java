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
    public void testIt() throws Exception {
        final String[] expected = {
            "51:5: " + getCheckMessage(MSG_KEY, "InputDesignForExtension", "doh"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtension.java"), expected);
    }

    @Test
    public void testNonFinalClasses() throws Exception {
        final String[] expected = {
            "35:9: " + getCheckMessage(MSG_KEY, "anotherNonFinalClass", "someMethod"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionNonFinalClasses.java"), expected);
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
                getPath("InputDesignForExtensionOverridableMethods.java"), expected);
    }

    @Test
    public void testOverridableMethodsTwo() throws Exception {
        final String[] expected = {
            "35:9: " + getCheckMessage(MSG_KEY, "A", "foo22"),
            "42:9: " + getCheckMessage(MSG_KEY, "A", "foo23"),
            "52:9: " + getCheckMessage(MSG_KEY, "A", "foo25"),
            "58:9: " + getCheckMessage(MSG_KEY, "A", "foo26"),
            "66:9: " + getCheckMessage(MSG_KEY, "A", "foo27"),
            "79:9: " + getCheckMessage(MSG_KEY, "A", "foo29"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionOverridableMethodsTwo.java"), expected);
    }

    @Test
    public void testOverridableMethodsThree() throws Exception {
        final String[] expected = {
            "32:9: " + getCheckMessage(MSG_KEY, "A", "foo31"),
            "44:9: " + getCheckMessage(MSG_KEY, "A", "foo33"),
            "51:9: " + getCheckMessage(MSG_KEY, "A", "foo34"),
            "74:9: " + getCheckMessage(MSG_KEY, "A", "foo39"),
            "82:9: " + getCheckMessage(MSG_KEY, "A", "foo41"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionOverridableMethodsThree.java"), expected);
    }

    @Test
    public void testOverridableMethodsFour() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionOverridableMethodsFour.java"), expected);
    }

    @Test
    public void testIgnoredAnnotationsOption() throws Exception {
        final String[] expected = {
            "40:5: "
                + getCheckMessage(MSG_KEY, "InputDesignForExtensionIgnoredAnnotations", "foo1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionIgnoredAnnotations.java"), expected);
    }

    @Test
    public void testIgnoredAnnotationsOptionTwo() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionIgnoredAnnotationsTwo.java"), expected);
    }

    @Test
    public void testIgnoredAnnotationsOptionThree() throws Exception {
        final String className = "InputDesignForExtensionIgnoredAnnotationsThree";
        final String[] expected = {
            "40:5: " + getCheckMessage(MSG_KEY, className, "foo21"),
            "46:5: " + getCheckMessage(MSG_KEY, className, "setAge"),
            "62:5: " + getCheckMessage(MSG_KEY, className, "foo24"),
            "70:5: " + getCheckMessage(MSG_KEY, className, "dontUse4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDesignForExtensionIgnoredAnnotationsThree.java"), expected);
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

    @Test
    public void testCompactSourceFileDefault() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/"
                        + "InputDesignForExtensionCompactSourceFileDefault.java"),
                expected);
    }

    @Test
    public void testCompactSourceFileProperties() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/"
                        + "InputDesignForExtensionCompactSourceFileProperties.java"),
                expected);
    }

}
