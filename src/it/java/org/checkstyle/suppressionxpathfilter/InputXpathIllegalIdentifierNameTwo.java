package org.checkstyle.suppressionxpathfilter;

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
