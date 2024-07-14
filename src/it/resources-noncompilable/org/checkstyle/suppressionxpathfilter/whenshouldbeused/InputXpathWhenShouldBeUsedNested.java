//non-compiled with javac: Compilable with Java21
package org.checkstyle.suppressionxpathfilter.whenshouldbeused;

public class InputXpathWhenShouldBeUsedNested {

    void test(Object o, Object o2, int y) {
        int x = switch (o) {
            case String s -> {
                int z = switch (o2) {
                    case Integer _ : { // warn
                       if (y == 0) {
                           yield 4;
                       }
                    }
                    default : yield  2;
                };
                yield z;
            }
            default -> 3;
        };
    }
}
