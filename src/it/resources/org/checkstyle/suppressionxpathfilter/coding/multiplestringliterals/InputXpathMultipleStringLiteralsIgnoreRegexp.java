/*
MultipleStringLiterals
allowedDuplicates = (default)1
ignoreOccurrenceContext = (default)ANNOTATION
ignoreStringsRegexp = (("")|(", "))$


*/

package org.checkstyle.suppressionxpathfilter.coding.multiplestringliterals;

public class InputXpathMultipleStringLiteralsIgnoreRegexp {

    public void myTest() {

      // violation below 'The String "DoubleString" appears 2 times in the file.'
      String a3 = "DoubleString" + "DoubleString";
      String a4 = "SingleString";
      String a5 = ", " + ", " + ", ";

    }
}
