///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.AstTreeStringPrinter;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
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

    /** Delimiter to separate xpath results. */
    private static final String DELIMITER = "---------" + System.lineSeparator();

    /** Stop instances being created. */
    private XpathUtil() {
    }

    /**
     * Checks, if specified node can have {@code @text} attribute.
     *
     * @param ast {@code DetailAst} element
     * @return true if element supports {@code @text} attribute, false otherwise
     */
    public static boolean supportsTextAttribute(DetailAST ast) {
        return ElementNode.supportsTextAttribute(ast);
    }

    /**
     * Returns content of the text attribute of the ast element.
     *
     * @param ast {@code DetailAst} element
     * @return text attribute of the ast element
     */
    public static String getTextAttributeValue(DetailAST ast) {
        return ElementNode.getTextAttributeValue(ast);
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
                .map(item -> (DetailAST) ((AbstractNode) item).getUnderlyingNode())
                .map(AstTreeStringPrinter::printBranch)
                .collect(Collectors.joining(DELIMITER));
        }
        catch (XPathException exc) {
            final String errMsg = String.format(Locale.ROOT,
                "Error during evaluation for xpath: %s, file: %s", xpath, file.getCanonicalPath());
            throw new CheckstyleException(errMsg, exc);
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
