///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;

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
import java.util.Set;
import java.util.function.Consumer;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.TodoCommentCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * TestCase to check DetailAST.
 */
public class DetailAstImplTest extends AbstractModuleTestSupport {

    // Ignores file which are not meant to have root node intentionally.
    public static final Set<String> NO_ROOT_FILES = Set.of(
                 // fails with unexpected character
                 "InputGrammar.java",
                 // comment only files, no root
                 "InputPackageDeclarationWithCommentOnly.java",
                 "InputSingleSpaceSeparatorEmpty.java",
                 "InputNoCodeInFile1.java",
                 "InputNoCodeInFile2.java",
                 "InputNoCodeInFile3.java",
                 "InputNoCodeInFile5.java"
        );

    @TempDir
    public File temporaryFolder;

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
        copy.setText(ast.getText());
        copy.setType(ast.getType());
        copy.setLineNo(ast.getLineNo());
        copy.setColumnNo(ast.getColumnNo());

        assertWithMessage("Invalid text")
            .that(copy.getText())
            .isEqualTo("test");
        assertWithMessage("Invalid type")
            .that(copy.getType())
            .isEqualTo(1);
        assertWithMessage("Invalid line number")
            .that(copy.getLineNo())
            .isEqualTo(2);
        assertWithMessage("Invalid column number")
            .that(copy.getColumnNo())
            .isEqualTo(3);
    }

    @Test
    public void testInitializeToken() {
        final CommonToken token = new CommonToken(1);
        token.setText("test");
        token.setLine(2);
        token.setCharPositionInLine(3);

        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(token);

        assertWithMessage("Invalid text")
            .that(ast.getText())
            .isEqualTo("test");
        assertWithMessage("Invalid type")
            .that(ast.getType())
            .isEqualTo(1);
        assertWithMessage("Invalid line number")
            .that(ast.getLineNo())
            .isEqualTo(2);
        assertWithMessage("Invalid column number")
            .that(ast.getColumnNo())
            .isEqualTo(3);
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

        assertWithMessage("Invalid child count")
            .that(secondLevelA.getChildCount())
            .isEqualTo(0);
        assertWithMessage("Invalid child count")
            .that(firstLevelB.getChildCount())
            .isEqualTo(0);
        assertWithMessage("Invalid child count")
            .that(firstLevelA.getChildCount())
            .isEqualTo(1);
        assertWithMessage("Invalid child count")
            .that(root.getChildCount())
            .isEqualTo(2);
        assertWithMessage("Invalid child count")
            .that(root.getNumberOfChildren())
            .isEqualTo(2);

        assertWithMessage("Previous sibling should be null")
            .that(root.getPreviousSibling())
            .isNull();
        assertWithMessage("Previous sibling should be null")
            .that(firstLevelA.getPreviousSibling())
            .isNull();
        assertWithMessage("Previous sibling should be null")
            .that(secondLevelA.getPreviousSibling())
            .isNull();
        assertWithMessage("Invalid previous sibling")
            .that(firstLevelB.getPreviousSibling())
            .isEqualTo(firstLevelA);
    }

    @Test
    public void testHasChildren() {
        final DetailAstImpl root = new DetailAstImpl();
        final DetailAstImpl child = new DetailAstImpl();
        root.setFirstChild(child);

        assertWithMessage("Root node should have children")
                .that(root.hasChildren())
                .isTrue();
        assertWithMessage("Child node should have no children")
                .that(child.hasChildren())
                .isFalse();
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

        final int childCountLevelB = firstLevelB.getChildCount(0);
        assertWithMessage("Invalid child count")
            .that(childCountLevelB)
            .isEqualTo(0);
        final int childCountLevelA = firstLevelA.getChildCount(TokenTypes.EXPR);
        assertWithMessage("Invalid child count")
            .that(childCountLevelA)
            .isEqualTo(0);
        final int identTypeCount = root.getChildCount(TokenTypes.IDENT);
        assertWithMessage("Invalid child count")
            .that(identTypeCount)
            .isEqualTo(1);
        final int exprTypeCount = root.getChildCount(TokenTypes.EXPR);
        assertWithMessage("Invalid child count")
            .that(exprTypeCount)
            .isEqualTo(1);
        final int invalidTypeCount = root.getChildCount(0);
        assertWithMessage("Invalid child count")
            .that(invalidTypeCount)
            .isEqualTo(0);
    }

    @Test
    public void testSetSiblingNull() throws Exception {
        final DetailAstImpl root = new DetailAstImpl();
        final DetailAstImpl firstLevelA = new DetailAstImpl();

        root.setFirstChild(firstLevelA);

        assertWithMessage("Invalid child count")
            .that(root.getChildCount())
            .isEqualTo(1);

        getSetParentMethod().invoke(firstLevelA, root);
        firstLevelA.addPreviousSibling(null);
        firstLevelA.addNextSibling(null);

        assertWithMessage("Invalid child count")
            .that(root.getChildCount())
            .isEqualTo(1);
    }

    @Test
    public void testAddPreviousSibling() {
        final DetailAST previousSibling = new DetailAstImpl();
        final DetailAstImpl instance = new DetailAstImpl();
        final DetailAstImpl parent = new DetailAstImpl();

        parent.setFirstChild(instance);

        instance.addPreviousSibling(previousSibling);

        assertWithMessage("unexpected result")
            .that(instance.getPreviousSibling())
            .isEqualTo(previousSibling);
        assertWithMessage("unexpected result")
            .that(parent.getFirstChild())
            .isEqualTo(previousSibling);

        final DetailAST newPreviousSibling = new DetailAstImpl();

        instance.addPreviousSibling(newPreviousSibling);

        assertWithMessage("unexpected result")
            .that(instance.getPreviousSibling())
            .isEqualTo(newPreviousSibling);
        assertWithMessage("unexpected result")
            .that(newPreviousSibling.getPreviousSibling())
            .isEqualTo(previousSibling);
        assertWithMessage("unexpected result")
            .that(previousSibling.getNextSibling())
            .isEqualTo(newPreviousSibling);
        assertWithMessage("unexpected result")
            .that(parent.getFirstChild())
            .isEqualTo(previousSibling);
    }

    @Test
    public void testAddPreviousSiblingNullParent() {
        final DetailAstImpl child = new DetailAstImpl();
        final DetailAST newSibling = new DetailAstImpl();

        child.addPreviousSibling(newSibling);

        assertWithMessage("Invalid child token")
            .that(newSibling.getNextSibling())
            .isEqualTo(child);
        assertWithMessage("Invalid child token")
            .that(child.getPreviousSibling())
            .isEqualTo(newSibling);
    }

    @Test
    public void testInsertSiblingBetween() throws Exception {
        final DetailAstImpl root = new DetailAstImpl();
        final DetailAstImpl firstLevelA = new DetailAstImpl();
        final DetailAST firstLevelB = new DetailAstImpl();
        final DetailAST firstLevelC = new DetailAstImpl();

        assertWithMessage("Invalid child count")
            .that(root.getChildCount())
            .isEqualTo(0);

        root.setFirstChild(firstLevelA);
        final Method setParentMethod = getSetParentMethod();
        setParentMethod.invoke(firstLevelA, root);

        assertWithMessage("Invalid child count")
            .that(root.getChildCount())
            .isEqualTo(1);

        firstLevelA.addNextSibling(firstLevelB);
        setParentMethod.invoke(firstLevelB, root);

        assertWithMessage("Invalid next sibling")
            .that(firstLevelA.getNextSibling())
            .isEqualTo(firstLevelB);

        firstLevelA.addNextSibling(firstLevelC);
        setParentMethod.invoke(firstLevelC, root);

        assertWithMessage("Invalid next sibling")
            .that(firstLevelA.getNextSibling())
            .isEqualTo(firstLevelC);
    }

    @Test
    public void testBranchContains() {
        final DetailAstImpl root = createToken(null, TokenTypes.CLASS_DEF);
        final DetailAstImpl modifiers = createToken(root, TokenTypes.MODIFIERS);
        createToken(modifiers, TokenTypes.LITERAL_PUBLIC);

        assertWithMessage("invalid result")
                .that(root.branchContains(TokenTypes.LITERAL_PUBLIC))
                .isTrue();
        assertWithMessage("invalid result")
                .that(root.branchContains(TokenTypes.OBJBLOCK))
                .isFalse();
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
                    TestUtil.invokeMethod(child, "setParent", ast);
                }
                // -@cs[IllegalCatch] Cannot avoid catching it.
                catch (Exception exception) {
                    throw new IllegalStateException(exception);
                }
            }
        );

        for (Consumer<DetailAstImpl> method : clearBranchTokenTypesMethods) {
            final BitSet branchTokenTypes = TestUtil.invokeMethod(parent, "getBranchTokenTypes");
            method.accept(null);
            final BitSet branchTokenTypes2 = TestUtil.invokeMethod(parent, "getBranchTokenTypes");
            assertWithMessage("Branch token types are not equal")
                .that(branchTokenTypes)
                .isEqualTo(branchTokenTypes2);
            assertWithMessage("Branch token types should not be the same")
                .that(branchTokenTypes)
                .isNotSameInstanceAs(branchTokenTypes2);
        }
    }

    @Test
    public void testCacheBranchTokenTypes() {
        final DetailAST root = new DetailAstImpl();
        final BitSet bitSet = new BitSet();
        bitSet.set(999);

        TestUtil.setInternalState(root, "branchTokenTypes", bitSet);
        assertWithMessage("Branch tokens has changed")
                .that(root.branchContains(999))
                .isTrue();
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
            final int intermediateCount = TestUtil.getInternalState(parent, "childCount");
            final int finishCount = parent.getChildCount();
            assertWithMessage("Child count has changed")
                .that(finishCount)
                .isEqualTo(startCount);
            assertWithMessage("Invalid child count")
                .that(intermediateCount)
                .isEqualTo(Integer.MIN_VALUE);
        }

        final int startCount = child.getChildCount();
        child.addChild(null);
        final int intermediateCount = TestUtil.getInternalState(child, "childCount");
        final int finishCount = child.getChildCount();
        assertWithMessage("Child count has changed")
            .that(finishCount)
            .isEqualTo(startCount);
        assertWithMessage("Invalid child count")
            .that(intermediateCount)
            .isEqualTo(Integer.MIN_VALUE);
    }

    @Test
    public void testCacheGetChildCount() {
        final DetailAST root = new DetailAstImpl();

        TestUtil.setInternalState(root, "childCount", 999);
        assertWithMessage("Child count has changed")
            .that(root.getChildCount())
            .isEqualTo(999);
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

        assertWithMessage("Invalid parent")
            .that(newSibling.getParent())
            .isEqualTo(parent);
        assertWithMessage("Invalid next sibling")
            .that(newSibling.getNextSibling())
            .isEqualTo(sibling);
        assertWithMessage("Invalid child")
            .that(child.getNextSibling())
            .isEqualTo(newSibling);
    }

    @Test
    public void testAddNextSiblingNullParent() {
        final DetailAstImpl child = new DetailAstImpl();
        final DetailAstImpl newSibling = new DetailAstImpl();
        final DetailAstImpl oldParent = new DetailAstImpl();
        oldParent.addChild(newSibling);
        child.addNextSibling(newSibling);

        assertWithMessage("Invalid parent")
            .that(newSibling.getParent())
            .isEqualTo(oldParent);
        assertWithMessage("Invalid next sibling")
            .that(newSibling.getNextSibling())
            .isNull();
        assertWithMessage("Invalid parent")
            .that(child.getNextSibling())
            .isSameInstanceAs(newSibling);
    }

    @Test
    public void testGetLineNo() {
        final DetailAstImpl root1 = new DetailAstImpl();
        root1.setLineNo(1);
        assertWithMessage("Invalid line number")
            .that(root1.getLineNo())
            .isEqualTo(1);

        final DetailAstImpl root2 = new DetailAstImpl();
        final DetailAstImpl firstChild = new DetailAstImpl();
        firstChild.setLineNo(2);
        root2.setFirstChild(firstChild);
        assertWithMessage("Invalid line number")
            .that(root2.getLineNo())
            .isEqualTo(2);

        final DetailAstImpl root3 = new DetailAstImpl();
        final DetailAstImpl nextSibling = new DetailAstImpl();
        nextSibling.setLineNo(3);
        root3.setNextSibling(nextSibling);
        assertWithMessage("Invalid line number")
            .that(root3.getLineNo())
            .isEqualTo(3);

        final DetailAstImpl root4 = new DetailAstImpl();
        final DetailAstImpl comment = new DetailAstImpl();
        comment.setType(TokenTypes.SINGLE_LINE_COMMENT);
        comment.setLineNo(3);
        root4.setFirstChild(comment);
        assertWithMessage("Invalid line number")
            .that(root4.getLineNo())
            .isEqualTo(Integer.MIN_VALUE);
    }

    @Test
    public void testGetColumnNo() {
        final DetailAstImpl root1 = new DetailAstImpl();
        root1.setColumnNo(1);
        assertWithMessage("Invalid column number")
            .that(root1.getColumnNo())
            .isEqualTo(1);

        final DetailAstImpl root2 = new DetailAstImpl();
        final DetailAstImpl firstChild = new DetailAstImpl();
        firstChild.setColumnNo(2);
        root2.setFirstChild(firstChild);
        assertWithMessage("Invalid column number")
            .that(root2.getColumnNo())
            .isEqualTo(2);

        final DetailAstImpl root3 = new DetailAstImpl();
        final DetailAstImpl nextSibling = new DetailAstImpl();
        nextSibling.setColumnNo(3);
        root3.setNextSibling(nextSibling);
        assertWithMessage("Invalid column number")
            .that(root3.getColumnNo())
            .isEqualTo(3);

        final DetailAstImpl root4 = new DetailAstImpl();
        final DetailAstImpl comment = new DetailAstImpl();
        comment.setType(TokenTypes.SINGLE_LINE_COMMENT);
        comment.setColumnNo(3);
        root4.setFirstChild(comment);
        assertWithMessage("Invalid column number")
            .that(root4.getColumnNo())
            .isEqualTo(Integer.MIN_VALUE);
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

        assertWithMessage("Invalid result")
            .that(firstChild.findFirstToken(TokenTypes.IDENT))
            .isNull();
        final DetailAST ident = root.findFirstToken(TokenTypes.IDENT);
        assertWithMessage("Invalid result")
            .that(ident)
            .isEqualTo(firstChild);
        final DetailAST expr = root.findFirstToken(TokenTypes.EXPR);
        assertWithMessage("Invalid result")
            .that(expr)
            .isEqualTo(secondChild);
        assertWithMessage("Invalid result")
            .that(root.findFirstToken(0))
            .isNull();
    }

    @Test
    public void testManyComments() throws Exception {
        final File file = new File(temporaryFolder, "InputDetailASTManyComments.java");

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

            assertWithMessage("file must return a root node: " + fileName)
                .that(rootAST)
                .isNotNull();

            assertWithMessage("tree is valid")
                    .that(checkTree(fileName, rootAST))
                    .isTrue();
        }
    }

    @Test
    public void testToString() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setText("text");
        ast.setColumnNo(0);
        ast.setLineNo(0);
        assertWithMessage("Invalid text")
            .that(ast.toString())
            .isEqualTo("text[0x0]");
    }

    private static List<File> getAllFiles(File dir) {
        final List<File> result = new ArrayList<>();

        dir.listFiles(file -> {
            if (file.isDirectory()) {
                result.addAll(getAllFiles(file));
            }
            else if (file.getName().endsWith(".java")
                    && !NO_ROOT_FILES.contains(file.getName())) {
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
        assertWithMessage(badParentMsg)
            .that(node.getParent())
            .isEqualTo(parent);
        final MessageFormat badPrevFormatter = new MessageFormat(
                "Bad prev node={0} prev={2} parent={1} filename={3} root={4}", Locale.ROOT);
        final String badPrevMsg = badPrevFormatter.format(params);
        assertWithMessage(badPrevMsg)
            .that(node.getPreviousSibling())
            .isEqualTo(prev);
    }

}
