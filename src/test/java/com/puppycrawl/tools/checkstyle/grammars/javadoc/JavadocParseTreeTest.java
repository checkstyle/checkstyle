////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.grammars.javadoc;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;

/** @noinspection ClassOnlyUsedInOnePackage */
public class JavadocParseTreeTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammars/javadoc/";
    }

    private String getHtmlPath(String filename) throws IOException {
        return getPath("htmlTags" + File.separator + filename);
    }

    private String getDocPath(String filename) throws IOException {
        return getPath("javadocTags" + File.separator + filename);
    }

    @Test
    public void oneSimpleHtmlTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedOneSimpleHtmlTagAst.txt"),
                getHtmlPath("InputOneSimpleHtmlTag.javadoc"));
    }

    @Test
    public void textBeforeJavadocTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedTextBeforeJavadocTagsAst.txt"),
                getDocPath("InputTextBeforeJavadocTags.javadoc"));
    }

    @Test
    public void customJavadocTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedCustomJavadocTagsAst.txt"),
                getDocPath("InputCustomJavadocTags.javadoc"));
    }

    @Test
    public void javadocTagDescriptionWithInlineTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedJavadocTagDescriptionWithInlineTagsAst.txt"),
                getDocPath("InputJavadocTagDescriptionWithInlineTags.javadoc"));
    }

    @Test
    public void leadingAsterisks() throws Exception {
        verifyJavadocTree(getPath("expectedLeadingAsterisksAst.txt"),
                getPath("InputLeadingAsterisks.javadoc"));
    }

    @Test
    public void authorWithMailto() throws Exception {
        verifyJavadocTree(getDocPath("expectedAuthorWithMailtoAst.txt"),
                getDocPath("InputAuthorWithMailto.javadoc"));
    }

    @Test
    public void htmlTagsInParagraph() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedHtmlTagsInParagraphAst.txt"),
                getHtmlPath("InputHtmlTagsInParagraph.javadoc"));
    }

    @Test
    public void linkInlineTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedLinkInlineTagsAst.txt"),
                getDocPath("InputLinkInlineTags.javadoc"));
    }

    @Test
    public void seeReferenceWithFewNestedClasses() throws Exception {
        verifyJavadocTree(getDocPath("expectedSeeReferenceWithFewNestedClassesAst.txt"),
                getDocPath("InputSeeReferenceWithFewNestedClasses.javadoc"));
    }

    @Test
    public void paramWithGeneric() throws Exception {
        verifyJavadocTree(getDocPath("expectedParamWithGenericAst.txt"),
                getDocPath("InputParamWithGeneric.javadoc"));
    }

    @Test
    public void serial() throws Exception {
        verifyJavadocTree(getDocPath("expectedSerialAst.txt"),
                getDocPath("InputSerial.javadoc"));
    }

    @Test
    public void since() throws Exception {
        verifyJavadocTree(getDocPath("expectedSinceAst.txt"),
                getDocPath("InputSince.javadoc"));
    }

    @Test
    public void unclosedAndClosedParagraphs() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedUnclosedAndClosedParagraphsAst.txt"),
                getHtmlPath("InputUnclosedAndClosedParagraphs.javadoc"));
    }

    @Test
    public void listWithUnclosedItemInUnclosedParagraph() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedListWithUnclosedItemInUnclosedParagraphAst.txt"),
                getHtmlPath("InputListWithUnclosedItemInUnclosedParagraph.javadoc"));
    }

    @Test
    public void unclosedParagraphFollowedByJavadocTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedUnclosedParagraphFollowedByJavadocTagAst.txt"),
                getHtmlPath("InputUnclosedParagraphFollowedByJavadocTag.javadoc"));
    }

    @Test
    public void allJavadocInlineTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedAllJavadocInlineTagsAst.txt"),
                getDocPath("InputAllJavadocInlineTags.javadoc"));
    }

    @Test
    public void docRootInheritDoc() throws Exception {
        verifyJavadocTree(getDocPath("expectedDocRootInheritDocAst.txt"),
                getDocPath("InputDocRootInheritDoc.javadoc"));
    }

    @Test
    public void fewWhiteSpacesAsSeparator() throws Exception {
        verifyJavadocTree(getDocPath("expectedFewWhiteSpacesAsSeparatorAst.txt"),
                getDocPath("InputFewWhiteSpacesAsSeparator.javadoc"));
    }

    @Test
    public void mixedCaseOfHtmlTags() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedMixedCaseOfHtmlTagsAst.txt"),
                getHtmlPath("InputMixedCaseOfHtmlTags.javadoc"));
    }

    @Test
    public void htmlComments() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedCommentsAst.txt"),
                getHtmlPath("InputComments.javadoc"));
    }

    @Test
    public void negativeNumberInAttribute() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedNegativeNumberInAttributeAst.txt"),
                getHtmlPath("InputNegativeNumberInAttribute.javadoc"));
    }

    @Test
    public void dollarInLink() throws Exception {
        verifyJavadocTree(getDocPath("expectedDollarInLinkAst.txt"),
                getDocPath("InputDollarInLink.javadoc"));
    }

    @Test
    public void dotCharacterInCustomTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedCustomTagWithDotAst.txt"),
                getDocPath("InputCustomTagWithDot.javadoc"));
    }

    @Test
    public void testLinkToPackage() throws Exception {
        verifyJavadocTree(getDocPath("expectedLinkToPackageAst.txt"),
                getDocPath("InputLinkToPackage.javadoc"));
    }

    @Test
    public void testLeadingAsterisksExtended() throws Exception {
        verifyJavadocTree(getPath("expectedLeadingAsterisksExtendedAst.txt"),
                getPath("InputLeadingAsterisksExtended.javadoc"));
    }

    @Test
    public void testInlineCustomJavadocTag() throws Exception {
        verifyJavadocTree(getDocPath("expectedInlineCustomJavadocTagAst.txt"),
                getDocPath("InputInlineCustomJavadocTag.javadoc"));
    }

    @Test
    public void testAttributeValueWithoutQuotes() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedAttributeValueWithoutQuotesAst.txt"),
                getHtmlPath("InputAttributeValueWithoutQuotes.javadoc"));
    }

    @Test
    public void testClosedOtherTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedClosedOtherTagAst.txt"),
                getHtmlPath("InputClosedOtherTag.javadoc"));
    }

    @Test
    public void testAllStandardJavadocTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedAllStandardJavadocTagsAst.txt"),
                getDocPath("InputAllStandardJavadocTags.javadoc"));
    }

    @Test
    public void testAsteriskInJavadocInlineTag() throws Exception {
        verifyJavadocTree(getDocPath("expectedAsteriskInJavadocInlineTagAst.txt"),
                getDocPath("InputAsteriskInJavadocInlineTag.javadoc"));
    }

    @Test
    public void testAsteriskInLiteral() throws Exception {
        verifyJavadocTree(getDocPath("expectedAsteriskInLiteralAst.txt"),
                getDocPath("InputAsteriskInLiteral.javadoc"));
    }

    @Test
    public void testInnerBracesInCodeTag() throws Exception {
        verifyJavadocTree(getDocPath("expectedInnerBracesInCodeTagAst.txt"),
                getDocPath("InputInnerBracesInCodeTag.javadoc"));
    }

    @Test
    public void testNewlineAndAsteriskInParameters() throws Exception {
        verifyJavadocTree(getDocPath("expectedNewlineAndAsteriskInParametersAst.txt"),
                getDocPath("InputNewlineAndAsteriskInParameters.javadoc"));
    }

    @Test
    public void testTwoLinkTagsInRow() throws Exception {
        verifyJavadocTree(getDocPath("expectedTwoLinkTagsInRowAst.txt"),
                getDocPath("InputTwoLinkTagsInRow.javadoc"));
    }

    @Test
    public void testJavadocWithCrAsNewline() throws Exception {
        verifyJavadocTree(getPath("expectedJavadocWithCrAsNewlineAst.txt"),
                getPath("InputJavadocWithCrAsNewline.javadoc"));
    }
}
