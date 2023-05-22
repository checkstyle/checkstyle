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

package com.puppycrawl.tools.checkstyle.checks.imports;

/**
 * Represents the policy for checking import order statements.
 *
 * @see ImportOrderCheck
 */
public enum ImportOrderOption {

    /**
     * Represents the policy that static imports are all at the top.
     * For example:
     *
     * <pre>
     *  import static java.awt.Button.ABORT;
     *  import static java.io.File.createTempFile;
     *  import static javax.swing.WindowConstants.*;
     *
     *  import java.awt.Button;
     *  import java.awt.event.ActionEvent;
     * </pre>
     */
    TOP,

    /**
     * Represents the policy that static imports are above the local group.
     * For example:
     *
     * <pre>
     *  import static java.awt.Button.A;
     *  import static javax.swing.WindowConstants.*;
     *  import java.awt.Dialog;
     *  import javax.swing.JComponent;
     *
     *  import static java.io.File.createTempFile;
     *  import java.io.File;
     *  import java.io.IOException;
     * </pre>
     */
    ABOVE,

    /**
     * Represents the policy that static imports are processed like non static
     * imports. For example:
     *
     * <pre>
     *  import java.awt.Button;
     *  import static java.awt.Button.ABORT;
     *  import java.awt.Dialog;
     *
     *  import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
     *  import javax.swing.JComponent;
     * </pre>
     */
    INFLOW,

    /**
     * Represents the policy that static imports are under the local group.
     * For example:
     *
     * <pre>
     *  import java.awt.Dialog;
     *  import javax.swing.JComponent;
     *  import static java.awt.Button.A;
     *  import static javax.swing.WindowConstants.*;
     *
     *  import java.io.File;
     *  import java.io.IOException;
     *  import static java.io.File.createTempFile;
     * </pre>
     */
    UNDER,

    /**
     * Represents the policy that static imports are all at the bottom.
     * For example:
     *
     * <pre>
     *  import java.awt.Button;
     *  import java.awt.event.ActionEvent;
     *
     *  import static java.awt.Button.ABORT;
     *  import static java.io.File.createTempFile;
     *  import static javax.swing.WindowConstants.*;
     * </pre>
     */
    BOTTOM,

}
