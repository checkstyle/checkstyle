/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullEnhancedInstanceof {

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
