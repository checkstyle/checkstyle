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

package com.puppycrawl.tools.checkstyle.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.AstTreeStringPrinter;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.xpath.AbstractNode;
import com.puppycrawl.tools.checkstyle.xpath.ElementNode;
import com.puppycrawl.tools.checkstyle.xpath.RootNode;

import net.sf.saxon.Configuration;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.sxpath.XPathDynamicContext;
import net.sf.saxon.sxpath.XPathEvaluator;
import net.sf.saxon.sxpath.XPathExpression;
import net.sf.saxon.trans.XPathException;

/**
 * Contains utility methods for xpath.
 *
 */
public final class XpathUtil {

    /**
     * Token types which support text attribute.
     * These token types were selected based on analysis that all others do not match required
     * criteria - text attribute of the token must be useful and help to retrieve more precise
     * results.
     * There are three types of AST tokens:
     * 1. Tokens for which the texts are equal to the name of the token. Or in other words,
     * nodes for which the following expression is always true:
     * <pre>
     *     detailAst.getText().equals(TokenUtil.getTokenName(detailAst.getType()))
     * </pre>
     * For example:
     * <pre>
     *     //MODIFIERS[@text='MODIFIERS']
     *     //OBJBLOCK[@text='OBJBLOCK']
     * </pre>
     * These tokens do not match required criteria because their texts do not carry any additional
     * information, they do not affect the xpath requests and do not help to get more accurate
     * results. The texts of these nodes are useless. No matter what code you analyze, these
     * texts are always the same.
     * In addition, they make xpath queries more complex, less readable and verbose.
     * 2. Tokens for which the texts differ from token names, but texts are always constant.
     * For example:
     * <pre>
     *     //LITERAL_VOID[@text='void']
     *     //RCURLY[@text='}']
     * </pre>
     * These tokens are not used for the same reasons as were described in the previous part.
     * 3. Tokens for which texts are not constant. The texts of these nodes are closely related
     * to a concrete class, method, variable and so on.
     * For example:
     * <pre>
     *     String greeting = "HelloWorld";
     *     //STRING_LITERAL[@text='HelloWorld']
     * </pre>
     * <pre>
     *     int year = 2017;
     *     //NUM_INT[@text=2017]
     * </pre>
     * <pre>
     *     int age = 23;
     *     //NUM_INT[@text=23]
     * </pre>
     * As you can see same {@code NUM_INT} token type can have different texts, depending on
     * context.
     * <pre>
     *     public class MyClass {}
     *     //IDENT[@text='MyClass']
     * </pre>
     * Only these tokens support text attribute because they make our xpath queries more accurate.
     * These token types are listed below.
     */
    private static final BitSet TOKEN_TYPES_WITH_TEXT_ATTRIBUTE = TokenUtil.asBitSet(
        TokenTypes.IDENT, TokenTypes.STRING_LITERAL, TokenTypes.CHAR_LITERAL,
        TokenTypes.NUM_LONG, TokenTypes.NUM_INT, TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT,
        TokenTypes.TEXT_BLOCK_CONTENT, TokenTypes.COMMENT_CONTENT
    );

    /**
     * This regexp is used to convert new line to newline tag.
     */
    private static final Pattern NEWLINE_TO_TAG = Pattern.compile("\n");

    /**
     * This regexp is used to convert carriage return to carriage-return tag.
     */
    private static final Pattern CARRIAGE_RETURN_TO_TAG = Pattern.compile("\r");

    /** Delimiter to separate xpath results. */
    private static final String DELIMITER = "---------" + System.lineSeparator();

    /** Stop instances being created. **/
    private XpathUtil() {
    }

    /**
     * Iterates siblings of the given node and creates new Xpath-nodes.
     *
     * @param root the root node
     * @param parent the parent node
     * @param firstChild the first DetailAST
     * @return children list
     */
    public static List<AbstractNode> createChildren(AbstractNode root, AbstractNode parent,
                                                    DetailAST firstChild) {
        DetailAST currentChild = firstChild;
        final int depth = parent.getDepth() + 1;
        final List<AbstractNode> result = new ArrayList<>();
        while (currentChild != null) {
            final int index = result.size();
            final ElementNode child = new ElementNode(root, parent, currentChild, depth, index);
            result.add(child);
            currentChild = currentChild.getNextSibling();
        }
        return result;
    }

    /**
     * Checks, if specified node can have {@code @text} attribute.
     *
     * @param ast {@code DetailAst} element
     * @return true if element supports {@code @text} attribute, false otherwise
     */
    public static boolean supportsTextAttribute(DetailAST ast) {
        return TOKEN_TYPES_WITH_TEXT_ATTRIBUTE.get(ast.getType());
    }

    /**
     * Returns content of the text attribute of the ast element.
     *
     * @param ast {@code DetailAst} element
     * @return text attribute of the ast element
     */
    public static String getTextAttributeValue(DetailAST ast) {
        String text = ast.getText();
        if (ast.getType() == TokenTypes.STRING_LITERAL) {
            text = text.substring(1, text.length() - 1);
        }
        text = CARRIAGE_RETURN_TO_TAG.matcher(text).replaceAll("\\\\r");
        return NEWLINE_TO_TAG.matcher(text).replaceAll("\\\\n");
    }

    /**
     * Returns xpath query results on file as string.
     *
     * @param xpath query to evaluate
     * @param file file to run on
     * @return all results as string separated by delimiter
     * @throws CheckstyleException if some parsing error happens
     * @throws IOException if an error occurs
     */
    public static String printXpathBranch(String xpath, File file) throws CheckstyleException,
        IOException {
        try {
            final RootNode rootNode = new RootNode(JavaParser.parseFile(file,
                JavaParser.Options.WITH_COMMENTS));
            final List<NodeInfo> matchingItems = getXpathItems(xpath, rootNode);
            return matchingItems.stream()
                .map(item -> ((ElementNode) item).getUnderlyingNode())
                .map(AstTreeStringPrinter::printBranch)
                .collect(Collectors.joining(DELIMITER));
        }
        catch (XPathException ex) {
            final String errMsg = String.format(Locale.ROOT,
                "Error during evaluation for xpath: %s, file: %s", xpath, file.getCanonicalPath());
            throw new CheckstyleException(errMsg, ex);
        }
    }

    /**
     * Returns list of nodes matching xpath expression given node context.
     *
     * @param xpath Xpath expression
     * @param rootNode {@code NodeInfo} node context
     * @return list of nodes matching xpath expression given node context
     * @throws XPathException if Xpath cannot be parsed
     */
    public static List<NodeInfo> getXpathItems(String xpath, AbstractNode rootNode)
        throws XPathException {
        final XPathEvaluator xpathEvaluator = new XPathEvaluator(Configuration.newConfiguration());
        final XPathExpression xpathExpression = xpathEvaluator.createExpression(xpath);
        final XPathDynamicContext xpathDynamicContext = xpathExpression
            .createDynamicContext(rootNode);
        final List<Item> items = xpathExpression.evaluate(xpathDynamicContext);
        return UnmodifiableCollectionUtil.unmodifiableList(items, NodeInfo.class);
    }
}
