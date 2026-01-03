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

package com.puppycrawl.tools.checkstyle.grammar.java25;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;

public class Java25AstRegressionTest extends AbstractTreeTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/java25";
    }

    @Test
    public void testImportModuleCustom() throws Exception {
        verifyAst(
                getNonCompilablePath("ExpectedImportModuleCustom.txt"),
                getNonCompilablePath("InputImportModuleCustom.java"));
    }

    @Test
    public void testImportModuleJavaBase() throws Exception {
        verifyAst(
                getNonCompilablePath("ExpectedImportModuleJavaBase.txt"),
                getNonCompilablePath("InputImportModuleJavaBase.java"));
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        verifyAst(
                getNonCompilablePath("ExpectedCompactSourceFile.txt"),
                getNonCompilablePath("InputCompactSourceFile.java"));
    }
}
