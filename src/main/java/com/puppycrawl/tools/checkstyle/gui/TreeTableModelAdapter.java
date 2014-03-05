////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

/*
 * %W% %E%
 *
 * Copyright 1997, 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

package com.puppycrawl.tools.checkstyle.gui;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreePath;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

/**
 * This is a wrapper class takes a TreeTableModel and implements
 * the table model interface. The implementation is trivial, with
 * all of the event dispatching support provided by the superclass:
 * the AbstractTableModel.
 *
 * <a href="http://java.sun.com/products/jfc/tsc/articles/treetable1/index.html">Original&nbsp;Source&nbsp;Location</a>
 *
 * @author Philip Milne
 * @author Scott Violet
 */
public class TreeTableModelAdapter extends AbstractTableModel
{
    /** For Serialisation that will never happen. */
    private static final long serialVersionUID = 8269213416115369275L;
    private final JTree mTree;
    private final TreeTableModel mTreeTableModel;

    public TreeTableModelAdapter(TreeTableModel aTreeTableModel, JTree aTree)
    {
        this.mTree = aTree;
        this.mTreeTableModel = aTreeTableModel;

        aTree.addTreeExpansionListener(new TreeExpansionListener()
        {
            // Don't use fireTableRowsInserted() here; the selection model
            // would get updated twice.
            public void treeExpanded(TreeExpansionEvent event)
            {
                fireTableDataChanged();
            }

            public void treeCollapsed(TreeExpansionEvent event)
            {
                fireTableDataChanged();
            }
        });

        // Install a TreeModelListener that can update the table when
        // mTree changes. We use delayedFireTableDataChanged as we can
        // not be guaranteed the mTree will have finished processing
        // the event before us.
        aTreeTableModel.addTreeModelListener(new TreeModelListener()
        {
            public void treeNodesChanged(TreeModelEvent e)
            {
                delayedFireTableDataChanged();
            }

            public void treeNodesInserted(TreeModelEvent e)
            {
                delayedFireTableDataChanged();
            }

            public void treeNodesRemoved(TreeModelEvent e)
            {
                delayedFireTableDataChanged();
            }

            public void treeStructureChanged(TreeModelEvent e)
            {
                delayedFireTableDataChanged();
            }
        });
    }

    // Wrappers, implementing TableModel interface.

    public int getColumnCount()
    {
        return mTreeTableModel.getColumnCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColumnName(int column)
    {
        return mTreeTableModel.getColumnName(column);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getColumnClass(int column)
    {
        return mTreeTableModel.getColumnClass(column);
    }

    public int getRowCount()
    {
        return mTree.getRowCount();
    }

    protected Object nodeForRow(int row)
    {
        final TreePath treePath = mTree.getPathForRow(row);
        return treePath.getLastPathComponent();
    }

    public Object getValueAt(int row, int column)
    {
        return mTreeTableModel.getValueAt(nodeForRow(row), column);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return mTreeTableModel.isCellEditable(nodeForRow(row), column);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAt(Object value, int row, int column)
    {
        mTreeTableModel.setValueAt(value, nodeForRow(row), column);
    }

    /**
     * Invokes fireTableDataChanged after all the pending events have been
     * processed. SwingUtilities.invokeLater is used to handle this.
     */
    protected void delayedFireTableDataChanged()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                fireTableDataChanged();
            }
        });
    }
}

