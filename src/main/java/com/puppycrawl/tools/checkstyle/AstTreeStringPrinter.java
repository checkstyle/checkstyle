////
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
///

package com.puppycrawl.tools.checkstyle;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Class for printing AST to String.
 */
public final class AstTreeStringPrinter {

    /** Newline pattern. */
    private static final Pattern NEWLINE = Pattern.compile("\n");
    /** Return pattern. */
    private static final Pattern RETURN = Pattern.compile("\r");
    /** Tab pattern. */
    private static final Pattern TAB = Pattern.compile("\t");

    /** OS specific line separator. */
    private static final String LINE_SEPARATOR = System.lineSeparator();

    /** Prevent instances. */
    private AstTreeStringPrinter() {
        // no code
    }

    /**
     * Parse a file and print the parse tree.
     *
     * @param file the file to print.
     * @param options {@link JavaParser.Options} to control the inclusion of comment nodes.
     * @return the AST of the file in String form.
     * @throws IOException if the file could not be read.
     * @throws CheckstyleException if the file is not a Java source.
     */
    public static String printFileAst(File file, JavaParser.Options options)
        throws IOException, CheckstyleException {
        return printTree(JavaParser.parseFile(file, options));
    }

    /**
     * Prints full AST (java + comments + javadoc) of the java file.
     *
     * @param file java file
     * @return Full tree
     * @throws IOException Failed to open a file
     * @throws CheckstyleException error while parsing the file
     */
    public static String printJavaAndJavadocTree(File file)
        throws IOException, CheckstyleException {
        final DetailAST tree = JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS);
        return printJavaAndJavadocTree(tree);
    }

    /**
     * Prints full tree (java + comments + javadoc) of the DetailAST.
     *
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
                && JavadocUtil.isJavadocComment(node.getParent())) {
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
     *
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
     *
     * @param text the text to parse.
     * @param options {@link JavaParser.Options} to control the inclusion of comment nodes.
     * @return the AST of the file in String form.
     * @throws CheckstyleException if the file is not a Java source.
     */
    public static String printAst(FileText text, JavaParser.Options options)
        throws CheckstyleException {
        final DetailAST ast = JavaParser.parseFileText(text, options);
        return printTree(ast);
    }

    /**
     * Print branch info from root down to given {@code node}.
     *
     * @param node last item of the branch
     * @return branch as string
     */
    public static String printBranch(DetailAST node) {
        final String result;
        if (node == null) {
            result = "";
        }
        else {
            result = printBranch(node.getParent())
                + getIndentation(node)
                + getNodeInfo(node)
                + LINE_SEPARATOR;
        }
        return result;
    }

    /**
     * Print AST.
     *
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
     *
     * @param node DetailAST
     * @return node info
     */
    private static String getNodeInfo(DetailAST node) {
        return TokenUtil.getTokenName(node.getType())
            + " -> " + escapeAllControlChars(node.getText())
            + " [" + node.getLineNo() + ':' + node.getColumnNo() + ']';
    }

    /**
     * Get indentation for an AST node.
     *
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
     *
     * @param text the String to process.
     * @return the processed String with all control chars escaped.
     */
    private static String escapeAllControlChars(String text) {
        final String textWithoutNewlines = NEWLINE.matcher(text).replaceAll("\\\\n");
        final String textWithoutReturns = RETURN.matcher(textWithoutNewlines).replaceAll("\\\\r");
        return TAB.matcher(textWithoutReturns).replaceAll("\\\\t");
    }

}
