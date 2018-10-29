////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.gui.MainFrameModel.ParseMode;

public class CodeSelectorPresentationTest extends AbstractPathTestSupport {

    private MainFrameModel model;

    private DetailAST tree;

    private ImmutableList<Integer> linesToPosition;

    @Before
    public void loadFile() throws Exception {
        model = new MainFrameModel();
        model.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        model.openFile(new File(getPath("InputCodeSelectorPresentation.java")));
        tree = ((DetailAST) model.getParseTreeTableModel().getRoot())
                .getFirstChild().getNextSibling();
        linesToPosition = ImmutableList.copyOf(convertLinesToPosition(model.getLinesToPosition()));
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/gui/codeselectorpresentation";
    }

    /** Converts lineToPosition from multicharacter to one character line separator
      * needs to support crossplatform line separators.
      * @param systemLinesToPosition lines to position mapping for current system
      * @return lines to position mapping with one character line separator
      */
    private static List<Integer> convertLinesToPosition(List<Integer> systemLinesToPosition) {
        final List<Integer> convertedLinesToPosition = new ArrayList<>();
        final int lineSeparationCorrection = System.lineSeparator().length() - 1;
        convertedLinesToPosition.add(0, systemLinesToPosition.get(0));
        for (int i = 1; i < systemLinesToPosition.size(); i++) {
            convertedLinesToPosition.add(i,
                    systemLinesToPosition.get(i) - lineSeparationCorrection * (i - 1));
        }
        return convertedLinesToPosition;
    }

    @Test
    public void testDetailASTSelection() {
        final CodeSelectorPresentation selector = new CodeSelectorPresentation(tree,
                linesToPosition);
        selector.findSelectionPositions();
        Assert.assertEquals("Invalid selection start", 94, selector.getSelectionStart());
        Assert.assertEquals("Invalid selection end", 280, selector.getSelectionEnd());
    }

    @Test
    public void testDetailASTLeafSelection() {
        final DetailAST leaf = tree.getLastChild().getFirstChild();
        final CodeSelectorPresentation selector = new CodeSelectorPresentation(leaf,
                linesToPosition);
        selector.findSelectionPositions();
        Assert.assertEquals("Invalid selection start", 130, selector.getSelectionStart());
        Assert.assertEquals("Invalid selection end", 131, selector.getSelectionEnd());
    }

    @Test
    public void testDetailASTNoSelection() {
        final DetailAST leaf = tree.getFirstChild();
        final CodeSelectorPresentation selector = new CodeSelectorPresentation(leaf,
                linesToPosition);
        selector.findSelectionPositions();
        Assert.assertEquals("Invalid selection start", 94, selector.getSelectionStart());
        Assert.assertEquals("Invalid selection end", 94, selector.getSelectionEnd());
    }

    @Test
    public void testDetailNodeSelection() {
        final DetailNode javadoc = (DetailNode) model.getParseTreeTableModel()
                .getChild(tree.getFirstChild().getNextSibling().getFirstChild(), 0);
        final CodeSelectorPresentation selector = new CodeSelectorPresentation(javadoc,
                linesToPosition);
        selector.findSelectionPositions();
        Assert.assertEquals("Invalid selection start", 74, selector.getSelectionStart());
        Assert.assertEquals("Invalid selection end", 96, selector.getSelectionEnd());
    }

    @Test
    public void testDetailNodeLeafSelection() {
        final DetailNode javadoc = (DetailNode) model.getParseTreeTableModel()
                .getChild(tree.getFirstChild().getNextSibling().getFirstChild(), 0);
        final DetailNode javadocLeaf = javadoc.getChildren()[2];
        final CodeSelectorPresentation selector = new CodeSelectorPresentation(javadocLeaf,
                linesToPosition);
        selector.findSelectionPositions();
        Assert.assertEquals("Invalid selection start", 76, selector.getSelectionStart());
        Assert.assertEquals("Invalid selection end", 90, selector.getSelectionEnd());
    }

}
