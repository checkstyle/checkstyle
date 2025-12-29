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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck.MSG_JAVADOC_MISSING;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingJavadocTypeCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadoctype";
    }

    @Test
    public void getRequiredTokens() {
        final MissingJavadocTypeCheck missingJavadocTypeCheck = new MissingJavadocTypeCheck();
        assertWithMessage(
                "MissingJavadocTypeCheck#getRequiredTokens should return empty array by default")
                        .that(missingJavadocTypeCheck.getRequiredTokens())
                        .isEmpty();
    }

    @Test
    public void getAcceptableTokens() {
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
    public void tagsOne() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeTagsOne.java"), expected);
    }

    @Test
    public void tagsTwo() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeTagsTwo.java"), expected);
    }

    @Test
    public void tagsThree() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeTagsThree.java"), expected);
    }

    @Test
    public void tagsFour() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeTagsFour.java"), expected);
    }

    @Test
    public void inner() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "32:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeInner.java"), expected);
    }

    @Test
    public void missingJavadocTypePublicOnly1One() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "20:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypePublicOnly1One.java"), expected);
    }

    @Test
    public void missingJavadocTypePublicOnly1Two() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypePublicOnly1Two.java"), expected);
    }

    @Test
    public void missingJavadocTypePublicOnly2One() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypePublicOnly2One.java"), expected);
    }

    @Test
    public void missingJavadocTypePublicOnly2Two() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypePublicOnly2Two.java"), expected);
    }

    @Test
    public void testPublic() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeScopeInnerInterfaces1.java"),
               expected);
    }

    @Test
    public void protest() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "71:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeScopeInnerInterfaces2.java"),
               expected);
    }

    @Test
    public void pkg() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeScopeInnerClasses1.java"), expected);
    }

    @Test
    public void eclipse() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeScopeInnerClasses2.java"), expected);
    }

    @Test
    public void scopesOne() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadoc1One.java"),
               expected);
    }

    @Test
    public void scopesTwo() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "62:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "74:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadoc1Two.java"),
               expected);
    }

    @Test
    public void limitViolationsBySpecifyingTokens() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadocOnInterface.java"),
               expected);
    }

    @Test
    public void missingJavadocTypeNoJavadoc2One() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadoc2One.java"),
               expected);
    }

    @Test
    public void missingJavadocTypeNoJavadoc2Two() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadoc2Two.java"),
               expected);
    }

    @Test
    public void missingJavadocTypeNoJavadoc3One() throws Exception {
        final String[] expected = {
            "37:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadoc3One.java"),
               expected);
    }

    @Test
    public void missingJavadocTypeNoJavadoc3Two() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "62:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "74:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeNoJavadoc3Two.java"),
               expected);
    }

    @Test
    public void dontAllowUnusedParameterTag() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeUnusedParamInJavadocForClass.java"),
                expected);
    }

    @Test
    public void skipAnnotationsDefault() throws Exception {

        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeSkipAnnotations1.java"),
            expected);
    }

    @Test
    public void skipAnnotationsWithFullyQualifiedName() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeSkipAnnotations2.java"),
                expected);
    }

    @Test
    public void skipAnnotationsAllowed() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeSkipAnnotations3.java"),
            expected);
    }

    @Test
    public void skipAnnotationsNotAllowed() throws Exception {

        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeSkipAnnotations4.java"),
            expected);
    }

    @Test
    public void missingJavadocTypeCheckRecords() throws Exception {

        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "19:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "23:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "31:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "32:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "41:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeRecords.java"),
            expected);
    }

    @Test
    public void interfaceMemberScopeIsPublic() throws Exception {

        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "19:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocTypeInterfaceMemberScopeIsPublic.java"),
            expected);
    }

    @Test
    public void qualifiedAnnotation1() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "20:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "23:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeQualifiedAnnotation1.java"), expected);
    }

    @Test
    public void qualifiedAnnotation2() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "23:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeQualifiedAnnotation2.java"), expected);
    }

    @Test
    public void qualifiedAnnotation3() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeQualifiedAnnotation3.java"), expected);
    }

    @Test
    public void qualifiedAnnotation4() throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeQualifiedAnnotation4.java"), expected);
    }

    @Test
    public void qualifiedAnnotation5() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeQualifiedAnnotation5.java"), expected);
    }

    @Test
    public void multipleQualifiedAnnotation() throws Exception {
        final String[] expected = {
            "29:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeMultipleQualifiedAnnotation.java"), expected);
    }

    @Test
    public void qualifiedAnnotationWithParameters() throws Exception {
        final String[] expected = {
            "33:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "42:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeQualifiedAnnotationWithParameters.java"),
            expected);
    }

    @Test
    public void missingJavadocTypeAboveComments() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
            getPath("InputMissingJavadocTypeAboveComments.java"),
            expected);
    }

}
