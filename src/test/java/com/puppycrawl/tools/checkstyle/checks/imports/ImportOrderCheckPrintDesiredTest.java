////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.imports;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests printDesiredOrder option of {@link ImportOrderCheck} that displays
 * the imports ordered as per check configuration.
 * This mode is ready to use for copy and paste for correction of import order after
 * "Organize Imports" disaster.
 */
public class ImportOrderCheckPrintDesiredTest extends BaseCheckTestSupport
{
    @Override
    protected Checker createChecker(Configuration aCheckConfig) throws Exception
    {
        ((DefaultConfiguration) aCheckConfig).addAttribute("printDesiredOrder", "true");
        ((DefaultConfiguration) aCheckConfig).addAttribute("blameIndividualImports", "false");
        return super.createChecker(aCheckConfig);
    }

    protected void verify(Checker aC,
                          File[] aProcessedFiles,
                          String aMessageFileName,
                          String[] aExpected)
        throws Exception
    {
        mStream.flush();
        final List<File> theFiles = Lists.newArrayList();
        Collections.addAll(theFiles, aProcessedFiles);
        final int errs = aC.process(theFiles);
        assertEquals("error count", errs, aExpected.length);
        if (errs > 0) {
            assertEquals("error count should be 1", 1, aExpected.length);
            assertEquals("error message", aMessageFileName + ":" + aExpected[0] + "\n\nAudit done.\n", mBAOS.toString());
        }

        aC.destroy();
    }

