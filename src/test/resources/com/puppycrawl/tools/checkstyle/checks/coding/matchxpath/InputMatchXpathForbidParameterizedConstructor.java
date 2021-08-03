/*
MatchXpath
query = //CTOR_DEF[count(./PARAMETERS/node()) > 0]


*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathForbidParameterizedConstructor {

    public InputMatchXpathForbidParameterizedConstructor(Object c) { } // violation

    public InputMatchXpathForbidParameterizedConstructor(int a, String b) { } // violation

    public InputMatchXpathForbidParameterizedConstructor() { } // ok
}
