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

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class ImportControlCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/importcontrol";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.io.File"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {};

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED,
                "com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck"),
            "18:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.lang.ref.ReferenceQueue"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "filters/Example3.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED,
                "com.puppycrawl.tools.checkstyle.checks.TranslationCheck"),
            "18:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "javax.swing.ActionMap"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "newdomain/dao/Example4.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(
                ImportControlCheck.MSG_DISALLOWED, "java.awt.Image"),
            "15:1: " + getCheckMessage(
                ImportControlCheck.MSG_DISALLOWED, "java.io.File"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.Random"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "someImports/Example6.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.awt.Image"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "someImports/Example7.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.sql.Blob"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "gui/Example8.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(ImportControlCheck.MSG_DISALLOWED, "java.util.Map"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "filters/Example9.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.stream.Stream"),
            "16:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.stream.Collectors"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "someImports/Example10.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(
                ImportControlCheck.MSG_DISALLOWED, "java.util.Date"),
            "16:1: " + getCheckMessage(
                ImportControlCheck.MSG_DISALLOWED, "java.util.List"),
            "19:1: " + getCheckMessage(
                ImportControlCheck.MSG_DISALLOWED, "sun.misc.Signal"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
            + "/resources/" + getPackageLocation() + "/"
            + "someImports/Example11.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testExample12() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.Date"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(getPath("Example12.java"), expected);
    }

}
