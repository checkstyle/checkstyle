/*
NeedBraces
allowSingleLineStatement = false
allowEmptyLoopBody = (default)false
tokens = LITERAL_CASE, LITERAL_DEFAULT, LAMBDA


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesPatternMatchingForSwitch {

    void testSwitchStatements(Object o) {
        switch (o) {
            case Integer i when (i == 0) :  // violation
                System.out.println("zero");
                break;

            case String s when (s.equals("a")) :  // violation
                System.out.println("a");
                break;

            case Point(int x, int y ) when (x>=0 && y >=0) :  // violation
                System.out.println("point");
                break;
            default :  // violation
                System.out.println("default");
        }

         switch (o) {
            case Integer i when (i == 0) : {
                System.out.println("zero");
                break;
            }
            case String s when (s.equals("a")) : {
                System.out.println("a");
                break;
            }
            case Point(int x, int y ) when (x>=0 && y >=0) : {
                System.out.println("point");
                break;
            }
            default : {
                System.out.println("default");
            }
        }
    }

    void testSwitchRule(Object o) {
        switch (o) {
            case Integer i when (i == 0) -> System.out.println("zero");   // violation

            case String s when (s.equals("a")) ->  // violation
                System.out.println("a");

            case Point(int x, int y ) when (x>=0 && y >=0) ->  // violation
                System.out.println("point");

            default ->  // violation
                System.out.println("default");
        }
        switch (o) {
            case Integer i when (i == 0) -> {System.out.println("zero");}
            case String s when (s.equals("a")) -> {System.out.println("a");}
            case Point(int x, int y ) when (x>=0 && y >=0) -> {System.out.println("point");}
            default -> {System.out.println("default");}
        }
    }

    void testSwitchExpression(Object o) {
        int a =  switch (o) {
            case Integer i when (i == 0) :  // violation
                System.out.println("zero");
                yield i;

            case String s when (s.equals("a")) : yield s.length();  // violation

            case Point(int x, int y ) when (x>=0 && y >=0) :  // violation
                yield x + y;

            default :  // violation
                yield 0;
        };

        int b =  switch (o) {
            case Integer i when (i == 0) -> i; // violation
            case String s when (s.equals("a")) -> s.length(); // violation
            case Point(int x, int y ) when (x>=0 && y >=0) -> // violation
                x + y;
            case Integer i when (i == 9) -> {
                int n = i;
                yield n;
            }
            default -> 0; // violation
        };
    }
    record Point(int x, int y) {}
}
