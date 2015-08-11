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

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 * This class makes it easy to drag and drop files from the operating
 * system to a Java program. Any <tt>java.awt.Component</tt> can be
 * dropped onto, but only <tt>javax.swing.JComponent</tt>s will indicate
 * the drop event with a changed border.
 * <p/>
 * To use this class, construct a new <tt>FileDrop</tt> by passing
 * it the target component and a <tt>Listener</tt> to receive notification
 * when file(s) have been dropped. Here is an example:
 * <p/>
 * {@code <pre>
 *      JPanel myPanel = new JPanel();
 *      new FileDrop( myPanel, new FileDrop.Listener()
 *      {   public void filesDropped( java.io.File[] files )
 *          {
 *              // handle file drop
 *              ...
 *          }   // end filesDropped
 *      }); // end FileDrop.Listener
 * </pre>}
 * <p/>
 * You can specify the border that will appear when files are being dragged by
 * calling the constructor with a <tt>javax.swing.border.Border</tt>. Only
 * <tt>JComponent</tt>s will show any indication with a border.
 * <p/>
 *
 * <p>Original author: Robert Harder, rharder@usa.net</p>
 *
 * @author  Robert Harder
 * @author  Lars K?hne
 */
class FileDrop {

    /* Default border color */
    private static final Color DEFAULT_BORDER_COLOR = new Color(0.0f, 0.0f, 1.0f, 0.25f);

    private Border normalBorder;
    private final DropTargetListener dropListener;

    /**
     * Constructs a class with a default light-blue border
     * and, if <var>c</var> is a {@link Container}, recursively
     * sets all elements contained within as drop targets, though only
     * the top level container will change borders.
     *
     * @param component Component on which files will be dropped.
     * @param listener Listens for <tt>filesDropped</tt>.
     * @since 1.0
     */
    FileDrop(
            final Component component,
            final Listener listener)
            throws TooManyListenersException {
        this(component,
             BorderFactory.createMatteBorder(2, 2, 2, 2, DEFAULT_BORDER_COLOR),
             true,
             listener);
    }

    /**
     * Full constructor with a specified border and debugging optionally turned on.
     * With Debugging turned on, more status messages will be displayed to
     * <tt>out</tt>. A common way to use this constructor is with
     * <tt>System.out</tt> or <tt>System.err</tt>. A <tt>null</tt> value for
     * the parameter <tt>out</tt> will result in no debugging output.
     *
     * @param component Component on which files will be dropped.
     * @param dragBorder Border to use on <tt>JComponent</tt> when dragging occurs.
     * @param recursive Recursively set children as drop targets.
     * @param listener Listens for <tt>filesDropped</tt>.
     * @since 1.0
     */
    FileDrop(
            final Component component,
            final Border dragBorder,
            final boolean recursive,
            final Listener listener)
            throws TooManyListenersException {
        dropListener = new FileDropTargetListener(component, dragBorder, listener);
        makeDropTarget(component, recursive);
    }

