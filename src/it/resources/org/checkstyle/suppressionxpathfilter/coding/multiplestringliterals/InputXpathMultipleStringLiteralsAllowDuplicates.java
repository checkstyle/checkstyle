/*
MultipleStringLiterals
allowedDuplicates = 2
ignoreOccurrenceContext = (default)ANNOTATION
ignoreStringsRegexp = (default)^""$


*/

package org.checkstyle.suppressionxpathfilter.coding.multiplestringliterals;

public class InputXpathMultipleStringLiteralsAllowDuplicates {
    String a = "StringContents";
    public void myTest() {
      String a2 = "StringContents";
      String a3 = "DoubleString" + "DoubleString";
      // violation below 'The String ", " appears 3 times in the file.'
      String a5 = ", " + ", " + ", ";
   }
}
