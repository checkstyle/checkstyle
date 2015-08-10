////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.util.BitSet;

/**
 * Encapsulates representation of notion of expected indentation levels.
 * Provide a way to have multiple accaptable levels.
 *
 * @author o_sukhodolsky
 */
public class IndentLevel {
    /** set of acceptable indentation levels. */
    private final BitSet levels = new BitSet();

    /**
     * Creates new instance with one accaptable indentation level.
     * @param indent accaptable indentation level.
     */
    public IndentLevel(int indent) {
        levels.set(indent);
    }

    /**
     * Creates new instance for nested structure.
     * @param base parent's level
     * @param offsets offsets from parent's level.
     */
    public IndentLevel(IndentLevel base, int... offsets) {
        final BitSet src = base.levels;
        for (int i = src.nextSetBit(0); i >= 0; i = src.nextSetBit(i + 1)) {
            for (int offset : offsets) {
                levels.set(i + offset);
            }
        }
    }

    /**
     * Checks wether we have more than one level.
     * @return wether we have more than one level.
     */
    public final boolean isMultiLevel() {
        return levels.cardinality() > 1;
    }

    /**
     * Checks if given indentation is acceptable.
     * @param indent indentation to check.
     * @return true if given indentation is acceptable,
     *         false otherwise.
     */
    public boolean accept(int indent) {
        return levels.get(indent);
    }

    /**
     * @param indent indentation to check.
     * @return true if {@code indent} less then minimal of
     *         acceptable indentation levels, false otherwise.
     */
    public boolean greaterThan(int indent) {
        return levels.nextSetBit(0) > indent;
    }

    /**
     * Adds one more acceptable indentation level.
     * @param indent new acceptable indentation.
     */
    public void addAcceptedIndent(int indent) {
        levels.set(indent);
    }

    /**
     * Adds one more acceptable indentation level.
     * @param indent new acceptable indentation.
     */
    public void addAcceptedIndent(IndentLevel indent) {
        levels.or(indent.levels);
    }

    /**
     * Returns first indentation level.
     * @return indentation level.
     */
    public int getFirstIndentLevel() {
        return levels.nextSetBit(0);
    }

    /**
     * Returns last indentation level.
     * @return indentation level.
     */
    public int getLastIndentLevel() {
        return levels.length() - 1;
    }

    @Override
    public String toString() {
        if (levels.cardinality() == 1) {
            return String.valueOf(levels.nextSetBit(0));
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = levels.nextSetBit(0); i >= 0;
            i = levels.nextSetBit(i + 1)) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(i);
        }
        return sb.toString();
    }
}
