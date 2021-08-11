/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullEnhancedInstanceof {
    public InputEqualsAvoidNullEnhancedInstanceof(String str) {
        if (str instanceof String myString) {
            System.out.println("Mystring!!");
            boolean myBool = myString.equals("MyString!!"); // violation 'String literal expressions should be on the left side of an equals comparison.'
            boolean myOtherBool = myString.equals(str); // ok
        } else if (str instanceof String oneMoreString) {
            if(oneMoreString.equals("test")) { // violation 'String literal expressions should be on the left side of an equals comparison.'
                System.out.println("Test!!");
            }
        }
    }

    void foo (Object string) {
        if(string instanceof String string1) {
            final boolean myBool = string1.equals("test1") // violation 'String literal expressions should be on the left side of an equals comparison.'
                    || string1.equals("test1.5") // violation 'String literal expressions should be on the left side of an equals comparison.'
                    || string1.equals("test2") || // violation 'String literal expressions should be on the left side of an equals comparison.'
                    string1.equals("test3"); // violation 'String literal expressions should be on the left side of an equals comparison.'
            if(string instanceof Integer integer) {
                String string5 = integer.toString();
                if(integer.toString().equals("integer")) {

                }
            }
        }
    }
}
