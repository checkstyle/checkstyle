///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck.MSG_KEY;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

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
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalClass.java"), expected);
    }

    @Test
    public void testFinalClass2() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_KEY, "someinnerClass"),
            "33:5: " + getCheckMessage(MSG_KEY, "SomeClass"),
            "39:5: " + getCheckMessage(MSG_KEY, "SomeClass"),
            "60:1: " + getCheckMessage(MSG_KEY, "TestNewKeyword"),
            "93:5: " + getCheckMessage(MSG_KEY, "NestedClass"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalClass2.java"), expected);
    }

    @Test
    public void testClassWithPrivateCtorAndNestedExtendingSubclass() throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_KEY, "ExtendA"),
            "18:9: " + getCheckMessage(MSG_KEY, "ExtendB"),
            "22:5: " + getCheckMessage(MSG_KEY, "C"),
            "24:9: " + getCheckMessage(MSG_KEY, "ExtendC"),
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
            "11:9: " + getCheckMessage(MSG_KEY, "ExtendA"),
            "14:5: " + getCheckMessage(MSG_KEY, "C"),
            "16:9: " + getCheckMessage(MSG_KEY, "ExtendC"),
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
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(badAst.toString());
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
            "16:5: " + getCheckMessage(MSG_KEY, "SubClass"),
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
            "49:5: " + getCheckMessage(MSG_KEY, "Nothing"),
        };
        verifyWithInlineConfigParser(getNonCompilablePath("InputFinalClassNestedInRecord.java"),
                                     expected);
    }

    /**
     * We cannot reproduce situation when visitToken is called and leaveToken is not.
     * So, we have to use reflection to be sure that even in such situation
     * state of the field will be cleared.
     *
     * @throws Exception when code tested throws exception
     */
    @Test
    public void testClearState() throws Exception {
        final FinalClassCheck check = new FinalClassCheck();
        final DetailAST root = JavaParser.parseFile(new File(getPath("InputFinalClass.java")),
                JavaParser.Options.WITHOUT_COMMENTS);
        final Optional<DetailAST> packageDef = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.PACKAGE_DEF);

        assertWithMessage("Ast should contain PACKAGE_DEF")
                .that(packageDef.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        packageDef.orElseThrow(), "packageName",
                        packageName -> ((String) packageName).isEmpty()))
                .isTrue();
    }

    @Test
    public void testPrivateClassWithDefaultCtor() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY, "Some2"),
            "19:1: " + getCheckMessage(MSG_KEY, "Some"),
            "24:5: " + getCheckMessage(MSG_KEY, "Some3"),
            "26:5: " + getCheckMessage(MSG_KEY, "Some4"),
            "31:5: " + getCheckMessage(MSG_KEY, "PaperSetter"),
            "36:5: " + getCheckMessage(MSG_KEY, "Paper"),
            "44:5: " + getCheckMessage(MSG_KEY, "Node"),
            "51:5: " + getCheckMessage(MSG_KEY, "Some1"),
            "55:1: " + getCheckMessage(MSG_KEY, "Some2"),
            "105:5: " + getCheckMessage(MSG_KEY, "NewCheck"),
            "108:5: " + getCheckMessage(MSG_KEY, "NewCheck2"),
            "112:5: " + getCheckMessage(MSG_KEY, "OldCheck"),
        };
        verifyWithInlineConfigParser(getPath("InputFinalClassPrivateCtor.java"),
                                     expected);
    }

    @Test
    public void testPrivateClassWithDefaultCtor2() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_KEY, "PrivateClass"),
            "34:5: " + getCheckMessage(MSG_KEY, "Check"),
            "44:5: " + getCheckMessage(MSG_KEY, "K"),
            "54:5: " + getCheckMessage(MSG_KEY, "Modifiers"),
        };
        verifyWithInlineConfigParser(getPath("InputFinalClassPrivateCtor2.java"),
                                     expected);
    }

    @Test
    public void testPrivateClassWithDefaultCtor3() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_KEY, "MyClass"),
            "30:5: " + getCheckMessage(MSG_KEY, "Check2"),
            "31:9: " + getCheckMessage(MSG_KEY, "Check3"),
            "35:5: " + getCheckMessage(MSG_KEY, "Check4"),
            "40:5: " + getCheckMessage(MSG_KEY, "Check"),
        };
        verifyWithInlineConfigParser(getPath("InputFinalClassPrivateCtor3.java"),
                                     expected);
    }
}
