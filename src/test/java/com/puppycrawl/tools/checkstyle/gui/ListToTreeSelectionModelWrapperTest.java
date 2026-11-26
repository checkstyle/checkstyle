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

package com.puppycrawl.tools.checkstyle.gui;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;

import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.tree.TreePath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractGuiTestSupport;

public class ListToTreeSelectionModelWrapperTest extends AbstractGuiTestSupport {

    private static final String TEST_FILE_NAME = "InputTreeTable.java";

    private JTree tree;

    private ListToTreeSelectionModelWrapper selectionModel;

    private ListSelectionModel listSelectionModel;

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/gui/treetable";
    }

    @BeforeEach
    public void prepare() throws Exception {
        final MainFrameModel model = new MainFrameModel();
        model.openFile(new File(getPath(TEST_FILE_NAME)));
        final TreeTable treeTable = new TreeTable(model.getParseTreeTableModel());
        tree = treeTable.getTree();
        tree.expandRow(0);
        selectionModel = (ListToTreeSelectionModelWrapper) tree.getSelectionModel();
        listSelectionModel = selectionModel.getListSelectionModel();
    }

    @Test
    public void testSelectedRowsSelectTreePaths() {
        listSelectionModel.setSelectionInterval(0, 0);
        listSelectionModel.addSelectionInterval(2, 2);

        assertWithMessage("Only the selected rows should have selected paths")
                .that(selectionModel.getSelectionPaths())
                .asList()
                .containsExactly(tree.getPathForRow(0), tree.getPathForRow(2));
    }

    @Test
    public void testClearedRowsClearTreePaths() {
        listSelectionModel.setSelectionInterval(0, 1);

        listSelectionModel.clearSelection();

        assertWithMessage("No path should be selected after clearing the rows")
                .that(selectionModel.getSelectionCount())
                .isEqualTo(0);
    }

    @Test
    public void testRowsWithoutPathAreIgnored() {
        final int rowCount = tree.getRowCount();

        listSelectionModel.setSelectionInterval(0, rowCount + 10);

        assertWithMessage("Only rows of the tree should have selected paths")
                .that(selectionModel.getSelectionCount())
                .isEqualTo(rowCount);
    }

    @Test
    public void testSelectedPathSelectsRow() {
        final TreePath path = tree.getPathForRow(1);

        selectionModel.setSelectionPath(path);

        assertWithMessage("The row of the selected path should be selected")
                .that(listSelectionModel.isSelectedIndex(1))
                .isTrue();
        assertWithMessage("Only one row should be selected")
                .that(listSelectionModel.getMinSelectionIndex())
                .isEqualTo(listSelectionModel.getMaxSelectionIndex());
        assertWithMessage("The selected path should stay selected")
                .that(selectionModel.getSelectionPath())
                .isEqualTo(path);
    }

}
