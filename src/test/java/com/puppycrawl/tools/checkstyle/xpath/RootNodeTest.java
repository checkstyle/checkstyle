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

package com.puppycrawl.tools.checkstyle.xpath;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;
import static com.puppycrawl.tools.checkstyle.utils.XpathUtil.getXpathItems;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.EmptyIterator;

public class RootNodeTest extends AbstractPathTestSupport {

    private static RootNode rootNode;

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/xpath/xpathmapper";
    }

    @BeforeEach
    public void init() throws Exception {
        final File file = new File(getPath("InputXpathMapperAst.java"));
        final DetailAST rootAst = JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS);
        rootNode = new RootNode(rootAst);
    }

    @Test
    public void testCompareOrder() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, () -> {
                    rootNode.compareOrder(null);
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testXpath() throws Exception {
        final String xpath = "/";
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertWithMessage("Invalid number of nodes")
            .that(nodes)
            .hasSize(1);
        final NodeInfo firstNode = nodes.getFirst();
        assertWithMessage("Should return true, because selected node is RootNode")
                .that(firstNode)
                .isInstanceOf(RootNode.class);
        assertWithMessage("Result node should have same reference as expected")
            .that(rootNode)
            .isEqualTo(firstNode);
    }

    @Test
    public void testGetDepth() {
        assertWithMessage("Root node depth should be 0")
                .that(rootNode.getDepth())
                .isEqualTo(0);
    }

    @Test
    public void testGetTokenType() {
        assertWithMessage("Invalid token type")
            .that(rootNode.getTokenType())
            .isEqualTo(TokenTypes.COMPILATION_UNIT);
    }

    @Test
    public void testGetLineNumber() {
        assertWithMessage("Invalid line number")
            .that(rootNode.getLineNumber())
            .isEqualTo(1);
    }

    @Test
    public void testGetColumnNumber() {
        assertWithMessage("Invalid column number")
            .that(rootNode.getColumnNumber())
            .isEqualTo(1);
    }

    /*
     * This test exists to cover pitest mutation.
     * It is impossible to create RootNode that does not have column as 1.
     */
    @Test
    public void testNonRealGetColumnNumber() {
        final DetailAstImpl nonRealNode = new DetailAstImpl();
        nonRealNode.setType(TokenTypes.PACKAGE_DEF);
        nonRealNode.setLineNo(555);
        nonRealNode.setColumnNo(888);

        final RootNode nonRealRootNode = new RootNode(nonRealNode);
        assertWithMessage("Invalid column number")
            .that(nonRealRootNode.getColumnNumber())
            .isEqualTo(888);
    }

    @Test
    public void testGetLocalPart() {
        assertWithMessage("Invalid local part")
            .that(rootNode.getLocalPart())
            .isEqualTo("ROOT");
    }

    @Test
    public void testIterate() {
        try (AxisIterator following = rootNode.iterateAxis(AxisInfo.FOLLOWING)) {
            assertWithMessage("Result iterator does not match expected")
                .that(following)
                .isEqualTo(EmptyIterator.ofNodes());
        }
        try (AxisIterator followingSibling = rootNode.iterateAxis(AxisInfo.FOLLOWING_SIBLING)) {
            assertWithMessage("Result iterator does not match expected")
                .that(followingSibling)
                .isEqualTo(EmptyIterator.ofNodes());
        }
        try (AxisIterator preceding = rootNode.iterateAxis(AxisInfo.PRECEDING)) {
            assertWithMessage("Result iterator does not match expected")
                .that(preceding)
                .isEqualTo(EmptyIterator.ofNodes());
        }
        try (AxisIterator precedingSibling = rootNode.iterateAxis(AxisInfo.PRECEDING_SIBLING)) {
            assertWithMessage("Result iterator does not match expected")
                .that(precedingSibling)
                .isEqualTo(EmptyIterator.ofNodes());
        }
        try (AxisIterator parent = rootNode.iterateAxis(AxisInfo.PARENT)) {
            assertWithMessage("Result iterator does not match expected")
                .that(parent)
                .isEqualTo(EmptyIterator.ofNodes());
        }
        try (AxisIterator parentNull = rootNode.iterateAxis(AxisInfo.PARENT, null)) {
            assertWithMessage("Result iterator does not match expected")
                .that(parentNull)
                .isEqualTo(EmptyIterator.ofNodes());
        }
    }

    @Test
    public void testRootWithNullDetailAst() {
        final RootNode emptyRootNode = new RootNode(null);
        assertWithMessage("Empty node should not have children")
                .that(emptyRootNode.hasChildNodes())
                .isFalse();

        try (AxisIterator iterator = emptyRootNode.iterateAxis(AxisInfo.DESCENDANT)) {
            assertWithMessage("Result iterator does not match expected")
                .that(iterator)
                .isEqualTo(EmptyIterator.ofNodes());
        }
        try (AxisIterator iterator = emptyRootNode.iterateAxis(AxisInfo.CHILD)) {
            assertWithMessage("Result iterator does not match expected")
                .that(iterator)
                .isEqualTo(EmptyIterator.ofNodes());
        }
    }

    @Test
    public void testGetStringValue() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::getStringValue);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testGetAttributeValue() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, () -> {
                    rootNode.getAttributeValue("", "");
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testGetDeclaredNamespaces() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, () -> {
                    rootNode.getDeclaredNamespaces(null);
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testIsId() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::isId);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testIsIdref() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::isIdref);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testIsNilled() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::isNilled);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testIsStreamed() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::isStreamed);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testGetConfiguration() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class,
                        rootNode::getConfiguration);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testSetSystemId() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, () -> {
                    rootNode.setSystemId("1");
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testGetSystemId() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::getSystemId);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testGetPublicId() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::getPublicId);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testBaseUri() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::getBaseURI);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testSaveLocation() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::saveLocation);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testGetStringValueCs() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class,
                        rootNode::getUnicodeStringValue);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testFingerprint() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::getFingerprint);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testGetDisplayName() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::getDisplayName);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testGetPrefix() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::getPrefix);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testGetSchemaType() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::getSchemaType);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testAtomize() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, rootNode::atomize);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testGenerateId() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, () -> {
                    rootNode.generateId(null);
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testCopy() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class, () -> {
                    rootNode.copy(null, -1, null);
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testGetAllNamespaces() {
        final UnsupportedOperationException exc =
                getExpectedThrowable(UnsupportedOperationException.class,
                        rootNode::getAllNamespaces);
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testSameNodeInfo() {
        assertWithMessage("Should return true, because object is being compared to itself")
                .that(rootNode.isSameNodeInfo(rootNode))
                .isTrue();
        assertWithMessage("Should return false, because object does not equal null")
                .that(rootNode.isSameNodeInfo(null))
                .isFalse();
    }
}