    @Test
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ImportOrderCheck.class);
        final String[] expected = {
            "3: Suggested import order is as follows: import java.awt.Button;\n"
                + "import java.awt.Dialog;\n"
                + "import java.awt.Frame;\n"
                + "import java.awt.event.ActionEvent;\n"
                + "import java.io.File;\n"
                + "import java.io.IOException;\n"
                + "import java.io.InputStream;\n"
                + "import java.io.Reader;\n"
                + "import javax.swing.JComponent;\n"
                + "import javax.swing.JTable;\n"
                + "import static java.awt.Button.ABORT;\n"
                + "import static java.io.File.createTempFile;\n"
                + "import static javax.swing.WindowConstants.*;",
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder.java"), expected);
    }

    @Test
    public void testGroups() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("groups", "java.awt");
        checkConfig.addAttribute("groups", "javax.swing");
        checkConfig.addAttribute("groups", "java.io");
        final String[] expected = {
            "3: Suggested import order is as follows: import java.awt.Button;\n"
                + "import java.awt.Dialog;\n"
                + "import java.awt.Frame;\n"
                + "import java.awt.event.ActionEvent;\n"
                + "import static java.awt.Button.ABORT;\n"
                + "import javax.swing.JComponent;\n"
                + "import javax.swing.JTable;\n"
                + "import static javax.swing.WindowConstants.*;\n"
                + "import java.io.File;\n"
                + "import java.io.IOException;\n"
                + "import java.io.InputStream;\n"
                + "import java.io.Reader;\n"
                + "import static java.io.File.createTempFile;",
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder.java"), expected);
    }

    @Test
    public void testGroupsRegexp() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("groups", "java, /^javax?\\.(awt|swing)\\./");
        checkConfig.addAttribute("ordered", "false");
        final String[] expected = {
            "3: Suggested import order is as follows: import java.io.File;\n"
                + "import static java.io.File.createTempFile;\n"
                + "import java.io.IOException;\n"
                + "import java.io.InputStream;\n"
                + "import java.io.Reader;\n"
                + "import java.awt.Button;\n"
                + "import java.awt.Frame;\n"
                + "import java.awt.Dialog;\n"
                + "import java.awt.event.ActionEvent;\n"
                + "import static java.awt.Button.ABORT;\n"
                + "import javax.swing.JComponent;\n"
                + "import javax.swing.JTable;\n"
                + "import static javax.swing.WindowConstants.*;",
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder.java"), expected);
    }

    @Test
    public void testSeparated() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("groups", "java.awt, javax.swing, java.io");
        checkConfig.addAttribute("separated", "true");
        checkConfig.addAttribute("ordered", "false");
        final String[] expected = {
            "3: Suggested import order is as follows: import java.awt.Button;\n"
                + "import java.awt.Frame;\n"
                + "import java.awt.Dialog;\n"
                + "import java.awt.event.ActionEvent;\n"
                + "import static java.awt.Button.ABORT;\n"
                + "\n"
                + "import javax.swing.JComponent;\n"
                + "import javax.swing.JTable;\n"
                + "import static javax.swing.WindowConstants.*;\n"
                + "\n"
                + "import java.io.File;\n"
                + "import static java.io.File.createTempFile;\n"
                + "import java.io.IOException;\n"
                + "import java.io.InputStream;\n"
                + "import java.io.Reader;",
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder.java"), expected);
    }

    @Test
    public void testCaseInsensitive() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("caseSensitive", "false");
        final String[] expected = {
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrderCaseInsensitive.java"), expected);
    }

    @Test
    public void testTop() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("option", "top");
        checkConfig.addAttribute("separated", "true");
        final String[] expected = {
            "3: Suggested import order is as follows: import static java.awt.Button.ABORT;\n"
                + "import static java.io.File.*;\n"
                + "import static java.io.File.createTempFile;\n"
                + "import static javax.swing.WindowConstants.*;\n"
                + "\n"
                + "import java.awt.Button;\n"
                + "import java.awt.Dialog;\n"
                + "import java.awt.Frame;\n"
                + "import java.awt.event.ActionEvent;\n"
                + "import java.io.File;\n"
                + "import java.io.IOException;\n"
                + "import java.io.InputStream;\n"
                + "import java.io.Reader;\n"
                + "import javax.swing.JComponent;\n"
                + "import javax.swing.JTable;",
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_Top.java"), expected);
    }

    @Test
    public void testAbove() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("option", "above");
        checkConfig.addAttribute("separated", "true");
        final String[] expected = {
            "3: Suggested import order is as follows: import static java.awt.Button.ABORT;\n"
                + "import static java.awt.Button.ABORT;\n"
                + "import static java.io.File.createTempFile;\n"
                + "import static javax.swing.WindowConstants.*;\n"
                + "import java.awt.Button;\n"
                + "import java.awt.Dialog;\n"
                + "import java.awt.Frame;\n"
                + "import java.awt.event.ActionEvent;\n"
                + "import java.io.File;\n"
                + "import java.io.IOException;\n"
                + "import java.io.InputStream;\n"
                + "import java.io.Reader;\n"
                + "import javax.swing.JComponent;\n"
                + "import javax.swing.JTable;",
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_Above.java"), expected);
    }

    @Test
    public void testInFlow() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("option", "inflow");
        final String[] expected = {
            "3: Suggested import order is as follows: import java.awt.Button;\n"
                + "import static java.awt.Button.ABORT;\n"
                + "import java.awt.Dialog;\n"
                + "import java.awt.Frame;\n"
                + "import java.awt.event.ActionEvent;\n"
                + "import java.io.File;\n"
                + "import static java.io.File.createTempFile;\n"
                + "import java.io.IOException;\n"
                + "import java.io.InputStream;\n"
                + "import java.io.Reader;\n"
                + "import javax.swing.JComponent;\n"
                + "import javax.swing.JTable;\n"
                + "import static javax.swing.WindowConstants.*;\n"
                + "import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;\n"
                + "import static javax.swing.WindowConstants.HIDE_ON_CLOSE;",
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_InFlow.java"), expected);
    }

    @Test
    public void testUnder() throws Exception
    {
        // is default (testDefault)
        final DefaultConfiguration checkConfig =
                createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("option", "under");
        checkConfig.addAttribute("separated", "true");
        final String[] expected = {
            "3: Suggested import order is as follows: import java.awt.Button;\n"
                + "import java.awt.Dialog;\n"
                + "import java.awt.Frame;\n"
                + "import java.awt.event.ActionEvent;\n"
                + "import java.io.File;\n"
                + "import java.io.IOException;\n"
                + "import java.io.InputStream;\n"
                + "import java.io.Reader;\n"
                + "import javax.swing.JComponent;\n"
                + "import javax.swing.JTable;\n"
                + "import static java.awt.Button.ABORT;\n"
                + "import static java.awt.Button.ABORT;\n"
                + "import static java.io.File.createTempFile;\n"
                + "import static javax.swing.WindowConstants.*;",
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_Under.java"), expected);
    }

    @Test
    public void testBottom() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("option", "bottom");
        checkConfig.addAttribute("separated", "true");
        final String[] expected = {
            "3: Suggested import order is as follows: import java.awt.Button;\n"
                + "import java.awt.Dialog;\n"
                + "import java.awt.Frame;\n"
                + "import java.awt.event.ActionEvent;\n"
                + "import java.io.File;\n"
                + "import java.io.IOException;\n"
                + "import java.io.InputStream;\n"
                + "import java.io.Reader;\n"
                + "import javax.swing.JComponent;\n"
                + "import javax.swing.JTable;\n"
                + "\n"
                + "import static java.awt.Button.ABORT;\n"
                + "import static java.io.File.*;\n"
                + "import static java.io.File.createTempFile;\n"
                + "import static javax.swing.WindowConstants.*;",
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_Bottom.java"), expected);
    }

    @Test
    public void testHonorsTokenProperty() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("tokens", "IMPORT");
        final String[] expected = {
            "5: Suggested import order is as follows: import java.awt.Button;\n"
                + "import java.awt.Dialog;",
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_HonorsTokensProperty.java"), expected);
    }

    @Test
    public void testWildcard() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("groups", "com,*,java");
        final String[] expected = {
            "6: Suggested import order is as follows: import com.puppycrawl.tools.checkstyle.imports.InputImportOrder_Above;\n"
                + "import javax.crypto.BadPaddingException;\n"
                + "import javax.crypto.Cipher;\n"
                + "import java.util.List;",
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_Wildcard.java"), expected);
    }

    @Test
    public void testWildcardUnspecified() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportOrderCheck.class);

        /*
        <property name="ordered" value="true"/>
        <property name="separated" value="true"/>
        */
        checkConfig.addAttribute("groups", "java,javax,org");
        final String[] expected = {};

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_WildcardUnspecified.java"), expected);
    }

    @Test
    public void testNoFailureForRedundantImports() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ImportOrderCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_NoFailureForRedundantImports.java"), expected);
    }
}
