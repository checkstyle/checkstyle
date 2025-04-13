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

package com.puppycrawl.tools.checkstyle.grammar;

/**
 * This interface is used to be notified by parser about comments
 * in the parsed code.
 *
 * @noinspection ClassOnlyUsedInOnePackage
 * @noinspectionreason ClassOnlyUsedInOnePackage - we restrict all parsing to a single package
 */
public interface CommentListener {

    /**
     * Report the location of a single-line comment that extends from the
     * given point to the end of the line. The type of comment is identified
     * by a String whose value depends on the language being parsed, but would
     * typically be the delimiter for the comment.
     *
     * @param type an identifier for what type of comment it is.
     * @param startLineNo the starting line number
     * @param startColNo the starting column number
     */
    void reportSingleLineComment(String type,
                                 int startLineNo, int startColNo);

    /**
     * Report the location of a block comment that can span multiple lines.
     * The type of comment is identified by a String whose value depends on
     * the language being parsed, but would typically be the delimiter for the
     * comment.
     *
     * @param type an identifier for what type of comment it is.
     * @param startLineNo the starting line number
     * @param startColNo the starting column number
     * @param endLineNo the ending line number
     * @param endColNo the ending column number
     */
    void reportBlockComment(String type,
                            int startLineNo, int startColNo,
                            int endLineNo, int endColNo);

}
