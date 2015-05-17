////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.puppycrawl.tools.checkstyle.grammars.javadoc.JavadocLexer;
import com.puppycrawl.tools.checkstyle.grammars.javadoc.JavadocParser;

public class JavadocParseTreeTest {
    private final File folderWithInputFiles = new File(
            "src/test/resources/com/puppycrawl/tools/checkstyle/grammars/javadoc");
    private JavadocParser parser;
    private final BaseErrorListener errorListener = new FailOnErrorListener();

    public ParseTree parseJavadoc(String aBlockComment)
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

    public static String getFileContent(File filename)
        throws IOException {
        return Files.toString(filename, Charsets.UTF_8);
    }

    @Test
    public void oneSimpleHtmlTag()
        throws IOException {
        String filename = folderWithInputFiles + "/htmlTags/OneSimpleHtmlTag.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeOneSimpleHtmlTag();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void textBeforeJavadocTags()
        throws IOException {
        String filename = folderWithInputFiles + "/javadocTags/TextBeforeJavadocTags.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeTextBeforeJavadocTags();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void customJavadocTags()
        throws IOException {
        String filename = folderWithInputFiles + "/javadocTags/CustomJavadocTags.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeCustomJavadocTags();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void javadocTagDescriptionWithInlineTags()
        throws IOException {
        String filename = folderWithInputFiles + "/javadocTags/JavadocTagDescriptionWithInlineTags.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeJavadocTagDescriptionWithInlineTags();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void leadingAsterisks()
        throws IOException {
        String filename = folderWithInputFiles + "/LeadingAsterisks.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeLeadingAsterisks();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void authorWithMailto()
        throws IOException {
        String filename = folderWithInputFiles + "/javadocTags/AuthorWithMailto.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeAuthorWithMailto();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void htmlTagsInParagraph()
        throws IOException {
        String filename = folderWithInputFiles + "/htmlTags/HtmlTagsInParagraph.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeHtmlTagsInParagraph();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void linkInlineTags()
        throws IOException {
        String filename = folderWithInputFiles + "/javadocTags/LinkInlineTags.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeLinkInlineTags();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void seeReferenceWithFewNestedClasses()
        throws IOException {
        String filename = folderWithInputFiles + "/javadocTags/SeeReferenceWithFewNestedClasses.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeSeeReferenceWithFewNestedClasses();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void paramWithGeneric()
        throws IOException {
        String filename = folderWithInputFiles + "/javadocTags/ParamWithGeneric.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeParamWithGeneric();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void serial()
        throws IOException {
        String filename = folderWithInputFiles + "/javadocTags/Serial.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeSerial();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void since()
        throws IOException {
        String filename = folderWithInputFiles + "/javadocTags/Since.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeSince();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void unclosedAndClosedParagraphs()
        throws IOException {
        String filename = folderWithInputFiles + "/htmlTags/UnclosedAndClosedParagraphs.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeUnclosedAndClosedParagraphs();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void listWithUnclosedItemInUnclosedParagraph()
        throws IOException {
        String filename = folderWithInputFiles + "/htmlTags/ListWithUnclosedItemInUnclosedParagraph.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeListWithUnclosedItemInUnclosedParagraph();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void unclosedParagraphFollowedByJavadocTag()
        throws IOException {
        String filename = folderWithInputFiles + "/htmlTags/UnclosedParagraphFollowedByJavadocTag.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeUnclosedParagraphFollowedByJavadocTag();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void allJavadocInlineTags()
        throws IOException {
        String filename = folderWithInputFiles + "/javadocTags/AllJavadocInlineTags.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeAllJavadocInlineTags();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void docRootInheritDoc()
        throws IOException {
        String filename = folderWithInputFiles + "/javadocTags/DocRootInheritDoc.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeDocRootInheritDoc();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void fewWhiteSpacesAsSeparator()
        throws IOException {
        String filename = folderWithInputFiles + "/javadocTags/FewWhiteSpacesAsSeparator.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeFewWhiteSpacesAsSeparator();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void mixedCaseOfHtmlTags()
        throws IOException {
        String filename = folderWithInputFiles + "/htmlTags/MixedCaseOfHtmlTags.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeMixedCaseOfHtmlTags();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void htmlComments()
        throws IOException {
        String filename = folderWithInputFiles + "/htmlTags/Comments.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeComments();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void negativeNumberInAttribute()
        throws IOException {
        String filename = folderWithInputFiles + "/htmlTags/NegativeNumberInAttribute.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeNegativeNumberInAttribute();
        compareTrees(expectedTree, generatedTree);
    }

    @Test
    public void dollarInLink()
        throws IOException {
        String filename = folderWithInputFiles + "/javadocTags/DollarInLink.txt";
        ParseTree generatedTree = parseJavadoc(getFileContent(new File(filename)));
        ParseTree expectedTree = ParseTreeBuilder.treeDollarInLink();
        compareTrees(expectedTree, generatedTree);
    }

    private void compareTrees(ParseTree t1, ParseTree t2) {
        Assert.assertEquals(t1.toStringTree(parser), t2.toStringTree(parser));
    }

    public static class FailOnErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(
                Recognizer<?, ?> aRecognizer, Object aOffendingSymbol,
                int aLine, int aCharPositionInLine,
                String aMsg, RecognitionException aEx) {
            Assert.fail("[" + aLine + ", " + aCharPositionInLine + "] " + aMsg);
        }
    }

}
