////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.doclets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser;
import com.puppycrawl.tools.checkstyle.Parser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtils;

/**
 * This class is used internally in the build process to write a property file
 * with short descriptions (the first sentences) of TokenTypes constants.
 * Request: 724871
 * For IDE plugins (like the eclipse plugin) it would be useful to have
 * a programmatic access to the first sentence of the TokenType constants,
 * so they can use them in their configuration gui.
 * @author Pavel Bludov
 */
public final class TokenTypesDoclet {

    /**
     * The command line option to specify the output file.
     */
    private static final String OPTION_DEST_FILE = "destfile";

    /**
     * The width of the CLI help option.
     */
    private static final int HELP_WIDTH = 100;

    /**
     * This regexp is used to extract the first sentence from the text.
     */
    private static final Pattern FIRST_SENTENCE_PATTERN = Pattern.compile("(.*?\\.)(\\s|$)");

    /**
     * Parses content of Javadoc comment as DetailNode tree.
     */
    private static final JavadocDetailNodeParser JAVADOC_PARSER = new JavadocDetailNodeParser();

    /**
     * Don't create instance of this class, use the {@link #main(String[])} method instead.
     */
    private TokenTypesDoclet() {
    }

    /**
     * TokenTypes.properties generator entry point.
     * @param args the command line arguments.
     * @throws CheckstyleException if parser or lexer failed or if there is an IO problem
     * @throws ParseException if the command line can not be passed
     **/
    public static void main(String... args)
            throws CheckstyleException, ParseException {
        final CommandLine commandLine = parseCli(args);
        if (commandLine.getArgList().size() == 1) {
            try {
                writePropertiesFile(commandLine.getArgList().get(0),
                    commandLine.getOptionValue(OPTION_DEST_FILE));
            }
            catch (IOException ex) {
                throw new CheckstyleException("Failed to write properties file", ex);
            }
        }
        else {
            printUsage();
        }
    }

    /**
     * Creates the .properties file from a .java file.
     * @param inputFile .java file.
     * @param outputFile .properties file.
     * @throws CheckstyleException if a javadoc comment can not be parsed
     * @throws IOException if there is a problem with files access
     */
    private static void writePropertiesFile(String inputFile, String outputFile)
            throws CheckstyleException, IOException {
        final FileOutputStream fos = new FileOutputStream(outputFile);
        final Writer osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        final PrintWriter writer = new PrintWriter(osw, false);

        try {
            for (DetailAST top = Parser.parseFileWithComments(new File(inputFile));
                 top != null; top = top.getNextSibling()) {
                if (top.getType() != TokenTypes.CLASS_DEF) {
                    continue;
                }
                final DetailAST objBlock = top.getLastChild();
                for (DetailAST ast = objBlock.getFirstChild(); ast != null;
                        ast = ast.getNextSibling()) {
                    if (isPublicStaticFinalIntField(ast)) {
                        final String firstJavadocSentence = getFirstJavadocSentence(ast);
                        if (firstJavadocSentence != null) {
                            writer.println(getName(ast) + "=" + firstJavadocSentence.trim());
                        }
                    }
                }
            }
        }
        finally {
            writer.close();
        }
    }

