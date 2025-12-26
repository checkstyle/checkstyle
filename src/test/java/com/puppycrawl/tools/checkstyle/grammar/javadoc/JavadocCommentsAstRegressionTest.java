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
    public String getPackageLocation() {
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
    void emptyJavadoc() throws Exception {
        verifyJavadocTree(getPath("ExpectedEmptyJavadoc.txt"),
                getPath("InputEmptyJavadoc.javadoc"));
    }

    @Test
    void emptyJavadocWithTabs() throws Exception {
        verifyJavadocTree(getPath("ExpectedEmptyJavadocWithTabs.txt"),
                getPath("InputEmptyJavadocWithTabs.javadoc"));
    }

    @Test
    void emptyJavadocStartsWithNewline() throws Exception {
        verifyJavadocTree(getPath("ExpectedEmptyJavadocStartsWithNewline.txt"),
                getPath("InputEmptyJavadocStartsWithNewline.javadoc"));
    }

    @Test
    void simpleJavadocWithText() throws Exception {
        verifyJavadocTree(getPath("ExpectedSimpleJavadocWithText.txt"),
                getPath("InputSimpleJavadocWithText.javadoc"));
    }

    @Test
    void simpleJavadocWithText2() throws Exception {
        verifyJavadocTree(getPath("ExpectedSimpleJavadocWithText2.txt"),
                getPath("InputSimpleJavadocWithText2.javadoc"));
    }

    @Test
    void authorTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedAuthorTag.txt"),
                getBlockTagsPath("InputAuthorTags.javadoc"));
    }

    @Test
    void deprecatedTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedDeprecatedTag.txt"),
                getBlockTagsPath("InputDeprecatedTag.javadoc"));
    }

    @Test
    void returnTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedReturnTag.txt"),
                getBlockTagsPath("InputReturnTag.javadoc"));
    }

    @Test
    void paramTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedParamTag.txt"),
                getBlockTagsPath("InputParamTag.javadoc"));
    }

    @Test
    void throwsAndExceptionTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedThrowsAndExceptionTag.txt"),
                getBlockTagsPath("InputThrowsAndExceptionTag.javadoc"));
    }

    @Test
    void sinceAndVersionTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedSinceAndVersionTag.txt"),
                getBlockTagsPath("InputSinceAndVersionTag.javadoc"));
    }

    @Test
    void seeTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedSeeTag.txt"),
                getBlockTagsPath("InputSeeTag.javadoc"));
    }

    @Test
    void hiddenAndUsesAndProvidesTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedHiddenAndUsesAndProvidesTag.txt"),
                getBlockTagsPath("InputHiddenAndUsesAndProvidesTag.javadoc"));
    }

    @Test
    void serialTags() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedSerialTags.txt"),
                getBlockTagsPath("InputSerialTags.javadoc"));
    }

    @Test
    void customBlockTag() throws Exception {
        verifyJavadocTree(getBlockTagsPath("ExpectedCustomBlockTag.txt"),
                getBlockTagsPath("InputCustomBlockTag.javadoc"));
    }

    @Test
    void codeInlineTag() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedCodeInlineTag.txt"),
                getInlineTagsPath("InputCodeInlineTag.javadoc"));
    }

    @Test
    void linkInlineTag() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedLinkInlineTag.txt"),
                getInlineTagsPath("InputLinkInlineTag.javadoc"));
    }

    @Test
    void linkInlineTag2() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedLinkInlineTag2.txt"),
                getInlineTagsPath("InputLinkInlineTag2.javadoc"));
    }

    @Test
    void linkInlineTag3() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedLinkInlineTag3.txt"),
                getInlineTagsPath("InputLinkInlineTag3.javadoc"));
    }

    @Test
    void valueAndInheritDocInlineTag() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedValueAndInheritDocInlineTag.txt"),
                getInlineTagsPath("InputValueAndInheritDocInlineTag.javadoc"));
    }

    @Test
    void systemPropertyInlineTag() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedSystemPropertyTag.txt"),
                getInlineTagsPath("InputSystemPropertyTag.javadoc"));
    }

    @Test
    void literalAndCustomInlineTag() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedLiteralAndCustomInline.txt"),
                getInlineTagsPath("InputLiteralAndCustomInline.javadoc"));
    }

    @Test
    void returnAndIndexInlineTag() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedReturnAndIndexInlineTag.txt"),
                getInlineTagsPath("InputReturnAndIndexInlineTag.javadoc"));
    }

    @Test
    void snippetAttributeInline() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedSnippetAttributeInlineTag.txt"),
                getInlineTagsPath("InputSnippetAttributeInlineTag.javadoc"));
    }

    @Test
    void snippetAttributeInline2() throws Exception {
        verifyJavadocTree(getInlineTagsPath("ExpectedSnippetAttributeInlineTag2.txt"),
                getInlineTagsPath("InputSnippetAttributeInlineTag2.javadoc"));
    }

    @Test
    void htmlElements() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedHtmlElements.txt"),
                getHtmlTagsPath("InputHtmlElements.javadoc"));
    }

    @Test
    void htmlVoidTags() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedHtmlVoidTags.txt"),
                getHtmlTagsPath("InputHtmlVoidTags.javadoc"));
    }

    @Test
    void emptyHtmlContent() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedEmptyHtmlContent.txt"),
                getHtmlTagsPath("InputEmptyHtmlContent.javadoc"));
    }

    @Test
    void nonTightTags1() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedNonTightTags1.txt"),
                getHtmlTagsPath("InputNonTightTags1.javadoc"));
    }

    @Test
    void nonTightTags2() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedNonTightTags2.txt"),
                getHtmlTagsPath("InputNonTightTags2.javadoc"));
    }

    @Test
    void nonTightTags3() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedNonTightTags3.txt"),
                getHtmlTagsPath("InputNonTightTags3.javadoc"));
    }

    @Test
    void nonTightTags4() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedNonTightTags4.txt"),
                getHtmlTagsPath("InputNonTightTags4.javadoc"));
    }

    @Test
    void javadocWithoutLeadingAsterisks() throws Exception {
        verifyJavadocTree(getPath("ExpectedJavadocWithoutLeadingAsterisk.txt"),
                getPath("InputJavadocWithoutLeadingAsterisk.javadoc"));
    }

    @Test
    void newlinesInHtmlAttributes() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedNewlinesInHtmlAttributes.txt"),
                getHtmlTagsPath("InputNewlinesInHtmlAttributes.javadoc"));
    }

    @Test
    void crAsNewLine() throws Exception {
        verifyJavadocTree(getPath("ExpectedCrAsNewline.txt"),
                getPath("InputCrAsNewline.javadoc"));
    }

    @Test
    void htmlComments() throws Exception {
        verifyJavadocTree(getHtmlTagsPath("ExpectedHtmlComment.txt"),
                getHtmlTagsPath("InputHtmlComment.javadoc"));
    }
}
