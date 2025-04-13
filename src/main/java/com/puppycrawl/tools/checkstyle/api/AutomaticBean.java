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

package com.puppycrawl.tools.checkstyle.api;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;

/**
 * A Java Bean that implements the component lifecycle interfaces by
 * calling the bean's setters for all configuration attributes.
 *
 * @noinspection AbstractClassNeverImplemented
 * @noinspectionreason All files now use {@code AbstractAutomaticBean}.
 *     We will remove this class in #12873.
 * @deprecated since 10.9.3. Use {@code AbstractAutomaticBean} instead.
 */
// -@cs[AbstractClassName] We can not break compatibility with previous versions.
@Deprecated(since = "10.9.3")
public abstract class AutomaticBean extends AbstractAutomaticBean {
    /**
     * Enum to specify behaviour regarding ignored modules.
     */
    public enum OutputStreamOptions {

        /**
         * Close stream in the end.
         */
        CLOSE,

        /**
         * Do nothing in the end.
         */
        NONE,

    }
}