    /**
     * Checks that the DetailAST is a {@code public} {@code static} {@code final} {@code int} field.
     * @param ast to process
     * @return {@code true} if matches, {@code false} otherwise.
     */
    private static boolean isPublicStaticFinalIntField(DetailAST ast) {
        boolean result = ast.getType() == TokenTypes.VARIABLE_DEF;
        if (result) {
            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
            result = type.findFirstToken(TokenTypes.LITERAL_INT) != null;
            if (result) {
                final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
                result = modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) != null
                    && modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null
                    && modifiers.findFirstToken(TokenTypes.FINAL) != null;
            }
        }
        return result;
    }

    /**
     * Extracts the name of an ast.
     * @param ast to extract the name
     * @return the text content of the inner {@code TokenTypes.IDENT} node
     */
    private static String getName(DetailAST ast) {
        return ast.findFirstToken(TokenTypes.IDENT).getText();
    }

    /**
     * Extracts the first sentence of an ast javadoc comment.
     * @param ast to extract the first sentence
     * @return the first sentence of the inner {@code TokenTypes.BLOCK_COMMENT_BEGIN} node
     *     or {@code null} if the first sentence is absent or malformed (does not end with period)
     * @throws CheckstyleException if a javadoc comment can not be parsed
     */
    private static String getFirstJavadocSentence(DetailAST ast) throws CheckstyleException {
        String firstSentence = null;
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        for (DetailAST comment = modifiers.getFirstChild(); comment != null;
                comment = comment.getNextSibling()) {
            if (comment.getType() == TokenTypes.BLOCK_COMMENT_BEGIN
                    && JavadocUtils.isJavadocComment(comment)) {
                final DetailNode root = JAVADOC_PARSER.parseJavadocAsDetailNode(comment);
                final StringBuilder builder = new StringBuilder(128);
                for (DetailNode node : root.getChildren()) {
                    if (node.getType() == JavadocTokenTypes.TEXT) {
                        final Matcher matcher = FIRST_SENTENCE_PATTERN.matcher(node.getText());
                        if (matcher.find()) {
                            firstSentence = builder.append(matcher.group(1)).toString();
                            break;
                        }
                        else {
                            builder.append(node.getText());
                        }
                    }
                    else if (node.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                        formatInlineCodeTag(builder, node);
                    }
                    else if (node.getType() == JavadocTokenTypes.HTML_ELEMENT) {
                        formatHtmlElement(builder, node);
                    }
                }
            }
        }
        return firstSentence;
    }

    /**
     * Converts inline code tag into HTML form.
     * @param builder to append
     * @param inlineTag to format
     * @throws CheckstyleException if the inline javadoc tag is not a code tag
     */
    private static void formatInlineCodeTag(StringBuilder builder, DetailNode inlineTag)
            throws CheckstyleException {
        if (JavadocUtils.findFirstToken(inlineTag, JavadocTokenTypes.CODE_LITERAL) == null) {
            throw new CheckstyleException("Expected inline @code tag");
        }
        builder.append("<code>");
        for (DetailNode node : inlineTag.getChildren()) {
            if (node.getType() == JavadocTokenTypes.TEXT) {
                builder.append(node.getText());
            }
        }
        builder.append("</code>");
    }

    /**
     * Rebuilds HTML text from the AST of a HTML_ELEMENT.
     * @param builder to append
     * @param node to format
     */
    private static void formatHtmlElement(StringBuilder builder, DetailNode node) {
        switch (node.getType()) {
            case JavadocTokenTypes.START:
            case JavadocTokenTypes.HTML_TAG_NAME:
            case JavadocTokenTypes.END:
            case JavadocTokenTypes.TEXT:
            case JavadocTokenTypes.SLASH:
                builder.append(node.getText());
                break;
            default:
                for (DetailNode child : node.getChildren()) {
                    formatHtmlElement(builder, child);
                }
                break;
        }
    }

    /**
     *  Prints the usage information.
     */
    private static void printUsage() {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(HELP_WIDTH);
        formatter.printHelp(String.format("java %s [options] <input file>.",
            TokenTypesDoclet.class.getName()), buildOptions());
    }

    /**
     * Parses doclet command line based on passed arguments.
     * @param args
     *        command line arguments
     * @return parsed information about passed arguments
     * @throws ParseException
     *         when passed arguments are not valid
     */
    private static CommandLine parseCli(String... args)
            throws ParseException {
        final CommandLineParser clp = new DefaultParser();
        return clp.parse(buildOptions(), args);
    }

    /**
     * Builds and returns the list of parameters supported by doclet cli.
     * @return available options
     */
    private static Options buildOptions() {
        final Options options = new Options();
        options.addRequiredOption(null, OPTION_DEST_FILE, true, "The output file.");
        return options;
    }

}
