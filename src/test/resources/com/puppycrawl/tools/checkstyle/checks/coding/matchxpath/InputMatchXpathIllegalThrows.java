package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

/* Config:
 *
 * default
 */
public class InputMatchXpathIllegalThrows {
    public void func1() throws RuntimeException {} // violation
    public void func2() throws Exception {}  // ok
    public void func3() throws Error {}  // violation
    public void func4() throws Throwable {} // violation
    public void func5() throws NullPointerException {} // ok
}
