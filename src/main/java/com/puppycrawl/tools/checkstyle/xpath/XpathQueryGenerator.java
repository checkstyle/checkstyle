////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.xpath;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;
import com.puppycrawl.tools.checkstyle.utils.XpathUtil;

/**
 * Generates xpath queries. Xpath queries are generated based on received
 * {@code DetailAst} element, line number, column number and token type.
 * Token type parameter is optional.
 *
 * <p>
 *     Example class
 * </p>
 * <pre>
 * public class Main {
 *
 *     public String sayHello(String name) {
 *         return "Hello, " + name;
 *     }
 * }
 * </pre>
 *
 * <p>
 *     Following expression returns list of queries. Each query is the string representing full
 *     path to the node inside Xpath tree, whose line number is 3 and column number is 4.
 * </p>
 * <pre>
 *     new XpathQueryGenerator(rootAst, 3, 4).generate();
 * </pre>
 *
 * <p>
 *     Result list
 * </p>
 * <ul>
 *     <li>
 *         /CLASS_DEF[./IDENT[@text='Main']]/OBJBLOCK/METHOD_DEF[./IDENT[@text='sayHello']]
 *     </li>
 *     <li>
 *         /CLASS_DEF[./IDENT[@text='Main']]/OBJBLOCK/METHOD_DEF[./IDENT[@text='sayHello']]
 *         /MODIFIERS
 *     </li>
 *     <li>
 *         /CLASS_DEF[./IDENT[@text='Main']]/OBJBLOCK/METHOD_DEF[./IDENT[@text='sayHello']]
 *         /MODIFIERS/LITERAL_PUBLIC
 *     </li>
 * </ul>
 *
 */
public class XpathQueryGenerator {

    /** The root ast. */
    private final DetailAST rootAst;
    /** The line number of the element for which the query should be generated. */
    private final int lineNumber;
    /** The column number of the element for which the query should be generated. */
    private final int columnNumber;
    /** The token type of the element for which the query should be generated. Optional. */
    private final int tokenType;
    /** The {@code FileText} object, representing content of the file. */
    private final FileText fileText;
    /** The distance between tab stop position. */
    private final int tabWidth;

    /**
     * Creates a new {@code XpathQueryGenerator} instance.
     *
     * @param event {@code TreeWalkerAuditEvent} object
     * @param tabWidth distance between tab stop position
     */
    public XpathQueryGenerator(TreeWalkerAuditEvent event, int tabWidth) {
        this(event.getRootAst(), event.getLine(), event.getColumn(), event.getTokenType(),
                event.getFileContents().getText(), tabWidth);
    }

    /**
     * Creates a new {@code XpathQueryGenerator} instance.
     *
     * @param rootAst root ast
     * @param lineNumber line number of the element for which the query should be generated
     * @param columnNumber column number of the element for which the query should be generated
     * @param fileText the {@code FileText} object
     * @param tabWidth distance between tab stop position
     */
    public XpathQueryGenerator(DetailAST rootAst, int lineNumber, int columnNumber,
                               FileText fileText, int tabWidth) {
        this(rootAst, lineNumber, columnNumber, 0, fileText, tabWidth);
    }

    /**
     * Creates a new {@code XpathQueryGenerator} instance.
     *
     * @param rootAst root ast
     * @param lineNumber line number of the element for which the query should be generated
     * @param columnNumber column number of the element for which the query should be generated
     * @param tokenType token type of the element for which the query should be generated
     * @param fileText the {@code FileText} object
     * @param tabWidth distance between tab stop position
     */
    public XpathQueryGenerator(DetailAST rootAst, int lineNumber, int columnNumber, int tokenType,
                               FileText fileText, int tabWidth) {
        this.rootAst = rootAst;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.tokenType = tokenType;
        this.fileText = fileText;
        this.tabWidth = tabWidth;
    }

    /**
     * Returns list of xpath queries of nodes, matching line number, column number and token type.
     * This approach uses DetailAST traversal. DetailAST means detail abstract syntax tree.
     *
     * @return list of xpath queries of nodes, matching line number, column number and token type
     */
    public List<String> generate() {
        return getMatchingAstElements()
            .stream()
            .map(XpathQueryGenerator::generateXpathQuery)
            .collect(Collectors.toList());
    }

    /**
     * Returns child {@code DetailAst} element of the given root, which has text attribute.
     *
     * @param root {@code DetailAST} root ast
     * @return child {@code DetailAst} element of the given root
     */
    private static DetailAST findChildWithTextAttribute(DetailAST root) {
        return TokenUtil.findFirstTokenByPredicate(root,
                XpathUtil::supportsTextAttribute).orElse(null);
    }

    /**
     * Returns child {@code DetailAst} element of the given root, which has text attribute.
     * Performs search recursively inside node's subtree.
     *
     * @param root {@code DetailAST} root ast
     * @return child {@code DetailAst} element of the given root
     */
    private static DetailAST findChildWithTextAttributeRecursively(DetailAST root) {
        DetailAST res = findChildWithTextAttribute(root);
        for (DetailAST ast = root.getFirstChild(); ast != null && res == null;
             ast = ast.getNextSibling()) {
            res = findChildWithTextAttributeRecursively(ast);
        }
        return res;
    }

