////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Grenner
 */
public class WriteTagCheckTest extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(WriteTagCheck.class);
    }

    @Test
    public void testDefaultSettings() throws Exception
    {
        final String[] expected =
        {
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testTag() throws Exception
    {
        mCheckConfig.addAttribute("tag", "@author");
        mCheckConfig.addAttribute("tagFormat", "\\S");
        final String[] expected =
        {
            "10: @author=Daniel Grenner",
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testMissingFormat() throws Exception
    {
        mCheckConfig.addAttribute("tag", "@author");
        final String[] expected =
        {
            "10: @author=Daniel Grenner",
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testTagSeverity() throws Exception
    {
        mCheckConfig.addAttribute("tag", "@incomplete");
        mCheckConfig.addAttribute("tagFormat", "\\S");
        mCheckConfig.addAttribute("tagSeverity", "warning");
        final String[] expected =
        {
            "11: warning: @incomplete=This class needs more code...",
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testDoubleTag() throws Exception
    {
        mCheckConfig.addAttribute("tag", "@doubletag");
        mCheckConfig.addAttribute("tagFormat", "\\S");
        final String[] expected = {
            "12: @doubletag=first text",
            "13: @doubletag=second text",
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testEmptyTag() throws Exception
    {
        mCheckConfig.addAttribute("tag", "@emptytag");
        mCheckConfig.addAttribute("tagFormat", "");
        final String[] expected = {
            "14: @emptytag=",
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }


    @Test
    public void testMissingTag() throws Exception
    {
        mCheckConfig.addAttribute("tag", "@missingtag");
        final String[] expected =
        {
            "16: Type Javadoc comment is missing an @missingtag tag.",
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testMethod() throws Exception
    {
        mCheckConfig.addAttribute("tag", "@todo");
        mCheckConfig.addAttribute("tagFormat", "\\S");
        mCheckConfig.addAttribute("tokens",
            "INTERFACE_DEF, CLASS_DEF, METHOD_DEF, CTOR_DEF");
        mCheckConfig.addAttribute("severity", "ignore");
        final String[] expected = {
            "19: @todo=Add a constructor comment",
            "30: @todo=Add a comment",
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testSeverity() throws Exception
    {
        mCheckConfig.addAttribute("tag", "@author");
        mCheckConfig.addAttribute("tagFormat", "\\S");
        mCheckConfig.addAttribute("severity", "ignore");
        final String[] expected =
        {
            "10: @author=Daniel Grenner",
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testIgnoreMissing() throws Exception
    {
        mCheckConfig.addAttribute("tag", "@todo2");
        mCheckConfig.addAttribute("tagFormat", "\\S");
        mCheckConfig.addAttribute("severity", "ignore");
        final String[] expected =
        {
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testRegularEx()
        throws Exception
    {
        mCheckConfig.addAttribute("tag", "@author");
        mCheckConfig.addAttribute("tagFormat", "0*");
        final String[] expected = {
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testRegularExError()
        throws Exception
    {
        mCheckConfig.addAttribute("tag", "@author");
        mCheckConfig.addAttribute("tagFormat", "ABC");
        final String[] expected = {
            "10: Type Javadoc tag @author must match pattern 'ABC'.",
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testEnumsAndAnnotations() throws Exception
    {
        mCheckConfig.addAttribute("tag", "@incomplete");
        mCheckConfig.addAttribute("tagFormat", ".*");
        mCheckConfig.addAttribute("severity", "ignore");
        mCheckConfig.addAttribute("tagSeverity", "error");
        mCheckConfig.addAttribute("tokens",
            "ANNOTATION_DEF, ENUM_DEF, ANNOTATION_FIELD_DEF, ENUM_CONSTANT_DEF");
        final String[] expected =
        {
            "9: @incomplete=This enum needs more code...",
            "13: @incomplete=This enum constant needs more code...",
            "19: @incomplete=This annotation needs more code...",
            "23: @incomplete=This annotation field needs more code...",
        };
        verify(mCheckConfig, getPath("InputWriteTag2.java"), expected);
    }

    @Override
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

        // process each of the lines
        final ByteArrayInputStream bais =
            new ByteArrayInputStream(mBAOS.toByteArray());
        final LineNumberReader lnr =
            new LineNumberReader(new InputStreamReader(bais));

        for (int i = 0; i < aExpected.length; i++) {
            final String expected = aMessageFileName + ":" + aExpected[i];
            final String actual = lnr.readLine();
            assertEquals("error message " + i, expected, actual);
        }

        assertTrue("unexpected output: " + lnr.readLine(),
                   aExpected.length >= errs);

        aC.destroy();
    }
}
