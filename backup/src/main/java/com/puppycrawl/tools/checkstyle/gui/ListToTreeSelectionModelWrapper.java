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

package com.puppycrawl.tools.checkstyle.gui;

import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;

/**
 * ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel
 * to listen for changes in the ListSelectionModel it maintains. Once
 * a change in the ListSelectionModel happens, the paths are updated
 * in the DefaultTreeSelectionModel.
 *
 */
class ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel {

    /** A unique serial version identifier. */
    private static final long serialVersionUID = 2267930983939339510L;
    /** TreeTable to perform updates on. */
    private final TreeTable treeTable;
    /** Set to true when we are updating the ListSelectionModel. */
    private boolean updatingListSelectionModel;

    /**
     * Constructor to initialise treeTable.
     *
     * @param jTreeTable TreeTable to perform updates on.
     */
    /* package */ ListToTreeSelectionModelWrapper(TreeTable jTreeTable) {
        treeTable = jTreeTable;
        getListSelectionModel().addListSelectionListener(event -> {
            updateSelectedPathsFromSelectedRows();
        });
    }

    /**
     * Returns the list selection model. ListToTreeSelectionModelWrapper
     * listens for changes to this model and updates the selected paths
     * accordingly.
     *
     * @return the list selection model
     */
    protected final ListSelectionModel getListSelectionModel() {
        return listSelectionModel;
    }

    /**
     * This is overridden to set {@code updatingListSelectionModel}
     * and message super. This is the only place DefaultTreeSelectionModel
     * alters the ListSelectionModel.
     */
    @Override
    public void resetRowSelection() {
        if (!updatingListSelectionModel) {
            updatingListSelectionModel = true;
            try {
                super.resetRowSelection();
            }
            finally {
                updatingListSelectionModel = false;
            }
        }
        // Notice how we don't message super if
        // updatingListSelectionModel is true. If
        // updatingListSelectionModel is true, it implies the
        // ListSelectionModel has already been updated and the
        // paths are the only thing that needs to be updated.
    }

    /**
     * If {@code updatingListSelectionModel} is false, this will
     * reset the selected paths from the selected rows in the list
     * selection model.
     */
    private void updateSelectedPathsFromSelectedRows() {
        if (!updatingListSelectionModel) {
            updatingListSelectionModel = true;
            try {
                // This is way expensive, ListSelectionModel needs an
                // enumerator for iterating.
                final int min = listSelectionModel.getMinSelectionIndex();
                final int max = listSelectionModel.getMaxSelectionIndex();

                clearSelection();
                if (min != -1 && max != -1) {
                    for (int counter = min; counter <= max; counter++) {
                        updateSelectedPathIfRowIsSelected(counter);
                    }
                }
            }
            finally {
                updatingListSelectionModel = false;
            }
        }
    }

    /**
     * If the row at given index is selected, selected paths are updated.
     *
     * @param counter number of row.
     */
    private void updateSelectedPathIfRowIsSelected(int counter) {
        if (listSelectionModel.isSelectedIndex(counter)) {
            final TreePath selPath = treeTable.getTree().getPathForRow(counter);

            if (selPath != null) {
                addSelectionPath(selPath);
            }
        }
    }

}
