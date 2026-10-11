/*
MatchXpath
query = //LITERAL_THROWS[./IDENT[@text='Throwable' or @text='RuntimeException' or \
        ends-with(@text, 'Error')]]
message.matchxpath.match = Illegal throws statement


*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathIllegalThrows {
    public void func1() throws RuntimeException {} // violation 'Illegal throws statement'
    public void func2() throws Exception {}
    public void func3() throws Error {}  // violation 'Illegal throws statement'
    public void func4() throws Throwable {} // violation 'Illegal throws statement'
    public void func5() throws NullPointerException {}
}
