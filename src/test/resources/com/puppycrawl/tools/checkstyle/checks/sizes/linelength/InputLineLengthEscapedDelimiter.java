/*
LineLength
fileExtensions = (default)null
ignorePattern = ^(package|import) .*$
max = (default)80
tabWidth = (default)0

*/
package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

public class InputLineLengthEscapedDelimiter {

    // \""" inside a text block is an escaped triple-quote and does not close the block.
    String text = """
            line one
            \"""still inside the text block, not closing it
            """;

    // violation below, 'Line is longer than 80 characters (found 92).'
    String longLine = "This is a regular string that is long enough to trigger a violation";
}
