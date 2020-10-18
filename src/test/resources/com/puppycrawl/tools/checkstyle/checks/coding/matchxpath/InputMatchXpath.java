package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

/* Config:
 *
 * default
 */
public class InputMatchXpath {
    public void test() { } // violation

    public void foo() { } // violation

    public void correct() { } // ok
}
