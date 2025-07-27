package com.puppycrawl.tools.checkstyle.grammar.javadoc;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class JavadocCommentsAstRegressionTest extends AbstractTreeTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/javadoc/";
    }

    private String geSimplePath(String filename) throws IOException {
        return getPath("simple" + File.separator + filename);
    }

    private String getInlineTagsPath(String filename) throws IOException {
        return getPath("inlinetags" + File.separator + filename);
    }

    private String getHTMLTagsPath(String filename) throws IOException {
        return getPath("htmltags" + File.separator + filename);
    }

    @Test
    public void testEmptyJavadoc() throws Exception {
        verifyJavadocTree(geSimplePath("ExpectedEmptyJavadoc.txt"),
                geSimplePath("InputEmptyJavadoc.javadoc"));
    }

    @Test
    public void testEmptyJavadocWithTabs() throws Exception {
        verifyJavadocTree(geSimplePath("ExpectedEmptyJavadocWithTabs.txt"),
                geSimplePath("InputEmptyJavadocWithTabs.javadoc"));
    }

    @Test
    public void testEmptyJavadocStartsWithNewline() throws Exception {
        verifyJavadocTree(geSimplePath("ExpectedEmptyJavadocStartsWithNewline.txt"),
                geSimplePath("InputEmptyJavadocStartsWithNewline.javadoc"));
    }

    @Test
    public void testSimpleJavadocWithText() throws Exception {
        verifyJavadocTree(geSimplePath("ExpectedSimpleJavadocWithText.txt"),
                geSimplePath("InputSimpleJavadocWithText.javadoc"));
    }

    @Test
    public void testSimpleJavadocWithText2() throws Exception {
        verifyJavadocTree(geSimplePath("ExpectedSimpleJavadocWithText2.txt"),
                geSimplePath("InputSimpleJavadocWithText2.javadoc"));
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
    public void testHtmlElements() throws Exception {
        verifyJavadocTree(getHTMLTagsPath("ExpectedHtmlElements.txt"),
                getHTMLTagsPath("InputHtmlElements.javadoc"));
    }

    @Test
    public void testHtmlVoidTags() throws Exception {
        verifyJavadocTree(getHTMLTagsPath("ExpectedHtmlVoidTags.txt"),
                getHTMLTagsPath("InputHtmlVoidTags.javadoc"));
    }

    @Test
    public void testEmptyHtmlContent() throws Exception {
        verifyJavadocTree(getHTMLTagsPath("ExpectedEmptyHtmlContent.txt"),
                getHTMLTagsPath("InputEmptyHtmlContent.javadoc"));
    }

    @Test
    public void testNonTightTags1() throws Exception {
        verifyJavadocTree(getHTMLTagsPath("ExpectedNonTightTags1.txt"),
                getHTMLTagsPath("InputNonTightTags1.javadoc"));
    }

    @Test
    public void testNonTightTags2() throws Exception {
        verifyJavadocTree(getHTMLTagsPath("ExpectedNonTightTags2.txt"),
                getHTMLTagsPath("InputNonTightTags2.javadoc"));
    }

    @Test
    public void testNonTightTags3() throws Exception {
        verifyJavadocTree(getHTMLTagsPath("ExpectedNonTightTags3.txt"),
                getHTMLTagsPath("InputNonTightTags3.javadoc"));
    }

    @Test
    public void testNonTightTags4() throws Exception {
        verifyJavadocTree(getHTMLTagsPath("ExpectedNonTightTags4.txt"),
                getHTMLTagsPath("InputNonTightTags4.javadoc"));
    }
}
