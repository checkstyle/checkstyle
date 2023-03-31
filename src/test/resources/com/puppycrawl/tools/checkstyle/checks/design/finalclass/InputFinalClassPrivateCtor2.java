/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public class InputFinalClassPrivateCtor2 {

    private static class c { // ok
       private c(){}
    }

    class x extends c {} // ok

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

class pr {
    private static class K { // violation 'Class K should be declared as final'

    }
}

class prw {
    static class we {

    }

    private class Modifiers {} // violation 'Class Modifiers should be declared as final'
}

class some {
    private class non {
        public non() {}
        private non(int i) {}
    }
}
