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

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractGuiTestSupport;

public class TreeTableCellRendererTest extends AbstractGuiTestSupport {

    private static final String TEST_FILE_NAME = "InputTreeTable.java";

    private MainFrameModel model;

    private TreeTable treeTable;

    private TreeTableCellRenderer renderer;

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/gui/treetable";
    }

    @BeforeEach
    public void prepare() throws Exception {
        model = new MainFrameModel();
        model.openFile(new File(getPath(TEST_FILE_NAME)));
        treeTable = new TreeTable(model.getParseTreeTableModel());
        renderer = (TreeTableCellRenderer) treeTable.getTree();
    }

    @Test
    public void testUpdateUiUsesTableSelectionColors() {
        renderer.updateUI();
        final DefaultTreeCellRenderer cellRenderer =
                (DefaultTreeCellRenderer) renderer.getCellRenderer();

        assertWithMessage("Border selection color should be removed")
                .that(cellRenderer.getBorderSelectionColor())
                .isNull();
        assertWithMessage("Text selection color should be the table one")
                .that(cellRenderer.getTextSelectionColor())
                .isEqualTo(UIManager.getColor("Table.selectionForeground"));
        assertWithMessage("Background selection color should be the table one")
                .that(cellRenderer.getBackgroundSelectionColor())
                .isEqualTo(UIManager.getColor("Table.selectionBackground"));
    }

    @Test
    public void testUpdateUiKeepsCustomCellRenderer() {
        final TreeCellRenderer customRenderer = new LabelTreeCellRenderer();
        renderer.setCellRenderer(customRenderer);

        renderer.updateUI();

        assertWithMessage("Custom cell renderer should be kept")
                .that(renderer.getCellRenderer())
                .isSameInstanceAs(customRenderer);
    }

    @Test
    public void testSetRowHeightIgnoresNonPositiveHeight() {
        final int rowHeight = renderer.getRowHeight();

        renderer.setRowHeight(0);

        assertWithMessage("Non-positive row height should be ignored")
                .that(renderer.getRowHeight())
                .isEqualTo(rowHeight);
    }

    @Test
    public void testSetRowHeightUpdatesTreeTable() {
        final int newRowHeight = treeTable.getRowHeight() + 5;

        renderer.setRowHeight(newRowHeight);

        assertWithMessage("Invalid tree row height")
                .that(renderer.getRowHeight())
                .isEqualTo(newRowHeight);
        assertWithMessage("Tree table row height should follow the tree")
                .that(treeTable.getRowHeight())
                .isEqualTo(newRowHeight);

        renderer.setRowHeight(newRowHeight);

        assertWithMessage("Tree table row height should stay the same")
                .that(treeTable.getRowHeight())
                .isEqualTo(newRowHeight);
    }

    @Test
    public void testSetRowHeightWithoutTreeTable() {
        final TreeTableCellRenderer standalone =
                new TreeTableCellRenderer(null, model.getParseTreeTableModel());

        standalone.setRowHeight(25);

        assertWithMessage("Row height should be set without a tree table")
                .that(standalone.getRowHeight())
                .isEqualTo(25);
    }

    @Test
    public void testSetBoundsUsesTreeTableHeight() {
        treeTable.setSize(300, 200);

        renderer.setBounds(5, 7, 100, 50);

        assertWithMessage("Invalid x position")
                .that(renderer.getX())
                .isEqualTo(5);
        assertWithMessage("Y position should always be 0")
                .that(renderer.getY())
                .isEqualTo(0);
        assertWithMessage("Invalid width")
                .that(renderer.getWidth())
                .isEqualTo(100);
        assertWithMessage("Height should be the tree table height")
                .that(renderer.getHeight())
                .isEqualTo(treeTable.getHeight());
    }

    @Test
    public void testPaintTranslatesToVisibleRow() {
        renderer.getTableCellRendererComponent(treeTable, null, false, false, 2, 0);
        final BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics = image.createGraphics();

        renderer.paint(graphics);

        assertWithMessage("Graphics should be translated to the visible row")
                .that(graphics.getTransform().getTranslateY())
                .isEqualTo(-2.0 * renderer.getRowHeight());
        graphics.dispose();
    }

    @Test
    public void testTableCellRendererComponentBackground() {
        final Component selected =
                renderer.getTableCellRendererComponent(treeTable, null, true, false, 0, 0);

        assertWithMessage("Renderer should return itself")
                .that(selected)
                .isSameInstanceAs(renderer);
        assertWithMessage("Selected row should use the table selection background")
                .that(renderer.getBackground())
                .isEqualTo(UIManager.getColor("Table.selectionBackground"));

        renderer.getTableCellRendererComponent(treeTable, null, false, false, 0, 0);

        assertWithMessage("Unselected row should use the table background")
                .that(renderer.getBackground())
                .isEqualTo(UIManager.getColor("Table.background"));
    }

    /**
     * Tree cell renderer that is not a {@link DefaultTreeCellRenderer}.
     */
    private static final class LabelTreeCellRenderer implements TreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean selected, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {
            return new JLabel();
        }

    }

}
