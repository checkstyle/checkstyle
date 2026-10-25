/*
MultipleStringLiterals
allowedDuplicates = (default)1
ignoreOccurrenceContext = (default)ANNOTATION
ignoreStringsRegexp = (default)^""$


*/

package org.checkstyle.suppressionxpathfilter.coding.multiplestringliterals;

public class InputXpathMultipleStringLiteralsDefault {
    // violation below 'The String "StringContents" appears 2 times in the file.'
    String a = "StringContents";
    String a1 = "unchecked";

    @SuppressWarnings("unchecked")
    public void myTest() {
      String a2 = "StringContents";
      String a4 = "SingleString";
    }
}
