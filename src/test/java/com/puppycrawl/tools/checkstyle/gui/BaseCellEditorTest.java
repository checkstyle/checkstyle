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

import java.util.EventObject;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import org.junit.jupiter.api.Test;

public class BaseCellEditorTest {

    @Test
    public void testToString() {

        final BaseCellEditor cellEditor = new BaseCellEditor();

        assertWithMessage("Should return null")
                .that(cellEditor.getCellEditorValue() == null)
                .isTrue();
    }

    @Test
    public void testStopCellEditing() {

        final BaseCellEditor cellEditor = new BaseCellEditor();

        assertWithMessage("Should be true")
                .that(cellEditor.stopCellEditing())
                .isTrue();
    }

    @Test
    public void testFireEditingStoppedAndCanceled() {

        final BaseCellEditor cellEditor = new BaseCellEditor();

        final boolean[] cellEditorListenerStopped = {false};
        final boolean[] cellEditorListenerCanceled = {false};

        final CellEditorListener cellEditorListener1 = new CellEditorListener() {

            @Override
            public void editingStopped(ChangeEvent e) {
                cellEditorListenerStopped[0] = true;
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
                cellEditorListenerCanceled[0] = true;
            }

        };

        cellEditor.addCellEditorListener(cellEditorListener1);
        cellEditor.fireEditingStopped();
        assertWithMessage("Editor listener should be stopped")
                .that(cellEditorListenerStopped[0])
                .isTrue();
        cellEditor.fireEditingCanceled();
        assertWithMessage("Editor listener should be canceled")
                .that(cellEditorListenerCanceled[0])
                .isTrue();

    }

    @Test
    public void testIsCellEditable() {
        final BaseCellEditor cellEditor = new BaseCellEditor();

        assertWithMessage("Cell should be editable")
                .that(cellEditor.isCellEditable(new EventObject(this)))
                .isTrue();
    }

    @Test
    public void testShouldSelectCell() {
        final BaseCellEditor cellEditor = new BaseCellEditor();

        assertWithMessage("Cell should not be selected")
                .that(cellEditor.shouldSelectCell(new EventObject(this)))
                .isFalse();
    }

    @Test
    public void testCancelCellEditingDoesNotNotifyListeners() {
        final BaseCellEditor cellEditor = new BaseCellEditor();
        final EditingEventCounter listener = new EditingEventCounter();
        cellEditor.addCellEditorListener(listener);

        cellEditor.cancelCellEditing();

        assertWithMessage("Cancelling should not notify listeners as the tree is read-only")
                .that(listener.getCanceledCount())
                .isEqualTo(0);
    }

    @Test
    public void testRemovedCellEditorListenerIsNotNotified() {
        final BaseCellEditor cellEditor = new BaseCellEditor();
        final EditingEventCounter removedListener = new EditingEventCounter();
        final EditingEventCounter remainingListener = new EditingEventCounter();
        cellEditor.addCellEditorListener(removedListener);
        cellEditor.addCellEditorListener(remainingListener);
        cellEditor.removeCellEditorListener(removedListener);

        cellEditor.fireEditingStopped();
        cellEditor.fireEditingCanceled();

        assertWithMessage("Removed listener should not be notified about stopping")
                .that(removedListener.getStoppedCount())
                .isEqualTo(0);
        assertWithMessage("Removed listener should not be notified about cancelling")
                .that(removedListener.getCanceledCount())
                .isEqualTo(0);
        assertWithMessage("Remaining listener should be notified about stopping")
                .that(remainingListener.getStoppedCount())
                .isEqualTo(1);
        assertWithMessage("Remaining listener should be notified about cancelling")
                .that(remainingListener.getCanceledCount())
                .isEqualTo(1);
    }

    /**
     * Cell editor listener that counts editing events.
     */
    private static final class EditingEventCounter implements CellEditorListener {

        private int stoppedCount;

        private int canceledCount;

        @Override
        public void editingStopped(ChangeEvent event) {
            stoppedCount++;
        }

        @Override
        public void editingCanceled(ChangeEvent event) {
            canceledCount++;
        }

        private int getStoppedCount() {
            return stoppedCount;
        }

        private int getCanceledCount() {
            return canceledCount;
        }

    }

}
