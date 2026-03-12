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

import java.util.EventObject;

import javax.swing.CellEditor;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;

/**
 * A base class for CellEditors, providing default implementations for all
 * methods in the CellEditor interface and support for managing a series
 * of listeners.
 * <a href=
 * "https://docs.oracle.com/cd/E48246_01/apirefs.1111/e13403/oracle/ide/controls/TreeTableModel.html">
 * Original&nbsp;Source&nbsp;Location</a>
 *
 */
public class BaseCellEditor implements CellEditor {

    /**
     * A list of event listeners for the cell editor.
     */
    private final EventListenerList listenerList = new EventListenerList();

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public boolean isCellEditable(EventObject event) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return false;
    }

    @Override
    public boolean stopCellEditing() {
        return true;
    }

    @Override
    public void cancelCellEditing() {
        // No code, tree is read-only
    }

    @Override
    public void addCellEditorListener(CellEditorListener listener) {
        listenerList.add(CellEditorListener.class, listener);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener listener) {
        listenerList.remove(CellEditorListener.class, listener);
    }

    /**
     * Notifies all listeners that have registered interest in
     * 'editing stopped' event.
     *
     * @see EventListenerList
     */
    protected void fireEditingStopped() {
        notifyListeners(true);
    }

    /**
     * Notifies all listeners that have registered interest in
     * 'editing canceled' event.
     *
     * @see EventListenerList
     */
    protected void fireEditingCanceled() {
        notifyListeners(false);
    }

    /**
     * Notifies all registered CellEditorListeners.
     * Invokes editingStopped if stopped is true,
     * editingCanceled otherwise.
     * 
     * @param stopped true to notify listeners of editing stopped,
     *                false to notify of editing canceled
     */
    private void notifyListeners(boolean stopped) {
        final Object[] listeners = listenerList.getListenerList();
        for (int idx = listeners.length - 2; idx >= 0; idx -= 2) {
            if (listeners[idx] == CellEditorListener.class) {
                final CellEditorListener listener =
                        (CellEditorListener) listeners[idx + 1];
                if (stopped) {
                    listener.editingStopped(new ChangeEvent(this));
                }
                else {
                    listener.editingCanceled(new ChangeEvent(this));
                }
            }
        }
    }

}
