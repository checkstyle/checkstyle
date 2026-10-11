/*
LineLength
fileExtensions = (default)null
ignorePattern = ^ *\\* *([^ ]+|\\{@code .*|<a href="[^"]+">)$
max = (default)80
tabWidth = (default)0

*/

package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

// violation 2 lines below 'Line is longer than 80 characters (found 98).'
/**
 * <a href="a long string that has exceeded the 80 character per line limit">with inline title</a>
 * <a href="another long string that has exceeded the 80 character per line limit">
 * with wrapped title</a>
 */
public class InputLineLengthLongLink {
}
