package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.Checker;
import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.LineNumberReader;
import java.io.InputStreamReader;

/**
 * @author Daniel Grenner
 */
public class WriteTagCheckTest extends BaseCheckTestCase
{
    private DefaultConfiguration mCheckConfig;
    
    public void setUp() {
        mCheckConfig = createCheckConfig(WriteTagCheck.class);
    }

    public void testDefaultSettings() throws Exception
    {
        final String[] expected =
        {
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

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

    public void testMissingFormat() throws Exception
    {
        mCheckConfig.addAttribute("tag", "@author");
        final String[] expected =
        {
            "10: @author=Daniel Grenner",
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

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

    public void testDoubleTag() throws Exception
    {
      mCheckConfig.addAttribute("tag", "@doubletag");
      mCheckConfig.addAttribute("tagFormat", "\\S");
      final String[] expected =
      {
          "12: @doubletag=first text",
          "13: @doubletag=second text",
      };
      verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }


    public void testMissingTag() throws Exception
    {
        mCheckConfig.addAttribute("tag", "@missingtag");
        final String[] expected =
        {
            "15: Type Javadoc comment is missing an @missingtag tag.",
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

    public void testMethod() throws Exception
    {
        mCheckConfig.addAttribute("tag", "@todo");
        mCheckConfig.addAttribute("tagFormat", "\\S");
        mCheckConfig.addAttribute("tokens",
            "INTERFACE_DEF, CLASS_DEF, METHOD_DEF");
        mCheckConfig.addAttribute("severity", "ignore");
        final String[] expected =
        {
            "22: @todo=Add a comment",
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

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

    public void testRegularEx()
        throws Exception
    {
        mCheckConfig.addAttribute("tag", "@author");
        mCheckConfig.addAttribute("tagFormat", "0*");
        final String[] expected = {
        };
        verify(mCheckConfig, getPath("InputWriteTag.java"), expected);
    }

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

    protected void verify(Checker aC,
                          File[] aProcessedFiles,
                          String aMessageFileName,
                          String[] aExpected)
        throws Exception
    {
        mStream.flush();
        final int errs = aC.process(aProcessedFiles);

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
