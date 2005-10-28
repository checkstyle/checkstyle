package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class GenericIllegalRegexpCheckTest
    extends BaseCheckTestCase
{
    public void testIt()
            throws Exception
    {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        final String[] expected = {
            "69: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    public void testMessageProperty()
        throws Exception
    {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        final String message = "Bad line :(";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("message", message);
        final String[] expected = {
            "69: " + message,
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    public void testIgnoreCaseTrue()
            throws Exception
    {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreCase", "true");
        final String[] expected = {
            "69: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    public void testIgnoreCaseFalse()
            throws Exception
    {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        final DefaultConfiguration checkConfigTrue =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfigTrue.addAttribute("format", illegal);
        checkConfigTrue.addAttribute("ignoreCase", "true");
        final String[] expectedTrue = {
            "69: Line matches the illegal pattern '" + illegal + "'."};
        verify(checkConfigTrue, getPath("InputSemantic.java"), expectedTrue);

        final DefaultConfiguration checkConfigFalse =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfigFalse.addAttribute("format", illegal);
        checkConfigFalse.addAttribute("ignoreCase", "false");
        final String[] expectedFalse = {};
        verify(checkConfigFalse, getPath("InputSemantic.java"), expectedFalse);
    }

    public void testIgnoreCommentsCppStyle()
            throws Exception
    {
        // See if the comment is removed properly
        final String illegal = "don't use trailing comments";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    public void testIgnoreCommentsFalseCppStyle()
            throws Exception
    {
        // See if the comment is removed properly
        final String illegal = "don't use trailing comments";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "false");
        final String[] expected = {
            "2: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    public void testIgnoreCommentsCStyle()
            throws Exception
    {
        // See if the comment is removed properly
        final String illegal = "c-style 1";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    public void testIgnoreCommentsFalseCStyle()
            throws Exception
    {
        final String illegal = "c-style 1";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "false");
        final String[] expected = {
            "17: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    public void testIgnoreCommentsMultipleCStyle()
            throws Exception
    {
        // See if a second comment on the same line is removed properly
        final String illegal = "c-style 2";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    public void testIgnoreCommentsMultiLine()
            throws Exception
    {
        final String illegal = "Let's check multi-line comments";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    public void testIgnoreCommentsInlineStart()
            throws Exception
    {
        final String illegal = "long ms /";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    public void testIgnoreCommentsInlineEnd()
            throws Exception
    {
        final String illegal = "int z";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
            "20: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    public void testIgnoreCommentsInlineMiddle() throws Exception
    {
        final String illegal = "int y";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
            "21: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    public void testIgnoreCommentsNoSpaces()
            throws Exception
    {
        // make sure the comment is not turned into spaces
        final String illegal = "long ms  ";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }
}
