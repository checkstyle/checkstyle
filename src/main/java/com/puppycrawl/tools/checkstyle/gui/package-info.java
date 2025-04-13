////
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

/**
 * User interface classes for CheckStyle. Currently, this is mainly a gui to aid
 * Check developers.
 *
 * <p>This is definitely work in progress. Here are some ideas where
 * <strong>you</strong> could help:
 * <ul>
 *   <li>
 *     Add a read only editor field that highlights the selected AST node.
 *   </li>
 *   <li>
 *     Clicking in the editor field could open the corresponding tree path.
 *   </li>
 *   <li>
 *     Add a configuration GUI that can be used in all IDE plugins
 *     (well, err.., except Eclipse).
 *   </li>
 *   <li>
 *     Add ability to execute individual Checks and display the violation messages.
 *   </li>
 *   <li>
 *     Add a GUI for the
 *     {@link com.puppycrawl.tools.checkstyle.api.FileContents}.
 *   </li>
 *   <li>
 *     Add ability to define a Check by example (another one of those crazy
 *     ideas... :-)
 *   </li>
 * </ul>
 */
package com.puppycrawl.tools.checkstyle.gui;
