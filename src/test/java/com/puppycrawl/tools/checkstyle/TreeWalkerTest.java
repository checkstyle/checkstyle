////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TreeWalkerTest extends BaseCheckTestSupport {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testProperFileExtension() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ConstantNameCheck.class);
        final String content = "public class Main { public static final int k = 5 + 4; }";
        final File file = temporaryFolder.newFile("file.java");
        final Writer writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.close();
        final String[] expected1 = {
            "1:45: Name 'k' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };
        verify(checkConfig, file.getPath(), expected1);
    }

    @Test
    public void testImproperFileExtension() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ConstantNameCheck.class);
        final File file = temporaryFolder.newFile("file.pdf");
        final String content = "public class Main { public static final int k = 5 + 4; }";
        final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.close();
        final String[] expected = {
        };
        verify(checkConfig, file.getPath(), expected);
    }


    @Test
    public void testAcceptableTokens()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("tokens", "VARIABLE_DEF, ENUM_DEF, CLASS_DEF, METHOD_DEF,"
                + "IMPORT");
        final String[] expected = {

        };
        try {
            verify(checkConfig, getPath("InputHiddenField.java"), expected);
            Assert.fail();
        }
        catch (CheckstyleException e) {
            String errorMsg = e.getMessage();
            Assert.assertTrue(errorMsg.contains("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.TreeWalker - Token \"IMPORT\""
                    + " was not found in Acceptable tokens list in check"
                    + " com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck"));
        }
    }

    @Test
    public void testOnEmptyFile() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HiddenFieldCheck.class);
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = {
        };

        verify(checkConfig, pathToEmptyFile, expected);
    }

    @Test
    public void testWithCheckNotHavingTreeWalkerAsParent() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocPackageCheck.class);
        final String[] expected = {
        };

        try {
            verify(checkConfig, temporaryFolder.newFile().getPath(), expected);
            fail();
        }
        catch (CheckstyleException exception) {
            assertTrue(exception.getMessage().contains("TreeWalker is not allowed as a parent of"));
        }
    }

    @Test
    public void testSettersForParameters() throws Exception {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.setTabWidth(1);
        treeWalker.setCacheFile(temporaryFolder.newFile().getPath());
    }

    @Test
    public void testNonExistingCacheFileDoesNotThrowException() {
        final TreeWalker treeWalker = new TreeWalker();
        treeWalker.setCacheFile("/invalid");
        treeWalker.destroy();
    }
}
