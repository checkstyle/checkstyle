/*
LambdaBodyLength
max = (default)10


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.lambdabodylength;

import java.util.stream.Stream;

public class InputLambdaBodyLengthSwitchExps {

    boolean method1(Nums k, String string) {
        switch (k) {
        case ONE -> { // ok
            System.out.println(2);
            System.out.println(3);
            System.out.println(4);
            System.out.println(5);
            System.out.println(6);
            System.out.println(7);
            System.out.println(8);
            System.out.println(9);
            System.out.println(10);
            System.out.println(11);
        }
        default -> Stream.of(string.split(" "))
            .anyMatch(Word -> "in".equals(Word));
        }
        return true;
    }

    enum Nums {ONE, TWO, THREE}
}
