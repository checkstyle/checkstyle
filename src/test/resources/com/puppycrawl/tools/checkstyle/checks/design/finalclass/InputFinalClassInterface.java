/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public interface InputFinalClassInterface {

    final class FinalClass {
        private FinalClass() {}
    }

    class DerivedClass extends SuperClass {
        // violation above 'Class DerivedClass should be declared as final'
        private DerivedClass() {}
    }

    class SuperClass {
        private SuperClass() {}
    }

}

