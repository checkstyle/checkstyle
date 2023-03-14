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

import static com.google.common.truth.Truth.assertWithMessage;

import java.awt.Window;
import java.util.Arrays;

import javax.swing.SwingUtilities;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.puppycrawl.tools.checkstyle.AbstractGuiTestSupport;

public class MainTest extends AbstractGuiTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/gui/main";
    }

    /**
     * Parametrized test to invoke Checkstyle GUI with different command line arguments.
     *
     * @param argList the command line arguments delimited by a semicolon (";")
     * @throws Exception if an exception occurs while executing the test.
     */
    @ParameterizedTest
    @ValueSource(strings = {";", "InputMain.java"})
    public void testMain(String argList) throws Exception {
        final String[] args = argList.split(";");
        for (int i = 0; i < args.length; i++) {
            args[i] = getPath(args[i]);
        }

        // Create the main window
        Main.main(args);

        SwingUtilities.invokeAndWait(() -> {
            // Close the main window
            final long mainFrameCount = Arrays.stream(Window.getWindows())
                    .filter(wnd -> wnd instanceof MainFrame && wnd.isVisible())
                    .peek(Window::dispose)
                    .count();
            assertWithMessage("Only one window is expected")
                    .that(mainFrameCount)
                    .isEqualTo(1);
        });
    }

}
