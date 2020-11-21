package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

/* Config:
 *
 * default
 */
public class InputMatchXpathForbidParameterizedConstructor {

    public InputMatchXpathForbidParameterizedConstructor(Object c) { } // violation

    public InputMatchXpathForbidParameterizedConstructor(int a, String b) { } // violation

    public InputMatchXpathForbidParameterizedConstructor() { } // ok
}
