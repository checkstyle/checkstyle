/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = false
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public abstract class InputEmptyLineSeparatorMultipleEmptyLinesInside
{
    public InputEmptyLineSeparatorMultipleEmptyLinesInside() {
        // violation


    }

    private int counter;

    private Object obj = null;

    abstract int generateSrc(String s);

    static {
        // violation


    }

    {
        // violation


    }

    private static void foo() { // violation


        // 1 empty line above should cause a violation

        // violation



        // 2 empty lines above should cause violations
    }
}
class SecondClassReturnWithVeryVeryVeryLongName{} // violation
