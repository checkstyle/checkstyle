/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public @interface InputFinalClassAnnotation {

    final class FinalClass {
        private FinalClass() {}
    }

    class DerivedClass extends SuperClass { // violation
        private DerivedClass() {}
    }

    class SuperClass {
        private SuperClass() {}
    }

}

