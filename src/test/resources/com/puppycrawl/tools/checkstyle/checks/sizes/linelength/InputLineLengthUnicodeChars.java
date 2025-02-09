/*
LineLength
fileExtensions = (default)""
ignorePattern = (default)^(package|import) .*
max = 100


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

public class InputLineLengthUnicodeChars {
    String aaaaaaa = "1234567890123456789012345678901234567890" + "1234567890123456789012345" + "_";
    String aaaaaab = "1234567890123456789012345678901234567890" + "1234567890123456789012345" + "💩";
    String aaaaaac = "This line is too long, and will be reported by checkstyle.  This line is 137 characters long, excluding unicode."; // violation
    String aaaaaad = "This line is too long, and will be reported by checkstyle.💩💩This line is 137 characters long, including unicode."; // violation
}
