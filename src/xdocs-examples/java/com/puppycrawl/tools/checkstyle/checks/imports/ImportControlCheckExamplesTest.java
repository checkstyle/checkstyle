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
            "13:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.awt.Image"),
            "14:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.lang.ref.SoftReference"),
            "16:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.io.File"),
            "18:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.Date"),
            "19:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.List"),
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
            "13:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.awt.Image"),
            "16:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.io.File"),
            "17:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.io.FileReader"),
            "18:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.Date"),
            "19:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.List"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "filters/Example3.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED,
                "com.puppycrawl.tools.checkstyle.checks.TranslationCheck"),
            "18:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "javax.swing.ActionMap"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "newdomain/dao/UseCase1.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testUseCase2() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(
                ImportControlCheck.MSG_DISALLOWED, "java.awt.Image"),
            "15:1: " + getCheckMessage(
                ImportControlCheck.MSG_DISALLOWED, "java.io.File"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(getPath("UseCase2.java"), expected);
    }

    @Test
    public void testUseCase3() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.awt.Image"),
            "17:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.Date"),
            "18:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.List"),
            "20:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.stream.Collectors"),
            "21:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.stream.Stream"),
            "23:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "sun.misc.Signal"),

        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "someImports/UseCase3.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testUseCase4() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.awt.Image"),
            "17:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.Date"),
            "18:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.List"),
            "20:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.stream.Collectors"),
            "21:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.stream.Stream"),
            "23:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "sun.misc.Signal"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "someImports/UseCase4.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testUseCase5() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.sql.Blob"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "gui/UseCase5.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testUseCase6() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(ImportControlCheck.MSG_DISALLOWED,
                    "com.google.common.io.Files"),
            "15:1: " + getCheckMessage(ImportControlCheck.MSG_DISALLOWED,
                    "com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck"),
            "18:1: " + getCheckMessage(ImportControlCheck.MSG_DISALLOWED,
                    "java.lang.ref.ReferenceQueue"),
            "20:1: " + getCheckMessage(ImportControlCheck.MSG_DISALLOWED,
                    "java.lang.ref.SoftReference"),
            "24:1: " + getCheckMessage(ImportControlCheck.MSG_DISALLOWED, "java.util.Map"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "filters/UseCase6.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testUseCase7() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "javax.swing.Action"),
            "17:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.awt.Image"),
            "18:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.Date"),
            "19:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.List"),
            "21:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.stream.Collectors"),
            "22:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.stream.Stream"),
            "24:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "sun.misc.Signal"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources/" + getPackageLocation() + "/"
                + "someImports/UseCase7.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testUseCase8() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(
                ImportControlCheck.MSG_DISALLOWED, "java.util.Date"),
            "18:1: " + getCheckMessage(
                ImportControlCheck.MSG_DISALLOWED, "java.util.List"),
            "23:1: " + getCheckMessage(
                ImportControlCheck.MSG_DISALLOWED, "sun.misc.Signal"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
            + "/resources/" + getPackageLocation() + "/"
            + "someImports/UseCase8.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

    @Test
    public void testUseCase9() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.awt.Image"),
            "15:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.io.File"),
            "16:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.io.FileReader"),
            "17:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.Date"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(getPath("UseCase9.java"), expected);
    }

    @Test
    public void testExample13() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.logging"),
            "17:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.sql"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(getNonCompilablePath("Example13.java"), expected);
    }

    @Test
    public void testExample14() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.sql"),
            "17:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.xml"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyWithInlineXmlConfig(getNonCompilablePath("Example14.java"), expected);
    }

}
