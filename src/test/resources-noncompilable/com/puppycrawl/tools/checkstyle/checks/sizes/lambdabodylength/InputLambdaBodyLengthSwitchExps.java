//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.lambdabodylength;

import java.util.stream.Stream;

/* Config: default
 */
public class InputLambdaBodyLengthSwitchExps {

    boolean method1(Nums k, String string) {
        switch (k) {
        case ONE -> { // ok
            Stream.of(string.split(" "))
                .anyMatch(Word -> "in".equals(Word));
            System.out.println(1);
            System.out.println(1);
            System.out.println(1);
            System.out.println(1);
            System.out.println(1);
            System.out.println(1);
            System.out.println(1);
            System.out.println(1);
            System.out.println(1);
            System.out.println(1);
            System.out.println(1);
        }
        default -> Stream.of(string.split(" "))
            .anyMatch(Word -> "in".equals(Word));
        }
        return true;
    }

    enum Nums {ONE, TWO, THREE}
}
