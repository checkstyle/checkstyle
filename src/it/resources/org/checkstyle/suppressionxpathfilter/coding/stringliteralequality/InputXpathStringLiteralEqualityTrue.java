/*
StringLiteralEquality


*/

package org.checkstyle.suppressionxpathfilter.coding.stringliteralequality;

public class InputXpathStringLiteralEqualityTrue {
    public void myFunction(){
        String foo = "pending";
        if (foo == "done") {}
        // violation above "Literal Strings should be compared using equals(), not '=='."
    }
}
