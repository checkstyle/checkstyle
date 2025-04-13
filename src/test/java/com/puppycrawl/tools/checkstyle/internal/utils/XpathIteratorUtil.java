///
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

package com.puppycrawl.tools.checkstyle.internal.utils;

import java.util.HashMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.xpath.RootNode;
import net.sf.saxon.om.NodeInfo;

/**
 * XpathIteratorUtil.
 *
 * @noinspection ClassOnlyUsedInOnePackage
 * @noinspectionreason ClassOnlyUsedInOnePackage - class is internal tool, and only used in testing
 */
public final class XpathIteratorUtil {

    /**
     * Map from {@link NodeInfo#getLocalPart()} to {@link NodeInfo}.
     */
    private static final Map<String, NodeInfo> NODES_BY_LOCAL_PART = new HashMap<>();

    static {
        fillMap(new RootNode(createTree()));
    }

    private XpathIteratorUtil() {
    }

    /**
     * Finds node by its {@link NodeInfo#getLocalPart()} value.
     *
     * @param localPart text to be searched
     *
     * @return corresponding node
     */
    public static NodeInfo findNode(String localPart) {
        return NODES_BY_LOCAL_PART.get(localPart);
    }

    /**
     * Iterates Xpath tree nodes and fills the map.
     *
     * @param cur current {@link NodeInfo} node
     */
    private static void fillMap(NodeInfo cur) {
        NODES_BY_LOCAL_PART.put(cur.getLocalPart(), cur);
        for (NodeInfo node : cur.children()) {
            fillMap(node);
        }
    }

    /**
     * Creates AST tree for iteration tests and returns root.
     *
     * <p>
     * PACKAGE_DEF -> package [1:0]
     * |--ANNOTATIONS -> ANNOTATIONS [1:8]
     *     `--ANNOTATION_DEF -> ANNOTATIONS [1:8]
     * `--CLASS_DEF -> CLASS_DEF [3:0]
     *     `--OBJBLOCK -> OBJBLOCK [3:19]
     *         |--LCURLY -> { [3:19]
     *         |--METHOD_DEF -> METHOD_DEF [4:4]
     *         |   |--PARAMETERS -> PARAMETERS [4:31]
     *         |   `--SLIST -> { [4:33]
     *         `--RCURLY -> } [8:0]
     * </p>
     *
     * @return {@link DetailAST} object.
     */
    private static DetailAST createTree() {
        final DetailAstImpl rootNode = createNode(TokenTypes.PACKAGE_DEF, 1, 0);

        final DetailAstImpl annotationsNode = createNode(rootNode, TokenTypes.ANNOTATIONS, 1, 8);
        createNode(annotationsNode, TokenTypes.ANNOTATION_DEF, 1, 8);

        final DetailAstImpl classDefNode = createNode(rootNode, TokenTypes.CLASS_DEF, 3, 0);
        final DetailAstImpl objblockNode = createNode(classDefNode, TokenTypes.OBJBLOCK, 3, 19);
        createNode(objblockNode, TokenTypes.LCURLY, 3, 19);
        final DetailAstImpl methodDefNode = createNode(objblockNode, TokenTypes.METHOD_DEF, 4, 4);
        createNode(objblockNode, TokenTypes.RCURLY, 8, 0);

        createNode(methodDefNode, TokenTypes.PARAMETERS, 4, 31);
        createNode(methodDefNode, TokenTypes.SLIST, 4, 33);

        return rootNode;
    }

    /**
     * Creates {@link DetailAstImpl} node with predefined parent.
     *
     * @param parent parent node
     * @param tokenType token type, see {@link TokenTypes}
     * @param lineNo line number
     * @param columnNo column number
     *
     * @return {@link DetailAstImpl} object.
     */
    private static DetailAstImpl createNode(DetailAstImpl parent, int tokenType, int lineNo,
                                            int columnNo) {
        final DetailAstImpl result = createNode(tokenType, lineNo, columnNo);
        parent.addChild(result);
        return result;
    }

    /**
     * Creates {@link DetailAstImpl} node.
     *
     * @param tokenType token type, see {@link TokenTypes}
     * @param lineNo line number
     * @param columnNo column number
     *
     * @return {@link DetailAstImpl} object.
     */
    private static DetailAstImpl createNode(int tokenType, int lineNo, int columnNo) {
        final DetailAstImpl result = new DetailAstImpl();
        result.setType(tokenType);
        result.setLineNo(lineNo);
        result.setColumnNo(columnNo);
        return result;
    }
}
