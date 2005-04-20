package com.puppycrawl.tools.checkstyle.checks.coding;

import java.io.File;
import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

/**
 * Test fixture for the UnnecessaryParenthesesCheck.
 *
 * @author  Eric K. Roe
 */
public class UnnecessaryParenthesesCheckTest extends BaseCheckTestCase {
    private static final String TEST_FILE = "coding" + File.separator +
        "InputUnnecessaryParentheses.java";

    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(UnnecessaryParenthesesCheck.class);

        final String[] expected = {
            "4:22: Unnecessary parentheses around assignment right-hand side.",
            "4:29: Unnecessary parentheses around expression.",
            "4:31: Unnecessary parentheses around identifier 'i'.",
            "4:46: Unnecessary parentheses around assignment right-hand side.",
            "5:15: Unnecessary parentheses around assignment right-hand side.",
            "6:14: Unnecessary parentheses around identifier 'x'.",
            "6:17: Unnecessary parentheses around assignment right-hand side.",
            "7:15: Unnecessary parentheses around assignment right-hand side.",
            "8:14: Unnecessary parentheses around identifier 'x'.",
            "8:17: Unnecessary parentheses around assignment right-hand side.",
            "11:22: Unnecessary parentheses around assignment right-hand side.",
            "11:30: Unnecessary parentheses around identifier 'i'.",
            "11:46: Unnecessary parentheses around assignment right-hand side.",
            "15:17: Unnecessary parentheses around literal '0'.",
            "25:11: Unnecessary parentheses around assignment right-hand side.",
            "29:11: Unnecessary parentheses around assignment right-hand side.",
            "31:11: Unnecessary parentheses around assignment right-hand side.",
            "33:11: Unnecessary parentheses around assignment right-hand side.",
            "34:16: Unnecessary parentheses around identifier 'a'.",
            "35:14: Unnecessary parentheses around identifier 'a'.",
            "35:20: Unnecessary parentheses around identifier 'b'.",
            "35:26: Unnecessary parentheses around literal '600'.",
            "35:40: Unnecessary parentheses around literal '12.5f'.",
            "35:56: Unnecessary parentheses around identifier 'arg2'.",
            "36:14: Unnecessary parentheses around string \"this\".",
            "36:25: Unnecessary parentheses around string \"that\".",
            "37:11: Unnecessary parentheses around assignment right-hand side.",
            "37:14: Unnecessary parentheses around string \"this is a really, really...\".",
            "39:16: Unnecessary parentheses around return value.",
            "43:21: Unnecessary parentheses around literal '1'.",
            "43:26: Unnecessary parentheses around literal '13.5'.",
            "44:22: Unnecessary parentheses around literal 'true'.",
            "45:17: Unnecessary parentheses around identifier 'b'.",
            "49:17: Unnecessary parentheses around assignment right-hand side.",
            "51:11: Unnecessary parentheses around assignment right-hand side.",
            "53:16: Unnecessary parentheses around return value.",
            "63:13: Unnecessary parentheses around expression.",
            "67:16: Unnecessary parentheses around expression.",
            "72:19: Unnecessary parentheses around expression.",
            "73:23: Unnecessary parentheses around literal '4000'.",
            "78:19: Unnecessary parentheses around assignment right-hand side.",
            "80:11: Unnecessary parentheses around assignment right-hand side.",
            "80:16: Unnecessary parentheses around literal '3'.",
            "81:27: Unnecessary parentheses around assignment right-hand side.",
        };

        verify(checkConfig, getPath(TEST_FILE), expected);
    }

    public void test15Extensions() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(UnnecessaryParenthesesCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("Input15Extensions.java"), expected);
    }
}
