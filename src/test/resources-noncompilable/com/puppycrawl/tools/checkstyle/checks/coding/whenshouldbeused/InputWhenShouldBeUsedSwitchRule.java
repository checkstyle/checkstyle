/*
WhenShouldBeUsed

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.whenshouldbeused;

public class InputWhenShouldBeUsedSwitchRule {

    void test(Object j, int x) {
        switch (j) {
            case String s -> { // violation, 'When should be used instead of a single if*.'
                if (s.isEmpty()) {
                    System.out.println("empty String");
                }
            }
            case Integer i -> { // violation, 'When should be used instead of a single if*.'
                if (x == 0) System.out.println("Integer");
            }
            default -> {}
        }
        switch (j) {
            case String s when s.isEmpty() -> {
                System.out.println("empty String");
            }
            case Integer i when x == 0 -> {
                System.out.println("Integer");
            }
            default -> {}
        }
        switch (j) {
            case String s -> {
                if (s.isEmpty()) {
                    System.out.println("empty String");
                }
                if (!s.isEmpty())
                    System.out.println("Non empty String");
            }
            case Integer i -> {}
            default -> {}
        }
        switch (j) {
            case String s -> {   // ok, not single if, there is else if
                if (s.isEmpty()) {
                    System.out.println("empty String");
                } else if (!s.isEmpty())
                    System.out.println("Non empty String");
            }
            default -> {}
        }
        switch (j) {
            case String s -> {
                if (s.isEmpty()) {
                    System.out.println("empty String");
                } else
                    System.out.println("Non empty String");
            }
            case Integer i -> {}
            default -> {}
        }
         switch (j) {
            case String s -> // ok, not a single if there is another statement
            {
                int y = get();
                if (s.isEmpty() && y == 0) {
                    System.out.println("empty String");
                }
            }
            default -> {}
        }
         switch (j) {
            case String s ->  {// ok, not a single if there is another statement
                if (s.isEmpty()) {
                    System.out.println("empty String");
                }
                System.out.println("A String");
            }
            default -> {}
        }
        switch (j) {
            case Integer _, String _ -> {
                // violation above, 'When should be used instead of a single if*.'
                if (x == 0) {
                    System.out.println("Integer, String");
                }
            }
            default -> {}
        }
        switch (j) {
            case Integer _, String _ when x == 0 ->
                System.out.println("Integer, String");
            default -> {}
        }
        switch (j) {
            case Point(int a,_) ->  { // violation, 'When should be used instead of a single if*.'
                if (a == 0) {}
            }
            default -> {}
        }
    }
    void testGuardedCases(Object obj, int x) {
        switch (obj) {
            // violation below, 'When should be used instead of a single if*.'
            case Integer i when i == 0 -> {
                if (x == 0) System.out.println("Integer");
            }
            default -> {}
        }
         switch (obj) {
            case Integer i when i == 0 && x == 0 -> {
            }
            default -> {}
        }
    }
    int get() { return 0; }
    record Point(int x, int y) {}
}
