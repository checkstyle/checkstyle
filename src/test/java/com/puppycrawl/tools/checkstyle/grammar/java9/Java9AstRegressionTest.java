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

package com.puppycrawl.tools.checkstyle.grammar.java9;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class Java9AstRegressionTest extends AbstractTreeTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/java9";
    }

    @Test
    public void testModuleInfoAllDirectives() throws Exception {
        verifyAst(
                getNonCompilablePath("ExpectedModuleInfoAllDirectives.txt"),
                getNonCompilablePath("alldirectives/module-info.java"));
    }

    @Test
    public void testModuleInfoKeywordsAsNames() throws Exception {
        verifyAst(
                getNonCompilablePath("ExpectedModuleInfoKeywordsAsNames.txt"),
                getNonCompilablePath("keywordsasnames/module-info.java"));
    }

    @Test
    public void testModuleKeywordsAsIdentifiers() throws Exception {
        verifyAst(
                getPath("ExpectedModuleKeywordsAsIdentifiers.txt"),
                getPath("InputModuleKeywordsAsIdentifiers.java"));
    }

    @Test
    public void testPackageBeforeModuleDeclarationRejected() throws Exception {
        final File file =
                new File(getNonCompilablePath("invalidpackagebeforemodule/module-info.java"));
        final CheckstyleException exc = getExpectedThrowable(CheckstyleException.class,
                () -> JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS),
                "CheckstyleException is expected");
        assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .contains("IllegalStateException occurred while parsing file");
    }

    @Test
    public void testTypeAfterModuleDeclarationRejected() throws Exception {
        final File file =
                new File(getNonCompilablePath("invalidtypeaftermodule/module-info.java"));
        final CheckstyleException exc = getExpectedThrowable(CheckstyleException.class,
                () -> JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS),
                "CheckstyleException is expected");
        assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .contains("IllegalStateException occurred while parsing file");
    }
}
