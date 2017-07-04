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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Test;

public class JavadocParseTreeTest {
    private final BaseErrorListener errorListener = new FailOnErrorListener();
    private JavadocParser parser;

    private ParseTree parseJavadoc(String aBlockComment)
            throws IOException {
        final Charset utf8Charset = Charset.forName("UTF-8");
        final InputStream in = new ByteArrayInputStream(aBlockComment.getBytes(utf8Charset));

        final ANTLRInputStream input = new ANTLRInputStream(in);
        final JavadocLexer lexer = new JavadocLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);

        final CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new JavadocParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        return parser.javadoc();
    }

    private static String getFileContent(File filename)
            throws IOException {
        return new String(Files.readAllBytes(filename.toPath()), StandardCharsets.UTF_8);
    }

    private static String getPath(String filename) throws IOException {
        return new File(
                "src/test/resources/com/puppycrawl/tools/checkstyle/grammars/javadoc/"
                    + filename).getCanonicalPath();
    }

    private static String getHtmlPath(String filename) throws IOException {
        return getPath("htmlTags" + File.separator + filename);
    }

    private static String getDocPath(String filename) throws IOException {
        return getPath("javadocTags" + File.separator + filename);
    }

    @Test
    public void oneSimpleHtmlTag()
            throws IOException {
        final String filename = getHtmlPath("InputOneSimpleHtmlTag.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeOneSimpleHtmlTag();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void textBeforeJavadocTags()
            throws IOException {
        final String filename = getDocPath("InputTextBeforeJavadocTags.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeTextBeforeJavadocTags();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void customJavadocTags()
            throws IOException {
        final String filename = getDocPath("InputCustomJavadocTags.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeCustomJavadocTags();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void javadocTagDescriptionWithInlineTags()
            throws IOException {
        final String filename = getDocPath("InputJavadocTagDescriptionWithInlineTags.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeJavadocTagDescriptionWithInlineTags();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void leadingAsterisks()
            throws IOException {
        final String filename = getPath("InputLeadingAsterisks.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeLeadingAsterisks();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void authorWithMailto()
            throws IOException {
        final String filename = getDocPath("InputAuthorWithMailto.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeAuthorWithMailto();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void htmlTagsInParagraph()
            throws IOException {
        final String filename = getHtmlPath("InputHtmlTagsInParagraph.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeHtmlTagsInParagraph();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void linkInlineTags()
            throws IOException {
        final String filename = getDocPath("InputLinkInlineTags.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeLinkInlineTags();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void seeReferenceWithFewNestedClasses()
            throws IOException {
        final String filename = getDocPath("InputSeeReferenceWithFewNestedClasses.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeSeeReferenceWithFewNestedClasses();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void paramWithGeneric()
            throws IOException {
        final String filename = getDocPath("InputParamWithGeneric.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeParamWithGeneric();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void serial()
            throws IOException {
        final String filename = getDocPath("InputSerial.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeSerial();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void since()
            throws IOException {
        final String filename = getDocPath("InputSince.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeSince();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void unclosedAndClosedParagraphs()
            throws IOException {
        final String filename = getHtmlPath("InputUnclosedAndClosedParagraphs.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeUnclosedAndClosedParagraphs();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void listWithUnclosedItemInUnclosedParagraph()
            throws IOException {
        final String filename = getHtmlPath("InputListWithUnclosedItemInUnclosedParagraph.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder
                .treeListWithUnclosedItemInUnclosedParagraph();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void unclosedParagraphFollowedByJavadocTag()
            throws IOException {
        final String filename = getHtmlPath("InputUnclosedParagraphFollowedByJavadocTag.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeUnclosedParagraphFollowedByJavadocTag();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void allJavadocInlineTags()
            throws IOException {
        final String filename = getDocPath("InputAllJavadocInlineTags.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeAllJavadocInlineTags();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void docRootInheritDoc()
            throws IOException {
        final String filename = getDocPath("InputDocRootInheritDoc.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeDocRootInheritDoc();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void fewWhiteSpacesAsSeparator()
            throws IOException {
        final String filename = getDocPath("InputFewWhiteSpacesAsSeparator.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeFewWhiteSpacesAsSeparator();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void mixedCaseOfHtmlTags()
            throws IOException {
        final String filename = getHtmlPath("InputMixedCaseOfHtmlTags.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeMixedCaseOfHtmlTags();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void htmlComments()
            throws IOException {
        final String filename = getHtmlPath("InputComments.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeComments();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void negativeNumberInAttribute()
            throws IOException {
        final String filename = getHtmlPath("InputNegativeNumberInAttribute.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeNegativeNumberInAttribute();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void dollarInLink()
            throws IOException {
        final String filename = getDocPath("InputDollarInLink.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeDollarInLink();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void dotCharacterInCustomTags()
            throws IOException {
        final String filename = getDocPath("InputCustomTagWithDot.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeCustomTagWithDot();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void testLinkToPackage() throws IOException {
        final String filename = getDocPath("InputLinkToPackage.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeLinkToPackage();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void testLeadingAsterisksExtended() throws IOException {
        final String filename = getPath("InputLeadingAsterisksExtended.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeLeadingAsterisksExtended();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void testInlineCustomJavadocTag() throws IOException {
        final String filename = getDocPath("InputInlineCustomJavadocTag.txt");
        final ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        final ParseTree expectedTree = ParseTreeBuilder.treeInlineCustomJavadocTag();
        compareTrees(expectedTree, generatedTree);
    }

    private void compareTrees(ParseTree first, ParseTree second) {
        Assert.assertEquals(first.toStringTree(parser), second.toStringTree(parser));
    }

    private static class FailOnErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(
                Recognizer<?, ?> recognizer, Object offendingSymbol,
                int line, int charPositionInLine,
                String msg, RecognitionException ex) {
            Assert.fail("[" + line + ", " + charPositionInLine + "] " + msg);
        }
    }

}
