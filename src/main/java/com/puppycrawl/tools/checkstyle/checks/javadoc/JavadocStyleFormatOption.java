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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

/**
 * Represents the Javadoc comment format options for the JavadocStyle check.
 */
public enum JavadocStyleFormatOption {

    /**
     * Represents any Javadoc format. All Javadoc comments are checked
     * regardless of their format (traditional or markdown).
     * Example of traditional format:
     * <pre>
     * &#47;** Summary text. *&#47;
     * </pre>
     */
    ANY,

    /**
     * Represents the traditional Javadoc format using {@code /**} delimiters.
     * Example:
     * <pre>
     * &#47;**
     *  * Summary text.
     *  *&#47;
     * </pre>
     * Only traditional Javadoc comments are checked when this option is selected.
     *
     * <p>Note: until Markdown Javadoc AST support (JEP 467) is integrated into the
     * check pipeline, this option behaves identically to {@link #ANY} since the
     * underlying API only surfaces traditional {@code /**} block comments.
     */
    TRADITIONAL,

    /**
     * Represents the Markdown Javadoc format using {@code ///} line prefixes.
     * Example:
     * <pre>
     * /// Summary text.
     * </pre>
     * Only Markdown Javadoc comments are checked when this option is selected.
     * Note: requires Java 23+ Markdown Javadoc support (JEP 467).
     */
    MARKDOWN,

}
