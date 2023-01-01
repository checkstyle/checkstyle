///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class FinalClassCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/finalclass";
    }

    @Test
    public void testGetRequiredTokens() {
        final FinalClassCheck checkObj = new FinalClassCheck();
        final int[] expected = {
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.LITERAL_NEW,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testFinalClass() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_KEY, "InputFinalClass"),
            "19:4: " + getCheckMessage(MSG_KEY, "test4"),
            "117:5: " + getCheckMessage(MSG_KEY, "someinnerClass"),
            "124:5: " + getCheckMessage(MSG_KEY, "SomeClass"),
            "130:5: " + getCheckMessage(MSG_KEY, "SomeClass"),
            "151:1: " + getCheckMessage(MSG_KEY, "TestNewKeyword"),
            "184:5: " + getCheckMessage(MSG_KEY, "NestedClass"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalClass.java"), expected);
    }

    @Test
    public void testClassWithPrivateCtorAndNestedExtendingSubclass() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_KEY, "C"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputFinalClassClassWithPrivateCtorWithNestedExtendingClass.java"),
                expected);
    }

    @Test
    public void testClassWithPrivateCtorAndNestedExtendingSubclassWithoutPackage()
            throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY, "C"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                "InputFinalClassClassWithPrivateCtorWithNestedExtendingClassWithoutPackage.java"),
                expected);
    }

    @Test
    public void testFinalClassConstructorInRecord() throws Exception {

        final String[] expected = {
            "27:9: " + getCheckMessage(MSG_KEY, "F"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFinalClassConstructorInRecord.java"),
            expected);
    }

    @Test
    public void testImproperToken() {
        final FinalClassCheck finalClassCheck = new FinalClassCheck();
        final DetailAstImpl badAst = new DetailAstImpl();
        final int unsupportedTokenByCheck = TokenTypes.COMPILATION_UNIT;
        badAst.setType(unsupportedTokenByCheck);
        try {
            finalClassCheck.visitToken(badAst);
            assertWithMessage("IllegalStateException is expected").fail();
        }
        catch (IllegalStateException ex) {
            // it is OK
        }
    }

    @Test
    public void testGetAcceptableTokens() {
        final FinalClassCheck obj = new FinalClassCheck();
        final int[] expected = {
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.LITERAL_NEW,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(obj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testFinalClassInnerAndNestedClasses() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY, "SameName"),
            "45:9: " + getCheckMessage(MSG_KEY, "SameName"),
            "69:13: " + getCheckMessage(MSG_KEY, "B"),
            "84:9: " + getCheckMessage(MSG_KEY, "c"),
        };
        verifyWithInlineConfigParser(getPath("InputFinalClassInnerAndNestedClass.java"), expected);
    }

    @Test
    public void testFinalClassStaticNestedClasses() throws Exception {

        final String[] expected = {
            "14:17: " + getCheckMessage(MSG_KEY, "C"),
            "32:9: " + getCheckMessage(MSG_KEY, "B"),
            "43:9: " + getCheckMessage(MSG_KEY, "C"),
            "60:13: " + getCheckMessage(MSG_KEY, "Q"),
            "76:9: " + getCheckMessage(MSG_KEY, "F"),
            "83:9: " + getCheckMessage(MSG_KEY, "c"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFinalClassNestedStaticClassInsideInnerClass.java"),
                expected);
    }

    @Test
    public void testFinalClassEnum() throws Exception {
        final String[] expected = {
            "35:5: " + getCheckMessage(MSG_KEY, "DerivedClass"),
        };
        verifyWithInlineConfigParser(getPath("InputFinalClassEnum.java"), expected);
    }

    @Test
    public void testFinalClassAnnotation() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_KEY, "DerivedClass"),
        };
        verifyWithInlineConfigParser(getPath("InputFinalClassAnnotation.java"), expected);
    }

    @Test
    public void testFinalClassInterface() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_KEY, "DerivedClass"),
        };
        verifyWithInlineConfigParser(getPath("InputFinalClassInterface.java"), expected);
    }

    @Test
    public void testFinalClassAnonymousInnerClass() throws Exception {
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_KEY, "b"),
            "27:9: " + getCheckMessage(MSG_KEY, "m"),
            "40:9: " + getCheckMessage(MSG_KEY, "q"),
            "52:13: " + getCheckMessage(MSG_KEY, "b"),
            "67:9: " + getCheckMessage(MSG_KEY, "g"),
            "71:9: " + getCheckMessage(MSG_KEY, "y"),
            "84:9: " + getCheckMessage(MSG_KEY, "n"),
            "91:9: " + getCheckMessage(MSG_KEY, "n"),
        };
        verifyWithInlineConfigParser(getPath("InputFinalClassAnonymousInnerClass.java"), expected);
    }

    @Test
    public void testFinalClassNestedInInterface() throws Exception {
        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_KEY, "b"),
            "28:13: " + getCheckMessage(MSG_KEY, "m"),
            "50:5: " + getCheckMessage(MSG_KEY, "c"),
        };
        verifyWithInlineConfigParser(
            getPath("InputFinalClassNestedInInterfaceWithAnonInnerClass.java"), expected);
    }

    @Test
    public void testFinalClassNestedInEnum() throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_KEY, "j"),
            "27:9: " + getCheckMessage(MSG_KEY, "n"),
        };
        verifyWithInlineConfigParser(getPath("InputFinalClassNestedInEnumWithAnonInnerClass.java"),
                                     expected);
    }

    @Test
    public void testFinalClassNestedInRecord() throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_KEY, "c"),
            "31:13: " + getCheckMessage(MSG_KEY, "j"),
        };
        verifyWithInlineConfigParser(getNonCompilablePath("InputFinalClassNestedInRecord.java"),
                                     expected);
    }

}
