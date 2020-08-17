//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

/* Config:
 *
 * ignoreEqualsIgnoreCase  = false
 */
public class InputEqualsAvoidNullTextBlocks {
    public void equalsAvoid(String myString) {
        if (myString.equals("stuff")) { // violation
        }
        if (myString.equals("""
                stuff""")) { // violation
        }
    }

    public void method(Object object) {
        if (object instanceof String s) {
            if (s.equals("""
                    my string""")) { // violation
                System.out.println(s);
            }
        }
    }

    record MyRecord(String a, Object obj) {
        public MyRecord {
            if (obj instanceof String s) {
                if (s.equalsIgnoreCase("""
                        my other string""" + """
                        plus this string""" + """
                        and also this one.""")) { // violation
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
