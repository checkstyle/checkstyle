/*
LineLength
fileExtensions = (default)""
ignorePattern = ^ *\\* *([^ ]+|\\{@code .*|<a href="[^"]+">)$
max = (default)80


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

/**
 * <a href="a long string that has exceeded the 80 character per line limit">with inline title</a> // violation, 'Line is longer than 80 characters (found 161).'
 * <a href="another long string that has exceeded the 80 character per line limit">
 * with wrapped title</a>
 */
public class InputLineLengthLongLink {
}
