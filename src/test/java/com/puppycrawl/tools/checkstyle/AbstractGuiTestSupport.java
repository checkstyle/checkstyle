///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle;

import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;

import org.junit.jupiter.api.BeforeEach;

/**
 * Abstract base class for testing GUI components.
 */
public abstract class AbstractGuiTestSupport extends AbstractPathTestSupport {

    /**
     * Validates the graphics environment.
     */
    @BeforeEach
    public void validateGraphicsEnvironment() {
        final boolean isHeadless = GraphicsEnvironment.isHeadless();
        assumeFalse(isHeadless, "This test is incompatible with headless environment");
    }

    /**
     * Helper method to find a component by its name.
     *
     * @param root the root component to start search
     * @param name the name of component to find
     * @param <T> the type of component to find
     * @return the component if found, {@code null} otherwise
     * @noinspection unchecked
     * @noinspectionreason unchecked - we know that any component is OK to typecast to T
     */
    protected static <T extends Component> T findComponentByName(Component root, String name) {
        Component result = null;
        if (name.equals(root.getName())) {
            result = root;
        }
        else if (root instanceof Container) {
            final Component[] children = ((Container) root).getComponents();
            for (Component component : children) {
                result = findComponentByName(component, name);
                if (result != null) {
                    break;
                }
            }
        }
        return (T) result;
    }
}
