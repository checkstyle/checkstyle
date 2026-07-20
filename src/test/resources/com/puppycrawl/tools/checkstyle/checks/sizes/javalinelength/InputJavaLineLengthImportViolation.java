/*
JavaLineLength
max = (default)100
tokens = TEXT_BLOCK_LITERAL_BEGIN, PACKAGE_DEF
ignorePattern = <a href="[^"]+">


*/
package com.puppycrawl.tools.checkstyle.checks.sizes.javalinelength;

import      java.                util.                                                           ArrayList;
// violation above, 'Line is longer than 100 characters (found 107).'
public class InputJavaLineLengthImportViolation {

    ArrayList<Integer> list = new ArrayList<>();
}
