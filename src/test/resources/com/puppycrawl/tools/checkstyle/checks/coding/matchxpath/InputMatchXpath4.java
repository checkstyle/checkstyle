/*
MatchXpath
query = //METHOD_DEF[./IDENT[@text='wrongName' or @text='nonExistingMethod']]


*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpath4 {
    public void test() { } // violation

    public void foo() { } // violation

    public void correct() { } // ok
}
