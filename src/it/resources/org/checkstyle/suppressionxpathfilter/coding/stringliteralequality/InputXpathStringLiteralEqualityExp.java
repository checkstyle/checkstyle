/*
StringLiteralEquality


*/

package org.checkstyle.suppressionxpathfilter.coding.stringliteralequality;

public class InputXpathStringLiteralEqualityExp {
    public void myFunction(){
        String foo = "pending";
        boolean flag = (foo == "done");
        // violation above "Literal Strings should be compared using equals(), not '=='."
    }
}
