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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.BitSet;

/**
 * Encapsulates representation of notion of expected indentation levels.
 * Provide a way to have multiple acceptable levels.
 * This class is immutable.
 */
public class IndentLevel {

    /** Set of acceptable indentation levels. */
    private final BitSet levels = new BitSet();

    /**
     * Creates new instance with one acceptable indentation level.
     *
     * @param indent acceptable indentation level.
     */
    public IndentLevel(int indent) {
        levels.set(indent);
    }

    /**
     * Creates new instance for nested structure.
     *
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
     * Creates new instance with no acceptable indentation level.
     * This is only used internally to combine multiple levels.
     */
    private IndentLevel() {
    }

    /**
     * Checks whether we have more than one level.
     *
     * @return whether we have more than one level.
     */
    public final boolean isMultiLevel() {
        return levels.cardinality() > 1;
    }

    /**
     * Checks if given indentation is acceptable.
     *
     * @param indent indentation to check.
     * @return true if given indentation is acceptable,
     *         false otherwise.
     */
    public boolean isAcceptable(int indent) {
        return levels.get(indent);
    }

    /**
     * Returns true if indent less than minimal of
     * acceptable indentation levels, false otherwise.
     *
     * @param indent indentation to check.
     * @return true if {@code indent} less than minimal of
     *         acceptable indentation levels, false otherwise.
     */
    public boolean isGreaterThan(int indent) {
        return levels.nextSetBit(0) > indent;
    }

    /**
     * Adds one or more acceptable indentation level.
     *
     * @param base class to add new indentations to.
     * @param additions new acceptable indentation.
     * @return New acceptable indentation level instance.
     */
    public static IndentLevel addAcceptable(IndentLevel base, int... additions) {
        final IndentLevel result = new IndentLevel();
        result.levels.or(base.levels);
        for (int addition : additions) {
            result.levels.set(addition);
        }
        return result;
    }

    /**
     * Combines 2 acceptable indentation level classes.
     *
     * @param base class to add new indentations to.
     * @param addition new acceptable indentation.
     * @return New acceptable indentation level instance.
     */
    public static IndentLevel addAcceptable(IndentLevel base, IndentLevel addition) {
        final IndentLevel result = new IndentLevel();
        result.levels.or(base.levels);
        result.levels.or(addition.levels);
        return result;
    }

    /**
     * Returns first indentation level.
     *
     * @return indentation level.
     */
    public int getFirstIndentLevel() {
        return levels.nextSetBit(0);
    }

    /**
     * Returns last indentation level.
     *
     * @return indentation level.
     */
    public int getLastIndentLevel() {
        return levels.length() - 1;
    }

    @Override
    public String toString() {
        final String result;
        if (levels.cardinality() == 1) {
            result = String.valueOf(levels.nextSetBit(0));
        }
        else {
            final StringBuilder sb = new StringBuilder(50);
            for (int i = levels.nextSetBit(0); i >= 0;
                 i = levels.nextSetBit(i + 1)) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(i);
            }
            result = sb.toString();
        }
        return result;
    }

}
