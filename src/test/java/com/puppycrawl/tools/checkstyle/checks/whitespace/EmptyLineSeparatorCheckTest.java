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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_MULTIPLE_LINES;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_MULTIPLE_LINES_AFTER;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_MULTIPLE_LINES_INSIDE;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

class EmptyLineSeparatorCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/emptylineseparator";
    }

    @Test
    void getRequiredTokens() {
        final EmptyLineSeparatorCheck checkObj = new EmptyLineSeparatorCheck();
        assertWithMessage("EmptyLineSeparatorCheck#getRequiredTokens should return empty array "
                + "by default")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    void testDefault() throws Exception {

        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "31:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
            "34:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "35:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "STATIC_INIT"),
            "39:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INSTANCE_INIT"),
            "53:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CTOR_DEF"),
            "58:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "75:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "106:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INTERFACE_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparator.java"), expected);
    }

    @Test
    void allowNoEmptyLineBetweenFields() throws Exception {

        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "31:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
            "35:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "STATIC_INIT"),
            "39:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INSTANCE_INIT"),
            "53:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CTOR_DEF"),
            "58:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "75:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "106:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INTERFACE_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparator2.java"), expected);
    }

    @Test
    void header() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorHeader.java"), expected);
    }

    @Test
    void multipleEmptyLinesBetweenClassMembers() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "package"),
            "17:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "import"),
            "22:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "CLASS_DEF"),
            "26:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "31:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "36:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "METHOD_DEF"),
            "38:33: " + getCheckMessage(MSG_MULTIPLE_LINES_AFTER, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorMultipleEmptyLines.java"), expected);
    }

    @Test
    void formerArrayIndexOutOfBounds() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorFormerException.java"), expected);
    }

    @Test
    void allowMultipleFieldInClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorMultipleFieldsInClass.java"), expected);
    }

    @Test
    void allowMultipleImportSeparatedFromPackage() throws Exception {
        final String[] expected = {
            "13:78: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorMultipleImportEmptyClass.java"),
            expected);
    }

    @Test
    void importSeparatedFromPackage() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorImportSeparatedFromPackage.java"),
            expected);
    }

    @Test
    void staticImport() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorStaticImport.java"),
            expected);
    }

    @Test
    void blockCommentNotSeparatedFromPackage() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "/*"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorBlockCommentUnderPackage.java"),
            expected);
    }

    @Test
    void singleCommentNotSeparatedFromPackage() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "//"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorSingleCommentUnderPackage.java"),
            expected);
    }

    @Test
    void classDefinitionNotSeparatedFromPackage() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorModifierUnderPackage.java"),
            expected);
    }

    @Test
    void commentAfterPackageWithImports() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "//"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorSingleLineCommentAfterPackage.java"),
                expected);
    }

    @Test
    void javadocCommentAfterPackageWithImports() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        final String[] expected = {
            "2:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "/*"),
        };
        verify(checkConfig,
                getPath("InputEmptyLineSeparatorJavadocCommentAfterPackage.java"),
                expected);
    }

    @Test
    void packageImportsClassInSingleLine() throws Exception {
        final String[] expected = {
            "13:79: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "13:101: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorPackageImportClassInOneLine.java"),
                expected);
    }

    @Test
    void emptyLineAfterPackageForPackageAst() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "/*"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorEmptyLineAfterPackageForPackageAst.java"),
                expected);
    }

    @Test
    void emptyLineAfterPackageForImportAst() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorEmptyLineAfterPackageForImportAst.java"),
                expected);
    }

    @Test
    void classDefinitionAndCommentNotSeparatedFromPackage() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "//"),
            "15:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
        };
        final String testFile =
            "InputEmptyLineSeparatorClassDefinitionAndCommentNotSeparatedFromPackage.java";
        verifyWithInlineConfigParser(
                getPath(testFile), expected);
    }

    @Test
    void blockCommentSeparatedFromPackage() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorBlockCommentSeparatedFromPackage.java"),
            expected);
    }

    @Test
    void singleCommentSeparatedFromPackage() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorSingleCommentSeparatedFromPackage.java"),
            expected);
    }

    @Test
    void enumMembers() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "27:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "28:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "31:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "CTOR_DEF"),
            "36:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "STATIC_INIT"),
            "40:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "STATIC_INIT"),
        };
        verifyWithInlineConfigParser(
            getPath("InputEmptyLineSeparatorEnumMembers.java"), expected
        );
    }

    @Test
    void interfaceFields() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "25:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "34:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "38:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "45:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
        };
        verifyWithInlineConfigParser(
            getPath("InputEmptyLineSeparatorInterfaceFields.java"), expected
        );
    }

    @Test
    void getAcceptableTokens() {
        final EmptyLineSeparatorCheck emptyLineSeparatorCheckObj = new EmptyLineSeparatorCheck();
        final int[] actual = emptyLineSeparatorCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    void prePreviousLineEmptiness() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addProperty("allowMultipleEmptyLines", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getPath("InputEmptyLineSeparatorPrePreviousLineEmptiness.java"), expected);
    }

    @Test
    void prePreviousLineIsEmpty() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addProperty("allowMultipleEmptyLines", "false");
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "package"),
        };
        verify(checkConfig,
                getPath("InputEmptyLineSeparatorPrePreviousLineIsEmpty.java"), expected);
    }

    @Test
    void previousLineEmptiness() throws Exception {
        final String[] expected = {
            "21:30: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "26:5: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "32:67: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "41:48: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "51:21: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorPreviousLineEmptiness.java"), expected);
    }

    @Test
    void disAllowMultipleEmptyLinesInsideClassMembers() throws Exception {
        final String[] expected = {
            "18:11: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "30:11: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "36:11: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "41:35: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "46:11: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "53:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorMultipleEmptyLinesInside.java"),
                expected);
    }

    @Test
    void allowMultipleEmptyLinesInsideClassMembers() throws Exception {
        final String[] expected = {
            "53:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorMultipleEmptyLinesInside2.java"),
                expected);
    }

    @Test
    void importsAndStaticImports() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorImports.java"), expected);
    }

    @Test
    void allowPackageAnnotation() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("packageinfo/test1/package-info.java"),
                expected);
    }

    @Test
    void allowJavadocBeforePackage() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("packageinfo/test2/package-info.java"),
                expected);
    }

    @Test
    void disAllowBlockCommentBeforePackage() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
        };
        verifyWithInlineConfigParser(
                getPath("packageinfo/test3/package-info.java"),
                expected);
    }

    @Test
    void allowSingleLineCommentPackage() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
        };
        verifyWithInlineConfigParser(
                getPath("packageinfo/test4/package-info.java"),
                expected);
    }

    @Test
    void nonPackageInfoWithJavadocBeforePackage() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorNonPackageInfoWithJavadocBeforePackage.java"),
                expected);
    }

    @Test
    void classOnly() throws Exception {
        final String[] expected = {
            "51:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorMultipleEmptyLinesInside3.java"),
                expected);
    }

    @Test
    void lineSeparationBeforeComments() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
            "16:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "20:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "import"),
            "25:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "32:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "/*"),
            "43:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "/*"),
            "60:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "71:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "/*"),
            "76:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "import"),
            "82:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "86:1: " + getCheckMessage(MSG_MULTIPLE_LINES, "/*"),
            "94:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "99:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "106:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "/*"),
            "119:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "/*"),
            "132:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "METHOD_DEF"),
            "139:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "149:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "/*"),
            "164:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "/*"),
            "181:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "CLASS_DEF"),
            "187:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "191:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "197:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "209:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "/*"),
            "222:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "/*"),
            "236:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "239:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INTERFACE_DEF"),
            "244:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "/*"),
            "260:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "269:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "282:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "287:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "293:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "301:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "310:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "316:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "336:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
            "344:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "//"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorWithComments.java"), expected);
    }

    @Test
    void ignoreEmptyLinesBeforeCommentsWhenItIsAllowed() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
            "239:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INTERFACE_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorWithComments2.java"), expected);
    }

    @Test
    void noViolationsOnEmptyLinesBeforeComments() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments.java"),
                expected);
    }

    @Test
    void emptyLineSeparatorRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
            "18:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "RECORD_DEF"),
            "20:9: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "21:9: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "COMPACT_CTOR_DEF"),
            "22:9: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CTOR_DEF"),
            "23:9: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "STATIC_INIT"),
            "25:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "RECORD_DEF"),
            "26:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
            "28:9: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "29:9: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CTOR_DEF"),
            "30:9: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CTOR_DEF"),
            "35:9: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "COMPACT_CTOR_DEF"),
            "36:9: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "41:9: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "COMPACT_CTOR_DEF"),
            "42:9: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputEmptyLineSeparatorRecordsAndCompactCtors.java"),
                expected);
    }

    @Test
    void emptyLineSeparatorRecordsAndCompactCtorsNoEmptyLines() throws Exception {

        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
            "17:27: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "23:29: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputEmptyLineSeparatorRecordsAndCompactCtorsNoEmptyLines.java"),
                expected);
    }

    @Test
    void emptyLineSeparatorMultipleSingleTypeVariables() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorSingleTypeVariables.java"),
                expected);
    }

    @Test
    void emptyLineSeparatorEmptyLinesInsideClassMembersRecursive() throws Exception {
        final String[] expected = {
            "27:15: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorRecursive.java"),
                expected);
    }

    @Test
    void emptyLineSeparatorNewMethodDef() throws Exception {
        final String[] expected = {
            "29:34: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "38:26: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorNewMethodDef.java"),
                expected);
    }

    @Test
    void emptyLineSeparatorPostFixCornerCases() throws Exception {
        final String[] expected = {
            "18:19: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "32:29: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "43:29: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorPostFixCornerCases.java"),
                expected);
    }

    @Test
    void emptyLineSeparatorAnnotation() throws Exception {
        final String[] expected = {
            "18:22: " + getCheckMessage(MSG_MULTIPLE_LINES_AFTER, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorAnnotations.java"),
                expected);
    }

    @Test
    void emptyLineSeparatorWithEmoji() throws Exception {

        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "27:5: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "33:15: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "41:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorWithEmoji.java"),
                expected);
    }

    @Test
    void multipleLines() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorMultipleLines.java"),
                expected);
    }

    @Test
    void multipleLines2() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineSeparatorMultipleLines2.java"),
                expected);
    }

    @Test
    void multipleLines3() throws Exception {
        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputEmptyLineSeparatorMultipleLines3.java"),
                expected);
    }

}
