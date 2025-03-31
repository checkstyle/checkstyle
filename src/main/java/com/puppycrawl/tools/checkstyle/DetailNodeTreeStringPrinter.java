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

package com.puppycrawl.tools.checkstyle;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.ParseErrorMessage;
import com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.ParseStatus;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.ParserUtil;

/**
 * Parses file as javadoc DetailNode tree and prints to system output stream.
 */
public final class DetailNodeTreeStringPrinter {

    /** OS specific line separator. */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /** Prevent instances. */
    private DetailNodeTreeStringPrinter() {
        // no code
    }

    /**
     * Parse a file and print the parse tree.
     *
     * @param file the file to print.
     * @return parse tree as a string
     * @throws IOException if the file could not be read.
     */
    public static String printFileAst(File file) throws IOException {
        return printTree(parseFile(file), "", "");
    }

    /**
     * Parse block comment DetailAST as Javadoc DetailNode tree.
     *
     * @param blockComment DetailAST
     * @return DetailNode tree
     * @throws IllegalArgumentException if there is an error parsing the Javadoc.
     */
    public static DetailNode parseJavadocAsDetailNode(DetailAST blockComment) {
        final JavadocDetailNodeParser parser = new JavadocDetailNodeParser();
        final ParseStatus status = parser.parseJavadocAsDetailNode(blockComment);
        if (status.getParseErrorMessage() != null) {
            throw new IllegalArgumentException(getParseErrorMessage(status.getParseErrorMessage()));
        }
        return status.getTree();
    }

    /**
     * Parse javadoc comment to DetailNode tree.
     *
     * @param javadocComment javadoc comment content
     * @return tree
     */
    private static DetailNode parseJavadocAsDetailNode(String javadocComment) {
        final DetailAST blockComment = ParserUtil.createBlockCommentNode(javadocComment);
        return parseJavadocAsDetailNode(blockComment);
    }

    /**
     * Builds violation base on ParseErrorMessage's violation key, its arguments, etc.
     *
     * @param parseErrorMessage ParseErrorMessage
     * @return error violation
     */
    private static String getParseErrorMessage(ParseErrorMessage parseErrorMessage) {
        final LocalizedMessage message = new LocalizedMessage(
                "com.puppycrawl.tools.checkstyle.checks.javadoc.messages",
                DetailNodeTreeStringPrinter.class,
                parseErrorMessage.getMessageKey(),
                parseErrorMessage.getMessageArguments());
        return "[ERROR:" + parseErrorMessage.getLineNumber() + "] " + message.getMessage();
    }

    /**
     * Print AST.
     *
     * @param ast the root AST node.
     * @param rootPrefix prefix for the root node
     * @param prefix prefix for other nodes
     * @return string AST.
     */
    public static String printTree(DetailNode ast, String rootPrefix, String prefix) {
        final StringBuilder messageBuilder = new StringBuilder(1024);
        DetailNode node = ast;
        while (node != null) {
            if (node.getType() == JavadocTokenTypes.JAVADOC) {
                messageBuilder.append(rootPrefix);
            }
            else {
                messageBuilder.append(prefix);
            }
            messageBuilder.append(getIndentation(node))
                    .append(JavadocUtil.getTokenName(node.getType())).append(" -> ")
                    .append(JavadocUtil.escapeAllControlChars(node.getText())).append(" [")
                    .append(node.getLineNumber()).append(':').append(node.getColumnNumber())
                    .append(']').append(LINE_SEPARATOR)
                    .append(printTree(JavadocUtil.getFirstChild(node), rootPrefix, prefix));
            node = JavadocUtil.getNextSibling(node);
        }
        return messageBuilder.toString();
    }

    /**
     * Get indentation for a node.
     *
     * @param node the DetailNode to get the indentation for.
     * @return the indentation in String format.
     */
    private static String getIndentation(DetailNode node) {
        final boolean isLastChild = JavadocUtil.getNextSibling(node) == null;
        DetailNode currentNode = node;
        final StringBuilder indentation = new StringBuilder(1024);
        while (currentNode.getParent() != null) {
            currentNode = currentNode.getParent();
            if (currentNode.getParent() == null) {
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
                if (JavadocUtil.getNextSibling(currentNode) == null) {
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
     * Determines the encoding to use based on the provided encoding and default encoding.
     * If the provided encoding is null or blank, the method returns the default encoding.
     * If the default encoding is null or blank, an IllegalStateException is thrown.
     *
     *
     * @param encode        the encoding to check, can be null or blank
     * @param defaultEncode the default encoding to use if {@code encode} is null or blank,
     *                      must not be null or blank
     * @return the determined encoding, either {@code encode} if valid or {@code defaultEncode}
     * @throws IllegalStateException if {@code defaultEncode} is null or blank
     */
    private static String getEncoding(String encode, String defaultEncode) {
        if (defaultEncode == null || defaultEncode.isBlank()) {
            throw new IllegalStateException("DefaultEncode should not be null or blank");
        }
        final String result;
        if (encode != null && !encode.isBlank()) {
            result = encode;
        }
        else {
            result = defaultEncode;
        }
        return result;
    }

    /**
     * Parse a file and return the parse tree.
     *
     * @param file the file to parse.
     * @return the root node of the parse tree.
     * @throws IOException if the file could not be read.
     */
    private static DetailNode parseFile(File file) throws IOException {
        final FileText text = new FileText(file,
                getEncoding(System.getProperty("file.encoding"),
                        StandardCharsets.UTF_8.name()));
        return parseJavadocAsDetailNode(text.getFullText().toString());
    }

}
