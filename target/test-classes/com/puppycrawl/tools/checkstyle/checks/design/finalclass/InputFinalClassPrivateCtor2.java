/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public class InputFinalClassPrivateCtor2 {

    private static class c {
       private c(){}
    }

    class x extends c {}

    private class a {
    }

    class b extends a {}

    private class PrivateClass { // violation 'Class PrivateClass should be declared as final'
    }
}

class TestClass {
    private class Private {
           public Private() {}
    }
}

class Private2 {

    private class Check { // violation 'Class Check should be declared as final'
        int i = 0;
    }
}

class Private {
    public Private() {}
}

class Private3 {
    private static class K { // violation 'Class K should be declared as final'

    }
}

class CheckStyle {
    static class we {

    }

    private class Modifiers {} // violation 'Class Modifiers should be declared as final'
}

class Check {
    private class Non {
        public Non() {}
        private Non(int i) {}
    }
}

class CheckCode {
    private class None {
        public None() {}
        protected None(int i) {}
    }
}

class a {
    private class Class {
    }

    Class newClass = new Class() {
    };

    private final class Final { }
}
