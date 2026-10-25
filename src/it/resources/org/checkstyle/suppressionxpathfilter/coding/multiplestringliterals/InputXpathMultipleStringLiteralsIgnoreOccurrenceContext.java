/*
MultipleStringLiterals
allowedDuplicates = (default)1
ignoreOccurrenceContext =
ignoreStringsRegexp = (default)^""$


*/

package org.checkstyle.suppressionxpathfilter.coding.multiplestringliterals;

public class InputXpathMultipleStringLiteralsIgnoreOccurrenceContext {
    String a = "StringContents";
    // violation below 'The String "unchecked" appears 3 times in the file.'
    String a1 = "unchecked";

    @SuppressWarnings("unchecked")
    public void myTest() {
       String a3 = "DoubleString";
       String a5 = "unchecked";
    }
}
