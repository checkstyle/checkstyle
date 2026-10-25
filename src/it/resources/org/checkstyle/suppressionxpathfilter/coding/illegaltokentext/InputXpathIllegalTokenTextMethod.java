/*
IllegalTokenText
format = forbiddenText
ignoreCase = (default)false
message = (default)
tokens = STRING_LITERAL


*/

package org.checkstyle.suppressionxpathfilter.coding.illegaltokentext;

public class InputXpathIllegalTokenTextMethod {
    public void myMethod() {
        // violation below 'Token text matches the illegal pattern 'forbiddenText'.'
        String illegalString = "forbiddenText";
    }
}
