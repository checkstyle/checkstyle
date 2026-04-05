/*
LineLength
fileExtensions = (default)null
ignorePattern = ^(package|import) .*$
max = (default)80
tabWidth = (default)0

*/

package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

public class InputLineLengthTextBlock {

    String text = """
            This is a very long line inside a text block that exceeds eighty characters and should not trigger a violation.
            """;

    // violation below, 'Line is longer than 80 characters (found 131).'
    String longLine = "This is a very long regular Java string literal that should trigger a line length violation in this check.";
}
