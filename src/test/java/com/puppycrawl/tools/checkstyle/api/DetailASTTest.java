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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.TreeWalker;

/**
 * TestCase to check DetailAST.
 * @author Oliver Burn
 */
public class DetailASTTest {
    private static Method getSetParentMethod() throws Exception {
        final Class<DetailAST> detailAstClass = DetailAST.class;
        final Method setParentMethod =
            detailAstClass.getDeclaredMethod("setParent", DetailAST.class);
        setParentMethod.setAccessible(true);
        return setParentMethod;
    }

    @Test
    public void testGetChildCount() throws Exception {
        final DetailAST root = new DetailAST();
        final DetailAST firstLevelA = new DetailAST();
        final DetailAST firstLevelB = new DetailAST();
        final DetailAST secondLevelA = new DetailAST();

        root.setFirstChild(firstLevelA);

        final Method setParentMethod = getSetParentMethod();
        setParentMethod.invoke(firstLevelA, root);
        firstLevelA.setFirstChild(secondLevelA);
        firstLevelA.setNextSibling(firstLevelB);

        setParentMethod.invoke(firstLevelB, root);

        setParentMethod.invoke(secondLevelA, root);

        assertEquals(0, secondLevelA.getChildCount());
        assertEquals(0, firstLevelB.getChildCount());
        assertEquals(1, firstLevelA.getChildCount());
        assertEquals(2, root.getChildCount());
        assertEquals(2, root.getChildCount());

        assertNull(root.getPreviousSibling());
        assertNull(firstLevelA.getPreviousSibling());
        assertNull(secondLevelA.getPreviousSibling());
        assertEquals(firstLevelA, firstLevelB.getPreviousSibling());
    }

    @Test
    public void testSetSiblingNull() throws Exception {
        final DetailAST root = new DetailAST();
        final DetailAST firstLevelA = new DetailAST();

        root.setFirstChild(firstLevelA);

        assertEquals(1, root.getChildCount());

        getSetParentMethod().invoke(firstLevelA, root);
        firstLevelA.addPreviousSibling(null);
        firstLevelA.addNextSibling(null);

        assertEquals(1, root.getChildCount());
    }

    @Test
    public void testInsertSiblingBetween() throws Exception {
        final DetailAST root = new DetailAST();
        final DetailAST firstLevelA = new DetailAST();
        final DetailAST firstLevelB = new DetailAST();
        final DetailAST firstLevelC = new DetailAST();

        assertEquals(0, root.getChildCount());

        root.setFirstChild(firstLevelA);
        final Method setParentMethod = getSetParentMethod();
        setParentMethod.invoke(firstLevelA, root);

        assertEquals(1, root.getChildCount());

        firstLevelA.addNextSibling(firstLevelB);
        setParentMethod.invoke(firstLevelB, root);

        assertEquals(firstLevelB, firstLevelA.getNextSibling());

        firstLevelA.addNextSibling(firstLevelC);
        setParentMethod.invoke(firstLevelC, root);

        assertEquals(firstLevelC, firstLevelA.getNextSibling());
    }

    @Test
    public void testClearBranchTokenTypes() throws Exception {
        final DetailAST parent = new DetailAST();
        final DetailAST child = new DetailAST();
        parent.setFirstChild(child);

        final List<Consumer<DetailAST>> clearBranchTokenTypesMethods = Arrays.asList(
            ast -> child.setFirstChild(ast),
            ast -> child.setNextSibling(ast),
            ast -> child.addPreviousSibling(ast),
            ast -> child.addNextSibling(ast),
            ast -> child.addChild(ast),
            ast -> {
                try {
                    Whitebox.invokeMethod(child, "setParent", ast);
                }
                // -@cs[IllegalCatch] Cannot avoid catching it.
                catch (Exception exception) {
                    throw new IllegalStateException(exception);
                }
            }
        );

        for (Consumer<DetailAST> method : clearBranchTokenTypesMethods) {
            final BitSet branchTokenTypes = Whitebox.invokeMethod(parent, "getBranchTokenTypes");
            method.accept(null);
            final BitSet branchTokenTypes2 = Whitebox.invokeMethod(parent, "getBranchTokenTypes");
            assertEquals(branchTokenTypes, branchTokenTypes2);
            assertNotSame(branchTokenTypes, branchTokenTypes2);
        }
    }

