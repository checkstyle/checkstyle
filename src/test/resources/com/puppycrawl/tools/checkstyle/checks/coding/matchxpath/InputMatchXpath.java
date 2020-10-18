package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

/* Config:
 *
 * query: //METHOD_DEF[./IDENT[@text='test' or @text='foo']]
 */
public class InputMatchXpath {
    public void test() { } // violation

    public void foo() { } // violation

    public void correct() { } // ok
}
