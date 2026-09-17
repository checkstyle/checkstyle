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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck.MSG_JAVADOC_MISSING;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingJavadocTypeCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadoctype";
    }

    @Test
    public void testGetRequiredTokens() {
        final MissingJavadocTypeCheck missingJavadocTypeCheck = new MissingJavadocTypeCheck();
        missingJavadocTypeCheck.visitJavadocToken(null);
        assertWithMessage(
                "MissingJavadocTypeCheck#getRequiredTokens should return empty array by default")
                        .that(missingJavadocTypeCheck.getRequiredTokens())
                        .isEmpty();
    }

    @Test
    public void testGetAcceptableTokens() {
        final MissingJavadocTypeCheck missingJavadocTypeCheck = new MissingJavadocTypeCheck();

        final int[] actual = missingJavadocTypeCheck.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testTagsOne() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocTypeTags1One"),
            "47:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputJavadocTypeTagsEnum"),
            "73:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputJavadocTypeTagsAnnotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeTagsOne.java"), expected);
    }

    @Test
    public void testTagsTwo() throws Exception {
        final String[] expected = {
            "22:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocTypeTags1Two"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeTagsTwo.java"), expected);
    }

    @Test
    public void testTagsThree() throws Exception {
        final String[] expected = {
            "22:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocTypeTags1Three"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeTagsThree.java"), expected);
    }

    @Test
    public void testTagsFour() throws Exception {
        final String[] expected = {
            "22:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocTypeTags1Four"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeTagsFour.java"), expected);
    }

    @Test
    public void testInner() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerInner2"),
            "29:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerInterface2"),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerInterfaceInnerClass"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeInner.java"), expected);
    }

    @Test
    public void testMissingJavadocTypePublicOnly1One() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocTypePublicOnly1One"),
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerInterface"),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerInnerClass"),
            "45:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerClass"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypePublicOnly1One.java"), expected);
    }

    @Test
    public void testMissingJavadocTypePublicOnly1Two() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocTypePublicOnly1Two"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypePublicOnly1Two.java"), expected);
    }

    @Test
    public void testMissingJavadocTypePublicOnly2One() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocTypePublicOnly2One"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypePublicOnly2One.java"), expected);
    }

    @Test
    public void testMissingJavadocTypePublicOnly2Two() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocTypePublicOnly2Two"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypePublicOnly2Two.java"), expected);
    }

    @Test
    public void testPublic() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocTypeScopeInnerInterfaces1"),
            "47:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "PublicInnerInterface"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeScopeInnerInterfaces1.java"),
               expected);
    }

    @Test
    public void testProtest() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocTypeScopeInnerInterfaces2"),
            "38:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "ProtectedInnerInterface"),
            "48:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "PublicInnerInterface"),
            "76:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "MyEnum"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeScopeInnerInterfaces2.java"),
               expected);
    }

    @Test
    public void testPkg() throws Exception {
        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerPublic"),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerProtected"),
            "30:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerPackage"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeScopeInnerClasses1.java"), expected);
    }

    @Test
    public void testEclipse() throws Exception {
        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerPublic"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeScopeInnerClasses2.java"), expected);
    }

    @Test
    public void testScopesOne() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocTypeNoJavadoc1One"),
            "28:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "ProtectedInner"),
            "41:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "PackageInner"),
            "54:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "PrivateInner"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadoc1One.java"),
               expected);
    }

    @Test
    public void testScopesTwo() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocTypeNoJavadoc1Two"),
            "18:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "PackageClass1"),
            "30:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "PublicInner"),
            "43:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "ProtectedInner"),
            "56:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "PackageInner"),
            "69:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "PrivateInner"),
            "82:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "IgnoredName"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadoc1Two.java"),
               expected);
    }

    @Test
    public void testLimitViolationsBySpecifyingTokens() throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "NoJavadoc"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadocOnInterface.java"),
               expected);
    }

    @Test
    public void testMissingJavadocTypeNoJavadoc2One() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocTypeNoJavadoc2One"),
            "28:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "ProtectedInner"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadoc2One.java"),
               expected);
    }

    @Test
    public void testMissingJavadocTypeNoJavadoc2Two() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocTypeNoJavadoc2Two"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadoc2Two.java"),
               expected);
    }

    @Test
    public void testMissingJavadocTypeNoJavadoc3One() throws Exception {
        final String[] expected = {
            "39:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "PackageInner"),
            "52:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "PrivateInner"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadoc3One.java"),
               expected);
    }

    @Test
    public void testMissingJavadocTypeNoJavadoc3Two() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "PackageClass3"),
            "29:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "PublicInner"),
            "42:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "ProtectedInner"),
            "55:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "PackageInner"),
            "68:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "PrivateInner"),
            "81:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "IgnoredName"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadoc3Two.java"),
               expected);
    }

    @Test
    public void testSkipAnnotationsDefault() throws Exception {

        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocTypeSkipAnnotations1"),
            "20:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputJavadocTypeSkipAnnotationsFullyQualifiedName1"),
            "25:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputJavadocTypeAllowedAnnotationByDefault1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeSkipAnnotations1.java"),
            expected);
    }

    @Test
    public void testSkipAnnotationsWithFullyQualifiedName() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocTypeSkipAnnotations2"),
            "20:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputJavadocTypeSkipAnnotationsFullyQualifiedName2"),
            "25:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputJavadocTypeAllowedAnnotationByDefault2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeSkipAnnotations2.java"),
                expected);
    }

    @Test
    public void testSkipAnnotationsAllowed() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeSkipAnnotations3.java"),
            expected);
    }

    @Test
    public void testSkipAnnotationsNotAllowed() throws Exception {

        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocTypeSkipAnnotations4"),
            "20:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputJavadocTypeSkipAnnotationsFullyQualifiedName4"),
            "25:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputJavadocTypeAllowedAnnotationByDefault4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeSkipAnnotations4.java"),
            expected);
    }

    @Test
    public void testMissingJavadocTypeCheckRecords() throws Exception {

        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocTypeRecords"),
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "TestClass"),
            "23:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "MyRecord1"),
            "28:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "MyRecord2"),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "MyRecord4"),
            "39:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "MyRecord5"),
            "49:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "NonNull1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeRecords.java"),
            expected);
    }

    @Test
    public void testInterfaceMemberScopeIsPublic() throws Exception {

        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocTypeInterfaceMemberScopeIsPublic"),
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "Enum"),
            "23:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "Class"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeInterfaceMemberScopeIsPublic.java"),
            expected);
    }

    @Test
    public void testQualifiedAnnotation1() throws Exception {
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "A"),
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "B"),
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "C"),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeQualifiedAnnotation1.java"), expected);
    }

    @Test
    public void testQualifiedAnnotation2() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "B"),
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "C"),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeQualifiedAnnotation2.java"), expected);
    }

    @Test
    public void testQualifiedAnnotation3() throws Exception {
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "A"),
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "C"),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeQualifiedAnnotation3.java"), expected);
    }

    @Test
    public void testQualifiedAnnotation4() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "A"),
            "23:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "B"),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeQualifiedAnnotation4.java"), expected);
    }

    @Test
    public void testQualifiedAnnotation5() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeQualifiedAnnotation5.java"), expected);
    }

    @Test
    public void testMultipleQualifiedAnnotation() throws Exception {
        final String[] expected = {
            "31:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "B"),
            "41:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "D"),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeMultipleQualifiedAnnotation.java"), expected);
    }

    @Test
    public void testQualifiedAnnotationWithParameters() throws Exception {
        final String[] expected = {
            "34:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "D"),
            "38:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "E"),
            "43:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "F"),
            "51:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "G"),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeQualifiedAnnotationWithParameters.java"),
            expected);
    }

    @Test
    public void testMissingJavadocTypeAboveComments() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocTypeAboveComments"),
            "30:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "MyClass2"),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeAboveComments.java"),
            expected);
    }

    @Test
    public void testNonJavadocBlockCommentDoesNotSatisfyJavadoc() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocTypeNonJavadocOnly"),
            "17:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerClass"),
            "24:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerClass2"),
            "29:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerClass3"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNonJavadocOnly.java"),
                expected);
    }

    @Test
    public void testMissingJavadocTypeEnum() throws Exception {
        final String[] expected = {
            "37:6: " + getCheckMessage(MSG_JAVADOC_MISSING, "Test2"),
            "51:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "A"),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeEnum.java"),
            expected);
    }

    @Test
    public void testClearJavadocComments() {
        final MissingJavadocTypeCheck check = new MissingJavadocTypeCheck();
        final List<Object> mockList = new ArrayList<>();
        mockList.add(new Object());
        TestUtil.setInternalState(check, "javadocComments", mockList);
        check.beginTree(null);
        final List<?> javadocComments =
                (List<?>) TestUtil.getInternalState(check, "javadocComments", List.class);
        assertWithMessage("javadocComments state is not cleared on beginTree")
                .that(javadocComments)
                .isEmpty();
    }

}
