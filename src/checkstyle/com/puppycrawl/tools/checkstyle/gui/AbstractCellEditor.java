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

package com.puppycrawl.tools.checkstyle.gui;

import java.util.EventObject;
import javax.swing.CellEditor;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;

/**
 * Abstract implementation of a CellEditor.
 *
 * @author http://java.sun.com/products/jfc/tsc/articles/treetable2
 */
public class AbstractCellEditor implements CellEditor
{
    private final EventListenerList mListenerList = new EventListenerList();

    /** @see CellEditor */
    public Object getCellEditorValue()
    {
        return null;
    }

    /** @see CellEditor */
    public boolean isCellEditable(EventObject e)
    {
        return true;
    }

    /** @see CellEditor */
    public boolean shouldSelectCell(EventObject anEvent)
    {
        return false;
    }

    /** @see CellEditor */
    public boolean stopCellEditing()
    {
        return true;
    }

    /** @see CellEditor */
    public void cancelCellEditing()
    {
    }

    /** @see CellEditor */
    public void addCellEditorListener(CellEditorListener l)
    {
        mListenerList.add(CellEditorListener.class, l);
    }

    /** @see CellEditor */
    public void removeCellEditorListener(CellEditorListener l)
    {
        mListenerList.remove(CellEditorListener.class, l);
    }

    /*
     * Notify all listeners that have registered interest for
     * notification on this event type.
     * @see EventListenerList
     */
    protected void fireEditingStopped()
    {
        // Guaranteed to return a non-null array
        final Object[] listeners = mListenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == CellEditorListener.class) {
                ((CellEditorListener) listeners[i + 1]).editingStopped(new ChangeEvent(this));
            }
        }
    }

    /*
     * Notify all listeners that have registered interest for
     * notification on this event type.
     * @see EventListenerList
     */
    protected void fireEditingCanceled()
    {
        // Guaranteed to return a non-null array
        final Object[] listeners = mListenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == CellEditorListener.class) {
                ((CellEditorListener) listeners[i + 1]).editingCanceled(new ChangeEvent(this));
            }
        }
    }
}
