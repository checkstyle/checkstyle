/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullTextBlocks {
  public void equalsAvoid(String myString) {
    if (myString.equals("stuff")) { // violation 'String.*should.*be.*left.*of.*equals'
    } // violation below 'String.*should.*be.*left.*of.*equals'
    if (myString.equals("""
        stuff""")) {
    }
  }

  public void method(Object object) {
    if (object instanceof String s) { // violation below 'String.*should.*be.*left.*of.*equals'
      if (s.equals("""
          my string""")) {
        System.out.println(s);
      }
    }
  }

  record MyRecord(String a, Object obj) {
    public MyRecord {
      if (obj instanceof String s) { // violation below 'String.*should.*left.*of.*equalsIgnoreCase'
        if (s.equalsIgnoreCase("""
            my other string""" + """
            plus this string""" + """
            and also this one.""")) {
          System.out.println("this is my other string");
        }
        else if ("""
            one more string""".equals(s)) {
          System.out.println("This is one more string");
        }
      }
    }
  }
}
