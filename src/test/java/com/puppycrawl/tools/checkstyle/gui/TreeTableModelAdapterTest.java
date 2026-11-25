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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractGuiTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class TreeTableModelAdapterTest extends AbstractGuiTestSupport {

    private static final String TEST_FILE_NAME = "InputTreeTable.java";

    private RecordingParseTreeTableModel parseTreeTableModel;

    private JTree tree;

    private TreeTableModelAdapter adapter;

    private List<TableModelEvent> tableEvents;

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/gui/treetable";
    }

    @BeforeEach
    public void prepare() throws Exception {
        final DetailAST rootAst = JavaParser.parseFile(new File(getPath(TEST_FILE_NAME)),
                JavaParser.Options.WITHOUT_COMMENTS);
        parseTreeTableModel = new RecordingParseTreeTableModel(rootAst);
        tree = new JTree(parseTreeTableModel);
        adapter = new TreeTableModelAdapter(parseTreeTableModel, tree);
        tableEvents = new ArrayList<>();
        adapter.addTableModelListener(tableEvents::add);
    }

    @Test
    public void testColumnsAreDelegatedToTreeTableModel() {
        assertWithMessage("Invalid column count")
                .that(adapter.getColumnCount())
                .isEqualTo(parseTreeTableModel.getColumnCount());
        for (int column = 0; column < adapter.getColumnCount(); column++) {
            assertWithMessage("Invalid column name")
                    .that(adapter.getColumnName(column))
                    .isEqualTo(parseTreeTableModel.getColumnName(column));
            assertWithMessage("Invalid column class")
                    .that(adapter.getColumnClass(column))
                    .isEqualTo(parseTreeTableModel.getColumnClass(column));
            assertWithMessage("Invalid cell editable state")
                    .that(adapter.isCellEditable(0, column))
                    .isEqualTo(parseTreeTableModel.isCellEditable(column));
        }
    }

    @Test
    public void testRowsFollowTreeRows() {
        final DetailAST root = (DetailAST) parseTreeTableModel.getRoot();
        assertWithMessage("The root and its children should be visible initially")
                .that(adapter.getRowCount())
                .isEqualTo(1 + parseTreeTableModel.getChildCount(root));
        assertWithMessage("Invalid value of the root row")
                .that(adapter.getValueAt(0, 0))
                .isEqualTo(parseTreeTableModel.getValueAt(root, 0));
        final Object firstChild = parseTreeTableModel.getChild(root, 0);
        assertWithMessage("Invalid value of the first child row")
                .that(adapter.getValueAt(1, 0))
                .isEqualTo(parseTreeTableModel.getValueAt(firstChild, 0));

        tree.collapseRow(0);
        assertWithMessage("Only the root row should be visible after collapsing")
                .that(adapter.getRowCount())
                .isEqualTo(1);
    }

    @Test
    public void testTreeExpansionFiresTableDataChanged() {
        tree.collapseRow(0);
        assertWithMessage("Collapsing the tree should fire a table change")
                .that(tableEvents)
                .hasSize(1);
        tree.expandRow(0);
        assertWithMessage("Expanding the tree should fire a table change")
                .that(tableEvents)
                .hasSize(2);
        assertWithMessage("The whole table should be reported as changed")
                .that(tableEvents.get(1).getFirstRow())
                .isEqualTo(0);
    }

    @Test
    public void testTreeModelChangesFireTableDataChangedLater() throws Exception {
        final TreeModelListener listener = parseTreeTableModel.getListeners().stream()
                .filter(TreeTableModelAdapterTest::isRegisteredByAdapter)
                .findFirst()
                .orElseThrow();
        final TreeModelEvent event =
                new TreeModelEvent(parseTreeTableModel, new Object[] {tree.getModel().getRoot()});

        listener.treeNodesChanged(event);
        listener.treeNodesInserted(event);
        listener.treeNodesRemoved(event);
        listener.treeStructureChanged(event);

        SwingUtilities.invokeAndWait(TreeTableModelAdapterTest::waitForEventQueue);
        assertWithMessage("Each tree model change should fire a table change")
                .that(tableEvents)
                .hasSize(4);
    }

    /**
     * Checks whether the listener was registered by {@link TreeTableModelAdapter}.
     *
     * @param listener the listener to check
     * @return true if the listener is an inner class of the adapter
     */
    private static boolean isRegisteredByAdapter(TreeModelListener listener) {
        return listener.getClass().getEnclosingClass() == TreeTableModelAdapter.class;
    }

    /**
     * Does nothing. It is run on the event queue to wait for the events queued before it.
     */
    private static void waitForEventQueue() {
        // no code by default
    }

    /**
     * Tree table model that records the listeners added to it.
     */
    private static final class RecordingParseTreeTableModel extends ParseTreeTableModel {

        private final List<TreeModelListener> listeners = new ArrayList<>();

        private RecordingParseTreeTableModel(DetailAST parseTree) {
            super(parseTree);
        }

        @Override
        public void addTreeModelListener(TreeModelListener listener) {
            listeners.add(listener);
            super.addTreeModelListener(listener);
        }

        private List<TreeModelListener> getListeners() {
            return listeners;
        }

    }

}
