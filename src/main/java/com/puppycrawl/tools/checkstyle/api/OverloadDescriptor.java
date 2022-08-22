///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.api;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Holds metadata about a method's modifiers. It stores the regex pattern used to match the
 * modifiers, the name of the method the modifiers are attached to, the index of the method
 * in order of appearance in the source class, and the {@code DetailAst} method token
 * the metadata was extracted from.
 * <br> Note: {@code index}, {@code lineNo}, and {@code methodToken} do not participate
 * in {@link #equals(Object)} or {@link #hashCode()}.
 */
public class OverloadDescriptor {

    /**
     * Overload group definition.
     */
    private final Pattern pattern;

    /**
     * Method name.
     */
    private final String methodName;

    /**
     * Note: does not participate in {@link #equals(Object)} or {@link #hashCode()}.
     */
    private final int index;

    /**
     * Note: does not participate in {@link #equals(Object)} or {@link #hashCode()}.
     */
    private final DetailAST methodToken;

    /**
     * A container class to represent a regex pattern and the method metadata
     * that it matches the modifiers for.
     *
     * @param pattern     the pattern which matched the modifiers, may not be null.
     * @param methodName  name of method, may not be null.
     * @param index       the index of the method in the source class
     * @param methodToken the matching ast of the method
     */
    public OverloadDescriptor(Pattern pattern,
                               String methodName, DetailAST methodToken, int index) {
        this.pattern =
            Objects.requireNonNull(pattern, "pattern may not be null");
        this.methodName =
            Objects.requireNonNull(methodName, "methodName may not be null");
        this.methodToken = methodToken;
        this.index = index;
    }

    /**
     * Note: does not participate in {@link #equals(Object)} or {@link #hashCode()}.
     *
     * @return method index in the source
     */
    public int getIndex() {
        return index;
    }

    /**
     * Note: does not participate in {@link #equals(Object)} or {@link #hashCode()}.
     *
     * @return method ast
     */
    public DetailAST getMethodToken() {
        return methodToken;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OverloadDescriptor other = (OverloadDescriptor) o;
        return Objects.equals(pattern.pattern(), other.pattern.pattern())
            && Objects.equals(methodName, other.methodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern.pattern(), methodName);
    }
}
