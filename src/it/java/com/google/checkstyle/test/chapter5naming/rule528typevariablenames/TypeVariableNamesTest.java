///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.google.checkstyle.test.chapter5naming.rule528typevariablenames;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class TypeVariableNamesTest extends AbstractGoogleModuleTestSupport {

    private static final String[] MODULES = {
        "MethodTypeParameterName",
        "RecordTypeParameterName",
        "InterfaceTypeParameterName",
        "ClassTypeParameterName",
    };

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule528typevariablenames";
    }

    @Test
    public void testMethodDefault() throws Exception {
        final String filePath = getPath("InputMethodTypeParameterName.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testClassDefault() throws Exception {
        final String filePath = getPath("InputClassTypeParameterName.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testInterfaceDefault() throws Exception {
        final String filePath = getPath("InputInterfaceTypeParameterName.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testRecordDefault() throws Exception {
        final String filePath = getNonCompilablePath("InputRecordTypeParameterName.java");
        verifyWithConfigParser(MODULES, filePath);
    }
}
