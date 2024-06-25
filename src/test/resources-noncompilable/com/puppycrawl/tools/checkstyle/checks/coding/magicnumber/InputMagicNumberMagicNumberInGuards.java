/*
MagicNumber
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = (default)false
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberMagicNumberInGuards {

    void m(Object o) {
        if (o instanceof Point(int x, int y, double z) && x < 3 && y > 8) {}
        // 2 violations above:
        //                  ''3' is a magic number'
       //                   ''8' is a magic number'

        switch (o) {
            case Point(int x, int y, double z) when (x < 3 && y != 6) -> {}
             // 2 violations above:
            //                  ''3' is a magic number.'
            //                   ''6' is a magic number.'
            case Point(_, _, double z) when z > (10.88) -> {}
            // violation above, ''10.88' is a magic number'
            case String s when s.length() != 6 -> {}
            // violation above, ''6' is a magic number'
            default -> {}
        }

        int w = switch (o) {
            case Point(int x, int y, double z) when (z == 0.5) -> 5;
             // 2 violations above:
            //                  ''0.5' is a magic number.'
           //                   ''5' is a magic number.'
            case String s -> {
                yield 6; // violation, ''6' is a magic number'
            }
            default -> 0;
        };
    }

    record Point(int x, int y, double z) { }
}
