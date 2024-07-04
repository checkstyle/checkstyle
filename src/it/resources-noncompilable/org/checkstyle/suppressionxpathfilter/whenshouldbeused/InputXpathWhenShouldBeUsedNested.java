//non-compiled with javac: Compilable with Java21
package org.checkstyle.suppressionxpathfilter.whenshouldbeused;

public class InputXpathWhenShouldBeUsedNested {

    void test(Object o, Object o2, int y) {
        int x = switch (o) {
            case String s -> {
                switch (o2) {
                    case Integer _ -> { // warn
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