    private void makeDropTarget(final Component component, boolean recursive)
            throws TooManyListenersException {
        // Make drop target
        final DropTarget dt = new DropTarget();
        dt.addDropTargetListener(dropListener);

        // Listen for hierarchy changes and remove the
        // drop target when the parent gets cleared out.
        component.addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent evt) {
                final Component parent = component.getParent();
                if (parent == null) {
                    component.setDropTarget(null);
                }
                else {
                    new DropTarget(component, dropListener);
                }
            }
        });

        if (component.getParent() != null) {
            new DropTarget(component, dropListener);
        }

        if (recursive && component instanceof Container) {
            final Container cont = (Container) component;
            final Component[] comps = cont.getComponents();
            for (Component element : comps) {
                makeDropTarget(element, recursive);
            }
        }
    }

    /**
     * Determine if the dragged data is a file list.
     *
     * @return True if the drag was ok
     */
    private static boolean isDragOk(final DropTargetDragEvent evt) {
        boolean ok = false;
        final DataFlavor[] flavors = evt.getCurrentDataFlavors();

        // See if any of the flavors are a file list
        int i = 0;
        while (!ok && i < flavors.length) {   // Is the flavor a file list?
            if (flavors[i].equals(DataFlavor.javaFileListFlavor)) {
                ok = true;
            }
            i++;
        }

        return ok;
    }

    /**
     * Removes the drag-and-drop hooks from the component and optionally
     * from the all children. You should call this if you add and remove
     * components after you've set up the drag-and-drop.
     * This will recursively unregister all components contained within
     * <var>c</var> if <var>c</var> is a {@link Container}.
     *
     * @param component The component to unregister as a drop target
     * @since 1.0
     */
    static void remove(Component component) {
        remove(component, true);
    }

    /**
     * Removes the drag-and-drop hooks from the component and optionally
     * from the all children. You should call this if you add and remove
     * components after you've set up the drag-and-drop.
     *
     * @param component The component to unregister
     * @param recursive Recursively unregister components within a container
     * @since 1.0
     */
    static void remove(Component component, boolean recursive) {
        component.setDropTarget(null);
        if (recursive && component instanceof Container) {
            final Component[] comps = ((Container) component).getComponents();
            for (Component element : comps) {
                remove(element, recursive);
            }
        }
    }

    /**
     * Implement this inner interface to listen for when files are dropped. For example
     * your class declaration may begin like this:
     * <code><pre>
     *      public class MyClass implements FileDrop.Listener
     *      ...
     *      public void filesDropped( File[] files )
     *      {
     *          ...
     *      }   // end filesDropped
     *      ...
     * </pre></code>
     *
     * @since 1.0
     */
    public interface Listener {
        /**
         * This method is called when files have been successfully dropped.
         *
         * @param files An array of <tt>File</tt>s that were dropped.
         * @since 1.0
         */
        void filesDropped(File... files);
    }

    private class FileDropTargetListener extends DropTargetAdapter {
        private final Component component;
        private final Border dragBorder;
        private final Listener listener;

        public FileDropTargetListener(Component component, Border dragBorder, Listener listener) {
            this.component = component;
            this.dragBorder = dragBorder;
            this.listener = listener;
        }

        @Override
        public void dragEnter(DropTargetDragEvent evt) {
            if (isDragOk(evt)) {
                if (component instanceof JComponent) {
                    final JComponent jc = (JComponent) component;
                    normalBorder = jc.getBorder();
                    jc.setBorder(dragBorder);
                }
                evt.acceptDrag(DnDConstants.ACTION_COPY);
            }
            else {
                evt.rejectDrag();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void drop(DropTargetDropEvent evt) {
            try {
                final Transferable tr = evt.getTransferable();

                if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);

                    final List<File> fileList = (List<File>) tr.getTransferData(
                            DataFlavor.javaFileListFlavor);
                    final File[] files = new File[fileList.size()];
                    fileList.toArray(files);

                    if (listener != null) {
                        listener.filesDropped(files);
                    }

                    evt.getDropTargetContext().dropComplete(true);
                }
                else {
                    evt.rejectDrop();
                }
            }
            catch (final IOException | UnsupportedFlavorException ignored) {
                evt.rejectDrop();
            }
            finally {
                if (component instanceof JComponent) {
                    final JComponent jc = (JComponent) component;
                    jc.setBorder(normalBorder);
                }
            }
        }

        @Override
        public void dragExit(DropTargetEvent evt) {
            if (component instanceof JComponent) {
                final JComponent jc = (JComponent) component;
                jc.setBorder(normalBorder);
            }
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent evt) {
            if (isDragOk(evt)) {
                evt.acceptDrag(DnDConstants.ACTION_COPY);
            }
            else {
                evt.rejectDrag();
            }
        }
    }

}
