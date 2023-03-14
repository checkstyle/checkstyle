/*
MatchXpath
query = //VARIABLE_DEF[./ASSIGN/EXPR/LITERAL_NEW and not(./TYPE/IDENT[@text='var'])]


*/


package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathAvoidInstanceCreationWithoutVar {
    public void test() {
        SomeObject a = new SomeObject(); // violation
        var b = new SomeObject(); // OK
    }

    class SomeObject {}
}
