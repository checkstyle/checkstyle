/*
WhenShouldBeUsed


*/

// non-compiled with javac: Compilable with Java25
package org.checkstyle.suppressionxpathfilter.coding.whenshouldbeused;

public class InputXpathWhenShouldBeUsedNested {

    void test(Object o, Object o2, int y) {
        int x = switch (o) {
            case String s -> {
                switch (o2) {
                    case Integer _ -> { // violation ''when' expression should be used*.'
                       if (y == 0) {
                           System.out.println(0);
                       }
                    }
                    default -> {}
                }
                yield 3;
            }
            default -> 3;
        };
    }
}
