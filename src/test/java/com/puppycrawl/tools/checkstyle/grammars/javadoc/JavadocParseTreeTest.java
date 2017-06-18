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

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;

public class JavadocParseTreeTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("grammars" + File.separator + "javadoc" + File.separator + filename);
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
                getHtmlPath("InputOneSimpleHtmlTag.txt"));
    }

    @Test
    public void textBeforeJavadocTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedTextBeforeJavadocTagsAst.txt"),
                getDocPath("InputTextBeforeJavadocTags.txt"));
    }

    @Test
    public void customJavadocTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedCustomJavadocTagsAst.txt"),
                getDocPath("InputCustomJavadocTags.txt"));
    }

    @Test
    public void javadocTagDescriptionWithInlineTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedJavadocTagDescriptionWithInlineTagsAst.txt"),
                getDocPath("InputJavadocTagDescriptionWithInlineTags.txt"));
    }

    @Test
    public void leadingAsterisks() throws Exception {
        verifyJavadocTree(getPath("expectedLeadingAsterisksAst.txt"),
                getPath("InputLeadingAsterisks.txt"));
    }

    @Test
    public void authorWithMailto() throws Exception {
        verifyJavadocTree(getDocPath("expectedAuthorWithMailtoAst.txt"),
                getDocPath("InputAuthorWithMailto.txt"));
    }

    @Test
    public void htmlTagsInParagraph() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedHtmlTagsInParagraphAst.txt"),
                getHtmlPath("InputHtmlTagsInParagraph.txt"));
    }

    @Test
    public void linkInlineTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedLinkInlineTagsAst.txt"),
                getDocPath("InputLinkInlineTags.txt"));
    }

    @Test
    public void seeReferenceWithFewNestedClasses() throws Exception {
        verifyJavadocTree(getDocPath("expectedSeeReferenceWithFewNestedClassesAst.txt"),
                getDocPath("InputSeeReferenceWithFewNestedClasses.txt"));
    }

    @Test
    public void paramWithGeneric() throws Exception {
        verifyJavadocTree(getDocPath("expectedParamWithGenericAst.txt"),
                getDocPath("InputParamWithGeneric.txt"));
    }

    @Test
    public void serial() throws Exception {
        verifyJavadocTree(getDocPath("expectedSerialAst.txt"),
                getDocPath("InputSerial.txt"));
    }

    @Test
    public void since() throws Exception {
        verifyJavadocTree(getDocPath("expectedSinceAst.txt"),
                getDocPath("InputSince.txt"));
    }

    @Test
    public void unclosedAndClosedParagraphs() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedUnclosedAndClosedParagraphsAst.txt"),
                getHtmlPath("InputUnclosedAndClosedParagraphs.txt"));
    }

    @Test
    public void listWithUnclosedItemInUnclosedParagraph() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedListWithUnclosedItemInUnclosedParagraphAst.txt"),
                getHtmlPath("InputListWithUnclosedItemInUnclosedParagraph.txt"));
    }

    @Test
    public void unclosedParagraphFollowedByJavadocTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedUnclosedParagraphFollowedByJavadocTagAst.txt"),
                getHtmlPath("InputUnclosedParagraphFollowedByJavadocTag.txt"));
    }

    @Test
    public void allJavadocInlineTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedAllJavadocInlineTagsAst.txt"),
                getDocPath("InputAllJavadocInlineTags.txt"));
    }

    @Test
    public void docRootInheritDoc() throws Exception {
        verifyJavadocTree(getDocPath("expectedDocRootInheritDocAst.txt"),
                getDocPath("InputDocRootInheritDoc.txt"));
    }

    @Test
    public void fewWhiteSpacesAsSeparator() throws Exception {
        verifyJavadocTree(getDocPath("expectedFewWhiteSpacesAsSeparatorAst.txt"),
                getDocPath("InputFewWhiteSpacesAsSeparator.txt"));
    }

    @Test
    public void mixedCaseOfHtmlTags() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedMixedCaseOfHtmlTagsAst.txt"),
                getHtmlPath("InputMixedCaseOfHtmlTags.txt"));
    }

    @Test
    public void htmlComments() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedCommentsAst.txt"),
                getHtmlPath("InputComments.txt"));
    }

    @Test
    public void negativeNumberInAttribute() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedNegativeNumberInAttributeAst.txt"),
                getHtmlPath("InputNegativeNumberInAttribute.txt"));
    }

    @Test
    public void dollarInLink() throws Exception {
        verifyJavadocTree(getDocPath("expectedDollarInLinkAst.txt"),
                getDocPath("InputDollarInLink.txt"));
    }

    @Test
    public void dotCharacterInCustomTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedCustomTagWithDotAst.txt"),
                getDocPath("InputCustomTagWithDot.txt"));
    }

    @Test
    public void testLinkToPackage() throws Exception {
        verifyJavadocTree(getDocPath("expectedLinkToPackageAst.txt"),
                getDocPath("InputLinkToPackage.txt"));
    }

    @Test
    public void testLeadingAsterisksExtended() throws Exception {
        verifyJavadocTree(getPath("expectedLeadingAsterisksExtendedAst.txt"),
                getPath("InputLeadingAsterisksExtended.txt"));
    }

    @Test
    public void testInlineCustomJavadocTag() throws Exception {
        verifyJavadocTree(getDocPath("expectedInlineCustomJavadocTagAst.txt"),
                getDocPath("InputInlineCustomJavadocTag.txt"));
    }

    @Test
    public void testAttributeValueWithoutQuotes() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedAttributeValueWithoutQuotesAst.txt"),
                getHtmlPath("InputAttributeValueWithoutQuotes.txt"));
    }

    @Test
    public void testClosedOtherTag() throws Exception {
        verifyJavadocTree(getHtmlPath("expectedClosedOtherTagAst.txt"),
                getHtmlPath("InputClosedOtherTag.txt"));
    }

    @Test
    public void testAllStandardJavadocTags() throws Exception {
        verifyJavadocTree(getDocPath("expectedAllStandardJavadocTagsAst.txt"),
                getDocPath("InputAllStandardJavadocTags.txt"));
    }

    @Test
    public void testAsteriskInJavadocInlineTag() throws Exception {
        verifyJavadocTree(getDocPath("expectedAsteriskInJavadocInlineTagAst.txt"),
                getDocPath("InputAsteriskInJavadocInlineTag.txt"));
    }

    @Test
    public void testAsteriskInLiteral() throws Exception {
        verifyJavadocTree(getDocPath("expectedAsteriskInLiteralAst.txt"),
                getDocPath("InputAsteriskInLiteral.txt"));
    }

    @Test
    public void testInnerBracesInCodeTag() throws Exception {
        verifyJavadocTree(getDocPath("expectedInnerBracesInCodeTagAst.txt"),
                getDocPath("InputInnerBracesInCodeTag.txt"));
    }

    @Test
    public void testNewlineAndAsteriskInParameters() throws Exception {
        verifyJavadocTree(getDocPath("expectedNewlineAndAsteriskInParametersAst.txt"),
                getDocPath("InputNewlineAndAsteriskInParameters.txt"));
    }

    @Test
    public void testTwoLinkTagsInRow() throws Exception {
        verifyJavadocTree(getDocPath("expectedTwoLinkTagsInRowAst.txt"),
                getDocPath("InputTwoLinkTagsInRow.txt"));
    }
}
