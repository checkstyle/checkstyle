/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public class InputFinalClassPrivateCtor2 {

    private static class c { // ok
       private c(){}
    }

    class x extends c {} // ok

    private class a { // ok
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

class Private {
    public Private() {}
}