    @Test
    public void testClearChildCountCache() throws Exception {
        final DetailAST parent = new DetailAST();
        final DetailAST child = new DetailAST();
        parent.setFirstChild(child);

        final List<Consumer<DetailAST>> clearChildCountCacheMethods = Arrays.asList(
            ast -> child.setNextSibling(ast),
            ast -> child.addPreviousSibling(ast),
            ast -> child.addNextSibling(ast)
        );

        for (Consumer<DetailAST> method : clearChildCountCacheMethods) {
            final int startCount = parent.getChildCount();
            method.accept(null);
            final int intermediateCount = Whitebox.getInternalState(parent, "childCount");
            final int finishCount = parent.getChildCount();
            assertEquals(startCount, finishCount);
            assertEquals(Integer.MIN_VALUE, intermediateCount);
        }

        final int startCount = child.getChildCount();
        child.addChild(null);
        final int intermediateCount = Whitebox.getInternalState(child, "childCount");
        final int finishCount = child.getChildCount();
        assertEquals(startCount, finishCount);
        assertEquals(Integer.MIN_VALUE, intermediateCount);
    }

    @Test
    public void testAddNextSibling() {
        final DetailAST parent = new DetailAST();
        final DetailAST child = new DetailAST();
        final DetailAST sibling = new DetailAST();
        final DetailAST newSibling = new DetailAST();
        parent.setFirstChild(child);
        child.setNextSibling(sibling);

        child.addNextSibling(newSibling);
        assertTrue(newSibling.getParent().equals(parent));
        assertTrue(newSibling.getNextSibling().equals(sibling));
        assertTrue(child.getNextSibling().equals(newSibling));
    }

    @Test
    public void testTreeStructure() throws Exception {
        checkDir(new File("src/test/resources/com/puppycrawl/tools/checkstyle"));
    }

    @Test
    public void testToString() {
        final DetailAST ast = new DetailAST();
        ast.setText("text");
        ast.setColumnNo(0);
        ast.setLineNo(0);
        assertEquals("text[0x0]", ast.toString());
    }

    private static void checkDir(File dir) throws Exception {
        final File[] files = dir.listFiles(file -> {
            return (file.getName().endsWith(".java")
                || file.isDirectory())
                && !file.getName().endsWith("InputGrammar.java");
        });
        for (File file : files) {
            if (file.isFile()) {
                checkFile(file.getCanonicalPath());
            }
            else if (file.isDirectory()) {
                checkDir(file);
            }
        }
    }

    private static void checkFile(String filename) throws Exception {
        final FileText text = new FileText(new File(filename),
                           System.getProperty("file.encoding", "UTF-8"));
        final FileContents contents = new FileContents(text);
        final DetailAST rootAST = TreeWalker.parse(contents);
        if (rootAST != null) {
            checkTree(filename, rootAST);
        }
    }

    private static void checkTree(final String filename, final DetailAST root) {
        DetailAST curNode = root;
        DetailAST parent = null;
        DetailAST prev = null;
        while (curNode != null) {
            checkNode(curNode, parent, prev, filename, root);
            DetailAST toVisit = curNode.getFirstChild();
            if (toVisit == null) {
                while (curNode != null && toVisit == null) {
                    toVisit = curNode.getNextSibling();
                    if (toVisit == null) {
                        curNode = curNode.getParent();
                        if (curNode != null) {
                            parent = curNode.getParent();
                        }
                    }
                    else {
                        prev = curNode;
                        curNode = toVisit;
                    }
                }
            }
            else {
                parent = curNode;
                curNode = toVisit;
                prev = null;
            }
        }
    }

    private static void checkNode(final DetailAST node,
                                  final DetailAST parent,
                                  final DetailAST prev,
                                  final String filename,
                                  final DetailAST root) {
        final Object[] params = {
            node, parent, prev, filename, root,
        };
        final MessageFormat badParentFormatter = new MessageFormat(
                "Bad parent node={0} parent={1} filename={3} root={4}", Locale.ROOT);
        final String badParentMsg = badParentFormatter.format(params);
        assertEquals(badParentMsg, parent, node.getParent());
        final MessageFormat badPrevFormatter = new MessageFormat(
                "Bad prev node={0} prev={2} parent={1} filename={3} root={4}", Locale.ROOT);
        final String badPrevMsg = badPrevFormatter.format(params);
        assertEquals(badPrevMsg, prev, node.getPreviousSibling());
    }
}
