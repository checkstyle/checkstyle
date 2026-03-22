/*
MatchXpath
query = //METHOD_DEF[./IDENT[@text='test' or @text='foo']]


*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpath3 {
    public void test() { } // violation 'Illegal code structure detected'

    public void foo() { } // violation 'Illegal code structure detected'

    public void correct() { }
}
