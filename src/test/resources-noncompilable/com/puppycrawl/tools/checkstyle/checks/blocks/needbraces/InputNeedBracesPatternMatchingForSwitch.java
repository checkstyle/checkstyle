/*
NeedBraces
allowSingleLineStatement = (default)false
allowEmptyLoopBody = (default)false
tokens = LITERAL_CASE, LITERAL_DEFAULT, LAMBDA


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesPatternMatchingForSwitch {

    void testSwitchStatements(Object o) {
        switch (o) {
            case Integer i when (i == 0) :  // violation ''case' construct must use '{}'s'
                System.out.println("zero");
                break;

            case String s when (s.equals("a")) :  // violation ''case' construct must use '{}'s'
                System.out.println("a");
                break;

            // violation below ''case' construct must use '{}'s'
            case Point(int x, int y ) when (x>=0 && y >=0) :
                System.out.println("point");
                break;
            default :  // violation ''default' construct must use '{}'s'
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
            // violation below ''case' construct must use '{}'s'
            case Integer i when (i == 0) -> System.out.println("zero");

            case String s when (s.equals("a")) ->  // violation ''case' construct must use '{}'s'
                System.out.println("a");

            // violation below ''case' construct must use '{}'s'
            case Point(int x, int y ) when (x>=0 && y >=0) ->
                System.out.println("point");

            default ->  // violation ''default' construct must use '{}'s'
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
            case Integer i when (i == 0) :  // violation ''case' construct must use '{}'s'
                System.out.println("zero");
                yield i;

            // violation below ''case' construct must use '{}'s'
            case String s when (s.equals("a")) : yield s.length();

            // violation below ''case' construct must use '{}'s'
            case Point(int x, int y ) when (x>=0 && y >=0) :
                yield x + y;

            default :  // violation ''default' construct must use '{}'s'
                yield 0;
        };

        int b =  switch (o) {
            // violation below ''case' construct must use '{}'s'
            case Integer i when (i == 0) -> i;
            // violation below ''case' construct must use '{}'s'
            case String s when (s.equals("a")) -> s.length();
            // violation below ''case' construct must use '{}'s'
            case Point(int x, int y ) when (x>=0 && y >=0) ->
                x + y;
            case Integer i when (i == 9) -> {
                int n = i;
                yield n;
            }
            default -> 0; // violation ''default' construct must use '{}'s'
        };
    }
    record Point(int x, int y) {}
}
