////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * The number of other classes a given class relies on. Also the square
 * of this has been shown to indicate the amount of maintenance required
 * in functional programs (on a file basis) at least.
 *
 */
public final class ClassFanOutComplexityCheck extends AbstractClassCouplingCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "classFanOutComplexity";

    /** Default value of max value. */
    private static final int DEFAULT_MAX = 20;

    /** Creates new instance of this check. */
    public ClassFanOutComplexityCheck() {
        super(DEFAULT_MAX);
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.EXTENDS_CLAUSE,
            TokenTypes.IMPLEMENTS_CLAUSE,
            TokenTypes.ANNOTATION,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.TYPE,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_THROWS,
            TokenTypes.ANNOTATION_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    // -@cs[SimpleAccessorNameNotation] Override methods from base class.
    // Issue: https://github.com/sevntu-checkstyle/sevntu.checkstyle/issues/166
    @Override
    protected String getLogMessageId() {
        return MSG_KEY;
    }

}
