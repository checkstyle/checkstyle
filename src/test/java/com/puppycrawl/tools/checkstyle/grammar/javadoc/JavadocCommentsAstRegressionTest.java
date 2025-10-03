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

public class JavadocCommentsAstRegressionTest extends AbstractTreeTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/javadoc/";
    }

    private String getInlineTagsPath(String filename) throws IOException {
        return getPath("inlinetags" + File.separator + filename);
    }

    private String getHtmlTagsPath(String filename) throws IOException {
        return getPath("htmltags" + File.separator + filename);
    }

    private String getBlockTagsPath(String filename) throws IOException {
        return getPath("blockTags" + File.separator + filename);
    }

    @Test
    public void testEmptyJavadoc() throws Exception {
        verifyJavadocTree(getPath("ExpectedEmptyJavadoc.txt"),
                getPath("InputEmptyJavadoc.javadoc"));
    }

    @Test
    public void testEmptyJavadocWithTabs() throws Exception {
        verifyJavadocTree(getPath("ExpectedEmptyJavadocWithTabs.txt"),
                getPath("InputEmptyJavadocWithTabs.javadoc"));
    }

    @Test
    public void testEmptyJavadocStartsWithNewline() throws Exception {
        verifyJavadocTree(getPath("ExpectedEmptyJavadocStartsWithNewline.txt"),
                getPath("InputEmptyJavadocStartsWithNewline.javadoc"));
    }

    @Test
    public void testSimpleJavadocWithText() throws Exception {
        verifyJavadocTree(getPath("ExpectedSimpleJavadocWithText.txt"),
                getPath("InputSimpleJavadocWithText.javadoc"));
    }

    @Test
    public void testSimpleJavadocWithText2() throws Exception {
        verifyJavadocTree(getPath("ExpectedSimpleJavadocWithText2.txt"),
                getPath("InputSimpleJavadocWithText2.javadoc"));
    }

    @Test
    public void testAuthorTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedAuthorTag.txt"),
                getBlockTagsPath("InputAuthorTags.javadoc"));
    }

    @Test
    public void testDeprecatedTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedDeprecatedTag.txt"),
                getBlockTagsPath("InputDeprecatedTag.javadoc"));
    }

    @Test
    public void testReturnTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedReturnTag.txt"),
                getBlockTagsPath("InputReturnTag.javadoc"));
    }

    @Test
    public void testParamTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedParamTag.txt"),
                getBlockTagsPath("InputParamTag.javadoc"));
    }

    @Test
    public void testThrowsAndExceptionTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedThrowsAndExceptionTag.txt"),
                getBlockTagsPath("InputThrowsAndExceptionTag.javadoc"));
    }

    @Test
    public void testSinceAndVersionTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedSinceAndVersionTag.txt"),
                getBlockTagsPath("InputSinceAndVersionTag.javadoc"));
    }

    @Test
    public void testSeeTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedSeeTag.txt"),
                getBlockTagsPath("InputSeeTag.javadoc"));
    }

    @Test
    public void testHiddenAndUsesAndProvidesTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedHiddenAndUsesAndProvidesTag.txt"),
                getBlockTagsPath("InputHiddenAndUsesAndProvidesTag.javadoc"));
    }

    @Test
    public void testSerialTags() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedSerialTags.txt"),
                getBlockTagsPath("InputSerialTags.javadoc"));
    }

    @Test
    public void testCustomBlockTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedCustomBlockTag.txt"),
                getBlockTagsPath("InputCustomBlockTag.javadoc"));
    }

    @Test
    public void testCodeInlineTag() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedCodeInlineTag.txt"),
                getInlineTagsPath("InputCodeInlineTag.javadoc"));
    }

    @Test
    public void testLinkInlineTag() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedLinkInlineTag.txt"),
                getInlineTagsPath("InputLinkInlineTag.javadoc"));
    }

    @Test
    public void testLinkInlineTag2() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedLinkInlineTag2.txt"),
                getInlineTagsPath("InputLinkInlineTag2.javadoc"));
    }

    @Test
    public void testLinkInlineTag3() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedLinkInlineTag3.txt"),
                getInlineTagsPath("InputLinkInlineTag3.javadoc"));
    }

    @Test
    public void testValueAndInheritDocInlineTag() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedValueAndInheritDocInlineTag.txt"),
                getInlineTagsPath("InputValueAndInheritDocInlineTag.javadoc"));
    }

    @Test
    public void testSystemPropertyInlineTag() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedSystemPropertyTag.txt"),
                getInlineTagsPath("InputSystemPropertyTag.javadoc"));
    }

    @Test
    public void testLiteralAndCustomInlineTag() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedLiteralAndCustomInline.txt"),
                getInlineTagsPath("InputLiteralAndCustomInline.javadoc"));
    }

    @Test
    public void testReturnAndIndexInlineTag() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedReturnAndIndexInlineTag.txt"),
                getInlineTagsPath("InputReturnAndIndexInlineTag.javadoc"));
    }

    @Test
    public void testSnippetAttributeInline() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedSnippetAttributeInlineTag.txt"),
                getInlineTagsPath("InputSnippetAttributeInlineTag.javadoc"));
    }

    @Test
    public void testSnippetAttributeInline2() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedSnippetAttributeInlineTag2.txt"),
                getInlineTagsPath("InputSnippetAttributeInlineTag2.javadoc"));
    }

    @Test
    public void testHtmlElements() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedHtmlElements.txt"),
                getHtmlTagsPath("InputHtmlElements.javadoc"));
    }

    @Test
    public void testHtmlVoidTags() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedHtmlVoidTags.txt"),
                getHtmlTagsPath("InputHtmlVoidTags.javadoc"));
    }

    @Test
    public void testEmptyHtmlContent() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedEmptyHtmlContent.txt"),
                getHtmlTagsPath("InputEmptyHtmlContent.javadoc"));
    }

    @Test
    public void testNonTightTags1() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedNonTightTags1.txt"),
                getHtmlTagsPath("InputNonTightTags1.javadoc"));
    }

    @Test
    public void testNonTightTags2() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedNonTightTags2.txt"),
                getHtmlTagsPath("InputNonTightTags2.javadoc"));
    }

    @Test
    public void testNonTightTags3() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedNonTightTags3.txt"),
                getHtmlTagsPath("InputNonTightTags3.javadoc"));
    }

    @Test
    public void testNonTightTags4() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedNonTightTags4.txt"),
                getHtmlTagsPath("InputNonTightTags4.javadoc"));
    }

    @Test
    public void testJavadocWithoutLeadingAsterisks() throws Exception {
        verifyJavadocTree(getPath("ExpectedJavadocWithoutLeadingAsterisk.txt"),
                getPath("InputJavadocWithoutLeadingAsterisk.javadoc"));
    }

    @Test
    public void testNewlinesInHtmlAttributes() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedNewlinesInHtmlAttributes.txt"),
                getHtmlTagsPath("InputNewlinesInHtmlAttributes.javadoc"));
    }

    @Test
    public void testCrAsNewLine() throws Exception {
        verifyJavadocTree(getPath("ExpectedCrAsNewline.txt"),
                getPath("InputCrAsNewline.javadoc"));
    }

    @Test
    public void testHtmlComments() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedHtmlComment.txt"),
                getHtmlTagsPath("InputHtmlComment.javadoc"));
    }
}