    /**
     * Returns full xpath query for given ast element.
     *
     * @param ast {@code DetailAST} ast element
     * @return full xpath query for given ast element
     */
    public static String generateXpathQuery(DetailAST ast) {
        final StringBuilder xpathQueryBuilder = new StringBuilder(getXpathQuery(null, ast));
        if (!isXpathQueryForNodeIsAccurateEnough(ast)) {
            xpathQueryBuilder.append('[');
            final DetailAST child = findChildWithTextAttributeRecursively(ast);
            if (child == null) {
                xpathQueryBuilder.append(findPositionAmongSiblings(ast));
            }
            else {
                xpathQueryBuilder.append('.').append(getXpathQuery(ast, child));
            }
            xpathQueryBuilder.append(']');
        }
        return xpathQueryBuilder.toString();
    }

    /**
     * Finds position of the ast element among siblings.
     *
     * @param ast {@code DetailAST} ast element
     * @return position of the ast element
     */
    private static int findPositionAmongSiblings(DetailAST ast) {
        DetailAST cur = ast;
        int pos = 0;
        while (cur != null) {
            if (cur.getType() == ast.getType()) {
                pos++;
            }
            cur = cur.getPreviousSibling();
        }
        return pos;
    }

    /**
     * Checks if ast element has all requirements to have unique xpath query.
     *
     * @param ast {@code DetailAST} ast element
     * @return true if ast element will have unique xpath query, false otherwise
     */
    private static boolean isXpathQueryForNodeIsAccurateEnough(DetailAST ast) {
        return !hasAtLeastOneSiblingWithSameTokenType(ast)
                || XpathUtil.supportsTextAttribute(ast)
                || findChildWithTextAttribute(ast) != null;
    }

    /**
     * Returns list of nodes matching defined line number, column number and token type.
     *
     * @return list of nodes matching defined line number, column number and token type
     */
    private List<DetailAST> getMatchingAstElements() {
        final List<DetailAST> result = new ArrayList<>();
        DetailAST curNode = rootAst;
        while (curNode != null) {
            if (isMatchingByLineAndColumnAndTokenType(curNode)) {
                result.add(curNode);
            }
            DetailAST toVisit = curNode.getFirstChild();
            while (curNode != null && toVisit == null) {
                toVisit = curNode.getNextSibling();
                curNode = curNode.getParent();
            }

            curNode = toVisit;
        }
        return result;
    }

    /**
     * Returns relative xpath query for given ast element from root.
     *
     * @param root {@code DetailAST} root element
     * @param ast {@code DetailAST} ast element
     * @return relative xpath query for given ast element from root
     */
    private static String getXpathQuery(DetailAST root, DetailAST ast) {
        final StringBuilder resultBuilder = new StringBuilder(1024);
        DetailAST cur = ast;
        while (cur != root) {
            final StringBuilder curNodeQueryBuilder = new StringBuilder(256);
            curNodeQueryBuilder.append('/')
                    .append(TokenUtil.getTokenName(cur.getType()));
            if (XpathUtil.supportsTextAttribute(cur)) {
                curNodeQueryBuilder.append("[@text='")
                        .append(XpathUtil.getTextAttributeValue(cur))
                        .append("']");
            }
            else {
                final DetailAST child = findChildWithTextAttribute(cur);
                if (child != null && child != ast) {
                    curNodeQueryBuilder.append("[.")
                            .append(getXpathQuery(cur, child))
                            .append(']');
                }
            }

            resultBuilder.insert(0, curNodeQueryBuilder);
            cur = cur.getParent();
        }
        return resultBuilder.toString();
    }

    /**
     * Checks if the given ast element has unique {@code TokenTypes} among siblings.
     *
     * @param ast {@code DetailAST} ast element
     * @return if the given ast element has unique {@code TokenTypes} among siblings
     */
    private static boolean hasAtLeastOneSiblingWithSameTokenType(DetailAST ast) {
        boolean result = false;
        DetailAST prev = ast.getPreviousSibling();
        while (prev != null) {
            if (prev.getType() == ast.getType()) {
                result = true;
                break;
            }
            prev = prev.getPreviousSibling();
        }
        DetailAST next = ast.getNextSibling();
        while (next != null) {
            if (next.getType() == ast.getType()) {
                result = true;
                break;
            }
            next = next.getNextSibling();
        }
        return result;
    }

    /**
     * Returns the column number with tabs expanded.
     *
     * @param ast {@code DetailAST} root ast
     * @return the column number with tabs expanded
     */
    private int expandedTabColumn(DetailAST ast) {
        return 1 + CommonUtil.lengthExpandedTabs(fileText.get(lineNumber - 1),
                ast.getColumnNo(), tabWidth);
    }

    /**
     * Checks if the given {@code DetailAST} node is matching line number, column number and token
     * type.
     *
     * @param ast {@code DetailAST} ast element
     * @return true if the given {@code DetailAST} node is matching
     */
    private boolean isMatchingByLineAndColumnAndTokenType(DetailAST ast) {
        return ast.getLineNo() == lineNumber
                && expandedTabColumn(ast) == columnNumber
                && (tokenType == 0 || tokenType == ast.getType());
    }
}
