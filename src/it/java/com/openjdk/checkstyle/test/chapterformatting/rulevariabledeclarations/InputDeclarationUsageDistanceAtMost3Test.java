///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.openjdk.checkstyle.test.chapterformatting.rulevariabledeclarations;

import org.junit.jupiter.api.Test;

import com.openjdk.checkstyle.test.base.AbstractOpenJdkModuleTestSupport;

public class InputDeclarationUsageDistanceAtMost3Test extends AbstractOpenJdkModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/openjdk/checkstyle/test/chapterformatting/"
            + "rulevariabledeclarations/declarationrightbeforefirstusage";
    }

    @Test
    public void testDeclarationUsageDistanceAtMostThree1() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationUsageDistanceAtMostThree1.java"));
    }

    @Test
    public void testDeclarationUsageDistanceAtMostThree2() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationUsageDistanceAtMostThree2.java"));
    }

    @Test
    public void testDeclarationUsageDistanceAtMostThree3() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationUsageDistanceAtMostThree3.java"));
    }

    @Test
    public void testDeclarationUsageDistanceAtMostThree4() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationUsageDistanceAtMostThree4.java"));
    }

    @Test
    public void testDeclarationUsageDistanceAtMostThree5() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationUsageDistanceAtMostThree5.java"));
    }

    @Test
    public void testDeclarationUsageDistanceAtMostThree6() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationUsageDistanceAtMostThree6.java"));
    }

    @Test
    public void testDeclarationUsageDistanceAtMostThree7() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationUsageDistanceAtMostThree7.java"));
    }

}
