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

package com.sun.checkstyle.test.chapter3fileorganization.rule313classdeclarations;

import org.junit.jupiter.api.Test;

import com.sun.checkstyle.test.base.AbstractSunModuleTestSupport;

public class DeclarationOrderTest extends AbstractSunModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/sun/checkstyle/test/chapter3fileorganization/rule313classdeclarations";
    }

    @Test
    public void testDeclarationOrderDefault() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationOrderDefault.java"));
    }

    @Test
    public void testDeclarationOrderAccessModifiers() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationOrderAccessModifiers.java"));
    }

    @Test
    public void testDeclarationOrderStaticMembers() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationOrderStaticMembers.java"));
    }

    @Test
    public void testDeclarationOrderConstructor() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationOrderConstructor.java"));
    }

    @Test
    public void testDeclarationOrderInstance() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationOrderInstance.java"));
    }

    @Test
    public void testDeclarationOrderMultipleViolations() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationOrderMultipleViolations.java"));
    }

    @Test
    public void testDeclarationOrderValid() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationOrderValid.java"));
    }

    @Test
    public void testDeclarationOrderValidInnerClass() throws Exception {
        verifyWithWholeConfig(getPath("InputDeclarationOrderValidInnerClass.java"));
    }

}
