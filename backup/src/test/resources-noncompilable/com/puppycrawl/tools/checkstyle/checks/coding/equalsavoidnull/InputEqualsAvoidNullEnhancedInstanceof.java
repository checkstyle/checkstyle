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
            boolean myBool = myString.equals("MyString!!"); // violation 'left .* of .* equals'
            boolean myOtherBool = myString.equals(str); // ok
        } else if (str instanceof String oneMoreString) {
            if(oneMoreString.equals("test")) { // violation 'left .* of .* equals'
                System.out.println("Test!!");
            }
        }
    }

    void foo (Object string) {
        if(string instanceof String string1) {
            final boolean myBool = string1.equals("test1") // violation 'left .* of .* equals'
                    || string1.equals("test1.5") // violation 'left .* of .* equals'
                    || string1.equals("test2") || // violation 'left .* of .* equals'
                    string1.equals("test3"); // violation 'left .* of .* equals'
            if(string instanceof Integer integer) {
                String string5 = integer.toString();
                if(integer.toString().equals("integer")) {

                }
            }
        }
    }
}
