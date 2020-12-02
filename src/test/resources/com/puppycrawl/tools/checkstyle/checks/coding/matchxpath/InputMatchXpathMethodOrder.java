package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

/* Config:
 *
 * query: //METHOD_DEF[.//LITERAL_PRIVATE and following-sibling::METHOD_DEF[.//LITERAL_PUBLIC]]
 */
public class InputMatchXpathMethodOrder {
    public void method1() { }

    private void method2() { } // violation

    public void method3() { }

    private void method4() { } // violation

    public void method5() { }

    private void method6() { } // ok
}
