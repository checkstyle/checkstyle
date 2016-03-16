////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtils;

/**
 * Parses file as javadoc DetailNode tree and prints to system output stream.
 * @author bizmailov
 */
public final class DetailNodeTreeStringPrinter {

    /** OS specific line separator. */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /** Newline pattern. */
    private static final Pattern NEWLINE = Pattern.compile("\n");
    /** Return pattern. */
    private static final Pattern RETURN = Pattern.compile("\r");
    /** Tab pattern. */
    private static final Pattern TAB = Pattern.compile("\t");

    /** Prevent instances. */
    private DetailNodeTreeStringPrinter() {
        // no code
    }

    /**
     * Parse a file and print the parse tree.
     * @param file the file to print.
     * @return parse tree as a string
     * @throws IOException if the file could not be read.
     */
    public static String printFileAst(File file) throws IOException {
        return printTree(parseFile(file), "", "");
    }

    /**
     * Parse javadoc comment to DetailNode tree.
     * @param javadocComment javadoc comment content
     * @return tree
     */
    private static DetailNode parseJavadocAsDetailNode(String javadocComment) {
        final JavadocDetailNodeParser parser = new JavadocDetailNodeParser();
        return parser.parseJavadocAsDetailNode(createFakeBlockComment(javadocComment))
                .getTree();
    }

    /**
     * Print AST.
     * @param ast the root AST node.
     * @param rootPrefix prefix for the root node
     * @param prefix prefix for other nodes
     * @return string AST.
     */
    public static String printTree(DetailNode ast, String rootPrefix, String prefix) {
        final StringBuilder messageBuilder = new StringBuilder();
        DetailNode node = ast;
        while (node != null) {
            if (node.getType() == JavadocTokenTypes.JAVADOC) {
                messageBuilder.append(rootPrefix);
            }
            else {
                messageBuilder.append(prefix);
            }
            messageBuilder.append(getIndentation(node))
                    .append(JavadocUtils.getTokenName(node.getType())).append(" -> ")
                    .append(excapeAllControlChars(node.getText())).append(" [")
                    .append(node.getLineNumber()).append(':').append(node.getColumnNumber())
                    .append(']').append(LINE_SEPARATOR)
                    .append(printTree(JavadocUtils.getFirstChild(node), rootPrefix, prefix));
            node = JavadocUtils.getNextSibling(node);
        }
        return messageBuilder.toString();
    }

    /**
     * Replace all control chars with excaped symbols.
     * @param text the String to process.
     * @return the processed String with all control chars excaped.
     */
    private static String excapeAllControlChars(String text) {
        final String textWithoutNewlines = NEWLINE.matcher(text).replaceAll("\\\\n");
        final String textWithoutReturns = RETURN.matcher(textWithoutNewlines).replaceAll("\\\\r");
        return TAB.matcher(textWithoutReturns).replaceAll("\\\\t");
    }

    /**
     * Get indentation for a node.
     * @param node the DetailNode to get the indentation for.
     * @return the indentation in String format.
     */
    private static String getIndentation(DetailNode node) {
        final boolean isLastChild = JavadocUtils.getNextSibling(node) == null;
        DetailNode currentNode = node;
        final StringBuilder indentation = new StringBuilder();
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
                if (JavadocUtils.getNextSibling(currentNode) == null) {
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
     * Parse a file and return the parse tree.
     * @param file the file to parse.
     * @return the root node of the parse tree.
     * @throws IOException if the file could not be read.
     */
    private static DetailNode parseFile(File file) throws IOException {
        // Details: https://github.com/checkstyle/checkstyle/issues/3034
        // noinspection MismatchedQueryAndUpdateOfCollection
        final FileText text = new FileText(file.getAbsoluteFile(),
            System.getProperty("file.encoding", "UTF-8"));
        return parseJavadocAsDetailNode(text.getFullText().toString());
    }

    /**
     * Creates DetailAST block comment to pass it to the Javadoc parser.
     * @param content comment content.
     * @return DetailAST block comment
     */
    private static DetailAST createFakeBlockComment(String content) {
        final DetailAST blockCommentBegin = new DetailAST();
        blockCommentBegin.setType(TokenTypes.BLOCK_COMMENT_BEGIN);
        blockCommentBegin.setText("/*");
        blockCommentBegin.setLineNo(0);
        blockCommentBegin.setColumnNo(0);

        final DetailAST commentContent = new DetailAST();
        commentContent.setType(TokenTypes.COMMENT_CONTENT);
        commentContent.setText("*" + content);
        commentContent.setLineNo(0);
        commentContent.setColumnNo(2);

        final DetailAST blockCommentEnd = new DetailAST();
        blockCommentEnd.setType(TokenTypes.BLOCK_COMMENT_END);
        blockCommentEnd.setText("*/");

        blockCommentBegin.setFirstChild(commentContent);
        commentContent.setNextSibling(blockCommentEnd);
        return blockCommentBegin;
    }

}
