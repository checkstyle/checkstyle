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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.checks.TodoCommentCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * TestCase to check DetailAST.
 * @author Oliver Burn
 */
public class DetailASTTest extends AbstractModuleTestSupport {
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/api";
    }

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

        assertEquals("Invalid child count", 0, secondLevelA.getChildCount());
        assertEquals("Invalid child count", 0, firstLevelB.getChildCount());
        assertEquals("Invalid child count", 1, firstLevelA.getChildCount());
        assertEquals("Invalid child count", 2, root.getChildCount());
        assertEquals("Invalid child count", 2, root.getChildCount());

        assertNull("Previous sibling should be null", root.getPreviousSibling());
        assertNull("Previous sibling should be null", firstLevelA.getPreviousSibling());
        assertNull("Previous sibling should be null", secondLevelA.getPreviousSibling());
        assertEquals("Invalid previous sibling", firstLevelA, firstLevelB.getPreviousSibling());
    }

    @Test
    public void testSetSiblingNull() throws Exception {
        final DetailAST root = new DetailAST();
        final DetailAST firstLevelA = new DetailAST();

        root.setFirstChild(firstLevelA);

        assertEquals("Invalid child count", 1, root.getChildCount());

        getSetParentMethod().invoke(firstLevelA, root);
        firstLevelA.addPreviousSibling(null);
        firstLevelA.addNextSibling(null);

        assertEquals("Invalid child count", 1, root.getChildCount());
    }

    @Test
    public void testInsertSiblingBetween() throws Exception {
        final DetailAST root = new DetailAST();
        final DetailAST firstLevelA = new DetailAST();
        final DetailAST firstLevelB = new DetailAST();
        final DetailAST firstLevelC = new DetailAST();

        assertEquals("Invalid child count", 0, root.getChildCount());

        root.setFirstChild(firstLevelA);
        final Method setParentMethod = getSetParentMethod();
        setParentMethod.invoke(firstLevelA, root);

        assertEquals("Invalid child count", 1, root.getChildCount());

        firstLevelA.addNextSibling(firstLevelB);
        setParentMethod.invoke(firstLevelB, root);

        assertEquals("Invalid next sibling", firstLevelB, firstLevelA.getNextSibling());

        firstLevelA.addNextSibling(firstLevelC);
        setParentMethod.invoke(firstLevelC, root);

        assertEquals("Invalid next sibling", firstLevelC, firstLevelA.getNextSibling());
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
            assertEquals("Branch token types are not equal", branchTokenTypes, branchTokenTypes2);
            assertNotSame("Branch token types should not be the same",
                    branchTokenTypes, branchTokenTypes2);
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
            assertEquals("Child count has changed", startCount, finishCount);
            assertEquals("Invalid child count", Integer.MIN_VALUE, intermediateCount);
        }

        final int startCount = child.getChildCount();
        child.addChild(null);
        final int intermediateCount = Whitebox.getInternalState(child, "childCount");
        final int finishCount = child.getChildCount();
        assertEquals("Child count has changed", startCount, finishCount);
        assertEquals("Invalid child count", Integer.MIN_VALUE, intermediateCount);
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

        assertEquals("Invalid parent", parent, newSibling.getParent());
        assertEquals("Invalid next sibling", sibling, newSibling.getNextSibling());
        assertEquals("Invalid child", newSibling, child.getNextSibling());
    }

    @Test
    public void testManyComments() throws Exception {
        final File file = temporaryFolder.newFile("InputDetailASTManyComments.java");

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8))) {
            bw.write("class C {\n");
            for (int i = 0; i <= 30000; i++) {
                bw.write("// " + i + "\n");
            }
            bw.write("}\n");
        }

        final DefaultConfiguration checkConfig = createModuleConfig(TodoCommentCheck.class);

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, file.getAbsolutePath(), expected);
    }

    /**
     * There are asserts in checkNode, but idea does not see them
     * @noinspection JUnitTestMethodWithNoAssertions
     */
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
        assertEquals("Invalid text", "text[0x0]", ast.toString());
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
