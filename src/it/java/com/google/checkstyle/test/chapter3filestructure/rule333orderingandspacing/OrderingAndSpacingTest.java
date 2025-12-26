///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class OrderingAndSpacingTest extends AbstractGoogleModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/google/checkstyle/test/chapter3filestructure/rule333orderingandspacing";
    }

    @Test
    void customImport1() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacing1.java"));
    }

    @Test
    void customImport1Formatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedOrderingAndSpacing1.java"));
    }

    @Test
    void customImport2() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacing2.java"));
    }

    @Test
    void customImport2Formatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedOrderingAndSpacing2.java"));
    }

    @Test
    void customImport3() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacing3.java"));
    }

    @Test
    void customImport3Formatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedOrderingAndSpacing3.java"));
    }

    @Test
    void customImport4() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacing4.java"));
    }

    @Test
    void customImport4Formatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedOrderingAndSpacing4.java"));
    }

    @Test
    void customImport5() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacing5.java"));
    }

    @Test
    void customImport5Formatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedOrderingAndSpacing5.java"));
    }

    @Test
    void valid() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacingValid.java"));
    }

    @Test
    void validFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedOrderingAndSpacingValid.java"));
    }

    @Test
    void valid2() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacingValid2.java"));
    }

    @Test
    void valid2Formatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedOrderingAndSpacingValid2.java"));
    }

    @Test
    void validGoogleStyleOrderOfImports() throws Exception {
        verifyWithWholeConfig(getPath("InputOrderingAndSpacingNoImports.java"));
    }

    @Test
    void validGoogleStyleOrderOfImportsFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedOrderingAndSpacingNoImports.java"));
    }

}
