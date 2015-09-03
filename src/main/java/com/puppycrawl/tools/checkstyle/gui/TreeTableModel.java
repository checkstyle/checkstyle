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

package com.puppycrawl.tools.checkstyle.gui;

import javax.swing.tree.TreeModel;

/**
 * TreeTableModel is the model used by a JTreeTable. It extends TreeModel
 * to add methods for getting information about the set of columns each
 * node in the TreeTableModel may have. Each column, like a column in
 * a TableModel, has a name and a type associated with it. Each node in
 * the TreeTableModel can return a value for each of the columns and
 * set that value if isCellEditable() returns true.
 *
 * <a href=
 * "http://docs.oracle.com/cd/E16162_01/apirefs.1112/e17493/oracle/ide/controls/TreeTableModel.html">
 * Original&nbsp;Source&nbsp;Location</a>
 *
 * @author Philip Milne
 * @author Scott Violet
 */
public interface TreeTableModel extends TreeModel {
    /**
     * @return the number of available column.
     */
    int getColumnCount();

    /**
     * @param column the column number
     * @return the name for column number {@code column}.
     */
    String getColumnName(int column);

    /**
     * @param column the column number
     * @return the type for column number {@code column}.
     */
    Class<?> getColumnClass(int column);

    /**
     * @param node the node
     * @param column the column number
     * @return the value to be displayed for node {@code node},
     *     at column number {@code column}.
     */
    Object getValueAt(Object node, int column);

    /**
     * Indicates whether the the value for node {@code node},
     * at column number {@code column} is editable.
     *
     * @param column the column number
     * @return true if editable
     */
    boolean isCellEditable(int column);
}
