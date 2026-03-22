/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar;

/**
 * Input for Java 7 String in Switch.
 */
public class InputJava7StringSwitch // ok
{
    public static void main(String[] args) {
        String mystr = "value" + "2";

        switch (mystr) {
            case "value1":
                break;
            case "value2":
                break;
            default:
                break;
        }
    }
}
