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

package com.puppycrawl.tools.checkstyle.api;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.xpath.ElementNode;
import com.puppycrawl.tools.checkstyle.xpath.RootNode;
import net.sf.saxon.om.GenericTreeInfo;
import net.sf.saxon.om.Item;
import net.sf.saxon.sxpath.XPathDynamicContext;
import net.sf.saxon.sxpath.XPathEvaluator;
import net.sf.saxon.sxpath.XPathExpression;
import net.sf.saxon.trans.XPathException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by timur on 6/13/17.
 */
public class XpathMapperTest {

    @Test
    public void testCorrectWithOneElement() throws XPathException, IOException, TokenStreamException, RecognitionException {
        final FileText text = new FileText(new File("src/test/resources/com/puppycrawl/tools/checkstyle/xpath/TestAst.java"),
                System.getProperty("file.encoding", "UTF-8"));
        final FileContents contents = new FileContents(text);

        DetailAST rootAST = TreeWalker.parse(contents);
        DetailNode detailNode = null;

        RootNode rootNode = new RootNode();
        ElementNode classDefinition = new ElementNode(TokenTypes.CLASS_DEF, rootNode, rootNode, detailNode, rootAST.getNextSibling());

        XPathEvaluator xpathEvaluator = new XPathEvaluator();
        String xpath = "/CLASS_DEF[@text='TestAst']";

        GenericTreeInfo genericTreeInfo = new GenericTreeInfo(null, rootNode);
        rootNode.setTreeInfo(genericTreeInfo);
        classDefinition.setTreeInfo(genericTreeInfo);

        XPathExpression xpathExpression = xpathEvaluator.createExpression(xpath);
        XPathDynamicContext xpathDynamicContext = xpathExpression.createDynamicContext(rootNode);
        List<Item> nodes = xpathExpression.evaluate(xpathDynamicContext);
        String[] actual = new String[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            actual[i] = nodes.get(i).getStringValue();
        }

        final String[] expected = {"CLASS_DEF"};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testWrongName() throws XPathException, IOException, TokenStreamException, RecognitionException {
        final FileText text = new FileText(new File("src/test/resources/com/puppycrawl/tools/checkstyle/xpath/TestAst.java"),
                System.getProperty("file.encoding", "UTF-8"));
        final FileContents contents = new FileContents(text);

        DetailAST rootAST = TreeWalker.parse(contents);
        DetailNode detailNode = null;

        RootNode rootNode = new RootNode();
        ElementNode classDefinition = new ElementNode(TokenTypes.CLASS_DEF, rootNode, rootNode, detailNode, rootAST.getNextSibling());

        XPathEvaluator xpathEvaluator = new XPathEvaluator();
        String xpath = "/CLASS_DEF[@text='WrongName']";

        GenericTreeInfo genericTreeInfo = new GenericTreeInfo(null, rootNode);
        rootNode.setTreeInfo(genericTreeInfo);
        classDefinition.setTreeInfo(genericTreeInfo);

        XPathExpression xpathExpression = xpathEvaluator.createExpression(xpath);
        XPathDynamicContext xpathDynamicContext = xpathExpression.createDynamicContext(rootNode);
        List<Item> nodes = xpathExpression.evaluate(xpathDynamicContext);
        String[] actual = new String[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            actual[i] = nodes.get(i).getStringValue();
        }

        final String[] expected = {};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testRoot() throws XPathException, IOException, TokenStreamException, RecognitionException {
        final FileText text = new FileText(new File("src/test/resources/com/puppycrawl/tools/checkstyle/xpath/TestAst.java"),
                System.getProperty("file.encoding", "UTF-8"));
        final FileContents contents = new FileContents(text);

        DetailAST rootAST = TreeWalker.parse(contents);
        DetailNode detailNode = null;

        RootNode rootNode = new RootNode();
        ElementNode classDefinition = new ElementNode(TokenTypes.CLASS_DEF, rootNode, rootNode, detailNode, rootAST.getNextSibling());

        XPathEvaluator xpathEvaluator = new XPathEvaluator();
        String xpath = "/";

        GenericTreeInfo genericTreeInfo = new GenericTreeInfo(null, rootNode);
        rootNode.setTreeInfo(genericTreeInfo);
        classDefinition.setTreeInfo(genericTreeInfo);

        XPathExpression xpathExpression = xpathEvaluator.createExpression(xpath);
        XPathDynamicContext xpathDynamicContext = xpathExpression.createDynamicContext(classDefinition);
        List<Item> nodes = xpathExpression.evaluate(xpathDynamicContext);
        String[] actual = new String[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            actual[i] = nodes.get(i).getStringValue();
        }

        final String[] expected = {"ROOT"};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testNotFoundQuery() throws XPathException, IOException, TokenStreamException, RecognitionException  {
        final FileText text = new FileText(new File("src/test/resources/com/puppycrawl/tools/checkstyle/xpath/TestAst.java"),
                System.getProperty("file.encoding", "UTF-8"));
        final FileContents contents = new FileContents(text);

        DetailAST rootAST = TreeWalker.parse(contents);
        DetailNode detailNode = null;

        RootNode rootNode = new RootNode();
        ElementNode classDefinition = new ElementNode(TokenTypes.CLASS_DEF, rootNode, rootNode, detailNode, rootAST.getNextSibling());

        XPathEvaluator xpathEvaluator = new XPathEvaluator();
        String xpath = "/WRONG_XPATH";

        GenericTreeInfo genericTreeInfo = new GenericTreeInfo(null, rootNode);
        rootNode.setTreeInfo(genericTreeInfo);
        classDefinition.setTreeInfo(genericTreeInfo);

        XPathExpression xpathExpression = xpathEvaluator.createExpression(xpath);
        XPathDynamicContext xpathDynamicContext = xpathExpression.createDynamicContext(rootNode);
        List<Item> nodes = xpathExpression.evaluate(xpathDynamicContext);
        String[] actual = new String[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            actual[i] = nodes.get(i).getStringValue();
        }

        final String[] expected = {};
        assertArrayEquals(expected, actual);
    }
}
