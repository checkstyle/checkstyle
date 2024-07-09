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

package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class OrderingAndSpacingTest extends AbstractGoogleModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter3filestructure/rule333orderingandspacing";
    }

    @Test
    public void testCustomImport1() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacing1.java"));
    }

    @Test
    public void testCustomImport2() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacing2.java"));
    }

    @Test
    public void testCustomImport3() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacing3.java"));
    }

    @Test
    public void testCustomImport4() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacing4.java"));
    }

    @Test
    public void testCustomImport5() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacing5.java"));
    }

    @Test
    public void testValid() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacingValid.java"));
    }

    @Test
    public void testValid2() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacingValid2.java"));
    }

    @Test
    public void testValidGoogleStyleOrderOfImports() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacingNoImports.java"));
    }

}
