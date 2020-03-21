////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.SortedMap;
import java.util.TreeMap;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Represents a set of abstract syntax tree.
 *
 */
public class DetailAstSet {

    /**
     * Maps line numbers to their ast.
     */
    private final SortedMap<Integer, DetailAST> astLines = new TreeMap<>();

    /**
     * Add ast to the set of ast.
     *
     * @param ast   the ast to add
     */
    public void addAst(DetailAST ast) {
        addLineWithAst(ast.getLineNo(), ast);
    }

    /**
     * Map ast with their line number.
     *
     * @param lineNo    line number of ast to add
     * @param ast       ast to add
     */
    private void addLineWithAst(int lineNo, DetailAST ast) {
        astLines.put(lineNo, ast);
    }

    /**
     * Get starting column number for the ast.
     *
     * @param lineNum the line number as key
     * @return start column for ast
     */
    public Integer getStartColumn(int lineNum) {
        Integer startColumn = null;

        if (getAst(lineNum) != null) {
            startColumn = getAst(lineNum).getColumnNo();
        }

        return startColumn;
    }

    /**
     * Check if the this set of ast is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return astLines.isEmpty();
    }

    /**
     * The first line in set of ast.
     *
     * @return first line in set of ast.
     */
    public DetailAST firstLine() {
        return astLines.get(astLines.firstKey());
    }

    /**
     * Get the ast corresponding to line number.
     *
     * @param lineNum   line number of ast.
     * @return          ast with their corresponding line number or null if no mapping is present
     */
    public DetailAST getAst(int lineNum) {
        return astLines.get(lineNum);
    }

    /**
     * Get the line number of the last line.
     *
     * @return the line number of the last line
     */
    public Integer lastLine() {
        return astLines.lastKey();
    }

}
