////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
import com.puppycrawl.tools.checkstyle.TreeWalker;
import java.io.File;
import java.io.FileFilter;
import java.text.MessageFormat;
import org.junit.Test;

/**
 * TestCase to check DetailAST.
 * @author Oliver Burn
 */
public class DetailASTTest
{
    @Test
    public void testGetChildCount()
    {
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
    public void testTreeStructure() throws Exception
    {
        checkDir(new File(System.getProperty("testinputs.dir")));
    }

    private void checkDir(File dir) throws Exception
    {
        File[] files = dir.listFiles(new FileFilter() {
                public boolean accept(File file)
                {
                    return (file.getName().endsWith(".java")
                            || file.isDirectory())
                        && !file.getName().endsWith("InputGrammar.java");
                }
            });
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                checkFile(files[i].getCanonicalPath());
            }
            else if (files[i].isDirectory()) {
                checkDir(files[i]);
            }
        }
    }

    private void checkFile(String filename) throws Exception
    {
        final FileText text = new FileText(new File(filename),
                           System.getProperty("file.encoding", "UTF-8"));
        final FileContents contents = new FileContents(text);
        final DetailAST rootAST = TreeWalker.parse(contents);
        checkTree(rootAST, null, null, filename, rootAST);
    }

    private void checkTree(final DetailAST node,
                           final DetailAST parent,
                           final DetailAST prev,
                           final String filename,
                           final DetailAST root)
    {
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
