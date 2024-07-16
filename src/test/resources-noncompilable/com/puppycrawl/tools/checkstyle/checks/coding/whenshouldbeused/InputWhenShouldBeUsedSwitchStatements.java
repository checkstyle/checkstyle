/*
WhenShouldBeUsed

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.whenshouldbeused;

public class InputWhenShouldBeUsedSwitchStatements {

    void test(Object j, int x) {
        switch (j) {
            case String s:
                if (s.isEmpty()) {
                    System.out.println("empty String");
                }
                break;
            case Integer i: if (x == 0) System.out.println("Integer"); break;
            default: {}
        }
        switch (j) {
            case String s: {
                if (s.isEmpty()) {
                    System.out.println("empty String");
                } break;
            }
            case Integer i : {
                if (x == 0) System.out.println("Integer"); break;
            }
            default: {}
        }
        switch (j) {
            case String s when s.isEmpty():
                     System.out.println("empty String");
                     break;
            case Integer i when x == 0: System.out.println("Integer"); break;
            default: {}
        }

        switch (j) {
            case String s: // ok, not a single if
                if (s.isEmpty()) {
                    System.out.println("empty String");
                }
                if (!s.isEmpty())
                      System.out.println("Non empty String");
                break;
            case Integer i: break;
            default: {}
        }
        switch (j) {
            case String s:  // ok, not single if, there is else if
                if (s.isEmpty()) {
                    System.out.println("empty String");
                }
                else if (!s.isEmpty())
                      System.out.println("Non empty String");
                break;
            case Integer i: break;
            default: {}
        }
        switch (j) {
            case String s: {
                if (s.isEmpty()) {
                    System.out.println("empty String");
                } else
                    System.out.println("Non empty String");
                break;
            }
            case Integer i: break;
            default: {}
        }
         switch (j) {
            case String s: // ok, not a single if there is another statement
                int y = get();
                if (s.isEmpty() && y == 0) {
                    System.out.println("empty String");
                }
                break;
            case Integer i: break;
            default: {}
        }
         switch (j) {
            case String s: // ok, not a single if there is another statement
                if (s.isEmpty()) {
                    System.out.println("empty String");
                }
                System.out.println("A String");
                break;
            case Integer i: break;
            default: {}
        }
        switch (j) {
            case Integer _:
            case String _:
                if (x == 0) {
                    System.out.println("Integer, String");
                }
            default: {}
        }
        switch (j) {
            case Integer _:
            case String _: {
                if (x == 0) {
                    System.out.println("Integer, String");
                }
            }
            default: {}
        }
        switch (j) {
            case Integer _ when x == 0:
            case String _ when x == 0: {
                    System.out.println("Integer, String");
            }
            default: {}
        }
    }
    int get() { return 0; }
}
