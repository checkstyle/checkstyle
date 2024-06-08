/*
HiddenField

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

public class InputHiddenFieldRecordPattern {
    private String s;
    private int x;
    static int z;

    record Color(String name, int value) { }
    record Point(Color c, int x, int y) { }

    public boolean doStuff(Object f) {
            return f instanceof Color(String s, int x);  // 2 violations
    }


    public void test(Object f) {
        if (f instanceof Color(String s, int x)) { // 2 violations
            System.out.println(s);
        }
    }

   public void test2(Object f) {
        if (f instanceof Point(Color(String s, int x), _, int z)) { // 3 violations
            System.out.println(s);
        }
        switch (f) {
            case Point(Color(String s, int x), _, _) : break; // 2 violations
            default:
                throw new IllegalStateException();
        }
   }
}
