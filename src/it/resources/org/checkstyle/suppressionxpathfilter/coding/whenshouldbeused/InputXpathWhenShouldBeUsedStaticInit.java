/*
WhenShouldBeUsed


*/

// Java21
package org.checkstyle.suppressionxpathfilter.coding.whenshouldbeused;

public class InputXpathWhenShouldBeUsedStaticInit {
    static Object obj = "test";
    static {
        switch (obj) {
            case Integer i -> { // violation ''when' expression should be used*.'
                if (i > 0) {
                    System.out.println("positive");
                }
            }
            default -> {}
        }
    }
}
