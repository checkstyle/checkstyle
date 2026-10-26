/*
HideUtilityClassConstructor
ignoreAnnotatedBy = (default)


*/

package org.checkstyle.suppressionxpathfilter.design.hideutilityclassconstructor;

// violation below 'Utility classes should not have a public or default constructor.'
final class InputXpathHideUtilityClassConstructorFinal {
    private static int value = 0;

    public static void foo(int val) {
        value = val;
    }
}
