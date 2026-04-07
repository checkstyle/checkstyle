/*
LineLength
fileExtensions = (default)null
ignorePattern = ^(package|import) .*$
max = (default)80
tabWidth = (default)0

*/
package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

public class InputLineLengthEscapedDelimiter {

    // escaped triple-quote \""" does not close the text block
    String text = """
            line one
            \"""still inside the block
            """;

    // violation below, 'Line is longer than 80 characters (found 92).'
    String longLine = "This is a regular string that is long enough to trigger a violation";
}
