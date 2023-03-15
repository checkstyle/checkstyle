/*
MatchXpath
query = //METHOD_DEF[./IDENT[@text='test' or @text='foo']]


*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpath3 {
    public void test() { } // violation

    public void foo() { } // violation

    public void correct() { } // ok
}
