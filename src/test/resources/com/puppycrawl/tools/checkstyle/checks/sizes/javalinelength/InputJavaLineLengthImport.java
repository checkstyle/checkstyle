/*
JavaLineLength
max = (default)100
tokens = IMPORT


*/
package com.puppycrawl.tools.checkstyle.checks.sizes.javalinelength;

import      java.                util.                                                           ArrayList;
import java.util.                                                                                   List;

public class InputJavaLineLengthImport {                                                          //
    // This line is exactly 100 character and will not show any violation                          .
    // This line is exactly 101 character and will show any violation                               .
    // violation above, 'Line is longer than 100 characters (found 101).'

    List<Integer> list = new ArrayList<>();


}
