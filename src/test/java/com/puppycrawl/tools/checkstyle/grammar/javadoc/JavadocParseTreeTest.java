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

package com.puppycrawl.tools.checkstyle.grammar.javadoc;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;

/**
 * JavadocParseTreeTest.
 *
 * @noinspection ClassOnlyUsedInOnePackage
 * @noinspectionreason ClassOnlyUsedInOnePackage - class is internal tool, and only used in testing
 */
class JavadocParseTreeTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/javadoc/";
    }

    private String getHtmlPath(String filename) throws IOException {
        return getPath("htmlTags" + File.separator + filename);
    }

    private String getDocPath(String filename) throws IOException {
        return getPath("javadocTags" + File.separator + filename);
    }

    @Test
    void oneSimpleHtmlTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedOneSimpleHtmlTagAst.txt"),
                getHtmlPath("InputOneSimpleHtmlTag.javadoc"));
    }

    @Test
    void textBeforeJavadocTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedTextBeforeJavadocTagsAst.txt"),
                getDocPath("InputTextBeforeJavadocTags.javadoc"));
    }

    @Test
    void customJavadocTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedCustomJavadocTagsAst.txt"),
                getDocPath("InputCustomJavadocTags.javadoc"));
    }

    @Test
    void javadocTagDescriptionWithInlineTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedJavadocTagDescriptionWithInlineTagsAst.txt"),
                getDocPath("InputJavadocTagDescriptionWithInlineTags.javadoc"));
    }

    @Test
    void leadingAsterisks() throws Exception {
        verifyJavadocTree(getPath("expectedLeadingAsterisksAst.txt"),
                getPath("InputLeadingAsterisks.javadoc"));
    }

    @Test
    void authorWithMailto() throws Exception {
        verifyJavadocTree(getDocPath("expectedAuthorWithMailtoAst.txt"),
                getDocPath("InputAuthorWithMailto.javadoc"));
    }

    @Test
    void htmlTagsInParagraph() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedHtmlTagsInParagraphAst.txt"),
                getHtmlPath("InputHtmlTagsInParagraph.javadoc"));
    }

    @Test
    void linkInlineTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedLinkInlineTagsAst.txt"),
                getDocPath("InputLinkInlineTags.javadoc"));
    }

    @Test
    void seeReferenceWithFewNestedClasses() throws Exception {
        verifyJavadocTree(getDocPath("expectedSeeReferenceWithFewNestedClassesAst.txt"),
                getDocPath("InputSeeReferenceWithFewNestedClasses.javadoc"));
    }

    @Test
    void paramWithGeneric() throws Exception {
        verifyJavadocTree(getDocPath("expectedParamWithGenericAst.txt"),
                getDocPath("InputParamWithGeneric.javadoc"));
    }

    @Test
    void serial() throws Exception {
        verifyJavadocTree(getDocPath("expectedSerialAst.txt"),
                getDocPath("InputSerial.javadoc"));
    }

    @Test
    void since() throws Exception {
        verifyJavadocTree(getDocPath("expectedSinceAst.txt"),
                getDocPath("InputSince.javadoc"));
    }

    @Test
    void unclosedAndClosedParagraphs() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedUnclosedAndClosedParagraphsAst.txt"),
                getHtmlPath("InputUnclosedAndClosedParagraphs.javadoc"));
    }

    @Test
    void listWithUnclosedItemInUnclosedParagraph() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedListWithUnclosedItemInUnclosedParagraphAst.txt"),
                getHtmlPath("InputListWithUnclosedItemInUnclosedParagraph.javadoc"));
    }

    @Test
    void unclosedParagraphFollowedByJavadocTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedUnclosedParagraphFollowedByJavadocTagAst.txt"),
                getHtmlPath("InputUnclosedParagraphFollowedByJavadocTag.javadoc"));
    }

    @Test
    void allJavadocInlineTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedAllJavadocInlineTagsAst.txt"),
                getDocPath("InputAllJavadocInlineTags.javadoc"));
    }

    @Test
    void docRootInheritDoc() throws Exception {
        verifyJavadocTree(getDocPath("expectedDocRootInheritDocAst.txt"),
                getDocPath("InputDocRootInheritDoc.javadoc"));
    }

    @Test
    void fewWhiteSpacesAsSeparator() throws Exception {
        verifyJavadocTree(getDocPath("expectedFewWhiteSpacesAsSeparatorAst.txt"),
                getDocPath("InputFewWhiteSpacesAsSeparator.javadoc"));
    }

    @Test
    void mixedCaseOfHtmlTags() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedMixedCaseOfHtmlTagsAst.txt"),
                getHtmlPath("InputMixedCaseOfHtmlTags.javadoc"));
    }

    @Test
    void htmlComments() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedCommentsAst.txt"),
                getHtmlPath("InputComments.javadoc"));
    }

    @Test
    void negativeNumberInAttribute() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedNegativeNumberInAttributeAst.txt"),
                getHtmlPath("InputNegativeNumberInAttribute.javadoc"));
    }

    @Test
    void dollarInLink() throws Exception {
        verifyJavadocTree(getDocPath("expectedDollarInLinkAst.txt"),
                getDocPath("InputDollarInLink.javadoc"));
    }

    @Test
    void dotCharacterInCustomTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedCustomTagWithDotAst.txt"),
                getDocPath("InputCustomTagWithDot.javadoc"));
    }

    @Test
    void linkToPackage() throws Exception {
        verifyJavadocTree(getDocPath("expectedLinkToPackageAst.txt"),
                getDocPath("InputLinkToPackage.javadoc"));
    }

    @Test
    void leadingAsterisksExtended() throws Exception {
        verifyJavadocTree(getPath("expectedLeadingAsterisksExtendedAst.txt"),
                getPath("InputLeadingAsterisksExtended.javadoc"));
    }

    @Test
    void inlineCustomJavadocTag() throws Exception {
        verifyJavadocTree(getDocPath("expectedInlineCustomJavadocTagAst.txt"),
                getDocPath("InputInlineCustomJavadocTag.javadoc"));
    }

    @Test
    void attributeValueWithoutQuotes() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedAttributeValueWithoutQuotesAst.txt"),
                getHtmlPath("InputAttributeValueWithoutQuotes.javadoc"));
    }

    @Test
    void closedOtherTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedClosedOtherTagAst.txt"),
                getHtmlPath("InputClosedOtherTag.javadoc"));
    }

    @Test
    void allStandardJavadocTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedAllStandardJavadocTagsAst.txt"),
                getDocPath("InputAllStandardJavadocTags.javadoc"));
    }

    @Test
    void asteriskInJavadocInlineTag() throws Exception {
        verifyJavadocTree(getDocPath("expectedAsteriskInJavadocInlineTagAst.txt"),
                getDocPath("InputAsteriskInJavadocInlineTag.javadoc"));
    }

    @Test
    void asteriskInLiteral() throws Exception {
        verifyJavadocTree(getDocPath("expectedAsteriskInLiteralAst.txt"),
                getDocPath("InputAsteriskInLiteral.javadoc"));
    }

    @Test
    void innerBracesInCodeTag() throws Exception {
        verifyJavadocTree(getDocPath("expectedInnerBracesInCodeTagAst.txt"),
                getDocPath("InputInnerBracesInCodeTag.javadoc"));
    }

    @Test
    void newlineAndAsteriskInParameters() throws Exception {
        verifyJavadocTree(getDocPath("expectedNewlineAndAsteriskInParametersAst.txt"),
                getDocPath("InputNewlineAndAsteriskInParameters.javadoc"));
    }

    @Test
    void twoLinkTagsInRow() throws Exception {
        verifyJavadocTree(getDocPath("expectedTwoLinkTagsInRowAst.txt"),
                getDocPath("InputTwoLinkTagsInRow.javadoc"));
    }

    @Test
    void javadocWithCrAsNewline() throws Exception {
        verifyJavadocTree(getPath("expectedJavadocWithCrAsNewlineAst.txt"),
                getPath("InputJavadocWithCrAsNewline.javadoc"));
    }

    @Test
    void nestingWithSingletonElement() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedNestingWithSingletonElementAst.txt"),
                getHtmlPath("InputNestingWithSingletonElement.javadoc"));
    }

    @Test
    void voidElements() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedVoidElementsAst.txt"),
                getHtmlPath("InputVoidElements.javadoc"));
    }

    @Test
    void htmlVoidElementEmbed() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedHtmlVoidElementEmbedAst.txt"),
                getHtmlPath("InputHtmlVoidElementEmbed.javadoc"));
    }

    @Test
    void spaceBeforeDescriptionInBlockJavadocTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedSpaceBeforeDescriptionInBlockJavadocTagsAst.txt"),
                getDocPath("InputSpaceBeforeDescriptionInBlockJavadocTags.javadoc"));
    }

    @Test
    void emptyDescriptionBeforeTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedEmptyDescriptionBeforeTags.txt"),
                getDocPath("InputEmptyDescriptionBeforeTags.javadoc"));
    }

    @Test
    void spaceBeforeDescriptionInInlineTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedSpaceBeforeArgsInInlineTagsAst.txt"),
                getDocPath("InputSpaceBeforeArgsInInlineTags.javadoc"));
    }

    @Test
    void htmlVoidElementKeygen() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedHtmlVoidElementKeygenAst.txt"),
                getHtmlPath("InputHtmlVoidElementKeygen.javadoc"));
    }

    @Test
    void htmlVoidElementSource() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedHtmlVoidElementSourceAst.txt"),
                getHtmlPath("InputHtmlVoidElementSource.javadoc"));
    }

    @Test
    void htmlVoidElementTrack() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedHtmlVoidElementTrackAst.txt"),
                getHtmlPath("InputHtmlVoidElementTrack.javadoc"));
    }

    @Test
    void htmlVoidElementWbr() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedHtmlVoidElementWbrAst.txt"),
                getHtmlPath("InputHtmlVoidElementWbr.javadoc"));
    }

    @Test
    void optgroupHtmlTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedOptgroupHtmlTagAst.txt"),
                getHtmlPath("InputOptgroupHtmlTag.javadoc"));
    }

    @Test
    void nonTightOptgroupHtmlTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedNonTightOptgroupHtmlTagAst.txt"),
                getHtmlPath("InputNonTightOptgroupHtmlTag.javadoc"));
    }

    @Test
    void rbHtmlTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedRbHtmlTagAst.txt"),
                getHtmlPath("InputRbHtmlTag.javadoc"));
    }

    @Test
    void nonTightRbHtmlTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedNonTightRbHtmlTagAst.txt"),
                getHtmlPath("InputNonTightRbHtmlTag.javadoc"));
    }

    @Test
    void rtHtmlTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedRtHtmlTagAst.txt"),
                getHtmlPath("InputRtHtmlTag.javadoc"));
    }

    @Test
    void nonTightRtHtmlTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedNonTightRtHtmlTagAst.txt"),
                getHtmlPath("InputNonTightRtHtmlTag.javadoc"));
    }

    @Test
    void rtcHtmlTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedRtcHtmlTagAst.txt"),
                getHtmlPath("InputRtcHtmlTag.javadoc"));
    }

    @Test
    void nonTightRtcHtmlTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedNonTightRtcHtmlTagAst.txt"),
                getHtmlPath("InputNonTightRtcHtmlTag.javadoc"));
    }

    @Test
    void rpHtmlTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedRpHtmlTagAst.txt"),
                getHtmlPath("InputRpHtmlTag.javadoc"));
    }

    @Test
    void nonTightRpHtmlTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedNonTightRpHtmlTagAst.txt"),
                getHtmlPath("InputNonTightRpHtmlTag.javadoc"));
    }

    @Test
    void leadingAsteriskAfterSeeTag() throws Exception {
        verifyJavadocTree(getDocPath("expectedLeadingAsteriskAfterSeeTagAst.txt"),
                getDocPath("InputLeadingAsteriskAfterSeeTag.javadoc"));
    }

    @Test
    void uppercaseInPackageName() throws Exception {
        verifyJavadocTree(getDocPath("expectedUppercaseInPackageNameAst.txt"),
                getDocPath("InputUppercaseInPackageName.javadoc"));
    }

    @Test
    void paragraph() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedParagraphAst.txt"),
                getHtmlPath("InputParagraph.javadoc"));
    }

    @Test
    void cdata() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedCdataAst.txt"),
                getHtmlPath("InputCdata.javadoc"));
    }

    @Test
    void crAsNewlineMultiline() throws Exception {
        verifyJavadocTree(getPath("expectedCrAsNewlineMultiline.txt"),
                getPath("InputCrAsNewlineMultiline.javadoc"));
    }

    @Test
    void dosLineEndingAsNewlineMultiline() throws Exception {
        verifyJavadocTree(getPath("expectedDosLineEndingAsNewlineMultiline.txt"),
                getPath("InputDosLineEndingAsNewlineMultiline.javadoc"));
    }

    @Test
    void annotationsInsideInlineTag() throws Exception {
        verifyJavadocTree(getDocPath("expectedAnnotationsInsideInlineTagAst.txt"),
                getDocPath("InputAnnotationsInsideInlineTag.javadoc"));
    }

    @Test
    void seeTagOptionalWhitespaceAfterHtmlTag() throws Exception {
        verifyJavadocTree(getDocPath("expectedSeeTagOptionalWhitespaceAfterHtmlTag.txt"),
                getDocPath("InputSeeTagOptionalWhitespaceAfterHtmlTag.javadoc"));
    }

    @Test
    void emptyReferenceInLink() throws Exception {
        verifyJavadocTree(getDocPath("expectedEmptyReferenceInLinkAst.txt"),
                getDocPath("InputEmptyReferenceInLink.javadoc"));
    }
}
