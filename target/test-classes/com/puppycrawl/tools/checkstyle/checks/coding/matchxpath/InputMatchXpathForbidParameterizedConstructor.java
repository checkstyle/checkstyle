/*
MatchXpath
query = //CTOR_DEF[count(./PARAMETERS/*) > 0]
message.matchxpath.match = Parameterized constructors are not allowed


*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathForbidParameterizedConstructor {

    // violation below 'Parameterized constructors are not allowed'
    public InputMatchXpathForbidParameterizedConstructor(Object c) { }

    // violation below 'Parameterized constructors are not allowed'
    public InputMatchXpathForbidParameterizedConstructor(int a, String b) { }

    public InputMatchXpathForbidParameterizedConstructor() { }
}
