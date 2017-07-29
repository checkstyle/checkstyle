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

package com.puppycrawl.tools.checkstyle;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtils;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

/**
 * Class for printing AST to String.
 * @author Vladislav Lisetskii
 */
public final class AstTreeStringPrinter {

    /**
     * Enum to be used for test if comments should be printed.
     */
    public enum PrintOptions {
        /**
         * Comments has to be printed.
         */
        WITH_COMMENTS,
        /**
         * Comments has NOT to be printed.
         */
        WITHOUT_COMMENTS
    }

    /** Newline pattern. */
    private static final Pattern NEWLINE = Pattern.compile("\n");
    /** Return pattern. */
    private static final Pattern RETURN = Pattern.compile("\r");
    /** Tab pattern. */
    private static final Pattern TAB = Pattern.compile("\t");

    /** OS specific line separator. */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /** Prevent instances. */
    private AstTreeStringPrinter() {
        // no code
    }

    /**
     * Parse a file and print the parse tree.
     * @param file the file to print.
     * @param withComments true to include comments to AST
     * @return the AST of the file in String form.
     * @throws IOException if the file could not be read.
     * @throws CheckstyleException if the file is not a Java source.
     */
    public static String printFileAst(File file, PrintOptions withComments)
            throws IOException, CheckstyleException {
        return printTree(parseFile(file, withComments));
    }

    /**
     * Prints full AST (java + comments + javadoc) of the java file.
     * @param file java file
     * @return Full tree
     * @throws IOException Failed to open a file
     * @throws CheckstyleException error while parsing the file
     */
    public static String printJavaAndJavadocTree(File file)
            throws IOException, CheckstyleException {
        final DetailAST tree = parseFile(file, PrintOptions.WITH_COMMENTS);
        return printJavaAndJavadocTree(tree);
    }

    /**
     * Prints full tree (java + comments + javadoc) of the DetailAST.
     * @param ast root DetailAST
     * @return Full tree
     */
    private static String printJavaAndJavadocTree(DetailAST ast) {
        final StringBuilder messageBuilder = new StringBuilder(1024);
        DetailAST node = ast;
        while (node != null) {
            messageBuilder.append(getIndentation(node))
                .append(getNodeInfo(node))
                .append(LINE_SEPARATOR);
            if (node.getType() == TokenTypes.COMMENT_CONTENT
                    && JavadocUtils.isJavadocComment(node.getParent())) {
                final String javadocTree = parseAndPrintJavadocTree(node);
                messageBuilder.append(javadocTree);
            }
            else {
                messageBuilder.append(printJavaAndJavadocTree(node.getFirstChild()));
            }
            node = node.getNextSibling();
        }
        return messageBuilder.toString();
    }

    /**
     * Parses block comment as javadoc and prints its tree.
     * @param node block comment begin
     * @return string javadoc tree
     */
    private static String parseAndPrintJavadocTree(DetailAST node) {
        final DetailAST javadocBlock = node.getParent();
        final DetailNode tree = DetailNodeTreeStringPrinter.parseJavadocAsDetailNode(javadocBlock);

        String baseIndentation = getIndentation(node);
        baseIndentation = baseIndentation.substring(0, baseIndentation.length() - 2);
        final String rootPrefix = baseIndentation + "   `--";
        final String prefix = baseIndentation + "       ";
        return DetailNodeTreeStringPrinter.printTree(tree, rootPrefix, prefix);
    }

    /**
     * Parse a file and print the parse tree.
     * @param text the text to parse.
     * @param withComments true to include comments to AST
     * @return the AST of the file in String form.
     * @throws CheckstyleException if the file is not a Java source.
     */
    public static String printAst(FileText text,
                                  PrintOptions withComments) throws CheckstyleException {
        return printTree(parseFileText(text, withComments));
    }

    /**
     * Print AST.
     * @param ast the root AST node.
     * @return string AST.
     */
    private static String printTree(DetailAST ast) {
        final StringBuilder messageBuilder = new StringBuilder(1024);
        DetailAST node = ast;
        while (node != null) {
            messageBuilder.append(getIndentation(node))
                    .append(getNodeInfo(node))
                    .append(LINE_SEPARATOR)
                    .append(printTree(node.getFirstChild()));
            node = node.getNextSibling();
        }
        return messageBuilder.toString();
    }

    /**
     * Get string representation of the node as token name,
     * node text, line number and column number.
     * @param node DetailAST
     * @return node info
     */
    private static String getNodeInfo(DetailAST node) {
        return TokenUtils.getTokenName(node.getType())
                + " -> " + escapeAllControlChars(node.getText())
                + " [" + node.getLineNo() + ':' + node.getColumnNo() + ']';
    }

    /**
     * Get indentation for an AST node.
     * @param ast the AST to get the indentation for.
     * @return the indentation in String format.
     */
    private static String getIndentation(DetailAST ast) {
        final boolean isLastChild = ast.getNextSibling() == null;
        DetailAST node = ast;
        final StringBuilder indentation = new StringBuilder(1024);
        while (node.getParent() != null) {
            node = node.getParent();
            if (node.getParent() == null) {
                if (isLastChild) {
                    // only ASCII symbols must be used due to
                    // problems with running tests on Windows
                    indentation.append("`--");
                }
                else {
                    indentation.append("|--");
                }
            }
            else {
                if (node.getNextSibling() == null) {
                    indentation.insert(0, "    ");
                }
                else {
                    indentation.insert(0, "|   ");
                }
            }
        }
        return indentation.toString();
    }

    /**
     * Replace all control chars with escaped symbols.
     * @param text the String to process.
     * @return the processed String with all control chars escaped.
     */
    private static String escapeAllControlChars(String text) {
        final String textWithoutNewlines = NEWLINE.matcher(text).replaceAll("\\\\n");
        final String textWithoutReturns = RETURN.matcher(textWithoutNewlines).replaceAll("\\\\r");
        return TAB.matcher(textWithoutReturns).replaceAll("\\\\t");
    }

    /**
     * Parse a file and return the parse tree.
     * @param file the file to parse.
     * @param withComments true to include comment nodes to the tree
     * @return the root node of the parse tree.
     * @throws IOException if the file could not be read.
     * @throws CheckstyleException if the file is not a Java source.
     */
    private static DetailAST parseFile(File file, PrintOptions withComments)
            throws IOException, CheckstyleException {
        final FileText text = new FileText(file.getAbsoluteFile(),
            System.getProperty("file.encoding", "UTF-8"));
        return parseFileText(text, withComments);
    }

    /**
     * Parse a text and return the parse tree.
     * @param text the text to parse.
     * @param withComments true to include comment nodes to the tree
     * @return the root node of the parse tree.
     * @throws CheckstyleException if the file is not a Java source.
     */
    private static DetailAST parseFileText(FileText text, PrintOptions withComments)
            throws CheckstyleException {
        final FileContents contents = new FileContents(text);
        final DetailAST result;
        try {
            if (withComments == PrintOptions.WITH_COMMENTS) {
                result = TreeWalker.parseWithComments(contents);
            }
            else {
                result = TreeWalker.parse(contents);
            }
        }
        catch (RecognitionException | TokenStreamException ex) {
            final String exceptionMsg = String.format(Locale.ROOT,
                "%s occurred during the analysis of file %s.",
                ex.getClass().getSimpleName(), text.getFile().getPath());
            throw new CheckstyleException(exceptionMsg, ex);
        }

        return result;
    }
}
