/*
LineLength
fileExtensions = (default)null
ignorePattern = ^(package|import) .*$
max = (default)80
tabWidth = (default)0

*/
package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

public class InputLineLengthEscapedDelimiterAtLineStart {
String text = """
\\"""
String longLine = "This is a regular string that is long enough to trigger a violation";
// violation above, 'Line is longer than 80 characters (found 88).'
}
