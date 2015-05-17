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

import java.awt.*;
import java.io.File;

import javax.swing.JFrame;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Entry point for starting the checkstyle GUI.
 */
public class Main {
    /**
     * Main frame
     */
    static JFrame frame;

    /**
     * Entry point
     */
    public static void main(String... args) {
        frame = new JFrame("CheckStyle");
        final ParseTreeInfoPanel panel = new ParseTreeInfoPanel();
        frame.getContentPane().add(panel);
        if (args.length >= 1) {
            final File f = new File(args[0]);
            panel.openFile(f, frame);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Runnable runner = new FrameShower(frame);
        EventQueue.invokeLater(runner);
    }

    /**
     * Method is used for testing inthe past
     * @param ast
     */
    public static void displayAst(DetailAST ast) {
        JFrame frame = new JFrame("CheckStyle");
        final ParseTreeInfoPanel panel = new ParseTreeInfoPanel();
        frame.getContentPane().add(panel);
        panel.openAst(ast, frame);
        frame.setSize(1500, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * http://findbugs.sourceforge.net/bugDescriptions.html#SW_SWING_METHODS_INVOKED_IN_SWING_THREAD
     */
    private static class FrameShower implements Runnable {
        /**
         * frame
         */
        final JFrame frame;

        /**
         * contstructor
         */
        public FrameShower(JFrame frame) {
            this.frame = frame;
        }

        /**
         * display a frame
         */
        public void run() {
            frame.pack();
            frame.setVisible(true);
        }
    }
}
