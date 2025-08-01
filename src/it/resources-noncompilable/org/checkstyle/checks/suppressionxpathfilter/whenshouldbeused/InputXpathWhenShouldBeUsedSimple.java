// non-compiled with javac: Compilable with Java21
package org.checkstyle.checks.suppressionxpathfilter.whenshouldbeused;

public class InputXpathWhenShouldBeUsedSimple {
    void test(Object o) {
        switch (o) {
            case String s -> { // warn
                if (s.isEmpty()) {
                    System.out.println("empty string");
                }
            }
            default -> {}
        }
    }
 }
