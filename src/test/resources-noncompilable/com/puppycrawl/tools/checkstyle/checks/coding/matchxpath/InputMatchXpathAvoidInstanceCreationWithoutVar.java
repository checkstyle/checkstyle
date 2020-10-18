package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

/* Config:
 *
 * query: //VARIABLE_DEF[./ASSIGN/EXPR/LITERAL_NEW and not(./TYPE/IDENT[@text='var'])]
 */
public class InputMatchXpathAvoidInstanceCreationWithoutVar {
    public void test() {
        SomeObject o = new SomeObject(); // violation
        var o = new SomeObject(); // OK
    }
}
