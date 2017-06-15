////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.internal.XpathUtil.getXpathItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.TestUtils;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.NodeInfo;

public class ElementNodeTest {

    private static RootNode rootNode;

    @Before
    public void init() throws Exception {
        final File file = new File("src/test/resources/com/puppycrawl/tools/"
                + "checkstyle/xpath/InputXpathMapperAst.java");
        final DetailAST rootAst = TestUtils.parseFile(file);
        rootNode = new RootNode(rootAst);
    }

    @Test
    public void testGetParent() throws Exception {
        final String xpath = "//OBJBLOCK";
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertEquals("Invalid number of nodes", 1, nodes.size());
        final AbstractNode parent = (AbstractNode) ((NodeInfo) nodes.get(0)).getParent();
        assertEquals("Invalid token type", TokenTypes.CLASS_DEF, parent.getTokenType());
    }

    @Test
    public void testRootOfElementNode() throws Exception {
        final String xpath = "//OBJBLOCK";
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertEquals("Invalid number of nodes", 1, nodes.size());
        final AbstractNode root = (AbstractNode) ((NodeInfo) nodes.get(0)).getRoot();
        assertEquals("Invalid token type", TokenTypes.EOF, root.getTokenType());
        assertTrue("Should return true, because selected node is RootNode",
                root instanceof RootNode);
    }
}
