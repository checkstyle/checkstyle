/*
MatchXpath
query = //VARIABLE_DEF[./ASSIGN/EXPR/LITERAL_NEW and not(./TYPE/IDENT[@text='var'])]


*/


package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathAvoidInstanceCreationWithoutVar {
    public void test() {
        SomeObject a = new SomeObject(); // violation 'Illegal code structure detected'
        var b = new SomeObject();
    }

    class SomeObject {}
}
