package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.junit.Test;

public class NewlineAtEndOfFileCheckTest
    extends BaseCheckTestSupport
{
    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration aCheckConfig)
    {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    @Test
    public void testNewlineAtEndOfFile()
         throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF.toString());
        final String[] expected = { };
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineAtEndOfFile.java"),
            expected);
    }

    @Test
    public void testNoNewlineAtEndOfFile()
         throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF.toString());
        final String[] expected = {
            "0: File does not end with a newline."
        };
        verify(
            createChecker(checkConfig),
            getPath("InputNoNewlineAtEndOfFile.java"),
            expected);
    }

    @Test(expected=CheckstyleException.class)
    public void testSetLineSeparatorFailure()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", "ct");
        createChecker(checkConfig);
    }

    @Test
    public void testEmptyFileFile()
         throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF.toString());
        final String[] expected = {
            "0: File does not end with a newline."
        };
        verify(
            createChecker(checkConfig),
            getPath("InputEmptyFile.txt"),
            expected);
    }
}
