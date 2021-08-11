/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullTextBlocks {
    public void equalsAvoid(String myString) {
        if (myString.equals("stuff")) { // violation 'String literal expressions should be on the left side of an equals comparison.'
        }
        if (myString.equals("""
                stuff""")) {
        }
    }

    public void method(Object object) {
        if (object instanceof String s) {
            if (s.equals("""
                    my string""")) {
                System.out.println(s);
            }
        }
    }

    record MyRecord(String a, Object obj) {
        public MyRecord {
            if (obj instanceof String s) { // next line is a violation
                if (s.equalsIgnoreCase("""
                        my other string""" + """
                        plus this string""" + """
                        and also this one.""")) {
                    System.out.println("this is my other string");
                }
                else if ("""
                        one more string""".equals(s)) { // ok
                    System.out.println("This is one more string");
                }
            }
        }
    }
}
