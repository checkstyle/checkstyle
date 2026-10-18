/*
FinalClass


*/

package org.checkstyle.suppressionxpathfilter.design.finalclass;

public class InputXpathFinalClassStaticNestedClass {

    // violation below 'Class StaticNestedClass should be declared as final.'
    static class StaticNestedClass {
        private StaticNestedClass() {}
    }
}
