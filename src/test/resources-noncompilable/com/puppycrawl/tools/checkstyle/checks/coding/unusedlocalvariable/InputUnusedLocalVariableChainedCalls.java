/*
UnusedLocalVariable


*/

//non-compiled with javac: Non compilable as hypothetical methods and variables used
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import somepackage.c;
import somepackage.d;

public class InputUnusedLocalVariableChainedCalls {

    public void testChainedCallsWithNewKeyword() {
        Object a; // violation
        Object b; // violation
        a = foo(new c.b(new d.a()));
        a = foo(new b().new a());
    }

    public void testChainedCalls() {
        Object p; // violation
        Object q; // ok
        p = q.foo().p;
    }

    class b {
        class a {
        }
    }

}

