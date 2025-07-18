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

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class ImportControlCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/importcontrol";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.io.File"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources-noncompilable/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(getNonCompilablePath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {};

        System.setProperty("config.folder", "src/xdocs-examples/resources-noncompilable/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(getNonCompilablePath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "com.puppycrawl.tools.checkstyle.checks"),
            "19:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.lang.ref.ReferenceQueue"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources-noncompilable/" + getPackageLocation() + "/"
                + "filters/Example3.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources-noncompilable/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED,
                "com.puppycrawl.tools.checkstyle.checks.imports.importcontrol.ui"),
            "19:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "javax.swing"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources-noncompilable/" + getPackageLocation() + "/"
                + "newdomain/dao/Example4.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources-noncompilable/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.awt.Image"),
            "16:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.io.File"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources-noncompilable/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(getNonCompilablePath("Example5.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.stream.Stream"),
            "17:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.stream.Collectors"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources-noncompilable/" + getPackageLocation() + "/"
                + "someImports/Example10.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources-noncompilable/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

}
