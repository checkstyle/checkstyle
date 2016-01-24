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
import java.util.Locale;
import java.util.regex.Pattern;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

/**
 * Class for printing AST to String.
 * @author Vladislav Lisetskii
 */
public final class AstTreeStringPrinter {

    /** Newline pattern. */
    private static final Pattern NEWLINE = Pattern.compile("\n");
    /** Return pattern. */
    private static final Pattern RETURN = Pattern.compile("\r");
    /** Tab pattern. */
    private static final Pattern TAB = Pattern.compile("\t");

    /** Prevent instances. */
    private AstTreeStringPrinter() {
        // no code
    }

    /**
     * Parse a file and print the parse tree.
     * @param file the file to print.
     * @return the AST of the file in String form.
     * @throws IOException if the file could not be read.
     * @throws CheckstyleException if the file is not a Java source.
     */
    public static String printFileAst(File file) throws IOException, CheckstyleException {
        return printTree(parseFile(file));
    }

    /**
     * Print AST.
     * @param ast the root AST node.
     * @return string AST.
     */
    private static String printTree(DetailAST ast) {
        final StringBuilder messageBuilder = new StringBuilder();
        DetailAST node = ast;
        while (node != null) {
            messageBuilder.append(getIndentation(node))
                    .append(TokenUtils.getTokenName(node.getType())).append(" -> ")
                    .append(excapeAllControlChars(node.getText())).append(" [")
                    .append(node.getLineNo()).append(':').append(node.getColumnNo()).append("]\n")
                    .append(printTree(node.getFirstChild()));
            node = node.getNextSibling();
        }
        return messageBuilder.toString();
    }

    /**
     * Get indentation for an AST node.
     * @param ast the AST to get the indentation for.
     * @return the indentation in String format.
     */
    private static String getIndentation(DetailAST ast) {
        final boolean isLastChild = ast.getNextSibling() == null;
        DetailAST node = ast;
        final StringBuilder indentation = new StringBuilder();
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
     * Parse a file and return the parse tree.
     * @param file the file to parse.
     * @return the root node of the parse tree.
     * @throws IOException if the file could not be read.
     * @throws CheckstyleException if the file is not a Java source.
     */
    private static DetailAST parseFile(File file) throws IOException, CheckstyleException {
        final FileText text = new FileText(file.getAbsoluteFile(),
            System.getProperty("file.encoding", "UTF-8"));
        final FileContents contents = new FileContents(text);
        try {
            return TreeWalker.parse(contents);
        }
        catch (RecognitionException | TokenStreamException ex) {
            final String exceptionMsg = String.format(Locale.ROOT,
                "%s occurred during the analysis of file %s.",
                ex.getClass().getSimpleName(), file.getPath());
            throw new CheckstyleException(exceptionMsg, ex);
        }
    }
}
