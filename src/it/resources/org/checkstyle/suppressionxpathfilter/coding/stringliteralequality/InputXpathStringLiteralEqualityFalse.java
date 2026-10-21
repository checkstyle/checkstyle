/*
StringLiteralEquality


*/

package org.checkstyle.suppressionxpathfilter.coding.stringliteralequality;

public class InputXpathStringLiteralEqualityFalse {
    public void myFunction(){
        String foo = "pending";
        while (foo != "done") {}
        // violation above "Literal Strings should be compared using equals(), not '!='."
    }
}
