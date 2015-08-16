////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileFilter;
import java.text.MessageFormat;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.TreeWalker;

/**
 * TestCase to check DetailAST.
 * @author Oliver Burn
 */
public class DetailASTTest {
    @Test
    public void testGetChildCount() {
        final DetailAST root = new DetailAST();
        final DetailAST firstLevelA = new DetailAST();
        final DetailAST firstLevelB = new DetailAST();
        final DetailAST secondLevelA = new DetailAST();

        root.setFirstChild(firstLevelA);

        firstLevelA.setParent(root);
        firstLevelA.setFirstChild(secondLevelA);
        firstLevelA.setNextSibling(firstLevelB);

        firstLevelB.setParent(root);

        secondLevelA.setParent(firstLevelA);

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
    public void testSetSiblingNull() {
        final DetailAST root = new DetailAST();
        final DetailAST firstLevelA = new DetailAST();

        root.setFirstChild(firstLevelA);

        assertEquals(1, root.getChildCount());

        firstLevelA.setParent(root);
        firstLevelA.addPreviousSibling(null);
        firstLevelA.addNextSibling(null);

        assertEquals(1, root.getChildCount());
    }

    @Test
    public void testInsertSiblingBetween() {
        final DetailAST root = new DetailAST();
        final DetailAST firstLevelA = new DetailAST();
        final DetailAST firstLevelB = new DetailAST();
        final DetailAST firstLevelC = new DetailAST();

        assertEquals(0, root.getChildCount());

        root.setFirstChild(firstLevelA);
        firstLevelA.setParent(root);

        assertEquals(1, root.getChildCount());

        firstLevelA.addNextSibling(firstLevelB);
        firstLevelB.setParent(root);

        assertEquals(firstLevelB, firstLevelA.getNextSibling());

        firstLevelA.addNextSibling(firstLevelC);
        firstLevelC.setParent(root);

        assertEquals(firstLevelC, firstLevelA.getNextSibling());
    }

    @Test
    public void testTreeStructure() throws Exception {
        checkDir(new File("src/test/resources/com/puppycrawl/tools/checkstyle"));
    }

    private void checkDir(File dir) throws Exception {
        File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return (file.getName().endsWith(".java")
                            || file.isDirectory())
                        && !file.getName().endsWith("InputGrammar.java");
                }
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
            checkTree(rootAST, null, null, filename, rootAST);
        }
    }

    private static void checkTree(final DetailAST node,
                           final DetailAST parent,
                           final DetailAST prev,
                           final String filename,
                           final DetailAST root) {
        Object[] params = new Object[] {
            node, parent, prev, filename, root,
        };
        String msg = MessageFormat.format(
            "Bad parent node={0} parent={1} filename={3} root={4}",
            params);
        assertEquals(msg, parent, node.getParent());
        msg = MessageFormat.format(
            "Bad prev node={0} prev={2} parent={1} filename={3} root={4}",
            params);
        assertEquals(msg, prev, node.getPreviousSibling());

        if (node.getFirstChild() != null) {
            checkTree(node.getFirstChild(), node, null,
                      filename, root);
        }
        if (node.getNextSibling() != null) {
            checkTree(node.getNextSibling(), parent, node,
                      filename, root);
        }
    }
}
