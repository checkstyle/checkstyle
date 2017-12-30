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

package com.puppycrawl.tools.checkstyle;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
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
public final class JavadocPropertiesGenerator {

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
     * The end of the sentence is determined by the symbol "period", "exclamation mark" or
     * "question mark", followed by a space or the end of the text.
     */
    private static final Pattern END_OF_SENTENCE_PATTERN = Pattern.compile("(.*?[.?!])(\\s|$)");

    /**
     * Don't create instance of this class, use the {@link #main(String[])} method instead.
     */
    private JavadocPropertiesGenerator() {
    }

    /**
     * TokenTypes.properties generator entry point.
     * @param args the command line arguments
     * @throws CheckstyleException if parser or lexer failed or if there is an IO problem
     * @throws ParseException if the command line can not be passed
     **/
    public static void main(String... args)
            throws CheckstyleException, ParseException {
        final CommandLine commandLine = parseCli(args);
        if (commandLine.getArgList().size() == 1) {
            final File inputFile = new File(commandLine.getArgList().get(0));
            final File outputFile = new File(commandLine.getOptionValue(OPTION_DEST_FILE));
            writePropertiesFile(inputFile, outputFile);
        }
        else {
            printUsage();
        }
    }

    /**
     * Creates the .properties file from a .java file.
     * @param inputFile .java file
     * @param outputFile .properties file
     * @throws CheckstyleException if a javadoc comment can not be parsed
     */
    private static void writePropertiesFile(File inputFile, File outputFile)
            throws CheckstyleException {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(outputFile, StandardCharsets.UTF_8.name());

            final DetailAST top = JavaParser.parseFile(inputFile, JavaParser.Options.WITH_COMMENTS);
            final DetailAST objBlock = getClassBody(top);
            if (objBlock != null) {
                iteratePublicStaticIntFields(objBlock, writer::println);
            }
        }
        catch (IOException ex) {
            throw new CheckstyleException("Failed to write javadoc properties of '" + inputFile
                + "' to '" + outputFile + "'", ex);
        }
        finally {
            CommonUtils.close(writer);
        }
    }

    /**
     * Walks over the type members and push the first javadoc sentence of every
     * {@code public} {@code static} {@code int} field to the consumer.
     * @param objBlock the OBJBLOCK of a class to iterate over its members
     * @param consumer first javadoc sentence consumer
     * @throws CheckstyleException if failed to parse a javadoc comment
     */
    private static void iteratePublicStaticIntFields(DetailAST objBlock, Consumer<String> consumer)
            throws CheckstyleException {
        for (DetailAST member = objBlock.getFirstChild(); member != null;
                member = member.getNextSibling()) {
            if (isPublicStaticFinalIntField(member)) {
                final DetailAST modifiers = member.findFirstToken(TokenTypes.MODIFIERS);
                final String firstJavadocSentence = getFirstJavadocSentence(modifiers);
                if (firstJavadocSentence != null) {
                    consumer.accept(getName(member) + "=" + firstJavadocSentence.trim());
                }
            }
        }
    }

    /**
     * Finds the class body of the first class in the DetailAST.
     * @param top AST to find the class body
     * @return OBJBLOCK token if found; {@code null} otherwise
     */
    private static DetailAST getClassBody(DetailAST top) {
        DetailAST ast = top;
        while (ast != null && ast.getType() != TokenTypes.CLASS_DEF) {
            ast = ast.getNextSibling();
        }
        DetailAST objBlock = null;
        if (ast != null) {
            objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        }
        return objBlock;
    }

    /**
     * Checks that the DetailAST is a {@code public} {@code static} {@code final} {@code int} field.
     * @param ast to process
     * @return {@code true} if matches; {@code false} otherwise
     */
    private static boolean isPublicStaticFinalIntField(DetailAST ast) {
        boolean result = ast.getType() == TokenTypes.VARIABLE_DEF;
        if (result) {
            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
            result = type.getFirstChild().getType() == TokenTypes.LITERAL_INT;
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
     * Extracts the first sentence as HTML formatted text from the comment of an DetailAST.
     * The end of the sentence is determined by the symbol "period", "exclamation mark" or
     * "question mark", followed by a space or the end of the text. Inline tags @code and @literal
     * are converted to HTML code.
     * @param ast to extract the first sentence
     * @return the first sentence of the inner {@code TokenTypes.BLOCK_COMMENT_BEGIN} node
     *      or {@code null} if the first sentence is absent or malformed (does not end with period)
     * @throws CheckstyleException if a javadoc comment can not be parsed or an unsupported inline
     *      tag found
     */
    private static String getFirstJavadocSentence(DetailAST ast) throws CheckstyleException {
        String firstSentence = null;
        for (DetailAST child = ast.getFirstChild(); child != null && firstSentence == null;
                child = child.getNextSibling()) {
            // If there is an annotation, the javadoc comment will be a child of it.
            if (child.getType() == TokenTypes.ANNOTATION) {
                firstSentence = getFirstJavadocSentence(child);
            }
            // Otherwise, the javadoc comment will be right here.
            else if (child.getType() == TokenTypes.BLOCK_COMMENT_BEGIN
                    && JavadocUtils.isJavadocComment(child)) {
                final DetailNode tree = DetailNodeTreeStringPrinter.parseJavadocAsDetailNode(child);
                firstSentence = getFirstJavadocSentence(tree);
            }
        }
        return firstSentence;
    }

    /**
     * Extracts the first sentence as HTML formatted text from a DetailNode.
     * The end of the sentence is determined by the symbol "period", "exclamation mark" or
     * "question mark", followed by a space or the end of the text. Inline tags @code and @literal
     * are converted to HTML code.
     * @param tree to extract the first sentence
     * @return the first sentence of the node or {@code null} if the first sentence is absent or
     *      malformed (does not end with any of the end-of-sentence markers)
     * @throws CheckstyleException if an unsupported inline tag found
     */
    private static String getFirstJavadocSentence(DetailNode tree) throws CheckstyleException {
        String firstSentence = null;
        final StringBuilder builder = new StringBuilder(128);
        for (DetailNode node : tree.getChildren()) {
            if (node.getType() == JavadocTokenTypes.TEXT) {
                final Matcher matcher = END_OF_SENTENCE_PATTERN.matcher(node.getText());
                if (matcher.find()) {
                    // Commit the sentence if an end-of-sentence marker is found.
                    firstSentence = builder.append(matcher.group(1)).toString();
                    break;
                }
                // Otherwise append the whole line and look for an end-of-sentence marker
                // on the next line.
                builder.append(node.getText());
            }
            else if (node.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                formatInlineCodeTag(builder, node);
            }
            else if (node.getType() == JavadocTokenTypes.HTML_ELEMENT) {
                formatHtmlElement(builder, node);
            }
        }
        return firstSentence;
    }

    /**
     * Converts inline code tag into HTML form.
     * @param builder to append
     * @param inlineTag to format
     * @throws CheckstyleException if the inline javadoc tag is not a literal nor a code tag
     */
    private static void formatInlineCodeTag(StringBuilder builder, DetailNode inlineTag)
            throws CheckstyleException {
        boolean wrapWithCodeTag = false;
        for (DetailNode node : inlineTag.getChildren()) {
            switch (node.getType()) {
                case JavadocTokenTypes.CODE_LITERAL:
                    wrapWithCodeTag = true;
                    break;
                // The text to append.
                case JavadocTokenTypes.TEXT:
                    if (wrapWithCodeTag) {
                        builder.append("<code>").append(node.getText()).append("</code>");
                    }
                    else {
                        builder.append(node.getText());
                    }
                    break;
                // Empty content tags.
                case JavadocTokenTypes.LITERAL_LITERAL:
                case JavadocTokenTypes.JAVADOC_INLINE_TAG_START:
                case JavadocTokenTypes.JAVADOC_INLINE_TAG_END:
                case JavadocTokenTypes.WS:
                    break;
                default:
                    throw new CheckstyleException("Unsupported inline tag "
                        + JavadocUtils.getTokenName(node.getType()));
            }
        }
    }

    /**
     * Concatenates the HTML text from AST of a JavadocTokenTypes.HTML_ELEMENT.
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
            JavadocPropertiesGenerator.class.getName()), buildOptions());
    }

    /**
     * Parses command line based on passed arguments.
     * @param args command line arguments
     * @return parsed information about passed arguments
     * @throws ParseException when passed arguments are not valid
     */
    private static CommandLine parseCli(String... args)
            throws ParseException {
        final CommandLineParser clp = new DefaultParser();
        return clp.parse(buildOptions(), args);
    }

    /**
     * Builds and returns the list of supported parameters.
     * @return available options
     */
    private static Options buildOptions() {
        final Options options = new Options();
        options.addRequiredOption(null, OPTION_DEST_FILE, true, "The output file.");
        return options;
    }

}
