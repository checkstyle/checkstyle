//non-compiled with javac: Compilable with Java17
package org.checkstyle.suppressionxpathfilter.illegalidentifiername;

/* Config:
 *
 * default
 */
public class InputXpathIllegalIdentifierNameTwo {
    int foo(int te$t) { // warn
        return switch (te$t) {
            case 1 -> 2;
            case 2 -> 3;
            case 3 -> 4;
            default -> 5;
        };
    }
}
