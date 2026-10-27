/*
WhenShouldBeUsed


*/

// Java21
package org.checkstyle.suppressionxpathfilter.coding.whenshouldbeused;

public class InputXpathWhenShouldBeUsedSimple {
    void test(Object o) {
        switch (o) {
            case String s -> { // violation ''when' expression should be used*.'
                if (s.isEmpty()) {
                    System.out.println("empty string");
                }
            }
            default -> {}
        }
    }
 }
