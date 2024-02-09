/*
MatchXpath
query = //CTOR_DEF[count(./PARAMETERS/*) > 0]
message.matchxpath.match = Parameterized constructors are not allowed


*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathForbidParameterizedConstructor {

    public InputMatchXpathForbidParameterizedConstructor(Object c) { } // violation

    public InputMatchXpathForbidParameterizedConstructor(int a, String b) { } // violation

    public InputMatchXpathForbidParameterizedConstructor() { }
}
