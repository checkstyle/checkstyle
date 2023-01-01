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

import java.io.File;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Entry point for starting the checkstyle GUI.
 *
 */
public final class Main {

    /** Hidden constructor of the current utility class. */
    private Main() {
        // no code
    }

    /**
     * Entry point.
     *
     * @param args the command line arguments.
     */
    public static void main(final String... args) {
        SwingUtilities.invokeLater(() -> {
            createMainFrame(args);
        });
    }

    /**
     * Helper method to create the {@code MainFrame} instance.
     *
     * @param args the command line arguments
     */
    private static void createMainFrame(String... args) {
        final MainFrame mainFrame = new MainFrame();
        if (args.length > 0) {
            final File sourceFile = new File(args[0]);
            mainFrame.openFile(sourceFile);
        }
        mainFrame.setTitle("Checkstyle GUI");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

}
