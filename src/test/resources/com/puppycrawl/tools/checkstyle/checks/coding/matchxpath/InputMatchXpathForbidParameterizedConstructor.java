package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

import java.util.HashMap;

/* Config:
 *
 * default
 */
public class InputMatchXpathForbidParameterizedConstructor {

    public InputMatchXpathForbidParameterizedConstructor(Object c) { } // violation

    public InputMatchXpathForbidParameterizedConstructor(int a, HashMap<String, Integer> b) { } // violation

    public InputMatchXpathForbidParameterizedConstructor() { } // ok
}
