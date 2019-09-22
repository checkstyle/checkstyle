////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.Writer;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.powermock.reflect.Whitebox;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.TodoCommentCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * TestCase to check DetailAST.
 */
public class DetailAstImplTest extends AbstractModuleTestSupport {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/api/detailast";
    }

    private static Method getSetParentMethod() throws Exception {
        final Class<DetailAstImpl> detailAstClass = DetailAstImpl.class;
        final Method setParentMethod =
            detailAstClass.getDeclaredMethod("setParent", DetailAstImpl.class);
        setParentMethod.setAccessible(true);
        return setParentMethod;
    }

    @Test
    public void testInitialize() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setText("test");
        ast.setType(1);
        ast.setLineNo(2);
        ast.setColumnNo(3);

        final DetailAstImpl copy = new DetailAstImpl();
        copy.initialize(ast);

        assertEquals("Invalid text", "test", copy.getText());
        assertEquals("Invalid type", 1, copy.getType());
        assertEquals("Invalid line number", 2, copy.getLineNo());
        assertEquals("Invalid column number", 3, copy.getColumnNo());
    }

    @Test
    public void testInitializeToken() {
        final CommonHiddenStreamToken token = new CommonHiddenStreamToken();
        token.setText("test");
        token.setType(1);
        token.setLine(2);
        token.setColumn(4);

        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(token);

        assertEquals("Invalid text", "test", ast.getText());
        assertEquals("Invalid type", 1, ast.getType());
        assertEquals("Invalid line number", 2, ast.getLineNo());
        assertEquals("Invalid column number", 3, ast.getColumnNo());
    }

    @Test
    public void testGetChildCount() throws Exception {
        final DetailAstImpl root = new DetailAstImpl();
        final DetailAstImpl firstLevelA = new DetailAstImpl();
        final DetailAstImpl firstLevelB = new DetailAstImpl();
        final DetailAstImpl secondLevelA = new DetailAstImpl();

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
    public void testGetChildCountType() throws Exception {
        final DetailAstImpl root = new DetailAstImpl();
        final DetailAstImpl firstLevelA = new DetailAstImpl();
        final DetailAstImpl firstLevelB = new DetailAstImpl();

        root.setFirstChild(firstLevelA);

        final Method setParentMethod = getSetParentMethod();
        setParentMethod.invoke(firstLevelA, root);
        firstLevelA.setNextSibling(firstLevelB);

        firstLevelA.setType(TokenTypes.IDENT);
        firstLevelB.setType(TokenTypes.EXPR);

        setParentMethod.invoke(firstLevelB, root);

        assertEquals("Invalid child count", 0, firstLevelB.getChildCount(0));
        assertEquals("Invalid child count", 0, firstLevelA.getChildCount(TokenTypes.EXPR));
        assertEquals("Invalid child count", 1, root.getChildCount(TokenTypes.IDENT));
        assertEquals("Invalid child count", 1, root.getChildCount(TokenTypes.EXPR));
        assertEquals("Invalid child count", 0, root.getChildCount(0));
    }

    @Test
    public void testSetSiblingNull() throws Exception {
        final DetailAstImpl root = new DetailAstImpl();
        final DetailAstImpl firstLevelA = new DetailAstImpl();

        root.setFirstChild(firstLevelA);

        assertEquals("Invalid child count", 1, root.getChildCount());

        getSetParentMethod().invoke(firstLevelA, root);
        firstLevelA.addPreviousSibling(null);
        firstLevelA.addNextSibling(null);

        assertEquals("Invalid child count", 1, root.getChildCount());
    }

    @Test
    public void testAddPreviousSibling() {
        final DetailAST previousSibling = new DetailAstImpl();
        final DetailAstImpl instance = new DetailAstImpl();
        final DetailAstImpl parent = new DetailAstImpl();

        parent.setFirstChild(instance);

        instance.addPreviousSibling(previousSibling);

        assertEquals("unexpected result", previousSibling, instance.getPreviousSibling());
        assertEquals("unexpected result", previousSibling, parent.getFirstChild());

        final DetailAST newPreviousSibling = new DetailAstImpl();

        instance.addPreviousSibling(newPreviousSibling);

        assertEquals("unexpected result", newPreviousSibling, instance.getPreviousSibling());
        assertEquals("unexpected result", previousSibling, newPreviousSibling.getPreviousSibling());
        assertEquals("unexpected result", newPreviousSibling, previousSibling.getNextSibling());
        assertEquals("unexpected result", previousSibling, parent.getFirstChild());
    }

    @Test
    public void testAddPreviousSiblingNullParent() {
        final DetailAstImpl child = new DetailAstImpl();
        final DetailAST newSibling = new DetailAstImpl();

        child.addPreviousSibling(newSibling);

        assertEquals("Invalid child token", child, newSibling.getNextSibling());
        assertEquals("Invalid child token", newSibling, child.getPreviousSibling());
    }

    @Test
    public void testInsertSiblingBetween() throws Exception {
        final DetailAstImpl root = new DetailAstImpl();
        final DetailAstImpl firstLevelA = new DetailAstImpl();
        final DetailAST firstLevelB = new DetailAstImpl();
        final DetailAST firstLevelC = new DetailAstImpl();

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
    public void testBranchContains() {
        final DetailAstImpl root = createToken(null, TokenTypes.CLASS_DEF);
        final DetailAstImpl modifiers = createToken(root, TokenTypes.MODIFIERS);
        createToken(modifiers, TokenTypes.LITERAL_PUBLIC);

        assertTrue("invalid result", root.branchContains(TokenTypes.LITERAL_PUBLIC));
        assertFalse("invalid result", root.branchContains(TokenTypes.OBJBLOCK));
    }

    private static DetailAstImpl createToken(DetailAstImpl root, int type) {
        final DetailAstImpl result = new DetailAstImpl();
        result.setType(type);
        if (root != null) {
            root.addChild(result);
        }
        return result;
    }

    @Test
    public void testClearBranchTokenTypes() throws Exception {
        final DetailAstImpl parent = new DetailAstImpl();
        final DetailAstImpl child = new DetailAstImpl();
        parent.setFirstChild(child);

        final List<Consumer<DetailAstImpl>> clearBranchTokenTypesMethods = Arrays.asList(
                child::setFirstChild,
                child::setNextSibling,
                child::addPreviousSibling,
                child::addNextSibling,
                child::addChild,
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

        for (Consumer<DetailAstImpl> method : clearBranchTokenTypesMethods) {
            final BitSet branchTokenTypes = Whitebox.invokeMethod(parent, "getBranchTokenTypes");
            method.accept(null);
            final BitSet branchTokenTypes2 = Whitebox.invokeMethod(parent, "getBranchTokenTypes");
            assertEquals("Branch token types are not equal", branchTokenTypes, branchTokenTypes2);
            assertNotSame("Branch token types should not be the same",
                    branchTokenTypes, branchTokenTypes2);
        }
    }

    @Test
    public void testCacheBranchTokenTypes() {
        final DetailAST root = new DetailAstImpl();
        final BitSet bitSet = new BitSet();
        bitSet.set(999);

        Whitebox.setInternalState(root, "branchTokenTypes", bitSet);
        assertTrue("Branch tokens has changed", root.branchContains(999));
    }

    @Test
    public void testClearChildCountCache() {
        final DetailAstImpl parent = new DetailAstImpl();
        final DetailAstImpl child = new DetailAstImpl();
        parent.setFirstChild(child);

        final List<Consumer<DetailAstImpl>> clearChildCountCacheMethods = Arrays.asList(
                child::setNextSibling,
                child::addPreviousSibling,
                child::addNextSibling
        );

        for (Consumer<DetailAstImpl> method : clearChildCountCacheMethods) {
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
    public void testCacheGetChildCount() {
        final DetailAST root = new DetailAstImpl();

        Whitebox.setInternalState(root, "childCount", 999);
        assertEquals("Child count has changed", 999, root.getChildCount());
    }

    @Test
    public void testAddNextSibling() {
        final DetailAstImpl parent = new DetailAstImpl();
        final DetailAstImpl child = new DetailAstImpl();
        final DetailAstImpl sibling = new DetailAstImpl();
        final DetailAST newSibling = new DetailAstImpl();
        parent.setFirstChild(child);
        child.setNextSibling(sibling);
        child.addNextSibling(newSibling);

        assertEquals("Invalid parent", parent, newSibling.getParent());
        assertEquals("Invalid next sibling", sibling, newSibling.getNextSibling());
        assertEquals("Invalid child", newSibling, child.getNextSibling());
    }

    @Test
    public void testAddNextSiblingNullParent() {
        final DetailAstImpl child = new DetailAstImpl();
        final DetailAstImpl newSibling = new DetailAstImpl();
        final DetailAstImpl oldParent = new DetailAstImpl();
        oldParent.addChild(newSibling);
        child.addNextSibling(newSibling);

        assertEquals("Invalid parent", oldParent, newSibling.getParent());
        assertNull("Invalid next sibling", newSibling.getNextSibling());
        assertEquals("Invalid child", newSibling, child.getNextSibling());
    }

    @Test
    public void testGetLineNo() {
        final DetailAstImpl root1 = new DetailAstImpl();
        root1.setLineNo(1);
        assertEquals("Invalid line number", 1, root1.getLineNo());

        final DetailAstImpl root2 = new DetailAstImpl();
        final DetailAstImpl firstChild = new DetailAstImpl();
        firstChild.setLineNo(2);
        root2.setFirstChild(firstChild);
        assertEquals("Invalid line number", 2, root2.getLineNo());

        final DetailAstImpl root3 = new DetailAstImpl();
        final DetailAstImpl nextSibling = new DetailAstImpl();
        nextSibling.setLineNo(3);
        root3.setNextSibling(nextSibling);
        assertEquals("Invalid line number", 3, root3.getLineNo());

        final DetailAstImpl root4 = new DetailAstImpl();
        final DetailAstImpl comment = new DetailAstImpl();
        comment.setType(TokenTypes.SINGLE_LINE_COMMENT);
        comment.setLineNo(3);
        root4.setFirstChild(comment);
        assertEquals("Invalid line number", Integer.MIN_VALUE, root4.getLineNo());
    }

    @Test
    public void testGetColumnNo() {
        final DetailAstImpl root1 = new DetailAstImpl();
        root1.setColumnNo(1);
        assertEquals("Invalid column number", 1, root1.getColumnNo());

        final DetailAstImpl root2 = new DetailAstImpl();
        final DetailAstImpl firstChild = new DetailAstImpl();
        firstChild.setColumnNo(2);
        root2.setFirstChild(firstChild);
        assertEquals("Invalid column number", 2, root2.getColumnNo());

        final DetailAstImpl root3 = new DetailAstImpl();
        final DetailAstImpl nextSibling = new DetailAstImpl();
        nextSibling.setColumnNo(3);
        root3.setNextSibling(nextSibling);
        assertEquals("Invalid column number", 3, root3.getColumnNo());

        final DetailAstImpl root4 = new DetailAstImpl();
        final DetailAstImpl comment = new DetailAstImpl();
        comment.setType(TokenTypes.SINGLE_LINE_COMMENT);
        comment.setColumnNo(3);
        root4.setFirstChild(comment);
        assertEquals("Invalid column number", Integer.MIN_VALUE, root4.getColumnNo());
    }

    @Test
    public void testFindFirstToken() {
        final DetailAstImpl root = new DetailAstImpl();
        final DetailAstImpl firstChild = new DetailAstImpl();
        firstChild.setType(TokenTypes.IDENT);
        final DetailAstImpl secondChild = new DetailAstImpl();
        secondChild.setType(TokenTypes.EXPR);
        final DetailAstImpl thirdChild = new DetailAstImpl();
        thirdChild.setType(TokenTypes.IDENT);

        root.addChild(firstChild);
        root.addChild(secondChild);
        root.addChild(thirdChild);

        assertNull("Invalid result", firstChild.findFirstToken(TokenTypes.IDENT));
        assertEquals("Invalid result", firstChild, root.findFirstToken(TokenTypes.IDENT));
        assertEquals("Invalid result", secondChild, root.findFirstToken(TokenTypes.EXPR));
        assertNull("Invalid result", root.findFirstToken(0));
    }

    @Test
    public void testManyComments() throws Exception {
        final File file = temporaryFolder.newFile("InputDetailASTManyComments.java");

        try (Writer bw = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            bw.write("class C {\n");
            for (int i = 0; i <= 30000; i++) {
                bw.write("// " + i + "\n");
            }
            bw.write("}\n");
        }

        final DefaultConfiguration checkConfig = createModuleConfig(TodoCommentCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, file.getAbsolutePath(), expected);
    }

    @Test
    public void testTreeStructure() throws Exception {
        final List<File> files = getAllFiles(
                new File("src/test/resources/com/puppycrawl/tools/checkstyle"));

        for (File file : files) {
            final String fileName = file.getCanonicalPath();
            final DetailAST rootAST = JavaParser.parseFile(new File(fileName),
                    JavaParser.Options.WITHOUT_COMMENTS);

            assertNotNull("file must return a root node: " + fileName, rootAST);

            assertTrue("tree is valid", checkTree(fileName, rootAST));
        }
    }

    @Test
    public void testToString() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setText("text");
        ast.setColumnNo(0);
        ast.setLineNo(0);
        assertEquals("Invalid text", "text[0x0]", ast.toString());
    }

    private static List<File> getAllFiles(File dir) {
        final List<File> result = new ArrayList<>();

        dir.listFiles(file -> {
            if (file.isDirectory()) {
                result.addAll(getAllFiles(file));
            }
            else if (file.getName().endsWith(".java")
                    // fails with unexpected character
                    && !file.getName().endsWith("InputGrammar.java")
                    // comment only files, no root
                    && !file.getName().endsWith("InputPackageDeclarationWithCommentOnly.java")
                    && !file.getName().endsWith("InputSingleSpaceSeparatorEmpty.java")) {
                result.add(file);
            }

            return false;
        });

        return result;
    }

    private static boolean checkTree(final String filename, final DetailAST root) {
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

        return true;
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
